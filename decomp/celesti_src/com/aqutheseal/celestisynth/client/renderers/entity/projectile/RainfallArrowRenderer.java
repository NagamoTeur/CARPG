package com.aqutheseal.celestisynth.client.renderers.entity.projectile;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.entity.projectile.RainfallArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class RainfallArrowRenderer extends ArrowRenderer<RainfallArrow> {
   public RainfallArrowRenderer(Context pContext) {
      super(pContext);
   }

   public ResourceLocation getTextureLocation(RainfallArrow pEntity) {
      return Celestisynth.prefix("textures/entity/projectile/rainfall_arrow.png");
   }

   public void render(RainfallArrow pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      pMatrixStack.m_85836_();
      pMatrixStack.m_85841_(2.0F, 2.0F, 2.0F);
      pMatrixStack.m_85849_();
      super.m_7392_(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
   }

   protected int getBlockLightLevel(RainfallArrow pEntity, BlockPos pPos) {
      return 15;
   }

   protected int getSkyLightLevel(RainfallArrow pEntity, BlockPos pPos) {
      return 15;
   }
}
