package net.cisco.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.cisco.entity.LegionnaireJotunnEntity;
import net.cisco.entity.model.LegionnaireJotunnModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LegionnaireJotunnRenderer extends GeoEntityRenderer<LegionnaireJotunnEntity> {
   public LegionnaireJotunnRenderer(Context renderManager) {
      super(renderManager, new LegionnaireJotunnModel());
      this.f_114477_ = 0.8F;
   }

   public RenderType getRenderType(
      LegionnaireJotunnEntity entity,
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
