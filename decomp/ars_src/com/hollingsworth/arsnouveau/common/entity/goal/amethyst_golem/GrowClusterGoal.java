package com.hollingsworth.arsnouveau.common.entity.goal.amethyst_golem;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import com.hollingsworth.arsnouveau.common.util.ArrayUtil;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;

public class GrowClusterGoal extends Goal {
   public AmethystGolem golem;
   public Supplier<Boolean> canUse;
   BlockPos pathPos;
   int usingTicks;
   boolean isDone;

   public GrowClusterGoal(AmethystGolem golem, Supplier<Boolean> canUse) {
      this.golem = golem;
      this.canUse = canUse;
   }

   public void m_8037_() {
      super.m_8037_();
      this.usingTicks--;
      if (this.pathPos != null) {
         this.golem.getNavigation().tryMoveToBlockPos(this.pathPos, 1.3F);
         if (BlockUtil.distanceFrom(this.golem.m_20183_(), this.pathPos) <= 2.0) {
            this.golem.setImbueing(true);
            this.golem.setImbuePos(this.pathPos);
         }
      }

      if (this.usingTicks <= 0) {
         this.growCluster();
      }
   }

   public void m_8056_() {
      this.usingTicks = 120;
      this.isDone = false;
      BlockPos p = ArrayUtil.getRandomElement(this.golem.buddingBlocks);
      this.golem.getNavigation().tryMoveToBlockPos(p, 1.0);
      this.pathPos = p;
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.GROW;
   }

   public void growCluster() {
      int numGrown = 0;

      for (BlockPos p : this.golem.buddingBlocks) {
         if (numGrown > 3) {
            break;
         }

         if (this.golem.f_19853_.m_8055_(p).m_204336_(BlockTagProvider.BUDDING_BLOCKS)) {
            this.golem.f_19853_.m_8055_(p).m_222972_((ServerLevel)this.golem.f_19853_, p, this.golem.m_217043_());
            numGrown++;
         }
      }

      this.isDone = true;
      this.golem.growCooldown = 300;
      this.golem.setImbueing(false);
   }

   public void m_8041_() {
      this.golem.setImbueing(false);
      this.golem.goalState = AmethystGolem.AmethystGolemGoalState.NONE;
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8045_() {
      return !this.isDone;
   }

   public boolean m_8036_() {
      return this.canUse.get() && this.golem.growCooldown <= 0 && !this.golem.buddingBlocks.isEmpty();
   }
}
