package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class GrieverShakeProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : !(entity.getPersistentData().m_128459_("screamTime") >= 1200.0);
   }
}
