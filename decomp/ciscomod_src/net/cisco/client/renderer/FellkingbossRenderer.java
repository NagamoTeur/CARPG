package net.cisco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cisco.entity.FellkingbossEntity;
import net.cisco.entity.model.FellkingbossModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FellkingbossRenderer extends GeoEntityRenderer<FellkingbossEntity> {
   public FellkingbossRenderer(Context renderManager) {
      super(renderManager, new FellkingbossModel());
      this.f_114477_ = 0.5F;
   }

   public RenderType getRenderType(
      FellkingbossEntity entity,
      float partialTicks,
      PoseStack stack,
      MultiBufferSource renderTypeBuffer,
      VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      stack.m_85841_(1.0F, 1.0F, 1.0F);
      return RenderType.m_110473_(this.getTextureLocation(entity));
   }
}
