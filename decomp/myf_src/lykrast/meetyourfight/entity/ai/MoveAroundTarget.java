package lykrast.meetyourfight.entity.ai;

import java.util.EnumSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class MoveAroundTarget extends Goal {
   private Mob mob;
   private double speed;

   public MoveAroundTarget(Mob mob, double speed) {
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.mob = mob;
      this.speed = speed;
   }

   public boolean m_8036_() {
      return this.mob.m_5448_() != null && !this.mob.m_21566_().m_24995_();
   }

   public void m_8056_() {
      LivingEntity target = this.mob.m_5448_();
      RandomSource rand = this.mob.m_217043_();
      float angle = (float)(rand.m_188503_(4) + 2) * 10.0F * (float) (Math.PI / 180.0);
      if (rand.m_188499_()) {
         angle *= -1.0F;
      }

      Vec3 offset = new Vec3(this.mob.m_20185_() - target.m_20185_(), 0.0, this.mob.m_20189_() - target.m_20189_()).m_82541_().m_82524_(angle);
      double distance = rand.m_188500_() * 2.0 + 4.0;
      this.mob
         .m_21566_()
         .m_6849_(
            target.m_20185_() + offset.f_82479_ * distance,
            target.m_20186_() + 1.0 + rand.m_188500_() * 2.0,
            target.m_20189_() + offset.f_82481_ * distance,
            this.speed
         );
   }

   public boolean m_8045_() {
      return false;
   }
}
