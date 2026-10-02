package com.bobmowzie.mowziesmobs.server.block;

import com.bobmowzie.mowziesmobs.server.block.entity.BlockEntityHandler;
import com.bobmowzie.mowziesmobs.server.block.entity.GongBlockEntity;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GongBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;
   private static final VoxelShape NORTH_SOUTH_SHAPE = Block.m_49796_(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
   private static final VoxelShape EAST_WEST_SHAPE = Block.m_49796_(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);

   public GongBlock(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(POWERED, Boolean.FALSE));
   }

   public void m_6861_(BlockState p_49729_, Level p_49730_, BlockPos p_49731_, Block p_49732_, BlockPos p_49733_, boolean p_49734_) {
      boolean flag = p_49730_.m_46753_(p_49731_);
      if (flag != (Boolean)p_49729_.m_61143_(POWERED)) {
         if (flag) {
            this.attemptToRing(p_49730_, p_49731_, (Direction)null);
         }

         p_49730_.m_7731_(p_49731_, (BlockState)p_49729_.m_61124_(POWERED, flag), 3);
      }
   }

   public BlockState m_6843_(BlockState state, Rotation rotation) {
      return (BlockState)state.m_61124_(FACING, rotation.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirror) {
      return state.m_60717_(mirror.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public void m_5581_(Level p_49708_, BlockState p_49709_, BlockHitResult p_49710_, Projectile p_49711_) {
      Entity entity = p_49711_.m_37282_();
      Player player = entity instanceof Player ? (Player)entity : null;
      this.onHit(p_49708_, p_49709_, p_49710_, player, true);
   }

   public InteractionResult m_6227_(BlockState p_49722_, Level p_49723_, BlockPos p_49724_, Player p_49725_, InteractionHand p_49726_, BlockHitResult p_49727_) {
      return this.onHit(p_49723_, p_49722_, p_49727_, p_49725_, true) ? InteractionResult.m_19078_(p_49723_.f_46443_) : InteractionResult.PASS;
   }

   public boolean onHit(Level p_49702_, BlockState p_49703_, BlockHitResult p_49704_, @Nullable Player p_49705_, boolean p_49706_) {
      Direction direction = p_49704_.m_82434_();
      BlockPos blockpos = p_49704_.m_82425_();
      boolean flag = !p_49706_ || this.isProperHit(p_49703_, direction, p_49704_.m_82450_().f_82480_ - (double)blockpos.m_123342_());
      if (flag) {
         this.attemptToRing(p_49705_, p_49702_, blockpos, direction);
         return true;
      } else {
         return false;
      }
   }

   private boolean isProperHit(BlockState p_49740_, Direction p_49741_, double p_49742_) {
      if (p_49741_.m_122434_() != Axis.Y && !(p_49742_ > 0.8124F)) {
         Direction direction = (Direction)p_49740_.m_61143_(FACING);
         return direction.m_122434_() == p_49741_.m_122434_();
      } else {
         return false;
      }
   }

   public boolean attemptToRing(Level p_49713_, BlockPos p_49714_, @Nullable Direction p_49715_) {
      return this.attemptToRing((Entity)null, p_49713_, p_49714_, p_49715_);
   }

   public boolean attemptToRing(@Nullable Entity p_152189_, Level p_152190_, BlockPos p_152191_, @Nullable Direction p_152192_) {
      BlockEntity blockentity = p_152190_.m_7702_(p_152191_);
      if (!p_152190_.f_46443_ && blockentity instanceof GongBlockEntity) {
         if (p_152192_ == null) {
            p_152192_ = (Direction)p_152190_.m_8055_(p_152191_).m_61143_(FACING);
         }

         ((GongBlockEntity)blockentity).onHit(p_152192_);
         p_152190_.m_5594_((Player)null, p_152191_, (SoundEvent)MMSounds.BLOCK_GONG.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
         p_152190_.m_142346_(p_152189_, GameEvent.f_157792_, p_152191_);
         return true;
      } else {
         return false;
      }
   }

   protected void m_7926_(Builder<Block, BlockState> p_49751_) {
      p_49751_.m_61104_(new Property[]{FACING, POWERED});
   }

   @org.jetbrains.annotations.Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new GongBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152194_, BlockState p_152195_, BlockEntityType<T> p_152196_) {
      return m_152132_(p_152196_, (BlockEntityType)BlockEntityHandler.GONG_BLOCK_ENTITY.get(), GongBlockEntity::tick);
   }

   public VoxelShape m_5939_(BlockState p_49760_, BlockGetter p_49761_, BlockPos p_49762_, CollisionContext p_49763_) {
      return this.getVoxelShape(p_49760_);
   }

   public VoxelShape m_5940_(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
      return this.getVoxelShape(p_49755_);
   }

   public RenderShape m_7514_(BlockState blockState) {
      return RenderShape.MODEL;
   }

   private VoxelShape getVoxelShape(BlockState p_49767_) {
      Direction direction = (Direction)p_49767_.m_61143_(FACING);
      return direction != Direction.NORTH && direction != Direction.SOUTH ? EAST_WEST_SHAPE : NORTH_SOUTH_SHAPE;
   }

   public boolean m_7357_(BlockState p_49717_, BlockGetter p_49718_, BlockPos p_49719_, PathComputationType p_49720_) {
      return false;
   }

   public PushReaction m_5537_(BlockState p_49765_) {
      return PushReaction.DESTROY;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      Direction direction = context.m_43719_();
      BlockPos blockpos = context.m_8083_();
      Axis direction$axis = direction.m_122434_();
      if (direction$axis == Axis.Y) {
         BlockState blockstate = (BlockState)this.m_49966_().m_61124_(FACING, context.m_8125_());
         if (blockstate.m_60710_(context.m_43725_(), blockpos)) {
            return blockstate;
         }
      } else {
         BlockState blockstate1 = (BlockState)this.m_49966_().m_61124_(FACING, direction.m_122424_());
         if (blockstate1.m_60710_(context.m_43725_(), context.m_8083_())) {
            return blockstate1;
         }
      }

      return null;
   }

   public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
      super.m_6402_(level, pos, state, entity, itemStack);
      if (!level.f_46443_) {
         for (int i = 0; i < 3; i++) {
            BlockPos abovePos = pos.m_6630_(i);
            BlockPos blockpos1 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
            BlockPos blockpos3 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
            BlockState defaultGongPart = ((Block)BlockHandler.GONG_PART.get()).m_49966_();
            level.m_7731_(
               blockpos1,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(GongBlock.GongPartBlock.PART, GongBlock.GongPart.SIDE_LEFT))
                  .m_61124_(GongBlock.GongPartBlock.Y_OFFSET, i),
               3
            );
            level.m_7731_(
               blockpos3,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(GongBlock.GongPartBlock.PART, GongBlock.GongPart.SIDE_RIGHT))
                  .m_61124_(GongBlock.GongPartBlock.Y_OFFSET, i),
               3
            );
            if (abovePos != pos) {
               level.m_7731_(
                  abovePos,
                  (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                        .m_61124_(GongBlock.GongPartBlock.PART, GongBlock.GongPart.CENTER))
                     .m_61124_(GongBlock.GongPartBlock.Y_OFFSET, i),
                  3
               );
            }

            level.m_6289_(abovePos, Blocks.f_50016_);
            state.m_60701_(level, abovePos, 3);
         }
      }
   }

   public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.f_46443_ && player.m_7500_()) {
         for (int i = 0; i < 2; i++) {
            BlockPos abovePos = pos.m_6630_(i);
            BlockPos blockpos1 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
            BlockPos blockpos3 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
            BlockPos[] toBreakPoses = new BlockPos[]{blockpos1, abovePos, blockpos3};

            for (BlockPos toBreakPos : toBreakPoses) {
               BlockState blockstate = level.m_8055_(toBreakPos);
               if (blockstate.m_60713_((Block)BlockHandler.GONG_PART.get())) {
                  level.m_7731_(toBreakPos, Blocks.f_50016_.m_49966_(), 35);
                  level.m_5898_(player, 2001, toBreakPos, Block.m_49956_(blockstate));
               }
            }
         }
      }

      super.m_5707_(level, pos, state, player);
   }

   public static enum GongPart implements StringRepresentable {
      SIDE_LEFT("side_left"),
      SIDE_RIGHT("side_right"),
      CENTER("center");

      private final String name;

      private GongPart(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public String m_7912_() {
         return this.name;
      }
   }

   public static class GongPartBlock extends HorizontalDirectionalBlock {
      public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
      public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;
      public static final EnumProperty<GongBlock.GongPart> PART = EnumProperty.m_61587_("gong_part", GongBlock.GongPart.class);
      public static final IntegerProperty Y_OFFSET = IntegerProperty.m_61631_("y_offset", 0, 2);

      protected GongPartBlock(Properties properties) {
         super(properties);
         this.m_49959_(
            (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH))
                     .m_61124_(POWERED, Boolean.FALSE))
                  .m_61124_(PART, GongBlock.GongPart.CENTER))
               .m_61124_(Y_OFFSET, 0)
         );
      }

      protected void m_7926_(Builder<Block, BlockState> p_49751_) {
         p_49751_.m_61104_(new Property[]{FACING, POWERED, PART, Y_OFFSET});
      }

      public void m_5581_(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
         BlockPos pos = hitResult.m_82425_();
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         if (baseState.m_60713_((Block)BlockHandler.GONG.get())) {
            BlockHitResult baseHitResult = new BlockHitResult(
               hitResult.m_82450_()
                  .m_82520_(
                     (double)(basePos.m_123341_() - pos.m_123341_()),
                     (double)(basePos.m_123342_() - pos.m_123342_()),
                     (double)(basePos.m_123343_() - pos.m_123343_())
                  ),
               hitResult.m_82434_(),
               basePos,
               hitResult.m_82436_()
            );
            baseState.m_60734_().m_5581_(level, baseState, baseHitResult, projectile);
         }
      }

      public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         if (baseState.m_60713_((Block)BlockHandler.GONG.get())) {
            BlockHitResult baseHitResult = new BlockHitResult(
               hitResult.m_82450_()
                  .m_82520_(
                     (double)(basePos.m_123341_() - pos.m_123341_()),
                     (double)(basePos.m_123342_() - pos.m_123342_()),
                     (double)(basePos.m_123343_() - pos.m_123343_())
                  ),
               hitResult.m_82434_(),
               basePos,
               hitResult.m_82436_()
            );
            return baseState.m_60734_().m_6227_(baseState, level, pos, player, hand, baseHitResult);
         } else {
            return super.m_6227_(state, level, pos, player, hand, hitResult);
         }
      }

      private BlockPos getBasePos(BlockState state, BlockPos pos) {
         BlockPos toReturn = pos.m_6625_((Integer)state.m_61143_(Y_OFFSET));
         if (state.m_61143_(PART) == GongBlock.GongPart.SIDE_LEFT) {
            toReturn = toReturn.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
         } else if (state.m_61143_(PART) == GongBlock.GongPart.SIDE_RIGHT) {
            toReturn = toReturn.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
         }

         return toReturn;
      }

      public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         if (baseState.m_60713_((Block)BlockHandler.GONG.get())) {
            level.m_7731_(basePos, Blocks.f_50016_.m_49966_(), 35);
            level.m_5898_(player, 2001, basePos, Block.m_49956_(state));
         }
      }

      public BlockState m_7417_(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         return !baseState.m_60713_((Block)BlockHandler.GONG.get()) ? Blocks.f_50016_.m_49966_() : super.m_7417_(state, direction, state1, level, pos, pos1);
      }

      public VoxelShape m_5939_(BlockState p_49760_, BlockGetter p_49761_, BlockPos p_49762_, CollisionContext p_49763_) {
         return this.getVoxelShape(p_49760_);
      }

      public VoxelShape m_5940_(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
         return this.getVoxelShape(p_49755_);
      }

      public RenderShape m_7514_(BlockState blockState) {
         return RenderShape.MODEL;
      }

      private VoxelShape getVoxelShape(BlockState p_49767_) {
         Direction direction = (Direction)p_49767_.m_61143_(FACING);
         return direction != Direction.NORTH && direction != Direction.SOUTH ? GongBlock.EAST_WEST_SHAPE : GongBlock.NORTH_SOUTH_SHAPE;
      }

      public boolean m_7357_(BlockState p_49717_, BlockGetter p_49718_, BlockPos p_49719_, PathComputationType p_49720_) {
         return false;
      }

      public PushReaction m_5537_(BlockState p_49765_) {
         return PushReaction.DESTROY;
      }

      public Item m_5456_() {
         return ((Block)BlockHandler.GONG.get()).m_5456_();
      }
   }
}
