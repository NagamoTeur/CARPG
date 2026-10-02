package com.aqutheseal.celestisynth.common.network.util;

import java.util.function.Supplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ChangeCameraTypePacket {
   private final int playerTarget;
   private final int enumID;

   public ChangeCameraTypePacket(int target, int enumID) {
      this.playerTarget = target;
      this.enumID = enumID;
   }

   public ChangeCameraTypePacket(FriendlyByteBuf buf) {
      this.playerTarget = buf.readInt();
      this.enumID = buf.readInt();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeInt(this.playerTarget);
      buf.writeInt(this.enumID);
   }

   public boolean handle(Supplier<Context> supplier) {
      Context context = supplier.get();
      context.enqueueWork(() -> {
         Minecraft instance = Minecraft.m_91087_();
         if (instance.f_91074_.m_19879_() == this.playerTarget) {
            instance.f_91066_.m_92157_(CameraType.values()[this.enumID]);
         }
      });
      return true;
   }
}
