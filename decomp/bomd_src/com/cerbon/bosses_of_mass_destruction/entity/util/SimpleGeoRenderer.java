package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SimpleGeoRenderer<T extends Entity & IAnimatable> extends EntityRenderer<T> implements IGeoRenderer<T> {
   private final AnimatedGeoModel<T> modelProvider;
   private final IRenderer<T> postRenderers;
   private final IRenderer<T> preRenderers;
   private final IRenderLight<T> brightness;
   private final IOverlayOverride overlayOverride;
   private MultiBufferSource provider;
   private final List<GeoLayerRenderer<T>> layerRenderers = new ArrayList<>();

   public SimpleGeoRenderer(
      Context context,
      AnimatedGeoModel<T> modelProvider,
      IRenderer<T> postRenderers,
      IRenderer<T> preRenderers,
      IRenderLight<T> brightness,
      IOverlayOverride overlayOverride
   ) {
      super(context);
      this.modelProvider = modelProvider;
      this.postRenderers = postRenderers;
      this.preRenderers = preRenderers;
      this.brightness = brightness;
      this.overlayOverride = overlayOverride;
   }

   protected int m_6086_(@NotNull T entity, @NotNull BlockPos pos) {
      return this.brightness != null ? this.brightness.getBlockLight(entity, pos) : super.m_6086_(entity, pos);
   }

   public void m_7392_(T entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null;
      EntityModelData entityModelData = new EntityModelData();
      entityModelData.isSitting = shouldSit;
      float f = Mth.m_14189_(partialTick, entity.f_19859_, entity.m_146908_());
      float f1 = 0.0F;
      float f2 = f1 - f;
      if (shouldSit && entity.m_20202_() instanceof LivingEntity livingEntity) {
         f = Mth.m_14189_(partialTick, livingEntity.f_20884_, livingEntity.f_20883_);
         f2 = f1 - f;
         float f7 = Mth.m_14177_(f2);
         if (f7 < -85.0F) {
            f7 = -85.0F;
         }

         if (f7 >= 85.0F) {
            f7 = 85.0F;
         }

         f = f1 - f7;
         if (f7 * f7 > 2500.0F) {
            f += f7 * 0.2F;
         }

         f2 = f1 - f;
      }

      float f6 = Mth.m_14179_(partialTick, entity.f_19860_, entity.m_146909_());
      float f7x = this.handleRotationFloat(entity, partialTick);
      this.applyRotations(entity, poseStack, f);
      float limbSwingAmount = 0.0F;
      float limbSwing = 0.0F;
      AnimationEvent<?> predicate = new AnimationEvent(entity, limbSwing, limbSwingAmount, partialTick, false, List.of(entityModelData));
      this.modelProvider.setLivingAnimations(entity, this.getInstanceId(entity), predicate);
      poseStack.m_85836_();
      if (this.preRenderers != null) {
         this.preRenderers.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      }

      poseStack.m_85837_(0.0, 0.01F, 0.0);
      Minecraft.m_91087_().f_90987_.m_174784_(this.m_5478_(entity));
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(entity));
      Color renderColor = Color.ofRGBA(255, 255, 255, 255);
      RenderType renderType = this.getRenderType(entity, partialTick, poseStack, buffer, null, packedLight, this.m_5478_(entity));
      this.render(
         model,
         entity,
         partialTick,
         renderType,
         poseStack,
         buffer,
         null,
         packedLight,
         this.overlayOverride != null ? this.overlayOverride.getOverlay() : this.getPackedOverlay(),
         (float)renderColor.getRed() / 255.0F,
         (float)renderColor.getBlue() / 255.0F,
         (float)renderColor.getGreen() / 255.0F,
         (float)renderColor.getAlpha() / 255.0F
      );
      if (!entity.m_5833_()) {
         for (GeoLayerRenderer<T> next : this.layerRenderers) {
            next.render(poseStack, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTick, f7x, f2, f6);
         }
      }

      poseStack.m_85849_();
      super.m_7392_(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      if (this.postRenderers != null) {
         this.postRenderers.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
      }
   }

   private void applyRotations(T entityLiving, PoseStack poseStack, float rotationYaw) {
      Pose pose = entityLiving.m_20089_();
      if (pose != Pose.SLEEPING) {
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(rotationYaw));
      }
   }

   private float handleRotationFloat(T livingBase, float partialTicks) {
      return (float)livingBase.f_19797_ + partialTicks;
   }

   public GeoModelProvider<T> getGeoModelProvider() {
      return this.modelProvider;
   }

   private int getPackedOverlay() {
      return OverlayTexture.m_118090_((float)OverlayTexture.m_118088_(0.0F), false);
   }

   public void setCurrentRTB(MultiBufferSource bufferSource) {
      this.provider = bufferSource;
   }

   public MultiBufferSource getCurrentRTB() {
      return this.provider;
   }

   @NotNull
   public ResourceLocation m_5478_(@NotNull T entity) {
      return this.modelProvider.getTextureResource(entity);
   }
}
