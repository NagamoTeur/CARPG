package com.aizistral.enigmaticlegacy.effects;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MoltenHeartEffect extends MobEffect {
   private static final List<String> IMMUNITIES = ImmutableList.of(
      DamageSource.f_19308_.f_19326_, DamageSource.f_19305_.f_19326_, DamageSource.f_19307_.f_19326_, DamageSource.f_19309_.f_19326_
   );
   public static Omniconfig.DoubleParameter lavafogDensity;

   @SubscribeConfig(
      receiveClient = true
   )
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("MoltenHeart");
      if (builder.config.getSidedType() == Configuration.SidedConfigType.CLIENT) {
         lavafogDensity = builder.comment(
               "Controls how obscured your vision is in lava when Molten Heart effect is active. Higher value equals more visibility."
            )
            .max(1024.0)
            .clientOnly()
            .getDouble("LavaDensity", 6.0);
      }

      builder.popPrefix();
   }

   public MoltenHeartEffect() {
      super(MobEffectCategory.BENEFICIAL, 15896076);
   }

   public void m_6742_(LivingEntity living, int amplifier) {
      if (living.m_6060_()) {
         living.m_20095_();
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public boolean providesImmunity(DamageSource to) {
      return IMMUNITIES.contains(to.f_19326_);
   }
}
