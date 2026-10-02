package com.github.L_Ender.cataclysm.effects;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent.ChorusFruit;

public class EffectAbyssal_Burn extends MobEffect {
   public EffectAbyssal_Burn() {
      super(MobEffectCategory.HARMFUL, 6619391);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
      boolean flag = LivingEntityIn.m_6469_(CMDamageTypes.ABYSSAL_BURN, 1.0F);
      if (flag && LivingEntityIn.m_217043_().m_188501_() < 0.75F - LivingEntityIn.m_21223_() / LivingEntityIn.m_21233_() && !LivingEntityIn.f_19853_.f_46443_) {
         double d0 = LivingEntityIn.m_20185_();
         double d1 = LivingEntityIn.m_20186_();
         double d2 = LivingEntityIn.m_20189_();

         for (int i = 0; i < 8; i++) {
            double d3 = LivingEntityIn.m_20185_() + (LivingEntityIn.m_217043_().m_188500_() - 0.5) * 8.0;
            double d4 = Mth.m_14008_(
               LivingEntityIn.m_20186_() + (double)(LivingEntityIn.m_217043_().m_188503_(8) - 4),
               (double)LivingEntityIn.f_19853_.m_141937_(),
               (double)(LivingEntityIn.f_19853_.m_141937_() + ((ServerLevel)LivingEntityIn.f_19853_).m_143344_() - 1)
            );
            double d5 = LivingEntityIn.m_20189_() + (LivingEntityIn.m_217043_().m_188500_() - 0.5) * 8.0;
            if (LivingEntityIn.m_20159_()) {
               LivingEntityIn.m_8127_();
            }

            Vec3 vec3 = LivingEntityIn.m_20182_();
            LivingEntityIn.f_19853_.m_214171_(GameEvent.f_238175_, vec3, Context.m_223717_(LivingEntityIn));
            ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(LivingEntityIn, d3, d4, d5);
            if (this.randomTeleportInwater(LivingEntityIn, event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
               SoundEvent soundevent = LivingEntityIn instanceof Fox ? SoundEvents.f_11953_ : SoundEvents.f_11757_;
               LivingEntityIn.f_19853_.m_6263_((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
               LivingEntityIn.m_5496_(soundevent, 1.0F, 1.0F);
               break;
            }
         }
      }
   }

   private boolean randomTeleportInwater(LivingEntity LivingEntityIn, double p_20985_, double p_20986_, double p_20987_, boolean p_20988_) {
      double d0 = LivingEntityIn.m_20185_();
      double d1 = LivingEntityIn.m_20186_();
      double d2 = LivingEntityIn.m_20189_();
      double d3 = p_20986_;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(p_20985_, p_20986_, p_20987_);
      Level level = LivingEntityIn.f_19853_;
      if (level.m_46805_(blockpos)) {
         boolean flag1 = false;

         while (!flag1 && blockpos.m_123342_() > level.m_141937_()) {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = level.m_8055_(blockpos1);
            if (blockstate.m_60767_().m_76334_()) {
               flag1 = true;
            } else {
               d3--;
               blockpos = blockpos1;
            }
         }

         if (flag1) {
            LivingEntityIn.m_6021_(p_20985_, d3, p_20987_);
            if (level.m_45786_(LivingEntityIn)) {
               flag = true;
            }
         }
      }

      if (!flag) {
         LivingEntityIn.m_6021_(d0, d1, d2);
         return false;
      } else {
         if (p_20988_) {
            level.m_7605_(LivingEntityIn, (byte)46);
         }

         if (LivingEntityIn instanceof PathfinderMob) {
            ((PathfinderMob)LivingEntityIn).m_21573_().m_26573_();
         }

         return true;
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }
}
