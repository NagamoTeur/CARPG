package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.TemporaryTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TemporaryBlock extends MirrorWeave implements EntityBlock, ITickableBlock {
   public static final IntegerProperty POWER = BlockStateProperties.f_61426_;

   public TemporaryBlock(Properties properties) {
      super(properties.m_60953_(b -> (Integer)b.m_61143_(LIGHT_LEVEL)));
   }

   @Override
   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      return InteractionResult.SUCCESS;
   }

   @Override
   public void m_213897_(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
   }

   @Nullable
   @Override
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new TemporaryTile(pPos, pState);
   }

   @Override
   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{POWER});
   }

   @Override
   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public boolean m_7899_(BlockState state) {
      return (Integer)state.m_61143_(POWER) > 0;
   }

   public int m_6378_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return (Integer)blockState.m_61143_(POWER);
   }

   public int m_6376_(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return (Integer)blockState.m_61143_(POWER);
   }
}
