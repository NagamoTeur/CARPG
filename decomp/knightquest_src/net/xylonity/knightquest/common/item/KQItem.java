package net.xylonity.knightquest.common.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.KnightQuest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KQItem extends Item {
   private final String tooltipInfoName;

   public KQItem(Properties pProperties, String tooltipInfoName) {
      super(pProperties.m_41491_(KnightQuest.CREATIVE_MODE_TAB));
      this.tooltipInfoName = tooltipInfoName;
   }

   public void m_7373_(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
      pTooltipComponents.add(Component.m_237115_("tooltip.item.knightquest." + this.tooltipInfoName));
      super.m_7373_(pStack, pLevel, pTooltipComponents, pIsAdvanced);
   }
}
