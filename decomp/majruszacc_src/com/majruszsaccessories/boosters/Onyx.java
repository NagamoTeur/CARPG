package com.majruszsaccessories.boosters;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.boosters.components.EfficiencyBonus;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.BoosterHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.items.BoosterItem;
import net.minecraft.world.entity.monster.warden.Warden;

@AutoInstance
public class Onyx extends BoosterHandler {
   public Onyx() {
      super(MajruszsAccessories.ONYX, Onyx.class);
      this.add(EfficiencyBonus.create(0.09F)).add(Onyx.WardenDropChance.create()).add(TradeOffer.create());
   }

   static class WardenDropChance extends BonusComponent<BoosterItem> {
      float chance = 1.0F;

      public static BonusComponent.ISupplier<BoosterItem> create() {
         return Onyx.WardenDropChance::new;
      }

      protected WardenDropChance(BonusHandler<BoosterItem> handler) {
         super(handler);
         OnLootGenerated.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(Condition.isLogicalServer())
            .addCondition(Condition.chance(() -> this.chance))
            .addCondition(data -> data.lastDamagePlayer != null)
            .addCondition(data -> data.entity instanceof Warden);
         handler.getConfig().define("warden_drop_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }
}
