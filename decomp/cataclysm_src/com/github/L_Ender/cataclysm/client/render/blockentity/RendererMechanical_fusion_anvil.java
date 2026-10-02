package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.cataclysm.blocks.Mechanical_fusion_Anvil;
import com.github.L_Ender.cataclysm.client.model.block.Mechanical_Anvil_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class RendererMechanical_fusion_anvil<T extends Mechanical_fusion_Anvil_Block_Entity> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/mechanical_fusion_anvil.png");
   private static final Mechanical_Anvil_Model MODEL = new Mechanical_Anvil_Model();

   public RendererMechanical_fusion_anvil(Context rendererDispatcherIn) {
   }

   public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      matrixStackIn.m_85836_();
      Direction dir = (Direction)tileEntityIn.m_58900_().m_61143_(Mechanical_fusion_Anvil.FACING);
      if (dir == Direction.NORTH) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.EAST) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.SOUTH) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.WEST) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      }

      matrixStackIn.m_85845_(dir.m_122424_().m_122406_());
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      matrixStackIn.m_85836_();
      MODEL.animate(tileEntityIn, partialTicks);
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }
}
