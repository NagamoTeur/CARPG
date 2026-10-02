package com.aizistral.enigmaticlegacy.client.renderers;

import com.aizistral.enigmaticlegacy.entities.UltimateWitherSkullEntity;
import com.aizistral.etherium.client.ShieldAuraLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UltimateWitherSkullRenderer extends EntityRenderer<UltimateWitherSkullEntity> {
   private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");
   private final SkullModel skeletonHeadModel;

   public UltimateWitherSkullRenderer(Context renderManagerIn) {
      super(renderManagerIn);
      this.skeletonHeadModel = new SkullModel(renderManagerIn.m_174023_(ModelLayers.f_171220_));
   }

   protected int getBlockLightLevel(UltimateWitherSkullEntity entityIn, BlockPos partialTicks) {
      return 15;
   }

   public void render(
      UltimateWitherSkullEntity entityIn, float entityYaw, float partialTicks, PoseStack PoseStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      PoseStackIn.m_85836_();
      float inflate = entityIn.isSkullInvulnerable() ? 1.4F : 1.0F;
      PoseStackIn.m_85841_(-inflate, -inflate, inflate);
      float f = Mth.m_14201_(entityIn.f_19859_, entityIn.m_146908_(), partialTicks);
      float f1 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(this.skeletonHeadModel.m_103119_(this.getTextureLocation(entityIn)));
      this.skeletonHeadModel.m_6251_(0.0F, f, f1);
      this.skeletonHeadModel.m_7695_(PoseStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      PoseStackIn.m_85849_();
      if (entityIn.isSkullInvulnerable()) {
         float fullTicks = (float)entityIn.f_19797_ + partialTicks;
         this.renderShield(PoseStackIn, packedLightIn, fullTicks, inflate + 0.1F, bufferIn);
         this.renderShield(PoseStackIn, packedLightIn, fullTicks, inflate + 0.2F, bufferIn);
      }

      super.m_7392_(entityIn, entityYaw, partialTicks, PoseStackIn, bufferIn, packedLightIn);
   }

   private void renderShield(PoseStack matrix, int light, float fullTicks, float scale, MultiBufferSource bufferIn) {
      matrix.m_85836_();
      matrix.m_85841_(-scale, -scale, scale);
      VertexConsumer ivertexbuilder1 = bufferIn.m_6299_(
         RenderType.m_110436_(ShieldAuraLayer.getTextureLocation(), ShieldAuraLayer.xOffset(fullTicks), fullTicks * 0.01F)
      );
      this.skeletonHeadModel.m_7695_(matrix, ivertexbuilder1, light, OverlayTexture.f_118083_, 0.5F, 0.5F, 0.5F, 1.0F);
      matrix.m_85849_();
   }

   public ResourceLocation getTextureLocation(UltimateWitherSkullEntity entity) {
      return WITHER_TEXTURES;
   }
}
