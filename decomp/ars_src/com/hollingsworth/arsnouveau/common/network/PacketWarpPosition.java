package com.hollingsworth.arsnouveau.common.network;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketWarpPosition {
   private final int entityID;
   double x;
   double y;
   double z;
   float xRot;
   float yRot;

   public PacketWarpPosition(Entity entity, double x, double y, double z) {
      this.entityID = entity.m_19879_();
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public PacketWarpPosition(int id, double x, double y, double z, float xRot, float yRot) {
      this.entityID = id;
      this.x = x;
      this.y = y;
      this.z = z;
      this.xRot = xRot;
      this.yRot = yRot;
   }

   public static PacketWarpPosition decode(FriendlyByteBuf buf) {
      return new PacketWarpPosition(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat());
   }

   public static void encode(PacketWarpPosition msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.entityID);
      buf.writeDouble(msg.x);
      buf.writeDouble(msg.y);
      buf.writeDouble(msg.z);
      buf.writeFloat(msg.xRot);
      buf.writeFloat(msg.yRot);
   }

   public static class Handler {
      public static void handle(final PacketWarpPosition message, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  Minecraft mc = Minecraft.m_91087_();
                  ClientLevel world = mc.f_91073_;
                  Entity e = world.m_6815_(message.entityID);
                  if (e != null) {
                     e.m_6034_(message.x, message.y, message.z);
                     e.m_146926_(message.xRot);
                     e.m_146922_(message.yRot);
                  }
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
