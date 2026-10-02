package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class PosRememberProcedure {
   public static void execute(double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("brainX", x);
         entity.getPersistentData().m_128347_("brainY", y);
         entity.getPersistentData().m_128347_("brainZ", z);
      }
   }
}
