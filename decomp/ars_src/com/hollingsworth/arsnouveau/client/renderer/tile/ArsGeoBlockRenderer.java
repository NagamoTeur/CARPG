package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoBlockRenderer;

public abstract class ArsGeoBlockRenderer<T extends BlockEntity & IAnimatable> extends GeoBlockRenderer<T> {
   public ArsGeoBlockRenderer(Context rendererProvider, AnimatedGeoModel<T> modelProvider) {
      super(rendererProvider, modelProvider);
   }

   @Override
   public void render(T tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.m_85837_(0.0, -0.01F, 0.0);
      super.render(tile, partialTick, poseStack, bufferSource, packedLight);
   }
}
