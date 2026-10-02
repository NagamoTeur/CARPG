package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.The_Harbinger_Model;
import com.github.L_Ender.cataclysm.client.render.entity.The_Harbinger_Renderer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class The_Harbinger_Shield_Layer extends RenderLayer<The_Harbinger_Entity, The_Harbinger_Model> {
   private static final ResourceLocation HARBINGER_LAYER_TEXTURES = new ResourceLocation(
      "cataclysm", "textures/entity/harbinger/the_harbinger_shield_layer.png"
   );

   public The_Harbinger_Shield_Layer(The_Harbinger_Renderer rendererTheHarbinger) {
      super(rendererTheHarbinger);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      The_Harbinger_Entity harbinger,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float f = (float)harbinger.f_19797_ + partialTicks;
      if (harbinger.m_7090_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.02F, 1.02F, 1.02F);
         EntityModel<The_Harbinger_Entity> entitymodel = this.m_117386_();
         entitymodel.m_6839_(harbinger, limbSwing, limbSwingAmount, partialTicks);
         ((The_Harbinger_Model)this.m_117386_()).m_102624_(entitymodel);
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110436_(this.getTextureLocation(), this.xOffset(f), f * 0.01F));
         entitymodel.m_6973_(harbinger, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         entitymodel.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 0.5F, 0.5F, 0.5F, 1.0F);
         matrixStackIn.m_85849_();
      }
   }

   protected float xOffset(float p_117702_) {
      return Mth.m_14089_(p_117702_ * 0.02F) * 2.0F;
   }

   protected ResourceLocation getTextureLocation() {
      return HARBINGER_LAYER_TEXTURES;
   }
}
