package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class TwiffleBonemealProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.TWIFFLE_BLOCK.get()).m_49966_(), 3);
      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123748_, x + 0.5, y + 0.5, z + 0.5, 16, 0.5, 0.5, 0.5, 0.025);
      }
   }
}
