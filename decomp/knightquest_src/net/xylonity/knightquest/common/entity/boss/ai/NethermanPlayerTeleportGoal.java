package net.xylonity.knightquest.common.entity.boss.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.xylonity.knightquest.common.entity.boss.NethermanEntity;

public class NethermanPlayerTeleportGoal extends Goal {
   private final NethermanEntity netherman;
   public int chargeTime;

   public NethermanPlayerTeleportGoal(NethermanEntity netherman) {
      this.netherman = netherman;
   }

   public boolean m_8036_() {
      return this.netherman.m_5448_() != null
         && (double)this.netherman.m_21223_() > (double)this.netherman.m_21233_() * 0.1
         && (
            this.netherman.getPhase() == 1
               || this.netherman.getPhase() == 2 && this.netherman.getCounterSwitchPhase2() == 130
               || this.netherman.getPhase() == 3 && this.netherman.getCounterSwitchPhase3() == 160
         );
   }

   public void m_8056_() {
      this.chargeTime = 400;
   }

   public void m_8041_() {
      this.chargeTime = 0;
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      if (this.chargeTime > 0) {
         this.chargeTime--;
      } else {
         for (ServerPlayer player : this.netherman.f_19853_.m_45976_(ServerPlayer.class, this.netherman.m_20191_().m_82400_(50.0))) {
            double randomX = this.netherman.m_20185_() + (this.netherman.m_217043_().m_188500_() - 0.5) * 25.0;
            double randomZ = this.netherman.m_20189_() + (this.netherman.m_217043_().m_188500_() - 0.5) * 25.0;
            double randomY = (double)this.netherman
               .f_19853_
               .m_5452_(Types.MOTION_BLOCKING, new BlockPos((int)randomX, (int)this.netherman.m_20186_(), (int)randomZ))
               .m_123342_();
            player.m_6021_(randomX, randomY, randomZ);
            this.netherman.f_19853_.m_214171_(GameEvent.f_238175_, player.m_20182_(), Context.m_223717_(player));
            this.netherman.f_19853_.m_6263_(null, player.f_19854_, player.f_19855_, player.f_19856_, SoundEvents.f_11852_, player.m_5720_(), 1.0F, 1.0F);
            this.netherman.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
         }

         this.chargeTime = 400;
      }
   }
}
