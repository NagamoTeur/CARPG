package com.hollingsworth.arsnouveau.common.event.timed;

import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.common.block.tile.SkyBlockTile;

public class SkyweaveVisibilityEvent implements ITimedEvent {
   public int ticks;
   SkyBlockTile skyweave;
   boolean visible;

   public SkyweaveVisibilityEvent(SkyBlockTile skyweave, int ticks, boolean visible) {
      this.ticks = ticks;
      this.skyweave = skyweave;
      this.visible = visible;
   }

   @Override
   public void tick(boolean serverSide) {
      this.ticks--;
      if (this.ticks <= 0 && this.skyweave != null && !this.skyweave.m_58901_()) {
         this.skyweave.setShowFacade(this.visible);
      }
   }

   @Override
   public boolean isExpired() {
      return this.ticks <= 0;
   }
}
