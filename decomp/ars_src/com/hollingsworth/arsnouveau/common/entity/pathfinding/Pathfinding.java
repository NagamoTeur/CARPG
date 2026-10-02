package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.AbstractPathJob;
import com.hollingsworth.arsnouveau.common.util.Log;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import java.util.ConcurrentModificationException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class Pathfinding {
   private static final BlockingQueue<Runnable> jobQueue = new LinkedBlockingDeque<>();
   private static ThreadPoolExecutor executor;

   public static ThreadPoolExecutor getExecutor() {
      if (executor == null) {
         executor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, jobQueue, new Pathfinding.MinecoloniesThreadFactory());
      }

      return executor;
   }

   public static void shutdown() {
      getExecutor().shutdownNow();
      jobQueue.clear();
      executor = null;
   }

   private Pathfinding() {
   }

   public static void enqueue(AbstractPathJob job) {
      job.getResult().startJob(getExecutor());
   }

   @OnlyIn(Dist.CLIENT)
   public static void debugDraw(double frame, PoseStack matrixStack) {
      if (AbstractPathJob.lastDebugNodesNotVisited != null) {
         Vec3 vec = Minecraft.m_91087_().m_91290_().f_114358_.m_90583_();
         double dx = vec.m_7096_();
         double dy = vec.m_7098_();
         double dz = vec.m_7094_();
         matrixStack.m_85836_();
         matrixStack.m_85837_(-dx, -dy, -dz);
         RenderSystem.m_69482_();
         RenderSystem.m_69472_();
         RenderSystem.m_69461_();
         Set<ModNode> debugNodesNotVisited;
         Set<ModNode> debugNodesVisited;
         Set<ModNode> debugNodesPath;
         synchronized (PathingConstants.debugNodeMonitor) {
            debugNodesNotVisited = AbstractPathJob.lastDebugNodesNotVisited;
            debugNodesVisited = AbstractPathJob.lastDebugNodesVisited;
            debugNodesPath = AbstractPathJob.lastDebugNodesPath;
         }

         try {
            for (ModNode n : debugNodesNotVisited) {
               debugDrawNode(n, 1.0F, 0.0F, 0.0F, matrixStack);
            }

            for (ModNode n : debugNodesVisited) {
               debugDrawNode(n, 0.0F, 0.0F, 1.0F, matrixStack);
            }

            for (ModNode n : debugNodesPath) {
               if (n.isReachedByWorker()) {
                  debugDrawNode(n, 1.0F, 0.4F, 0.0F, matrixStack);
               } else {
                  debugDrawNode(n, 0.0F, 1.0F, 0.0F, matrixStack);
               }
            }
         } catch (ConcurrentModificationException var16) {
            Log.getLogger().catching(var16);
         }

         RenderSystem.m_69465_();
         RenderSystem.m_69493_();
         RenderSystem.m_69478_();
         matrixStack.m_85849_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static void debugDrawNode(ModNode n, float r, float g, float b, PoseStack matrixStack) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)n.pos.m_123341_() + 0.375, (double)n.pos.m_123342_() + 0.375, (double)n.pos.m_123343_() + 0.375);
      Entity entity = Minecraft.m_91087_().m_91288_();
      double dx = (double)n.pos.m_123341_() - entity.m_20185_();
      double dy = (double)n.pos.m_123342_() - entity.m_20186_();
      double dz = (double)n.pos.m_123343_() - entity.m_20189_();
      if (Math.sqrt(dx * dx + dy * dy + dz * dz) <= 5.0) {
         renderDebugText(n, matrixStack);
      }

      matrixStack.m_85841_(0.25F, 0.25F, 0.25F);
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder vertexBuffer = tessellator.m_85915_();
      Matrix4f matrix4f = matrixStack.m_85850_().m_85861_();
      vertexBuffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85814_);
      RenderSystem.m_157429_(r, g, b, 1.0F);
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 1.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 1.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 0.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 0.0F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, 1.0F, 0.0F, 1.0F).m_5752_();
      tessellator.m_85914_();
      if (n.parent != null) {
         float pdx = (float)(n.parent.pos.m_123341_() - n.pos.m_123341_()) + 0.125F;
         float pdy = (float)(n.parent.pos.m_123342_() - n.pos.m_123342_()) + 0.125F;
         float pdz = (float)(n.parent.pos.m_123343_() - n.pos.m_123343_()) + 0.125F;
         vertexBuffer.m_166779_(Mode.LINES, DefaultVertexFormat.f_85815_);
         vertexBuffer.m_85982_(matrix4f, 0.5F, 0.5F, 0.5F).m_85950_(0.75F, 0.75F, 0.75F, 1.0F).m_5752_();
         vertexBuffer.m_85982_(matrix4f, pdx / 0.25F, pdy / 0.25F, pdz / 0.25F).m_85950_(0.75F, 0.75F, 0.75F, 1.0F).m_5752_();
         tessellator.m_85914_();
      }

      matrixStack.m_85849_();
   }

   @OnlyIn(Dist.CLIENT)
   public static void renderDebugText(ModNode n, PoseStack matrixStack) {
      String s1 = String.format("F: %.3f [%d]", n.getCost(), n.getCounterAdded());
      String s2 = String.format("G: %.3f [%d]", n.getScore(), n.getCounterVisited());
      Font fontrenderer = Minecraft.m_91087_().f_91062_;
      matrixStack.m_85836_();
      matrixStack.m_85837_(0.0, 0.75, 0.0);
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      matrixStack.m_85845_(renderManager.m_114470_());
      matrixStack.m_85841_(-0.014F, -0.014F, 0.014F);
      matrixStack.m_85837_(0.0, 18.0, 0.0);
      RenderSystem.m_69458_(false);
      RenderSystem.m_69478_();
      RenderSystem.m_69416_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      RenderSystem.m_69472_();
      int i = Math.max(fontrenderer.m_92895_(s1), fontrenderer.m_92895_(s2)) / 2;
      Matrix4f matrix4f = matrixStack.m_85850_().m_85861_();
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder vertexBuffer = tessellator.m_85915_();
      vertexBuffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      vertexBuffer.m_85982_(matrix4f, (float)(-i - 1), -5.0F, 0.0F).m_85950_(0.0F, 0.0F, 0.0F, 0.7F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, (float)(-i - 1), 12.0F, 0.0F).m_85950_(0.0F, 0.0F, 0.0F, 0.7F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, (float)(i + 1), 12.0F, 0.0F).m_85950_(0.0F, 0.0F, 0.0F, 0.7F).m_5752_();
      vertexBuffer.m_85982_(matrix4f, (float)(i + 1), -5.0F, 0.0F).m_85950_(0.0F, 0.0F, 0.0F, 0.7F).m_5752_();
      tessellator.m_85914_();
      RenderSystem.m_69493_();
      BufferSource buffer = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
      matrixStack.m_85837_(0.0, -5.0, 0.0);
      fontrenderer.m_92811_(s1, (float)(-fontrenderer.m_92895_(s1)) / 2.0F, 0.0F, -1, false, matrix4f, buffer, false, 0, 15728880);
      matrixStack.m_85837_(0.0, 8.0, 0.0);
      fontrenderer.m_92811_(s2, (float)(-fontrenderer.m_92895_(s2)) / 2.0F, 0.0F, -1, false, matrix4f, buffer, false, 0, 15728880);
      RenderSystem.m_69458_(true);
      matrixStack.m_85837_(0.0, -8.0, 0.0);
      fontrenderer.m_92811_(s1, (float)(-fontrenderer.m_92895_(s1)) / 2.0F, 0.0F, -1, false, matrix4f, buffer, false, 0, 15728880);
      matrixStack.m_85837_(0.0, 8.0, 0.0);
      fontrenderer.m_92811_(s2, (float)(-fontrenderer.m_92895_(s2)) / 2.0F, 0.0F, -1, false, matrix4f, buffer, false, 0, 15728880);
      buffer.m_109911_();
      matrixStack.m_85849_();
   }

   public static class MinecoloniesThreadFactory implements ThreadFactory {
      public static int id;

      @Override
      public Thread newThread(Runnable runnable) {
         Thread thread = new Thread(runnable, "AN stolen Minecolonies Pathfinding Worker #" + id++);
         thread.setDaemon(true);
         thread.setUncaughtExceptionHandler((thread1, throwable) -> Log.getLogger().error("AN stolen Minecolonies Pathfinding Thread errored! ", throwable));
         thread.setContextClassLoader(ClassLoader.getSystemClassLoader());
         return thread;
      }
   }
}
