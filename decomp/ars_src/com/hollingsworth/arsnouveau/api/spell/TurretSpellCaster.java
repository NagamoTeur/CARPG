package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TurretSpellCaster extends SpellCaster {
   public TurretSpellCaster(ItemStack stack) {
      super(stack);
   }

   public TurretSpellCaster(CompoundTag itemTag) {
      super(itemTag);
   }

   @Override
   public ResourceLocation getTagID() {
      return new ResourceLocation("ars_nouveau", "turret_caster");
   }
}
