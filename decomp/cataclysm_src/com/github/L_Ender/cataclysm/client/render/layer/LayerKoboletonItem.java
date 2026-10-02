package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Koboleton_Model;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
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

public class LayerKoboletonItem extends RenderLayer<Koboleton_Entity, Koboleton_Model> {
   private final ItemInHandRenderer itemInHandRenderer;

   public LayerKoboletonItem(RenderLayerParent p_234846_, ItemInHandRenderer p_234847_) {
      super(p_234846_);
      this.itemInHandRenderer = p_234847_;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Koboleton_Entity entitylivingbaseIn,
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
      matrixStackIn.m_85837_(0.0, -0.1F, -0.1F);
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-190.0F));
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
      ItemInHandRenderer renderer = Minecraft.m_91087_().m_91290_().m_234586_();
      renderer.m_109322_(entitylivingbaseIn, itemstack, TransformType.THIRD_PERSON_RIGHT_HAND, false, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }

   protected void translateToHand(PoseStack matrixStack, boolean left) {
      ((Koboleton_Model)this.m_117386_()).root.translateAndRotate(matrixStack);
      ((Koboleton_Model)this.m_117386_()).pelvis.translateAndRotate(matrixStack);
      ((Koboleton_Model)this.m_117386_()).lower_body.translateAndRotate(matrixStack);
      ((Koboleton_Model)this.m_117386_()).body.translateAndRotate(matrixStack);
      if (left) {
         ((Koboleton_Model)this.m_117386_()).left_arm.translateAndRotate(matrixStack);
         ((Koboleton_Model)this.m_117386_()).left_weapon.translateAndRotate(matrixStack);
      } else {
         ((Koboleton_Model)this.m_117386_()).right_arm.translateAndRotate(matrixStack);
         ((Koboleton_Model)this.m_117386_()).right_weapon.translateAndRotate(matrixStack);
      }
   }
}
