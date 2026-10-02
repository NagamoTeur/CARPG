package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class DropletHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
      if (entity != null && immediatesourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123769_, x, y, z, 8, 0.1, 0.1, 0.1, 0.0);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123772_, x, y, z, 4, 0.1, 0.1, 0.1, 0.01);
         }

         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fishing_bobber.splash")),
                  SoundSource.NEUTRAL,
                  0.5F,
                  5.0F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fishing_bobber.splash")),
                  SoundSource.NEUTRAL,
                  0.5F,
                  5.0F,
                  false
               );
            }
         }

         entity.m_20095_();
         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
