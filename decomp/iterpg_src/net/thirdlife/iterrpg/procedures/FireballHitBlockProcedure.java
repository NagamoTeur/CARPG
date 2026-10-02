package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class FireballHitBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123756_, x + 0.5, y + 0.5, z + 0.5, 8, 0.25, 0.25, 0.25, 0.15);
      }

      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
               SoundSource.HOSTILE,
               1.0F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 4.0)
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
               SoundSource.HOSTILE,
               1.0F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 4.0),
               false
            );
         }
      }

      if (world.m_46859_(new BlockPos(x, y + 1.0, z)) && world.m_6106_().m_5470_().m_46207_(GameRules.f_46132_)) {
         world.m_7731_(new BlockPos(x, y + 1.0, z), Blocks.f_50083_.m_49966_(), 3);
      }
   }
}
