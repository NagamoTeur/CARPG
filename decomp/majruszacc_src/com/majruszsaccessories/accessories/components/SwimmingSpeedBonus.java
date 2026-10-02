package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.events.OnEntitySwimSpeedMultiplierGet;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.time.TimeHelper;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerLevel;

public class SwimmingSpeedBonus extends BonusComponent<AccessoryItem> {
   RangedFloat multiplier = new RangedFloat().id("multiplier").maxRange(Range.of(0.0F, 10.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float bonus) {
      return handler -> new SwimmingSpeedBonus(handler, bonus);
   }

   protected SwimmingSpeedBonus(BonusHandler<AccessoryItem> handler, float bonus) {
      super(handler);
      this.multiplier.set(bonus, Range.of(0.0F, 10.0F));
      OnEntitySwimSpeedMultiplierGet.listen(this::increaseSwimSpeed);
      this.addTooltip("majruszsaccessories.bonuses.swim_bonus", new ITooltipProvider[]{TooltipHelper.asPercent(this.multiplier)});
      handler.getConfig().define("swim_speed", this.multiplier::define);
   }

   private void increaseSwimSpeed(OnEntitySwimSpeedMultiplierGet data) {
      AccessoryHolder holder = AccessoryHolders.get(data.entity).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         data.multiplier = data.multiplier * (1.0F + holder.apply(this.multiplier));
         if (data.entity.m_20069_() && data.getLevel() instanceof ServerLevel && TimeHelper.haveTicksPassed(5)) {
            this.spawnEffects(data, holder);
         }
      }
   }

   private void spawnEffects(OnEntitySwimSpeedMultiplierGet data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(1).sizeBased(data.entity).emit(data.getServerLevel());
   }
}
