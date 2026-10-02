package com.hollingsworth.arsnouveau.api.mana;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import net.minecraft.world.item.ItemStack;

public interface IManaDiscountEquipment {
   default int getManaDiscount(ItemStack i) {
      return this.getManaDiscount(i, new Spell());
   }

   default int getManaDiscount(ItemStack i, Spell spell) {
      return 0;
   }
}
