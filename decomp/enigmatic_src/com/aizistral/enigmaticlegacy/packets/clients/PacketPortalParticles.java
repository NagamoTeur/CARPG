package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketPortalParticles {
   private double x;
   private double y;
   private double z;
   private int num;
   private double rangeModifier;
   private boolean check;

   public PacketPortalParticles(double x, double y, double z, int number, double rangeModifier, boolean checkSettings) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.num = number;
      this.rangeModifier = rangeModifier;
      this.check = checkSettings;
   }

   public static void encode(PacketPortalParticles msg, FriendlyByteBuf buf) {
      buf.writeDouble(msg.x);
      buf.writeDouble(msg.y);
      buf.writeDouble(msg.z);
      buf.writeInt(msg.num);
      buf.writeDouble(msg.rangeModifier);
      buf.writeBoolean(msg.check);
   }

   public static PacketPortalParticles decode(FriendlyByteBuf buf) {
      return new PacketPortalParticles(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readDouble(), buf.readBoolean());
   }

   public static void handle(PacketPortalParticles msg, Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(
            () -> {
               LocalPlayer player = Minecraft.m_91087_().f_91074_;
               int amount = msg.num;
               if (msg.check) {
                  amount = (int)((float)amount * SuperpositionHandler.getParticleMultiplier());
               }

               for (int counter = 0; counter <= amount; counter++) {
                  player.f_19853_
                     .m_6493_(
                        ParticleTypes.f_123760_,
                        true,
                        msg.x,
                        msg.y,
                        msg.z,
                        (Math.random() - 0.5) * 2.0 * msg.rangeModifier,
                        (Math.random() - 0.5) * 2.0 * msg.rangeModifier,
                        (Math.random() - 0.5) * 2.0 * msg.rangeModifier
                     );
               }
            }
         );
      ctx.get().setPacketHandled(true);
   }
}
