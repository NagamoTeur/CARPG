package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class SpiderPlaceWebProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double xplace = 0.0;
         double yplace = 0.0;
         double zplace = 0.0;
         double decideblock = 0.0;
         entity.getPersistentData().m_128347_("tileplace", entity.getPersistentData().m_128459_("tileplace") + 1.0);
         entity.getPersistentData().m_128347_("timer", 800.0 - entity.getPersistentData().m_128459_("cocooncharge"));
         if (entity.getPersistentData().m_128459_("tileplace") >= entity.getPersistentData().m_128459_("timer")) {
            xplace = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
            yplace = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 0);
            zplace = (double)Mth.m_216271_(RandomSource.m_216327_(), -2, 2);
            if (world.m_46859_(new BlockPos(x + xplace, y + yplace, z + zplace))
               && !world.m_46859_(new BlockPos(x + xplace, y + yplace - 1.0, z + zplace))
               && entity.getPersistentData().m_128459_("cocooncharge") >= 8.0) {
               decideblock = Mth.m_216263_(RandomSource.m_216327_(), 0.0, 12.0);
               if (decideblock > 0.0 && decideblock <= 3.0 && entity.getPersistentData().m_128459_("cocooncharge") >= 1.0) {
                  world.m_7731_(new BlockPos(x + xplace, y + yplace, z + zplace), Blocks.f_50267_.m_49966_(), 3);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123759_, x + xplace, y + yplace, z + zplace, 8, 0.3, 0.3, 0.3, 0.025);
                  }

                  entity.getPersistentData().m_128347_("cocooncharge", entity.getPersistentData().m_128459_("cocooncharge") - 1.0);
               }

               if (decideblock > 3.0 && decideblock <= 8.0 && entity.getPersistentData().m_128459_("cocooncharge") >= 4.0) {
                  world.m_7731_(new BlockPos(x + xplace, y + yplace, z + zplace), Blocks.f_50033_.m_49966_(), 3);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123759_, x + xplace, y + yplace, z + zplace, 12, 0.3, 0.3, 0.3, 0.025);
                  }

                  entity.getPersistentData().m_128347_("cocooncharge", entity.getPersistentData().m_128459_("cocooncharge") - 4.0);
               }

               if (decideblock > 8.0 && decideblock <= 12.0 && entity.getPersistentData().m_128459_("cocooncharge") >= 8.0) {
                  world.m_7731_(new BlockPos(x + xplace, y + yplace, z + zplace), ((Block)IterRpgModBlocks.SPIDER_EGG.get()).m_49966_(), 3);
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123759_, x + xplace, y + yplace, z + zplace, 16, 0.3, 0.3, 0.3, 0.025);
                  }

                  entity.getPersistentData().m_128347_("cocooncharge", entity.getPersistentData().m_128459_("cocooncharge") - 8.0);
               }
            }

            entity.getPersistentData().m_128347_("tileplace", 0.0);
            entity.getPersistentData().m_128347_("cocooncharge", entity.getPersistentData().m_128459_("cocooncharge") + 0.05);
         }
      }
   }
}
