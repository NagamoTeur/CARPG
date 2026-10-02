package com.hollingsworth.arsnouveau.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TableBlock extends TickableModBlock {
   public static final EnumProperty<ThreePartBlock> PART = BlockProps.TABLE_ENUM;
   protected static final VoxelShape BASE = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape LEG_NORTH_WEST = Block.m_49796_(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
   protected static final VoxelShape LEG_SOUTH_WEST = Block.m_49796_(0.0, 0.0, 16.0, 3.0, 3.0, 16.0);
   protected static final VoxelShape LEG_NORTH_EAST = Block.m_49796_(16.0, 0.0, 0.0, 16.0, 3.0, 3.0);
   protected static final VoxelShape LEG_SOUTH_EAST = Block.m_49796_(16.0, 0.0, 16.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape NORTH_SHAPE = Shapes.m_83124_(BASE, new VoxelShape[]{LEG_NORTH_WEST, LEG_NORTH_EAST});
   protected static final VoxelShape SOUTH_SHAPE = Shapes.m_83124_(BASE, new VoxelShape[]{LEG_SOUTH_WEST, LEG_SOUTH_EAST});
   protected static final VoxelShape WEST_SHAPE = Shapes.m_83124_(BASE, new VoxelShape[]{LEG_NORTH_WEST, LEG_SOUTH_WEST});
   protected static final VoxelShape EAST_SHAPE = Shapes.m_83124_(BASE, new VoxelShape[]{LEG_NORTH_EAST, LEG_SOUTH_EAST});
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;

   public TableBlock() {
      super(Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60913_(2.0F, 3.0F).m_60955_());
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(PART, ThreePartBlock.FOOT));
   }

   public PushReaction m_5537_(BlockState p_149656_1_) {
      return PushReaction.BLOCK;
   }

   public void m_6402_(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
      if (!world.f_46443_) {
         BlockPos blockpos = pos.m_121945_((Direction)state.m_61143_(FACING));
         world.m_7731_(blockpos, (BlockState)state.m_61124_(PART, ThreePartBlock.HEAD), 3);
         world.m_6289_(pos, Blocks.f_50016_);
         state.m_60701_(world, pos, 3);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_196258_1_) {
      Direction direction = p_196258_1_.m_8125_();
      BlockPos blockpos = p_196258_1_.m_8083_();
      BlockPos blockpos1 = blockpos.m_121945_(direction);
      return p_196258_1_.m_43725_().m_8055_(blockpos1).m_60629_(p_196258_1_) ? (BlockState)this.m_49966_().m_61124_(FACING, direction) : null;
   }

   public BlockState tearDown(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
      return Blocks.f_50016_.m_49966_();
   }

   public BlockState m_7417_(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
      if (direction != getNeighbourDirection((ThreePartBlock)state.m_61143_(PART), (Direction)state.m_61143_(FACING))) {
         return super.m_7417_(state, direction, state2, world, pos, pos2);
      } else {
         return state2.m_60713_(this) && state2.m_61143_(PART) != state.m_61143_(PART) ? state : this.tearDown(state, direction, state2, world, pos, pos2);
      }
   }

   public static Direction getNeighbourDirection(ThreePartBlock p_208070_0_, Direction p_208070_1_) {
      return p_208070_0_ == ThreePartBlock.FOOT ? p_208070_1_ : p_208070_1_.m_122424_();
   }

   public static Direction getConnectedDirection(BlockState p_226862_0_) {
      Direction direction = (Direction)p_226862_0_.m_61143_(FACING);
      return p_226862_0_.m_61143_(PART) == ThreePartBlock.HEAD ? direction.m_122424_() : direction;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      Direction direction = getConnectedDirection(state).m_122424_();

      return switch (direction) {
         case NORTH -> NORTH_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         default -> EAST_SHAPE;
      };
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, PART});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public RenderShape m_7514_(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
