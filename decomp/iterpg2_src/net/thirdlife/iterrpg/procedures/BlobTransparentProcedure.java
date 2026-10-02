package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class BlobTransparentProcedure {
   public static boolean execute(Entity entity) {
      if (entity == null) {
         return false;
      } else {
         double chance = 0.0;
         chance = entity.getPersistentData().m_128459_("deathtime");
         return entity.getPersistentData().m_128459_("timer") >= chance * 0.75
            && entity.getPersistentData().m_128459_("timer") >= (double)Mth.m_216271_(RandomSource.m_216327_(), 1, (int)chance);
      }
   }
}
