package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.BrazierRelayTile;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrazierRelay extends TickableModBlock {
   public static VoxelShape shape = Stream.of(
         Block.m_49796_(3.0, 0.0, 3.0, 13.0, 3.0, 13.0),
         Block.m_49796_(2.0, 2.0, 2.0, 11.0, 4.0, 5.0),
         Block.m_49796_(2.0, 2.0, 5.0, 5.0, 4.0, 14.0),
         Block.m_49796_(5.0, 2.0, 11.0, 14.0, 4.0, 14.0),
         Block.m_49796_(11.0, 2.0, 2.0, 14.0, 4.0, 11.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final Property<Boolean> LIT = BooleanProperty.m_61465_("lit");

   public BrazierRelay() {
      super(defaultProperties().m_60955_().m_60953_(b -> b.m_61143_(LIT) ? 15 : 0));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LIT});
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new BrazierRelayTile(pos, state);
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
