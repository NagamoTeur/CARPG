package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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

public class EMP_Block extends BaseEntityBlock {
   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.f_155997_;
   public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;
   public static final BooleanProperty OVERLOAD = BooleanProperty.m_61465_("overload");

   public EMP_Block() {
      super(
         Properties.m_60939_(Material.f_76281_)
            .m_60955_()
            .m_60953_(block -> 7)
            .m_222994_()
            .m_60991_((block, world, pos) -> true)
            .m_60913_(-1.0F, 3600000.0F)
            .m_60918_(SoundType.f_56743_)
      );
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(TIP_DIRECTION, Direction.UP)).m_61124_(POWERED, false))
            .m_61124_(OVERLOAD, false)
      );
   }

   public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!worldIn.f_46443_) {
         this.updateState(state, worldIn, pos, blockIn);
      }
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if (!worldIn.f_46443_) {
         this.updateState(state, worldIn, pos, state.m_60734_());
      }
   }

   public void updateState(BlockState state, Level worldIn, BlockPos pos, Block blockIn) {
      boolean flag = (Boolean)state.m_61143_(POWERED);
      boolean flag1 = worldIn.m_46753_(pos);
      if (flag1 != flag) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(POWERED, flag1), 3);
         worldIn.m_46672_(pos.m_7495_(), this);
      }
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(TIP_DIRECTION, context.m_151260_().m_122424_()))
            .m_61124_(POWERED, context.m_43725_().m_46753_(context.m_8083_())))
         .m_61124_(OVERLOAD, false);
   }

   public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
      Direction direction = Direction.m_235672_(rand);
      double d0 = 0.5625;
      double d = (double)(rand.m_188501_() - 0.5F);
      if (direction != Direction.UP) {
         BlockPos blockpos = pos.m_121945_(direction);
         BlockState blockstate = worldIn.m_8055_(blockpos);
         if (!stateIn.m_60815_() || !blockstate.m_60783_(worldIn, blockpos, direction.m_122424_())) {
            Axis direction$axis = direction.m_122434_();
            double d1 = direction$axis == Axis.X ? 0.5 + d0 * (double)direction.m_122429_() : (double)rand.m_188501_();
            double d3 = direction$axis == Axis.Z ? 0.5 + d0 * (double)direction.m_122431_() : (double)rand.m_188501_();
            if ((Boolean)stateIn.m_61143_(OVERLOAD)) {
               for (int i1 = 0; i1 < 20; i1++) {
                  worldIn.m_7106_(
                     DustParticleOptions.f_123656_, (double)pos.m_123341_() + d1, (double)pos.m_123342_() + 0.75, (double)pos.m_123343_() + d3, 0.0, 0.0, 0.0
                  );
               }
            } else {
               worldIn.m_7106_(
                  new LightningParticle.OrbData(255, 51, 0),
                  (double)pos.m_123341_() + 0.5,
                  (double)pos.m_123342_() + 0.75,
                  (double)pos.m_123343_() + 0.5,
                  d * 2.0,
                  d,
                  d * 2.0
               );
            }
         }
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new EMP_Block_Entity(pos, state);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{TIP_DIRECTION, POWERED, OVERLOAD});
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.EMP.get(), EMP_Block_Entity::commonTick);
   }
}
