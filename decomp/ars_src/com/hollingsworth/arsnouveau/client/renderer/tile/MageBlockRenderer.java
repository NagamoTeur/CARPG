package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.entity.EnchantedMageblock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class MageBlockRenderer extends GeoProjectilesRenderer<EnchantedMageblock> {
   public static GenericModel model = new GenericModel("mage_block");

   public MageBlockRenderer(Context rendererDispatcherIn) {
      super(rendererDispatcherIn, model);
   }

   public void render(
      GeoModel model,
      EnchantedMageblock animatable,
      float partialTicks,
      RenderType type,
      PoseStack matrixStackIn,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      ParticleColor color = animatable.getParticleColor();
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, -0.01, 0.0);
      super.render(
         model,
         animatable,
         partialTicks,
         type,
         matrixStackIn,
         renderTypeBuffer,
         vertexBuilder,
         packedLightIn,
         packedOverlayIn,
         color.getRed(),
         color.getGreen(),
         color.getBlue(),
         alpha
      );
      matrixStackIn.m_85849_();
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
