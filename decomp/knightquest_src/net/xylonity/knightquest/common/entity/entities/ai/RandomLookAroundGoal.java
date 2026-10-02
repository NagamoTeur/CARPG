package net.xylonity.knightquest.common.entity.entities.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;

public class RandomLookAroundGoal extends Goal {
   private final GremlinEntity mob;
   private double relX;
   private double relZ;
   private int lookTime;

   public RandomLookAroundGoal(GremlinEntity pMob) {
      this.mob = pMob;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.mob.m_217043_().m_188501_() < 0.02F && !this.mob.getIsPassive();
   }

   public boolean m_8045_() {
      return this.lookTime >= 0;
   }

   public void m_8056_() {
      double $$0 = (Math.PI * 2) * this.mob.m_217043_().m_188500_();
      this.relX = Math.cos($$0);
      this.relZ = Math.sin($$0);
      this.lookTime = 20 + this.mob.m_217043_().m_188503_(20);
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      this.lookTime--;
      this.mob.m_21563_().m_24946_(this.mob.m_20185_() + this.relX, this.mob.m_20188_(), this.mob.m_20189_() + this.relZ);
   }
}
