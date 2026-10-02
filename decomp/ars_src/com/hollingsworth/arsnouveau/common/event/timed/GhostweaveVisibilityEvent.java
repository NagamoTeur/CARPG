package com.hollingsworth.arsnouveau.common.event.timed;

import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;

public class GhostweaveVisibilityEvent implements ITimedEvent {
   public int ticks;
   GhostWeaveTile ghostWeaveTile;
   boolean visible;

   public GhostweaveVisibilityEvent(GhostWeaveTile ghostWeaveTile, int ticks, boolean visible) {
      this.ticks = ticks;
      this.ghostWeaveTile = ghostWeaveTile;
      this.visible = visible;
   }

   @Override
   public void tick(boolean serverSide) {
      this.ticks--;
      if (this.ticks <= 0 && this.ghostWeaveTile != null && !this.ghostWeaveTile.m_58901_()) {
         this.ghostWeaveTile.setVisibility(this.visible);
      }
   }

   @Override
   public boolean isExpired() {
      return this.ticks <= 0;
   }
}
