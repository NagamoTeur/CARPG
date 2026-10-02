package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Cataclysm_Skull_BlockEntity;
import com.github.L_Ender.cataclysm.blocks.Abstract_Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.blocks.Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.blocks.Wall_Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.block.AptrgangrHeadModel;
import com.github.L_Ender.cataclysm.client.model.block.Cataclysm_Skull_Model_Base;
import com.github.L_Ender.cataclysm.client.model.block.DraugrHeadModel;
import com.github.L_Ender.cataclysm.client.model.block.KobolediatorHeadModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Cataclysm_Skull_Block_Renderer implements BlockEntityRenderer<Cataclysm_Skull_BlockEntity> {
   private final Map<Cataclysm_Skull_Block.Type, Cataclysm_Skull_Model_Base> modelByType;
   public static final Map<Cataclysm_Skull_Block.Type, ResourceLocation> SKIN_BY_TYPE = (Map<Cataclysm_Skull_Block.Type, ResourceLocation>)Util.m_137469_(
      Maps.newHashMap(), p_261388_ -> {
         p_261388_.put(Cataclysm_Skull_Block.Types.KOBOLEDIATOR, new ResourceLocation("cataclysm", "textures/entity/koboleton/kobolediator.png"));
         p_261388_.put(Cataclysm_Skull_Block.Types.APTRGANGR, new ResourceLocation("cataclysm", "textures/entity/draugar/aptrgangr.png"));
         p_261388_.put(Cataclysm_Skull_Block.Types.DRAUGR, new ResourceLocation("cataclysm", "textures/entity/draugar/draugr.png"));
      }
   );

   public static Map<Cataclysm_Skull_Block.Type, Cataclysm_Skull_Model_Base> createSkullRenderers(EntityModelSet p_173662_) {
      Builder<Cataclysm_Skull_Block.Type, Cataclysm_Skull_Model_Base> builder = ImmutableMap.builder();
      builder.put(Cataclysm_Skull_Block.Types.KOBOLEDIATOR, new KobolediatorHeadModel(p_173662_.m_171103_(CMModelLayers.KOBOLEDIATOR_HEAD_MODEL)));
      builder.put(Cataclysm_Skull_Block.Types.APTRGANGR, new AptrgangrHeadModel(p_173662_.m_171103_(CMModelLayers.APTRGANGR_HEAD_MODEL)));
      builder.put(Cataclysm_Skull_Block.Types.DRAUGR, new DraugrHeadModel(p_173662_.m_171103_(CMModelLayers.DRAUGR_HEAD_MODEL)));
      return builder.build();
   }

   public Cataclysm_Skull_Block_Renderer(Context p_173660_) {
      this.modelByType = createSkullRenderers(p_173660_.m_173585_());
   }

   public void render(Cataclysm_Skull_BlockEntity p_112534_, float p_112535_, PoseStack p_112536_, MultiBufferSource p_112537_, int p_112538_, int p_112539_) {
      float f = p_112534_.getAnimation(p_112535_);
      BlockState blockstate = p_112534_.m_58900_();
      boolean flag = blockstate.m_60734_() instanceof Wall_Cataclysm_Skull_Block;
      Direction direction = flag ? (Direction)blockstate.m_61143_(Wall_Cataclysm_Skull_Block.FACING) : null;
      float f1 = 22.5F * (float)(flag ? (2 + direction.m_122416_()) * 4 : (Integer)blockstate.m_61143_(Cataclysm_Skull_Block.ROTATION));
      Cataclysm_Skull_Block.Type Cataclysm_Skull_Block$type = ((Abstract_Cataclysm_Skull_Block)blockstate.m_60734_()).getType();
      Cataclysm_Skull_Model_Base Cataclysm_Skull_Model_Base = this.modelByType.get(Cataclysm_Skull_Block$type);
      ResourceLocation resourcelocation = SKIN_BY_TYPE.get(Cataclysm_Skull_Block$type);
      RenderType rendertype = RenderType.m_110464_(resourcelocation);
      renderSkull(direction, f1, f, p_112536_, p_112537_, p_112538_, Cataclysm_Skull_Model_Base, rendertype);
   }

   public static void renderSkull(
      @Nullable Direction p_173664_,
      float p_173665_,
      float p_173666_,
      PoseStack p_173667_,
      MultiBufferSource p_173668_,
      int p_173669_,
      Cataclysm_Skull_Model_Base p_173670_,
      RenderType p_173671_
   ) {
      p_173667_.m_85836_();
      if (p_173664_ == null) {
         p_173667_.m_85837_(0.5, 0.0, 0.5);
      } else {
         float f = 0.25F;
         p_173667_.m_85837_((double)(0.5F - (float)p_173664_.m_122429_() * 0.25F), 0.25, (double)(0.5F - (float)p_173664_.m_122431_() * 0.25F));
      }

      p_173667_.m_85841_(-1.0F, -1.0F, 1.0F);
      VertexConsumer vertexconsumer = p_173668_.m_6299_(p_173671_);
      p_173670_.setupAnim(p_173666_, p_173665_, 0.0F);
      p_173670_.m_7695_(p_173667_, vertexconsumer, p_173669_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_173667_.m_85849_();
   }
}
