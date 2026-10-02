package com.github.L_Ender.cataclysm.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class SandstormUtils {
   public static void toggleFlight(LivingEntity living, boolean flight) {
      if (!living.f_19853_.f_46443_ && living instanceof ServerPlayer player) {
         boolean prevFlying = player.m_150110_().f_35935_;
         boolean trueFlight = isCreativePlayer(living) || flight;
         player.m_150110_().f_35936_ = trueFlight;
         player.m_150110_().f_35935_ = trueFlight;
         float defaultFlightSpeed = 0.05F;
         if (flight) {
            player.m_150110_().m_35943_(defaultFlightSpeed * 0.5F);
         } else {
            player.m_150110_().m_35943_(defaultFlightSpeed);
            if (!player.m_5833_()) {
               player.m_150110_().f_35935_ = false;
               if (!player.m_7500_()) {
                  player.m_150110_().f_35936_ = false;
               }
            }
         }

         if (prevFlying != flight) {
            player.m_6885_();
         }
      }
   }

   private static boolean isCreativePlayer(LivingEntity living) {
      if (living instanceof Player player && player.m_7500_()) {
         return true;
      }

      return false;
   }
}
