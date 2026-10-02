package com.aizistral.enigmaticlegacy.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EndPortalActivatedEvent extends PlayerEvent {
   private final BlockPos pos;

   public EndPortalActivatedEvent(Player player, BlockPos pos) {
      super(player);
      this.pos = pos;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
