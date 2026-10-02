package com.bobmowzie.mowziesmobs.server.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public final class EntityAIAvoidEntity<T extends Entity> extends Goal {
   private final PathfinderMob entity;
   private final Class<T> avoidClass;
   private final float distance;
   private final Predicate<T> predicate;
   private final double speed;
   private final PathNavigation navigator;
   private T avoiding;
   private Path path;

   public EntityAIAvoidEntity(PathfinderMob entity, Class<T> avoidClass, float distance, double speed) {
      this(entity, avoidClass, e -> true, distance, speed);
   }

   public EntityAIAvoidEntity(PathfinderMob entity, Class<T> avoidClass, Predicate<? super T> predicate, float distance, double speed) {
      this.entity = entity;
      this.avoidClass = avoidClass;
      this.distance = distance;
      Predicate<T> visible = e -> e.m_6084_() && entity.m_21574_().m_148306_(e);
      Predicate<T> targetable = e -> !(e instanceof Player) || !e.m_5833_() && !((Player)e).m_7500_();
      this.predicate = targetable.and(predicate).and(visible);
      this.speed = speed;
      this.navigator = entity.m_21573_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      List<T> entities = this.entity
         .f_19853_
         .m_6443_(this.avoidClass, this.entity.m_20191_().m_82377_((double)this.distance, 3.0, (double)this.distance), this.predicate);
      if (entities.isEmpty()) {
         return false;
      } else {
         this.avoiding = entities.get(this.entity.m_217043_().m_188503_(entities.size()));
         Vec3 pos = DefaultRandomPos.m_148407_(
            this.entity,
            (int)(this.distance + 1.0F),
            (int)(this.distance / 2.0F + 1.0F),
            new Vec3(this.avoiding.m_20185_(), this.avoiding.m_20186_(), this.avoiding.m_20189_())
         );
         if (pos == null) {
            return false;
         } else if (this.avoiding.m_20275_(pos.f_82479_, pos.f_82480_, pos.f_82481_) < this.avoiding.m_20280_(this.entity)) {
            return false;
         } else {
            this.path = this.navigator.m_7864_(new BlockPos(pos), 0);
            return this.path != null;
         }
      }
   }

   public boolean m_8045_() {
      return !this.navigator.m_26571_();
   }

   public void m_8056_() {
      this.navigator.m_26536_(this.path, this.speed);
   }

   public void m_8041_() {
      this.avoiding = null;
   }

   public void m_8037_() {
      this.entity.m_21573_().m_26517_(this.speed);
   }
}
