package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.block.tile.ItemDetectorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

public class ItemDetectorRenderer implements BlockEntityRenderer<ItemDetectorTile> {
   private final EntityRenderDispatcher entityRenderer;

   public ItemDetectorRenderer(Context pContext) {
      this.entityRenderer = pContext.m_234446_();
   }

   public void render(
      ItemDetectorTile tileEntityIn, float pPartialTick, PoseStack matrixStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay
   ) {
      if (tileEntityIn.filterStack != null && !tileEntityIn.filterStack.m_41619_()) {
         float yOffset = 0.5F;
         float xOffset = 0.5F;
         float zOffset = 0.5F;
         float ticks = pPartialTick + (float)ClientInfo.ticksInGame;
         matrixStack.m_85836_();
         matrixStack.m_85837_((double)xOffset, (double)yOffset, (double)zOffset);
         matrixStack.m_85841_(0.5F, 0.5F, 0.5F);
         matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(ticks * 2.0F));
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               tileEntityIn.filterStack,
               TransformType.FIXED,
               pPackedLight,
               pPackedOverlay,
               matrixStack,
               pBufferSource,
               (int)tileEntityIn.m_58899_().m_121878_()
            );
         matrixStack.m_85849_();
      }
   }
}
