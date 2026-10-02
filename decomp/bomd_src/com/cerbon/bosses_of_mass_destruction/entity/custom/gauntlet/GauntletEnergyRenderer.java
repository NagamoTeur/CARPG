package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GauntletEnergyRenderer implements IRendererWithModel, IRenderer<GauntletEntity> {
   private final AnimatedGeoModel<GauntletEntity> geoModel;
   private final Context context;
   private final ResourceLocation armorTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/obsidilith_armor.png");
   private GauntletEnergyRenderer.RenderHelper geoModelProvider;
   private GauntletEntity gauntletEntity;
   private RenderType type;

   public GauntletEnergyRenderer(AnimatedGeoModel<GauntletEntity> geoModel, Context context) {
      this.geoModel = geoModel;
      this.context = context;
   }

   public void render(GauntletEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float renderAge = (float)entity.f_19797_ + partialTicks;
      float textureOffset = renderAge * 0.01F;
      if (this.geoModelProvider == null) {
         this.geoModelProvider = new GauntletEnergyRenderer.RenderHelper(this.geoModel, this.context);
      }

      this.gauntletEntity = entity;
      this.type = RenderType.m_110436_(this.armorTexture, textureOffset, textureOffset);
   }

   @Override
   public void render(
      GeoModel model,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      VertexConsumer energyBuffer = buffer.m_6299_(this.type);
      if (this.gauntletEntity != null) {
         if (this.type != null) {
            float renderAlpha = this.gauntletEntity.energyShieldHandler.getRenderAlpha();
            if (renderAlpha != 0.0F) {
               float lerpedAlpha = Mth.m_14179_(partialTicks, renderAlpha - 0.1F, renderAlpha);
               if (this.geoModelProvider != null) {
                  this.geoModelProvider
                     .render(
                        model,
                        this.gauntletEntity,
                        partialTicks,
                        this.type,
                        poseStack,
                        buffer,
                        energyBuffer,
                        packedLightIn,
                        OverlayTexture.f_118083_,
                        0.8F * lerpedAlpha,
                        0.2F * lerpedAlpha,
                        0.2F * lerpedAlpha,
                        lerpedAlpha
                     );
               }
            }
         }
      }
   }

   private static class RenderHelper extends GeoEntityRenderer<GauntletEntity> {
      public RenderHelper(AnimatedGeoModel<GauntletEntity> parentModel, Context context) {
         super(context, parentModel);
      }

      public void renderCube(
         GeoCube cube, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
      ) {
         poseStack.m_85836_();
         poseStack.m_85841_(1.1F, 1.05F, 1.1F);
         super.renderCube(cube, poseStack, buffer, 15728880, packedOverlay, red, green, blue, alpha);
         poseStack.m_85849_();
      }
   }
}
