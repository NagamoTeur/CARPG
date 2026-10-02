package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.Deepling.AbstractDeepling;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AbstractDeepling_Layer<T extends AbstractDeepling> extends RenderLayer<T, EntityModel<T>> {
   private final ResourceLocation texture;
   private final RenderType renderType;

   public AbstractDeepling_Layer(RenderLayerParent<T, EntityModel<T>> renderer, ResourceLocation texture) {
      super(renderer);
      this.texture = texture;
      this.renderType = CMRenderTypes.CMEyes(texture);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer VertexConsumer = bufferIn.m_6299_(this.renderType);
      float strength = 0.5F + Mth.m_14036_((float)Math.cos((double)(((float)entitylivingbaseIn.LayerTicks + partialTicks) * 0.1F)) - 0.5F, -0.5F, 0.5F);
      strength += Mth.m_14179_(partialTicks, entitylivingbaseIn.oLayerBrightness, entitylivingbaseIn.LayerBrightness) * 1.0F * (float) Math.PI;
      strength = Mth.m_14036_(strength, 0.1F, 1.0F);
      this.m_117386_().m_7695_(matrixStackIn, VertexConsumer, 15728640, OverlayTexture.f_118083_, strength, strength, strength, 1.0F);
   }
}
