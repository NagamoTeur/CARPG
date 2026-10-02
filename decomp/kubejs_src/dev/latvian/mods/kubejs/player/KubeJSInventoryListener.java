package dev.latvian.mods.kubejs.player;

import dev.latvian.mods.kubejs.bindings.event.PlayerEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

public class KubeJSInventoryListener implements ContainerListener {
   public final Player player;

   public KubeJSInventoryListener(Player p) {
      this.player = p;
   }

   public void m_7934_(AbstractContainerMenu container, int index, ItemStack stack) {
      if (PlayerEvents.INVENTORY_CHANGED.hasListeners() && !stack.m_41619_() && container.m_38853_(index).f_40218_ == this.player.m_150109_()) {
         PlayerEvents.INVENTORY_CHANGED.post(this.player, stack.m_41720_(), new InventoryChangedEventJS(this.player, stack, index));
      }
   }

   public void m_142153_(AbstractContainerMenu container, int id, int value) {
   }
}
