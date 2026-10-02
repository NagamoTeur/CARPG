package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ISpellCasterProvider {
   ISpellCaster getSpellCaster();

   default ISpellCaster getSpellCaster(ItemStack stack) {
      return this.getSpellCaster(stack.m_41784_());
   }

   ISpellCaster getSpellCaster(CompoundTag var1);
}
