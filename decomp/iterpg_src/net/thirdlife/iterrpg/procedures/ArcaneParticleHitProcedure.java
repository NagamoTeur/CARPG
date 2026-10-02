package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class ArcaneParticleHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ARCANE_PARTICLE.get(), x, y, z, 10, 0.0, 0.0, 0.0, 0.05);
         }

         entity.m_6469_(DamageSource.f_19319_, 1.0F);
      }
   }
}
