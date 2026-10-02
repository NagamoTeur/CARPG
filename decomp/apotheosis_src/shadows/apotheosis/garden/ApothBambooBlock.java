package shadows.apotheosis.garden;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import shadows.placebo.util.IReplacementBlock;

public class ApothBambooBlock extends BambooBlock implements IReplacementBlock {
   protected StateDefinition<Block, BlockState> container;

   public ApothBambooBlock() {
      super(Properties.m_60926_(Blocks.f_50571_));
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if ((Integer)state.m_61143_(f_48871_) == 0 && random.m_188503_(3) == 0 && worldIn.m_46859_(pos.m_7494_()) && worldIn.m_45524_(pos.m_7494_(), 0) >= 9) {
         int i = this.m_48932_(worldIn, pos) + 1;
         if (i < GardenModule.maxBambooHeight) {
            this.m_220731_(state, worldIn, pos, random, i);
         }
      }
   }

   public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
      int i = this.m_48882_(worldIn, pos);
      int j = this.m_48932_(worldIn, pos);
      return i + j + 1 < GardenModule.maxBambooHeight && (Integer)worldIn.m_8055_(pos.m_6630_(i)).m_61143_(f_48871_) != 1;
   }

   public void m_214148_(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
      int bambooAbove = this.m_48882_(worldIn, pos);
      int bambooBelow = this.m_48932_(worldIn, pos);
      int bambooSize = bambooAbove + bambooBelow + 1;
      int l = 1 + rand.m_188503_(2);

      for (int i1 = 0; i1 < l; i1++) {
         BlockPos blockpos = pos.m_6630_(bambooAbove);
         BlockState blockstate = worldIn.m_8055_(blockpos);
         if (bambooSize >= GardenModule.maxBambooHeight || (Integer)blockstate.m_61143_(f_48871_) == 1 || !worldIn.m_46859_(blockpos.m_7494_())) {
            return;
         }

         this.m_220731_(blockstate, worldIn, blockpos, rand, bambooSize);
         bambooAbove++;
         bambooSize++;
      }
   }

   protected int m_48882_(BlockGetter worldIn, BlockPos pos) {
      int i = 0;

      while (i < GardenModule.maxBambooHeight && worldIn.m_8055_(pos.m_6630_(i + 1)).m_60734_() == Blocks.f_50571_) {
         i++;
      }

      return i;
   }

   protected int m_48932_(BlockGetter worldIn, BlockPos pos) {
      int i = 0;

      while (i < GardenModule.maxBambooHeight && worldIn.m_8055_(pos.m_6625_(i + 1)).m_60734_() == Blocks.f_50571_) {
         i++;
      }

      return i;
   }

   protected void m_220731_(BlockState blockStateIn, Level worldIn, BlockPos posIn, RandomSource rand, int size) {
      BlockState blockstate = worldIn.m_8055_(posIn.m_7495_());
      BlockPos blockpos = posIn.m_6625_(2);
      BlockState blockstate1 = worldIn.m_8055_(blockpos);
      BambooLeaves bambooleaves = BambooLeaves.NONE;
      if (size >= 1) {
         if (blockstate.m_60734_() != Blocks.f_50571_ || blockstate.m_61143_(f_48870_) == BambooLeaves.NONE) {
            bambooleaves = BambooLeaves.SMALL;
         } else if (blockstate.m_60734_() == Blocks.f_50571_ && blockstate.m_61143_(f_48870_) != BambooLeaves.NONE) {
            bambooleaves = BambooLeaves.LARGE;
            if (blockstate1.m_60734_() == Blocks.f_50571_) {
               worldIn.m_7731_(posIn.m_7495_(), (BlockState)blockstate.m_61124_(f_48870_, BambooLeaves.SMALL), 3);
               worldIn.m_7731_(blockpos, (BlockState)blockstate1.m_61124_(f_48870_, BambooLeaves.NONE), 3);
            }
         }
      }

      int i = blockStateIn.m_61143_(f_48869_) != 1 && blockstate1.m_60734_() != Blocks.f_50571_ ? 0 : 1;
      int j = ((double)size < (double)GardenModule.maxBambooHeight - (double)GardenModule.maxBambooHeight / 5.0 || !(rand.m_188501_() < 0.25F))
            && size != GardenModule.maxBambooHeight - 1
         ? 0
         : 1;
      worldIn.m_7731_(
         posIn.m_7494_(),
         (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_48869_, i)).m_61124_(f_48870_, bambooleaves)).m_61124_(f_48871_, j),
         3
      );
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
