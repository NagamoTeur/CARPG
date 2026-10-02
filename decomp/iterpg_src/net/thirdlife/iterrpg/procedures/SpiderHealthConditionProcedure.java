package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SpiderHealthConditionProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            <= (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 1.25;
   }
}
