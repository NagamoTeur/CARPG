package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class DemonsoulTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         if (entity.getPersistentData().m_128459_("deathtimer") >= 666.0) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123744_, x, y + 0.25, z, 32, 0.5, 0.5, 0.5, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123777_, x, y + 0.25, z, 24, 0.5, 0.5, 0.5, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123778_, x, y + 0.25, z, 16, 0.5, 0.5, 0.5, 0.025);
            }

            for (int index0 = 0; index0 < 16; index0++) {
               xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
               ypos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
               zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
               if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == Blocks.f_50134_) {
                  world.m_7731_(new BlockPos(x + xpos, y + ypos, z + zpos), ((Block)IterRpgModBlocks.MAGMANUM_ORE.get()).m_49966_(), 3);
               }
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         } else {
            entity.getPersistentData().m_128347_("deathtimer", entity.getPersistentData().m_128459_("deathtimer") + 1.0);
            if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(ParticleTypes.f_123744_, x, y + 0.25, z, 1, 0.2, 0.2, 0.2, 0.025);
               }
            } else if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 0.25, z, 1, 0.2, 0.2, 0.2, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123755_, x, y + 0.25, z, 1, 0.2, 0.2, 0.2, 0.025);
            }
         }
      }
   }
}
