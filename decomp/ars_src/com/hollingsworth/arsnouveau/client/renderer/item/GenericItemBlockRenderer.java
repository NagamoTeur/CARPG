package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.AnimBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoItemRenderer;

public class GenericItemBlockRenderer extends GeoItemRenderer<AnimBlockItem> {
   public boolean isTranslucent;

   public GenericItemBlockRenderer(AnimatedGeoModel modelProvider) {
      super(new GenericItemModel(modelProvider));
   }

   public GenericItemBlockRenderer withTranslucency() {
      this.isTranslucent = true;
      return this;
   }

   public RenderType getRenderType(
      AnimBlockItem animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return this.isTranslucent
         ? RenderType.m_110473_(textureLocation)
         : super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
   }
}
