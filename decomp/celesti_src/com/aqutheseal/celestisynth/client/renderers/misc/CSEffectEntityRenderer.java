package com.aqutheseal.celestisynth.client.renderers.misc;

import com.aqutheseal.celestisynth.client.models.misc.CSEffectEntityModel;
import com.aqutheseal.celestisynth.client.renderers.entity.projectile.SilencedRotationProjectileRenderer;
import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.manager.CSConfigManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.util.Color;

public class CSEffectEntityRenderer extends SilencedRotationProjectileRenderer<CSEffectEntity> {
   public CSEffectEntityRenderer(Context renderManager) {
      super(renderManager, new CSEffectEntityModel());
   }

   public void renderEarly(
      CSEffectEntity animatable,
      PoseStack poseStack,
      float partialTick,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      int packedLight,
      int packedOverlay,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      float lerpBodyRot = Mth.m_14189_(partialTick, animatable.f_19859_, animatable.m_146908_()) - 165.0F;
      poseStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - lerpBodyRot));
      if (animatable.getVisualType().isRotateRandomly() && !animatable.getVisualType().hasSpecialProperties()) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_((float)animatable.getRotationX()));
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_((float)animatable.getRotationZ()));
      }

      CSVisualType.setSpecialProperties(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public float getHeightScale(CSEffectEntity entity) {
      float effectScale = (float)((CSEffectEntity)this.animatable).getVisualType().getScale();
      return super.getHeightScale(entity) * ((CSEffectEntity)this.animatable).getCustomizableSize() * effectScale;
   }

   public float getWidthScale(CSEffectEntity animatable) {
      float effectScale = (float)animatable.getVisualType().getScale();
      return super.getWidthScale(animatable) * animatable.getCustomizableSize() * effectScale;
   }

   public RenderType getRenderType(
      CSEffectEntity animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110473_(texture);
   }

   protected int getBlockLightLevel(CSEffectEntity p_114496_, BlockPos p_114497_) {
      return 15;
   }

   protected int getSkyLightLevel(CSEffectEntity p_114509_, BlockPos p_114510_) {
      return 7;
   }

   public Color getRenderColor(
      CSEffectEntity animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight
   ) {
      Minecraft mc = Minecraft.m_91087_();
      float decreasingAlpha = 1.0F;
      boolean shouldHideAtFirstPerson = false;
      if (animatable.getVisualType().isFadeOut()) {
         int lifespan = animatable.getVisualType().getAnimation().getLifespan();
         decreasingAlpha = 1.0F - (float)animatable.f_19797_ / (float)lifespan;
      }

      if (!(Boolean)CSConfigManager.CLIENT.visibilityOnFirstPerson.get()
         && mc.f_91073_ != null
         && animatable.getOwnerUuid() != null
         && mc.f_91074_ != null
         && mc.f_91066_.m_92176_().m_90612_()
         && animatable.getOwnerUuid() == mc.f_91074_.m_20148_()) {
         shouldHideAtFirstPerson = true;
      }

      return Color.ofRGBA(1.0F, 1.0F, 1.0F, shouldHideAtFirstPerson ? 0.0F : decreasingAlpha);
   }
}
