package com.github.alexthe666.alexsmobs.client.render.layer;

import com.github.alexthe666.alexsmobs.client.model.ModelMantisShrimp;
import com.github.alexthe666.alexsmobs.client.render.RenderMantisShrimp;
import com.github.alexthe666.alexsmobs.entity.EntityMantisShrimp;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class LayerMantisShrimpItem extends RenderLayer<EntityMantisShrimp, ModelMantisShrimp> {
   public LayerMantisShrimpItem(RenderMantisShrimp render) {
      super(render);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      EntityMantisShrimp entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemstack = entitylivingbaseIn.m_6844_(EquipmentSlot.MAINHAND);
      matrixStackIn.m_85836_();
      boolean left = entitylivingbaseIn.m_21526_();
      if (entitylivingbaseIn.m_6162_()) {
         matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
         matrixStackIn.m_85837_(0.0, 1.5, 0.0);
      }

      matrixStackIn.m_85836_();
      this.translateToHand(matrixStackIn, left);
      matrixStackIn.m_85837_(left ? 0.075F : -0.075F, 0.45F, -0.125);
      if (!Minecraft.m_91087_().m_91291_().m_115103_().m_109406_(itemstack).m_7539_()) {
         matrixStackIn.m_85837_(0.0, 0.0, 0.05F);
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(left ? -40.0F : 40.0F));
      }

      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(-2.5F));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-180.0F));
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      matrixStackIn.m_85841_(1.2F, 1.2F, 1.2F);
      ItemInHandRenderer renderer = Minecraft.m_91087_().m_91290_().m_234586_();
      renderer.m_109322_(entitylivingbaseIn, itemstack, TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }

   protected void translateToHand(PoseStack matrixStack, boolean left) {
      ((ModelMantisShrimp)this.m_117386_()).root.translateAndRotate(matrixStack);
      ((ModelMantisShrimp)this.m_117386_()).body.translateAndRotate(matrixStack);
      ((ModelMantisShrimp)this.m_117386_()).head.translateAndRotate(matrixStack);
      if (left) {
         ((ModelMantisShrimp)this.m_117386_()).arm_left.translateAndRotate(matrixStack);
         ((ModelMantisShrimp)this.m_117386_()).fist_left.translateAndRotate(matrixStack);
      } else {
         ((ModelMantisShrimp)this.m_117386_()).arm_right.translateAndRotate(matrixStack);
         ((ModelMantisShrimp)this.m_117386_()).fist_right.translateAndRotate(matrixStack);
      }
   }
}
