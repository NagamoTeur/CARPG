package net.cisco.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class GeminiBlightOnEffectActiveTickProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         entity.m_6469_(DamageSource.f_19311_, 2.0F);
      }
   }
}
