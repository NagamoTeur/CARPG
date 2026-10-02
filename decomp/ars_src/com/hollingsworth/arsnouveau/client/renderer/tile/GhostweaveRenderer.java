package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;

public class GhostweaveRenderer implements BlockEntityRenderer<GhostWeaveTile> {
   private BlockRenderDispatcher blockRenderer;

   public GhostweaveRenderer(Context pContext) {
      this.blockRenderer = pContext.m_173584_();
   }

   public void render(
      GhostWeaveTile tileEntityIn, float partialTick, PoseStack pPoseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      BlockState renderState = tileEntityIn.mimicState;
      boolean hasMagicFind = Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_21023_((MobEffect)ModPotions.MAGIC_FIND_EFFECT.get());
      boolean shouldShow = hasMagicFind || !tileEntityIn.isInvisible();
      if (shouldShow) {
         ModelBlockRenderer.m_111000_();
         pPoseStack.m_85836_();
         this.renderBlock(tileEntityIn.m_58899_(), renderState, pPoseStack, bufferIn, tileEntityIn.m_58904_(), false, combinedOverlayIn);
         pPoseStack.m_85849_();
         ModelBlockRenderer.m_111077_();
      }
   }

   private void renderBlock(
      BlockPos pPos, BlockState pState, PoseStack pPoseStack, MultiBufferSource pBufferSource, Level pLevel, boolean pExtended, int pPackedOverlay
   ) {
      ForgeHooksClient.renderPistonMovedBlocks(
         pPos,
         pState,
         pPoseStack,
         pBufferSource,
         pLevel,
         pExtended,
         pPackedOverlay,
         this.blockRenderer == null ? (this.blockRenderer = Minecraft.m_91087_().m_91289_()) : this.blockRenderer
      );
   }

   public int m_142163_() {
      return 68;
   }
}
