package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PointedIcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {
   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.f_155997_;
   public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.f_155998_;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;
   private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
   private static final int DELAY_BEFORE_FALLING = 2;
   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
   private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
   private static final float WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
   private static final float LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.05859375F;
   private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6;
   private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
   private static final int STALACTITE_MAX_DAMAGE = 40;
   private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
   private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
   private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
   private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
   private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
   private static final int MAX_GROWTH_LENGTH = 7;
   private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
   private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
   private static final VoxelShape TIP_MERGE_SHAPE = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape TIP_SHAPE_UP = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
   private static final VoxelShape TIP_SHAPE_DOWN = Block.m_49796_(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape FRUSTUM_SHAPE = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private static final VoxelShape MIDDLE_SHAPE = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape BASE_SHAPE = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
   private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

   public PointedIcicleBlock(Properties p_154025_) {
      super(p_154025_);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(TIP_DIRECTION, Direction.UP))
               .m_61124_(THICKNESS, DripstoneThickness.TIP))
            .m_61124_(WATERLOGGED, false)
      );
   }

   protected void m_7926_(Builder<Block, BlockState> p_154157_) {
      p_154157_.m_61104_(new Property[]{TIP_DIRECTION, THICKNESS, WATERLOGGED});
   }

   public boolean m_7898_(BlockState p_154137_, LevelReader p_154138_, BlockPos p_154139_) {
      return isValidPointedIciclePlacement(p_154138_, p_154139_, (Direction)p_154137_.m_61143_(TIP_DIRECTION));
   }

   public BlockState m_7417_(BlockState p_154147_, Direction p_154148_, BlockState p_154149_, LevelAccessor p_154150_, BlockPos p_154151_, BlockPos p_154152_) {
      if ((Boolean)p_154147_.m_61143_(WATERLOGGED)) {
         p_154150_.m_186469_(p_154151_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154150_));
      }

      if (p_154148_ != Direction.UP && p_154148_ != Direction.DOWN) {
         return p_154147_;
      } else {
         Direction direction = (Direction)p_154147_.m_61143_(TIP_DIRECTION);
         if (direction == Direction.DOWN && p_154150_.m_183326_().m_183582_(p_154151_, this)) {
            return p_154147_;
         } else if (p_154148_ == direction.m_122424_() && !this.m_7898_(p_154147_, p_154150_, p_154151_)) {
            if (direction == Direction.DOWN) {
               p_154150_.m_186460_(p_154151_, this, 2);
            } else {
               p_154150_.m_186460_(p_154151_, this, 1);
            }

            return p_154147_;
         } else {
            boolean flag = p_154147_.m_61143_(THICKNESS) == DripstoneThickness.TIP_MERGE;
            DripstoneThickness dripstonethickness = calculateDripstoneThickness(p_154150_, p_154151_, direction, flag);
            return (BlockState)p_154147_.m_61124_(THICKNESS, dripstonethickness);
         }
      }
   }

   public void m_5581_(Level p_154042_, BlockState p_154043_, BlockHitResult p_154044_, Projectile p_154045_) {
      BlockPos blockpos = p_154044_.m_82425_();
      if (!p_154042_.f_46443_ && p_154045_.m_142265_(p_154042_, blockpos) && p_154045_ instanceof ThrownTrident && p_154045_.m_20184_().m_82553_() > 0.6) {
         p_154042_.m_46961_(blockpos, true);
      }
   }

   public void m_142072_(Level p_154047_, BlockState p_154048_, BlockPos p_154049_, Entity p_154050_, float p_154051_) {
      if (p_154048_.m_61143_(TIP_DIRECTION) == Direction.UP && p_154048_.m_61143_(THICKNESS) == DripstoneThickness.TIP) {
         p_154050_.m_142535_(p_154051_ + 2.0F, 2.0F, DamageSource.f_146703_);
      } else {
         super.m_142072_(p_154047_, p_154048_, p_154049_, p_154050_, p_154051_);
      }
   }

   public void m_213897_(BlockState p_221865_, ServerLevel p_221866_, BlockPos p_221867_, RandomSource p_221868_) {
      if (isStalagmite(p_221865_) && !this.m_7898_(p_221865_, p_221866_, p_221867_)) {
         p_221866_.m_46961_(p_221867_, true);
      } else {
         spawnFallingStalactite(p_221865_, p_221866_, p_221867_);
      }
   }

   public void m_213898_(BlockState p_221883_, ServerLevel p_221884_, BlockPos p_221885_, RandomSource p_221886_) {
      if (p_221886_.m_188501_() < 0.011377778F && isStalactiteStartPos(p_221883_, p_221884_, p_221885_)) {
         growStalactiteOrStalagmiteIfPossible(p_221883_, p_221884_, p_221885_, p_221886_);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_154040_) {
      LevelAccessor levelaccessor = p_154040_.m_43725_();
      BlockPos blockpos = p_154040_.m_8083_();
      Direction direction = p_154040_.m_151260_().m_122424_();
      Direction direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
      if (direction1 == null) {
         return null;
      } else {
         boolean flag = !p_154040_.m_7078_();
         DripstoneThickness dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
         return dripstonethickness == null
            ? null
            : (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(TIP_DIRECTION, direction1)).m_61124_(THICKNESS, dripstonethickness))
               .m_61124_(WATERLOGGED, levelaccessor.m_6425_(blockpos).m_76152_() == Fluids.f_76193_);
      }
   }

   public FluidState m_5888_(BlockState p_154235_) {
      return p_154235_.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_154235_);
   }

   public VoxelShape m_7952_(BlockState p_154170_, BlockGetter p_154171_, BlockPos p_154172_) {
      return Shapes.m_83040_();
   }

   public VoxelShape m_5940_(BlockState p_154117_, BlockGetter p_154118_, BlockPos p_154119_, CollisionContext p_154120_) {
      DripstoneThickness dripstonethickness = (DripstoneThickness)p_154117_.m_61143_(THICKNESS);
      VoxelShape voxelshape;
      if (dripstonethickness == DripstoneThickness.TIP_MERGE) {
         voxelshape = TIP_MERGE_SHAPE;
      } else if (dripstonethickness == DripstoneThickness.TIP) {
         if (p_154117_.m_61143_(TIP_DIRECTION) == Direction.DOWN) {
            voxelshape = TIP_SHAPE_DOWN;
         } else {
            voxelshape = TIP_SHAPE_UP;
         }
      } else if (dripstonethickness == DripstoneThickness.FRUSTUM) {
         voxelshape = FRUSTUM_SHAPE;
      } else if (dripstonethickness == DripstoneThickness.MIDDLE) {
         voxelshape = MIDDLE_SHAPE;
      } else {
         voxelshape = BASE_SHAPE;
      }

      Vec3 vec3 = p_154117_.m_60824_(p_154118_, p_154119_);
      return voxelshape.m_83216_(vec3.f_82479_, 0.0, vec3.f_82481_);
   }

   public boolean m_180643_(BlockState p_181235_, BlockGetter p_181236_, BlockPos p_181237_) {
      return false;
   }

   public float m_142740_() {
      return 0.125F;
   }

   public void m_142525_(Level p_154059_, BlockPos p_154060_, FallingBlockEntity p_154061_) {
      if (!p_154061_.m_20067_()) {
         p_154059_.m_46796_(1045, p_154060_, 0);
      }
   }

   public DamageSource m_142088_() {
      return DamageSource.f_146702_;
   }

   private static void spawnFallingStalactite(BlockState p_154098_, ServerLevel p_154099_, BlockPos p_154100_) {
      MutableBlockPos blockpos$mutableblockpos = p_154100_.m_122032_();
      BlockState blockstate = p_154098_;

      while (isStalactite(blockstate)) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.m_201971_(p_154099_, blockpos$mutableblockpos, blockstate);
         if (isTip(blockstate, true)) {
            int i = Math.max(1 + p_154100_.m_123342_() - blockpos$mutableblockpos.m_123342_(), 6);
            float f = 1.0F * (float)i;
            fallingblockentity.m_149656_(f, 40);
            break;
         }

         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         blockstate = p_154099_.m_8055_(blockpos$mutableblockpos);
      }
   }

   @VisibleForTesting
   public static void growStalactiteOrStalagmiteIfPossible(BlockState p_221888_, ServerLevel p_221889_, BlockPos p_221890_, RandomSource p_221891_) {
      BlockState blockstate = p_221889_.m_8055_(p_221890_.m_6630_(1));
      if (canGrow(blockstate)) {
         BlockPos blockpos = findTip(p_221888_, p_221889_, p_221890_, 7, false);
         if (blockpos != null) {
            BlockState blockstate2 = p_221889_.m_8055_(blockpos);
            if (canDrip(blockstate2) && canTipGrow(blockstate2, p_221889_, blockpos)) {
               if (p_221891_.m_188499_()) {
                  grow(p_221889_, blockpos, Direction.DOWN);
               } else {
                  growStalagmiteBelow(p_221889_, blockpos);
               }
            }
         }
      }
   }

   private static void growStalagmiteBelow(ServerLevel p_154033_, BlockPos p_154034_) {
      MutableBlockPos blockpos$mutableblockpos = p_154034_.m_122032_();

      for (int i = 0; i < 10; i++) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         BlockState blockstate = p_154033_.m_8055_(blockpos$mutableblockpos);
         if (!blockstate.m_60819_().m_76178_()) {
            return;
         }

         if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, p_154033_, blockpos$mutableblockpos)) {
            grow(p_154033_, blockpos$mutableblockpos, Direction.UP);
            return;
         }

         if (isValidPointedIciclePlacement(p_154033_, blockpos$mutableblockpos, Direction.UP) && !p_154033_.m_46801_(blockpos$mutableblockpos.m_7495_())) {
            grow(p_154033_, blockpos$mutableblockpos.m_7495_(), Direction.UP);
            return;
         }

         if (!canDripThrough(p_154033_, blockpos$mutableblockpos, blockstate)) {
            return;
         }
      }
   }

   private static void grow(ServerLevel p_154036_, BlockPos p_154037_, Direction p_154038_) {
      BlockPos blockpos = p_154037_.m_121945_(p_154038_);
      BlockState blockstate = p_154036_.m_8055_(blockpos);
      if (isUnmergedTipWithDirection(blockstate, p_154038_.m_122424_())) {
         createMergedTips(blockstate, p_154036_, blockpos);
      } else if (blockstate.m_60795_() || blockstate.m_60713_(Blocks.f_49990_)) {
         createDripstone(p_154036_, blockpos, p_154038_, DripstoneThickness.TIP);
      }
   }

   private static void createDripstone(LevelAccessor p_154088_, BlockPos p_154089_, Direction p_154090_, DripstoneThickness p_154091_) {
      BlockState blockstate = (BlockState)((BlockState)((BlockState)((Block)ModBlocks.POINTED_ICICLE.get()).m_49966_().m_61124_(TIP_DIRECTION, p_154090_))
            .m_61124_(THICKNESS, p_154091_))
         .m_61124_(WATERLOGGED, p_154088_.m_6425_(p_154089_).m_76152_() == Fluids.f_76193_);
      p_154088_.m_7731_(p_154089_, blockstate, 3);
   }

   private static void createMergedTips(BlockState p_154231_, LevelAccessor p_154232_, BlockPos p_154233_) {
      BlockPos blockpos;
      BlockPos blockpos1;
      if (p_154231_.m_61143_(TIP_DIRECTION) == Direction.UP) {
         blockpos1 = p_154233_;
         blockpos = p_154233_.m_7494_();
      } else {
         blockpos = p_154233_;
         blockpos1 = p_154233_.m_7495_();
      }

      createDripstone(p_154232_, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
      createDripstone(p_154232_, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
   }

   @Nullable
   private static BlockPos findTip(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_) {
      if (isTip(p_154131_, p_154135_)) {
         return p_154133_;
      } else {
         Direction direction = (Direction)p_154131_.m_61143_(TIP_DIRECTION);
         BiPredicate<BlockPos, BlockState> bipredicate = (p_202023_, p_202024_) -> p_202024_.m_60713_((Block)ModBlocks.POINTED_ICICLE.get())
               && p_202024_.m_61143_(TIP_DIRECTION) == direction;
         return findBlockVertical(p_154132_, p_154133_, direction.m_122421_(), bipredicate, p_154168_ -> isTip(p_154168_, p_154135_), p_154134_)
            .orElse((BlockPos)null);
      }
   }

   @Nullable
   private static Direction calculateTipDirection(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_) {
      Direction direction;
      if (isValidPointedIciclePlacement(p_154191_, p_154192_, p_154193_)) {
         direction = p_154193_;
      } else {
         if (!isValidPointedIciclePlacement(p_154191_, p_154192_, p_154193_.m_122424_())) {
            return null;
         }

         direction = p_154193_.m_122424_();
      }

      return direction;
   }

   private static DripstoneThickness calculateDripstoneThickness(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_) {
      Direction direction = p_154095_.m_122424_();
      BlockState blockstate = p_154093_.m_8055_(p_154094_.m_121945_(p_154095_));
      if (!isPointedDripstoneWithDirection(blockstate, direction)) {
         if (!isPointedDripstoneWithDirection(blockstate, p_154095_)) {
            return DripstoneThickness.TIP;
         } else {
            DripstoneThickness dripstonethickness = (DripstoneThickness)blockstate.m_61143_(THICKNESS);
            if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
               BlockState blockstate1 = p_154093_.m_8055_(p_154094_.m_121945_(direction));
               return !isPointedDripstoneWithDirection(blockstate1, p_154095_) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
               return DripstoneThickness.FRUSTUM;
            }
         }
      } else {
         return !p_154096_ && blockstate.m_61143_(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
      }
   }

   public static boolean canDrip(BlockState p_154239_) {
      return isStalactite(p_154239_) && p_154239_.m_61143_(THICKNESS) == DripstoneThickness.TIP && !(Boolean)p_154239_.m_61143_(WATERLOGGED);
   }

   private static boolean canTipGrow(BlockState p_154195_, ServerLevel p_154196_, BlockPos p_154197_) {
      Direction direction = (Direction)p_154195_.m_61143_(TIP_DIRECTION);
      BlockPos blockpos = p_154197_.m_121945_(direction);
      BlockState blockstate = p_154196_.m_8055_(blockpos);
      if (!blockstate.m_60819_().m_76178_()) {
         return false;
      } else {
         return blockstate.m_60795_() ? true : isUnmergedTipWithDirection(blockstate, direction.m_122424_());
      }
   }

   private static Optional<BlockPos> findRootBlock(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_) {
      Direction direction = (Direction)p_154069_.m_61143_(TIP_DIRECTION);
      BiPredicate<BlockPos, BlockState> bipredicate = (p_202015_, p_202016_) -> p_202016_.m_60713_((Block)ModBlocks.POINTED_ICICLE.get())
            && p_202016_.m_61143_(TIP_DIRECTION) == direction;
      return findBlockVertical(
         p_154067_,
         p_154068_,
         direction.m_122424_().m_122421_(),
         bipredicate,
         p_154245_ -> !p_154245_.m_60713_((Block)ModBlocks.POINTED_ICICLE.get()),
         p_154070_
      );
   }

   private static boolean isValidPointedIciclePlacement(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_) {
      BlockPos blockpos = p_154223_.m_121945_(p_154224_.m_122424_());
      BlockState blockstate = p_154222_.m_8055_(blockpos);
      return blockstate.m_60783_(p_154222_, blockpos, p_154224_) || isPointedDripstoneWithDirection(blockstate, p_154224_);
   }

   private static boolean isTip(BlockState p_154154_, boolean p_154155_) {
      if (!p_154154_.m_60713_((Block)ModBlocks.POINTED_ICICLE.get())) {
         return false;
      } else {
         DripstoneThickness dripstonethickness = (DripstoneThickness)p_154154_.m_61143_(THICKNESS);
         return dripstonethickness == DripstoneThickness.TIP || p_154155_ && dripstonethickness == DripstoneThickness.TIP_MERGE;
      }
   }

   private static boolean isUnmergedTipWithDirection(BlockState p_154144_, Direction p_154145_) {
      return isTip(p_154144_, false) && p_154144_.m_61143_(TIP_DIRECTION) == p_154145_;
   }

   private static boolean isStalactite(BlockState p_154241_) {
      return isPointedDripstoneWithDirection(p_154241_, Direction.DOWN);
   }

   private static boolean isStalagmite(BlockState p_154243_) {
      return isPointedDripstoneWithDirection(p_154243_, Direction.UP);
   }

   private static boolean isStalactiteStartPos(BlockState p_154204_, LevelReader p_154205_, BlockPos p_154206_) {
      return isStalactite(p_154204_) && !p_154205_.m_8055_(p_154206_.m_7494_()).m_60713_((Block)ModBlocks.POINTED_ICICLE.get());
   }

   public boolean m_7357_(BlockState p_154112_, BlockGetter p_154113_, BlockPos p_154114_, PathComputationType p_154115_) {
      return false;
   }

   private static boolean isPointedDripstoneWithDirection(BlockState p_154208_, Direction p_154209_) {
      return p_154208_.m_60713_((Block)ModBlocks.POINTED_ICICLE.get()) && p_154208_.m_61143_(TIP_DIRECTION) == p_154209_;
   }

   private static boolean canGrow(BlockState p_154141_) {
      return p_154141_.m_204336_(BlockTags.f_13047_);
   }

   private static Optional<BlockPos> findBlockVertical(
      LevelAccessor p_202007_,
      BlockPos p_202008_,
      AxisDirection p_202009_,
      BiPredicate<BlockPos, BlockState> p_202010_,
      Predicate<BlockState> p_202011_,
      int p_202012_
   ) {
      Direction direction = Direction.m_122390_(p_202009_, Axis.Y);
      MutableBlockPos blockpos$mutableblockpos = p_202008_.m_122032_();

      for (int i = 1; i < p_202012_; i++) {
         blockpos$mutableblockpos.m_122173_(direction);
         BlockState blockstate = p_202007_.m_8055_(blockpos$mutableblockpos);
         if (p_202011_.test(blockstate)) {
            return Optional.of(blockpos$mutableblockpos.m_7949_());
         }

         if (p_202007_.m_151562_(blockpos$mutableblockpos.m_123342_()) || !p_202010_.test(blockpos$mutableblockpos, blockstate)) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }

   private static boolean canDripThrough(BlockGetter p_202018_, BlockPos p_202019_, BlockState p_202020_) {
      if (p_202020_.m_60795_()) {
         return true;
      } else if (p_202020_.m_60804_(p_202018_, p_202019_)) {
         return false;
      } else if (!p_202020_.m_60819_().m_76178_()) {
         return false;
      } else {
         VoxelShape voxelshape = p_202020_.m_60812_(p_202018_, p_202019_);
         return !Shapes.m_83157_(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.f_82689_);
      }
   }
}
