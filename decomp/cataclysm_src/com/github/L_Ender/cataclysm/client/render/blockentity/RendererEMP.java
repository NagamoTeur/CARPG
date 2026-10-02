package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.client.model.block.EMP_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class RendererEMP<T extends EMP_Block_Entity> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/emp.png");
   private static final EMP_Model MODEL_EMP = new EMP_Model();

   public RendererEMP(Context rendererDispatcherIn) {
   }

   public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      matrixStackIn.m_85836_();
      Direction dir = (Direction)tileEntityIn.m_58900_().m_61143_(EMP_Block.TIP_DIRECTION);
      if (dir == Direction.UP) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else {
         matrixStackIn.m_85837_(0.5, -0.5, 0.5);
      }

      matrixStackIn.m_85845_(dir.m_122424_().m_122406_());
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, 0.15F, 0.0);
      matrixStackIn.m_85841_(0.9F, 0.9F, 0.9F);
      MODEL_EMP.animate(tileEntityIn, partialTicks);
      MODEL_EMP.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }
}
