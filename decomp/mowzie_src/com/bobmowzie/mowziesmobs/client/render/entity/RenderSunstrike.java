package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderSunstrike extends EntityRenderer<EntitySunstrike> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/effects/sunstrike.png");
   private static final Random RANDOMIZER = new Random(0L);
   private static final float TEXTURE_WIDTH = 256.0F;
   private static final float TEXTURE_HEIGHT = 32.0F;
   private static final float BEAM_MIN_U = 0.875F;
   private static final float BEAM_MAX_U = 1.0F;
   private static final float PIXEL_SCALE = 0.0625F;
   private static final int MAX_HEIGHT = 256;
   private static final float DRAW_FADE_IN_RATE = 2.0F;
   private static final float DRAW_FADE_IN_POINT = 0.5F;
   private static final float DRAW_OPACITY_MULTIPLER = 0.7F;
   private static final float RING_RADIUS = 1.6F;
   private static final int RING_FRAME_SIZE = 16;
   private static final int RING_FRAME_COUNT = 10;
   private static final int BREAM_FRAME_COUNT = 31;
   private static final float BEAM_DRAW_START_RADIUS = 2.0F;
   private static final float BEAM_DRAW_END_RADIUS = 0.25F;
   private static final float BEAM_STRIKE_RADIUS = 1.0F;
   private static final float LINGER_RADIUS = 1.2F;
   private static final float SCORCH_MIN_U = 0.75F;
   private static final float SCORCH_MAX_U = 0.8125F;
   private static final float SCORCH_MIN_V = 0.5F;
   private static final float SCORCH_MAX_V = 1.0F;

   public RenderSunstrike(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(EntitySunstrike entity) {
      return TEXTURE;
   }

   public void render(EntitySunstrike sunstrike, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      float maxY = (float)(256.0 - sunstrike.m_20186_());
      if (!(maxY < 0.0F)) {
         RANDOMIZER.setSeed(sunstrike.getVariant());
         boolean isLingering = sunstrike.isLingering(delta);
         boolean isStriking = sunstrike.isStriking(delta);
         matrixStackIn.m_85836_();
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(MMRenderType.getGlowingEffect(TEXTURE));
         if (isLingering) {
            this.drawScorch(sunstrike, delta, matrixStackIn, ivertexbuilder, packedLightIn);
         } else if (isStriking) {
            this.drawStrike(sunstrike, maxY, delta, matrixStackIn, ivertexbuilder, packedLightIn);
         }

         matrixStackIn.m_85849_();
      }
   }

   private void drawScorch(EntitySunstrike sunstrike, float delta, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      Level world = sunstrike.m_20193_();
      double ex = sunstrike.f_19790_ + (sunstrike.m_20185_() - sunstrike.f_19790_) * (double)delta;
      double ey = sunstrike.f_19791_ + (sunstrike.m_20186_() - sunstrike.f_19791_) * (double)delta;
      double ez = sunstrike.f_19792_ + (sunstrike.m_20189_() - sunstrike.f_19792_) * (double)delta;
      int minX = Mth.m_14107_(ex - 1.2F);
      int maxX = Mth.m_14107_(ex + 1.2F);
      int minY = Mth.m_14107_(ey - 1.2F);
      int maxY = Mth.m_14107_(ey);
      int minZ = Mth.m_14107_(ez - 1.2F);
      int maxZ = Mth.m_14107_(ez + 1.2F);
      float opacityMultiplier = (0.6F + RANDOMIZER.nextFloat() * 0.2F) * (float)world.m_46803_(new BlockPos(ex, ey, ez));
      byte mirrorX = (byte)(RANDOMIZER.nextBoolean() ? -1 : 1);
      byte mirrorZ = (byte)(RANDOMIZER.nextBoolean() ? -1 : 1);

      for (BlockPos pos : BlockPos.m_121940_(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
         BlockState block = world.m_8055_(pos.m_7495_());
         if (block.m_60767_() != Material.f_76296_ && world.m_46803_(pos) > 3) {
            this.drawScorchBlock(world, block, pos, ex, ey, ez, opacityMultiplier, mirrorX, mirrorZ, matrixStack, builder, packedLightIn);
         }
      }
   }

   private void drawScorchBlock(
      Level world,
      BlockState block,
      BlockPos pos,
      double ex,
      double ey,
      double ez,
      float opacityMultiplier,
      byte mirrorX,
      byte mirrorZ,
      PoseStack matrixStack,
      VertexConsumer builder,
      int packedLightIn
   ) {
      Pose matrixstack$entry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      if (block.m_60796_(world, pos)) {
         int bx = pos.m_123341_();
         int by = pos.m_123342_();
         int bz = pos.m_123343_();
         float opacity = (float)((1.0 - (ey - (double)by) / 2.0) * (double)opacityMultiplier);
         if (opacity >= 0.0F) {
            if (opacity > 1.0F) {
               opacity = 1.0F;
            }

            AABB aabb = block.m_60816_(world, pos).m_83215_();
            float minX = (float)((double)bx + aabb.f_82288_ - ex);
            float maxX = (float)((double)bx + aabb.f_82291_ - ex);
            float y = (float)((double)by + aabb.f_82289_ - ey + 0.015625);
            float minZ = (float)((double)bz + aabb.f_82290_ - ez);
            float maxZ = (float)((double)bz + aabb.f_82293_ - ez);
            float minU = ((float)mirrorX * minX / 2.0F / 1.2F + 0.5F) * 0.0625F + 0.75F;
            float maxU = ((float)mirrorX * maxX / 2.0F / 1.2F + 0.5F) * 0.0625F + 0.75F;
            float minV = ((float)mirrorZ * minZ / 2.0F / 1.2F + 0.5F) * 0.5F + 0.5F;
            float maxV = ((float)mirrorZ * maxZ / 2.0F / 1.2F + 0.5F) * 0.5F + 0.5F;
            this.drawVertex(matrix4f, matrix3f, builder, minX, y, minZ, minU, minV, opacity, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, minX, y, maxZ, minU, maxV, opacity, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, maxX, y, maxZ, maxU, maxV, opacity, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, builder, maxX, y, minZ, maxU, minV, opacity, packedLightIn);
         }
      }
   }

   private void drawStrike(EntitySunstrike sunstrike, float maxY, float delta, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      float drawTime = sunstrike.getStrikeDrawTime(delta);
      float strikeTime = sunstrike.getStrikeDamageTime(delta);
      boolean drawing = sunstrike.isStrikeDrawing(delta);
      float opacity = drawing && drawTime < 0.5F ? drawTime * 2.0F : 1.0F;
      if (drawing) {
         opacity *= 0.7F;
      }

      this.drawRing(drawing, drawTime, strikeTime, opacity, matrixStack, builder, packedLightIn);
      matrixStack.m_85845_(new Quaternion(0.0F, -Minecraft.m_91087_().f_91063_.m_109153_().m_90590_(), 0.0F, true));
      this.drawBeam(drawing, drawTime, strikeTime, opacity, maxY, matrixStack, builder, packedLightIn);
   }

   private void drawRing(boolean drawing, float drawTime, float strikeTime, float opacity, PoseStack matrixStack, VertexConsumer builder, int packedLightIn) {
      int frame = (int)((drawing ? drawTime : strikeTime) * 11.0F);
      if (frame > 10) {
         frame = 10;
      }

      float minU = (float)(frame * 16) / 256.0F;
      float maxU = minU + 0.0625F;
      float minV = drawing ? 0.0F : 0.5F;
      float maxV = minV + 0.5F;
      float offset = 0.1F * (float)(frame % 2);
      Pose matrixstack$entry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      this.drawVertex(matrix4f, matrix3f, builder, -1.6F + offset, 0.0F, -1.6F + offset, minU, minV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -1.6F + offset, 0.0F, 1.6F + offset, minU, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.6F + offset, 0.0F, 1.6F + offset, maxU, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, 1.6F + offset, 0.0F, -1.6F + offset, maxU, minV, opacity, packedLightIn);
   }

   private void drawBeam(
      boolean drawing, float drawTime, float strikeTime, float opacity, float maxY, PoseStack matrixStack, VertexConsumer builder, int packedLightIn
   ) {
      int frame = drawing ? 0 : (int)(strikeTime * 32.0F);
      if (frame > 31) {
         frame = 31;
      }

      float radius = 1.0F;
      if (drawing) {
         radius = -1.75F * drawTime + 2.0F;
      }

      float minV = (float)frame / 32.0F;
      float maxV = (float)(frame + 1) / 32.0F;
      Pose matrixstack$entry = matrixStack.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_85861_();
      Matrix3f matrix3f = matrixstack$entry.m_85864_();
      this.drawVertex(matrix4f, matrix3f, builder, -radius, 0.0F, 0.0F, 0.875F, minV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -radius, maxY, 0.0F, 0.875F, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, radius, maxY, 0.0F, 1.0F, maxV, opacity, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, radius, 0.0F, 0.0F, 1.0F, minV, opacity, packedLightIn);
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
