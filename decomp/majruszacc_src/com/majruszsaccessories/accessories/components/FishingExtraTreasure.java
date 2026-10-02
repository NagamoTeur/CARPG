package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.events.OnFishingExtraItemsGet;
import com.majruszlibrary.item.LootHelper;
import com.majruszlibrary.level.LevelHelper;
import com.majruszlibrary.math.AnyPos;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class FishingExtraTreasure extends BonusComponent<AccessoryItem> {
   RangedFloat chance = new RangedFloat().id("chance").maxRange(Range.CHANCE);

   public static BonusComponent.ISupplier<AccessoryItem> create(float chance) {
      return handler -> new FishingExtraTreasure(handler, chance);
   }

   protected FishingExtraTreasure(BonusHandler<AccessoryItem> handler, float chance) {
      super(handler);
      this.chance.set(chance, Range.CHANCE);
      OnFishingExtraItemsGet.listen(this::addExtraTreasure);
      this.addTooltip("majruszsaccessories.bonuses.extra_fishing_treasure", new ITooltipProvider[]{TooltipHelper.asPercent(this.chance)});
      handler.getConfig().define("extra_fishing_treasure", this.chance::define);
   }

   private void addExtraTreasure(OnFishingExtraItemsGet data) {
      AccessoryHolder holder = AccessoryHolders.get(data.player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled() && Random.check(holder.apply(this.chance))) {
         data.extraItems.addAll(LootHelper.getLootTable(BuiltInLootTables.f_78722_).m_230922_(LootHelper.toGiftParams(data.player)));
         this.spawnEffects(data, holder);
      }
   }

   private void spawnEffects(OnFishingExtraItemsGet data, AccessoryHolder holder) {
      BlockPos position = LevelHelper.getPositionOverFluid(data.getLevel(), data.hook.m_20183_());
      holder.getParticleEmitter()
         .count(4)
         .offset(ParticleEmitter.offset(0.125F))
         .position(AnyPos.from(data.hook.m_20185_(), (double)position.m_123342_() + 0.25, data.hook.m_20189_()).vec3())
         .emit(data.getServerLevel());
   }
}
