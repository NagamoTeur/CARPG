package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class EnchantedFallingBlockRenderer<T extends EnchantedFallingBlock> extends EntityRenderer<T> {
   private final BlockRenderDispatcher dispatcher;

   public EnchantedFallingBlockRenderer(Context p_174112_) {
      super(p_174112_);
      this.f_114477_ = 0.5F;
      this.dispatcher = p_174112_.m_234597_();
   }

   public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      try {
         BlockState blockstate = pEntity.getBlockState();
         Level level = pEntity.m_9236_();
         if (blockstate != level.m_8055_(pEntity.m_20183_()) && blockstate.m_60799_() != RenderShape.INVISIBLE) {
            pMatrixStack.m_85836_();
            BlockPos blockpos = new BlockPos(pEntity.m_20185_(), pEntity.m_20191_().f_82292_, pEntity.m_20189_());
            pMatrixStack.m_85837_(-0.5, 0.0, -0.5);
            BakedModel model = this.dispatcher.m_110910_(blockstate);

            for (RenderType renderType : model.getRenderTypes(blockstate, RandomSource.m_216335_(blockstate.m_60726_(pEntity.getStartPos())), ModelData.EMPTY)) {
               this.dispatcher
                  .m_110937_()
                  .tesselateBlock(
                     level,
                     model,
                     blockstate,
                     blockpos,
                     pMatrixStack,
                     pBuffer.m_6299_(renderType),
                     false,
                     RandomSource.m_216327_(),
                     blockstate.m_60726_(pEntity.getStartPos()),
                     OverlayTexture.f_118083_,
                     ModelData.EMPTY,
                     renderType
                  );
            }

            pMatrixStack.m_85849_();
            super.m_7392_(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
         }
      } catch (Exception var13) {
      }
   }

   public ResourceLocation getTextureLocation(EnchantedFallingBlock pEntity) {
      return TextureAtlas.f_118259_;
   }
}
