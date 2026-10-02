package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class GoblinsCampRememberProcedure {
   public static void execute(double x, double z, Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("xcord", x);
         entity.getPersistentData().m_128347_("zcord", z);
      }
   }
}
