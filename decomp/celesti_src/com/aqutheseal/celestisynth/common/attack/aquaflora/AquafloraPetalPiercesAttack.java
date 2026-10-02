package com.aqutheseal.celestisynth.common.attack.aquaflora;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AquafloraPetalPiercesAttack extends AquafloraAttack {
   public AquafloraPetalPiercesAttack(Player player, ItemStack stack, int heldDuration) {
      super(player, stack, heldDuration);
   }

   @Override
   public AnimationManager.AnimationsList getAnimation() {
      if (this.player.m_21205_() == this.stack && this.getPlayer().m_21206_() == this.stack) {
         return this.getPlayer().f_19853_.f_46441_.m_188499_()
            ? AnimationManager.AnimationsList.ANIM_AQUAFLORA_PIERCE_LEFT
            : AnimationManager.AnimationsList.ANIM_AQUAFLORA_PIERCE_RIGHT;
      } else {
         return this.getPlayer().m_21205_() == this.stack
            ? AnimationManager.AnimationsList.ANIM_AQUAFLORA_PIERCE_RIGHT
            : AnimationManager.AnimationsList.ANIM_AQUAFLORA_PIERCE_LEFT;
      }
   }

   @Override
   public int getCooldown() {
      return (Integer)CSConfigManager.COMMON.aquafloraSkillCD.get();
   }

   @Override
   public int getAttackStopTime() {
      return 20;
   }

   @Override
   public boolean getCondition() {
      return !this.getTagController().m_128471_("cs.checkPassiveIfBlooming") && !this.player.m_6047_();
   }

   @Override
   public void startUsing() {
      CSEffectEntity.createInstance(
         this.player,
         null,
         (CSVisualType)CSVisualTypes.AQUAFLORA_PIERCE_START.get(),
         this.calculateXLook(this.player) * 3.0,
         1.2 + this.calculateYLook(this.player) * 3.0,
         this.calculateZLook(this.player) * 3.0
      );
      this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_BLING.get(), 0.15F, 0.5F);
      if (this.getPlayer().f_19853_.m_5776_()) {
         this.shakeScreens(this.player, 15, 5, 0.02F);
      }
   }

   @Override
   public void tickAttack() {
      if (this.getTimerProgress() >= 0 && this.getTimerProgress() <= 15) {
         this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_AIR_SWING.get(), 0.25F, 1.3F + this.getPlayer().f_19853_.f_46441_.m_188501_());
         CSEffectEntity.createInstance(
            this.player,
            null,
            (CSVisualType)CSVisualTypes.AQUAFLORA_STAB.get(),
            -0.5 + this.getPlayer().f_19853_.f_46441_.m_188500_() + this.calculateXLook(this.player) * 3.0,
            -0.5 + this.getPlayer().f_19853_.f_46441_.m_188500_() + 2.0 + this.calculateYLook(this.player) * 3.0,
            -0.5 + this.getPlayer().f_19853_.f_46441_.m_188500_() + this.calculateZLook(this.player) * 3.0
         );
         List<Entity> entities = this.iterateEntities(
            this.getPlayer().f_19853_,
            this.createAABB(
               this.player
                  .m_20183_()
                  .m_7637_(this.calculateXLook(this.player) * 4.5, 1.0 + this.calculateYLook(this.player) * 4.5, this.calculateZLook(this.player) * 4.5),
               2.0
            )
         );
         entities.addAll(
            this.iterateEntities(
               this.getPlayer().f_19853_,
               this.createAABB(
                  this.player
                     .m_20183_()
                     .m_7637_(this.calculateXLook(this.player) * 3.0, 1.0 + this.calculateYLook(this.player) * 3.0, this.calculateZLook(this.player) * 3.0),
                  2.0
               )
            )
         );
         entities.addAll(
            this.iterateEntities(
               this.getPlayer().f_19853_,
               this.createAABB(
                  this.player
                     .m_20183_()
                     .m_7637_(this.calculateXLook(this.player) * 1.5, 1.0 + this.calculateYLook(this.player) * 1.5, this.calculateZLook(this.player) * 1.5),
                  2.0
               )
            )
         );
         if (entities.size() > 0) {
            this.getPlayer().m_5496_((SoundEvent)CSSoundEvents.CS_BLING.get(), 0.15F, 1.0F + this.getPlayer().f_19853_.f_46441_.m_188501_());
         }

         for (Entity entityBatch : entities) {
            if (entityBatch instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)entityBatch;
               if (target != this.player && target.m_6084_() && !this.player.m_7307_(target)) {
                  this.hurtNoKB(
                     this.player,
                     target,
                     (float)((Double)CSConfigManager.COMMON.aquafloraSkillDmg.get()).doubleValue() + this.getSharpnessValue(this.getStack(), 0.15F)
                  );
                  this.createHitEffect(this.getStack(), this.getPlayer().f_19853_, this.player, target);
               }
            }
         }
      }
   }

   @Override
   public void stopUsing() {
   }
}
