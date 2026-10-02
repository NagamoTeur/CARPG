package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.common.entities.GoldenMoth;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class JarBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public JarBlock() {
      super(
         Properties.m_60944_(Material.f_76275_, MaterialColor.f_76366_)
            .m_60918_(SoundType.f_56744_)
            .m_60913_(1.0F, 10.0F)
            .m_60953_(s -> 8)
            .m_60999_()
            .m_60955_()
            .m_60924_((bs, br, bp) -> false)
            .m_222994_()
      );
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(WATERLOGGED, false));
   }

   public boolean m_7420_(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return state.m_60819_().m_76178_();
   }

   public int m_7753_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      return 0;
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return Shapes.m_83113_(
         Shapes.m_83110_(m_49796_(3.0, 0.0, 3.0, 13.0, 12.0, 13.0), m_49796_(4.0, 12.0, 4.0, 12.0, 14.0, 12.0)),
         m_49796_(3.1, 0.1, 3.1, 12.9, 10.5, 12.9),
         BooleanOp.f_82685_
      );
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{WATERLOGGED});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      boolean flag = context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_;
      return (BlockState)this.m_49966_().m_61124_(WATERLOGGED, flag);
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

   public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState blockstate, LivingEntity entity, @NotNull ItemStack itemstack) {
      super.m_6402_(world, pos, blockstate, entity, itemstack);
      if (world instanceof ServerLevel server) {
         Mob moth = new GoldenMoth((EntityType<GoldenMoth>)AquamiraeEntities.GOLDEN_MOTH.get(), server);
         moth.m_7678_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.3, (double)pos.m_123343_() + 0.5, 0.0F, 0.0F);
         moth.m_21530_();
         moth.m_6518_(server, world.m_6436_(moth.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
         world.m_7967_(moth);
      }
   }
}
