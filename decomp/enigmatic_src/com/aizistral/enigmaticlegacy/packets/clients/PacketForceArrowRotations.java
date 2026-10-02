package com.aizistral.enigmaticlegacy.packets.clients;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketForceArrowRotations {
   private int entityID;
   private double motionX;
   private double motionY;
   private double motionZ;
   private double posX;
   private double posY;
   private double posZ;
   private float rotationYaw;
   private float rotationPitch;

   public PacketForceArrowRotations(
      int entityID, float rotationYaw, float rotationPitch, double vecX, double vecY, double vecZ, double posX, double posY, double posZ
   ) {
      this.entityID = entityID;
      this.rotationYaw = rotationYaw;
      this.rotationPitch = rotationPitch;
      this.motionX = vecX;
      this.motionY = vecY;
      this.motionZ = vecZ;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }

   public static void encode(PacketForceArrowRotations msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.entityID);
      buf.writeFloat(msg.rotationYaw);
      buf.writeFloat(msg.rotationPitch);
      buf.writeDouble(msg.motionX);
      buf.writeDouble(msg.motionY);
      buf.writeDouble(msg.motionZ);
      buf.writeDouble(msg.posX);
      buf.writeDouble(msg.posY);
      buf.writeDouble(msg.posZ);
   }

   public static PacketForceArrowRotations decode(FriendlyByteBuf buf) {
      return new PacketForceArrowRotations(
         buf.readInt(),
         buf.readFloat(),
         buf.readFloat(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble(),
         buf.readDouble()
      );
   }

   public static void handle(PacketForceArrowRotations msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ClientLevel theWorld = Minecraft.m_91087_().f_91073_;
         Entity arrow = theWorld.m_6815_(msg.entityID);
         if (arrow != null) {
            arrow.m_20049_("enigmaticlegacy.redirected");
            arrow.m_6027_(msg.posX, msg.posY, msg.posZ);
            arrow.m_20334_(msg.motionX, msg.motionY, msg.motionZ);
            arrow.m_146922_(msg.rotationYaw);
            arrow.f_19859_ = msg.rotationYaw;
            arrow.m_146926_(msg.rotationPitch);
            arrow.f_19860_ = msg.rotationPitch;
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
