package com.cerbon.bosses_of_mass_destruction.particle;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.function.Function;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SimpleParticle extends TextureSheetParticle {
   private final ParticleContext particleContext;
   private final IParticleGeometry particleGeometry;
   private Function<Float, Integer> brightnessOverride;
   private Function<Float, Float> scaleOverride;
   private Function<Float, Vec3> colorOverride;
   private Function<SimpleParticle, Vec3> velocityOverride;
   private Function<SimpleParticle, Vec3> positionOverride;
   private Function<SimpleParticle, Float> rotationOverride;
   private Vec3 colorVariation = Vec3.f_82478_;
   private float rotation = 0.0F;
   private float prevRotation = 0.0F;
   public float ageRatio = 1.0F;
   private final boolean cycleSprites;

   public SimpleParticle(ParticleContext particleContext, int particleAge, IParticleGeometry particleGeometry, boolean cycleSprites, boolean doCollision) {
      super(particleContext.level(), particleContext.pos().m_7096_(), particleContext.pos().m_7098_(), particleContext.pos().m_7094_());
      this.particleContext = particleContext;
      this.particleGeometry = particleGeometry;
      this.cycleSprites = cycleSprites;
      this.f_107225_ = particleAge;
      if (cycleSprites) {
         this.m_108339_(particleContext.spriteSet());
      } else {
         this.m_108337_(particleContext.spriteSet().m_213979_(this.f_107223_));
      }

      this.f_107215_ = particleContext.vel().m_7096_();
      this.f_107216_ = particleContext.vel().m_7098_();
      this.f_107217_ = particleContext.vel().m_7094_();
      this.f_107219_ = doCollision;
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   @NotNull
   public Vec3 getPos() {
      return new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
   }

   public int getAge() {
      return this.f_107224_;
   }

   public void m_5989_() {
      super.m_5989_();
      if (this.m_107276_()) {
         if (this.cycleSprites) {
            this.m_108339_(this.particleContext.spriteSet());
         }

         this.ageRatio = (float)this.f_107224_ / (float)this.f_107225_;
         this.setColorFromOverride(this.colorOverride, this.ageRatio);
         this.setScaleFromOverride(this.scaleOverride, this.ageRatio);
         this.setVelocityFromOverride(this.velocityOverride);
         this.setPositionFromOverride(this.positionOverride);
         this.setRotationFromOverride(this.rotationOverride);
      }
   }

   private void setRotationFromOverride(Function<SimpleParticle, Float> rotationOverride) {
      if (rotationOverride != null) {
         float rot = rotationOverride.apply(this);
         this.prevRotation = this.rotation;
         this.rotation = rot;
      }
   }

   private void setVelocityFromOverride(Function<SimpleParticle, Vec3> velocityOverride) {
      if (velocityOverride != null) {
         Vec3 velocity = velocityOverride.apply(this);
         this.f_107215_ = velocity.m_7096_();
         this.f_107216_ = velocity.m_7098_();
         this.f_107217_ = velocity.m_7094_();
      }
   }

   private void setPositionFromOverride(Function<SimpleParticle, Vec3> positionOverride) {
      if (positionOverride != null) {
         Vec3 pos = positionOverride.apply(this);
         this.m_107264_(pos.m_7096_(), pos.m_7098_(), pos.m_7094_());
      }
   }

   private void setScaleFromOverride(Function<Float, Float> scaleOverride, float ageRatio) {
      if (scaleOverride != null) {
         this.f_107663_ = scaleOverride.apply(ageRatio);
         this.m_107250_(0.2F * this.f_107663_, 0.2F * this.f_107663_);
      }
   }

   private void setColorFromOverride(Function<Float, Vec3> colorOverride, float ageRatio) {
      if (colorOverride != null) {
         Vec3 color = colorOverride.apply(ageRatio);
         Vec3 variedColor = VecUtils.coerceAtMost(VecUtils.coerceAtLeast(color.m_82549_(this.colorVariation), Vec3.f_82478_), VecUtils.unit);
         this.m_107253_((float)variedColor.m_7096_(), (float)variedColor.m_7098_(), (float)variedColor.m_7094_());
      }
   }

   public void setBrightnessOverride(Function<Float, Integer> override) {
      this.brightnessOverride = override;
   }

   public void setColorOverride(Function<Float, Vec3> override) {
      this.colorOverride = override;
      this.setColorFromOverride(override, 0.0F);
   }

   public void setScaleOverride(Function<Float, Float> override) {
      this.scaleOverride = override;
      this.setScaleFromOverride(override, 0.0F);
   }

   public void setColorVariation(double variation) {
      this.colorVariation = RandomUtils.randVec().m_82542_(variation, variation, variation);
      this.setColorFromOverride(this.colorOverride, 0.0F);
   }

   public void setVelocityOverride(Function<SimpleParticle, Vec3> override) {
      this.velocityOverride = override;
   }

   public void setPositionOverride(Function<SimpleParticle, Vec3> override) {
      this.positionOverride = override;
   }

   public void setRotationOverride(Function<SimpleParticle, Float> override) {
      this.rotationOverride = override;
      if (this.rotationOverride != null) {
         this.rotation = this.rotationOverride.apply(this);
         this.prevRotation = this.rotationOverride.apply(this);
      }
   }

   protected int m_6355_(float partialTick) {
      return this.brightnessOverride != null ? this.brightnessOverride.apply(this.ageRatio) : super.m_6355_(partialTick);
   }

   public void m_5744_(VertexConsumer vertexConsumer, @NotNull Camera camera, float partialTicks) {
      Vector3f[] vector3fs = this.particleGeometry
         .getGeometry(
            camera,
            partialTicks,
            this.f_107209_,
            this.f_107210_,
            this.f_107211_,
            this.f_107212_,
            this.f_107213_,
            this.f_107214_,
            this.m_5902_(partialTicks),
            Mth.m_14179_(partialTicks, this.prevRotation, this.rotation)
         );
      float l = this.m_5970_();
      float m = this.m_5952_();
      float n = this.m_5951_();
      float o = this.m_5950_();
      float p = (float)this.m_6355_(partialTicks);
      vertexConsumer.m_5483_((double)vector3fs[0].m_122239_(), (double)vector3fs[0].m_122260_(), (double)vector3fs[0].m_122269_())
         .m_7421_(m, o)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_((int)p)
         .m_5752_();
      vertexConsumer.m_5483_((double)vector3fs[1].m_122239_(), (double)vector3fs[1].m_122260_(), (double)vector3fs[1].m_122269_())
         .m_7421_(m, n)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_((int)p)
         .m_5752_();
      vertexConsumer.m_5483_((double)vector3fs[2].m_122239_(), (double)vector3fs[2].m_122260_(), (double)vector3fs[2].m_122269_())
         .m_7421_(l, n)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_((int)p)
         .m_5752_();
      vertexConsumer.m_5483_((double)vector3fs[3].m_122239_(), (double)vector3fs[3].m_122260_(), (double)vector3fs[3].m_122269_())
         .m_7421_(l, o)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_((int)p)
         .m_5752_();
   }
}
