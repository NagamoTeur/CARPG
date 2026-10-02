package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Abyssal_Egg_Block extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;
   public static final int MAX_HATCH_LEVEL = 2;
   public static final IntegerProperty HATCH = BlockStateProperties.f_61416_;
   private static final int REGULAR_HATCH_TIME_TICKS = 12000;
   private static final int RANDOM_HATCH_OFFSET_TICKS = 300;
   private static final VoxelShape SHAPE = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public Abyssal_Egg_Block() {
      super(
         Properties.m_60939_(Material.f_76286_)
            .m_60955_()
            .m_60953_(block -> 1)
            .m_155949_(MaterialColor.f_76365_)
            .m_60991_((block, world, pos) -> true)
            .m_60913_(3.0F, 9.0F)
            .m_60918_(SoundType.f_56743_)
      );
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(HATCH, 0)).m_61124_(WATERLOGGED, Boolean.FALSE));
   }

   protected void m_7926_(Builder<Block, BlockState> p_277441_) {
      p_277441_.m_61104_(new Property[]{HATCH, WATERLOGGED});
   }

   public VoxelShape m_5940_(BlockState p_277872_, BlockGetter p_278090_, BlockPos p_277364_, CollisionContext p_278016_) {
      return SHAPE;
   }

   public FluidState m_5888_(BlockState p_51581_) {
      return p_51581_.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_51581_);
   }

   public BlockState m_5573_(BlockPlaceContext p_48781_) {
      FluidState fluidstate = p_48781_.m_43725_().m_6425_(p_48781_.m_8083_());
      return (BlockState)this.m_49966_().m_61124_(WATERLOGGED, fluidstate.m_76152_() == Fluids.f_76193_);
   }

   public BlockState m_7417_(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if ((Boolean)p_51555_.m_61143_(WATERLOGGED)) {
         p_51558_.m_186469_(p_51559_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51558_));
      }

      return super.m_7417_(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   public int getHatchLevel(BlockState p_279125_) {
      return (Integer)p_279125_.m_61143_(HATCH);
   }

   private boolean isReadyToHatch(BlockState p_278021_) {
      return this.getHatchLevel(p_278021_) == 2;
   }

   public void m_213897_(BlockState p_277841_, ServerLevel p_277739_, BlockPos p_277692_, RandomSource p_277973_) {
      if (!this.isReadyToHatch(p_277841_)) {
         p_277739_.m_5594_((Player)null, p_277692_, SoundEvents.f_12534_, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.m_188501_() * 0.2F);
         p_277739_.m_7731_(p_277692_, (BlockState)p_277841_.m_61124_(HATCH, this.getHatchLevel(p_277841_) + 1), 2);
      } else {
         p_277739_.m_5594_((Player)null, p_277692_, SoundEvents.f_12535_, SoundSource.BLOCKS, 0.7F, 0.9F + p_277973_.m_188501_() * 0.2F);
         p_277739_.m_46961_(p_277692_, false);
         The_Baby_Leviathan_Entity levia = (The_Baby_Leviathan_Entity)((EntityType)ModEntities.THE_BABY_LEVIATHAN.get()).m_20615_(p_277739_);
         if (levia != null) {
            levia.m_7678_(
               (double)p_277692_.m_123341_() + 0.5,
               (double)p_277692_.m_123342_() + 0.5,
               (double)p_277692_.m_123343_() + 0.5,
               Mth.m_14177_(p_277739_.f_46441_.m_188501_() * 360.0F),
               0.0F
            );
            p_277739_.m_7967_(levia);
         }
      }
   }

   public void m_6807_(BlockState p_277964_, Level p_277827_, BlockPos p_277526_, BlockState p_277618_, boolean p_277819_) {
      int j = 4000;
      p_277827_.m_220407_(GameEvent.f_157797_, p_277526_, Context.m_223722_(p_277964_));
      p_277827_.m_186460_(p_277526_, this, j + p_277827_.f_46441_.m_188503_(300));
   }

   public boolean m_7357_(BlockState p_279414_, BlockGetter p_279243_, BlockPos p_279294_, PathComputationType p_279299_) {
      return false;
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new Abyssal_Egg_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.ABYSSAL_EGG.get(), Abyssal_Egg_Block_Entity::commonTick);
   }
}
