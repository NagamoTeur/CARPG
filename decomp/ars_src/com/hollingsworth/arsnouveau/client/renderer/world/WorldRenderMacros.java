package com.hollingsworth.arsnouveau.client.renderer.world;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;

public class WorldRenderMacros {
   public static void renderBox(BufferSource buffer, PoseStack ps, BlockPos posA, BlockPos posB, int argbColor) {
      renderBox(buffer.m_6299_(RenderType.f_110371_), ps, posA, posB, argbColor >> 16 & 0xFF, argbColor >> 8 & 0xFF, argbColor & 0xFF, argbColor >> 24 & 0xFF);
   }

   public static void renderBox(VertexConsumer buffer, PoseStack ps, BlockPos posA, BlockPos posB, int red, int green, int blue, int alpha) {
      if (alpha != 0) {
         float minX = (float)Math.min(posA.m_123341_(), posB.m_123341_());
         float minY = (float)Math.min(posA.m_123342_(), posB.m_123342_());
         float minZ = (float)Math.min(posA.m_123343_(), posB.m_123343_());
         float maxX = (float)(Math.max(posA.m_123341_(), posB.m_123341_()) + 1);
         float maxY = (float)(Math.max(posA.m_123342_(), posB.m_123342_()) + 1);
         float maxZ = (float)(Math.max(posA.m_123343_(), posB.m_123343_()) + 1);
         Matrix4f m = ps.m_85850_().m_85861_();
         buffer.m_7404_(red, green, blue, alpha);
         populateCuboid(minX, minY, minZ, maxX, maxY, maxZ, m, buffer);
         buffer.m_141991_();
      }
   }

   public static void populateCuboid(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Matrix4f m, VertexConsumer buf) {
      buf.m_85982_(m, minX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, minZ).m_5752_();
      buf.m_85982_(m, minX, minY, minZ).m_5752_();
      buf.m_85982_(m, minX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, minZ).m_5752_();
      buf.m_85982_(m, minX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, minX, minY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, minY, maxZ).m_5752_();
      buf.m_85982_(m, minX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, minY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, minX, minY, maxZ).m_5752_();
      buf.m_85982_(m, minX, minY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, minZ).m_5752_();
      buf.m_85982_(m, minX, minY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, minY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, maxZ).m_5752_();
      buf.m_85982_(m, minX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, minZ).m_5752_();
      buf.m_85982_(m, minX, maxY, minZ).m_5752_();
      buf.m_85982_(m, minX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, minZ).m_5752_();
      buf.m_85982_(m, minX, minY, maxZ).m_5752_();
      buf.m_85982_(m, minX, maxY, minZ).m_5752_();
      buf.m_85982_(m, minX, minY, minZ).m_5752_();
      buf.m_85982_(m, minX, minY, maxZ).m_5752_();
      buf.m_85982_(m, minX, maxY, maxZ).m_5752_();
      buf.m_85982_(m, minX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, minY, minZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, minY, maxZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, minZ).m_5752_();
      buf.m_85982_(m, maxX, maxY, maxZ).m_5752_();
   }

   public static void renderFillRectangle(BufferSource buffer, PoseStack ps, int x, int y, int z, int w, int h, int argbColor) {
      populateRectangle(
         x,
         y,
         z,
         w,
         h,
         argbColor >> 16 & 0xFF,
         argbColor >> 8 & 0xFF,
         argbColor & 0xFF,
         argbColor >> 24 & 0xFF,
         buffer.m_6299_(RenderType.f_110371_),
         ps.m_85850_().m_85861_()
      );
   }

   public static void populateRectangle(int x, int y, int z, int w, int h, int red, int green, int blue, int alpha, VertexConsumer buffer, Matrix4f m) {
      if (alpha != 0) {
         buffer.m_85982_(m, (float)x, (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_85982_(m, (float)x, (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_85982_(m, (float)(x + w), (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_85982_(m, (float)x, (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_85982_(m, (float)(x + w), (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_85982_(m, (float)(x + w), (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
      }
   }
}
