package com.aizistral.enigmaticlegacy.client.renderers;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nonnull;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnigmaticElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("enigmaticlegacy", "textures/models/misc/elytra.png");
   private final ElytraModel<T> elytraModel;

   public EnigmaticElytraLayer(RenderLayerParent<T, M> layerParent, EntityModelSet modelSet) {
      super(layerParent);
      this.elytraModel = new ElytraModel(modelSet.m_171103_(ModelLayers.f_171141_));
   }

   public void render(
      @Nonnull PoseStack pMatrixStack,
      @Nonnull MultiBufferSource pBuffer,
      int pPackedLight,
      T pLivingEntity,
      float pLimbSwing,
      float pLimbSwingAmount,
      float pPartialTicks,
      float pAgeInTicks,
      float pNetHeadYaw,
      float pHeadPitch
   ) {
      if (SuperpositionHandler.hasEnigmaticElytra(pLivingEntity)) {
         ItemStack stack = SuperpositionHandler.getEnigmaticElytra(pLivingEntity);
         pMatrixStack.m_85836_();
         pMatrixStack.m_85837_(0.0, 0.0, 0.125);
         this.m_117386_().m_102624_(this.elytraModel);
         this.elytraModel.m_6973_(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
         VertexConsumer vertexconsumer = ItemRenderer.m_115184_(pBuffer, RenderType.m_110431_(TEXTURE), false, stack.m_41793_());
         float red = 1.0F;
         float green = 1.0F;
         float blue = 1.0F;
         float alpha = 1.0F;
         this.elytraModel.m_7695_(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
         pMatrixStack.m_85849_();
      }
   }
}
