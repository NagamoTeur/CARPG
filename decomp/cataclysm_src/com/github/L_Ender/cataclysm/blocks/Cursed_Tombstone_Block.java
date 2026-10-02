package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Cursed_tombstone_Entity;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cursed_Tombstone_Block extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty LIT = BlockStateProperties.f_61443_;
   public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;
   private static final VoxelShape X_BASE = Block.m_49796_(5.0, 0.0, 0.0, 11.0, 2.0, 16.0);
   private static final VoxelShape Z_BASE = Block.m_49796_(0.0, 0.0, 5.0, 16.0, 2.0, 11.0);
   private static final VoxelShape X_MID = Block.m_49796_(6.0, 2.0, 1.0, 10.0, 24.0, 15.0);
   private static final VoxelShape Z_MID = Block.m_49796_(1.0, 2.0, 6.0, 15.0, 24.0, 10.0);
   private static final VoxelShape X_AXIS_AABB = Shapes.m_83110_(X_BASE, X_MID);
   private static final VoxelShape Z_AXIS_AABB = Shapes.m_83110_(Z_BASE, Z_MID);

   public Cursed_Tombstone_Block() {
      super(
         Properties.m_60939_(Material.f_76278_)
            .m_155949_(MaterialColor.f_76404_)
            .m_60988_()
            .m_60913_(-1.0F, 3600000.0F)
            .m_222994_()
            .m_60918_(SoundType.f_56742_)
      );
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(LIT, false))
            .m_61124_(POWERED, false)
      );
   }

   public BlockState m_5573_(BlockPlaceContext p_48689_) {
      return (BlockState)this.m_49966_().m_61124_(FACING, p_48689_.m_8125_().m_122424_());
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if ((Boolean)state.m_61143_(POWERED)) {
         if (!(Boolean)state.m_61143_(LIT)) {
            state = (BlockState)state.m_61124_(LIT, true);
            worldIn.m_7731_(pos, state, 10);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.FAIL;
         }
      } else {
         player.m_5661_(Component.m_237115_("block.cataclysm.cursed_tombstone.message"), true);
         return InteractionResult.FAIL;
      }
   }

   protected void m_7926_(Builder<Block, BlockState> p_48814_) {
      p_48814_.m_61104_(new Property[]{FACING, LIT, POWERED});
   }

   public VoxelShape m_5940_(BlockState p_48816_, BlockGetter p_48817_, BlockPos p_48818_, CollisionContext p_48819_) {
      Direction direction = (Direction)p_48816_.m_61143_(FACING);
      return direction.m_122434_() == Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new Cursed_tombstone_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.CURSED_TOMBSTONE.get(), Cursed_tombstone_Entity::commonTick);
   }
}
