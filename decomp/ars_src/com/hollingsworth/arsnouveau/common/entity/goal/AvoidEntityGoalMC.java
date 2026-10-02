package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AvoidEntityGoalMC<T extends LivingEntity> extends Goal {
   protected final Starbuncle mob;
   private final double walkSpeedModifier;
   private final double sprintSpeedModifier;
   protected T toAvoid;
   protected final float maxDist;
   protected Path path;
   protected final PathNavigation pathNav;
   protected final Class<T> avoidClass;
   protected final Predicate<LivingEntity> avoidPredicate;
   private final TargetingConditions avoidEntityTargeting;

   public AvoidEntityGoalMC(Starbuncle carby, Class<T> avoidClass, float maxDist, double walkModifier, double sprintModifier) {
      this(carby, avoidClass, p_200828_0_ -> true, maxDist, walkModifier, sprintModifier, EntitySelector.f_20406_::test);
   }

   public AvoidEntityGoalMC(
      Starbuncle carby,
      Class<T> avoidClass,
      Predicate<LivingEntity> avoidPredicate,
      float maxDist,
      double walkModifier,
      double sprintModifier,
      Predicate<LivingEntity> selectPredicate
   ) {
      this.mob = carby;
      this.avoidClass = avoidClass;
      this.avoidPredicate = avoidPredicate;
      this.maxDist = maxDist;
      this.walkSpeedModifier = walkModifier;
      this.sprintSpeedModifier = sprintModifier;
      this.pathNav = carby.getNavigation();
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.avoidEntityTargeting = TargetingConditions.m_148352_().m_26883_((double)maxDist).m_26888_(selectPredicate.and(avoidPredicate));
   }

   public boolean m_8036_() {
      this.toAvoid = (T)this.mob
         .f_19853_
         .m_45963_(
            this.avoidClass,
            this.avoidEntityTargeting,
            this.mob,
            this.mob.m_20185_(),
            this.mob.m_20186_(),
            this.mob.m_20189_(),
            this.mob.m_20191_().m_82377_((double)this.maxDist, 3.0, (double)this.maxDist)
         );
      if (this.toAvoid == null) {
         return false;
      } else {
         Vec3 vector3d = DefaultRandomPos.m_148407_(this.mob, 16, 7, this.toAvoid.m_20182_());
         if (vector3d == null) {
            return false;
         } else if (this.toAvoid.m_20275_(vector3d.f_82479_, vector3d.f_82480_, vector3d.f_82481_) < this.toAvoid.m_20280_(this.mob)) {
            return false;
         } else {
            this.mob.getNavigation().tryMoveToBlockPos(new BlockPos(vector3d.f_82479_, vector3d.f_82480_, vector3d.f_82481_), this.sprintSpeedModifier);
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return !this.pathNav.m_26571_();
   }

   public void m_8056_() {
   }

   public void m_8041_() {
      this.toAvoid = null;
   }

   public void m_8037_() {
   }
}
