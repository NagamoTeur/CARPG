package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.ShieldModel;
import net.xylonity.knightquest.common.entity.entities.GhastlingEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ShieldRenderer extends GeoEntityRenderer<GhastlingEntity> {
   public ShieldRenderer(Context renderManager) {
      super(renderManager, new ShieldModel());
   }

   public ResourceLocation getTextureLocation(GhastlingEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/shield.png");
   }

   public void render(GhastlingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.15F, 1.15F, 1.15F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
