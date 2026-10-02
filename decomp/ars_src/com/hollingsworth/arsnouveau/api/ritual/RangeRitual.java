package com.hollingsworth.arsnouveau.api.ritual;

public abstract class RangeRitual extends AbstractRitual {
   @Override
   protected void tick() {
      if (!this.getWorld().f_46443_ && this.getWorld().m_46467_() % 20L == 0L && !RitualEventQueue.containsPosition(this.tile.m_58904_(), this.tile.m_58899_())
         )
       {
         RitualEventQueue.addPosition(this.tile.m_58904_(), this.tile.m_58899_());
      }
   }
}
