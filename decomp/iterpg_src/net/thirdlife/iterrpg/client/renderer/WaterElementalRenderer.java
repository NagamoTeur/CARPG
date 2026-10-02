package net.thirdlife.iterrpg.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.entity.WaterElementalEntity;
import net.thirdlife.iterrpg.entity.model.WaterElementalModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WaterElementalRenderer extends GeoEntityRenderer<WaterElementalEntity> {
   public WaterElementalRenderer(Context renderManager) {
      super(renderManager, new WaterElementalModel());
      this.f_114477_ = 0.0F;
   }

   public RenderType getRenderType(
      WaterElementalEntity entity,
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
