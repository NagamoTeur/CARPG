package com.hollingsworth.arsnouveau.client;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau"
)
public class SkyTextureHandler {
   @SubscribeEvent
   public static void renderSky(RenderLevelStageEvent event) {
      if (event.getStage().equals(Stage.AFTER_SKY)) {
         if (ArsNouveau.optifineLoaded || (Boolean)Config.DISABLE_SKY_SHADER.get()) {
            return;
         }

         Minecraft minecraft = Minecraft.m_91087_();
         if (ClientInfo.skyRenderTarget == null) {
            Window window = minecraft.m_91268_();
            setupRenderTarget(window.m_85441_(), window.m_85442_());
         }

         PoseStack poseStack = event.getPoseStack();
         GameRenderer gameRenderer = minecraft.f_91063_;
         LevelRenderer levelRenderer = minecraft.f_91060_;
         Camera camera = gameRenderer.m_109153_();
         Vec3 cameraPosition = camera.m_90583_();
         Matrix4f projectionMatrix = event.getProjectionMatrix();
         float partialTick = event.getPartialTick();
         boolean isFoggy = minecraft.f_91073_.m_104583_().m_5781_(Mth.m_14107_(cameraPosition.f_82479_), Mth.m_14107_(cameraPosition.f_82480_))
            || minecraft.f_91065_.m_93090_().m_93715_();
         ClientInfo.skyRenderTarget.m_83947_(true);
         RenderSystem.m_69421_(16640, Minecraft.f_91002_);
         FogRenderer.m_109018_(camera, partialTick, minecraft.f_91073_, minecraft.f_91066_.m_193772_(), gameRenderer.m_109131_(partialTick));
         FogRenderer.m_109036_();
         RenderSystem.m_157427_(GameRenderer::m_172808_);
         levelRenderer.m_202423_(
            poseStack,
            projectionMatrix,
            partialTick,
            camera,
            isFoggy,
            () -> FogRenderer.m_234172_(camera, FogMode.FOG_SKY, gameRenderer.m_109152_(), isFoggy, partialTick)
         );
         PoseStack modelViewStack = RenderSystem.m_157191_();
         modelViewStack.m_85836_();
         modelViewStack.m_166854_(poseStack.m_85850_().m_85861_());
         RenderSystem.m_157182_();
         if (minecraft.f_91066_.m_92174_() != CloudStatus.OFF) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            levelRenderer.m_172954_(poseStack, projectionMatrix, partialTick, cameraPosition.f_82479_, cameraPosition.f_82480_, cameraPosition.f_82481_);
         }

         RenderSystem.m_69458_(false);
         levelRenderer.m_109703_(gameRenderer.m_109154_(), partialTick, cameraPosition.f_82479_, cameraPosition.f_82480_, cameraPosition.f_82481_);
         RenderSystem.m_69458_(true);
         modelViewStack.m_85849_();
         RenderSystem.m_157182_();
         minecraft.m_91385_().m_83947_(true);
      }
   }

   public static void setupRenderTarget(int width, int height) {
      if (ClientInfo.skyRenderTarget != null) {
         ClientInfo.skyRenderTarget.m_83930_();
      }

      ClientInfo.skyRenderTarget = new TextureTarget(width, height, true, Minecraft.f_91002_);
   }
}
