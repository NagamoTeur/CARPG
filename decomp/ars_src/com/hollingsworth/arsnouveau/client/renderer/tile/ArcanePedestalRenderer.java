package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ArcanePedestalRenderer implements BlockEntityRenderer<ArcanePedestalTile> {
   private final EntityRenderDispatcher entityRenderer;

   public ArcanePedestalRenderer(Context pContext) {
      this.entityRenderer = pContext.m_234446_();
   }

   public void render(
      ArcanePedestalTile tileEntityIn, float pPartialTick, PoseStack matrixStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay
   ) {
      if (tileEntityIn.getStack() != null && !tileEntityIn.getStack().m_41619_()) {
         float yOffset = 0.5F;
         float xOffset = 0.5F;
         float zOffset = 0.5F;
         matrixStack.m_85836_();
         if (tileEntityIn.m_58900_().m_61138_(BlockStateProperties.f_61372_)) {
            switch ((Direction)tileEntityIn.m_58900_().m_61143_(BlockStateProperties.f_61372_)) {
               case DOWN:
                  yOffset = 0.4F;
                  break;
               case WEST:
                  xOffset = 0.45F;
                  break;
               case EAST:
                  xOffset = 0.55F;
                  break;
               case SOUTH:
                  zOffset = 0.55F;
                  break;
               case NORTH:
                  zOffset = 0.45F;
                  break;
               default:
                  yOffset = 0.6F;
            }
         } else {
            yOffset = 1.1F;
         }

         matrixStack.m_85837_((double)xOffset, (double)yOffset, (double)zOffset);
         matrixStack.m_85841_(0.5F, 0.5F, 0.5F);
         matrixStack.m_85845_(Vector3f.f_122225_.m_122240_((pPartialTick + (float)ClientInfo.ticksInGame) * 3.0F));
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               tileEntityIn.getStack(), TransformType.FIXED, pPackedLight, pPackedOverlay, matrixStack, pBufferSource, (int)tileEntityIn.m_58899_().m_121878_()
            );
         matrixStack.m_85849_();
      }
   }
}
