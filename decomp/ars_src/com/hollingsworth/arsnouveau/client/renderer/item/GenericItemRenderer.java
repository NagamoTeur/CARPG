package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.AnimModItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoItemRenderer;

public class GenericItemRenderer extends GeoItemRenderer<AnimModItem> {
   public boolean isTranslucent;

   public GenericItemRenderer(AnimatedGeoModel<AnimModItem> modelProvider) {
      super(modelProvider);
   }

   public GenericItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet, AnimatedGeoModel<AnimModItem> modelProvider) {
      super(dispatcher, modelSet, modelProvider);
   }

   public GenericItemRenderer withTranslucency() {
      this.isTranslucent = true;
      return this;
   }

   public RenderType getRenderType(
      AnimModItem animatable,
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
