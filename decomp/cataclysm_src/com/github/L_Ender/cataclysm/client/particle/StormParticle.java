package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.init.ModParticle;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class StormParticle extends AbstractTrailParticle {
   private static final ResourceLocation TRAIL_TEXTURE = new ResourceLocation("cataclysm", "textures/particle/storm.png");
   private final int EntityId;
   private final float width;
   private final float height;
   private final float initialYRot;
   private final float rotateByAge;

   public StormParticle(ClientLevel world, double x, double y, double z, float r, float g, float b, float width, float height, int EntityId) {
      super(world, x, y, z, 0.0, 0.0, 0.0, r, g, b);
      this.EntityId = EntityId;
      this.f_107226_ = 0.0F;
      this.f_107225_ = 20 + this.f_107223_.m_188503_(20);
      this.initialYRot = this.f_107223_.m_188501_() * 360.0F;
      this.rotateByAge = (10.0F + this.f_107223_.m_188501_() * 10.0F) * (this.f_107223_.m_188499_() ? -1.0F : 1.0F);
      this.width = width;
      this.height = height;
      Vec3 vec3 = this.getOrbitPosition();
      this.f_107212_ = this.f_107209_ = vec3.f_82479_;
      this.f_107213_ = this.f_107210_ = vec3.f_82480_;
      this.f_107214_ = this.f_107211_ = vec3.f_82481_;
      this.f_107215_ = 0.0;
      this.f_107216_ = 0.0;
      this.f_107217_ = 0.0;
   }

   public Vec3 getEntityPosition() {
      Entity from = this.getFromEntity();
      return from != null ? from.m_20182_() : new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
   }

   public Entity getFromEntity() {
      return this.EntityId == -1 ? null : this.f_107208_.m_6815_(this.EntityId);
   }

   public Vec3 getOrbitPosition() {
      Vec3 dinoPos = this.getEntityPosition();
      Vec3 vec3 = new Vec3(0.0, (double)this.height, (double)this.width)
         .m_82524_((float)Math.toRadians((double)(this.initialYRot + this.rotateByAge * (float)this.f_107224_)));
      return dinoPos.m_82549_(vec3);
   }

   @Override
   public void m_5989_() {
      super.m_5989_();
      float fade = 1.0F - (float)this.f_107224_ / (float)this.f_107225_;
      this.trailA = 1.0F * fade;
      Vec3 vec3 = this.getOrbitPosition();
      this.f_107212_ = vec3.f_82479_;
      this.f_107213_ = vec3.f_82480_;
      this.f_107214_ = vec3.f_82481_;
      Entity from = this.getFromEntity();
      if (from == null) {
         this.m_107274_();
      }
   }

   @Override
   public int sampleCount() {
      return 4;
   }

   @Override
   public int sampleStep() {
      return 1;
   }

   @Override
   public float getTrailHeight() {
      return 0.5F;
   }

   public int m_6355_(float f) {
      return 240;
   }

   @Override
   public ResourceLocation getTrailTexture() {
      return TRAIL_TEXTURE;
   }

   public static class OrbData implements ParticleOptions {
      public static final Deserializer<StormParticle.OrbData> DESERIALIZER = new Deserializer<StormParticle.OrbData>() {
         public StormParticle.OrbData fromCommand(ParticleType<StormParticle.OrbData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float width = reader.readFloat();
            reader.expect(' ');
            float height = reader.readFloat();
            reader.expect(' ');
            int EntityId = reader.readInt();
            return new StormParticle.OrbData(r, g, b, width, height, EntityId);
         }

         public StormParticle.OrbData fromNetwork(ParticleType<StormParticle.OrbData> particleTypeIn, FriendlyByteBuf buffer) {
            return new StormParticle.OrbData(
               buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readInt()
            );
         }
      };
      private final float r;
      private final float g;
      private final float b;
      private final float width;
      private final float height;
      private final int entityid;

      public OrbData(float r, float g, float b, float width, float height, int entityid) {
         this.r = r;
         this.g = g;
         this.b = b;
         this.width = width;
         this.height = height;
         this.entityid = entityid;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.r);
         buffer.writeFloat(this.g);
         buffer.writeFloat(this.b);
         buffer.writeFloat(this.width);
         buffer.writeFloat(this.height);
         buffer.writeInt(this.entityid);
      }

      public String m_5942_() {
         return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %.2f %d",
            ForgeRegistries.PARTICLE_TYPES.getKey(this.m_6012_()),
            this.r,
            this.g,
            this.b,
            this.width,
            this.height,
            this.entityid
         );
      }

      public ParticleType<StormParticle.OrbData> m_6012_() {
         return (ParticleType<StormParticle.OrbData>)ModParticle.STORM.get();
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
      public float getWidth() {
         return this.width;
      }

      @OnlyIn(Dist.CLIENT)
      public float getHeight() {
         return this.height;
      }

      @OnlyIn(Dist.CLIENT)
      public int getentityid() {
         return this.entityid;
      }

      public static Codec<StormParticle.OrbData> CODEC(ParticleType<StormParticle.OrbData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("r").forGetter(StormParticle.OrbData::getR),
                     Codec.FLOAT.fieldOf("g").forGetter(StormParticle.OrbData::getG),
                     Codec.FLOAT.fieldOf("b").forGetter(StormParticle.OrbData::getB),
                     Codec.FLOAT.fieldOf("width").forGetter(StormParticle.OrbData::getWidth),
                     Codec.FLOAT.fieldOf("height").forGetter(StormParticle.OrbData::getHeight),
                     Codec.INT.fieldOf("entityid").forGetter(StormParticle.OrbData::getentityid)
                  )
                  .apply(codecBuilder, StormParticle.OrbData::new)
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class OrbFactory implements ParticleProvider<StormParticle.OrbData> {
      public Particle createParticle(
         StormParticle.OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new StormParticle(worldIn, x, y, z, typeIn.getR(), typeIn.getG(), typeIn.getB(), typeIn.getWidth(), typeIn.getHeight(), typeIn.getentityid());
      }
   }
}
