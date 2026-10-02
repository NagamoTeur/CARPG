package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelSculptor;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.GeckoSunblockLayer;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class RenderSculptor extends MowzieGeoEntityRenderer<EntitySculptor> {
   public final ResourceLocation staff_geo_location = new ResourceLocation("mowziesmobs", "geo/sculptor_staff.geo.json");
   public final ResourceLocation staff_tex_location = new ResourceLocation("mowziesmobs", "textures/item/sculptor_staff.png");
   public int staffController = 0;
   public EntitySculptor animatable;

   public RenderSculptor(Context renderManager) {
      super(renderManager, new ModelSculptor());
      this.addLayer(new FrozenRenderHandler.GeckoLayerFrozen(this, renderManager));
      this.addLayer(new GeckoSunblockLayer(this, renderManager));
      this.f_114477_ = 0.7F;
   }

   public ResourceLocation getTextureLocation(EntitySculptor entity) {
      return this.getGeoModelProvider().getTextureResource(entity);
   }

   public void render(
      GeoModel model,
      EntitySculptor animatable,
      float partialTick,
      RenderType type,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.staffController = (int)((GeoBone)model.getBone("staffController").get()).getPositionX();
   }

   protected void renderLayer(
      PoseStack poseStack,
      MultiBufferSource bufferSource,
      int packedLight,
      EntitySculptor animatable,
      float limbSwing,
      float limbSwingAmount,
      float partialTick,
      float rotFloat,
      float netHeadYaw,
      float headPitch,
      MultiBufferSource bufferSource2,
      GeoLayerRenderer<EntitySculptor> layerRenderer
   ) {
      super.renderLayer(
         poseStack,
         bufferSource,
         packedLight,
         animatable,
         limbSwing,
         limbSwingAmount,
         partialTick,
         rotFloat,
         netHeadYaw,
         headPitch,
         bufferSource2,
         layerRenderer
      );
   }

   public void renderEarly(
      EntitySculptor animatable,
      PoseStack stackIn,
      float ticks,
      MultiBufferSource renderTypeBuffer,
      VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      super.renderEarly(
         (LivingEntity)animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks
      );
      this.animatable = animatable;
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
