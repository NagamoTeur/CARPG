package com.bobmowzie.mowziesmobs.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleVanillaCloudExtended extends TextureSheetParticle {
   private final SpriteSet animatedSprite;
   private final float oSize;
   private final float airDrag;
   private final float red;
   private final float green;
   private final float blue;
   private final Vec3[] destination;

   protected ParticleVanillaCloudExtended(
      ClientLevel worldIn,
      SpriteSet animatedSprite,
      double xCoordIn,
      double yCoordIn,
      double zCoordIn,
      double motionX,
      double motionY,
      double motionZ,
      double scale,
      double r,
      double g,
      double b,
      double drag,
      double duration,
      Vec3[] destination
   ) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0, 0.0, 0.0);
      this.f_107215_ *= 0.1F;
      this.f_107216_ *= 0.1F;
      this.f_107217_ *= 0.1F;
      this.f_107215_ += motionX;
      this.f_107216_ += motionY;
      this.f_107217_ += motionZ;
      float f1 = 1.0F - this.f_107223_.m_188501_() * 0.3F;
      this.red = (float)((double)f1 * r);
      this.green = (float)((double)f1 * g);
      this.blue = (float)((double)f1 * b);
      this.f_107663_ *= 0.75F;
      this.f_107663_ *= 2.5F;
      this.oSize = this.f_107663_ * (float)scale;
      this.f_107225_ = (int)duration;
      if (this.f_107225_ == 0) {
         this.f_107225_ = 1;
      }

      this.airDrag = (float)drag;
      this.destination = destination;
      this.f_107219_ = false;
      this.animatedSprite = animatedSprite;
      if (destination != null) {
         this.m_108337_(animatedSprite.m_5819_(this.f_107225_ - this.f_107224_, this.f_107225_));
      } else {
         this.m_108339_(this.animatedSprite);
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      }

      this.m_108339_(this.animatedSprite);
      if (this.destination != null && this.destination.length == 1) {
         this.m_108337_(this.animatedSprite.m_5819_(this.f_107225_ - this.f_107224_, this.f_107225_));
         Vec3 destinationVec = this.destination[0];
         Vec3 diff = destinationVec.m_82546_(new Vec3(this.f_107212_, this.f_107213_, this.f_107214_));
         if (diff.m_82553_() < 0.5) {
            this.m_107274_();
         }

         float attractScale = 0.7F * ((float)this.f_107224_ / (float)this.f_107225_) * ((float)this.f_107224_ / (float)this.f_107225_);
         this.f_107215_ = diff.f_82479_ * (double)attractScale;
         this.f_107216_ = diff.f_82480_ * (double)attractScale;
         this.f_107217_ = diff.f_82481_ * (double)attractScale;
      }

      this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      this.f_107215_ = this.f_107215_ * (double)this.airDrag;
      this.f_107216_ = this.f_107216_ * (double)this.airDrag;
      this.f_107217_ = this.f_107217_ * (double)this.airDrag;
      if (this.f_107218_) {
         this.f_107215_ *= 0.7F;
         this.f_107217_ *= 0.7F;
      }
   }

   public static void spawnVanillaCloud(
      Level world,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      double scale,
      double r,
      double g,
      double b,
      double drag,
      double duration
   ) {
      world.m_7106_(
         new ParticleVanillaCloudExtended.VanillaCloudData((float)scale, (float)r, (float)g, (float)b, (float)drag, (float)duration, null),
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   public static void spawnVanillaCloudDestination(
      Level world,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      double scale,
      double r,
      double g,
      double b,
      double drag,
      double duration,
      Vec3[] destination
   ) {
      world.m_7106_(
         new ParticleVanillaCloudExtended.VanillaCloudData((float)scale, (float)r, (float)g, (float)b, (float)drag, (float)duration, destination),
         x,
         y,
         z,
         motionX,
         motionY,
         motionZ
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static final class CloudFactory implements ParticleProvider<ParticleVanillaCloudExtended.VanillaCloudData> {
      private final SpriteSet spriteSet;

      public CloudFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleVanillaCloudExtended.VanillaCloudData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleVanillaCloudExtended particle = new ParticleVanillaCloudExtended(
            worldIn,
            this.spriteSet,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            (double)typeIn.getScale(),
            (double)typeIn.getRed(),
            (double)typeIn.getGreen(),
            (double)typeIn.getBlue(),
            (double)typeIn.getDrag(),
            (double)typeIn.getDuration(),
            typeIn.getDestination()
         );
         particle.m_107253_(typeIn.getRed(), typeIn.getGreen(), typeIn.getBlue());
         return particle;
      }
   }

   public static class VanillaCloudData implements ParticleOptions {
      public static final Deserializer<ParticleVanillaCloudExtended.VanillaCloudData> DESERIALIZER = new Deserializer<ParticleVanillaCloudExtended.VanillaCloudData>() {
         public ParticleVanillaCloudExtended.VanillaCloudData fromCommand(
            ParticleType<ParticleVanillaCloudExtended.VanillaCloudData> particleTypeIn, StringReader reader
         ) throws CommandSyntaxException {
            reader.expect(' ');
            float scale = (float)reader.readDouble();
            reader.expect(' ');
            float red = (float)reader.readDouble();
            reader.expect(' ');
            float green = (float)reader.readDouble();
            reader.expect(' ');
            float blue = (float)reader.readDouble();
            reader.expect(' ');
            float drag = (float)reader.readDouble();
            reader.expect(' ');
            float duration = (float)reader.readDouble();
            return new ParticleVanillaCloudExtended.VanillaCloudData(scale, red, green, blue, drag, duration, null);
         }

         public ParticleVanillaCloudExtended.VanillaCloudData fromNetwork(
            ParticleType<ParticleVanillaCloudExtended.VanillaCloudData> particleTypeIn, FriendlyByteBuf buffer
         ) {
            return new ParticleVanillaCloudExtended.VanillaCloudData(
               buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), null
            );
         }
      };
      private final float red;
      private final float green;
      private final float blue;
      private final float scale;
      private final float drag;
      private final float duration;
      private final Vec3[] destination;

      public VanillaCloudData(float scale, float redIn, float greenIn, float blueIn, float drag, float duration, Vec3[] destination) {
         this.red = redIn;
         this.green = greenIn;
         this.blue = blueIn;
         this.scale = scale;
         this.drag = drag;
         this.duration = duration;
         this.destination = destination;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.scale);
         buffer.writeFloat(this.red);
         buffer.writeFloat(this.green);
         buffer.writeFloat(this.blue);
         buffer.writeFloat(this.drag);
         buffer.writeFloat(this.duration);
      }

      public String m_5942_() {
         return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %.2f %.2f",
            Registry.f_122829_.m_7981_(this.m_6012_()),
            this.scale,
            this.red,
            this.green,
            this.blue,
            this.drag,
            this.duration
         );
      }

      public ParticleType<ParticleVanillaCloudExtended.VanillaCloudData> m_6012_() {
         return (ParticleType<ParticleVanillaCloudExtended.VanillaCloudData>)ParticleHandler.VANILLA_CLOUD_EXTENDED.get();
      }

      @OnlyIn(Dist.CLIENT)
      public float getScale() {
         return this.scale;
      }

      @OnlyIn(Dist.CLIENT)
      public float getRed() {
         return this.red;
      }

      @OnlyIn(Dist.CLIENT)
      public float getGreen() {
         return this.green;
      }

      @OnlyIn(Dist.CLIENT)
      public float getBlue() {
         return this.blue;
      }

      @OnlyIn(Dist.CLIENT)
      public float getDrag() {
         return this.drag;
      }

      @OnlyIn(Dist.CLIENT)
      public float getDuration() {
         return this.duration;
      }

      @OnlyIn(Dist.CLIENT)
      public Vec3[] getDestination() {
         return this.destination;
      }

      public static Codec<ParticleVanillaCloudExtended.VanillaCloudData> CODEC(ParticleType<ParticleVanillaCloudExtended.VanillaCloudData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("r").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getRed),
                     Codec.FLOAT.fieldOf("g").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getGreen),
                     Codec.FLOAT.fieldOf("b").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getBlue),
                     Codec.FLOAT.fieldOf("scale").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getScale),
                     Codec.FLOAT.fieldOf("duration").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getDuration),
                     Codec.FLOAT.fieldOf("drag").forGetter(ParticleVanillaCloudExtended.VanillaCloudData::getScale)
                  )
                  .apply(
                     codecBuilder, (r, g, b, scale, duration, drag) -> new ParticleVanillaCloudExtended.VanillaCloudData(r, g, b, scale, drag, duration, null)
                  )
         );
      }
   }
}
