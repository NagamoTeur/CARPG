package com.hollingsworth.arsnouveau.api.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IManaCap extends INBTSerializable<CompoundTag> {
   double getCurrentMana();

   int getMaxMana();

   void setMaxMana(int var1);

   double setMana(double var1);

   double addMana(double var1);

   double removeMana(double var1);

   default int getGlyphBonus() {
      return 0;
   }

   default int getBookTier() {
      return 0;
   }

   default void setGlyphBonus(int bonus) {
   }

   default void setBookTier(int tier) {
   }
}
