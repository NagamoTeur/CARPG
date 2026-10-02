package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class ForestvinesSpawnProcedure {
   public static void execute(double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("lifetime", (double)Mth.m_216271_(RandomSource.m_216327_(), 200, 300));
         entity.getPersistentData().m_128347_("ascend", 3.5);
         entity.m_6021_(x, y - 1.3, z);
         if (entity instanceof ServerPlayer _serverPlayer) {
            _serverPlayer.f_8906_.m_9774_(x, y - 1.3, z, entity.m_146908_(), entity.m_146909_());
         }
      }
   }
}
