package com.hollingsworth.arsnouveau.common.block;

import com.google.common.collect.Maps;
import com.hollingsworth.arsnouveau.common.block.tile.SconceTile;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SconceBlock extends TickableModBlock {
   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
      Map.of(
         Direction.NORTH,
         Block.m_49796_(5.0, 3.0, 10.0, 11.0, 13.0, 16.0),
         Direction.SOUTH,
         Block.m_49796_(5.0, 3.0, 0.0, 11.0, 13.0, 6.0),
         Direction.WEST,
         Block.m_49796_(10.0, 3.0, 5.0, 16.0, 13.0, 11.0),
         Direction.EAST,
         Block.m_49796_(0.0, 3.0, 5.0, 6.0, 13.0, 11.0)
      )
   );
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final Property<Integer> LIGHT_LEVEL = IntegerProperty.m_61631_("level", 0, 15);

   public SconceBlock() {
      super(
         Properties.m_60939_(Material.f_76278_)
            .m_60918_(SoundType.f_56742_)
            .m_60913_(2.0F, 3.0F)
            .m_60955_()
            .m_60910_()
            .m_60953_(b -> (Integer)b.m_61143_(LIGHT_LEVEL))
      );
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return context.m_43719_().m_122434_().m_122479_() ? (BlockState)this.m_49966_().m_61124_(FACING, context.m_43719_()) : null;
   }

   public VoxelShape m_5940_(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
      return AABBS.get(p_220053_1_.m_61143_(FACING));
   }

   public BlockState m_7417_(
      BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_
   ) {
      return p_196271_2_.m_122424_() == p_196271_1_.m_61143_(FACING) && !p_196271_1_.m_60710_(p_196271_4_, p_196271_5_)
         ? Blocks.f_50016_.m_49966_()
         : p_196271_1_;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
      builder.m_61104_(new Property[]{LIGHT_LEVEL});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new SconceTile(pos, state);
   }
}
