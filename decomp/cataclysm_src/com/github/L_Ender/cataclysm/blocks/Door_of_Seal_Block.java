package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Door_of_Seal_Block extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty OPEN = BlockStateProperties.f_61446_;
   public static final BooleanProperty LIT = BlockStateProperties.f_61443_;
   private static final VoxelShape CLOSED_SHAPE = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

   public Door_of_Seal_Block() {
      super(
         Properties.m_60939_(Material.f_76279_)
            .m_155949_(MaterialColor.f_76404_)
            .m_60955_()
            .m_60988_()
            .m_60913_(-1.0F, 3600000.0F)
            .m_222994_()
            .m_60999_()
            .m_60918_(SoundType.f_56743_)
      );
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(LIT, false))
            .m_61124_(OPEN, false)
      );
   }

   public BlockState m_6843_(BlockState state, Rotation rotation) {
      return (BlockState)state.m_61124_(FACING, rotation.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirror) {
      return state.m_60717_(mirror.m_54846_((Direction)state.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> p_49751_) {
      p_49751_.m_61104_(new Property[]{FACING, OPEN, LIT});
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new Door_Of_Seal_BlockEntity(pos, state);
   }

   public InteractionResult m_6227_(BlockState p_49722_, Level p_49723_, BlockPos p_49724_, Player p_49725_, InteractionHand p_49726_, BlockHitResult p_49727_) {
      return this.onHit(p_49723_, p_49722_, p_49727_, p_49725_, true) ? InteractionResult.m_19078_(p_49723_.f_46443_) : InteractionResult.PASS;
   }

   public boolean onHit(Level p_49702_, BlockState blockState, BlockHitResult p_49704_, @javax.annotation.Nullable Player p_49705_, boolean p_49706_) {
      BlockPos blockpos = p_49704_.m_82425_();
      if (p_49706_) {
         this.attemptToRing(p_49705_, p_49702_, blockState, blockpos);
         return true;
      } else {
         return false;
      }
   }

   public boolean attemptToRing(@javax.annotation.Nullable Entity p_152189_, Level p_152190_, BlockState blockState, BlockPos p_152191_) {
      BlockEntity blockentity = p_152190_.m_7702_(p_152191_);
      if (!p_152190_.f_46443_ && blockentity instanceof Door_Of_Seal_BlockEntity && !(Boolean)blockState.m_61143_(LIT)) {
         ((Door_Of_Seal_BlockEntity)blockentity).onHit(p_152190_);
         p_152190_.m_7731_(p_152191_, (BlockState)blockState.m_61124_(LIT, true), 3);
         p_152190_.m_142346_(p_152189_, GameEvent.f_157792_, p_152191_);
         return true;
      } else {
         return false;
      }
   }

   @javax.annotation.Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152194_, BlockState p_152195_, BlockEntityType<T> p_152196_) {
      return m_152132_(p_152196_, (BlockEntityType)ModTileentites.DOOR_OF_SEAL.get(), Door_Of_Seal_BlockEntity::tick);
   }

   public VoxelShape m_5940_(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
      return CLOSED_SHAPE;
   }

   public RenderShape m_7514_(BlockState blockState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public VoxelShape m_5909_(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_, CollisionContext p_48738_) {
      return Shapes.m_83040_();
   }

   public VoxelShape m_7947_(BlockState p_253862_, BlockGetter p_254569_, BlockPos p_254197_) {
      return p_253862_.m_61143_(OPEN) ? Shapes.m_83040_() : CLOSED_SHAPE;
   }

   public VoxelShape m_5939_(BlockState p_53396_, BlockGetter p_53397_, BlockPos p_53398_, CollisionContext p_53399_) {
      return p_53396_.m_61143_(OPEN) ? Shapes.m_83040_() : CLOSED_SHAPE;
   }

   public VoxelShape m_7952_(BlockState p_53401_, BlockGetter p_53402_, BlockPos p_53403_) {
      return Shapes.m_83040_();
   }

   public boolean m_7357_(BlockState p_49717_, BlockGetter p_49718_, BlockPos p_49719_, PathComputationType p_49720_) {
      return false;
   }

   private boolean doesGongFitInDirection(BlockPos pos, Direction direction, Level level) {
      for (int i = 0; i <= 7; i++) {
         BlockPos abovePos = pos.m_6630_(i);
         BlockPos blockpos1 = abovePos.m_121945_(direction.m_122427_());
         BlockPos blockpos3 = abovePos.m_121945_(direction.m_122428_());
         BlockPos blockpos4 = abovePos.m_5484_(direction.m_122427_(), 2);
         BlockPos blockpos5 = abovePos.m_5484_(direction.m_122428_(), 2);
         BlockPos[] toBreakPoses = new BlockPos[]{blockpos1, abovePos, blockpos3, blockpos4, blockpos5};

         for (BlockPos toBreakPos : toBreakPoses) {
            BlockState blockstate = level.m_8055_(toBreakPos);
            if (!blockstate.m_60767_().m_76336_()) {
               return false;
            }
         }
      }

      return true;
   }

   @javax.annotation.Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      Direction direction = context.m_43719_();
      BlockPos blockpos = context.m_8083_();
      Axis direction$axis = direction.m_122434_();
      if (direction$axis == Axis.Y) {
         Direction dir = context.m_8125_();
         BlockState blockstate = (BlockState)this.m_49966_().m_61124_(FACING, dir);
         if (blockstate.m_60710_(context.m_43725_(), blockpos) && this.doesGongFitInDirection(blockpos, dir, context.m_43725_())) {
            return blockstate;
         }
      } else {
         Direction dir = direction.m_122424_();
         BlockState blockstate1 = (BlockState)this.m_49966_().m_61124_(FACING, dir);
         if (blockstate1.m_60710_(context.m_43725_(), context.m_8083_()) && this.doesGongFitInDirection(context.m_8083_(), dir, context.m_43725_())) {
            return blockstate1;
         }
      }

      return null;
   }

   public void m_6402_(Level level, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity entity, ItemStack itemStack) {
      super.m_6402_(level, pos, state, entity, itemStack);
      if (!level.f_46443_) {
         for (int i = 0; i < 8; i++) {
            BlockPos abovePos = pos.m_6630_(i);
            BlockPos blockpos1 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
            BlockPos blockpos3 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
            BlockPos blockpos4 = abovePos.m_5484_(((Direction)state.m_61143_(FACING)).m_122427_(), 2);
            BlockPos blockpos5 = abovePos.m_5484_(((Direction)state.m_61143_(FACING)).m_122428_(), 2);
            BlockState defaultGongPart = ((Block)ModBlocks.DOOR_OF_SEAL_PART.get()).m_49966_();
            level.m_7731_(
               blockpos1,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.PART, Door_of_Seal_Block.Door_Of_Seal_Part.SIDE_LEFT))
                  .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.Y_OFFSET, i),
               3
            );
            level.m_7731_(
               blockpos3,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.PART, Door_of_Seal_Block.Door_Of_Seal_Part.SIDE_RIGHT))
                  .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.Y_OFFSET, i),
               3
            );
            level.m_7731_(
               blockpos4,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.PART, Door_of_Seal_Block.Door_Of_Seal_Part.END_LEFT))
                  .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.Y_OFFSET, i),
               3
            );
            level.m_7731_(
               blockpos5,
               (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                     .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.PART, Door_of_Seal_Block.Door_Of_Seal_Part.END_RIGHT))
                  .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.Y_OFFSET, i),
               3
            );
            if (abovePos != pos) {
               level.m_7731_(
                  abovePos,
                  (BlockState)((BlockState)((BlockState)defaultGongPart.m_61124_(FACING, (Direction)state.m_61143_(FACING)))
                        .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.PART, Door_of_Seal_Block.Door_Of_Seal_Part.CENTER))
                     .m_61124_(Door_of_Seal_Block.Door_Of_Seal_Part_Block.Y_OFFSET, i),
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
         for (int i = 0; i <= 7; i++) {
            BlockPos abovePos = pos.m_6630_(i);
            BlockPos blockpos1 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
            BlockPos blockpos3 = abovePos.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
            BlockPos blockpos4 = abovePos.m_5484_(((Direction)state.m_61143_(FACING)).m_122427_(), 2);
            BlockPos blockpos5 = abovePos.m_5484_(((Direction)state.m_61143_(FACING)).m_122428_(), 2);
            BlockPos[] toBreakPoses = new BlockPos[]{blockpos1, abovePos, blockpos3, blockpos4, blockpos5};

            for (BlockPos toBreakPos : toBreakPoses) {
               BlockState blockstate = level.m_8055_(toBreakPos);
               if (blockstate.m_60713_((Block)ModBlocks.DOOR_OF_SEAL_PART.get())) {
                  level.m_7731_(toBreakPos, Blocks.f_50016_.m_49966_(), 35);
                  level.m_5898_(player, 2001, toBreakPos, Block.m_49956_(blockstate));
               }
            }
         }
      }

      super.m_5707_(level, pos, state, player);
   }

   public static enum Door_Of_Seal_Part implements StringRepresentable {
      SIDE_LEFT("side_left"),
      SIDE_RIGHT("side_right"),
      END_LEFT("end_left"),
      END_RIGHT("end_right"),
      CENTER("center");

      private final String name;

      private Door_Of_Seal_Part(String name) {
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

   public static class Door_Of_Seal_Part_Block extends HorizontalDirectionalBlock {
      public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
      public static final BooleanProperty OPEN = BlockStateProperties.f_61446_;
      public static final EnumProperty<Door_of_Seal_Block.Door_Of_Seal_Part> PART = EnumProperty.m_61587_(
         "door_part", Door_of_Seal_Block.Door_Of_Seal_Part.class
      );
      public static final IntegerProperty Y_OFFSET = IntegerProperty.m_61631_("y_offset", 0, 7);

      public Door_Of_Seal_Part_Block(Properties properties) {
         super(properties);
         this.m_49959_(
            (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(OPEN, false))
                  .m_61124_(PART, Door_of_Seal_Block.Door_Of_Seal_Part.CENTER))
               .m_61124_(Y_OFFSET, 0)
         );
      }

      protected void m_7926_(Builder<Block, BlockState> p_49751_) {
         p_49751_.m_61104_(new Property[]{FACING, OPEN, PART, Y_OFFSET});
      }

      public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         if (baseState.m_60713_((Block)ModBlocks.DOOR_OF_SEAL.get())) {
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
         if (state.m_61143_(PART) == Door_of_Seal_Block.Door_Of_Seal_Part.SIDE_LEFT) {
            toReturn = toReturn.m_121945_(((Direction)state.m_61143_(FACING)).m_122428_());
         } else if (state.m_61143_(PART) == Door_of_Seal_Block.Door_Of_Seal_Part.SIDE_RIGHT) {
            toReturn = toReturn.m_121945_(((Direction)state.m_61143_(FACING)).m_122427_());
         }

         if (state.m_61143_(PART) == Door_of_Seal_Block.Door_Of_Seal_Part.END_LEFT) {
            toReturn = toReturn.m_5484_(((Direction)state.m_61143_(FACING)).m_122428_(), 2);
         } else if (state.m_61143_(PART) == Door_of_Seal_Block.Door_Of_Seal_Part.END_RIGHT) {
            toReturn = toReturn.m_5484_(((Direction)state.m_61143_(FACING)).m_122427_(), 2);
         }

         return toReturn;
      }

      public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         if (baseState.m_60713_((Block)ModBlocks.DOOR_OF_SEAL.get())) {
            level.m_7731_(basePos, Blocks.f_50016_.m_49966_(), 35);
            level.m_5898_(player, 2001, basePos, Block.m_49956_(state));
         }
      }

      public BlockState m_7417_(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
         BlockPos basePos = this.getBasePos(state, pos);
         BlockState baseState = level.m_8055_(basePos);
         return !baseState.m_60713_((Block)ModBlocks.DOOR_OF_SEAL.get())
            ? Blocks.f_50016_.m_49966_()
            : super.m_7417_(state, direction, state1, level, pos, pos1);
      }

      public boolean m_7357_(BlockState p_49717_, BlockGetter p_49718_, BlockPos p_49719_, PathComputationType p_49720_) {
         return false;
      }

      public VoxelShape m_5940_(BlockState p_49755_, BlockGetter p_49756_, BlockPos p_49757_, CollisionContext p_49758_) {
         return Door_of_Seal_Block.CLOSED_SHAPE;
      }

      public RenderShape m_7514_(BlockState blockState) {
         return RenderShape.MODEL;
      }

      public VoxelShape m_7947_(BlockState p_253862_, BlockGetter p_254569_, BlockPos p_254197_) {
         return p_253862_.m_61143_(OPEN) ? Shapes.m_83040_() : Door_of_Seal_Block.CLOSED_SHAPE;
      }

      public VoxelShape m_5939_(BlockState p_53396_, BlockGetter p_53397_, BlockPos p_53398_, CollisionContext p_53399_) {
         return p_53396_.m_61143_(OPEN) ? Shapes.m_83040_() : Door_of_Seal_Block.CLOSED_SHAPE;
      }

      public VoxelShape m_7952_(BlockState p_53401_, BlockGetter p_53402_, BlockPos p_53403_) {
         return Shapes.m_83040_();
      }

      public Item m_5456_() {
         return ((Block)ModBlocks.DOOR_OF_SEAL.get()).m_5456_();
      }
   }
}
