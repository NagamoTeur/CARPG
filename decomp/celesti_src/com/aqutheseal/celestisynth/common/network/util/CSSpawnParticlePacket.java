package com.aqutheseal.celestisynth.common.network.util;

import com.aqutheseal.celestisynth.Celestisynth;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class CSSpawnParticlePacket {
   private final double x;
   private final double y;
   private final double z;
   private final float xDist;
   private final float yDist;
   private final float zDist;
   private final float xSpeed;
   private final float ySpeed;
   private final float zSpeed;
   private final int count;
   private final boolean overrideLimiter;
   private final ParticleType<?> particle;

   public <T extends ParticleType<?>> CSSpawnParticlePacket(
      T pParticle,
      boolean pOverrideLimiter,
      double pX,
      double pY,
      double pZ,
      float pXDist,
      float pYDist,
      float pZDist,
      float xSpeed,
      float ySpeed,
      float zSpeed,
      int pCount
   ) {
      this.particle = pParticle;
      this.overrideLimiter = pOverrideLimiter;
      this.x = pX;
      this.y = pY;
      this.z = pZ;
      this.xDist = pXDist;
      this.yDist = pYDist;
      this.zDist = pZDist;
      this.xSpeed = xSpeed;
      this.ySpeed = ySpeed;
      this.zSpeed = zSpeed;
      this.count = pCount;
   }

   public CSSpawnParticlePacket(FriendlyByteBuf buffer) {
      ParticleType<?> particletype = (ParticleType<?>)ForgeRegistries.PARTICLE_TYPES.getValue(buffer.m_130281_());
      this.overrideLimiter = buffer.readBoolean();
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.xDist = buffer.readFloat();
      this.yDist = buffer.readFloat();
      this.zDist = buffer.readFloat();
      this.xSpeed = buffer.readFloat();
      this.ySpeed = buffer.readFloat();
      this.zSpeed = buffer.readFloat();
      this.count = buffer.readInt();
      this.particle = particletype;
   }

   public void toBytes(FriendlyByteBuf buffer) {
      buffer.m_130085_(ForgeRegistries.PARTICLE_TYPES.getKey(this.particle));
      buffer.writeBoolean(this.overrideLimiter);
      buffer.writeDouble(this.x);
      buffer.writeDouble(this.y);
      buffer.writeDouble(this.z);
      buffer.writeFloat(this.xDist);
      buffer.writeFloat(this.yDist);
      buffer.writeFloat(this.zDist);
      buffer.writeFloat(this.xSpeed);
      buffer.writeFloat(this.ySpeed);
      buffer.writeFloat(this.zSpeed);
      buffer.writeInt(this.count);
   }

   public boolean isOverrideLimiter() {
      return this.overrideLimiter;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getXDist() {
      return this.xDist;
   }

   public float getYDist() {
      return this.yDist;
   }

   public float getZDist() {
      return this.zDist;
   }

   public float getXSpeed() {
      return this.xSpeed;
   }

   public float getYSpeed() {
      return this.ySpeed;
   }

   public float getZSpeed() {
      return this.zSpeed;
   }

   public int getCount() {
      return this.count;
   }

   public ParticleType<?> getParticle() {
      return this.particle;
   }

   public boolean handle(Supplier<Context> supplier) {
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.f_91073_ == null) {
         return false;
      } else {
         if (this.getCount() == 0) {
            double d0 = (double)this.getXSpeed();
            double d2 = (double)this.getYSpeed();
            double d4 = (double)this.getZSpeed();

            try {
               minecraft.f_91073_.m_6485_((ParticleOptions)this.getParticle(), true, this.getX(), this.getY(), this.getZ(), d0, d2, d4);
            } catch (Throwable var18) {
               Celestisynth.LOGGER.warn("Could not spawn particle effect {}", this.getParticle());
            }
         } else {
            for (int i = 0; i < this.getCount(); i++) {
               double d1 = minecraft.f_91073_.f_46441_.m_188583_() * (double)this.getXDist();
               double d3 = minecraft.f_91073_.f_46441_.m_188583_() * (double)this.getYDist();
               double d5 = minecraft.f_91073_.f_46441_.m_188583_() * (double)this.getZDist();
               double d6 = (double)this.getXSpeed();
               double d7 = (double)this.getYSpeed();
               double d8 = (double)this.getZSpeed();

               try {
                  minecraft.f_91073_.m_6485_((ParticleOptions)this.getParticle(), true, this.getX() + d1, this.getY() + d3, this.getZ() + d5, d6, d7, d8);
               } catch (Throwable var17) {
                  Celestisynth.LOGGER.warn("Could not spawn particle effect {}", this.getParticle());
               }
            }
         }

         return true;
      }
   }
}
