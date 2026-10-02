package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSolarBeam extends EntityRenderer<EntitySolarBeam> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/effects/solar_beam.png");
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float START_RADIUS = 1.3F;
   private static final float BEAM_RADIUS = 1.0F;
   private boolean clearerView = false;

   public RenderSolarBeam(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntitySolarBeam entity) {
      return TEXTURE;
   }

   public void render(EntitySolarBeam solarBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      this.clearerView = solarBeam.caster instanceof Player
         && Minecraft.m_91087_().f_91074_ == solarBeam.caster
         && Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      double collidePosX = solarBeam.prevCollidePosX + (solarBeam.collidePosX - solarBeam.prevCollidePosX) * (double)delta;
      double collidePosY = solarBeam.prevCollidePosY + (solarBeam.collidePosY - solarBeam.prevCollidePosY) * (double)delta;
      double collidePosZ = solarBeam.prevCollidePosZ + (solarBeam.collidePosZ - solarBeam.prevCollidePosZ) * (double)delta;
      double posX = solarBeam.f_19854_ + (solarBeam.m_20185_() - solarBeam.f_19854_) * (double)delta;
      double posY = solarBeam.f_19855_ + (solarBeam.m_20186_() - solarBeam.f_19855_) * (double)delta;
      double posZ = solarBeam.f_19856_ + (solarBeam.m_20189_() - solarBeam.f_19856_) * (double)delta;
      float yaw = solarBeam.prevYaw + (solarBeam.renderYaw - solarBeam.prevYaw) * delta;
      float pitch = solarBeam.prevPitch + (solarBeam.renderPitch - solarBeam.prevPitch) * delta;
      float length = (float)Math.sqrt(Math.pow(collidePosX - posX, 2.0) + Math.pow(collidePosY - posY, 2.0) + Math.pow(collidePosZ - posZ, 2.0));
      int frame = Mth.m_14143_(((float)(solarBeam.appear.getTimer() - 1) + delta) * 2.0F);
      if (frame < 0) {
         frame = 6;
      }

      VertexConsumer ivertexbuilder = bufferIn.m_6299_(MMRenderType.getGlowingEffect(this.getTextureLocation(solarBeam)));
      this.renderStart(frame, matrixStackIn, ivertexbuilder, packedLightIn);
      this.renderBeam(length, (180.0F / (float)Math.PI) * yaw, (180.0F / (float)Math.PI) * pitch, frame, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
      this.renderEnd(frame, solarBeam.blockSide, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.m_85849_();
   }

   private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F + 0.0625F * (float)frame;
      float minV = 0.0F;
      float maxU = minU + 0.0625F;
      float maxV = minV + 0.5F;
      Pose matrixstack$entry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      this.drawVertex(matrix4f, matrix3f, builder, -1.3F, -1.3F, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -1.3F, 1.3F, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.3F, 1.3F, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.3F, -1.3F, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderStart(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      if (!this.clearerView) {
         matrixStackIn.m_85836_();
         Quaternion quat = this.f_114476_.m_114470_();
         matrixStackIn.m_85845_(quat);
         this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   private void renderEnd(int frame, Direction side, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.m_85836_();
      Quaternion quat = this.f_114476_.m_114470_();
      matrixStackIn.m_85845_(quat);
      this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
      matrixStackIn.m_85849_();
      if (side != null) {
         matrixStackIn.m_85836_();
         Quaternion sideQuat = side.m_122406_();
         sideQuat.m_80148_(new Quaternion(90.0F, 0.0F, 0.0F, true));
         matrixStackIn.m_85845_(sideQuat);
         matrixStackIn.m_85837_(0.0, 0.0, -0.01F);
         this.renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.m_85849_();
      }
   }

   private void drawBeam(float length, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F;
      float minV = 0.5F + 0.03125F * (float)frame;
      float maxU = minU + 0.078125F;
      float maxV = minV + 0.03125F;
      Pose matrixstack$entry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      float offset = this.clearerView ? -1.0F : 0.0F;
      this.drawVertex(matrix4f, matrix3f, builder, -1.0F, offset, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -1.0F, length, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.0F, length, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.0F, offset, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(new Quaternion(90.0F, 0.0F, 0.0F, true));
      matrixStackIn.m_85845_(new Quaternion(0.0F, 0.0F, yaw - 90.0F, true));
      matrixStackIn.m_85845_(new Quaternion(-pitch, 0.0F, 0.0F, true));
      matrixStackIn.m_85836_();
      if (!this.clearerView) {
         matrixStackIn.m_85845_(new Quaternion(0.0F, Minecraft.m_91087_().f_91063_.m_109153_().m_90589_() + 90.0F, 0.0F, true));
      }

      this.drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
      matrixStackIn.m_85849_();
      if (!this.clearerView) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85845_(new Quaternion(0.0F, -Minecraft.m_91087_().f_91063_.m_109153_().m_90589_() - 90.0F, 0.0F, true));
         this.drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
         matrixStackIn.m_85849_();
      }

      matrixStackIn.m_85849_();
   }

   public void drawVertex(
      Matrix4f matrix,
      Matrix3f normals,
      VertexConsumer vertexBuilder,
      float offsetX,
      float offsetY,
      float offsetZ,
      float textureX,
      float textureY,
      float alpha,
      int packedLightIn
   ) {
      vertexBuilder.m_85982_(matrix, offsetX, offsetY, offsetZ)
         .m_85950_(1.0F, 1.0F, 1.0F, 1.0F * alpha)
         .m_7421_(textureX, textureY)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(packedLightIn)
         .m_85977_(normals, 0.0F, 1.0F, 0.0F)
         .m_5752_();
   }
}
