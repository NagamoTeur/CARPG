package com.hollingsworth.arsnouveau.common.entity.goal;

import net.minecraft.world.entity.ai.goal.Goal;

public class ExtendedRangeGoal extends Goal {
   public int ticksRunning;
   public int ticksPerDistance;
   public double startDistance;
   public double extendedRange;

   public ExtendedRangeGoal(int ticksPerDistance) {
      this.ticksPerDistance = ticksPerDistance;
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.startDistance == 0.0) {
         this.extendedRange = 0.0;
      } else {
         this.ticksRunning++;
         if ((double)this.ticksRunning > this.startDistance * (double)this.ticksPerDistance) {
            this.extendedRange = 0.5 + ((double)this.ticksRunning - this.startDistance * (double)this.ticksPerDistance) / (double)this.ticksPerDistance * 0.5;
         }
      }
   }

   public void reset() {
      this.ticksRunning = 0;
      this.extendedRange = 0.0;
      this.startDistance = 0.0;
   }

   public void m_8056_() {
      super.m_8056_();
      this.reset();
   }

   public void m_8041_() {
      super.m_8041_();
      this.reset();
   }

   public boolean m_8036_() {
      return true;
   }
}
