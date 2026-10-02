package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class WaterDamageProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity.m_20071_()) {
            entity.m_6469_(DamageSource.f_146701_, 1.0F);
         }
      }
   }
}
