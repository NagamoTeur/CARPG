package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.The_Prowler_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.entity.The_Prowler_Renderer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.The_Prowler_Entity;
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
public class The_Prowler_Layer extends RenderLayer<The_Prowler_Entity, The_Prowler_Model> {
   private static final ResourceLocation PROWLER_LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/factory/the_prowler_layer.png");

   public The_Prowler_Layer(The_Prowler_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      The_Prowler_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float f = 1.0F - (float)entity.f_20919_ / (float)entity.deathtimer();
      RenderType eyes = CMRenderTypes.CMEyes(PROWLER_LAYER_TEXTURES);
      VertexConsumer VertexConsumer = bufferIn.m_6299_(eyes);
      ((The_Prowler_Model)this.m_117386_()).m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, f, f, f, 1.0F);
   }
}
