package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.List;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class AnimationFWNAttackAI extends AnimationAI<EntityWroughtnaut> {
   protected float applyKnockback = 1.0F;
   protected float range;
   private final float arc;

   public AnimationFWNAttackAI(EntityWroughtnaut entity, float applyKnockback, float range, float arc) {
      super(entity);
      this.applyKnockback = applyKnockback;
      this.range = range;
      this.arc = arc;
   }

   @Override
   protected boolean test(Animation animation) {
      return animation == EntityWroughtnaut.ATTACK_ANIMATION
         || animation == EntityWroughtnaut.ATTACK_TWICE_ANIMATION
         || animation == EntityWroughtnaut.ATTACK_THRICE_ANIMATION;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) {
         this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_1.get(), 1.5F, 1.0F);
      }
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
   }

   private boolean shouldFollowUp(float bonusRange) {
      LivingEntity entityTarget = this.entity.m_5448_();
      if (entityTarget != null && entityTarget.m_6084_()) {
         Vec3 targetMoveVec = entityTarget.m_20184_();
         Vec3 betweenEntitiesVec = this.entity.m_20182_().m_82546_(entityTarget.m_20182_());
         boolean targetComingCloser = targetMoveVec.m_82526_(betweenEntitiesVec) > 0.0;
         return this.entity.targetDistance < this.range + bonusRange || this.entity.targetDistance < this.range + 5.0F + bonusRange && targetComingCloser;
      } else {
         return false;
      }
   }

   public void m_8037_() {
      LivingEntity entityTarget = this.entity.m_5448_();
      this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
      if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_ANIMATION) {
         if (this.entity.getAnimationTick() < 23 && entityTarget != null) {
            this.entity.m_21391_(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 6) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_CREAK.get(), 0.5F, 1.0F);
         } else if (this.entity.getAnimationTick() == 25) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1.0F);
         } else if (this.entity.getAnimationTick() == 27) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_1.get(), 1.5F, 1.0F);
            List<LivingEntity> entitiesHit = this.entity.getEntityLivingBaseNearby((double)this.range, 3.0, (double)this.range, (double)this.range);
            float damage = (float)this.entity.m_21051_(Attributes.f_22281_).m_22135_();
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
                  entityHit.m_6469_(DamageSource.m_19370_(this.entity), damage);
                  if (entityHit.m_21254_()) {
                     entityHit.m_21211_().m_41622_(400, entityHit, player -> player.m_21190_(entityHit.m_7655_()));
                  }

                  entityHit.m_20334_(
                     entityHit.m_20184_().f_82479_ * (double)this.applyKnockback,
                     entityHit.m_20184_().f_82480_,
                     entityHit.m_20184_().f_82481_ * (double)this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         } else if (this.entity.getAnimationTick() == 37
            && this.shouldFollowUp(2.5F)
            && (double)this.entity.getHealthRatio() <= 0.9
            && this.entity.m_217043_().m_188501_() < 0.6F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityWroughtnaut.ATTACK_TWICE_ANIMATION);
         }
      } else if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_TWICE_ANIMATION) {
         if (this.entity.getAnimationTick() < 7 && entityTarget != null) {
            this.entity.m_21391_(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 10) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 1.0F);
         } else if (this.entity.getAnimationTick() == 12) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_SWING_3.get(), 1.5F, 1.0F);
            List<LivingEntity> entitiesHit = this.entity
               .getEntityLivingBaseNearby((double)this.range - 0.3, 3.0, (double)this.range - 0.3, (double)this.range - 0.3);
            float damage = (float)this.entity.m_21051_(Attributes.f_22281_).m_22135_();
            boolean hit = false;

            for (LivingEntity entityHit : entitiesHit) {
               float entityHitAnglex = (float)(
                  (Math.atan2(entityHit.m_20189_() - this.entity.m_20189_(), entityHit.m_20185_() - this.entity.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
               );
               float entityAttackingAnglex = this.entity.f_20883_ % 360.0F;
               if (entityHitAnglex < 0.0F) {
                  entityHitAnglex += 360.0F;
               }

               if (entityAttackingAnglex < 0.0F) {
                  entityAttackingAnglex += 360.0F;
               }

               float entityRelativeAngle = entityHitAnglex - entityAttackingAnglex;
               float entityHitDistance = (float)Math.sqrt(
                  (entityHit.m_20189_() - this.entity.m_20189_()) * (entityHit.m_20189_() - this.entity.m_20189_())
                     + (entityHit.m_20185_() - this.entity.m_20185_()) * (entityHit.m_20185_() - this.entity.m_20185_())
               );
               if ((double)entityHitDistance <= (double)this.range - 0.3 && entityRelativeAngle <= this.arc / 2.0F && entityRelativeAngle >= -this.arc / 2.0F
                  || entityRelativeAngle >= 360.0F - this.arc / 2.0F
                  || entityRelativeAngle <= -360.0F + this.arc / 2.0F) {
                  entityHit.m_6469_(DamageSource.m_19370_(this.entity), damage);
                  if (entityHit.m_21254_()) {
                     entityHit.m_21211_().m_41622_(400, entityHit, player -> player.m_21190_(entityHit.m_7655_()));
                  }

                  entityHit.m_20334_(
                     entityHit.m_20184_().f_82479_ * (double)this.applyKnockback,
                     entityHit.m_20184_().f_82480_,
                     entityHit.m_20184_().f_82481_ * (double)this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         } else if (this.entity.getAnimationTick() == 23
            && this.shouldFollowUp(3.5F)
            && (double)this.entity.getHealthRatio() <= 0.6
            && this.entity.m_217043_().m_188501_() < 0.6F) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityWroughtnaut.ATTACK_THRICE_ANIMATION);
         }
      } else if (this.entity.getAnimation() == EntityWroughtnaut.ATTACK_THRICE_ANIMATION) {
         if (this.entity.getAnimationTick() == 1) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_PRE_SWING_3.get(), 1.2F, 1.0F);
         }

         if (this.entity.getAnimationTick() < 22 && entityTarget != null) {
            this.entity.m_21391_(entityTarget, 30.0F, 30.0F);
         } else {
            this.entity.m_146922_(this.entity.f_19859_);
         }

         if (this.entity.getAnimationTick() == 20) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_WHOOSH.get(), 1.2F, 0.9F);
         } else if (this.entity.getAnimationTick() == 24) {
            this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_GRUNT_3.get(), 1.5F, 1.13F);
            this.entity
               .m_6478_(
                  MoverType.SELF,
                  new Vec3(
                     Math.cos(Math.toRadians((double)(this.entity.m_146908_() + 90.0F))),
                     0.0,
                     Math.sin(Math.toRadians((double)(this.entity.m_146908_() + 90.0F)))
                  )
               );
            List<LivingEntity> entitiesHit = this.entity
               .getEntityLivingBaseNearby((double)this.range + 0.2, 3.0, (double)this.range + 0.2, (double)this.range + 0.2);
            float damage = (float)this.entity.m_21051_(Attributes.f_22281_).m_22135_();
            boolean hit = false;

            for (LivingEntity entityHit : entitiesHit) {
               float entityHitDistance = (float)Math.sqrt(
                  (entityHit.m_20189_() - this.entity.m_20189_()) * (entityHit.m_20189_() - this.entity.m_20189_())
                     + (entityHit.m_20185_() - this.entity.m_20185_()) * (entityHit.m_20185_() - this.entity.m_20185_())
               );
               if ((double)entityHitDistance <= (double)this.range + 0.2) {
                  entityHit.m_6469_(DamageSource.m_19370_(this.entity), damage);
                  if (entityHit.m_21254_()) {
                     entityHit.m_21211_().m_41622_(400, entityHit, player -> player.m_21190_(entityHit.m_7655_()));
                  }

                  entityHit.m_20334_(
                     entityHit.m_20184_().f_82479_ * (double)this.applyKnockback,
                     entityHit.m_20184_().f_82480_,
                     entityHit.m_20184_().f_82481_ * (double)this.applyKnockback
                  );
                  hit = true;
               }
            }

            if (hit) {
               this.entity.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_AXE_HIT.get(), 1.0F, 0.5F);
            }
         }
      }
   }
}
