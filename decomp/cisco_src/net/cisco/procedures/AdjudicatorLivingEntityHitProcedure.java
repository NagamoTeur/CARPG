package net.cisco.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class AdjudicatorLivingEntityHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            < (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 5.0F) {
            entity.m_6469_(DamageSource.f_19311_, (float)((double)(entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21223_() : -1.0F) / 1.2));
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123810_, x, y, z, 40, 1.0, 1.0, 1.0, 1.0);
         }
      }
   }
}
