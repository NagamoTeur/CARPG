package net.cisco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cisco.entity.VengefulAfterImageEntity;
import net.cisco.entity.model.VengefulAfterImageModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VengefulAfterImageRenderer extends GeoEntityRenderer<VengefulAfterImageEntity> {
   public VengefulAfterImageRenderer(Context renderManager) {
      super(renderManager, new VengefulAfterImageModel());
      this.f_114477_ = 0.5F;
   }

   public RenderType getRenderType(
      VengefulAfterImageEntity entity,
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
