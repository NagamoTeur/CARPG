package com.aqutheseal.celestisynth.common.attack.cresentia;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.attack.base.WeaponAttackInstance;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;

public class CrescentiaBarrageAttack extends WeaponAttackInstance {
   public CrescentiaBarrageAttack(Player player, ItemStack stack) {
      super(player, stack);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      return AnimationManager.AnimationsList.ANIM_CRESCENTIA_STRIKE;
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.crescentiaSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 70;
   }

   @Override
   public boolean getCondition() {
      return !this.getPlayer().m_6144_();
   }

   @Override
   public void startUsing() {
      this.useAndDamageItem(this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), 4);
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() == 15) {
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_WHIRLWIND.get(), 0.35F, 0.5F + this.player.f_19853_.f_46441_.m_188501_());
      }

      if (this.getTimerProgress() >= 15 && this.getTimerProgress() <= 60) {
         double range = 7.0;
         double rangeSq = Mth.m_144952_(range);

         for (Entity entityBatch : this.getPlayer()
            .f_19853_
            .m_45976_(
               Entity.class,
               this.getPlayer()
                  .m_20191_()
                  .m_82377_(range, range, range)
                  .m_82386_(this.calculateXLook(this.getPlayer()), 0.0, this.calculateZLook(this.getPlayer()))
            )) {
            if (entityBatch instanceof LivingEntity target
               && target != this.getPlayer()
               && target.m_6084_()
               && !this.getPlayer().m_7307_(target)
               && target.m_20280_(this.getPlayer()) < rangeSq) {
               this.hurtNoKB(
                  this.getPlayer(),
                  target,
                  (float)((Double)CSConfigManager.COMMON.crescentiaSkillDmg.get() + (double)this.getSharpnessValue(this.getStack(), 0.25F))
               );
               target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 2));
            }

            if (entityBatch instanceof Projectile projectile) {
               CrescentiaItem.createCrescentiaFirework(
                  this.getStack(), this.getPlayer().f_19853_, this.getPlayer(), projectile.m_20185_(), projectile.m_20186_(), projectile.m_20189_(), true
               );
               this.getPlayer().m_5496_(SoundEvents.f_11932_, 1.0F, 1.0F);
               projectile.m_142687_(RemovalReason.DISCARDED);
            }
         }

         if (this.getTimerProgress() % 30 == 0) {
            this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_WHIRLWIND.get(), 0.15F, 1.5F);
         }

         if (this.getTimerProgress() % 3 == 0) {
            if (this.getPlayer().f_19853_.f_46441_.m_188499_()) {
               CSEffectEntity.createInstance(
                  this.getPlayer(),
                  null,
                  (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE.get(),
                  this.calculateXLook(this.getPlayer()),
                  -0.3,
                  this.calculateZLook(this.getPlayer())
               );
            } else {
               CSEffectEntity.createInstance(
                  this.getPlayer(),
                  null,
                  (CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE_INVERTED.get(),
                  this.calculateXLook(this.getPlayer()),
                  -0.3,
                  this.calculateZLook(this.getPlayer())
               );
            }

            playRandomBladeSound(this.getPlayer(), BASE_WEAPON_EFFECTS.length);
         }

         CSEffectEntity.createInstance(this.getPlayer(), null, (CSVisualType)CSVisualTypes.SOLARIS_AIR_LARGE.get(), 0.0, -1.0, 0.0);
         float offX = this.getPlayer().f_19853_.f_46441_.m_188501_() * 16.0F - 8.0F;
         float offY = this.getPlayer().f_19853_.f_46441_.m_188501_() * 16.0F - 8.0F;
         float offZ = this.getPlayer().f_19853_.f_46441_.m_188501_() * 16.0F - 8.0F;
         CrescentiaItem.createCrescentiaFirework(
            this.getStack(),
            this.getPlayer().f_19853_,
            this.getPlayer(),
            this.getPlayer().m_20185_() + (double)offX,
            this.getPlayer().m_20186_() + (double)offY,
            this.getPlayer().m_20189_() + (double)offZ,
            false
         );
         if (this.getPlayer().f_19853_.f_46441_.m_188499_()) {
            CrescentiaItem.createCrescentiaFirework(
               this.getStack(),
               this.getPlayer().f_19853_,
               this.getPlayer(),
               this.getPlayer().m_20185_() + (double)offZ,
               this.getPlayer().m_20186_() + (double)offX,
               this.getPlayer().m_20189_() + (double)offY,
               false
            );
         }
      }
   }

   @Override
   public void stopUsing() {
   }
}
