package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class PeeperAggroProcedure {
   public static boolean execute(Entity entity) {
      return entity == null ? false : entity.getPersistentData().m_128459_("aggro") >= 128.0;
   }
}
