package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RenderBlank extends EntityRenderer {
   private final ResourceLocation entityTexture;

   public RenderBlank(Context renderManager, ResourceLocation entityTexture) {
      super(renderManager);
      this.entityTexture = entityTexture;
   }

   public void m_7392_(Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public ResourceLocation m_5478_(Entity entity) {
      return this.entityTexture;
   }
}
