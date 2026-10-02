package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.model.tools.MathUtils;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.client.Camera;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleOrb extends TextureSheetParticle {
   private double targetX;
   private double targetY;
   private double targetZ;
   private double startX;
   private double startY;
   private double startZ;
   private double signX;
   private double signZ;
   private float red;
   private float green;
   private float blue;
   private int mode;
   private double duration;

   public ParticleOrb(ClientLevel world, double x, double y, double z, double targetX, double targetZ) {
      super(world, x, y, z);
      this.targetX = targetX;
      this.targetZ = targetZ;
      this.f_107663_ = (4.5F + this.f_107223_.m_188501_() * 1.5F) * 0.1F;
      this.f_107225_ = 120;
      this.signX = Math.signum(targetX - x);
      this.signZ = Math.signum(targetZ - z);
      this.mode = 0;
      this.f_107230_ = 0.0F;
      this.red = this.green = this.blue = 1.0F;
   }

   public ParticleOrb(ClientLevel world, double x, double y, double z, double targetX, double targetY, double targetZ, double speed) {
      this(world, x, y, z, targetX, targetZ);
      this.targetY = targetY;
      this.startX = x;
      this.startY = y;
      this.startZ = z;
      this.duration = speed;
      this.mode = 1;
      this.f_107230_ = 0.1F;
   }

   public ParticleOrb(
      ClientLevel world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, double scale, int duration
   ) {
      super(world, x, y, z);
      this.f_107663_ = (float)scale * 0.1F;
      this.f_107225_ = duration;
      this.duration = (double)duration;
      this.f_107215_ = vx;
      this.f_107216_ = vy;
      this.f_107217_ = vz;
      this.m_107253_((float)r, (float)g, (float)b);
      this.mode = 2;
   }

   public ParticleRenderType m_7556_() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public int m_6355_(float delta) {
      return 240 | super.m_6355_(delta) & 0xFF0000;
   }

   public void m_5989_() {
      this.f_107230_ = 0.1F;
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.mode == 0) {
         double vecX = this.targetX - this.f_107212_;
         double vecZ = this.targetZ - this.f_107214_;
         double dist = Math.sqrt(vecX * vecX + vecZ * vecZ);
         if (dist > 2.0 || Math.signum(vecX) != this.signX || Math.signum(vecZ) != this.signZ || this.f_107224_ > this.f_107225_) {
            this.m_107274_();
            return;
         }

         double peak = 0.5;
         this.f_107230_ = (float)(dist > 0.5 ? MathUtils.linearTransformd(dist, 0.5, 2.0, 1.0, 0.0) : MathUtils.linearTransformd(dist, 0.1F, 0.5, 0.0, 1.0));
         double minVel = 0.05;
         double maxVel = 0.3;
         double progress = Math.sin((-Math.PI / 4) * dist) + 1.0;
         double magMultipler = (progress * 0.25 + 0.05) / dist;
         vecX *= magMultipler;
         vecZ *= magMultipler;
         this.f_107215_ = vecX;
         this.f_107216_ = progress;
         this.f_107217_ = vecZ;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      } else if (this.mode == 1) {
         this.f_107230_ = (float)this.f_107224_ / (float)this.duration;
         this.f_107212_ = this.startX
            + (this.targetX - this.startX) / (1.0 + Math.exp(-(8.0 / this.duration) * ((double)this.f_107224_ - this.duration / 2.0)));
         this.f_107213_ = this.startY
            + (this.targetY - this.startY) / (1.0 + Math.exp(-(8.0 / this.duration) * ((double)this.f_107224_ - this.duration / 2.0)));
         this.f_107214_ = this.startZ
            + (this.targetZ - this.startZ) / (1.0 + Math.exp(-(8.0 / this.duration) * ((double)this.f_107224_ - this.duration / 2.0)));
         if ((double)this.f_107224_ == this.duration) {
            this.m_107274_();
         }
      } else if (this.mode == 2) {
         super.m_5989_();
         if (this.f_107224_ >= this.f_107225_) {
            this.m_107274_();
         }
      }

      this.f_107224_++;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      if (this.mode == 2) {
         this.f_107230_ = Math.max(1.0F - ((float)this.f_107224_ + partialTicks) / (float)this.duration, 0.001F);
      } else {
         this.f_107230_ = ((float)this.f_107224_ + partialTicks) / (float)this.duration;
      }

      this.f_107227_ = this.red;
      this.f_107228_ = this.green;
      this.f_107229_ = this.blue;
      super.m_5744_(buffer, renderInfo, partialTicks);
   }

   public static class OrbData implements ParticleOptions {
      public static final Deserializer<ParticleOrb.OrbData> DESERIALIZER = new Deserializer<ParticleOrb.OrbData>() {
         public ParticleOrb.OrbData fromCommand(ParticleType<ParticleOrb.OrbData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = (float)reader.readDouble();
            reader.expect(' ');
            float g = (float)reader.readDouble();
            reader.expect(' ');
            float b = (float)reader.readDouble();
            reader.expect(' ');
            float scale = (float)reader.readDouble();
            reader.expect(' ');
            int duration = reader.readInt();
            return new ParticleOrb.OrbData(r, g, b, scale, duration);
         }

         public ParticleOrb.OrbData fromNetwork(ParticleType<ParticleOrb.OrbData> particleTypeIn, FriendlyByteBuf buffer) {
            return new ParticleOrb.OrbData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt());
         }
      };
      private final float r;
      private final float g;
      private final float b;
      private float scale;
      private int duration;
      private float targetX;
      private float targetY;
      private float targetZ;
      private float speed;
      private int mode;

      public OrbData(float targetX, float targetZ) {
         this.targetX = targetX;
         this.targetZ = targetZ;
         this.r = this.g = this.b = 1.0F;
         this.mode = 0;
      }

      public OrbData(float targetX, float targetY, float targetZ, float speed) {
         this(targetX, targetZ);
         this.targetY = targetY;
         this.speed = speed;
         this.mode = 1;
      }

      public OrbData(float r, float g, float b, float scale, int duration) {
         this.r = r;
         this.g = g;
         this.b = b;
         this.scale = scale;
         this.duration = duration;
         this.mode = 2;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.r);
         buffer.writeFloat(this.g);
         buffer.writeFloat(this.b);
         buffer.writeFloat(this.scale);
         buffer.writeInt(this.duration);
      }

      public String m_5942_() {
         return String.format(
            Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d", Registry.f_122829_.m_7981_(this.m_6012_()), this.r, this.g, this.b, this.scale, this.duration
         );
      }

      public ParticleType<ParticleOrb.OrbData> m_6012_() {
         return (ParticleType<ParticleOrb.OrbData>)ParticleHandler.ORB.get();
      }

      @OnlyIn(Dist.CLIENT)
      public float getR() {
         return this.r;
      }

      @OnlyIn(Dist.CLIENT)
      public float getG() {
         return this.g;
      }

      @OnlyIn(Dist.CLIENT)
      public float getB() {
         return this.b;
      }

      @OnlyIn(Dist.CLIENT)
      public float getScale() {
         return this.scale;
      }

      @OnlyIn(Dist.CLIENT)
      public int getDuration() {
         return this.duration;
      }

      @OnlyIn(Dist.CLIENT)
      public float getTargetX() {
         return this.targetX;
      }

      @OnlyIn(Dist.CLIENT)
      public float getTargetY() {
         return this.targetY;
      }

      @OnlyIn(Dist.CLIENT)
      public float getTargetZ() {
         return this.targetZ;
      }

      @OnlyIn(Dist.CLIENT)
      public float getSpeed() {
         return this.speed;
      }

      @OnlyIn(Dist.CLIENT)
      public int getMode() {
         return this.mode;
      }

      public static Codec<ParticleOrb.OrbData> CODEC(ParticleType<ParticleOrb.OrbData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("r").forGetter(ParticleOrb.OrbData::getR),
                     Codec.FLOAT.fieldOf("g").forGetter(ParticleOrb.OrbData::getG),
                     Codec.FLOAT.fieldOf("b").forGetter(ParticleOrb.OrbData::getB),
                     Codec.FLOAT.fieldOf("scale").forGetter(ParticleOrb.OrbData::getScale),
                     Codec.INT.fieldOf("duration").forGetter(ParticleOrb.OrbData::getDuration)
                  )
                  .apply(codecBuilder, ParticleOrb.OrbData::new)
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class OrbFactory implements ParticleProvider<ParticleOrb.OrbData> {
      private final SpriteSet spriteSet;

      public OrbFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(ParticleOrb.OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleOrb particle;
         if (typeIn.getMode() == 0) {
            particle = new ParticleOrb(worldIn, x, y, z, (double)typeIn.getTargetX(), (double)typeIn.getTargetZ());
         } else if (typeIn.getMode() == 1) {
            particle = new ParticleOrb(
               worldIn, x, y, z, (double)typeIn.getTargetX(), (double)typeIn.getTargetY(), (double)typeIn.getTargetZ(), (double)typeIn.getSpeed()
            );
         } else {
            particle = new ParticleOrb(
               worldIn,
               x,
               y,
               z,
               xSpeed,
               ySpeed,
               zSpeed,
               (double)typeIn.getR(),
               (double)typeIn.getG(),
               (double)typeIn.getB(),
               (double)typeIn.getScale(),
               typeIn.getDuration()
            );
         }

         particle.m_108339_(this.spriteSet);
         return particle;
      }
   }
}
