package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.api.event.ChimeraSummonEvent;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.WildenChimera;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketTimedEvent;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ChimeraSummonGoal extends Goal {
   private WildenChimera mob;
   public int timeSummoning;
   public boolean done;
   public boolean howling;

   public ChimeraSummonGoal(WildenChimera boss) {
      this.mob = boss;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public void m_8056_() {
      super.m_8056_();
      this.timeSummoning = 0;
      this.done = false;
      this.howling = false;
   }

   public boolean m_6767_() {
      return false;
   }

   public boolean m_8045_() {
      return !this.done && !this.mob.getPhaseSwapping();
   }

   public boolean m_8036_() {
      return this.mob.canSummon() && !this.mob.m_6069_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (!this.howling) {
         this.howling = true;
         this.mob.setHowling(true);
         ChimeraSummonEvent summonEvent = new ChimeraSummonEvent(
            40 + this.mob.getPhase() * 20, this.mob.getPhase(), this.mob.f_19853_, this.mob.m_20183_(), this.mob.m_19879_()
         );
         EventQueue.getServerInstance().addEvent(summonEvent);
         Networking.sendToNearby(this.mob.f_19853_, this.mob, new PacketTimedEvent(summonEvent));
         this.mob.f_19853_.m_5594_(null, this.mob.m_20183_(), SoundEvents.f_12620_, SoundSource.HOSTILE, 1.0F, 0.2F);
      }

      this.timeSummoning++;
      if (this.timeSummoning >= 80) {
         this.done = true;
         this.mob.summonCooldown = (int)(1000.0 + ParticleUtil.inRange(-100.0, 100.0) + (double)this.mob.getCooldownModifier());
         this.mob.setHowling(false);
      }
   }
}
