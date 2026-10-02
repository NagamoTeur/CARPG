package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Ignited_Berserker_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ignited_Berserker_Renderer extends MobRenderer<Ignited_Berserker_Entity, Ignited_Berserker_Model<Ignited_Berserker_Entity>> {
   private static final ResourceLocation BERSERKER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/ignited_berserker.png");
   private static final ResourceLocation BERSERKER_LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/ignited_berserker_layer.png");

   public Ignited_Berserker_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Ignited_Berserker_Model(renderManagerIn.m_174023_(CMModelLayers.IGNITED_BERSERKER_MODEL)), 0.5F);
      this.m_115326_(new Ignited_Berserker_Renderer.Ignited_Berserker_GlowLayer(this));
   }

   public ResourceLocation getTextureLocation(Ignited_Berserker_Entity entity) {
      return BERSERKER_TEXTURES;
   }

   protected void scale(Ignited_Berserker_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.05F, 1.05F, 1.05F);
   }

   static class Ignited_Berserker_GlowLayer extends RenderLayer<Ignited_Berserker_Entity, Ignited_Berserker_Model<Ignited_Berserker_Entity>> {
      public Ignited_Berserker_GlowLayer(Ignited_Berserker_Renderer p_i50928_1_) {
         super(p_i50928_1_);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         Ignited_Berserker_Entity entitylivingbaseIn,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(CMRenderTypes.getFlickering(Ignited_Berserker_Renderer.BERSERKER_LAYER_TEXTURES, 0.0F));
         float alpha = 0.5F + (Mth.m_14089_(ageInTicks * 0.2F) + 1.0F) * 0.2F;
         ((Ignited_Berserker_Model)this.m_117386_())
            .m_7695_(matrixStackIn, ivertexbuilder, 240, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, alpha);
      }
   }
}
