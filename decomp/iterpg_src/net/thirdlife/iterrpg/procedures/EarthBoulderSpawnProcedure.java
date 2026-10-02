package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class EarthBoulderSpawnProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double ypos = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double k = 0.0;
         entity.getPersistentData().m_128347_("ascend", 3.5);
         entity.getPersistentData().m_128347_("lifetime", 0.0);
      }
   }
}
