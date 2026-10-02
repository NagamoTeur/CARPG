package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.debug.DebugEvent;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class GoToBedGoal extends Goal {
   boolean unreachable;
   public Starbuncle starbuncle;
   BlockPos bedPos;
   public StarbyBehavior behavior;
   public int ticksRunning;

   public GoToBedGoal(Starbuncle starbuncle, StarbyBehavior behavior) {
      this.starbuncle = starbuncle;
      this.behavior = behavior;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8045_() {
      if (this.ticksRunning >= 300) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("BedTimeout", "Took too long to find bed"));
         this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.NONE;
         return false;
      } else if (this.bedPos != null && this.starbuncle.data.bedPos != null) {
         boolean bedValid = true;
         boolean isOnBed = false;
         if (this.starbuncle.f_19853_.m_46467_() % 10L == 0L) {
            bedValid = this.isBedValid();
            isOnBed = this.isOnBed();
         }

         return !this.unreachable && bedValid && !isOnBed;
      } else {
         return false;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.unreachable = false;
      this.starbuncle.getNavigation().m_26573_();
      this.starbuncle.goalState = Starbuncle.StarbuncleGoalState.RESTING;
      this.ticksRunning = 0;
      this.bedPos = this.starbuncle.data.bedPos;
   }

   public void m_8041_() {
      super.m_8041_();
      this.ticksRunning = 0;
   }

   public void m_8037_() {
      super.m_8037_();
      this.ticksRunning++;
      this.setPath((double)this.bedPos.m_123341_(), (double)this.bedPos.m_123342_() + 1.0, (double)this.bedPos.m_123343_(), 1.3);
      this.starbuncle
         .addGoalDebug(
            this, new DebugEvent("PathToBed", "Pathing to " + this.bedPos.m_123341_() + " " + this.bedPos.m_123342_() + " " + this.bedPos.m_123343_())
         );
   }

   public void setPath(double x, double y, double z, double speedIn) {
      this.starbuncle.getNavigation().tryMoveToBlockPos(new BlockPos(x, y, z), 1.3);
      if (this.starbuncle.getNavigation().m_26570_() != null && !this.starbuncle.getNavigation().m_26570_().m_77403_()) {
         this.starbuncle.addGoalDebug(this, new DebugEvent("BedUnreachable", "Unreachable"));
         this.unreachable = true;
      }
   }

   public boolean m_8036_() {
      if (this.starbuncle.f_19853_.f_46441_.m_188503_(2) == 0) {
         return false;
      } else {
         this.bedPos = this.starbuncle.data.bedPos;
         if (this.starbuncle.getBedBackoff() <= 0
            && this.starbuncle.goalState == Starbuncle.StarbuncleGoalState.NONE
            && this.bedPos != null
            && this.behavior.canGoToBed()) {
            boolean canRun = this.isBedValid() && !this.isOnBed();
            if (!canRun) {
               this.starbuncle.setBedBackoff(60);
               this.starbuncle.addGoalDebug(this, new DebugEvent("InvalidBed", "Bed position invalid"));
            }

            return canRun;
         } else {
            this.starbuncle.addGoalDebug(this, new DebugEvent("CannotSleep", "Bed not valid backoff: " + this.starbuncle.getBedBackoff()));
            return false;
         }
      }
   }

   public boolean isBedValid() {
      return this.starbuncle.f_19853_.m_46749_(this.bedPos)
         && this.starbuncle.f_19853_.m_8055_(new BlockPos(this.bedPos)).m_204336_(BlockTagProvider.SUMMON_SLEEPABLE);
   }

   public boolean isOnBed() {
      return this.starbuncle.f_19853_.m_8055_(new BlockPos(this.starbuncle.f_19825_)).m_204336_(BlockTagProvider.SUMMON_SLEEPABLE);
   }

   public boolean m_6767_() {
      return true;
   }
}
