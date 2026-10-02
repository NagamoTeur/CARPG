package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RenderRitualProjectile extends RenderBlank {
   public RenderRitualProjectile(Context renderManager, ResourceLocation entityTexture) {
      super(renderManager, entityTexture);
   }

   @Override
   public void m_7392_(Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }
}
