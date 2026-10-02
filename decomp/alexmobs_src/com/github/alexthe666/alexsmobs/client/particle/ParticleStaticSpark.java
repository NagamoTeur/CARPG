package com.github.alexthe666.alexsmobs.client.particle;

import com.github.alexthe666.alexsmobs.client.render.AMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleStaticSpark extends Particle {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      new ResourceLocation("textures/particle/generic_0.png"),
      new ResourceLocation("textures/particle/generic_1.png"),
      new ResourceLocation("textures/particle/generic_2.png"),
      new ResourceLocation("textures/particle/generic_3.png"),
      new ResourceLocation("textures/particle/generic_4.png"),
      new ResourceLocation("textures/particle/generic_5.png"),
      new ResourceLocation("textures/particle/generic_6.png"),
      new ResourceLocation("textures/particle/generic_7.png")
   };
   private int decrement = 1;
   private int textureIndex = 0;
   private float size;

   private ParticleStaticSpark(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
      super(world, x, y, z);
      this.m_107250_(1.0F, 1.0F);
      this.f_107226_ = 0.0F;
      this.f_107215_ = motionX;
      this.f_107216_ = motionY;
      this.f_107217_ = motionZ;
      this.f_107225_ = 10 + this.f_107223_.m_188503_(15);
      this.textureIndex = Mth.m_14045_(this.f_107223_.m_188503_(8), 0, 7);
      this.decrement = this.textureIndex > 0 ? this.f_107225_ / this.textureIndex : this.f_107225_;
      this.size = this.f_107223_.m_188501_() * 0.2F + 0.2F;
   }

   public void m_5989_() {
      super.m_5989_();
      this.f_107215_ *= 0.97;
      this.f_107216_ *= 0.97;
      this.f_107217_ *= 0.97;
      if (this.textureIndex > 0 && this.f_107224_ % this.decrement == 0) {
         this.textureIndex--;
      }

      if (this.size > 0.2F) {
         this.size -= 0.015F;
      }
   }

   public void m_5744_(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Quaternion quaternion;
      if (this.f_107231_ == 0.0F) {
         quaternion = camera.m_90591_();
      } else {
         quaternion = new Quaternion(camera.m_90591_());
         float f3 = Mth.m_14179_(partialTick, this.f_107204_, this.f_107231_);
         quaternion.m_80148_(Vector3f.f_122227_.m_122270_(f3));
      }

      BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      VertexConsumer portalStatic = VertexMultiConsumer.m_86168_(
         multibuffersource$buffersource.m_6299_(AMRenderTypes.STATIC_PARTICLE),
         multibuffersource$buffersource.m_6299_(RenderType.m_110473_(TEXTURES[this.textureIndex]))
      );
      PoseStack posestack = new PoseStack();
      Pose posestack$pose = posestack.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.m_122251_(quaternion);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = this.size;

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
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_7421_(f8, f6)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_7421_(f8, f5)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_7421_(f7, f5)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(j)
         .m_85977_(matrix3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
      portalStatic.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_())
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
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
         return new ParticleStaticSpark(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
      }
   }
}
