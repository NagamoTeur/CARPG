package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractLightTrailParticle extends Particle {
   private Vec3[] trailPositions = new Vec3[64];
   private int trailPointer = -1;
   public float r;
   public float g;
   public float b;
   protected float trailA = 0.4F;

   public AbstractLightTrailParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd, float r, float g, float b) {
      super(world, x, y, z);
      this.f_107215_ = xd;
      this.f_107216_ = yd;
      this.f_107217_ = zd;
      this.r = r;
      this.g = g;
      this.b = b;
   }

   public void m_5989_() {
      this.tickTrail();
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.f_107215_ *= 0.99;
      this.f_107216_ *= 0.99;
      this.f_107217_ *= 0.99;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107216_ = this.f_107216_ - (double)this.f_107226_;
      }
   }

   public void tickTrail() {
      Vec3 currentPosition = new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
      if (this.trailPointer == -1) {
         for (int i = 0; i < this.trailPositions.length; i++) {
            this.trailPositions[i] = currentPosition;
         }
      }

      if (++this.trailPointer == this.trailPositions.length) {
         this.trailPointer = 0;
      }

      this.trailPositions[this.trailPointer] = currentPosition;
   }

   public void m_5744_(VertexConsumer consumer, Camera camera, float partialTick) {
      if (this.trailPointer > -1) {
         BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
         VertexConsumer vertexconsumer = multibuffersource$buffersource.m_6299_(CMRenderTypes.CMEyes(this.getTrailTexture()));
         Vec3 cameraPos = camera.m_90583_();
         double x = (double)((float)Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_));
         double y = (double)((float)Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_));
         double z = (double)((float)Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_));
         PoseStack posestack = new PoseStack();
         posestack.m_85836_();
         posestack.m_85837_(-cameraPos.f_82479_, -cameraPos.f_82480_, -cameraPos.f_82481_);
         int samples = 0;
         Vec3 drawFrom = new Vec3(x, y, z);
         float zRot = this.getTrailRot(camera);
         Vec3 topAngleVec = new Vec3(0.0, (double)(this.getTrailHeight() / 2.0F), 0.0).m_82535_(zRot);
         Vec3 bottomAngleVec = new Vec3(0.0, (double)(this.getTrailHeight() / -2.0F), 0.0).m_82535_(zRot);
         int j = this.m_6355_(partialTick);

         while (samples < this.sampleCount()) {
            Vec3 sample = this.getTrailPosition(samples * this.sampleStep(), partialTick);
            float u1 = (float)samples / (float)this.sampleCount();
            float u2 = u1 + 1.0F / (float)this.sampleCount();
            Pose posestack$pose = posestack.m_85850_();
            Matrix4f matrix4f = posestack$pose.m_85861_();
            Matrix3f matrix3f = posestack$pose.m_85864_();
            vertexconsumer.m_85982_(
                  matrix4f,
                  (float)drawFrom.f_82479_ + (float)bottomAngleVec.f_82479_,
                  (float)drawFrom.f_82480_ + (float)bottomAngleVec.f_82480_,
                  (float)drawFrom.f_82481_ + (float)bottomAngleVec.f_82481_
               )
               .m_85950_(this.r, this.g, this.b, this.trailA)
               .m_7421_(u1, 1.0F)
               .m_86008_(OverlayTexture.f_118083_)
               .m_85969_(j)
               .m_85977_(matrix3f, 0.0F, 1.0F, 0.0F)
               .m_5752_();
            vertexconsumer.m_85982_(
                  matrix4f,
                  (float)sample.f_82479_ + (float)bottomAngleVec.f_82479_,
                  (float)sample.f_82480_ + (float)bottomAngleVec.f_82480_,
                  (float)sample.f_82481_ + (float)bottomAngleVec.f_82481_
               )
               .m_85950_(this.r, this.g, this.b, this.trailA)
               .m_7421_(u2, 1.0F)
               .m_86008_(OverlayTexture.f_118083_)
               .m_85969_(j)
               .m_85977_(matrix3f, 0.0F, 1.0F, 0.0F)
               .m_5752_();
            vertexconsumer.m_85982_(
                  matrix4f,
                  (float)sample.f_82479_ + (float)topAngleVec.f_82479_,
                  (float)sample.f_82480_ + (float)topAngleVec.f_82480_,
                  (float)sample.f_82481_ + (float)topAngleVec.f_82481_
               )
               .m_85950_(this.r, this.g, this.b, this.trailA)
               .m_7421_(u2, 0.0F)
               .m_86008_(OverlayTexture.f_118083_)
               .m_85969_(j)
               .m_85977_(matrix3f, 0.0F, 1.0F, 0.0F)
               .m_5752_();
            vertexconsumer.m_85982_(
                  matrix4f,
                  (float)drawFrom.f_82479_ + (float)topAngleVec.f_82479_,
                  (float)drawFrom.f_82480_ + (float)topAngleVec.f_82480_,
                  (float)drawFrom.f_82481_ + (float)topAngleVec.f_82481_
               )
               .m_85950_(this.r, this.g, this.b, this.trailA)
               .m_7421_(u1, 0.0F)
               .m_86008_(OverlayTexture.f_118083_)
               .m_85969_(j)
               .m_85977_(matrix3f, 0.0F, 1.0F, 0.0F)
               .m_5752_();
            samples++;
            drawFrom = sample;
         }

         multibuffersource$buffersource.m_109911_();
         posestack.m_85849_();
      }
   }

   public float getTrailRot(Camera camera) {
      return (float) (-Math.PI / 180.0) * camera.m_90589_();
   }

   public abstract float getTrailHeight();

   public abstract ResourceLocation getTrailTexture();

   public int sampleCount() {
      return 20;
   }

   public int sampleStep() {
      return 1;
   }

   public Vec3 getTrailPosition(int pointer, float partialTick) {
      if (this.f_107220_) {
         partialTick = 1.0F;
      }

      int i = this.trailPointer - pointer & 63;
      int j = this.trailPointer - pointer - 1 & 63;
      Vec3 d0 = this.trailPositions[j];
      Vec3 d1 = this.trailPositions[i].m_82546_(d0);
      return d0.m_82549_(d1.m_82490_((double)partialTick));
   }

   public int m_6355_(float f) {
      return 240;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }
}
