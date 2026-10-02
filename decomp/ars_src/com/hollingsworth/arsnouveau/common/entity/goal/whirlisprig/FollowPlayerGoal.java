package com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowPlayerGoal extends Goal {
   private final Mob entity;
   private final Predicate<Player> followPredicate;
   private Player followingEntity;
   private final double speedModifier;
   private final PathNavigation navigation;
   private int timeToRecalcPath;
   private final float stopDistance;
   private float oldWaterCost;
   private final float areaSize;
   private final float probability;

   public FollowPlayerGoal(Mob mob, double speedModifier, float stopDistance, float areaSize, float probability) {
      this.entity = mob;
      this.followPredicate = Objects::nonNull;
      this.speedModifier = speedModifier;
      this.navigation = mob.m_21573_();
      this.stopDistance = stopDistance;
      this.areaSize = areaSize;
      this.probability = probability;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      if (!(mob.m_21573_() instanceof GroundPathNavigation) && !(mob.m_21573_() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   public FollowPlayerGoal(Mob mob, double speedModifier, float stopDistance, float areaSize) {
      this(mob, speedModifier, stopDistance, areaSize, 0.001F);
   }

   public boolean m_8036_() {
      List<Player> list = this.entity.f_19853_.m_6443_(Player.class, this.entity.m_20191_().m_82400_((double)this.areaSize), this.followPredicate);
      if (!list.isEmpty()) {
         for (Player mobentity : list) {
            if (!mobentity.m_20145_()) {
               this.followingEntity = mobentity;
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_8045_() {
      return this.followingEntity != null
         && !this.navigation.m_26571_()
         && this.entity.m_20280_(this.followingEntity) > (double)(this.stopDistance * this.stopDistance);
   }

   public void m_8056_() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.entity.m_21439_(BlockPathTypes.WATER);
      this.entity.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.followingEntity = null;
      this.navigation.m_26573_();
      this.entity.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
   }

   public void m_8037_() {
      if (this.followingEntity != null && !this.entity.m_21523_()) {
         this.entity.m_21563_().m_24960_(this.followingEntity, 10.0F, (float)this.entity.m_8132_());
         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            double d0 = this.entity.m_20185_() - this.followingEntity.m_20185_();
            double d1 = this.entity.m_20186_() - this.followingEntity.m_20186_();
            double d2 = this.entity.m_20189_() - this.followingEntity.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (!(d3 <= (double)(this.stopDistance * this.stopDistance))) {
               this.navigation.m_5624_(this.followingEntity, this.speedModifier);
            } else {
               this.navigation.m_26573_();
               if (d3 <= (double)this.stopDistance) {
                  double d4 = this.followingEntity.m_20185_() - this.entity.m_20185_();
                  double d5 = this.followingEntity.m_20189_() - this.entity.m_20189_();
                  this.navigation.m_26519_(this.entity.m_20185_() - d4, this.entity.m_20186_(), this.entity.m_20189_() - d5, this.speedModifier);
               }
            }
         }
      }
   }
}
