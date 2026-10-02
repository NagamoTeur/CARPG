package io.redspace.ironsspellbooks.entity.spells.eldritch_blast;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class EldritchBlastRenderer extends EntityRenderer<EldritchBlastVisualEntity> {
   public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(
      new ResourceLocation("irons_spellbooks", "eldritch_blast_model"), "main"
   );
   private static final ResourceLocation TEXTURE_CORE = IronsSpellbooks.id("textures/entity/eldritch_blast/core.png");
   private static final ResourceLocation TEXTURE_OVERLAY = IronsSpellbooks.id("textures/entity/eldritch_blast/overlay.png");
   private final ModelPart body;

   public EldritchBlastRenderer(Context context) {
      super(context);
      ModelPart modelpart = context.m_174023_(MODEL_LAYER_LOCATION);
      this.body = modelpart.m_171324_("body");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public boolean shouldRender(EldritchBlastVisualEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
      return true;
   }

   public void render(EldritchBlastVisualEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
      poseStack.m_85836_();
      float lifetime = 8.0F;
      float scalar = 0.25F;
      float length = 32.0F * scalar * scalar;
      float f = (float)entity.f_19797_ + partialTicks;
      poseStack.m_85837_(0.0, entity.m_20191_().m_82376_() * 0.5, 0.0);
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-entity.m_146908_() - 180.0F));
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(-entity.m_146909_() - 90.0F));
      poseStack.m_85841_(scalar, scalar, scalar);
      float alpha = Mth.m_14036_(1.0F - f / lifetime, 0.0F, 1.0F);

      for (float i = 0.0F; i < entity.distance * 4.0F; i += length) {
         poseStack.m_85837_(0.0, (double)length, 0.0);
         VertexConsumer consumer = bufferSource.m_6299_(MagicArrowRenderer.CustomRenderType.magicNoCull(TEXTURE_OVERLAY));
         poseStack.m_85836_();
         float expansion = Mth.m_144920_(1.2F, 0.0F, f / lifetime);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(f * 5.0F));
         poseStack.m_85841_(expansion, 1.0F, expansion);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(45.0F));
         this.body.m_104306_(poseStack, consumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, alpha);
         poseStack.m_85849_();
         consumer = bufferSource.m_6299_(EldritchBlastRenderer.CustomerRenderType.m_110494_(TEXTURE_CORE));
         poseStack.m_85836_();
         expansion = Mth.m_144920_(1.0F, 0.0F, f / (lifetime - 5.0F));
         poseStack.m_85841_(expansion, 1.0F, expansion);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(f * -10.0F));
         this.body.m_104306_(poseStack, consumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         poseStack.m_85849_();
      }

      poseStack.m_85849_();
      super.m_7392_(entity, yaw, partialTicks, poseStack, bufferSource, light);
   }

   public ResourceLocation getTextureLocation(EldritchBlastVisualEntity entity) {
      return TEXTURE_CORE;
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
