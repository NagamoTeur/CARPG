package io.redspace.ironsspellbooks.entity.spells.magic_arrow;

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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.OffsetTexturingStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MagicArrowRenderer extends EntityRenderer<MagicArrowProjectile> {
   private static final ResourceLocation TEXTURE = IronsSpellbooks.id("textures/entity/magic_arrow.png");

   public MagicArrowRenderer(Context context) {
      super(context);
   }

   public void render(MagicArrowProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      Vec3 motion = entity.m_20184_();
      float xRot = -((float)(Mth.m_14136_(motion.m_165924_(), motion.f_82480_) * 180.0F / (float)Math.PI) - 90.0F);
      float yRot = -((float)(Mth.m_14136_(motion.f_82481_, motion.f_82479_) * 180.0F / (float)Math.PI) + 90.0F);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(yRot));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(xRot));
      renderModel(poseStack, bufferSource);
      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public static void renderModel(PoseStack poseStack, MultiBufferSource bufferSource) {
      poseStack.m_85841_(0.13F, 0.13F, 0.13F);
      Pose pose = poseStack.m_85850_();
      Matrix4f poseMatrix = pose.m_85861_();
      Matrix3f normalMatrix = pose.m_85864_();
      VertexConsumer consumer = bufferSource.m_6299_(MagicArrowRenderer.CustomRenderType.magic(getTextureLocation()));
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
      poseStack.m_85837_(-2.0, 0.0, 0.0);

      for (int j = 0; j < 4; j++) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         vertex(poseMatrix, normalMatrix, consumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, 15728880);
         vertex(poseMatrix, normalMatrix, consumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, 15728880);
         vertex(poseMatrix, normalMatrix, consumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, 15728880);
         vertex(poseMatrix, normalMatrix, consumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, 15728880);
      }
   }

   public static void vertex(
      Matrix4f pMatrix,
      Matrix3f pNormals,
      VertexConsumer pVertexBuilder,
      int pOffsetX,
      int pOffsetY,
      int pOffsetZ,
      float pTextureX,
      float pTextureY,
      int pNormalX,
      int p_113835_,
      int p_113836_,
      int pPackedLight
   ) {
      pVertexBuilder.m_85982_(pMatrix, (float)pOffsetX, (float)pOffsetY, (float)pOffsetZ)
         .m_6122_(200, 200, 200, 255)
         .m_7421_(pTextureX, pTextureY)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(pPackedLight)
         .m_85977_(pNormals, (float)pNormalX, (float)p_113836_, (float)p_113835_)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(MagicArrowProjectile entity) {
      return getTextureLocation();
   }

   public static ResourceLocation getTextureLocation() {
      return TEXTURE;
   }

   public static class CustomRenderType extends RenderType {
      public CustomRenderType(
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

      public static RenderType magic(ResourceLocation pLocation) {
         return m_173215_(
            "magic_glow",
            DefaultVertexFormat.f_85812_,
            Mode.QUADS,
            256,
            false,
            true,
            CompositeState.m_110628_()
               .m_173292_(f_173074_)
               .m_173290_(new TextureStateShard(pLocation, false, false))
               .m_110685_(f_110135_)
               .m_110661_(f_110158_)
               .m_110671_(f_110152_)
               .m_110677_(f_110154_)
               .m_110691_(false)
         );
      }

      public static RenderType magicNoCull(ResourceLocation pLocation) {
         return m_173215_(
            "magic_glow",
            DefaultVertexFormat.f_85812_,
            Mode.QUADS,
            256,
            false,
            true,
            CompositeState.m_110628_()
               .m_173292_(f_173074_)
               .m_173290_(new TextureStateShard(pLocation, false, false))
               .m_110685_(f_110135_)
               .m_110661_(f_110110_)
               .m_110671_(f_110152_)
               .m_110677_(f_110154_)
               .m_110691_(false)
         );
      }

      public static RenderType magicSwirl(ResourceLocation pLocation, float pU, float pV) {
         return m_173215_(
            "magic_glow",
            DefaultVertexFormat.f_85812_,
            Mode.QUADS,
            256,
            false,
            true,
            CompositeState.m_110628_()
               .m_173292_(f_173074_)
               .m_173290_(new TextureStateShard(pLocation, false, false))
               .m_110683_(new OffsetTexturingStateShard(pU, pV))
               .m_110685_(f_110135_)
               .m_110661_(f_110158_)
               .m_110671_(f_110152_)
               .m_110677_(f_110154_)
               .m_110691_(false)
         );
      }
   }
}
