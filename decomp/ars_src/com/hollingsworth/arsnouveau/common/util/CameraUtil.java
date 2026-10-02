package com.hollingsworth.arsnouveau.common.util;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CameraUtil {
   public static boolean isPlayerMountedOnCamera(LivingEntity entity) {
      if (entity instanceof Player player) {
         return player.f_19853_.f_46443_ ? ClientCameraUtil.isPlayerMountedOnCamera() : ((ServerPlayer)player).m_8954_() instanceof ScryerCamera;
      } else {
         return false;
      }
   }
}
