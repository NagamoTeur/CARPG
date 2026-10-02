package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderFallingBlock extends EntityRenderer<EntityFallingBlock> {
   public RenderFallingBlock(Context mgr) {
      super(mgr);
   }

   public void render(EntityFallingBlock entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, 0.5, 0.0);
      if (entityIn.getMode() == EntityFallingBlock.EnumFallingBlockMode.MOBILE) {
         matrixStackIn.m_85845_(new Quaternion(0.0F, Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()), 0.0F, true));
         matrixStackIn.m_85845_(new Quaternion(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_()), 0.0F, 0.0F, true));
      } else {
         matrixStackIn.m_85837_(0.0, (double)Mth.m_14179_(partialTicks, entityIn.prevAnimY, entityIn.animY), 0.0);
         matrixStackIn.m_85837_(0.0, -1.0, 0.0);
      }

      matrixStackIn.m_85837_(-0.5, -0.5, -0.5);
      dispatcher.m_110912_(entityIn.getBlock(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_);
      matrixStackIn.m_85849_();
   }

   public ResourceLocation getTextureLocation(EntityFallingBlock entity) {
      return null;
   }
}
