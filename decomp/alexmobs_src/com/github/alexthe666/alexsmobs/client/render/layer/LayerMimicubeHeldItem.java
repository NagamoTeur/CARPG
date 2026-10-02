package com.github.alexthe666.alexsmobs.client.render.layer;

import com.github.alexthe666.alexsmobs.client.model.ModelMimicube;
import com.github.alexthe666.alexsmobs.client.render.RenderMimicube;
import com.github.alexthe666.alexsmobs.entity.EntityMimicube;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

public class LayerMimicubeHeldItem extends RenderLayer<EntityMimicube, ModelMimicube> {
   public LayerMimicubeHeldItem(RenderMimicube render) {
      super(render);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      EntityMimicube entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ItemStack itemRight = entitylivingbaseIn.m_21205_();
      ItemStack itemLeft = entitylivingbaseIn.m_21206_();
      float rightSwap = Mth.m_14179_(partialTicks, entitylivingbaseIn.prevRightSwapProgress, entitylivingbaseIn.rightSwapProgress) * 0.2F;
      float leftSwap = Mth.m_14179_(partialTicks, entitylivingbaseIn.prevLeftSwapProgress, entitylivingbaseIn.leftSwapProgress) * 0.2F;
      float attackprogress = Mth.m_14179_(partialTicks, entitylivingbaseIn.prevAttackProgress, entitylivingbaseIn.attackProgress);
      double bob1 = Math.cos((double)(ageInTicks * 0.1F)) * 0.1F + 0.1F;
      double bob2 = Math.sin((double)(ageInTicks * 0.1F)) * 0.1F + 0.1F;
      if (!itemRight.m_41619_()) {
         matrixStackIn.m_85836_();
         this.translateToHand(false, matrixStackIn);
         matrixStackIn.m_85837_(-0.5, 0.1F - bob1, -0.1F);
         matrixStackIn.m_85841_(0.9F * (1.0F - rightSwap), 0.9F * (1.0F - rightSwap), 0.9F * (1.0F - rightSwap));
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         if (itemRight.m_41720_() instanceof ShieldItem) {
            matrixStackIn.m_85837_(-0.1F, 0.0, -0.4F);
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
         }

         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(-10.0F));
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(360.0F * rightSwap));
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-40.0F * attackprogress));
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               itemRight,
               TransformType.THIRD_PERSON_RIGHT_HAND,
               rightSwap > 0.0F ? (int)(-100.0F * rightSwap) : packedLightIn,
               LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F),
               matrixStackIn,
               bufferIn,
               0
            );
         matrixStackIn.m_85849_();
      }

      if (!itemLeft.m_41619_()) {
         matrixStackIn.m_85836_();
         this.translateToHand(false, matrixStackIn);
         matrixStackIn.m_85837_(0.45F, 0.1F - bob2, -0.1F);
         matrixStackIn.m_85841_(0.9F * (1.0F - leftSwap), 0.9F * (1.0F - leftSwap), 0.9F * (1.0F - leftSwap));
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
         int clampedLight = (int)Math.floor((double)((float)packedLightIn * (1.0F - leftSwap)));
         if (itemLeft.m_41720_() instanceof ShieldItem) {
            matrixStackIn.m_85837_(-0.2F, 0.0, -0.4F);
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F));
         }

         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(10.0F));
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(360.0F * leftSwap));
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               itemLeft,
               TransformType.THIRD_PERSON_RIGHT_HAND,
               leftSwap > 0.0F ? (int)(-100.0F * leftSwap) : packedLightIn,
               LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F),
               matrixStackIn,
               bufferIn,
               0
            );
         matrixStackIn.m_85849_();
      }
   }

   protected void translateToHand(boolean left, PoseStack matrixStack) {
      ((ModelMimicube)this.m_117386_()).root.translateAndRotate(matrixStack);
      ((ModelMimicube)this.m_117386_()).innerbody.translateAndRotate(matrixStack);
   }
}
