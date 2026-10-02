package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class WeeperTearHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x, y, z, 4, 0.1, 0.1, 0.1, 0.05);
         }

         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
                  SoundSource.NEUTRAL,
                  0.5F,
                  2.0F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
                  SoundSource.NEUTRAL,
                  0.5F,
                  2.0F,
                  false
               );
            }
         }

         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
