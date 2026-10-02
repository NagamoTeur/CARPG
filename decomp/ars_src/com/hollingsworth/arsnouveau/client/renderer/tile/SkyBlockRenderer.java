package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.client.ShaderRegistry;
import com.hollingsworth.arsnouveau.common.block.tile.SkyBlockTile;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(Dist.CLIENT)
public class SkyBlockRenderer<T extends SkyBlockTile> implements BlockEntityRenderer<T> {
   private BlockRenderDispatcher blockRenderer;

   public SkyBlockRenderer(Context rendererDispatcherIn) {
      this.blockRenderer = rendererDispatcherIn.m_173584_();
   }

   public void render(
      SkyBlockTile tileEntityIn, float partialTicks, PoseStack pPoseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      if (tileEntityIn.showFacade()
         || Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_21023_((MobEffect)ModPotions.MAGIC_FIND_EFFECT.get())) {
         BlockState renderState = tileEntityIn.mimicState;
         if (renderState == null) {
            return;
         }

         ModelBlockRenderer.m_111000_();
         pPoseStack.m_85836_();
         this.renderBlock(tileEntityIn.m_58899_(), renderState, pPoseStack, bufferIn, tileEntityIn.m_58904_(), false, combinedOverlayIn);
         pPoseStack.m_85849_();
         ModelBlockRenderer.m_111077_();
      } else {
         this.renderCube(tileEntityIn, pPoseStack.m_85850_().m_85861_(), bufferIn.m_6299_(ShaderRegistry.SKY_RENDER_TYPE));
      }
   }

   private void renderCube(SkyBlockTile tileEntityIn, Matrix4f p_228883_4_, VertexConsumer p_228883_5_) {
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
      this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
   }

   private void renderFace(
      SkyBlockTile tileEntityIn,
      Matrix4f matrix,
      VertexConsumer buffer,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      Direction direction
   ) {
      buffer.m_85982_(matrix, f, h, j).m_5752_();
      buffer.m_85982_(matrix, g, h, k).m_5752_();
      buffer.m_85982_(matrix, g, i, l).m_5752_();
      buffer.m_85982_(matrix, f, i, m).m_5752_();
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
      return 256;
   }
}
