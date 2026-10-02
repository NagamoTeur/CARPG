package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AnimationAreaAttackAI<T extends MowzieEntity & IAnimatedEntity> extends AnimationAttackAI<T> {
   private final float arc;
   private final float height;
   private final boolean faceTarget;

   public AnimationAreaAttackAI(
      T entity,
      Animation animation,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float applyKnockback,
      float range,
      float height,
      float arc,
      float damageMultiplier,
      int damageFrame
   ) {
      this(entity, animation, attackSound, hitSound, applyKnockback, range, height, arc, damageMultiplier, damageFrame, true);
   }

   public AnimationAreaAttackAI(
      T entity,
      Animation animation,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float applyKnockback,
      float range,
      float height,
      float arc,
      float damageMultiplier,
      int damageFrame,
      boolean faceTarget
   ) {
      super(entity, animation, attackSound, hitSound, applyKnockback, range, damageMultiplier, damageFrame);
      this.arc = arc;
      this.height = height;
      this.faceTarget = faceTarget;
      if (faceTarget) {
         this.m_7021_(EnumSet.of(Flag.LOOK));
      }
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
   }

   @Override
   public void m_8037_() {
      if (this.faceTarget && this.entity.getAnimationTick() < this.damageFrame && this.entityTarget != null) {
         this.entity.m_21391_(this.entityTarget, 30.0F, 30.0F);
      } else if (this.entity.getAnimationTick() == this.damageFrame) {
         this.hitEntities();
      }
   }

   public void hitEntities() {
      List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby((double)this.range, (double)this.height, (double)this.range, (double)this.range);
      boolean hit = false;

      for (LivingEntity entityHit : entitiesHit) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.entity.m_20189_(), entityHit.m_20185_() - this.entity.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.entity.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
               (entityHit.m_20189_() - this.entity.m_20189_()) * (entityHit.m_20189_() - this.entity.m_20189_())
                  + (entityHit.m_20185_() - this.entity.m_20185_()) * (entityHit.m_20185_() - this.entity.m_20185_())
            )
            - entityHit.m_20205_() / 2.0F;
         if (entityHitDistance <= this.range && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
            || entityRelativeAngle >= 360.0F - this.arc / 2.0F
            || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
            this.entity.doHurtTarget(entityHit, this.damageMultiplier, this.applyKnockbackMultiplier);
            this.onAttack(entityHit, this.damageMultiplier, this.applyKnockbackMultiplier);
            hit = true;
         }
      }

      if (hit && this.hitSound != null) {
         this.entity.m_5496_(this.hitSound, 1.0F, 1.0F);
      }

      if (this.attackSound != null) {
         this.entity.m_5496_(this.attackSound, 1.0F, 1.0F);
      }
   }
}
