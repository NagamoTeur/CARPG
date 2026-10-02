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

public class SolarisSoulDashAttack extends WeaponAttackInstance {
   public static final String STARTED = "cs.hasStartedSoulDash";
   public static final String HEAD_ROT_LOCK_KEY = "cs.headRotLock";

   public SolarisSoulDashAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_SOLARIS_SPIN;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.solarisShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 80;
   }

   @Override
   public boolean getCondition() {
      return this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      this.getTagController().m_128379_("cs.hasStartedSoulDash", true);
      this.getTagController().m_128350_("cs.headRotLock", this.getPlayer().m_146908_());
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 3);
   }

   @Override
   public void stopUsing() {
      this.getTagController().m_128379_("cs.hasStartedSoulDash", false);
      this.getTagController().m_128350_("cs.headRotLock", 0.0F);
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
            for (int ix = 0; ix < 10; ix++) {
               ParticleUtil.sendParticles(
                  (ServerLevel)this.getPlayer().f_19853_,
                  ParticleTypes.f_123745_,
                  this.getPlayer().m_20185_(),
                  this.getPlayer().m_20186_(),
                  this.getPlayer().m_20189_(),
                  0,
                  (double)(-1.0F + new Random().nextFloat(2.0F)),
                  0.1,
                  (double)(-1.0F + new Random().nextFloat(2.0F))
               );
            }
         }

         this.getPlayer().m_20334_(0.0, 0.0, 0.0);
         this.getPlayer().f_19864_ = true;
      } else if (this.getTimerProgress() > 23 && this.getTimerProgress() < 60) {
         BlockPos blockPosForAttack = this.getPlayer().m_20183_();
         int range = 7;

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
         this.movePlayerInStraightMotion(this.getPlayer(), (float)this.getTagController().m_128451_("cs.headRotLock"));
         CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_BLITZ_SOUL.get());
         CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE.get());
         playRandomBladeSound(this.getPlayer(), BASE_WEAPON_EFFECTS.length);
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
               ParticleUtil.sendParticles((ServerLevel)this.getPlayer().f_19853_, ParticleTypes.f_123745_, x + 0.5, y, z + 0.5, 0, motionX, motionY, motionZ);
               ParticleUtil.sendParticles((ServerLevel)this.getPlayer().f_19853_, ParticleTypes.f_123746_, x + 0.5, y, z + 0.5, 0, motionX, motionY, motionZ);
            }
         }
      }
   }

   private void movePlayerInStraightMotion(Player player, float yRot) {
      double speed = 1.5;
      double lookX = -Math.sin(Math.toRadians((double)yRot));
      double lookZ = Math.cos(Math.toRadians((double)yRot));
      double motionX = lookX * speed;
      double motionZ = lookZ * speed;
      this.getPlayer().m_20334_(motionX, this.getPlayer().m_20184_().f_82480_, motionZ);
   }
}
