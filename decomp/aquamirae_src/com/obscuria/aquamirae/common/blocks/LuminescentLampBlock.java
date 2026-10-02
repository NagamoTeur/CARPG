package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class LuminescentLampBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public LuminescentLampBlock() {
      super(
         Properties.m_60939_(Material.f_76320_).m_60918_(SoundType.f_56736_).m_60913_(1.4F, 6.0F).m_60953_(s -> 15).m_60955_().m_60924_((bs, br, bp) -> false)
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
      return m_49796_(5.0, 0.0, 5.0, 11.0, 32.0, 11.0);
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

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return Collections.singletonList(new ItemStack(this));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      super.m_214162_(blockstate, world, pos, random);
      if ((double)random.m_188501_() < 0.2) {
         world.m_7106_(
            (ParticleOptions)AquamiraeParticleTypes.GHOST_SHINE.get(),
            (double)pos.m_123341_() + 0.5 + ((double)random.m_188501_() - 0.5) * 0.05,
            (double)pos.m_123342_() + 1.6 + ((double)random.m_188501_() - 0.5) * 0.05,
            (double)pos.m_123343_() + 0.5 + ((double)random.m_188501_() - 0.5) * 0.05,
            0.0,
            0.0,
            0.0
         );
      }
   }
}
