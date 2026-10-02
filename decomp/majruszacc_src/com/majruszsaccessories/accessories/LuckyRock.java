package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.MiningExtraItem;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;

@AutoInstance
public class LuckyRock extends AccessoryHandler {
   public LuckyRock() {
      super(MajruszsAccessories.LUCKY_ROCK, LuckyRock.class);
      this.add(MiningExtraItem.create(0.03F))
         .add(LuckyRock.MiningDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.MINER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class MiningDropChance extends BonusComponent<AccessoryItem> {
      float chance = 7.5E-4F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return LuckyRock.MiningDropChance::new;
      }

      protected MiningDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         MiningExtraItem.OnStoneMined.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(CustomConditions.dropChance(s -> this.chance, data -> data.entity));
         handler.getConfig().define("mining_drop_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }
}
