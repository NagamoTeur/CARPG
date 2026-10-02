package io.redspace.ironsspellbooks.block;

import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireflyJar extends Block {
   public static final VoxelShape SHAPE = Shapes.m_83110_(Block.m_49796_(4.0, 0.0, 4.0, 12.0, 13.0, 12.0), Block.m_49796_(6.0, 13.0, 6.0, 10.0, 16.0, 10.0));

   public FireflyJar() {
      super(Properties.m_60926_(Blocks.f_50058_).m_60953_(x -> 8));
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return SHAPE;
   }

   public void m_214162_(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
      double d0 = (double)pPos.m_123341_() + 0.5;
      double d1 = (double)pPos.m_123342_();
      double d2 = (double)pPos.m_123343_() + 0.5;
      double d3 = pRandom.m_188500_() * 0.6 - 0.3;
      double d4 = pRandom.m_188500_() * 0.6;
      double d6 = pRandom.m_188500_() * 0.6 - 0.3;
      pLevel.m_7106_(ParticleHelper.FIREFLY, d0 + d3, d1 + d4, d2 + d6, 0.0, 0.0, 0.0);
      pLevel.m_7106_(ParticleHelper.FIREFLY, d0 + d3 * 2.0, d1 + d4 * 2.0, d2 + d6 * 2.0, 0.0, 0.0, 0.0);
   }
}
