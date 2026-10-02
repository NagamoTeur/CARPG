package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.collection.DefaultMap;
import com.majruszlibrary.collection.DefaultMap.Entry;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Event;
import com.majruszlibrary.item.LootHelper;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

public class MiningExtraItem extends BonusComponent<AccessoryItem> {
   RangedFloat chance = new RangedFloat().id("chance").maxRange(Range.CHANCE);
   Map<String, ResourceLocation> lootIds = DefaultMap.of(
      new Entry[]{
         DefaultMap.defaultEntry(MajruszsAccessories.HELPER.getLocation("gameplay/lucky_rock_default")),
         DefaultMap.entry("minecraft:the_nether", MajruszsAccessories.HELPER.getLocation("gameplay/lucky_rock_nether")),
         DefaultMap.entry("minecraft:the_end", MajruszsAccessories.HELPER.getLocation("gameplay/lucky_rock_end"))
      }
   );

   public static BonusComponent.ISupplier<AccessoryItem> create(float chance) {
      return handler -> new MiningExtraItem(handler, chance);
   }

   protected MiningExtraItem(BonusHandler<AccessoryItem> handler, float chance) {
      super(handler);
      this.chance.set(chance, Range.CHANCE);
      MiningExtraItem.OnStoneMined.listen(this::addExtraLoot);
      this.addTooltip("majruszsaccessories.bonuses.extra_stone_loot", new ITooltipProvider[]{TooltipHelper.asPercent(this.chance)});
      handler.getConfig().define("extra_mining_item", subconfig -> {
         this.chance.define(subconfig);
         subconfig.define("loot_ids", Reader.map(Reader.location()), s -> this.lootIds, (s, v) -> this.lootIds = DefaultMap.of(v));
      });
   }

   private void addExtraLoot(OnLootGenerated data) {
      AccessoryHolder holder = AccessoryHolders.get((LivingEntity)data.entity).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled() && Random.check(holder.apply(this.chance))) {
         LivingEntity entity = (LivingEntity)data.entity;
         ResourceLocation id = this.lootIds.get(entity.m_9236_().m_46472_().m_135782_().toString());
         data.generatedLoot.addAll(LootHelper.getLootTable(id).m_230922_(LootHelper.toGiftParams(entity)));
         this.spawnEffects(data, holder);
      }
   }

   private void spawnEffects(OnLootGenerated data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(3).offset(ParticleEmitter.offset(0.2F)).position(data.origin).emit(data.getServerLevel());
   }

   public static class OnStoneMined {
      public static Event<OnLootGenerated> listen(Consumer<OnLootGenerated> consumer) {
         return OnLootGenerated.listen(consumer)
            .addCondition(Condition.isLogicalServer())
            .addCondition(data -> data.blockState != null)
            .addCondition(
               data -> data.blockState.m_204336_(BlockTags.f_13061_)
                     || data.blockState.m_204336_(BlockTags.f_13062_)
                     || data.blockState.m_60713_(Blocks.f_50259_)
            )
            .addCondition(data -> data.entity instanceof LivingEntity)
            .addCondition(data -> data.origin != null);
      }
   }
}
