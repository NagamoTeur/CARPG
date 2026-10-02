package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.block.tile.IAnimationListener;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketAnimEntity {
   int entityID;
   int anim;

   public PacketAnimEntity(int entityID) {
      this.entityID = entityID;
      this.anim = 0;
   }

   public PacketAnimEntity(int entityID, int anim) {
      this.entityID = entityID;
      this.anim = anim;
   }

   public static PacketAnimEntity decode(FriendlyByteBuf buf) {
      return new PacketAnimEntity(buf.readInt(), buf.readInt());
   }

   public static void encode(PacketAnimEntity msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.entityID);
      buf.writeInt(msg.anim);
   }

   public static class Handler {
      public static void handle(final PacketAnimEntity m, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  Minecraft mc = Minecraft.m_91087_();
                  ClientLevel world = mc.f_91073_;
                  if (world.m_6815_(m.entityID) instanceof IAnimationListener) {
                     ((IAnimationListener)world.m_6815_(m.entityID)).startAnimation(m.anim);
                  }
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
