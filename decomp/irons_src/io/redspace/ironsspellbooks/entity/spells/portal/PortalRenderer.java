package io.redspace.ironsspellbooks.entity.spells.portal;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PortalRenderer extends EntityRenderer<PortalEntity> {
   private static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/portal.png");
   static int frameCount = 10;
   static int ticksPerFrame = 2;

   public PortalRenderer(Context context) {
      super(context);
   }

   public void render(PortalEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-entity.m_146908_()));
      poseStack.m_85841_(0.0625F, 0.0625F, 0.0625F);
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(PortalRenderer.CustomerRenderType.m_110494_(getTextureLocation()));
      int anim = entity.f_19797_ / ticksPerFrame % 9;
      float uvMin = (float)anim / (float)frameCount;
      float uvMax = (float)(anim + 1) / (float)frameCount;
      vertex(poseMatrix, normalMatrix, consumer, -8.0F, 0.0F, 0.0F, uvMin, 0.0F);
      vertex(poseMatrix, normalMatrix, consumer, 8.0F, 0.0F, 0.0F, uvMax, 0.0F);
      vertex(poseMatrix, normalMatrix, consumer, 8.0F, 32.0F, 0.0F, uvMax, 1.0F);
      vertex(poseMatrix, normalMatrix, consumer, -8.0F, 32.0F, 0.0F, uvMin, 1.0F);
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   private static void debugText(String text, float yOffset, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, (double)yOffset, 0.0);
      matrixStackIn.m_85845_(Minecraft.m_91087_().f_91063_.m_109153_().m_90591_());
      matrixStackIn.m_85841_(-0.025F, -0.025F, 0.025F);
      Matrix4f matrix4f = matrixStackIn.m_85850_().m_85861_();
      float f1 = Minecraft.m_91087_().f_91066_.m_92141_(0.25F);
      int j = (int)(f1 * 255.0F) << 24;
      Font font = Minecraft.m_91087_().f_91062_;
      float f2 = (float)(-font.m_92895_(text) / 2);
      font.m_92811_(text, f2, 0.0F, 553648127, false, matrix4f, bufferIn, true, j, packedLightIn);
      matrixStackIn.m_85849_();
   }

   public static void vertex(
      Matrix4f pMatrix, Matrix3f pNormals, VertexConsumer pVertexBuilder, float pOffsetX, float pOffsetY, float pOffsetZ, float pTextureX, float pTextureY
   ) {
      pVertexBuilder.m_85982_(pMatrix, pOffsetX, pOffsetY, pOffsetZ)
         .m_6122_(255, 255, 255, 100)
         .m_7421_(pTextureX, pTextureY)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(pNormals, 0.0F, 0.0F, 1.0F)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(PortalEntity entity) {
      return getTextureLocation();
   }

   public static ResourceLocation getTextureLocation() {
      return TEXTURE;
   }

   public static class CustomerRenderType extends RenderType {
      protected static final TransparencyStateShard ONE_MINUS = new TransparencyStateShard("one_minus", () -> {
         RenderSystem.m_69478_();
         RenderSystem.m_69416_(SourceFactor.ONE_MINUS_SRC_ALPHA, DestFactor.SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
      }, () -> {
         RenderSystem.m_69461_();
         RenderSystem.m_69453_();
      });

      public CustomerRenderType(
         String pName,
         VertexFormat pFormat,
         Mode pMode,
         int pBufferSize,
         boolean pAffectsCrumbling,
         boolean pSortOnUpload,
         Runnable pSetupState,
         Runnable pClearState
      ) {
         super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
      }

      @NotNull
      public static RenderType m_110494_(@NotNull ResourceLocation pLocation) {
         return m_173215_(
            "crumbling",
            DefaultVertexFormat.f_85812_,
            Mode.QUADS,
            256,
            false,
            true,
            CompositeState.m_110628_()
               .m_173292_(f_173074_)
               .m_173290_(new TextureStateShard(pLocation, false, false))
               .m_110685_(ONE_MINUS)
               .m_110661_(f_110110_)
               .m_110671_(f_110152_)
               .m_110677_(f_110154_)
               .m_110691_(false)
         );
      }
   }
}
