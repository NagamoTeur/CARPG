package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.AgronomicSourcelinkTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AgronomicSourcelinkBlock extends SourcelinkBlock {
   public AgronomicSourcelinkBlock() {
      super(TickableModBlock.defaultProperties().m_60955_());
   }

   public void m_6402_(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.m_6402_(worldIn, pos, state, placer, stack);
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new AgronomicSourcelinkTile(pos, state);
   }
}
