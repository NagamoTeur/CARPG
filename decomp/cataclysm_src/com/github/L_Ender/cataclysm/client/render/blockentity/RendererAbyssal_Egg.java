package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.cataclysm.client.model.block.Abyssal_Egg_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RendererAbyssal_Egg implements BlockEntityRenderer<Abyssal_Egg_Block_Entity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/abyssal_egg.png");
   private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation("cataclysm", "textures/block/abyssal_egg_layer.png");
   private static final Abyssal_Egg_Model MODEL = new Abyssal_Egg_Model();

   public RendererAbyssal_Egg(Context rendererDispatcherIn) {
   }

   public void render(
      Abyssal_Egg_Block_Entity tileEntityIn,
      float partialTicks,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
      MODEL.animate(tileEntityIn, partialTicks);
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(CMRenderTypes.getGhost(LAYER_TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
   }
}
