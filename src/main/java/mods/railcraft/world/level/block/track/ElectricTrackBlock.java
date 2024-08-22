package mods.railcraft.world.level.block.track;

import java.util.List;
import mods.railcraft.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ElectricTrackBlock extends TrackBlock {

  public ElectricTrackBlock(Properties properties) {
    super(TrackTypes.ELECTRIC, properties);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context,
      List<Component> tooltip, TooltipFlag flag) {
    tooltip.add(Component.translatable(Translations.Tips.DANGER)
        .append(CommonComponents.SPACE)
        .append(Component.translatable(Translations.Tips.HIGH_VOLTAGE))
        .withStyle(ChatFormatting.BLUE));
    tooltip.add(Component.translatable(Translations.Tips.POWERED_BY_ELECTRICITY)
        .withStyle(ChatFormatting.WHITE));
    tooltip.add(Component.translatable(Translations.Tips.USE_ELECTRIC_LOCOMOTIVE)
        .withStyle(ChatFormatting.GRAY));
  }
}
