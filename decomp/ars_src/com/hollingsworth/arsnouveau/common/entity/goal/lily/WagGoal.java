package com.hollingsworth.arsnouveau.common.entity.goal.lily;

import com.hollingsworth.arsnouveau.common.entity.Lily;
import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class WagGoal extends Goal {
   Lily lily;
   public LivingEntity target;
   public int wagAtTicks;

   public WagGoal(Lily lily) {
      this.lily = lily;
      this.m_7021_(EnumSet.of(Flag.LOOK));
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.wagAtTicks > 0) {
         this.wagAtTicks--;
      }

      if (this.target != null) {
         this.lily.m_21563_().m_24960_(this.target, 30.0F, 30.0F);
      }
   }

   public boolean m_8036_() {
      this.target = null;
      ServerLevel level = (ServerLevel)this.lily.f_19853_;

      for (Player player : level.m_8795_(p -> true)) {
         if (player.m_20270_(this.lily) < 5.0F && this.lily.isLookingAtMe(player)) {
            this.target = player;
            this.wagAtTicks = 100;
            this.lily.setWagging(true);
            this.lily.wagTicks = 100;
            return true;
         }
      }

      return false;
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public boolean m_8045_() {
      return this.wagAtTicks > 0;
   }
}
