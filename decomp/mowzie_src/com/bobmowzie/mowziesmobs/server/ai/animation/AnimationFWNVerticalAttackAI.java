package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AnimationFWNVerticalAttackAI extends AnimationAttackAI<EntityWroughtnaut> {
   private final float arc;

   public AnimationFWNVerticalAttackAI(EntityWroughtnaut entity, Animation animation, SoundEvent sound, float applyKnockback, float range, float arc) {
      super(entity, animation, sound, null, applyKnockback, range, 0.0F, 0);
      this.arc = arc;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_2.get(), 1.5F, 1.0F);
   }

   @Override
   public void m_8037_() {
      this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
      if (this.entity.getAnimationTick() < 21 && this.entityTarget != null) {
         this.entity.m_21391_(this.entityTarget, 30.0F, 30.0F);
      } else {
         this.entity.m_146922_(this.entity.f_19859_);
      }

      if (this.entity.getAnimationTick() == 6) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
      } else if (this.entity.getAnimationTick() == 25) {
         this.entity.m_5496_(this.attackSound, 1.2F, 1.0F);
      } else if (this.entity.getAnimationTick() == 27) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_2.get(), 1.5F, 1.0F);
         List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby((double)this.range, 3.0, (double)this.range, (double)this.range);
         float damage = (float)this.entity.m_21051_(Attributes.f_22281_).m_22135_();

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
            );
            if (entityHitDistance <= this.range && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
               || entityRelativeAngle >= 360.0F - this.arc / 2.0F
               || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
               entityHit.m_6469_(DamageSource.m_19370_(this.entity), damage * 1.5F);
               if (entityHit.m_21254_()) {
                  entityHit.m_21211_().m_41622_(400, entityHit, player -> player.m_21190_(entityHit.m_7655_()));
               }

               entityHit.m_20334_(
                  entityHit.m_20184_().f_82479_ * (double)this.applyKnockbackMultiplier,
                  entityHit.m_20184_().f_82480_,
                  entityHit.m_20184_().f_82481_ * (double)this.applyKnockbackMultiplier
               );
            }
         }
      } else if (this.entity.getAnimationTick() == 28) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_LAND.get(), 1.0F, 0.5F);
         EntityCameraShake.cameraShake(this.entity.f_19853_, this.entity.m_20182_(), 20.0F, 0.3F, 0, 10);
      } else if (this.entity.getAnimationTick() == 44) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_PULL_1.get(), 1.0F, 1.0F);
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
      } else if (this.entity.getAnimationTick() == 75) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_PULL_5.get(), 1.0F, 1.0F);
      } else if (this.entity.getAnimationTick() == 83) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_RELEASE_2.get(), 1.0F, 1.0F);
      }

      if (this.entity.getAnimationTick() > 26 && this.entity.getAnimationTick() < 85) {
         this.entity.vulnerable = true;
         this.entity.m_146922_(this.entity.f_19859_);
         this.entity.f_20883_ = this.entity.f_20884_;
      } else {
         this.entity.vulnerable = false;
      }
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.entity.vulnerable = false;
   }
}
