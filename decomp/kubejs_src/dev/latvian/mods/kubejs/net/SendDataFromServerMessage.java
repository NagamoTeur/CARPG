package dev.latvian.mods.kubejs.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.latvian.mods.kubejs.KubeJS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public class SendDataFromServerMessage extends BaseS2CMessage {
   private final String channel;
   private final CompoundTag data;

   public SendDataFromServerMessage(String c, @Nullable CompoundTag d) {
      this.channel = c;
      this.data = d;
   }

   SendDataFromServerMessage(FriendlyByteBuf buf) {
      this.channel = buf.m_130136_(120);
      this.data = buf.m_130260_();
   }

   public MessageType getType() {
      return KubeJSNet.SEND_DATA_FROM_SERVER;
   }

   public void write(FriendlyByteBuf buf) {
      buf.m_130072_(this.channel, 120);
      buf.m_130079_(this.data);
   }

   public void handle(PacketContext context) {
      if (!this.channel.isEmpty()) {
         KubeJS.PROXY.handleDataFromServerPacket(this.channel, this.data);
      }
   }
}
