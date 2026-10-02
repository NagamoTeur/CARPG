package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.SpellSensorTile;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SpellSensor extends TickableModBlock {
   public static final EnumProperty<SculkSensorPhase> PHASE = BlockStateProperties.f_155999_;

   public SpellSensor() {
      this(defaultProperties().m_60955_());
   }

   public SpellSensor(Properties p_49795_) {
      super(p_49795_);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(PHASE, SculkSensorPhase.INACTIVE));
   }

   protected void m_7926_(Builder<Block, BlockState> pBuilder) {
      pBuilder.m_61104_(new Property[]{PHASE});
   }

   public int m_6378_(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
      if (pBlockAccess.m_7702_(pPos) instanceof SpellSensorTile sensorTile && sensorTile.outputDuration > 0) {
         return sensorTile.outputStrength;
      }

      return 0;
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         ItemStack heldStack = pPlayer.m_21120_(pHand);
         if (heldStack.m_41720_() instanceof SpellParchment && pLevel.m_7702_(pPos) instanceof SpellSensorTile sensorTile) {
            sensorTile.parchment = heldStack.m_41777_();
            sensorTile.updateBlock();
            pPlayer.m_213846_(Component.m_237115_("ars_nouveau.sensor.set_spell"));
            return InteractionResult.SUCCESS;
         } else {
            return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
         }
      }
   }

   public void m_213897_(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
      super.m_213897_(pState, pLevel, pPos, pRandom);
      if (pLevel.m_7702_(pPos) instanceof SpellSensorTile sensorTile) {
         sensorTile.onCooldown = false;
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new SpellSensorTile(pPos, pState);
   }
}
