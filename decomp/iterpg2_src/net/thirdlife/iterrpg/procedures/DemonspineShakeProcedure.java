package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class DemonspineShakeProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().m_128459_("ascend") > 0.0;
   }
}
