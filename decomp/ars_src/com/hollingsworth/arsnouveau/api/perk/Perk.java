package com.hollingsworth.arsnouveau.api.perk;

import net.minecraft.resources.ResourceLocation;

public abstract class Perk implements IPerk {
   private ResourceLocation id;

   public Perk(ResourceLocation key) {
      this.id = key;
   }

   @Override
   public ResourceLocation getRegistryName() {
      return this.id;
   }
}
