package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.items.AstralBreaker;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketFlameParticles {
   public static final Random random = new Random();
   private double x;
   private double y;
   private double z;
   private int num;
   private boolean check;

   public PacketFlameParticles(double x, double y, double z, int number, boolean checkSettings) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.num = number;
      this.check = checkSettings;
   }

   public static void encode(PacketFlameParticles msg, FriendlyByteBuf buf) {
      buf.writeDouble(msg.x);
      buf.writeDouble(msg.y);
      buf.writeDouble(msg.z);
      buf.writeInt(msg.num);
      buf.writeBoolean(msg.check);
   }

   public static PacketFlameParticles decode(FriendlyByteBuf buf) {
      return new PacketFlameParticles(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readBoolean());
   }

   public static void handle(PacketFlameParticles msg, Supplier<Context> ctx) {
      if (AstralBreaker.flameParticlesToggle.getValue()) {
         ctx.get()
            .enqueueWork(
               () -> {
                  LocalPlayer player = Minecraft.m_91087_().f_91074_;
                  int amount = msg.num;
                  float modifier = 1.0F;
                  if (msg.check) {
                     if (Minecraft.m_91087_().f_91066_.m_231929_().m_231551_() == ParticleStatus.MINIMAL) {
                        modifier = 0.1F;
                     } else if (Minecraft.m_91087_().f_91066_.m_231929_().m_231551_() == ParticleStatus.DECREASED) {
                        modifier = 0.25F;
                     } else {
                        modifier = 0.35F;
                     }
                  }

                  amount = (int)((float)amount * modifier);

                  for (int counter = 0; counter <= amount; counter++) {
                     player.f_19853_
                        .m_6493_(
                           ParticleTypes.f_123744_,
                           true,
                           msg.x + (Math.random() - 0.5),
                           msg.y + (Math.random() - 0.5),
                           msg.z + (Math.random() - 0.5),
                           (Math.random() - 0.5) * 0.1,
                           (Math.random() - 0.5) * 0.1,
                           (Math.random() - 0.5) * 0.1
                        );
                  }
               }
            );
         ctx.get().setPacketHandled(true);
      }
   }
}
