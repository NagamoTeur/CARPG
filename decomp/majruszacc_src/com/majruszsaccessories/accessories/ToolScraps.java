package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnItemDamaged;
import com.majruszlibrary.events.base.Priority;
import com.majruszlibrary.math.AnyPos;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.MiningDurabilityBonus;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;

@AutoInstance
public class ToolScraps extends AccessoryHandler {
   public ToolScraps() {
      super(MajruszsAccessories.TOOL_SCRAPS, ToolScraps.class);
      this.add(MiningDurabilityBonus.create(0.1F))
         .add(ToolScraps.MiningDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.MINER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class MiningDropChance extends BonusComponent<AccessoryItem> {
      float multiplier = 3.0E-4F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return ToolScraps.MiningDropChance::new;
      }

      protected MiningDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         OnItemDamaged.listen(this::spawnScraps)
            .priority(Priority.LOWEST)
            .addCondition(OnItemDamaged::isAboutToBroke)
            .addCondition(data -> data.player != null)
            .addCondition(CustomConditions.dropChance(data -> this.multiplier * (float)data.itemStack.m_41776_(), data -> data.player));
         handler.getConfig()
            .define("durability_drop_chance_multiplier", Reader.number(), s -> this.multiplier, (s, v) -> this.multiplier = (Float)Range.CHANCE.clamp(v));
      }

      private void spawnScraps(OnItemDamaged data) {
         AnyPos pos = AnyPos.from(data.player.m_20182_());
         this.spawnFlyingItem(data.getLevel(), pos.vec3(), pos.add(0.0F, 1.0F, 0.0F).vec3());
      }
   }
}
