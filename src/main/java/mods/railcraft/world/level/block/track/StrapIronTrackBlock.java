package mods.railcraft.world.level.block.track;

import java.util.List;
import mods.railcraft.Translations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class StrapIronTrackBlock extends TrackBlock {

  public StrapIronTrackBlock(Properties properties) {
    super(TrackTypes.STRAP_IRON, properties);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context,
      List<Component> tooltip, TooltipFlag flag) {
    tooltip.add(Component.translatable(Translations.Tips.STRAP_IRON_TRACK)
        .withStyle(ChatFormatting.GRAY));
  }
}
