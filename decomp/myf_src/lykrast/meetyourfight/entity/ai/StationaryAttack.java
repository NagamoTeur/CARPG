package lykrast.meetyourfight.entity.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public abstract class StationaryAttack extends Goal {
   private Mob boss;
   private double stationaryY;

   public StationaryAttack(Mob boss) {
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.boss = boss;
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8056_() {
      if (this.boss.m_5448_() != null) {
         this.stationaryY = this.boss.m_5448_().m_20186_() + 1.0 + this.boss.m_217043_().m_188500_() * 2.0;
      } else {
         this.stationaryY = this.boss.m_20186_();
      }
   }

   public void m_8037_() {
      if (!this.boss.m_21566_().m_24995_() && Math.abs(this.boss.m_20186_() - this.stationaryY) >= 1.0) {
         this.boss.m_21566_().m_6849_(this.boss.m_20185_(), this.stationaryY, this.boss.m_20189_(), 1.0);
      }
   }
}
