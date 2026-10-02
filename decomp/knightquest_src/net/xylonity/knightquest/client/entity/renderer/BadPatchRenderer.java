package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.BadPatchModel;
import net.xylonity.knightquest.common.entity.entities.BadPatchEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BadPatchRenderer extends GeoEntityRenderer<BadPatchEntity> {
   public BadPatchRenderer(Context renderManager) {
      super(renderManager, new BadPatchModel());
   }

   public ResourceLocation getTextureLocation(BadPatchEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/bad_patch.png");
   }

   public void render(BadPatchEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(0.85F, 0.85F, 0.85F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
