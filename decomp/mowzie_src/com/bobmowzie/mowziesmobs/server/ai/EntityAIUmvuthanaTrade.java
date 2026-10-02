package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public final class EntityAIUmvuthanaTrade extends Goal {
   private final EntityUmvuthanaMinion umvuthana;

   public EntityAIUmvuthanaTrade(EntityUmvuthanaMinion umvuthana) {
      this.umvuthana = umvuthana;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.umvuthana.m_6084_() && !this.umvuthana.m_20069_() && this.umvuthana.m_20096_() && !this.umvuthana.f_19864_) {
         Player plyr = this.umvuthana.getCustomer();
         return plyr != null && this.umvuthana.m_20280_(plyr) <= 16.0 && plyr.f_36096_ != null;
      } else {
         return false;
      }
   }

   public void m_8056_() {
      this.umvuthana.m_21573_().m_26573_();
   }

   public void m_8041_() {
      this.umvuthana.setCustomer(null);
   }
}
