package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.client.renderer.tile.EnchantedFallingBlockRenderer;
import com.hollingsworth.arsnouveau.common.entity.EnchantedSkull;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.item.ItemStack;

public class EnchantedSkullRenderer extends EnchantedFallingBlockRenderer<EnchantedSkull> {
   public EnchantedSkullRenderer(Context p_174112_) {
      super(p_174112_);
   }

   public void render(EnchantedSkull pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      pMatrixStack.m_85836_();
      renderSkull(pEntity.getStack(), pMatrixStack, pBuffer, pPackedLight);
      pMatrixStack.m_85849_();
   }

   public static void renderSkull(ItemStack stack, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
      Minecraft.m_91087_().m_91291_().m_174269_(stack, TransformType.HEAD, pPackedLight, pPackedLight, pMatrixStack, pBuffer, 0);
   }
}
