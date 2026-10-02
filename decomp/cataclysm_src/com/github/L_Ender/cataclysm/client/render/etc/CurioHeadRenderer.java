package com.github.L_Ender.cataclysm.client.render.etc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioHeadRenderer implements ICurioRenderer {
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
      if (renderLayerParent.m_7200_() instanceof HeadedModel headModel) {
         matrixStack.m_85836_();
         headModel.m_5585_().m_104299_(matrixStack);
         matrixStack.m_85837_(0.0, -0.25, 0.0);
         matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         matrixStack.m_85841_(0.625F, -0.625F, -0.625F);
         ItemInHandRenderer renderer = new ItemInHandRenderer(Minecraft.m_91087_(), Minecraft.m_91087_().m_91290_(), Minecraft.m_91087_().m_91291_());
         renderer.m_109322_(slotContext.entity(), stack, TransformType.HEAD, false, matrixStack, renderTypeBuffer, light);
         matrixStack.m_85849_();
      }
   }
}
