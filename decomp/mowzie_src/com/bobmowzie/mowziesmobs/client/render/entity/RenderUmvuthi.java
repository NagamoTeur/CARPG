package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelUmvuthi;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoSunblockLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.UmvuthiSunLayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderUmvuthi extends MowzieGeoEntityRenderer<EntityUmvuthi> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/umvuthi.png");
   public static final ResourceLocation SUN = new ResourceLocation("mowziesmobs", "textures/effects/sun_effect.png");
   public static final float BURST_RADIUS = 3.5F;
   public static final int BURST_FRAME_COUNT = 10;
   public static final int BURST_START_FRAME = 12;
   private MultiBufferSource source;
   private EntityUmvuthi entity;

   public RenderUmvuthi(Context mgr) {
      super(mgr, new ModelUmvuthi());
      this.addLayer(new FrozenRenderHandler.GeckoLayerFrozen(this, mgr));
      this.addLayer(new GeckoSunblockLayer(this, mgr));
      this.addLayer(new UmvuthiSunLayer(this));
      this.f_114477_ = 1.0F;
   }

   public ResourceLocation getTextureLocation(EntityUmvuthi entity) {
      return this.getGeoModelProvider().getTextureResource(entity);
   }

   public void render(EntityUmvuthi umvuthi, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      this.source = bufferIn;
      this.entity = umvuthi;
      if (!umvuthi.m_20145_()
         && umvuthi.getActiveAbilityType() == EntityUmvuthi.SOLAR_FLARE_ABILITY
         && umvuthi.getActiveAbility().getTicksInUse() > 12
         && umvuthi.getActiveAbility().getTicksInUse() < 21) {
         matrixStackIn.m_85836_();
         Quaternion quat = this.f_114476_.m_114470_();
         matrixStackIn.m_85845_(quat);
         matrixStackIn.m_85837_(0.0, 1.0, 0.0);
         matrixStackIn.m_85841_(0.8F, 0.8F, 0.8F);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(MMRenderType.getSolarFlare(RenderSunstrike.TEXTURE));
         Pose matrixstack$entry = matrixStackIn.m_85850_();
         Matrix4f matrix4f = matrixstack$entry.m_85861_();
         Matrix3f matrix3f = matrixstack$entry.m_85864_();
         drawBurst(matrix4f, matrix3f, ivertexbuilder, (float)(umvuthi.getActiveAbility().getTicksInUse() - 12) + delta, packedLightIn);
         matrixStackIn.m_85849_();
      }

      super.render(umvuthi, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
      MowzieGeoBone sunRender = this.getMowzieAnimatedGeoModel().getMowzieBone("sun_render");
      Vector3d sunRenderPos = sunRender.getWorldPosition();
      if (umvuthi.headPos != null && umvuthi.headPos.length > 0) {
         umvuthi.headPos[0] = new Vec3(sunRenderPos.f_86214_, sunRenderPos.f_86215_, sunRenderPos.f_86216_);
      }

      if (umvuthi.getActiveAbilityType() == EntityUmvuthi.SUPERNOVA_ABILITY && umvuthi.betweenHandPos != null && umvuthi.betweenHandPos.length > 0) {
         Vector3d novaRenderPos = this.getMowzieAnimatedGeoModel().getMowzieBone("superNovaCenter").getWorldPosition();
         float blendStart = 4.0F;
         float blendDuration = 4.0F;
         int ticksInUse = umvuthi.getActiveAbility().getTicksInUse();
         if ((float)ticksInUse <= blendDuration + blendStart) {
            Vec3 sunRenderPosVec3 = new Vec3(sunRenderPos.f_86214_, sunRenderPos.f_86215_, sunRenderPos.f_86216_);
            Vec3 novaRenderPosVec3 = new Vec3(novaRenderPos.f_86214_, novaRenderPos.f_86215_, novaRenderPos.f_86216_);
            float alpha = ((float)umvuthi.getActiveAbility().getTicksInUse() + delta - blendStart) / blendDuration;
            alpha = Math.max(0.0F, alpha);
            Vec3 newPos = novaRenderPosVec3.m_82549_(sunRenderPosVec3.m_82546_(novaRenderPosVec3).m_82490_(1.0 - (double)alpha));
            novaRenderPos.m_176285_(newPos.f_82479_, newPos.f_82480_, newPos.f_82481_);
         }

         ((EntityUmvuthi)this.animatable).betweenHandPos[0] = new Vec3(novaRenderPos.f_86214_, novaRenderPos.f_86215_, novaRenderPos.f_86216_);
      }

      if (!Minecraft.m_91087_().m_91104_()) {
         MowzieGeoBone mask = this.getMowzieAnimatedGeoModel().getMowzieBone("maskTwitcher");
         ((EntityUmvuthi)this.animatable).updateRattleSound(mask.getRotationZ());
      }
   }

   public boolean shouldRender(EntityUmvuthi umvuthi, Frustum frustum, double p_114493_, double p_114494_, double p_114495_) {
      boolean result = super.m_5523_(umvuthi, frustum, p_114493_, p_114494_, p_114495_);
      if (!result) {
         umvuthi.headPos[0] = umvuthi.m_20182_().m_82520_(0.0, (double)umvuthi.m_20192_(), 0.0);
      }

      return result;
   }

   public static void drawBurst(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, float tick, int packedLightIn) {
      int dissapateFrame = 6;
      float firstSpeed = 2.0F;
      float secondSpeed = 1.0F;
      int frame = (int)(tick * firstSpeed) <= dissapateFrame
         ? (int)(tick * firstSpeed)
         : (int)((float)dissapateFrame + (tick - (float)dissapateFrame / firstSpeed) * secondSpeed);
      if (frame > 10) {
         frame = 10;
      }

      float minU = 0.0625F * (float)frame;
      float maxU = minU + 0.0625F;
      float minV = 0.5F;
      float maxV = minV + 0.5F;
      float offset = 0.219F * (float)(frame % 2);
      float opacity = tick < 8.0F ? 0.8F : 0.4F;
      drawVertex(matrix4f, matrix3f, builder, -3.5F + offset, -3.5F + offset, 0.0F, minU, minV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, -3.5F + offset, 3.5F + offset, 0.0F, minU, maxV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, 3.5F + offset, 3.5F + offset, 0.0F, maxU, maxV, opacity, packedLightIn);
      drawVertex(matrix4f, matrix3f, builder, 3.5F + offset, -3.5F + offset, 0.0F, maxU, minV, opacity, packedLightIn);
   }

   public static void drawVertex(
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
