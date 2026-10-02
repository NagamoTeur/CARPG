package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.GremlinModel;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GremlinRenderer extends GeoEntityRenderer<GremlinEntity> {
   public GremlinRenderer(Context renderManager) {
      super(renderManager, new GremlinModel());
   }

   public ResourceLocation getTextureLocation(GremlinEntity animatable) {
      return animatable.getPhase() == 2
         ? new ResourceLocation("knightquest", "textures/entity/gremlin_angry.png")
         : new ResourceLocation("knightquest", "textures/entity/gremlin.png");
   }

   public void render(GremlinEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.1F, 1.1F, 1.1F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
