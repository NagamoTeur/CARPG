package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Aptrgangr_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.entity.Aptrgangr_Renderer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
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
public class Aptrgangr_Layer extends RenderLayer<Aptrgangr_Entity, Aptrgangr_Model> {
   private static final ResourceLocation LAYER = new ResourceLocation("cataclysm", "textures/entity/draugar/aptrgangr_layer.png");

   public Aptrgangr_Layer(Aptrgangr_Renderer renderIn) {
      super(renderIn);
   }

   public ResourceLocation getLayerTextureLocation() {
      return LAYER;
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Aptrgangr_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float f = 1.0F - (float)entity.f_20919_ / (float)entity.deathtimer();
      RenderType eyes = CMRenderTypes.CMEyes(this.getLayerTextureLocation());
      VertexConsumer VertexConsumer = bufferIn.m_6299_(eyes);
      ((Aptrgangr_Model)this.m_117386_()).m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, f, f, f, 1.0F);
   }
}
