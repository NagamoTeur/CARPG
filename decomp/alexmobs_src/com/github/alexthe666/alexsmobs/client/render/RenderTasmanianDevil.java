package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelTasmanianDevil;
import com.github.alexthe666.alexsmobs.entity.EntityTasmanianDevil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class RenderTasmanianDevil extends MobRenderer<EntityTasmanianDevil, ModelTasmanianDevil> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("alexsmobs:textures/entity/tasmanian_devil.png");
   private static final ResourceLocation TEXTURE_EYES = new ResourceLocation("alexsmobs:textures/entity/tasmanian_devil_eyes.png");

   public RenderTasmanianDevil(Context renderManagerIn) {
      super(renderManagerIn, new ModelTasmanianDevil(), 0.3F);
      this.m_115326_(new RenderTasmanianDevil.EyeLayer(this));
   }

   protected void scale(EntityTasmanianDevil entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
   }

   public ResourceLocation getTextureLocation(EntityTasmanianDevil entity) {
      return TEXTURE;
   }

   class EyeLayer extends RenderLayer<EntityTasmanianDevil, ModelTasmanianDevil> {
      public EyeLayer(RenderTasmanianDevil render) {
         super(render);
      }

      public void render(
         PoseStack matrixStackIn,
         MultiBufferSource bufferIn,
         int packedLightIn,
         EntityTasmanianDevil entitylivingbaseIn,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (entitylivingbaseIn.getAnimation() == EntityTasmanianDevil.ANIMATION_HOWL && entitylivingbaseIn.getAnimationTick() < 34) {
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110488_(RenderTasmanianDevil.TEXTURE_EYES));
            ((ModelTasmanianDevil)this.m_117386_())
               .m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }
}
