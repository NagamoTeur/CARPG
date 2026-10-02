package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class GrimKeyPassProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.GRIM_KEYHOLE.get()) {
         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50626_.m_49966_(), 3);
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelx) {
            _levelx.m_46672_(new BlockPos(x, y, z), _levelx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      } else if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.CHARGED_GRIMSTONE_BRICKS.get()) {
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.GRIMSTONE_BRICKS.get()).m_49966_(), 3);
         if (world instanceof ServerLevel _levelx) {
            _levelx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _levelx) {
            if (!_levelx.m_5776_()) {
               _levelx.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _levelx.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelxx) {
            _levelxx.m_46672_(new BlockPos(x, y, z), _levelxx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      } else if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.GRIM_SOULTRAP.get()) {
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.POLISHED_GRIMSTONE.get()).m_49966_(), 3);
         if (world instanceof ServerLevel _levelxx) {
            _levelxx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _levelxx) {
            if (!_levelxx.m_5776_()) {
               _levelxx.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _levelxx.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelxxx) {
            _levelxxx.m_46672_(new BlockPos(x, y, z), _levelxxx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      } else if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.BARRIER_PROJECTOR.get()) {
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.POLISHED_GRIMSTONE.get()).m_49966_(), 3);
         if (world instanceof ServerLevel _levelxxx) {
            _levelxxx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _levelxxx) {
            if (!_levelxxx.m_5776_()) {
               _levelxxx.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _levelxxx.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelxxxx) {
            _levelxxxx.m_46672_(new BlockPos(x, y, z), _levelxxxx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      } else if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE.get()) {
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.POLISHED_GRIMSTONE.get()).m_49966_(), 3);
         if (world instanceof ServerLevel _levelxxxx) {
            _levelxxxx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _levelxxxx) {
            if (!_levelxxxx.m_5776_()) {
               _levelxxxx.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _levelxxxx.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelxxxxx) {
            _levelxxxxx.m_46672_(new BlockPos(x, y, z), _levelxxxxx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      } else if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.MOURNERS_SPAWNER.get()) {
         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50723_.m_49966_(), 3);
         if (world instanceof ServerLevel _levelxxxxx) {
            _levelxxxxx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x + 0.5, y + 0.5, z + 0.5, 32, 0.25, 0.25, 0.25, 0.025);
         }

         if (world instanceof Level _levelxxxxx) {
            if (!_levelxxxxx.m_5776_()) {
               _levelxxxxx.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F
               );
            } else {
               _levelxxxxx.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ender_eye.death")),
                  SoundSource.BLOCKS,
                  1.0F,
                  0.5F,
                  false
               );
            }
         }

         if (world instanceof Level _levelxxxxxx) {
            _levelxxxxxx.m_46672_(new BlockPos(x, y, z), _levelxxxxxx.m_8055_(new BlockPos(x, y, z)).m_60734_());
         }
      }
   }
}
