package com.aqutheseal.celestisynth.common.network.util;

import com.aqutheseal.celestisynth.manager.CSNetworkManager;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ShakeScreenServerPacket {
   private final UUID playerId;
   private final int duration;
   private final int fadeOutStart;
   private final float intensity;

   public ShakeScreenServerPacket(UUID playerId, int duration, int fadeOutStart, float intensity) {
      this.playerId = playerId;
      this.duration = duration;
      this.fadeOutStart = fadeOutStart;
      this.intensity = intensity;
   }

   public ShakeScreenServerPacket(FriendlyByteBuf buf) {
      this.playerId = buf.m_130259_();
      this.duration = buf.readInt();
      this.fadeOutStart = buf.readInt();
      this.intensity = buf.readFloat();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130077_(this.playerId);
      buf.writeInt(this.duration);
      buf.writeInt(this.fadeOutStart);
      buf.writeFloat(this.intensity);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context context = supplier.get();
      context.enqueueWork(() -> CSNetworkManager.sendToAll(new ShakeScreenToAllPacket(this.playerId, this.duration, this.fadeOutStart, this.intensity)));
      return true;
   }
}
