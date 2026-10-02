package io.redspace.ironsspellbooks.compat.tetra;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.gui.stats.getter.StatGetterAttribute;

public class StatGetterPercentAttribute extends StatGetterAttribute {
   public StatGetterPercentAttribute(Attribute attribute) {
      super(attribute);
      this.withOffset(-1.0);
   }

   public double getValue(Player player, ItemStack itemStack) {
      return 100.0 * super.getValue(player, itemStack);
   }

   public double getValue(Player player, ItemStack itemStack, String slot) {
      return 100.0 * super.getValue(player, itemStack, slot);
   }

   public double getValue(Player player, ItemStack itemStack, String slot, String improvement) {
      return 100.0 * super.getValue(player, itemStack, slot, improvement);
   }

   public boolean shouldShow(Player player, ItemStack currentStack, ItemStack previewStack) {
      double baseValue = 0.0;
      double currentValue = this.getValue(player, currentStack);
      return currentValue != baseValue || this.getValue(player, previewStack) != currentValue;
   }
}
