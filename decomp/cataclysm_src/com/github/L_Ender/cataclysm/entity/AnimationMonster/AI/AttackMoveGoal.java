package com.github.L_Ender.cataclysm.entity.AnimationMonster.AI;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class AttackMoveGoal extends Goal {
   private final LLibrary_Monster Boss_monster;
   private final boolean followingTargetEvenIfNotSeen;
   private Path path;
   private int delayCounter;
   protected final double moveSpeed;

   public AttackMoveGoal(LLibrary_Monster boss, boolean followingTargetEvenIfNotSeen, double moveSpeed) {
      this.Boss_monster = boss;
      this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
      this.moveSpeed = moveSpeed;
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity target = this.Boss_monster.m_5448_();
      return target != null && target.m_6084_() && this.Boss_monster.getAnimation() == IAnimatedEntity.NO_ANIMATION;
   }

   public void m_8041_() {
      this.Boss_monster.m_21573_().m_26573_();
      LivingEntity livingentity = this.Boss_monster.m_5448_();
      if (!EntitySelector.f_20406_.test(livingentity)) {
         this.Boss_monster.m_6710_((LivingEntity)null);
      }

      this.Boss_monster.m_21561_(false);
      this.Boss_monster.m_21573_().m_26573_();
   }

   public boolean m_8045_() {
      LivingEntity target = this.Boss_monster.m_5448_();
      if (target == null) {
         return false;
      } else if (!target.m_6084_()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.Boss_monster.m_21573_().m_26571_();
      } else {
         return !this.Boss_monster.m_21444_(target.m_20183_()) ? false : !(target instanceof Player) || !target.m_5833_() && !((Player)target).m_7500_();
      }
   }

   public void m_8056_() {
      this.Boss_monster.m_21573_().m_26536_(this.path, this.moveSpeed);
      this.Boss_monster.m_21561_(true);
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      LivingEntity target = this.Boss_monster.m_5448_();
      if (target != null) {
         this.Boss_monster.m_21563_().m_24960_(target, 30.0F, 30.0F);
         double distSq = this.Boss_monster.m_20275_(target.m_20185_(), target.m_20191_().f_82289_, target.m_20189_());
         if (--this.delayCounter <= 0) {
            this.delayCounter = 4 + this.Boss_monster.m_217043_().m_188503_(7);
            if (distSq > Math.pow(this.Boss_monster.m_21051_(Attributes.f_22277_).m_22135_(), 2.0)) {
               if (!this.Boss_monster.m_21691_() && !this.Boss_monster.m_21573_().m_5624_(target, 1.0)) {
                  this.delayCounter += 5;
               }
            } else {
               this.Boss_monster.m_21573_().m_5624_(target, this.moveSpeed);
            }
         }
      }
   }
}
