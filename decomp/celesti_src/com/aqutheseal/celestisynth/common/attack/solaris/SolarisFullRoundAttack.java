package com.aqutheseal.celestisynth.common.attack.solaris;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class SolarisFullRoundAttack extends WeaponAttackInstance {
   public static final String DIRECTION_INDEX_KEY = "cs.directionIndex";

   public SolarisFullRoundAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_SOLARIS_SPIN;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.solarisSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 80;
   }

   @Override
   public boolean getCondition() {
      return !this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      this.getTagController().m_128405_("cs.directionIndex", this.getPlayer().m_217043_().m_188503_(2));
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 2);
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128405_("cs.directionIndex", this.getPlayer().m_217043_().m_188503_(2));
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 13) {
         this.getPlayer().m_216990_((SoundEvent)CSSoundEvents.CS_STEP.get());

         for (int i = 0; i < 15; i++) {
            Random rand = new Random();
            if (!this.getPlayer().f_19853_.m_5776_()) {
               ParticleUtil.sendParticles(
                  (ServerLevel)this.getPlayer().f_19853_,
                  ParticleTypes.f_123755_,
                  this.getPlayer().m_20185_(),
                  this.getPlayer().m_20186_(),
                  this.getPlayer().m_20189_(),
                  0,
                  (double)(-1.0F + rand.nextFloat(2.0F)) * 0.5,
                  0.1,
                  (double)(-1.0F + rand.nextFloat(2.0F)) * 0.5
               );
            }
         }
      }

      if (this.getTimerProgress() > 0 && this.getTimerProgress() < 24) {
         if (!this.getPlayer().f_19853_.m_5776_()) {
            ParticleUtil.sendParticles(
               (ServerLevel)this.getPlayer().f_19853_,
               ParticleTypes.f_123744_,
               this.getPlayer().m_20185_(),
               this.getPlayer().m_20186_(),
               this.getPlayer().m_20189_(),
               0,
               0.0,
               0.1,
               0.0
            );
         }

         this.getPlayer().m_20334_(0.0, 0.0, 0.0);
         this.getPlayer().f_19864_ = true;
      } else if (this.getTimerProgress() > 23 && this.getTimerProgress() < 60) {
         BlockPos blockPosForAttack = this.getPlayer().m_20183_();
         int range = 4;

         for (LivingEntity target : this.getPlayer()
            .f_19853_
            .m_45976_(LivingEntity.class, new AABB(blockPosForAttack.m_7918_(-range, -range, -range), blockPosForAttack.m_7918_(range, range, range)))) {
            if (target != this.getPlayer() && !this.getPlayer().m_7307_(target) && target.m_6084_()) {
               this.hurtNoKB(
                  this.getPlayer(),
                  target,
                  (float)((Double)CSConfigManager.COMMON.solarisSkillDmg.get() + (double)this.getSharpnessValue(this.getStack(), 0.5F))
               );
               target.m_20254_(5);
            }
         }

         this.getPlayer().m_216990_(SoundEvents.f_12455_);
         if (this.getTagController().m_128451_("cs.directionIndex") == 0) {
            this.movePlayerInCircularMotion(this.getPlayer(), this.getTimerProgress(), false);
            CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_BLITZ.get());
            CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_AIR.get());
            playRandomBladeSound(this.getPlayer(), 4);
         } else if (this.getTagController().m_128451_("cs.directionIndex") == 1) {
            this.movePlayerInCircularMotion(this.getPlayer(), this.getTimerProgress(), true);
            CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_BLITZ.get());
            CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_AIR.get());
            playRandomBladeSound(this.getPlayer(), 4);
         }

         BlockPos playerPos = this.getPlayer().m_20183_();
         double radius = 3.0;
         double particleCount = 50.0;
         double angleIncrement = (Math.PI * 2) / particleCount;

         for (int ix = 0; (double)ix < particleCount; ix++) {
            double angle = (double)ix * angleIncrement;
            double rotationX = this.getPlayer().f_19853_.f_46441_.m_188500_() * 360.0;
            double rotationZ = this.getPlayer().f_19853_.f_46441_.m_188500_() * 360.0;
            double x = (double)playerPos.m_123341_() + radius * Math.cos(angle);
            double y = (double)playerPos.m_123342_() + 1.5;
            double z = (double)playerPos.m_123343_() + radius * Math.sin(angle);
            double motionX = Math.sin(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
            double motionY = Math.sin(Math.toRadians(rotationZ));
            double motionZ = Math.cos(Math.toRadians(rotationX)) * Math.cos(Math.toRadians(rotationZ));
            if (!this.getPlayer().f_19853_.m_5776_()) {
               ParticleUtil.sendParticles((ServerLevel)this.getPlayer().f_19853_, ParticleTypes.f_123744_, x + 0.5, y, z + 0.5, 0, motionX, motionY, motionZ);
            }
         }
      }
   }

   private void movePlayerInCircularMotion(Player player, int tick, boolean isRight) {
      double radius = 1.5;
      double forwardX = Math.sin(Math.toRadians((double)player.m_146908_()));
      double forwardZ = -Math.cos(Math.toRadians((double)player.m_146908_()));
      double perpendicularX = -forwardZ;
      double angle = (double)(tick - 45) / 25.0 * Math.PI * 2.0;
      double offsetX = radius * Math.cos(angle);
      double offsetZ = radius * Math.sin(angle);
      double finalX = isRight
         ? this.getPlayer().m_20185_() + forwardX * offsetX - perpendicularX * offsetZ
         : this.getPlayer().m_20185_() + forwardX * offsetX + perpendicularX * offsetZ;
      double finalZ = isRight
         ? this.getPlayer().m_20189_() + forwardZ * offsetX - forwardX * offsetZ
         : this.getPlayer().m_20189_() + forwardZ * offsetX + forwardX * offsetZ;
      this.getPlayer().m_20334_(finalX - this.getPlayer().m_20185_(), this.getPlayer().m_20184_().f_82480_, finalZ - this.getPlayer().m_20189_());
   }
}
