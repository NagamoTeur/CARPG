package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.level.BlockHelper;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.SwimmingSpeedBonus;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

@AutoInstance
public class SwimmerGuide extends AccessoryHandler {
   public SwimmerGuide() {
      super(MajruszsAccessories.SWIMMER_GUIDE, SwimmerGuide.class);
      this.add(SwimmingSpeedBonus.create(0.2F))
         .add(SwimmerGuide.UnderwaterChestDropChance.create())
         .add(SwimmerGuide.BuriedTreasureDropChance.create())
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.ADVENTURER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   static class BuriedTreasureDropChance extends BonusComponent<AccessoryItem> {
      float chance = 0.25F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return SwimmerGuide.BuriedTreasureDropChance::new;
      }

      protected BuriedTreasureDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         OnLootGenerated.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(Condition.hasLevel())
            .addCondition(data -> data.origin != null)
            .addCondition(data -> data.lootId.equals(BuiltInLootTables.f_78692_))
            .addCondition(CustomConditions.dropChance(s -> this.chance, data -> data.entity));
         handler.getConfig().define("buried_treasure_spawn_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }

   static class UnderwaterChestDropChance extends BonusComponent<AccessoryItem> {
      float chance = 0.05F;

      public static BonusComponent.ISupplier<AccessoryItem> create() {
         return SwimmerGuide.UnderwaterChestDropChance::new;
      }

      protected UnderwaterChestDropChance(BonusHandler<AccessoryItem> handler) {
         super(handler);
         OnLootGenerated.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(Condition.hasLevel())
            .addCondition(data -> data.origin != null)
            .addCondition(data -> BlockHelper.getState(data.getLevel(), data.origin).m_60819_().m_164512_(Fluids.f_76193_))
            .addCondition(data -> data.lootId.toString().contains("chest"))
            .addCondition(CustomConditions.dropChance(s -> this.chance, data -> data.entity));
         handler.getConfig().define("underwater_chest_spawn_chance", Reader.number(), s -> this.chance, (s, v) -> this.chance = (Float)Range.CHANCE.clamp(v));
      }
   }
}
