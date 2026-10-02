package com.hollingsworth.arsnouveau.common.entity.goal.stalker;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class StartFlightGoal extends Goal {
   WildenStalker stalker;

   public StartFlightGoal(WildenStalker stalker) {
      this.stalker = stalker;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public void m_8056_() {
      this.stalker.setLeapCooldown(400);
      this.stalker.m_5997_(0.0, 0.5, 0.0);
      this.stalker.setFlying(true);
      Networking.sendToNearby(this.stalker.f_19853_, this.stalker, new PacketAnimEntity(this.stalker.m_19879_(), WildenStalker.Animations.FLY.ordinal()));
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public boolean m_8036_() {
      return this.stalker.m_5448_() != null && !this.stalker.isFlying() && this.stalker.getLeapCooldown() == 0;
   }
}
