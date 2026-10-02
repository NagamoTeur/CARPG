package com.bobmowzie.mowziesmobs.server.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MMAIAvoidEntity<U extends PathfinderMob, T extends Entity> extends Goal {
   private static final double NEAR_DISTANCE = 7.0;
   protected final U entity;
   private final Predicate<T> selector;
   private final double farSpeed;
   private final double nearSpeed;
   private final float evadeDistance;
   private final Class<T> avoidedEntityType;
   private final int horizontalEvasion;
   private final int verticalEvasion;
   private final int numChecks;
   private T entityEvading;
   private Path entityPathEntity;

   public MMAIAvoidEntity(U entity, Class<T> avoidedEntityType, float evadeDistance, double farSpeed, double nearSpeed) {
      this(entity, avoidedEntityType, Predicates.alwaysTrue(), evadeDistance, farSpeed, nearSpeed, 10, 12, 7);
   }

   public MMAIAvoidEntity(
      U entity, Class<T> avoidedEntityType, float evadeDistance, double farSpeed, double nearSpeed, int numChecks, int horizontalEvasion, int verticalEvasion
   ) {
      this(entity, avoidedEntityType, Predicates.alwaysTrue(), evadeDistance, farSpeed, nearSpeed, numChecks, horizontalEvasion, verticalEvasion);
   }

   public MMAIAvoidEntity(
      U entity,
      Class<T> avoidedEntityType,
      Predicate<? super T> predicate,
      float evadeDistance,
      double farSpeed,
      double nearSpeed,
      int numChecks,
      int horizontalEvasion,
      int verticalEvasion
   ) {
      this.entity = entity;
      this.selector = e -> e != null
            && EntitySelector.f_20406_.test(e)
            && e.m_6084_()
            && entity.m_21574_().m_148306_(e)
            && !entity.m_7307_(e)
            && predicate.test(e);
      this.avoidedEntityType = avoidedEntityType;
      this.evadeDistance = evadeDistance;
      this.farSpeed = farSpeed;
      this.nearSpeed = nearSpeed;
      this.numChecks = numChecks;
      this.horizontalEvasion = horizontalEvasion;
      this.verticalEvasion = verticalEvasion;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      List<T> entities = this.entity
         .f_19853_
         .m_6443_(this.avoidedEntityType, this.entity.m_20191_().m_82377_((double)this.evadeDistance, 3.0, (double)this.evadeDistance), this.selector);
      if (entities.isEmpty()) {
         this.onSafe();
         return false;
      } else {
         this.entityEvading = entities.get(0);

         for (int n = 0; n < this.numChecks; n++) {
            Vec3 pos = DefaultRandomPos.m_148407_(this.entity, this.horizontalEvasion, this.verticalEvasion, this.entityEvading.m_20182_());
            if (pos != null && !(this.entityEvading.m_20275_(pos.f_82479_, pos.f_82480_, pos.f_82481_) < this.entityEvading.m_20280_(this.entity))) {
               this.entityPathEntity = this.entity.m_21573_().m_7864_(new BlockPos(pos), 0);
               if (this.entityPathEntity != null) {
                  return true;
               }
            }
         }

         this.onPathNotFound();
         return false;
      }
   }

   protected void onSafe() {
   }

   protected void onPathNotFound() {
   }

   public boolean m_8045_() {
      return !this.entity.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.entity.m_21573_().m_26536_(this.entityPathEntity, this.farSpeed);
   }

   public void m_8041_() {
      this.entityEvading = null;
   }

   public void m_8037_() {
      this.entity.m_21573_().m_26517_(this.entity.m_20280_(this.entityEvading) < 49.0 ? this.nearSpeed : this.farSpeed);
   }
}
