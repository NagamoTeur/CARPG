package shadows.apotheosis.garden;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.common.ForgeHooks;
import shadows.placebo.util.IReplacementBlock;

public class ApothSugarcaneBlock extends SugarCaneBlock implements IReplacementBlock {
   protected StateDefinition<Block, BlockState> container;

   public ApothSugarcaneBlock() {
      super(Properties.m_60926_(Blocks.f_50130_));
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if (worldIn.m_46859_(pos.m_7494_())) {
         int i = 0;
         if (GardenModule.maxReedHeight <= 32) {
            i = 1;

            while (worldIn.m_8055_(pos.m_6625_(i)).m_60734_() == this) {
               i++;
            }
         }

         if (i < GardenModule.maxReedHeight) {
            int j = (Integer)state.m_61143_(f_57164_);
            if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
               if (j == 15) {
                  worldIn.m_46597_(pos.m_7494_(), this.m_49966_());
                  worldIn.m_7731_(pos, (BlockState)state.m_61124_(f_57164_, 0), 4);
               } else {
                  worldIn.m_7731_(pos, (BlockState)state.m_61124_(f_57164_, j + 1), 4);
               }

               ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
         }
      }
   }

   @Deprecated
   public void m_6861_(BlockState state, Level world, BlockPos pos, Block block, BlockPos origin, boolean isMoving) {
      if (pos.m_123342_() != origin.m_123342_()) {
         super.m_6861_(state, world, pos, block, origin, isMoving);
      }
   }

   public void _setDefaultState(BlockState state) {
      this.m_49959_(state);
   }

   public void setStateContainer(StateDefinition<Block, BlockState> container) {
      this.container = container;
   }

   public StateDefinition<Block, BlockState> m_49965_() {
      return this.container == null ? super.m_49965_() : this.container;
   }
}
