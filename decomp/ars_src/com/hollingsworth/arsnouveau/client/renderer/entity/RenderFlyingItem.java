package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderFlyingItem extends EntityRenderer<EntityFlyingItem> {
   public RenderFlyingItem(Context renderManager) {
      super(renderManager);
   }

   public void render(EntityFlyingItem entityIn, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
      matrixStack.m_85836_();
      matrixStack.m_85841_(0.35F, 0.35F, 0.35F);
      Minecraft.m_91087_()
         .m_91291_()
         .m_174269_(entityIn.getStack(), TransformType.FIXED, 15728880, OverlayTexture.f_118083_, matrixStack, bufferIn, (int)entityIn.m_20183_().m_121878_());
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(EntityFlyingItem entity) {
      return new ResourceLocation("ars_nouveau", "textures/entity/spell_proj.png");
   }
}
