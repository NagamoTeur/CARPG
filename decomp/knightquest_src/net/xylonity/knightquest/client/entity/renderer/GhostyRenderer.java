package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.GhostyModel;
import net.xylonity.knightquest.common.entity.entities.GhostyEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GhostyRenderer extends GeoEntityRenderer<GhostyEntity> {
   public GhostyRenderer(Context renderManager) {
      super(renderManager, new GhostyModel());
   }

   public ResourceLocation getTextureLocation(GhostyEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/ghosty.png");
   }

   public void render(GhostyEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.1F, 1.1F, 1.1F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
