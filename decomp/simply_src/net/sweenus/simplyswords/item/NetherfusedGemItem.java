package net.sweenus.simplyswords.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.util.HelperMethods;

public class NetherfusedGemItem extends Item {
   public NetherfusedGemItem() {
      super(new Properties().m_41491_(SimplySwords.SIMPLYSWORDS).m_41497_(Rarity.EPIC).m_41486_().m_41487_(1));
   }

   public boolean m_142305_(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
      if (stack.m_41784_().m_128461_("nether_power").isEmpty()) {
         String netherfusedPowerSelection = HelperMethods.chooseNetherfusedPower();
         stack.m_41784_().m_128359_("nether_power", netherfusedPowerSelection);
      }

      return false;
   }

   public void m_7836_(ItemStack stack, Level world, Player player) {
      if (!world.f_46443_) {
         String netherfusedPowerSelection = HelperMethods.chooseNetherfusedPower();
         stack.m_41784_().m_128359_("nether_power", netherfusedPowerSelection);
      }
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130940_(ChatFormatting.GOLD);
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      if (itemStack.m_41784_().m_128461_("nether_power").contains("greater")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.greater_nether_power").m_130944_(new ChatFormatting[]{ChatFormatting.DARK_AQUA, ChatFormatting.BOLD})
         );
      }

      if (itemStack.m_41784_().m_128461_("nether_power").isEmpty()) {
         tooltip.add(Component.m_237115_("item.simplyswords.unidentifiedsworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("echo")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.echo.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("berserk")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.berserk.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("radiance")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.radiance.description3"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("onslaught")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description3"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description4"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description5"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.onslaught.description6"));
      }

      if (itemStack.m_41784_().m_128461_("nether_power").equals("nullification")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification").m_130940_(ChatFormatting.RED));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description2"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description3"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description4"));
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.netherfused_power.nullification.description5"));
      }

      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.gem_description").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      tooltip.add(Component.m_237115_("item.simplyswords.gem_description2").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
   }
}
