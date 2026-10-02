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

public class RunefusedGemItem extends Item {
   public RunefusedGemItem() {
      super(new Properties().m_41491_(SimplySwords.SIMPLYSWORDS).m_41497_(Rarity.EPIC).m_41486_().m_41487_(1));
   }

   public boolean m_142305_(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
      if (stack.m_41784_().m_128461_("runic_power").isEmpty()) {
         String runefusedPowerSelection = HelperMethods.chooseRunefusedPower();
         stack.m_41784_().m_128359_("runic_power", runefusedPowerSelection);
      }

      return false;
   }

   public void m_7836_(ItemStack stack, Level world, Player player) {
      if (!world.f_46443_) {
         String runefusedPowerSelection = HelperMethods.chooseRunefusedPower();
         stack.m_41784_().m_128359_("runic_power", runefusedPowerSelection);
      }
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130940_(ChatFormatting.GOLD);
   }

   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      if (itemStack.m_41784_().m_128461_("runic_power").contains("greater")) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.greater_runic_power").m_130944_(new ChatFormatting[]{ChatFormatting.DARK_AQUA, ChatFormatting.BOLD})
         );
      }

      if (itemStack.m_41784_().m_128461_("runic_power").isEmpty()) {
         tooltip.add(
            Component.m_237115_("item.simplyswords.unidentifiedsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.AQUA, ChatFormatting.BOLD})
         );
         tooltip.add(Component.m_237115_("item.simplyswords.unidentifiedsworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("freeze")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.freeze").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.freezesworditem.tooltip2"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("wildfire")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.wildfire").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wildfiresworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("slow")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.slow").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.slownesssworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("swiftness")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.swiftness").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.speedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("float")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.float").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.levitationsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("zephyr")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.zephyr").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.zephyrsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("shielding")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.shielding").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.shieldingsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("stoneskin")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.stoneskin").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.stoneskinsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("frost_ward")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.frost_ward").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.frostwardsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("trailblaze")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.trailblaze").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.trailblazesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("active_defence")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.active_defence").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.activedefencesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("weaken")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.weaken").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.weakensworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("unstable")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.unstable").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.unstablesworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("momentum")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.momentum").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.momentumsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("imbued")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.imbued").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.imbuedsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").contains("pincushion")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.pincushion").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.pincushionsworditem.tooltip3"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("ward")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.ward").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.wardsworditem.tooltip4"));
      }

      if (itemStack.m_41784_().m_128461_("runic_power").equals("immolation")) {
         tooltip.add(Component.m_237115_("item.simplyswords.uniquesworditem.runefused_power.immolation").m_130940_(ChatFormatting.AQUA));
         tooltip.add(Component.m_237113_(""));
         tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip2"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip3"));
         tooltip.add(Component.m_237115_("item.simplyswords.immolationsworditem.tooltip4"));
      }

      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.gem_description").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      tooltip.add(Component.m_237115_("item.simplyswords.gem_description2").m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
   }
}
