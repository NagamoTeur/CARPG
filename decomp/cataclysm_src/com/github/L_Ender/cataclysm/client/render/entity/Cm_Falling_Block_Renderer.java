package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class Cm_Falling_Block_Renderer extends EntityRenderer<Cm_Falling_Block_Entity> {
   private final BlockRenderDispatcher dispatcher;

   public Cm_Falling_Block_Renderer(Context p_174112_) {
      super(p_174112_);
      this.dispatcher = p_174112_.m_234597_();
   }

   public void render(Cm_Falling_Block_Entity p_114634_, float p_114635_, float p_114636_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
      BlockState blockstate = p_114634_.getBlockState();
      if (blockstate != null && blockstate.m_60799_() == RenderShape.MODEL) {
         Level level = p_114634_.f_19853_;
         if (blockstate != level.m_8055_(p_114634_.m_20183_()) && blockstate.m_60799_() != RenderShape.INVISIBLE) {
            p_114637_.m_85836_();
            BlockPos blockpos = new BlockPos(p_114634_.m_20185_(), p_114634_.m_20191_().f_82292_, p_114634_.m_20189_());
            p_114637_.m_85837_(-0.5, 0.0, -0.5);
            BakedModel model = this.dispatcher.m_110910_(blockstate);

            for (RenderType renderType : model.getRenderTypes(blockstate, RandomSource.m_216335_(blockstate.m_60726_(p_114634_.getStartPos())), ModelData.EMPTY)) {
               this.dispatcher
                  .m_110937_()
                  .tesselateBlock(
                     level,
                     model,
                     blockstate,
                     blockpos,
                     p_114637_,
                     p_114638_.m_6299_(renderType),
                     false,
                     RandomSource.m_216327_(),
                     blockstate.m_60726_(p_114634_.getStartPos()),
                     OverlayTexture.f_118083_,
                     ModelData.EMPTY,
                     renderType
                  );
            }

            p_114637_.m_85849_();
            super.m_7392_(p_114634_, p_114635_, p_114636_, p_114637_, p_114638_, p_114639_);
         }
      }
   }

   public ResourceLocation getTextureLocation(Cm_Falling_Block_Entity p_114632_) {
      return TextureAtlas.f_118259_;
   }
}
