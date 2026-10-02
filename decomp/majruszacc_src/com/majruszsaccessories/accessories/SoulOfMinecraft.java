package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.BreedingTwins;
import com.majruszsaccessories.accessories.components.FishingExtraTreasure;
import com.majruszsaccessories.accessories.components.FishingLuckBonus;
import com.majruszsaccessories.accessories.components.FishingLureBonus;
import com.majruszsaccessories.accessories.components.HarvestingDoubleCrops;
import com.majruszsaccessories.accessories.components.MiningDurabilityBonus;
import com.majruszsaccessories.accessories.components.MiningExtraItem;
import com.majruszsaccessories.accessories.components.MiningSpeedBonus;
import com.majruszsaccessories.accessories.components.MoreChestLoot;
import com.majruszsaccessories.accessories.components.SleepingBonuses;
import com.majruszsaccessories.accessories.components.StrongerPotions;
import com.majruszsaccessories.accessories.components.SwimmingSpeedBonus;
import com.majruszsaccessories.accessories.components.TamingStrongerAnimals;
import com.majruszsaccessories.accessories.components.TradingDiscount;
import com.majruszsaccessories.common.AccessoryHandler;

@AutoInstance
public class SoulOfMinecraft extends AccessoryHandler {
   public SoulOfMinecraft() {
      super(MajruszsAccessories.SOUL_OF_MINECRAFT, SoulOfMinecraft.class);
      this.add(MoreChestLoot.create(1.8F))
         .add(SwimmingSpeedBonus.create(0.3F))
         .add(FishingLuckBonus.create(3.5F))
         .add(FishingLureBonus.create(0.3F))
         .add(FishingExtraTreasure.create(0.07F))
         .add(TradingDiscount.create(0.18F))
         .add(SleepingBonuses.create(1.5F, 420))
         .add(StrongerPotions.create(0.4F, 1.4F))
         .add(MiningExtraItem.create(0.05F))
         .add(MiningSpeedBonus.create(0.15F))
         .add(MiningDurabilityBonus.create(0.15F))
         .add(TamingStrongerAnimals.create(0.3F))
         .add(BreedingTwins.create(0.36F))
         .add(HarvestingDoubleCrops.create(0.36F));
   }
}
