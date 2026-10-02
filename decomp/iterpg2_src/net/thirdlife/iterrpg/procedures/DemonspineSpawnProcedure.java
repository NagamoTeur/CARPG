package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class DemonspineSpawnProcedure {
   public static void execute(double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("lifetime", 0.0);
         entity.getPersistentData().m_128347_("ascend", 4.0);
         entity.m_6021_(x, y - 1.6, z);
         if (entity instanceof ServerPlayer _serverPlayer) {
            _serverPlayer.f_8906_.m_9774_(x, y - 1.6, z, entity.m_146908_(), entity.m_146909_());
         }
      }
   }
}
