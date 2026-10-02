package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;

public class CoinTimerProcedure {
   public static boolean execute(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         boolean agr = false;
         return !(entity.getPersistentData().m_128459_("otcup") >= 1.0) || !(entity.getPersistentData().m_128459_("war") <= 1.0);
      }
   }
}
