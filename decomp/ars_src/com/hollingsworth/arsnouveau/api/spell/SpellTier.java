package com.hollingsworth.arsnouveau.api.spell;

import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;

public class SpellTier {
   public static final ConcurrentHashMap<Integer, SpellTier> SPELL_TIER_MAP = new ConcurrentHashMap<>();
   public static SpellTier ONE = createTier(new ResourceLocation("ars_nouveau", "one"), 1);
   public static SpellTier TWO = createTier(new ResourceLocation("ars_nouveau", "two"), 2);
   public static SpellTier THREE = createTier(new ResourceLocation("ars_nouveau", "three"), 3);
   public static SpellTier CREATIVE = createTier(new ResourceLocation("ars_nouveau", "creative"), 99);
   public int value;
   public ResourceLocation id;

   @Deprecated
   public SpellTier(ResourceLocation id, int value) {
      this.value = value;
      this.id = id;
      if (value > 99) {
         throw new IllegalArgumentException("Spell tier cannot be greater than 99");
      }
   }

   public static SpellTier createTier(ResourceLocation id, int value) {
      SpellTier tier = new SpellTier(id, value);
      SPELL_TIER_MAP.put(value, tier);
      return tier;
   }
}
