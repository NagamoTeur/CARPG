package com.hollingsworth.arsnouveau.common.network;

import java.util.function.Supplier;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketNoSpamChatMessage {
   private final Component message;
   private final int messageChannelId;
   private final boolean overlayMessage;
   private static final int MESSAGE_ID = "ars_nouveau".hashCode();
   private static MessageSignature AN_SIGNATURE = new MessageSignature(new byte[]{-64, -34, -64, -34});

   public PacketNoSpamChatMessage(Component message, int messageChannelId, boolean overlayMessage) {
      this.message = message;
      this.messageChannelId = MESSAGE_ID + messageChannelId;
      this.overlayMessage = overlayMessage;
   }

   public PacketNoSpamChatMessage(FriendlyByteBuf buf) {
      this.message = buf.m_130238_();
      this.messageChannelId = buf.readInt();
      this.overlayMessage = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130083_(this.message);
      buf.writeInt(this.messageChannelId);
      buf.writeBoolean(this.overlayMessage);
   }

   public void handle(Supplier<Context> ctxSupplier) {
      Context ctx = ctxSupplier.get();
      ctx.enqueueWork(() -> {
         if (this.overlayMessage) {
            if (Minecraft.m_91087_().f_91074_ != null) {
               Minecraft.m_91087_().f_91074_.m_5661_(this.message, true);
            }
         } else {
            ChatComponent gui = Minecraft.m_91087_().f_91065_.m_93076_();
            gui.m_240953_(AN_SIGNATURE);
            gui.m_240964_(this.message, AN_SIGNATURE, GuiMessageTag.m_240701_());
         }
      });
      ctx.setPacketHandled(true);
   }
}
