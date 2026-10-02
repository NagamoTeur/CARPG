package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.item.ISpellHotkeyListener;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketQuickCast {
   int slot;

   public PacketQuickCast(int slot) {
      this.slot = slot;
   }

   public PacketQuickCast(FriendlyByteBuf buf) {
      this.slot = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.slot);
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null) {
            InteractionHand hand = StackUtil.getQuickCaster(player);
            if (hand == null) {
               return;
            }

            ItemStack stack = player.m_21120_(hand);
            if (!(stack.m_41720_() instanceof ISpellHotkeyListener hotkeyListener)) {
               return;
            }

            hotkeyListener.onQuickCast(stack, player, hand, this.slot);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
