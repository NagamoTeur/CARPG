package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.init.IterRpgModGameRules;

public class SpiderEggMagicHatchProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true).isEmpty()
         && !world.m_6106_().m_5470_().m_46207_(IterRpgModGameRules.BUILDINGDEBUG)) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123809_, x + 0.5, y + 1.5, z + 0.5, 16, 0.3, 0.3, 0.3, 0.0);
         }

         IterRpgMod.queueServerWork(Mth.m_216271_(RandomSource.m_216327_(), 20, 30), () -> {
            if (world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.SPIDER_EGG.get()) {
               world.m_46961_(new BlockPos(x, y, z), false);
               CocoonExplodeProcedure.execute(world, x, y, z);
            }
         });
      }
   }
}
