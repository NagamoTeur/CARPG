package net.sweenus.simplyswords.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.SimplySwords;

public class EmpoweredRemnantItem extends Item {
   public EmpoweredRemnantItem() {
      super(new Properties().m_41491_(SimplySwords.SIMPLYSWORDS).m_41497_(Rarity.EPIC).m_41486_());
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130940_(ChatFormatting.GOLD);
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.remnant_description").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      tooltip.add(Component.m_237115_("item.simplyswords.remnant_description2").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      tooltip.add(Component.m_237115_("item.simplyswords.remnant_description3").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      tooltip.add(Component.m_237115_("item.simplyswords.remnant_description4").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
   }
}
