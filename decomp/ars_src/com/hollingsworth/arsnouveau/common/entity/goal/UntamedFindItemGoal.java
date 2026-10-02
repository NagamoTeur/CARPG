package com.hollingsworth.arsnouveau.common.entity.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

public class UntamedFindItemGoal extends Goal {
   Mob mobEntity;
   Predicate<ItemEntity> itemSelector;
   Supplier<Boolean> canRun;
   int timeFinding;
   boolean itemStuck;
   ItemEntity pathingEntity;

   public List<ItemEntity> nearbyItems() {
      return this.mobEntity.f_19853_.m_6443_(ItemEntity.class, new AABB(this.mobEntity.m_20183_()).m_82400_(8.0), this.itemSelector);
   }

   public UntamedFindItemGoal(Mob mobEntity, Supplier<Boolean> canRun, Predicate<ItemEntity> itemSelector) {
      this.mobEntity = mobEntity;
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.canRun = canRun;
      this.itemSelector = itemSelector;
   }

   public void m_8037_() {
      super.m_8037_();
      this.timeFinding++;
      if (this.pathingEntity != null && !this.pathingEntity.m_213877_()) {
         this.pathToTarget(this.pathingEntity, 1.2F);
      }
   }

   public boolean m_8045_() {
      return this.timeFinding <= 600
         && !this.itemStuck
         && this.pathingEntity != null
         && !this.pathingEntity.m_213877_()
         && !this.pathingEntity.m_32055_().m_41619_()
         && this.m_8036_();
   }

   public boolean m_8036_() {
      return this.canRun.get() && !this.nearbyItems().isEmpty();
   }

   public void m_8041_() {
      super.m_8041_();
      this.timeFinding = 0;
      this.itemStuck = false;
   }

   public void m_8056_() {
      super.m_8056_();
      this.timeFinding = 0;
      this.itemStuck = false;
      List<ItemEntity> list = this.nearbyItems();
      if (!list.isEmpty() && !this.itemStuck) {
         for (ItemEntity item : list) {
            Path path = this.mobEntity.m_21573_().m_6570_(item, 0);
            if (path != null && path.m_77403_()) {
               this.pathingEntity = item;
               this.pathToTarget(this.pathingEntity, 1.2F);
               break;
            }
         }
      }

      if (this.pathingEntity == null) {
         this.itemStuck = true;
      }
   }

   public void pathToTarget(Entity entity, double speed) {
      Path path = this.mobEntity.m_21573_().m_6570_(entity, 0);
      if (path != null && path.m_77403_()) {
         this.mobEntity.m_21573_().m_26536_(path, speed);
      }

      if (path != null && !path.m_77403_()) {
         this.itemStuck = true;
      }
   }
}
