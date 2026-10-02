package net.xylonity.knightquest.common.entity.entities.ai;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;

public class NearestAttackableTargetGoal<T extends LivingEntity> extends TargetGoal {
   protected final Class<T> targetType;
   protected final int randomInterval;
   @Nullable
   protected LivingEntity target;
   protected TargetingConditions targetConditions;
   private final GremlinEntity gremlinEntity;

   public NearestAttackableTargetGoal(GremlinEntity pMob, Class<T> pTargetType, boolean pMustSee) {
      this(pMob, pTargetType, 10, pMustSee, false, null);
   }

   public NearestAttackableTargetGoal(
      GremlinEntity pMob, Class<T> pTargetType, int pRandomInterval, boolean pMustSee, boolean pMustReach, @Nullable Predicate<LivingEntity> pTargetPredicate
   ) {
      super(pMob, pMustSee, pMustReach);
      this.gremlinEntity = pMob;
      this.targetType = pTargetType;
      this.randomInterval = m_186073_(pRandomInterval);
      this.m_7021_(EnumSet.of(Flag.TARGET));
      this.targetConditions = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(pTargetPredicate);
   }

   public boolean m_8036_() {
      if (this.randomInterval > 0 && this.f_26135_.m_217043_().m_188503_(this.randomInterval) != 0) {
         return false;
      } else {
         this.findTarget();
         return this.target != null && !this.gremlinEntity.getIsPassive();
      }
   }

   protected AABB getTargetSearchArea(double pTargetDistance) {
      return this.f_26135_.m_20191_().m_82377_(pTargetDistance, 4.0, pTargetDistance);
   }

   protected void findTarget() {
      if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
         this.target = this.f_26135_
            .f_19853_
            .m_45982_(
               this.f_26135_.f_19853_.m_6443_(this.targetType, this.getTargetSearchArea(this.m_7623_()), p_148152_ -> true),
               this.targetConditions,
               this.f_26135_,
               this.f_26135_.m_20185_(),
               this.f_26135_.m_20188_(),
               this.f_26135_.m_20189_()
            );
      } else {
         this.target = this.f_26135_
            .f_19853_
            .m_45949_(this.targetConditions, this.f_26135_, this.f_26135_.m_20185_(), this.f_26135_.m_20188_(), this.f_26135_.m_20189_());
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.target);
      super.m_8056_();
   }

   public void setTarget(@Nullable LivingEntity pTarget) {
      this.target = pTarget;
   }
}
