package dev.latvian.mods.kubejs.item.forge;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.jetbrains.annotations.Nullable;

public class ItemDestroyedEventJS extends PlayerEventJS {
   private final PlayerDestroyItemEvent event;

   public ItemDestroyedEventJS(PlayerDestroyItemEvent e) {
      this.event = e;
   }

   public ServerPlayer getEntity() {
      return (ServerPlayer)this.event.getEntity();
   }

   @Nullable
   public InteractionHand getHand() {
      return this.event.getHand();
   }

   public ItemStack getItem() {
      return this.event.getOriginal();
   }
}
