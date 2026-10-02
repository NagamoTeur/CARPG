package com.majruszsaccessories.accessories;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.events.OnItemFished;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.registry.Registries;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.AccessoryIncompatibility;
import com.majruszsaccessories.accessories.components.FishingLuckBonus;
import com.majruszsaccessories.common.AccessoryHandler;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.common.components.TradeOffer;
import com.majruszsaccessories.events.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

@AutoInstance
public class AnglerTrophy extends AccessoryHandler {
   public AnglerTrophy() {
      super(MajruszsAccessories.ANGLER_TROPHY, AnglerTrophy.class);
      this.add(FishingLuckBonus.create(2.5F))
         .add(AnglerTrophy.FishingDropChance.create(0.01F))
         .add(TradeOffer.create())
         .add(AccessoryIncompatibility.create(MajruszsAccessories.ANGLER_RUNE))
         .add(AccessoryIncompatibility.create(MajruszsAccessories.SOUL_OF_MINECRAFT));
   }

   public static class FishDropChance extends BonusComponent<AccessoryItem> {
      float fishChance;
      List<ResourceLocation> lootIds = Stream.of("minecraft:cod", "minecraft:pufferfish", "minecraft:salmon", "minecraft:tropical_fish")
         .<ResourceLocation>map(ResourceLocation::new)
         .toList();

      public static BonusComponent.ISupplier<AccessoryItem> create(float chance) {
         return handler -> new AnglerTrophy.FishDropChance(handler, chance);
      }

      protected FishDropChance(BonusHandler<AccessoryItem> handler, float chance) {
         super(handler);
         this.fishChance = chance;
         OnLootGenerated.listen(x$0 -> this.addToGeneratedLoot(x$0))
            .addCondition(data -> data.entity != null)
            .addCondition(data -> this.lootIds.contains(Registries.ENTITY_TYPES.getId(data.entity.m_6095_())))
            .addCondition(CustomConditions.dropChance(s -> this.fishChance, data -> data.killer));
         handler.getConfig()
            .define("fish_drop_chance", Reader.number(), s -> this.fishChance, (s, v) -> this.fishChance = (Float)Range.CHANCE.clamp(v))
            .define("fish_ids", Reader.list(Reader.location()), s -> this.lootIds, (s, v) -> this.lootIds = v);
      }
   }

   public static class FishingDropChance extends BonusComponent<AccessoryItem> {
      float fishingChance = 0.005F;

      public static BonusComponent.ISupplier<AccessoryItem> create(float chance) {
         return handler -> new AnglerTrophy.FishingDropChance(handler, chance);
      }

      protected FishingDropChance(BonusHandler<AccessoryItem> handler, float chance) {
         super(handler);
         this.fishingChance = chance;
         OnItemFished.listen(this::onFished).addCondition(CustomConditions.dropChance(s -> this.fishingChance, data -> data.player));
         handler.getConfig()
            .define("fishing_drop_chance", Reader.number(), s -> this.fishingChance, (s, v) -> this.fishingChance = (Float)Range.CHANCE.clamp(v));
      }

      private void onFished(OnItemFished data) {
         this.spawnFlyingItem(data.getLevel(), data.hook.m_20182_(), data.player.m_20182_());
      }
   }
}
