package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.common.block.tile.TimerSpellTurretTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;

public class TimerSpellTurret extends BasicSpellTurret {
   public TimerSpellTurret(Properties properties) {
      super(properties);
   }

   public TimerSpellTurret() {
      super(defaultProperties().m_60955_());
   }

   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new TimerSpellTurretTile(pos, state);
   }

   @Override
   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack stack = player.m_21120_(handIn);
      if (handIn == InteractionHand.MAIN_HAND) {
         if (stack.m_41720_() instanceof ICasterTool || worldIn.f_46443_) {
            return super.m_6227_(state, worldIn, pos, player, handIn, hit);
         }

         if (worldIn.m_7702_(pos) instanceof TimerSpellTurretTile timerSpellTurretTile) {
            if (timerSpellTurretTile.isLocked) {
               return InteractionResult.SUCCESS;
            }

            timerSpellTurretTile.addTime(20 * (player.m_6144_() ? 10 : 1));
         }
      }

      return InteractionResult.SUCCESS;
   }

   public void m_6256_(BlockState state, Level level, BlockPos pos, Player player) {
      if (!level.f_46443_ && level.m_7702_(pos) instanceof TimerSpellTurretTile tile && !tile.isLocked) {
         tile.addTime(-20 * (player.m_6144_() ? 10 : 1));
      }
   }

   @Override
   public void m_6861_(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      if (!world.m_5776_() && world.m_7702_(pos) instanceof TimerSpellTurretTile tile) {
         tile.isOff = world.m_46753_(pos);
         tile.updateBlock();
      }
   }
}
