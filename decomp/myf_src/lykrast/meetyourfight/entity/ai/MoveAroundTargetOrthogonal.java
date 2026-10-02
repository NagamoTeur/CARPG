package lykrast.meetyourfight.entity.ai;

import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MoveAroundTargetOrthogonal extends Goal {
   private Mob mob;
   private int moveCooldown;
   private double speed;

   public MoveAroundTargetOrthogonal(Mob mob, double speed) {
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.mob = mob;
      this.speed = speed;
   }

   public boolean m_8036_() {
      return this.mob.m_5448_() != null && !this.mob.m_21566_().m_24995_();
   }

   public void m_8056_() {
      this.moveCooldown = 20;
      LivingEntity target = this.mob.m_5448_();
      RandomSource rand = this.mob.m_217043_();
      Direction dir = Direction.m_122366_(this.mob.m_20185_() - target.m_20185_(), 0.0, this.mob.m_20189_() - target.m_20189_());
      switch (rand.m_188503_(8)) {
         case 0:
            dir = dir.m_122427_();
            break;
         case 1:
            dir = dir.m_122428_();
      }

      double distance = rand.m_188500_() * 2.0 + 4.0;
      this.mob
         .m_21566_()
         .m_6849_(
            target.m_20185_() + (double)dir.m_122429_() * distance,
            target.m_20186_() + 1.0 + rand.m_188500_() * 2.0,
            target.m_20189_() + (double)dir.m_122431_() * distance,
            this.speed
         );
   }

   public boolean m_183429_() {
      return true;
   }

   public boolean m_8045_() {
      return this.moveCooldown > 0;
   }

   public void m_8037_() {
      this.moveCooldown--;
   }
}
