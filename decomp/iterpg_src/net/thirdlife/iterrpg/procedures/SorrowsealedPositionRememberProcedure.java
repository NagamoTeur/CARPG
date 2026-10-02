package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class SorrowsealedPositionRememberProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.getPersistentData().m_128347_("brainX", entity.m_20185_());
         entity.getPersistentData().m_128347_("brainY", entity.m_20186_());
         entity.getPersistentData().m_128347_("brainZ", entity.m_20189_());
      }
   }
}
