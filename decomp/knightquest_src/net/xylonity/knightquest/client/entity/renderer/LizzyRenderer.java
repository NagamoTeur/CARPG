package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.LizzyModel;
import net.xylonity.knightquest.common.entity.entities.LizzyEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LizzyRenderer extends GeoEntityRenderer<LizzyEntity> {
   public LizzyRenderer(Context renderManager) {
      super(renderManager, new LizzyModel());
   }

   public ResourceLocation getTextureLocation(LizzyEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/lizzy.png");
   }

   public void render(LizzyEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.1F, 1.1F, 1.1F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
