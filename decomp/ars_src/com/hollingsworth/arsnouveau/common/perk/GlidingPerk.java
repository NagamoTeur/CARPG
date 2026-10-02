package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public class GlidingPerk extends Perk {
   public static final GlidingPerk INSTANCE = new GlidingPerk(new ResourceLocation("ars_nouveau", "thread_gliding"));
   public static final UUID PERK_UUID = UUID.fromString("556fd264-22f2-4454-85b1-19070179f09a");

   public GlidingPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public PerkSlot minimumSlot() {
      return PerkSlot.THREE;
   }

   @Override
   public String getLangName() {
      return "Gliding";
   }

   @Override
   public String getLangDescription() {
      return "Allows you to glide as if you are wearing an elytra. Must be equipped in a slot of at least level 3.";
   }
}
