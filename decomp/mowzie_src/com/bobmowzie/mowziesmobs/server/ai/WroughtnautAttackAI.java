package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class WroughtnautAttackAI extends Goal {
   private final EntityWroughtnaut wroughtnaut;
   private int repath;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int attacksSinceVertical;
   private int timeSinceStomp;

   public WroughtnautAttackAI(EntityWroughtnaut wroughtnaut) {
      this.wroughtnaut = wroughtnaut;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
   }

   public boolean m_8036_() {
      LivingEntity target = this.wroughtnaut.m_5448_();
      return target != null && target.m_6084_() && this.wroughtnaut.isActive() && this.wroughtnaut.getAnimation() == IAnimatedEntity.NO_ANIMATION;
   }

   public void m_8056_() {
      this.repath = 0;
   }

   public void m_8041_() {
      this.wroughtnaut.m_21573_().m_26573_();
   }

   public void m_8037_() {
      LivingEntity target = this.wroughtnaut.m_5448_();
      if (target != null) {
         double dist = this.wroughtnaut.m_20275_(this.targetX, this.targetY, this.targetZ);
         this.wroughtnaut.m_21563_().m_24960_(target, 30.0F, 30.0F);
         if (--this.repath <= 0
               && (this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0 || target.m_20275_(this.targetX, this.targetY, this.targetZ) >= 1.0)
            || this.wroughtnaut.m_21573_().m_26571_()) {
            this.targetX = target.m_20185_();
            this.targetY = target.m_20186_();
            this.targetZ = target.m_20189_();
            this.repath = 4 + this.wroughtnaut.m_217043_().m_188503_(7);
            if (dist > 1024.0) {
               this.repath += 10;
            } else if (dist > 256.0) {
               this.repath += 5;
            }

            if (!this.wroughtnaut.m_21573_().m_5624_(target, 0.2)) {
               this.repath += 15;
            }
         }

         dist = this.wroughtnaut.m_20275_(this.targetX, this.targetY, this.targetZ);
         if (target.m_20186_() - this.wroughtnaut.m_20186_() >= -1.0 && target.m_20186_() - this.wroughtnaut.m_20186_() <= 3.0) {
            boolean couldStomp = dist < 36.0 && this.timeSinceStomp > 200;
            if (dist < 12.25
               && this.wroughtnaut.getDotProductBodyFacingEntity(target) > 0.0
               && (!couldStomp || this.wroughtnaut.m_217043_().m_188501_() < 0.667F)) {
               if (!((float)this.attacksSinceVertical > 3.0F + 2.0F * (1.0F - this.wroughtnaut.getHealthRatio()))
                  && !(this.wroughtnaut.m_217043_().m_188501_() < 0.18F)) {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.ATTACK_ANIMATION);
                  this.attacksSinceVertical++;
               } else {
                  AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.VERTICAL_ATTACK_ANIMATION);
                  this.attacksSinceVertical = 0;
               }
            } else if (couldStomp) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.wroughtnaut, EntityWroughtnaut.STOMP_ATTACK_ANIMATION);
               this.timeSinceStomp = 0;
               this.attacksSinceVertical++;
            }
         }

         this.timeSinceStomp++;
      }
   }
}
