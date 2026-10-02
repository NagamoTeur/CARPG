package com.aqutheseal.celestisynth.common.network.util;

import com.aqutheseal.celestisynth.api.mixin.PlayerMixinSupport;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ShakeScreenToAllPacket {
   private final UUID playerId;
   private final int duration;
   private final int fadeOutStart;
   private final float intensity;

   public ShakeScreenToAllPacket(UUID playerId, int duration, int fadeOutStart, float intensity) {
      this.playerId = playerId;
      this.duration = duration;
      this.fadeOutStart = fadeOutStart;
      this.intensity = intensity;
   }

   public ShakeScreenToAllPacket(FriendlyByteBuf buf) {
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
      context.enqueueWork(() -> {
         Minecraft instance = Minecraft.m_91087_();
         if (instance.f_91073_.m_46003_(this.playerId) instanceof PlayerMixinSupport pms) {
            pms.setScreenShakeDuration(this.duration);
            pms.setScreenShakeFadeoutBegin(this.fadeOutStart);
            pms.setScreenShakeIntensity(this.intensity);
         }
      });
      return true;
   }
}
