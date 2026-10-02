package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.client.render.IRendererWithModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector4f;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SimpleLivingGeoRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
   private final IRenderLight<T> brightness;
   private final IBoneLight iBoneLight;
   private final IRenderer<T> renderer;
   private final IRendererWithModel renderWithModel;
   private final IOverlayOverride overlayOverride;
   private final RenderType renderType;
   private final boolean deathRotation;
   private final SimpleLivingGeoRenderer.GeoRenderer<T> renderHelper = new SimpleLivingGeoRenderer.GeoRenderer<>(
      this.modelProvider, x$0 -> this.getTextureLocation(x$0), this
   );

   public SimpleLivingGeoRenderer(
      Context renderManager,
      AnimatedGeoModel<T> model,
      IRenderLight<T> brightness,
      IBoneLight iBoneLight,
      IRenderer<T> renderer,
      IRendererWithModel renderWithModel,
      IOverlayOverride overlayOverride,
      RenderType renderType,
      boolean deathRotation
   ) {
      super(renderManager, model);
      this.brightness = brightness;
      this.iBoneLight = iBoneLight;
      this.renderer = renderer;
      this.renderWithModel = renderWithModel;
      this.overlayOverride = overlayOverride;
      this.deathRotation = deathRotation;
      this.renderType = renderType;
   }

   public RenderType getRenderType(
      T animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return this.renderType != null
         ? this.renderHelper.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture)
         : super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
   }

   protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
      return this.brightness != null ? this.brightness.getBlockLight(entity, pos) : super.m_6086_(entity, pos);
   }

   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      int packedLight1 = this.iBoneLight != null ? this.iBoneLight.getLightForBone(bone, packedLight) : packedLight;
      Vector4f color = new Vector4f(red, green, blue, alpha);
      Vector4f newColor = this.iBoneLight != null ? this.iBoneLight.getColorForBone(bone, color) : color;
      super.renderRecursively(
         bone, poseStack, buffer, packedLight1, packedOverlay, newColor.m_123601_(), newColor.m_123615_(), newColor.m_123616_(), newColor.m_123617_()
      );
   }

   public void render(
      @NotNull T entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight
   ) {
      if (this.renderer != null) {
         this.renderer.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      }

      poseStack.m_85836_();
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.m_85849_();
   }

   public void render(
      GeoModel model,
      T animatable,
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
      int packetOverlay = this.overlayOverride != null ? this.overlayOverride.getOverlay() : packedOverlay;
      this.renderHelper.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packetOverlay, red, green, blue, alpha);
      if (this.renderWithModel != null) {
         this.renderWithModel.render(model, partialTick, poseStack, bufferSource, packedLight, packetOverlay, red, green, blue, alpha);
      }
   }

   public int getPackedOverlay(LivingEntity entity, float u) {
      return this.overlayOverride != null ? this.overlayOverride.getOverlay() : super.getPackedOverlay(entity, u);
   }

   protected float getDeathMaxRotation(T animatable) {
      return this.deathRotation ? 90.0F : 0.0F;
   }

   private static class GeoRenderer<T extends LivingEntity & IAnimatable> implements IGeoRenderer<T> {
      private final AnimatedGeoModel<T> geoModel;
      private final Function<T, ResourceLocation> textureLocation;
      private final SimpleLivingGeoRenderer<T> renderer;
      private MultiBufferSource provider;

      public GeoRenderer(AnimatedGeoModel<T> geoModel, Function<T, ResourceLocation> textureLocation, SimpleLivingGeoRenderer<T> renderer) {
         this.geoModel = geoModel;
         this.textureLocation = textureLocation;
         this.renderer = renderer;
      }

      public GeoModelProvider<T> getGeoModelProvider() {
         return this.geoModel;
      }

      public void renderRecursively(
         GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
      ) {
         this.renderer.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      }

      public void setCurrentRTB(MultiBufferSource bufferSource) {
         this.provider = bufferSource;
      }

      public MultiBufferSource getCurrentRTB() {
         return this.provider;
      }

      public ResourceLocation getTextureLocation(T t) {
         return this.textureLocation.apply(t);
      }
   }
}
