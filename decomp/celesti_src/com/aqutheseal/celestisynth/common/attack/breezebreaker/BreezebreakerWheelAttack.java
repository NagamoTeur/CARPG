package com.aqutheseal.celestisynth.common.attack.breezebreaker;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BreezebreakerWheelAttack extends BreezebreakerAttack {
   public BreezebreakerWheelAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_JUMP_ATTACK;
   }

   @Override
   public int getCooldown() {
      return this.buffStateModified((Integer)CSConfigManager.COMMON.breezebreakerMidairSkillCD.get());
   }

   @Override
   public int getAttackStopTime() {
      return 10;
   }

   @Override
   public boolean getCondition() {
      return !this.player.m_20096_();
   }

   @Override
   public void startUsing() {
      super.startUsing();
      this.useAndDamageItem(this.stack, this.getPlayer().f_19853_, this.player, 4);
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 10) {
         double range = 7.5;

         for (Entity entityBatch : this.getPlayer()
            .f_19853_
            .m_45976_(Entity.class, this.getPlayer().m_20191_().m_82377_(range, range, 3.0).m_82386_(0.0, 1.0, 0.0))) {
            if (entityBatch instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)entityBatch;
               if (target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
                  this.hurtNoKB(
                     this.player,
                     target,
                     (float)((Double)CSConfigManager.COMMON.breezebreakerSprintSkillDmg.get()).doubleValue() + this.getSharpnessValue(this.stack, 1.5F)
                  );
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 40, 1));
                  this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123759_, target.m_20183_().m_7494_(), 45, 0.0F);
               }
            }
         }

         CSEffectEntity.createInstance(this.player, null, (CSVisualType)CSVisualTypes.BREEZEBREAKER_WHEEL.get(), 0.0, -1.0, 0.0);
         CSEffectEntity.createInstance(
            this.player,
            null,
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_WHEEL_IMPACT.get(),
            this.calculateXLook(this.player) * 3.0,
            1.5 + this.calculateYLook(this.player) * 3.0,
            this.calculateZLook(this.player) * 3.0
         );
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_FIRE_SHOOT.get(), 1.0F, 1.0F);
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_AIR_SWING.get(), 1.0F, 1.0F);
         this.getPlayer().m_216990_((SoundEvent)CSSoundEvents.CS_WIND_STRIKE.get());
         this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123810_, this.getPlayer().m_20183_().m_7494_(), 75, 0.0F);
      }
   }

   @Override
   public void stopUsing() {
   }
}
