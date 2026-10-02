package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.LivingCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SunblockLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation SUNBLOCK_ARMOR = new ResourceLocation("mowziesmobs", "textures/entity/sunblock_glow.png");

   public SunblockLayer(RenderLayerParent<T, M> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      LivingCapability.ILivingCapability livingCapability = CapabilityHandler.getCapability(entitylivingbaseIn, CapabilityHandler.LIVING_CAPABILITY);
      if (livingCapability != null && livingCapability.getHasSunblock()) {
         float f = (float)entitylivingbaseIn.f_19797_ + partialTicks;
         EntityModel<T> entitymodel = this.m_117386_();
         entitymodel.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.m_117386_().m_102624_(entitymodel);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110436_(this.getTextureLocation(), this.xOffset(f), f * 0.01F));
         entitymodel.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         entitymodel.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 0.1F, 1.0F);
      }
   }

   protected float xOffset(float p_225634_1_) {
      return p_225634_1_ * 0.02F;
   }

   protected ResourceLocation getTextureLocation() {
      return SUNBLOCK_ARMOR;
   }
}
