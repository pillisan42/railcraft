package mods.railcraft.world.level.block.entity;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import mods.railcraft.api.core.CompoundTagKeys;
import mods.railcraft.data.recipes.builders.RollingRecipeBuilder;
import mods.railcraft.util.container.AdvancedContainer;
import mods.railcraft.util.container.ContainerTools;
import mods.railcraft.world.inventory.ManualRollingMachineMenu;
import mods.railcraft.world.item.crafting.RailcraftRecipeTypes;
import mods.railcraft.world.item.crafting.RollingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ManualRollingMachineBlockEntity extends RailcraftBlockEntity implements MenuProvider {

  private final AdvancedContainer invResult;
  private final FakeRollingContainer matrixListener = new FakeRollingContainer();
  protected final RollingCraftingContainer craftMatrix =
      new RollingCraftingContainer(matrixListener, 3, 3);

  protected boolean isWorking, useLast;
  private Optional<RecipeHolder<RollingRecipe>> currentRecipe = Optional.empty();
  private int progress, clock = 0, processTime = 100;

  public ManualRollingMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntityType, blockPos, blockState);
    this.invResult = new AdvancedContainer(1);
  }

  public ManualRollingMachineBlockEntity(BlockPos blockPos, BlockState blockState) {
    this(RailcraftBlockEntityTypes.MANUAL_ROLLING_MACHINE.get(), blockPos, blockState);
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
    super.saveAdditional(tag, provider);
    tag.put(CompoundTagKeys.CONTAINER, this.invResult.createTag(provider));
    tag.put(CompoundTagKeys.CRAFT_MATRIX, ContainerTools.writeContainer(craftMatrix, provider));
    tag.putInt(CompoundTagKeys.PROGRESS, this.progress);
  }

  @Override
  public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
    super.loadAdditional(tag, provider);
    this.invResult.fromTag(tag.getList(CompoundTagKeys.CONTAINER, Tag.TAG_COMPOUND), provider);
    ContainerTools.readContainer(this.craftMatrix,
        tag.getList(CompoundTagKeys.CRAFT_MATRIX, Tag.TAG_COMPOUND), provider);
    this.progress = tag.getInt(CompoundTagKeys.PROGRESS);
  }

  @Override
  public void setChanged() {
    super.setChanged();
    this.craftMatrix.setChanged();
  }

  public int getProgress() {
    return this.progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public void setProcessTime(int processTime) {
    this.processTime = Math.max(processTime, 1);
  }

  public int getProcessTime() {
    return this.processTime;
  }

  public RollingCraftingContainer getCraftMatrix(AbstractContainerMenu listener) {
    this.matrixListener.listener = listener;
    return this.craftMatrix;
  }

  public AdvancedContainer getInvResult() {
    return this.invResult;
  }

  public Optional<RecipeHolder<RollingRecipe>> getRecipe() {
    return this.level.getRecipeManager()
        .getRecipeFor(RailcraftRecipeTypes.ROLLING.get(), this.craftMatrix.asCraftInput(), this.level);
  }

  public static void serverTick(Level level, BlockPos blockPos, BlockState blockState,
      ManualRollingMachineBlockEntity blockEntity) {
    blockEntity.balanceSlots();

    if (++blockEntity.clock % 8 == 0) {
      blockEntity.currentRecipe = blockEntity.getRecipe();
      blockEntity.processTime = blockEntity.currentRecipe
          .map(RecipeHolder::value)
          .map(RollingRecipe::getProcessTime)
          .orElse(RollingRecipeBuilder.DEFAULT_PROCESSING_TIME);
      blockEntity.clock = 0;
    }

    if (blockEntity.currentRecipe.isPresent() && blockEntity.canMakeMore()) {
      var recipe = blockEntity.currentRecipe.get();
      if (blockEntity.progress >= recipe.value().getProcessTime()) {
        blockEntity.isWorking = false;
        var result = recipe.value().assemble(blockEntity.craftMatrix.asCraftInput(), level.registryAccess());
        if (blockEntity.invResult.canFit(result)) {
          blockEntity.craftMatrix.getItems().forEach(x -> x.shrink(1));
          blockEntity.invResult.insert(result);
          blockEntity.useLast = false;
          blockEntity.progress = 0;
          blockEntity.currentRecipe = Optional.empty();
        }
      } else {
        blockEntity.isWorking = true;
        blockEntity.progress();
      }
    } else {
      blockEntity.progress = 0;
      blockEntity.isWorking = false;
    }
  }

  protected void progress() {
    this.progress++;
  }

  private void balanceSlots() {
    int size = this.craftMatrix.getContainerSize();
    for (int i = 0; i < size; i++) {
      var stackA = this.craftMatrix.getItem(i);
      if (stackA.isEmpty())
        continue;
      for (int j = 0; j < size; j++) {
        if (i == j)
          continue;
        var stackB = this.craftMatrix.getItem(j);
        if (stackB.isEmpty())
          continue;
        if (ItemStack.isSameItem(stackA, stackB))
          if (stackA.getCount() > stackB.getCount() + 1) {
            stackA.shrink(1);
            stackB.grow(1);
            this.craftMatrix.setItem(i, stackA);
            this.craftMatrix.setItem(j, stackB);
            return;
          }
      }
    }
  }

  public void setUseLast() {
    this.useLast = true;
  }

  public boolean canMakeMore() {
    if (this.getRecipe().isEmpty())
      return false;
    if (this.useLast)
      return true;
    return this.craftMatrix.getItems()
        .stream()
        .filter(x -> !x.isEmpty())
        .allMatch(x -> x.getCount() > 1);
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
    return new ManualRollingMachineMenu(containerId, inventory, this);
  }

  public static class RollingCraftingContainer extends TransientCraftingContainer {

    private RollingCraftingContainer(FakeRollingContainer menu, int width, int height) {
      super(menu, width, height);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
      if (stack.isEmpty())
        return false;
      if (!stack.isStackable())
        return false;
      return !getItem(index).isEmpty();
    }
  }

  private static class FakeRollingContainer extends AbstractContainerMenu {

    @Nullable
    AbstractContainerMenu listener;

    public FakeRollingContainer() {
      super(null, 0);
      this.listener = null;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
      return true;
    }

    @Override
    public void slotsChanged(Container container) {
      if (listener != null) {
        listener.slotsChanged(container);
      }
    }
  }
}
