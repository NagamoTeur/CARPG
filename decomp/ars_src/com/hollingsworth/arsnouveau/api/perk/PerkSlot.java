package com.hollingsworth.arsnouveau.api.perk;

import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;

public class PerkSlot {
   public static ConcurrentHashMap<ResourceLocation, PerkSlot> PERK_SLOTS = new ConcurrentHashMap<>();
   public static final PerkSlot ONE = new PerkSlot(new ResourceLocation("ars_nouveau", "one"), 1);
   public static final PerkSlot TWO = new PerkSlot(new ResourceLocation("ars_nouveau", "two"), 2);
   public static final PerkSlot THREE = new PerkSlot(new ResourceLocation("ars_nouveau", "three"), 3);
   public final ResourceLocation id;
   public final int value;

   public PerkSlot(ResourceLocation id, int value) {
      this.value = value;
      this.id = id;
   }

   static {
      PERK_SLOTS.put(ONE.id, ONE);
      PERK_SLOTS.put(TWO.id, TWO);
      PERK_SLOTS.put(THREE.id, THREE);
   }
}
