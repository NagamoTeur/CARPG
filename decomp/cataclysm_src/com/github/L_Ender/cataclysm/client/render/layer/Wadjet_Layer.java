package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Wadjet_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Wadjet_Renderer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Wadjet_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Wadjet_Layer extends RenderLayer<Wadjet_Entity, Wadjet_Model> {
   private static final ResourceLocation LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/koboleton/wadjet_layer.png");

   public Wadjet_Layer(Wadjet_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Wadjet_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entity.getAttackState() != 1 && entity.m_6084_()) {
         RenderType eyes = RenderType.m_110488_(LAYER_TEXTURES);
         VertexConsumer VertexConsumer = bufferIn.m_6299_(eyes);
         ((Wadjet_Model)this.m_117386_()).m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
