package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.The_Harbinger_Model;
import com.github.L_Ender.cataclysm.client.render.entity.The_Harbinger_Renderer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
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
public class The_Harbinger_Layer extends RenderLayer<The_Harbinger_Entity, The_Harbinger_Model> {
   private static final ResourceLocation HARBINGER_LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/harbinger/the_harbinger_layer.png");

   public The_Harbinger_Layer(The_Harbinger_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      The_Harbinger_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float f = 1.0F - entity.deactivateProgress / 40.0F;
      RenderType eyes = RenderType.m_110488_(HARBINGER_LAYER_TEXTURES);
      VertexConsumer VertexConsumer = bufferIn.m_6299_(eyes);
      ((The_Harbinger_Model)this.m_117386_()).m_7695_(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.f_118083_, f, f, f, f);
   }
}
