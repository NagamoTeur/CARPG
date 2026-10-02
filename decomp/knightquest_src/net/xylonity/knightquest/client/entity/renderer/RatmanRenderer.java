package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.RatmanModel;
import net.xylonity.knightquest.common.entity.entities.RatmanEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RatmanRenderer extends GeoEntityRenderer<RatmanEntity> {
   public RatmanRenderer(Context renderManager) {
      super(renderManager, new RatmanModel());
   }

   public ResourceLocation getTextureLocation(RatmanEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/ratman" + animatable.getVariation() + ".png");
   }

   public void render(RatmanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.0F, 1.0F, 1.0F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
