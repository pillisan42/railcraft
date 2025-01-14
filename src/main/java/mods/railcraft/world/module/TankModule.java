package mods.railcraft.world.module;

import org.jetbrains.annotations.NotNull;
import mods.railcraft.api.core.CompoundTagKeys;
import mods.railcraft.util.fluids.FluidTools;
import mods.railcraft.util.fluids.FluidTools.ProcessType;
import mods.railcraft.world.level.block.entity.tank.TankBlockEntity;
import mods.railcraft.world.level.material.StandardTank;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class TankModule extends ContainerModule<TankBlockEntity> {

  public static final int SLOT_INPUT = 0;
  public static final int SLOT_PROCESS = 1;
  public static final int SLOT_OUTPUT = 2;
  private final StandardTank tank;

  private final IItemHandler itemHandler = new InvWrapper(this) {
    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (slot == SLOT_OUTPUT)
        return ItemStack.EMPTY;
      return super.extractItem(slot, amount, simulate);
    }
  };

  private FluidTools.ProcessState processState = FluidTools.ProcessState.RESET;
  private int processTicks;


  public TankModule(TankBlockEntity provider, StandardTank tank) {
    super(provider, 3);
    this.tank = tank;
  }

  public StandardTank getTank() {
    return this.tank;
  }

  @Override
  public void serverTick() {
    if (this.processTicks++ >= FluidTools.BUCKET_FILL_TIME) {
      this.processTicks = 0;
      this.processState = FluidTools.processContainer(this, this.tank,
          ProcessType.DRAIN_THEN_FILL, this.processState);
    }
  }

  @Override
  public boolean canPlaceItem(int slot, ItemStack stack) {
    return switch (slot) {
      case SLOT_INPUT -> (!this.tank.isEmpty()
          && FluidTools.isRoomInContainer(stack, this.tank.getFluid().getFluid()))
          || FluidUtil.getFluidContained(stack).isPresent();
      case SLOT_PROCESS, SLOT_OUTPUT -> true;
      default -> false;
    } && super.canPlaceItem(slot, stack);
  }

  public IItemHandler getItemHandler() {
    return this.itemHandler;
  }

  @Override
  public CompoundTag serializeNBT(HolderLookup.Provider provider) {
    var tag = super.serializeNBT(provider);
    tag.put(CompoundTagKeys.TANK, this.tank.writeToNBT(provider, new CompoundTag()));
    tag.putString(CompoundTagKeys.PROCESS_STATE, this.processState.getSerializedName());
    return tag;
  }

  @Override
  public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
    super.deserializeNBT(provider, tag);
    this.tank.readFromNBT(provider, tag.getCompound(CompoundTagKeys.TANK));
    this.processState = FluidTools.ProcessState.fromTag(tag);
  }

  @Override
  public void writeToBuf(RegistryFriendlyByteBuf out) {
    super.writeToBuf(out);
    out.writeVarInt(this.tank.getCapacity());
    FluidStack.OPTIONAL_STREAM_CODEC.encode(out, this.tank.getFluid());
  }

  @Override
  public void readFromBuf(RegistryFriendlyByteBuf in) {
    super.readFromBuf(in);
    this.tank.setCapacity(in.readVarInt());
    this.tank.setFluid(FluidStack.OPTIONAL_STREAM_CODEC.decode(in));
  }
}
