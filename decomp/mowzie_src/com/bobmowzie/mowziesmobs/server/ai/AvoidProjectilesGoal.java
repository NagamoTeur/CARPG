package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AvoidProjectilesGoal extends Goal {
   protected final PathfinderMob entity;
   private final double farSpeed;
   private final double nearSpeed;
   protected Projectile avoidTarget;
   protected final float avoidDistance;
   protected Path path;
   protected Vec3 dodgeVec;
   protected final PathNavigation navigation;
   protected final Class<Projectile> classToAvoid;
   protected final Predicate<Projectile> avoidTargetSelector;
   private int dodgeTimer = 0;

   public AvoidProjectilesGoal(PathfinderMob entityIn, Class<Projectile> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
      this(entityIn, classToAvoidIn, entity -> true, avoidDistanceIn, farSpeedIn, nearSpeedIn);
   }

   public AvoidProjectilesGoal(
      PathfinderMob entityIn, Class<Projectile> avoidClass, Predicate<Projectile> targetPredicate, float distance, double nearSpeedIn, double farSpeedIn
   ) {
      this.entity = entityIn;
      this.classToAvoid = avoidClass;
      this.avoidTargetSelector = targetPredicate.and(target -> {
         Vec3 aActualMotion = new Vec3(target.m_20185_() - target.f_19854_, target.m_20186_() - target.f_19855_, target.m_20189_() - target.f_19856_);
         if (!(aActualMotion.m_82553_() < 0.1) && target.f_19797_ >= 0) {
            if (!this.entity.m_21574_().m_148306_(target)) {
               return false;
            } else {
               float dot = (float)target.m_20184_().m_82541_().m_82526_(this.entity.m_20182_().m_82546_(target.m_20182_()).m_82541_());
               return !((double)dot < 0.8);
            }
         } else {
            return false;
         }
      });
      this.avoidDistance = distance;
      this.farSpeed = nearSpeedIn;
      this.nearSpeed = farSpeedIn;
      this.navigation = entityIn.m_21573_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.dodgeTimer > 0) {
         return false;
      } else {
         this.avoidTarget = this.getMostMovingTowardsMeEntity(
            this.classToAvoid,
            this.avoidTargetSelector,
            this.entity,
            this.entity.m_20191_().m_82377_((double)this.avoidDistance, 3.0, (double)this.avoidDistance)
         );
         if (this.avoidTarget == null) {
            return false;
         } else {
            Vec3 projectilePos = this.guessProjectileDestination(this.avoidTarget);
            this.dodgeVec = this.avoidTarget.m_20184_().m_82537_(new Vec3(0.0, 1.0, 0.0)).m_82541_().m_82490_(1.0);
            Vec3 newPosLeft = this.entity.m_20182_().m_82549_(this.dodgeVec);
            Vec3 newPosRight = this.entity.m_20182_().m_82549_(this.dodgeVec.m_82490_(-1.0));
            Vec3 diffLeft = newPosLeft.m_82546_(projectilePos);
            Vec3 diffRight = newPosRight.m_82546_(projectilePos);
            if (diffRight.m_82556_() > diffLeft.m_82556_()) {
               this.dodgeVec = this.dodgeVec.m_82490_(-1.0);
            }

            Vec3 dodgeDest = diffRight.m_82556_() > diffLeft.m_82556_() ? newPosRight : newPosLeft;
            Vec3 vector3d = DefaultRandomPos.m_148412_(this.entity, 5, 3, dodgeDest, (float) (Math.PI / 2));
            if (vector3d == null) {
               this.path = null;
               return true;
            } else if (projectilePos.m_82546_(vector3d).m_82556_() < projectilePos.m_82546_(this.entity.m_20182_()).m_82556_()) {
               return false;
            } else {
               this.path = this.navigation.m_26524_(vector3d.f_82479_, vector3d.f_82480_, vector3d.f_82481_, 0);
               return true;
            }
         }
      }
   }

   public boolean m_8045_() {
      return !this.navigation.m_26571_() || this.dodgeTimer < 10;
   }

   public void m_8056_() {
      if (this.path != null) {
         this.navigation.m_26536_(this.path, this.farSpeed);
         this.dodgeVec = this.path.m_77380_(this.entity).m_82546_(this.entity.m_20182_()).m_82541_().m_82490_(1.0);
      }

      this.entity.m_20256_(this.entity.m_20184_().m_82549_(this.dodgeVec));
   }

   public void m_8041_() {
      this.avoidTarget = null;
      this.dodgeTimer = 0;
   }

   public void m_8037_() {
      if (this.entity.m_20280_(this.avoidTarget) < 49.0) {
         this.entity.m_21573_().m_26517_(this.nearSpeed);
      } else {
         this.entity.m_21573_().m_26517_(this.farSpeed);
      }

      this.dodgeTimer++;
   }

   private Vec3 guessProjectileDestination(Projectile projectile) {
      Vec3 vector3d = projectile.m_20182_();
      Vec3 vector3d1 = vector3d.m_82549_(projectile.m_20184_().m_82490_(50.0));
      return this.entity.f_19853_.m_45547_(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, projectile)).m_82450_();
   }

   @Nullable
   private <T extends Projectile> T getMostMovingTowardsMeEntity(
      Class<? extends T> entityClazz, Predicate<? super T> predicate, LivingEntity entity, AABB p_225318_10_
   ) {
      return this.getMostMovingTowardsMeEntityFromList(entity.f_19853_.m_6443_(entityClazz, p_225318_10_, predicate), entity);
   }

   private <T extends Projectile> T getMostMovingTowardsMeEntityFromList(List<? extends T> entities, LivingEntity target) {
      double d0 = -2.0;
      T t = null;

      for (T t1 : entities) {
         double d1 = t1.m_20184_().m_82541_().m_82526_(target.m_20182_().m_82546_(t1.m_20182_()).m_82541_());
         if (d1 > d0) {
            d0 = d1;
            t = t1;
         }
      }

      return t;
   }
}
