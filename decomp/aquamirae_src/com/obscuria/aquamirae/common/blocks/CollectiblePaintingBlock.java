package com.obscuria.aquamirae.common.blocks;

import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import org.jetbrains.annotations.NotNull;

public class CollectiblePaintingBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public CollectiblePaintingBlock() {
      super(
         Properties.m_60939_(Material.f_76320_)
            .m_60918_(
               new ForgeSoundType(
                  1.0F,
                  1.0F,
                  () -> SoundEvents.f_12175_,
                  () -> SoundEvents.f_12591_,
                  () -> SoundEvents.f_12176_,
                  () -> SoundEvents.f_12634_,
                  () -> SoundEvents.f_12633_
               )
            )
            .m_60978_(1.0F)
            .m_60999_()
            .m_60910_()
            .m_60955_()
            .m_60924_((bs, br, bp) -> false)
      );
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(WATERLOGGED, false));
   }

   public void m_5871_(@NotNull ItemStack stack, BlockGetter world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
      super.m_5871_(stack, world, list, flag);
      list.add(Component.m_237113_(ChatFormatting.GRAY + Component.m_237115_(this.m_7705_() + "_desc").getString()));
   }

   public boolean m_7420_(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return state.m_60819_().m_76178_();
   }

   public int m_7753_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      return 0;
   }

   @NotNull
   public VoxelShape m_5940_(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return switch ((Direction)state.m_61143_(FACING)) {
         case NORTH -> m_49796_(-8.0, 0.0, 15.0, 24.0, 16.0, 16.0);
         case EAST -> m_49796_(0.0, 0.0, -8.0, 1.0, 16.0, 24.0);
         case WEST -> m_49796_(15.0, 0.0, -8.0, 16.0, 16.0, 24.0);
         default -> m_49796_(-8.0, 0.0, 0.0, 24.0, 16.0, 1.0);
      };
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, WATERLOGGED});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      boolean flag = context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_;
      return context.m_43719_().m_122434_() == Axis.Y
         ? (BlockState)((BlockState)this.m_49966_().m_61124_(FACING, Direction.NORTH)).m_61124_(WATERLOGGED, flag)
         : (BlockState)((BlockState)this.m_49966_().m_61124_(FACING, context.m_43719_())).m_61124_(WATERLOGGED, flag);
   }

   @NotNull
   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   @NotNull
   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   @NotNull
   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   @NotNull
   public BlockState m_7417_(
      BlockState state,
      @NotNull Direction facing,
      @NotNull BlockState facingState,
      @NotNull LevelAccessor world,
      @NotNull BlockPos currentPos,
      @NotNull BlockPos facingPos
   ) {
      if ((Boolean)state.m_61143_(WATERLOGGED)) {
         world.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(world));
      }

      return super.m_7417_(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
      return true;
   }

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return Collections.singletonList(new ItemStack(this, 1));
   }
}
