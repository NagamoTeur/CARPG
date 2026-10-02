package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.MiningSpeedBonus;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;

@AutoInstance
public class MinerGuide extends AccessoryHandler {
   public MinerGuide() {
      super(MajruszsAccessories.MINER_GUIDE, MinerGuide.class);
      this.add(MiningSpeedBonus.create(0.1F))
         .add(MinerGuide.UndergroundChestDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.MINER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class UndergroundChestDropChance extends BonusComponent<AccessoryItem> {
      float chance = 0.05F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return MinerGuide.UndergroundChestDropChance::new;
      }

      protected UndergroundChestDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         OnLootGenerated.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(data -> data.origin != null && data.origin.f_82480_ < 50.0)
            .addCondition(data -> data.lootId.toString().contains("chest"))
            .addCondition(CustomConditions.dropChance(s -> this.chance, data -> data.entity));
         handler.getConfig().define("underground_chest_spawn_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }
}
