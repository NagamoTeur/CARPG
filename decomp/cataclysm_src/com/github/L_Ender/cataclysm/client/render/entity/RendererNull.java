package com.github.L_Ender.cataclysm.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RendererNull extends EntityRenderer<Entity> {
   public RendererNull(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public ResourceLocation m_5478_(Entity entity) {
      return null;
   }

   public void m_7392_(Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
   }
}
