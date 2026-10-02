package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import net.minecraft.world.entity.player.Player;

public final class InventorySculptor extends InventoryOneInput {
   private final EntitySculptor sculptor;

   public InventorySculptor(EntitySculptor sculptor) {
      super(sculptor);
      this.sculptor = sculptor;
   }

   public boolean m_6542_(Player player) {
      return this.sculptor.getCustomer() == player;
   }
}
