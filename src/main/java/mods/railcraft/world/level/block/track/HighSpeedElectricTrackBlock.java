package mods.railcraft.world.level.block.track;

import java.util.List;
import mods.railcraft.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class HighSpeedElectricTrackBlock extends TrackBlock {

  public HighSpeedElectricTrackBlock(Properties properties) {
    super(TrackTypes.HIGH_SPEED_ELECTRIC, properties);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context,
      List<Component> tooltip, TooltipFlag flag) {
    tooltip.add(Component.translatable(Translations.Tips.DANGER)
        .append(CommonComponents.SPACE)
        .append(Component.translatable(Translations.Tips.HIGH_VOLTAGE_SPEED))
        .withStyle(ChatFormatting.BLUE));
    tooltip.add(Component.translatable(Translations.Tips.VERY_FAST)
        .withStyle(ChatFormatting.WHITE));
    tooltip.add(Component.translatable(Translations.Tips.POWERED_BY_ELECTRICITY)
        .withStyle(ChatFormatting.WHITE));
    tooltip.add(Component.translatable(Translations.Tips.USE_ELECTRIC_LOCOMOTIVE)
        .withStyle(ChatFormatting.GRAY));
    tooltip.add(Component.translatable(Translations.Tips.REQUIRE_BOOSTERS_TRANSITION)
        .withStyle(ChatFormatting.GRAY));
    tooltip.add(Component.translatable(Translations.Tips.CANNOT_MAKE_CORNERS_HIGH_SPEED)
        .withStyle(ChatFormatting.GRAY));
  }
}
