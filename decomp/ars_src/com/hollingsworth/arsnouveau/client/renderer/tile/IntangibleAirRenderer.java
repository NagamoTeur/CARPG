package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.IntangibleAirBlock;
import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class IntangibleAirRenderer implements BlockEntityRenderer<IntangibleAirTile> {
   public IntangibleAirRenderer(Context rendererDispatcherIn) {
   }

   private void renderModelBrightnessColorQuads(
      Pose matrixEntry,
      VertexConsumer builder,
      float red,
      float green,
      float blue,
      float alpha,
      List<BakedQuad> listQuads,
      int combinedLightsIn,
      int combinedOverlayIn
   ) {
      for (BakedQuad bakedquad : listQuads) {
         float f;
         float f1;
         float f2;
         if (bakedquad.m_111304_()) {
            f = red;
            f1 = green;
            f2 = blue;
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         builder.putBulkData(matrixEntry, bakedquad, f, f1, f2, alpha, combinedLightsIn, combinedOverlayIn, true);
      }
   }

   public void render(
      IntangibleAirTile tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn
   ) {
      BlockState renderState = Block.m_49803_(tileEntityIn.stateID);
      if (renderState != null) {
         double scale = (double)tileEntityIn.duration / (double)tileEntityIn.maxLength;
         BlockRenderDispatcher blockrendererdispatcher = Minecraft.m_91087_().m_91289_();
         RenderSystem.m_157456_(0, InventoryMenu.f_39692_);
         BakedModel ibakedmodel = blockrendererdispatcher.m_110910_(renderState);
         BlockColors blockColors = Minecraft.m_91087_().m_91298_();
         int color = blockColors.m_92577_(renderState, tileEntityIn.m_58904_(), tileEntityIn.m_58899_(), 0);
         float f = (float)(color >> 16 & 0xFF) / 255.0F;
         float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
         float f2 = (float)(color & 0xFF) / 255.0F;
         matrixStackIn.m_85836_();

         for (Direction direction : Direction.values()) {
            if (!(tileEntityIn.m_58904_().m_8055_(tileEntityIn.m_58899_().m_121945_(direction)).m_60734_() instanceof IntangibleAirBlock)) {
               this.renderModelBrightnessColorQuads(
                  matrixStackIn.m_85850_(),
                  bufferIn.m_6299_(IntangibleAirRenderer.DummyRender.RenderBlock),
                  f,
                  f1,
                  f2,
                  (float)scale,
                  ibakedmodel.getQuads(renderState, direction, RandomSource.m_216335_(Mth.m_14057_(tileEntityIn.m_58899_())), ModelData.EMPTY, null),
                  combinedLightIn,
                  combinedOverlayIn
               );
            }
         }

         matrixStackIn.m_85849_();
      }
   }

   static class DummyRender extends RenderType {
      public static final RenderType RenderBlock = m_173215_(
         "IntangibleRenderBlock",
         DefaultVertexFormat.f_85811_,
         Mode.QUADS,
         256,
         false,
         false,
         CompositeState.m_110628_()
            .m_173292_(ShaderStateShard.f_173097_)
            .m_110671_(f_110152_)
            .m_173290_(f_110145_)
            .m_110669_(f_110119_)
            .m_110685_(f_110139_)
            .m_110663_(f_110113_)
            .m_110661_(f_110158_)
            .m_110687_(f_110115_)
            .m_110691_(false)
      );

      public DummyRender(
         String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_
      ) {
         super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
      }
   }
}
