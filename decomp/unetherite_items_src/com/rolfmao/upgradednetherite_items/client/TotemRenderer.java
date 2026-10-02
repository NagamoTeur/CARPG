package com.rolfmao.upgradednetherite_items.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class TotemRenderer implements ICurioRenderer {
   public <T extends LivingEntity, M extends EntityModel<T>> void render(
      ItemStack stack,
      SlotContext slotContext,
      PoseStack matrixStack,
      RenderLayerParent<T, M> renderLayerParent,
      MultiBufferSource renderTypeBuffer,
      int light,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      LivingEntity livingEntity = slotContext.entity();
      ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
      ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
      matrixStack.m_85841_(0.35F, 0.35F, 0.35F);
      matrixStack.m_85837_(0.0, 0.5, -0.4F);
      matrixStack.m_85845_(Direction.DOWN.m_122406_());
      Minecraft.m_91087_().m_91291_().m_174269_(stack, TransformType.NONE, light, OverlayTexture.f_118083_, matrixStack, renderTypeBuffer, 0);
   }
}
