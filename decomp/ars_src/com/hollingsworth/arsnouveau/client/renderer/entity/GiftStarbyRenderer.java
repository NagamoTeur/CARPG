package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.GiftStarbuncle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;

public class GiftStarbyRenderer extends GeoEntityRenderer<GiftStarbuncle> {
   public GiftStarbyRenderer(Context renderManager) {
      super(renderManager, new GiftStarbyModel());
   }

   public RenderType getRenderType(
      GiftStarbuncle animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110458_(textureLocation);
   }
}
