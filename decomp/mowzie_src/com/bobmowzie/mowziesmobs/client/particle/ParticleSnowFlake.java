package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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

public class ParticleSnowFlake extends TextureSheetParticle {
   private int swirlTick;
   private final float spread;
   boolean swirls;

   public ParticleSnowFlake(ClientLevel world, double x, double y, double z, double vX, double vY, double vZ, double duration, boolean swirls) {
      super(world, x, y, z);
      this.m_107250_(1.0F, 1.0F);
      this.f_107215_ = vX;
      this.f_107216_ = vY;
      this.f_107217_ = vZ;
      this.f_107225_ = (int)duration;
      this.swirlTick = this.f_107223_.m_188503_(120);
      this.spread = this.f_107223_.m_188501_();
      this.swirls = swirls;
   }

   protected float m_5952_() {
      return super.m_5952_() - (super.m_5952_() - super.m_5970_()) / 8.0F;
   }

   protected float m_5950_() {
      return super.m_5950_() - (super.m_5950_() - super.m_5951_()) / 8.0F;
   }

   public ParticleRenderType m_7556_() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public void m_5989_() {
      super.m_5989_();
      if (this.swirls) {
         Vector3f motionVec = new Vector3f((float)this.f_107215_, (float)this.f_107216_, (float)this.f_107217_);
         motionVec.m_122278_();
         float yaw = (float)Math.atan2((double)motionVec.m_122239_(), (double)motionVec.m_122269_());
         float pitch = (float)Math.atan2((double)motionVec.m_122260_(), 1.0);
         float swirlRadius = 4.0F * ((float)this.f_107224_ / (float)this.f_107225_) * this.spread;
         Quaternion quatSpin = motionVec.m_122270_((float)this.swirlTick * 0.2F);
         Quaternion quatOrient = new Quaternion(pitch, yaw, 0.0F, false);
         Vector3f vec = new Vector3f(swirlRadius, 0.0F, 0.0F);
         vec.m_122251_(quatOrient);
         vec.m_122251_(quatSpin);
         this.f_107212_ = this.f_107212_ + (double)vec.m_122239_();
         this.f_107213_ = this.f_107213_ + (double)vec.m_122260_();
         this.f_107214_ = this.f_107214_ + (double)vec.m_122269_();
      }

      if (this.f_107224_ >= this.f_107225_) {
         this.m_107274_();
      }

      this.f_107224_++;
      this.swirlTick++;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      this.f_107230_ = (float)(1.0 - Math.exp((double)(10.0F * (var - 1.0F))) - Math.pow(2000.0, (double)(-var)));
      if ((double)this.f_107230_ < 0.01) {
         this.f_107230_ = 0.01F;
      }

      super.m_5744_(buffer, renderInfo, partialTicks);
   }

   @OnlyIn(Dist.CLIENT)
   public static final class SnowFlakeFactory implements ParticleProvider<ParticleSnowFlake.SnowflakeData> {
      private final SpriteSet spriteSet;

      public SnowFlakeFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleSnowFlake.SnowflakeData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleSnowFlake particle = new ParticleSnowFlake(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, (double)typeIn.getDuration(), typeIn.getSwirls());
         particle.m_108335_(this.spriteSet);
         return particle;
      }
   }

   public static class SnowflakeData implements ParticleOptions {
      public static final Deserializer<ParticleSnowFlake.SnowflakeData> DESERIALIZER = new Deserializer<ParticleSnowFlake.SnowflakeData>() {
         public ParticleSnowFlake.SnowflakeData fromCommand(ParticleType<ParticleSnowFlake.SnowflakeData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float duration = (float)reader.readDouble();
            reader.expect(' ');
            boolean swirls = reader.readBoolean();
            return new ParticleSnowFlake.SnowflakeData(duration, swirls);
         }

         public ParticleSnowFlake.SnowflakeData fromNetwork(ParticleType<ParticleSnowFlake.SnowflakeData> particleTypeIn, FriendlyByteBuf buffer) {
            return new ParticleSnowFlake.SnowflakeData(buffer.readFloat(), buffer.readBoolean());
         }
      };
      private final float duration;
      private final boolean swirls;

      public SnowflakeData(float duration, boolean spins) {
         this.duration = duration;
         this.swirls = spins;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.duration);
         buffer.writeBoolean(this.swirls);
      }

      public String m_5942_() {
         return String.format(Locale.ROOT, "%s %.2f %b", Registry.f_122829_.m_7981_(this.m_6012_()), this.duration, this.swirls);
      }

      public ParticleType<ParticleSnowFlake.SnowflakeData> m_6012_() {
         return (ParticleType<ParticleSnowFlake.SnowflakeData>)ParticleHandler.SNOWFLAKE.get();
      }

      @OnlyIn(Dist.CLIENT)
      public float getDuration() {
         return this.duration;
      }

      @OnlyIn(Dist.CLIENT)
      public boolean getSwirls() {
         return this.swirls;
      }

      public static Codec<ParticleSnowFlake.SnowflakeData> CODEC(ParticleType<ParticleSnowFlake.SnowflakeData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("duration").forGetter(ParticleSnowFlake.SnowflakeData::getDuration),
                     Codec.BOOL.fieldOf("swirls").forGetter(ParticleSnowFlake.SnowflakeData::getSwirls)
                  )
                  .apply(codecBuilder, ParticleSnowFlake.SnowflakeData::new)
         );
      }
   }
}
