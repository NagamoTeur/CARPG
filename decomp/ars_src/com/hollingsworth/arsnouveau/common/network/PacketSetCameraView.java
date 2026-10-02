package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.camera.CameraController;
import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import java.util.function.Supplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetCameraView {
   private int id;

   public PacketSetCameraView() {
   }

   public PacketSetCameraView(Entity camera) {
      this.id = camera.m_19879_();
   }

   public static void encode(PacketSetCameraView message, FriendlyByteBuf buf) {
      buf.m_130130_(message.id);
   }

   public static PacketSetCameraView decode(FriendlyByteBuf buf) {
      PacketSetCameraView message = new PacketSetCameraView();
      message.id = buf.m_130242_();
      return message;
   }

   public static void onMessage(PacketSetCameraView message, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         Minecraft mc = Minecraft.m_91087_();
         Entity entity = mc.f_91073_.m_6815_(message.id);
         boolean isCamera = entity instanceof ScryerCamera;
         if (isCamera || entity instanceof Player) {
            mc.m_91118_(entity);
            if (isCamera) {
               CameraController.previousCameraType = mc.f_91066_.m_92176_();
               mc.f_91066_.m_92157_(CameraType.FIRST_PERSON);
               CameraController.setRenderPosition(entity);
            } else if (CameraController.previousCameraType != null) {
               mc.f_91066_.m_92157_(CameraController.previousCameraType);
            }

            mc.f_91060_.m_109818_();
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
