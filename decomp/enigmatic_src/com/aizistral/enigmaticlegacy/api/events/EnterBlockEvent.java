package com.aizistral.enigmaticlegacy.api.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EnterBlockEvent extends PlayerEvent {
   private final BlockState blockState;

   public EnterBlockEvent(Player player, BlockState blockState) {
      super(player);
      this.blockState = blockState;
   }

   public BlockState getBlockState() {
      return this.blockState;
   }
}
