package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.item.ISpellHotkeyListener;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketHotkeyPressed {
   PacketHotkeyPressed.Key key;

   public PacketHotkeyPressed(PacketHotkeyPressed.Key key) {
      this.key = key;
   }

   public PacketHotkeyPressed(FriendlyByteBuf buf) {
      this.key = PacketHotkeyPressed.Key.valueOf(buf.m_130277_());
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.key.name());
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null) {
            InteractionHand hand = StackUtil.getHeldCasterTool(player, tool -> tool.getSpellCaster().getMaxSlots() > 1);
            if (hand == null) {
               return;
            }

            ItemStack stack = player.m_21120_(hand);
            if (!(stack.m_41720_() instanceof ISpellHotkeyListener hotkeyListener)) {
               return;
            }

            if (this.key == PacketHotkeyPressed.Key.NEXT) {
               hotkeyListener.onNextKeyPressed(stack, player);
            } else if (this.key == PacketHotkeyPressed.Key.PREVIOUS) {
               hotkeyListener.onPreviousKeyPressed(stack, player);
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }

   public static enum Key {
      NEXT,
      PREVIOUS;
   }
}
