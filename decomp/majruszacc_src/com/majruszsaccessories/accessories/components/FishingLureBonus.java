package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.events.OnFishingTimeGet;
import com.majruszlibrary.level.LevelHelper;
import com.majruszlibrary.math.AnyPos;
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

public class FishingLureBonus extends BonusComponent<AccessoryItem> {
   RangedFloat multiplier = new RangedFloat().id("multiplier").maxRange(Range.of(0.0F, 1.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float bonus) {
      return handler -> new FishingLureBonus(handler, bonus);
   }

   protected FishingLureBonus(BonusHandler<AccessoryItem> handler, float bonus) {
      super(handler);
      this.multiplier.set(bonus, Range.of(0.0F, 1.0F));
      OnFishingTimeGet.listen(this::decreaseFishingTime);
      this.addTooltip("majruszsaccessories.bonuses.fishing_lure", new ITooltipProvider[]{TooltipHelper.asPercent(this.multiplier)});
      handler.getConfig().define("fishing_time", this.multiplier::define);
   }

   private void decreaseFishingTime(OnFishingTimeGet data) {
      AccessoryHolder holder = AccessoryHolders.get(data.player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         data.time = Math.round((float)data.time * (1.0F - holder.apply(this.multiplier)));
         this.spawnEffects(data, holder);
      }
   }

   private void spawnEffects(OnFishingTimeGet data, AccessoryHolder holder) {
      BlockPos position = LevelHelper.getPositionOverFluid(data.getLevel(), data.hook.m_20183_());
      holder.getParticleEmitter()
         .count(4)
         .offset(ParticleEmitter.offset(0.125F))
         .position(AnyPos.from(data.hook.m_20185_(), (double)position.m_123342_() + 0.25, data.hook.m_20189_()).vec3())
         .emit(data.getServerLevel());
   }
}
