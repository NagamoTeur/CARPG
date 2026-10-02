package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EM_PulseParticle extends Particle {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/particle/em_pulse.png");
   private float size;
   private float prevSize;
   private float prevAlpha;
   private float alphaDecrease;

   private EM_PulseParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
      super(world, x, y, z);
      this.m_107250_(1.0F, 0.1F);
      this.f_107230_ = 1.0F;
      this.f_107226_ = 0.0F;
      this.f_107215_ = motionX;
      this.f_107216_ = motionY;
      this.f_107217_ = motionZ;
      this.f_107225_ = 20;
      this.alphaDecrease = 1.0F / Math.max((float)this.f_107225_, 1.0F);
      this.size = 0.1F;
   }

   public void m_5989_() {
      super.m_5989_();
      this.prevSize = this.size;
      this.prevAlpha = this.f_107230_;
      this.size += 0.3F;
      this.f_107215_ *= 0.1;
      this.f_107216_ *= 0.8;
      this.f_107217_ *= 0.1;
      if (this.f_107230_ > 0.0F) {
         this.f_107230_ = Math.max(this.f_107230_ - this.alphaDecrease, 0.0F);
      }

      this.m_107250_(1.0F + this.size, 0.1F);
   }

   public void m_5744_(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Quaternion quaternion = Vector3f.f_122223_.m_122240_(90.0F);
      BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      VertexConsumer portalStatic = multibuffersource$buffersource.m_6299_(CMRenderTypes.getPulse());
      PoseStack posestack = new PoseStack();
      Pose posestack$pose = posestack.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = this.prevSize + partialTick * (this.size - this.prevSize);
      float alphaLerp = this.prevAlpha + partialTick * (this.f_107230_ - this.prevAlpha);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.m_122251_(quaternion);
         vector3f.m_122261_(f4);
         vector3f.m_122272_(f, f1, f2);
      }

      float f7 = 0.0F;
      float f8 = 1.0F;
      float f5 = 0.0F;
      float f6 = 1.0F;
      int j = 240;
      portalStatic.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, alphaLerp)
         .m_7421_(f8, f6)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, alphaLerp)
         .m_7421_(f8, f5)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, alphaLerp)
         .m_7421_(f7, f5)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, alphaLerp)
         .m_7421_(f7, f6)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      multibuffersource$buffersource.m_109911_();
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new EM_PulseParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
      }
   }
}
