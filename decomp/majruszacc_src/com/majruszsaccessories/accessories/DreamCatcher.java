package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnPlayerWakedUp;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.SleepingBonuses;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;

@AutoInstance
public class DreamCatcher extends AccessoryHandler {
   public DreamCatcher() {
      super(MajruszsAccessories.DREAM_CATCHER, DreamCatcher.class);
      this.add(SleepingBonuses.create(1.0F, 300))
         .add(DreamCatcher.SleepingDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.HOUSEHOLD_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class SleepingDropChance extends BonusComponent<AccessoryItem> {
      float chance = 0.1F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return DreamCatcher.SleepingDropChance::new;
      }

      protected SleepingDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         OnPlayerWakedUp.listen(this::spawnAccessory)
            .addCondition(Condition.isLogicalServer())
            .addCondition(data -> !data.wasSleepStoppedManually)
            .addCondition(CustomConditions.dropChance(s -> this.chance, data -> data.player));
         handler.getConfig().define("sleeping_drop_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }

      private void spawnAccessory(OnPlayerWakedUp data) {
         this.spawnFlyingItem(data.getLevel(), data.player.m_20182_());
      }
   }
}
