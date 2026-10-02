package com.aizistral.enigmaticlegacy.client.renderers;

import com.aizistral.enigmaticlegacy.blocks.TileEndAnchor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndAnchorRenderer implements BlockEntityRenderer<TileEndAnchor> {
   public EndAnchorRenderer(Context pContext) {
   }

   public void render(
      TileEndAnchor pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay
   ) {
      if ((Integer)pBlockEntity.m_58900_().m_61143_(BlockStateProperties.f_61389_) > 0) {
         Matrix4f matrix4f = pPoseStack.m_85850_().m_85861_();
         this.renderCube(pBlockEntity, matrix4f, pBufferSource.m_6299_(this.renderType()));
      }
   }

   private void renderCube(TileEndAnchor pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer) {
      float f = this.getOffsetDown();
      float f1 = this.getOffsetUp();
      this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
      this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
      this.renderFace(pBlockEntity, pPose, pConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
      this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
      this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
      this.renderFace(pBlockEntity, pPose, pConsumer, 0.1F, 0.9F, f1, f1, 0.9F, 0.9F, 0.1F, 0.1F, Direction.UP);
   }

   private void renderFace(
      TileEndAnchor pBlockEntity,
      Matrix4f pPose,
      VertexConsumer pConsumer,
      float pX0,
      float pX1,
      float pY0,
      float pY1,
      float pZ0,
      float pZ1,
      float pZ2,
      float pZ3,
      Direction pDirection
   ) {
      if (pBlockEntity.shouldRenderFace(pDirection)) {
         pConsumer.m_85982_(pPose, pX0, pY0, pZ0).m_5752_();
         pConsumer.m_85982_(pPose, pX1, pY0, pZ1).m_5752_();
         pConsumer.m_85982_(pPose, pX1, pY1, pZ2).m_5752_();
         pConsumer.m_85982_(pPose, pX0, pY1, pZ3).m_5752_();
      }
   }

   protected float getOffsetUp() {
      return 0.9375F;
   }

   protected float getOffsetDown() {
      return 0.375F;
   }

   protected RenderType renderType() {
      return RenderType.m_173239_();
   }
}
