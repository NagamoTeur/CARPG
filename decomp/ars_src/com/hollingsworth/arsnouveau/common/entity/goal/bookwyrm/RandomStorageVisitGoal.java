package com.hollingsworth.arsnouveau.common.entity.goal.bookwyrm;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class RandomStorageVisitGoal extends Goal {
   public BlockPos target;
   public int ticksRunning;
   public boolean arrived;
   public int arrivedTicks;
   public boolean isDone;
   public EntityBookwyrm bookwyrm;
   public Supplier<BlockPos> getTarget;

   public RandomStorageVisitGoal(EntityBookwyrm bookwyrm, Supplier<BlockPos> getTarget) {
      this.bookwyrm = bookwyrm;
      this.getTarget = getTarget;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public void m_8056_() {
      this.target = this.getTarget.get();
      this.ticksRunning = 0;
      this.arrived = false;
      this.arrivedTicks = 0;
      this.isDone = false;
      this.bookwyrm.backoffTicks = 100 + this.bookwyrm.f_19853_.f_46441_.m_188503_(100);
   }

   public void m_8037_() {
      if (this.target != null) {
         this.ticksRunning++;
         if (!this.arrived) {
            if (BlockUtil.distanceFrom(
                  this.bookwyrm.f_19825_,
                  new Vec3((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 0.5, (double)this.target.m_123343_() + 0.5)
               )
               < 1.5) {
               this.arrived = true;
            }

            this.bookwyrm
               .m_21573_()
               .m_26519_((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 0.5, (double)this.target.m_123343_() + 0.5, 1.2);
         } else {
            this.arrivedTicks++;
            if (this.arrivedTicks > 100) {
               this.isDone = true;
            }
         }
      }
   }

   public boolean m_8045_() {
      return this.target != null && this.ticksRunning < 200 && !this.isDone && !this.bookwyrm.playerTooFar;
   }

   public boolean m_8036_() {
      return !this.bookwyrm.playerTooFar && this.bookwyrm.backoffTicks <= 0 && this.bookwyrm.m_217043_().m_188503_(4) == 0;
   }
}
