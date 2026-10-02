package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Death_Laser_beam_Renderer extends EntityRenderer<Death_Laser_Beam_Entity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/entity/harbinger/death_laser_beam.png");
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float START_RADIUS = 0.75F;
   private static final float BEAM_RADIUS = 0.75F;
   private boolean clearerView = false;
   private Map<UUID, LightningRender> lightningRenderMap = new HashMap<>();

   public Death_Laser_beam_Renderer(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Death_Laser_Beam_Entity entity) {
      return TEXTURE;
   }

   public void render(Death_Laser_Beam_Entity solarBeam, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
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

      VertexConsumer ivertexbuilder = bufferIn.m_6299_(CMRenderTypes.getGlowingEffect(this.getTextureLocation(solarBeam)));
      this.renderBeam(length, (180.0F / (float)Math.PI) * yaw, (180.0F / (float)Math.PI) * pitch, frame, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
      this.renderEnd(frame, solarBeam.blockSide, matrixStackIn, ivertexbuilder, packedLightIn);
      matrixStackIn.m_85849_();
      this.renderLighting(delta, matrixStackIn, solarBeam, bufferIn);
   }

   private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      float minU = 0.0F + 0.0625F * (float)frame;
      float minV = 0.0F;
      float maxU = minU + 0.0625F;
      float maxV = minV + 0.5F;
      Pose matrixstack$entry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      this.drawVertex(matrix4f, matrix3f, builder, -0.75F, -0.75F, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -0.75F, 0.75F, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 0.75F, 0.75F, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 0.75F, -0.75F, 0.0F, maxU, minV, 1.0F, packedLightIn);
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

   private void renderLighting(float frame, PoseStack poseStack, Death_Laser_Beam_Entity entity, MultiBufferSource buffer) {
      double x = Mth.m_14139_((double)frame, entity.f_19790_, entity.m_20185_());
      double y = Mth.m_14139_((double)frame, entity.f_19791_, entity.m_20186_());
      double z = Mth.m_14139_((double)frame, entity.f_19792_, entity.m_20189_());
      float f1 = 0.0F;
      if (entity.f_19797_ > 20) {
         poseStack.m_85836_();
         poseStack.m_85837_(-x, -y, -z);
         LightningBoltData.BoltRenderInfo RedboltData = new LightningBoltData.BoltRenderInfo(
            0.5F, 0.15F, 0.25F, 0.25F, new Vector4f(1.0F, 0.101960786F, 0.0F, 0.9F), 0.86F
         );
         LightningBoltData bolt1 = new LightningBoltData(
               RedboltData, new Vec3(x, y, z), new Vec3(entity.collidePosX, entity.collidePosY, entity.collidePosZ), 5
            )
            .size(0.1F)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningBoltData.BoltRenderInfo YellowboltData = new LightningBoltData.BoltRenderInfo(
            0.5F, 0.1F, 0.25F, 0.15F, new Vector4f(0.9764706F, 0.7607843F, 0.16862746F, 0.7F), 0.86F
         );
         LightningBoltData bolt2 = new LightningBoltData(
               YellowboltData, new Vec3(x, y, z), new Vec3(entity.collidePosX, entity.collidePosY, entity.collidePosZ), 5
            )
            .size(0.07F)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningRender lightningRender = this.getLightingRender(entity.m_20148_());
         if (!Minecraft.m_91087_().m_91104_()) {
            lightningRender.update(entity, bolt1, frame);
            lightningRender.update(entity, bolt2, frame);
         }

         lightningRender.render(frame, poseStack, buffer);
         poseStack.m_85849_();
      }

      if (entity.m_213877_() && this.lightningRenderMap.containsKey(entity.m_20148_())) {
         this.lightningRenderMap.remove(entity.m_20148_());
      }
   }

   private LightningRender getLightingRender(UUID uuid) {
      if (this.lightningRenderMap.get(uuid) == null) {
         this.lightningRenderMap.put(uuid, new LightningRender());
      }

      return this.lightningRenderMap.get(uuid);
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
         sideQuat.m_80148_(CMMathUtil.quatFromRotationXYZ(90.0F, 0.0F, 0.0F, true));
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
      this.drawVertex(matrix4f, matrix3f, builder, -0.75F, offset, 0.0F, minU, minV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -0.75F, length, 0.0F, minU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 0.75F, length, 0.0F, maxU, maxV, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 0.75F, offset, 0.0F, maxU, minV, 1.0F, packedLightIn);
   }

   private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(CMMathUtil.quatFromRotationXYZ(90.0F, 0.0F, 0.0F, true));
      matrixStackIn.m_85845_(CMMathUtil.quatFromRotationXYZ(0.0F, 0.0F, yaw - 90.0F, true));
      matrixStackIn.m_85845_(CMMathUtil.quatFromRotationXYZ(-pitch, 0.0F, 0.0F, true));
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
