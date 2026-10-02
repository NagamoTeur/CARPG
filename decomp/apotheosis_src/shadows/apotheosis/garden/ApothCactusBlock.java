package shadows.apotheosis.garden;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.common.ForgeHooks;
import shadows.placebo.util.IReplacementBlock;

public class ApothCactusBlock extends CactusBlock implements IReplacementBlock {
   protected StateDefinition<Block, BlockState> container;

   public ApothCactusBlock() {
      super(Properties.m_60926_(Blocks.f_50128_));
   }

   public void m_213898_(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
      BlockPos blockpos = pos.m_7494_();
      if (!world.m_151570_(blockpos) && world.m_46859_(blockpos)) {
         int i = 1;
         if (GardenModule.maxCactusHeight <= 32) {
            while (world.m_8055_(pos.m_6625_(i)).m_60734_() == this) {
               i++;
            }
         }

         if (i < GardenModule.maxCactusHeight) {
            int j = (Integer)state.m_61143_(f_51131_);
            if (ForgeHooks.onCropsGrowPre(world, blockpos, state, true)) {
               if (j == 15) {
                  world.m_46597_(blockpos, this.m_49966_());
                  BlockState newState = (BlockState)state.m_61124_(f_51131_, 0);
                  world.m_7731_(pos, newState, 4);
                  world.m_213960_(newState, blockpos, this, pos, false);
               } else {
                  world.m_7731_(pos, (BlockState)state.m_61124_(f_51131_, j + 1), 4);
               }

               ForgeHooks.onCropsGrowPost(world, pos, state);
            }
         }
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
