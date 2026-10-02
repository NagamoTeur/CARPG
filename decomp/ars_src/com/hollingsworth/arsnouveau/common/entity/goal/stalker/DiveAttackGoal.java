package com.hollingsworth.arsnouveau.common.entity.goal.stalker;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DiveAttackGoal extends Goal {
   WildenStalker stalker;

   public DiveAttackGoal(WildenStalker stalker) {
      this.stalker = stalker;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.stalker.m_5448_() != null && this.stalker.isFlying();
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.stalker.m_5448_();
      if (livingentity == null) {
         return false;
      } else if (!livingentity.m_6084_()) {
         return false;
      } else {
         return livingentity instanceof Player && (livingentity.m_5833_() || ((Player)livingentity).m_7500_()) ? false : this.m_8036_();
      }
   }

   public void m_8056_() {
   }

   public void m_8037_() {
      LivingEntity livingentity = this.stalker.m_5448_();
      this.stalker.orbitOffset = new Vec3(livingentity.m_20185_(), livingentity.m_20227_(0.5), livingentity.m_20189_());
      if (this.stalker.m_20191_().m_82400_(0.2F).m_82381_(livingentity.m_20191_())) {
         this.stalker.m_7327_(livingentity);
         this.stalker.setFlying(false);
      } else if (this.stalker.f_19862_ || this.stalker.f_20916_ > 0) {
         this.stalker.setFlying(false);
      }
   }
}
