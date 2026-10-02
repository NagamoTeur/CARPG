package com.hollingsworth.arsnouveau.api.spell;

import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BasicReductionCaster extends SpellCaster {
   Function<Spell, Spell> modificationFunc;

   public BasicReductionCaster(ItemStack stack, Function<Spell, Spell> modifyFunc) {
      super(stack);
      this.modificationFunc = modifyFunc;
   }

   public BasicReductionCaster(CompoundTag itemTag, Function<Spell, Spell> modifyFunc) {
      super(itemTag);
      this.modificationFunc = modifyFunc;
   }

   @Override
   public Spell modifySpellBeforeCasting(Level worldIn, @Nullable Entity playerIn, @Nullable InteractionHand handIn, Spell spell) {
      return this.modificationFunc.apply(spell);
   }
}
