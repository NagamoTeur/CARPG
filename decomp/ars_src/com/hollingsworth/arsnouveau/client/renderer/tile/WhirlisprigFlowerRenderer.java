package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemBlockRenderer;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoModel;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class WhirlisprigFlowerRenderer extends ArsGeoBlockRenderer<WhirlisprigTile> {
   public static AnimatedGeoModel model = new GenericModel("whirlisprig_blossom");

   public WhirlisprigFlowerRenderer(Context rendererDispatcherIn) {
      this(rendererDispatcherIn, model);
   }

   public WhirlisprigFlowerRenderer(Context rendererDispatcherIn, AnimatedGeoModel<WhirlisprigTile> modelProvider) {
      super(rendererDispatcherIn, modelProvider);
   }

   public void render(
      GeoModel model,
      WhirlisprigTile animatable,
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
      super.render(
         model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha
      );
   }

   public static GenericItemBlockRenderer getISTER() {
      return new GenericItemBlockRenderer(model);
   }
}
