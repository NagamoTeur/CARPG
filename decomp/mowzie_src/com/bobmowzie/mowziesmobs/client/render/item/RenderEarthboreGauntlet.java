package com.bobmowzie.mowziesmobs.client.render.item;

import com.bobmowzie.mowziesmobs.client.model.item.ModelEarthboreGauntlet;
import com.bobmowzie.mowziesmobs.server.item.ItemEarthboreGauntlet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RenderEarthboreGauntlet extends GeoItemRenderer<ItemEarthboreGauntlet> {
   public RenderEarthboreGauntlet() {
      super(new ModelEarthboreGauntlet());
   }

   public void m_108829_(
      ItemStack itemStack, TransformType transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      if (!this.modelProvider.getAnimationProcessor().getModelRendererList().isEmpty()) {
         if (transformType != TransformType.THIRD_PERSON_LEFT_HAND && transformType != TransformType.FIRST_PERSON_LEFT_HAND) {
            this.modelProvider.getBone("root").setHidden(false);
            this.modelProvider.getBone("rootFlipped").setHidden(true);
         } else {
            this.modelProvider.getBone("root").setHidden(true);
            this.modelProvider.getBone("rootFlipped").setHidden(false);
         }
      }

      super.m_108829_(itemStack, transformType, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
   }
}
