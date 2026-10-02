package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.events.OnPlayerWakedUp;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.time.TimeHelper;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.config.RangedInteger;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class SleepingBonuses extends BonusComponent<AccessoryItem> {
   RangedFloat count = new RangedFloat().id("count").maxRange(Range.of(1.0F, 100.0F));
   RangedInteger duration = new RangedInteger().id("duration").maxRange(Range.of(1, 10000));
   List<SleepingBonuses.EffectDef> effects = List.of(
      new SleepingBonuses.EffectDef(MobEffects.f_19605_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19618_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19617_, 1),
      new SleepingBonuses.EffectDef(MobEffects.f_19606_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19607_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19596_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19598_, 0),
      new SleepingBonuses.EffectDef(MobEffects.f_19600_, 0)
   );

   public static BonusComponent.ISupplier<AccessoryItem> create(float count, int duration) {
      return handler -> new SleepingBonuses(handler, count, duration);
   }

   protected SleepingBonuses(BonusHandler<AccessoryItem> handler, float count, int duration) {
      super(handler);
      this.count.set(count, Range.of(1.0F, 10.0F));
      this.duration.set(duration, Range.of(1, 10000));
      OnPlayerWakedUp.listen(this::applyBonuses).addCondition(Condition.isLogicalServer()).addCondition(data -> !data.wasSleepStoppedManually);
      this.addTooltip(
         "majruszsaccessories.bonuses.sleep_bonuses",
         new ITooltipProvider[]{TooltipHelper.asValue(this.count).scaleOnlyOnDetailed(), TooltipHelper.asValue(this.duration)}
      );
      handler.getConfig().define("sleep_bonuses", subconfig -> {
         this.count.define(subconfig);
         this.duration.define(subconfig);
         subconfig.define("effects", Reader.list(Reader.custom(SleepingBonuses.EffectDef::new)), s -> this.effects, (s, v) -> this.effects = v);
      });
   }

   private void applyBonuses(OnPlayerWakedUp data) {
      AccessoryHolder holder = AccessoryHolders.get(data.player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
      if (holder.isValid() && !holder.isBonusDisabled()) {
         int count = Math.round(holder.apply(this.count));
         int duration = TimeHelper.toTicks((double)holder.apply(this.duration));
         this.getRandomMobEffects(data.player, count).forEach(effect -> data.player.m_7292_(new MobEffectInstance(effect.effect, duration, effect.amplifier)));
         this.spawnEffects(data, holder);
      }
   }

   private List<SleepingBonuses.EffectDef> getRandomMobEffects(Player player, int count) {
      List<SleepingBonuses.EffectDef> missingEffects = this.effects.stream().filter(effect -> !player.m_21023_(effect.effect)).toList();
      if (missingEffects.isEmpty()) {
         missingEffects = this.effects;
      }

      return Random.next(missingEffects, count);
   }

   private void spawnEffects(OnPlayerWakedUp data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(5).position(data.player.m_20182_()).emit(data.getServerLevel());
   }

   private static class EffectDef {
      private MobEffect effect;
      private int amplifier;

      public EffectDef(MobEffect effect, int amplifier) {
         this.effect = effect;
         this.amplifier = amplifier;
      }

      public EffectDef() {
      }

      static {
         Serializables.get(SleepingBonuses.EffectDef.class)
            .define("id", Reader.mobEffect(), s -> s.effect, (s, v) -> s.effect = v)
            .define("amplifier", Reader.integer(), s -> s.amplifier, (s, v) -> s.amplifier = v);
      }
   }
}
