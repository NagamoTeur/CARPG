package lykrast.meetyourfight.entity.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class MoveFrontOfTarget extends Goal {
   private Mob mob;
   private int moveCooldown;
   private double speed;

   public MoveFrontOfTarget(Mob mob, double speed) {
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.mob = mob;
      this.speed = speed;
   }

   public boolean m_8036_() {
      return this.mob.m_5448_() != null && !this.mob.m_21566_().m_24995_();
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8056_() {
      this.moveCooldown = 20;
      LivingEntity target = this.mob.m_5448_();
      BlockPos targetP = target.m_20183_();
      Vec3 look = Vec3.m_82498_(0.0F, target.m_146908_());
      this.mob
         .m_21566_()
         .m_6849_(
            (double)targetP.m_123341_() + look.f_82479_ * 4.0 - 0.5 + this.mob.m_217043_().m_188500_() * 2.0,
            (double)(targetP.m_123342_() + 2) + this.mob.m_217043_().m_188500_() * 2.0,
            (double)targetP.m_123343_() + look.f_82481_ * 4.0 - 0.5 + this.mob.m_217043_().m_188500_() * 2.0,
            this.speed
         );
   }

   public boolean m_8045_() {
      return this.moveCooldown > 0;
   }

   public void m_8037_() {
      this.moveCooldown--;
   }
}
