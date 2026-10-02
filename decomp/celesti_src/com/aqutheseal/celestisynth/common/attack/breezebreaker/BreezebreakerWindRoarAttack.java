package com.aqutheseal.celestisynth.common.attack.breezebreaker;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.Vec3;

public class BreezebreakerWindRoarAttack extends BreezebreakerAttack {
   public BreezebreakerWindRoarAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_SPRINT_ATTACK;
   }

   @Override
   public int getCooldown() {
      return this.buffStateModified((Integer)CSConfigManager.COMMON.breezebreakerSprintSkillCD.get());
   }

   @Override
   public int getAttackStopTime() {
      return 20;
   }

   @Override
   public boolean getCondition() {
      return this.getPlayer().m_20142_();
   }

   @Override
   public void startUsing() {
      super.startUsing();
      this.useAndDamageItem(this.stack, this.getPlayer().f_19853_, this.player, 5);
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 10) {
         this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123777_, this.getPlayer().m_20183_(), 45, 0.2F);
         LivingEntity observedLivingTarget = this.getLookedAtEntity(this.player, 16.0) instanceof LivingEntity entity ? entity : null;
         if (observedLivingTarget != null) {
            double attackDamage = (Double)CSConfigManager.COMMON.breezebreakerSprintSkillDmg.get() + (double)this.getSharpnessValue(this.stack, 1.0F);
            this.hurtNoKB(this.player, observedLivingTarget, (float)attackDamage);
            this.getPlayer()
               .f_19853_
               .m_46511_(
                  this.player, observedLivingTarget.m_20185_(), observedLivingTarget.m_20186_(), observedLivingTarget.m_20189_(), 1.0F, BlockInteraction.NONE
               );
            observedLivingTarget.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 60, 2));
            observedLivingTarget.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 60, 2));
            this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123815_, this.getPlayer().m_20183_().m_7494_(), 45, 0.2F);
         }

         double speed = 7.0;

         for (float distii = 0.0F; (double)distii < speed; distii += 0.25F) {
            BlockPos newPos = new BlockPos(
               this.player.m_20185_() + this.calculateXLook(this.player) * (double)distii,
               this.getPlayer().m_20186_(),
               this.getPlayer().m_20189_() + this.calculateZLook(this.player) * (double)distii
            );
            if (!this.player.f_19853_.m_46859_(newPos)) {
               speed = (double)distii;
               break;
            }
         }

         Vec3 delta = new Vec3(this.calculateXLook(this.player) * speed, 0.0, this.calculateZLook(this.player) * speed);
         this.getPlayer()
            .m_6027_(
               this.player.m_20185_() + this.calculateXLook(this.player) * speed,
               this.getPlayer().m_20186_(),
               this.getPlayer().m_20189_() + this.calculateZLook(this.player) * speed
            );
         double[] multipliers = new double[]{2.0, 1.5, 1.0, 0.5, 0.0};
         CSVisualType[] effectTypes = new CSVisualType[]{
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_DASH.get(),
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_DASH_2.get(),
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_DASH_3.get(),
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_DASH_3.get(),
            (CSVisualType)CSVisualTypes.BREEZEBREAKER_DASH_3.get()
         };

         for (int i = 0; i < multipliers.length; i++) {
            int yOffset = i > 1 ? 1 : 0;
            CSEffectEntity.createInstance(
               this.player, null, effectTypes[i], delta.m_7096_() * multipliers[i], (double)yOffset, delta.m_7094_() * multipliers[i]
            );
         }

         this.getPlayer().m_5496_(SoundEvents.f_11913_, 1.0F, 1.5F);
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_IMPACT_HIT.get(), 1.0F, 1.0F);
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_STEP.get(), 1.0F, 1.0F);
      }
   }

   @Override
   public void stopUsing() {
   }
}
