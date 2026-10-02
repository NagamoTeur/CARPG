package com.github.alexthe666.alexsmobs.client.render.tile;

import com.github.alexthe666.alexsmobs.block.BlockVoidWormBeak;
import com.github.alexthe666.alexsmobs.client.model.ModelVoidWormBeak;
import com.github.alexthe666.alexsmobs.tileentity.TileEntityVoidWormBeak;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class RenderVoidWormBeak<T extends TileEntityVoidWormBeak> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("alexsmobs:textures/entity/void_worm/void_worm_beak.png");
   private static ModelVoidWormBeak HEAD_MODEL = new ModelVoidWormBeak();

   public RenderVoidWormBeak(Context rendererDispatcherIn) {
   }

   public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      matrixStackIn.m_85836_();
      Direction dir = (Direction)tileEntityIn.m_58900_().m_61143_(BlockVoidWormBeak.FACING);
      if (dir == Direction.UP) {
         matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      } else if (dir == Direction.DOWN) {
         matrixStackIn.m_85837_(0.5, -0.5, 0.5);
      } else if (dir == Direction.NORTH) {
         matrixStackIn.m_85837_(0.5, 0.5, -0.5);
      } else if (dir == Direction.EAST) {
         matrixStackIn.m_85837_(1.5, 0.5, 0.5);
      } else if (dir == Direction.SOUTH) {
         matrixStackIn.m_85837_(0.5, 0.5, 1.5);
      } else if (dir == Direction.WEST) {
         matrixStackIn.m_85837_(-0.5, 0.5, 0.5);
      }

      matrixStackIn.m_85845_(dir.m_122424_().m_122406_());
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, -0.01F, 0.0);
      HEAD_MODEL.renderBeak(tileEntityIn, partialTicks);
      HEAD_MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }
}
