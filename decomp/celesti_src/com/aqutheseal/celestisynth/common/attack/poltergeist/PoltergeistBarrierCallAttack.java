package com.aqutheseal.celestisynth.common.attack.poltergeist;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

public class PoltergeistBarrierCallAttack extends WeaponAttackInstance {
   public PoltergeistBarrierCallAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_POLTERGEIST_RETREAT;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.poltergeistShiftSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 1;
   }

   @Override
   public boolean getCondition() {
      return this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      double range = 4.0;

      for (Entity entityBatch : this.iterateEntities(
         this.player.f_19853_,
         this.createAABB(this.player.m_20183_().m_7494_().m_7637_(this.calculateXLook(this.player) * 2.0, 0.0, this.calculateZLook(this.player) * 2.0), range)
      )) {
         if (entityBatch instanceof LivingEntity target && target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
            this.hurtNoKB(
               this.player,
               target,
               (float)((Double)CSConfigManager.COMMON.poltergeistShiftSkillDmg.get()).doubleValue() + this.getSharpnessValue(this.getStack(), 1.2F)
            );
            target.m_5496_((SoundEvent)CSSoundEvents.CS_SWORD_CLASH.get(), 0.25F, 0.5F);
            if (target instanceof LivingMixinSupport lms) {
               lms.setPhantomTagger(this.player);
            }
            continue;
         }

         if (entityBatch instanceof Projectile) {
            entityBatch.m_142687_(RemovalReason.DISCARDED);
         }
      }

      CSEffectEntity.createInstance(
         this.player,
         null,
         (CSVisualType)CSVisualTypes.POLTERGEIST_RETREAT.get(),
         this.calculateXLook(this.player) * 2.0,
         1.0,
         this.calculateZLook(this.player) * 2.0
      );
      this.sendExpandingParticles(this.player.f_19853_, ParticleTypes.f_123746_, this.getPlayer().m_20183_(), 45, 0.5F);
      double deltaY = this.player.m_20096_() ? 3.0 : 0.9;
      this.getPlayer().m_20334_(this.calculateXLook(this.player) * -0.5, deltaY, this.calculateZLook(this.player) * -0.5);
      this.getPlayer().f_19864_ = true;
      this.getPlayer().m_5496_(SoundEvents.f_11889_, 1.0F, 1.5F);
      this.getPlayer().m_5496_(SoundEvents.f_11705_, 1.0F, 1.5F);
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 2);
   }

   @Override
   public void tickAttack() {
   }

   @Override
   public void stopUsing() {
   }
}
