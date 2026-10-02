package net.thirdlife.iterrpg.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.thirdlife.iterrpg.entity.VoidElementalEntity;
import net.thirdlife.iterrpg.entity.model.VoidElementalModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidElementalRenderer extends GeoEntityRenderer<VoidElementalEntity> {
   public VoidElementalRenderer(Context renderManager) {
      super(renderManager, new VoidElementalModel());
      this.f_114477_ = 0.0F;
   }

   public RenderType getRenderType(
      VoidElementalEntity entity,
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
