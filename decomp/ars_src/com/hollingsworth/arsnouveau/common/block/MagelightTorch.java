package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.MagelightTorchTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class MagelightTorch extends TickableModBlock {
   public static final BooleanProperty FLOOR = BooleanProperty.m_61465_("floor");
   public static final BooleanProperty ROOF = BooleanProperty.m_61465_("roof");

   public MagelightTorch() {
      super(
         Properties.m_60939_(Material.f_76278_)
            .m_60918_(SoundType.f_56742_)
            .m_60913_(2.0F, 3.0F)
            .m_60955_()
            .m_60910_()
            .m_60953_(b -> (Integer)b.m_61143_(SconceBlock.LIGHT_LEVEL))
      );
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61372_, Direction.NORTH)).m_61124_(FLOOR, true))
            .m_61124_(ROOF, false)
      );
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         if (pLevel.m_7702_(pPos) instanceof MagelightTorchTile torchTile) {
            torchTile.setHorizontalFire(!torchTile.isHorizontalFire());
         }

         return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      Direction clickedDirection = context.m_43719_();
      if (clickedDirection == Direction.UP) {
         return (BlockState)((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61372_, context.m_7820_().m_122424_())).m_61124_(FLOOR, Boolean.TRUE);
      } else if (clickedDirection == Direction.DOWN) {
         Direction direction = context.m_8125_();
         if (direction == Direction.SOUTH) {
            direction = Direction.NORTH;
         }

         if (direction == Direction.WEST) {
            direction = Direction.EAST;
         }

         if (direction == Direction.DOWN) {
            direction = Direction.EAST;
         }

         return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61372_, direction)).m_61124_(ROOF, Boolean.TRUE))
            .m_61124_(FLOOR, false);
      } else {
         return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61372_, clickedDirection)).m_61124_(FLOOR, false))
            .m_61124_(ROOF, false);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{BlockStateProperties.f_61372_})
         .m_61104_(new Property[]{SconceBlock.LIGHT_LEVEL})
         .m_61104_(new Property[]{FLOOR})
         .m_61104_(new Property[]{ROOF});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(BlockStateProperties.f_61372_, rot.m_55954_((Direction)state.m_61143_(BlockStateProperties.f_61372_)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(BlockStateProperties.f_61372_)));
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new MagelightTorchTile(pPos, pState);
   }
}
