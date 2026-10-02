package com.github.L_Ender.cataclysm.message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class MessageParticle {
   private final List<MessageParticle.QueuedParticle> queuedParticles = new ArrayList<>();

   public MessageParticle() {
   }

   public MessageParticle(FriendlyByteBuf buf) {
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         ParticleType<?> type = (ParticleType<?>)ForgeRegistries.PARTICLE_TYPES.getValue(buf.m_130281_());
         if (type == null) {
            break;
         }

         this.queuedParticles
            .add(
               new MessageParticle.QueuedParticle(
                  this.readParticle((ParticleType<ParticleOptions>)type, buf),
                  buf.readBoolean(),
                  buf.readDouble(),
                  buf.readDouble(),
                  buf.readDouble(),
                  buf.readDouble(),
                  buf.readDouble(),
                  buf.readDouble()
               )
            );
      }
   }

   private <T extends ParticleOptions> T readParticle(ParticleType<T> particleType, FriendlyByteBuf buf) {
      return (T)particleType.m_123743_().m_6507_(particleType, buf);
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.queuedParticles.size());

      for (MessageParticle.QueuedParticle queuedParticle : this.queuedParticles) {
         ResourceLocation d = ForgeRegistries.PARTICLE_TYPES.getKey(queuedParticle.particleOptions.m_6012_());
         buf.m_130085_(d);
         queuedParticle.particleOptions.m_7711_(buf);
         buf.writeBoolean(queuedParticle.b);
         buf.writeDouble(queuedParticle.x);
         buf.writeDouble(queuedParticle.y);
         buf.writeDouble(queuedParticle.z);
         buf.writeDouble(queuedParticle.x2);
         buf.writeDouble(queuedParticle.y2);
         buf.writeDouble(queuedParticle.z2);
      }
   }

   public void queueParticle(ParticleOptions particleOptions, boolean b, double x, double y, double z, double x2, double y2, double z2) {
      this.queuedParticles.add(new MessageParticle.QueuedParticle(particleOptions, b, x, y, z, x2, y2, z2));
   }

   public void queueParticle(ParticleOptions particleOptions, boolean b, Vec3 xyz, Vec3 xyz2) {
      this.queuedParticles
         .add(new MessageParticle.QueuedParticle(particleOptions, b, xyz.f_82479_, xyz.f_82480_, xyz.f_82481_, xyz2.f_82479_, xyz2.f_82480_, xyz2.f_82481_));
   }

   public static class Handler {
      public static boolean onMessage(MessageParticle message, Supplier<Context> ctx) {
         ctx.get()
            .enqueueWork(
               () -> {
                  ClientLevel level = Minecraft.m_91087_().f_91073_;
                  if (level != null) {
                     for (MessageParticle.QueuedParticle queuedParticle : message.queuedParticles) {
                        level.m_6493_(
                           queuedParticle.particleOptions,
                           queuedParticle.b,
                           queuedParticle.x,
                           queuedParticle.y,
                           queuedParticle.z,
                           queuedParticle.x2,
                           queuedParticle.y2,
                           queuedParticle.z2
                        );
                     }
                  }
               }
            );
         ctx.get().setPacketHandled(true);
         return true;
      }
   }

   private static record QueuedParticle(ParticleOptions particleOptions, boolean b, double x, double y, double z, double x2, double y2, double z2) {
   }
}
