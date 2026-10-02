package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Deepling_Model;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class LayerDeeplingItem extends RenderLayer<Deepling_Entity, Deepling_Model> {
   private final ItemInHandRenderer itemInHandRenderer;

   public LayerDeeplingItem(RenderLayerParent p_234846_, ItemInHandRenderer p_234847_) {
      super(p_234846_);
      this.itemInHandRenderer = p_234847_;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Deepling_Entity entitylivingbaseIn,
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
      matrixStackIn.m_85836_();
      this.translateToHand(matrixStackIn, left);
      matrixStackIn.m_85837_(0.0, 1.4225F, -0.1F);
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
      ItemInHandRenderer renderer = Minecraft.m_91087_().m_91290_().m_234586_();
      renderer.m_109322_(entitylivingbaseIn, itemstack, TransformType.THIRD_PERSON_RIGHT_HAND, false, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }

   protected void translateToHand(PoseStack matrixStack, boolean left) {
      ((Deepling_Model)this.m_117386_()).root.translateAndRotate(matrixStack);
      ((Deepling_Model)this.m_117386_()).body.translateAndRotate(matrixStack);
      if (left) {
         ((Deepling_Model)this.m_117386_()).left_arm.translateAndRotate(matrixStack);
      } else {
         ((Deepling_Model)this.m_117386_()).right_arm.translateAndRotate(matrixStack);
      }
   }
}
