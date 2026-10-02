package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class GrieverSpawnProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("screamTime", 1200.0);
      }
   }
}
