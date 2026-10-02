package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;

public class WealdWalkerRenderer<W extends WealdWalker> extends GeoEntityRenderer<W> {
   public WealdWalkerRenderer(Context manager, String type) {
      super(manager, new WealdWalkerModel<>(type));
   }

   public void render(W entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
   }

   public RenderType getRenderType(
      W animatable,
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
