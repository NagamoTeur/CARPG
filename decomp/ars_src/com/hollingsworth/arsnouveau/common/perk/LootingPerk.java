package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.Perk;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public class LootingPerk extends Perk {
   public static final LootingPerk INSTANCE = new LootingPerk(new ResourceLocation("ars_nouveau", "thread_drygmy"));
   public static final UUID PERK_UUID = UUID.fromString("ff9459e5-ec2c-44c8-ac3b-19c78c76b4bb");

   public LootingPerk(ResourceLocation key) {
      super(key);
   }

   @Override
   public String getLangName() {
      return "Drygmy";
   }

   @Override
   public String getLangDescription() {
      return "Grants an additional stack of looting.";
   }
}
