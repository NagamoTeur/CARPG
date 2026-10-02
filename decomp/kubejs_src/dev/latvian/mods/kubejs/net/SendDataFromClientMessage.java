package dev.latvian.mods.kubejs.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import dev.latvian.mods.kubejs.bindings.event.NetworkEvents;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class SendDataFromClientMessage extends BaseC2SMessage {
   private final String channel;
   private final CompoundTag data;

   public SendDataFromClientMessage(String c, @Nullable CompoundTag d) {
      this.channel = c;
      this.data = d;
   }

   SendDataFromClientMessage(FriendlyByteBuf buf) {
      this.channel = buf.m_130136_(120);
      this.data = buf.m_130260_();
   }

   public MessageType getType() {
      return KubeJSNet.SEND_DATA_FROM_CLIENT;
   }

   public void write(FriendlyByteBuf buf) {
      buf.m_130072_(this.channel, 120);
      buf.m_130079_(this.data);
   }

   public void handle(PacketContext context) {
      if (!this.channel.isEmpty() && context.getPlayer() instanceof ServerPlayer serverPlayer && NetworkEvents.DATA_RECEIVED.hasListeners(this.channel)) {
         NetworkEvents.DATA_RECEIVED.post(ScriptType.SERVER, this.channel, new NetworkEventJS(serverPlayer, this.channel, this.data));
      }
   }
}
