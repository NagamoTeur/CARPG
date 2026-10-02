package shadows.apotheosis.ench.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import shadows.apotheosis.ench.table.EnchantingStatManager;

public interface IEnchantingBlock extends IForgeBlock {
   @OverrideOnly
   default float getEternaBonus(BlockState state, LevelReader world, BlockPos pos) {
      return this.getEnchantPowerBonus(state, world, pos);
   }

   @OverrideOnly
   default float getMaxEnchantingPower(BlockState state, LevelReader world, BlockPos pos) {
      return 15.0F;
   }

   @OverrideOnly
   default float getQuantaBonus(BlockState state, LevelReader world, BlockPos pos) {
      return 0.0F;
   }

   @OverrideOnly
   default float getArcanaBonus(BlockState state, LevelReader world, BlockPos pos) {
      return 0.0F;
   }

   @OverrideOnly
   default float getQuantaRectification(BlockState state, LevelReader world, BlockPos pos) {
      return 0.0F;
   }

   @OverrideOnly
   default int getBonusClues(BlockState state, LevelReader world, BlockPos pos) {
      return 0;
   }

   default void spawnTableParticle(BlockState state, Level level, RandomSource rand, BlockPos pos, BlockPos offset) {
      if (rand.m_188503_(16) == 0
         && EnchantingStatManager.getEterna(level.m_8055_(pos.m_121955_(offset)), level, pos.m_121955_(offset)) > 0.0F
         && level.m_46859_(pos.m_7918_(offset.m_123341_() / 2, 0, offset.m_123343_() / 2))) {
         level.m_7106_(
            this.getTableParticle(state),
            (double)pos.m_123341_() + 0.5,
            (double)pos.m_123342_() + 2.0,
            (double)pos.m_123343_() + 0.5,
            (double)((float)offset.m_123341_() + rand.m_188501_()) - 0.5,
            (double)((float)offset.m_123342_() - rand.m_188501_() - 1.0F),
            (double)((float)offset.m_123343_() + rand.m_188501_()) - 0.5
         );
      }
   }

   default ParticleOptions getTableParticle(BlockState state) {
      return ParticleTypes.f_123809_;
   }
}
