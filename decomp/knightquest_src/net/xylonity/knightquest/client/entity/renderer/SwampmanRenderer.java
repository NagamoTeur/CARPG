package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.SwampmanModel;
import net.xylonity.knightquest.common.entity.entities.SwampmanEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SwampmanRenderer extends GeoEntityRenderer<SwampmanEntity> {
   public SwampmanRenderer(Context renderManager) {
      super(renderManager, new SwampmanModel());
   }

   public ResourceLocation getTextureLocation(SwampmanEntity animatable) {
      return animatable.getPhase() == 2
         ? new ResourceLocation("knightquest", "textures/entity/swampman_2.png")
         : new ResourceLocation("knightquest", "textures/entity/swampman.png");
   }

   public void render(SwampmanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.35F, 0.35F, 0.35F);
      } else {
         poseStack.m_85841_(1.1F, 1.1F, 1.1F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
