package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.LivingCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GeckoSunblockLayer<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {
   private static final ResourceLocation SUNBLOCK_ARMOR = new ResourceLocation("mowziesmobs", "textures/entity/sunblock_glow.png");

   public GeckoSunblockLayer(IGeoRenderer<T> entityRendererIn, Context context) {
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
         RenderType renderType = RenderType.m_110436_(this.getTextureLocation(), this.xOffset(f), f * 0.01F);
         this.getRenderer()
            .render(
               this.getEntityModel().getModel(this.getEntityModel().getModelResource(entitylivingbaseIn)),
               entitylivingbaseIn,
               partialTicks,
               renderType,
               matrixStackIn,
               bufferIn,
               bufferIn.m_6299_(renderType),
               packedLightIn,
               OverlayTexture.f_118083_,
               1.0F,
               1.0F,
               0.1F,
               1.0F
            );
      }
   }

   protected float xOffset(float p_225634_1_) {
      return p_225634_1_ * 0.02F;
   }

   protected ResourceLocation getTextureLocation() {
      return SUNBLOCK_ARMOR;
   }
}
