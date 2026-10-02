package dev.latvian.mods.kubejs.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public class PaintMessage extends BaseS2CMessage {
   private final CompoundTag tag;

   public PaintMessage(CompoundTag c) {
      this.tag = c;
   }

   PaintMessage(FriendlyByteBuf buffer) {
      this.tag = NBTUtils.read(buffer);
   }

   public MessageType getType() {
      return KubeJSNet.PAINT;
   }

   public void write(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.tag);
   }

   public void handle(PacketContext context) {
      KubeJS.PROXY.paint(this.tag);
   }
}
