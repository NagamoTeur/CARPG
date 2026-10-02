package lykrast.meetyourfight.entity.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class VexMoveRandomGoal extends Goal {
   private Mob mob;
   private double speed;

   public VexMoveRandomGoal(Mob mob, double speed) {
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.mob = mob;
      this.speed = speed;
   }

   public boolean m_8036_() {
      return !this.mob.m_21566_().m_24995_() && this.mob.m_217043_().m_188503_(7) == 0;
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8037_() {
      BlockPos blockpos = this.mob.m_20183_();

      for (int i = 0; i < 3; i++) {
         BlockPos blockpos1 = blockpos.m_7918_(
            this.mob.m_217043_().m_188503_(15) - 7, this.mob.m_217043_().m_188503_(11) - 5, this.mob.m_217043_().m_188503_(15) - 7
         );
         if (this.mob.f_19853_.m_46859_(blockpos1)) {
            this.mob
               .m_21566_()
               .m_6849_((double)blockpos1.m_123341_() + 0.5, (double)blockpos1.m_123342_() + 0.5, (double)blockpos1.m_123343_() + 0.5, this.speed);
            if (this.mob.m_5448_() == null) {
               this.mob
                  .m_21563_()
                  .m_24950_((double)blockpos1.m_123341_() + 0.5, (double)blockpos1.m_123342_() + 0.5, (double)blockpos1.m_123343_() + 0.5, 180.0F, 20.0F);
            }
            break;
         }
      }
   }
}
