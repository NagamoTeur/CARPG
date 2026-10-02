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

public class BreezebreakerGalestormAttack extends BreezebreakerAttack {
   public BreezebreakerGalestormAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_NORMAL_SINGLE;
   }

   @Override
   public int getCooldown() {
      return this.buffStateModified((Integer)CSConfigManager.COMMON.breezebreakerSkillCD.get());
   }

   @Override
   public int getAttackStopTime() {
      return 15;
   }

   @Override
   public boolean getCondition() {
      return !this.player.m_20142_() && !this.player.m_6047_() && this.getPlayer().m_20096_() && this.heldDuration < 6;
   }

   @Override
   public void startUsing() {
      super.startUsing();
      this.useAndDamageItem(this.stack, this.getPlayer().f_19853_, this.player, 1);
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 6) {
         double range = 6.0;

         for (Entity entityBatch : this.iterateEntities(
            this.player.f_19853_,
            this.createAABB(this.player.m_20183_().m_7637_(this.calculateXLook(this.player) * 3.0, 1.0, this.calculateZLook(this.player) * 3.0), range)
         )) {
            if (entityBatch instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)entityBatch;
               if (target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
                  this.hurtNoKB(
                     this.player,
                     target,
                     (float)((Double)CSConfigManager.COMMON.breezebreakerSkillDmg.get() + (double)this.getSharpnessValue(this.stack, 1.0F))
                  );
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 40, 1));
                  this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123759_, target.m_20183_().m_7494_(), 15, 0.0F);
               }
            }
         }

         this.getPlayer().m_216990_((SoundEvent)CSSoundEvents.CS_WIND_STRIKE.get());
         CSEffectEntity.createInstance(
            this.player, null, (CSVisualType)CSVisualTypes.BREEZEBREAKER_SLASH.get(), this.calculateXLook(this.player), 0.0, this.calculateZLook(this.player)
         );
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_AIR_SWING.get(), 1.0F, 1.0F);
      }
   }

   @Override
   public void stopUsing() {
   }
}
