package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketANEffect {
   private final PacketANEffect.EffectType type;
   private final double x;
   private final double y;
   private final double z;
   private final int red;
   private final int green;
   private final int blue;
   private final int[] args;

   public PacketANEffect(PacketANEffect.EffectType type, double x, double y, double z, int... args) {
      this.type = type;
      this.x = x;
      this.y = y;
      this.z = z;
      this.red = 255;
      this.green = 25;
      this.blue = 180;
      this.args = args;
   }

   public PacketANEffect(PacketANEffect.EffectType type, double x, double y, double z, ParticleColor.IntWrapper wrapper, int... args) {
      this.type = type;
      this.x = x;
      this.y = y;
      this.z = z;
      this.red = wrapper.r;
      this.blue = wrapper.b;
      this.green = wrapper.g;
      this.args = args;
   }

   public PacketANEffect(PacketANEffect.EffectType type, BlockPos pos, int... args) {
      this(type, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), args);
   }

   public PacketANEffect(PacketANEffect.EffectType type, BlockPos pos, ParticleColor.IntWrapper wrapper, int... args) {
      this(type, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), wrapper, args);
   }

   public static PacketANEffect decode(FriendlyByteBuf buf) {
      PacketANEffect.EffectType type = PacketANEffect.EffectType.values()[buf.readByte()];
      double x = buf.readDouble();
      double y = buf.readDouble();
      double z = buf.readDouble();
      int red = buf.readInt();
      int green = buf.readInt();
      int blue = buf.readInt();
      int[] args = new int[type.argCount];

      for (int i = 0; i < args.length; i++) {
         args[i] = buf.m_130242_();
      }

      return new PacketANEffect(type, x, y, z, new ParticleColor.IntWrapper(red, green, blue), args);
   }

   public static void encode(PacketANEffect msg, FriendlyByteBuf buf) {
      buf.writeByte(msg.type.ordinal());
      buf.writeDouble(msg.x);
      buf.writeDouble(msg.y);
      buf.writeDouble(msg.z);
      buf.writeInt(msg.red);
      buf.writeInt(msg.green);
      buf.writeInt(msg.blue);

      for (int i = 0; i < msg.type.argCount; i++) {
         buf.m_130130_(msg.args[i]);
      }
   }

   public static enum EffectType {
      TIMED_GLOW(4),
      TIMED_HELIX(0),
      BURST(0);

      private final int argCount;

      private EffectType(int argCount) {
         this.argCount = argCount;
      }
   }

   public static class Handler {
      public static void handle(final PacketANEffect message, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get()
               .enqueueWork(
                  new Runnable() {
                     @Override
                     public void run() {
                        Minecraft mc = Minecraft.m_91087_();
                        ClientLevel world = mc.f_91073_;
                        switch (message.type) {
                           case BURST:
                              for (int i = 0; i < 10; i++) {
                                 double d0 = message.x + 0.5;
                                 double d1 = message.y + 1.2;
                                 double d2 = message.z + 0.5;
                                 world.m_7106_(
                                    GlowParticleData.createData(new ParticleColor(message.red, message.green, message.blue)),
                                    d0,
                                    d1,
                                    d2,
                                    ((double)world.f_46441_.m_188501_() - 0.5) / 3.0,
                                    ((double)world.f_46441_.m_188501_() - 0.5) / 3.0,
                                    ((double)world.f_46441_.m_188501_() - 0.5) / 3.0
                                 );
                              }
                        }
                     }
                  }
               );
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
