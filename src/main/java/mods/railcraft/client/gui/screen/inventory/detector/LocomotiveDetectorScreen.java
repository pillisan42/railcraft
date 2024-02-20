package mods.railcraft.client.gui.screen.inventory.detector;

import mods.railcraft.Translations;
import mods.railcraft.api.core.RailcraftConstants;
import mods.railcraft.client.gui.screen.inventory.RailcraftMenuScreen;
import mods.railcraft.world.inventory.detector.LocomotiveDetectorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LocomotiveDetectorScreen extends RailcraftMenuScreen<LocomotiveDetectorMenu> {

  private static final ResourceLocation BACKGROUND_TEXTURE =
      RailcraftConstants.rl("textures/gui/container/double_slot.png");

  public LocomotiveDetectorScreen(LocomotiveDetectorMenu menu, Inventory inventory, Component title) {
    super(menu, inventory, title);
    this.imageHeight = 170;
    this.inventoryLabelY = this.imageHeight - 94;
  }

  @Override
  public ResourceLocation getWidgetsTexture() {
    return BACKGROUND_TEXTURE;
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    super.renderLabels(guiGraphics, mouseX, mouseY);
    guiGraphics.drawString(this.font, Component.translatable(Translations.Screen.LOCOMOTIVE_DETECTOR_PRIMARY), 60,
        31, 0x404040, false);
    guiGraphics.drawString(this.font, Component.translatable(Translations.Screen.LOCOMOTIVE_DETECTOR_SECONDARY), 60,
        57, 0x404040, false);
  }
}
