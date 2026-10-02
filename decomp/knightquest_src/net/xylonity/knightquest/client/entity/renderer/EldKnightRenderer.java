package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.EldKnightModel;
import net.xylonity.knightquest.common.entity.entities.EldKnightEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EldKnightRenderer extends GeoEntityRenderer<EldKnightEntity> {
   public EldKnightRenderer(Context renderManager) {
      super(renderManager, new EldKnightModel());
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EldKnightEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/eldknight.png");
   }

   public void render(
      EldKnightEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      if (entity.m_6162_()) {
         poseStack.m_85841_(0.4F, 0.4F, 0.4F);
      } else {
         poseStack.m_85841_(1.1F, 1.1F, 1.1F);
      }

      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
