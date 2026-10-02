package com.aqutheseal.celestisynth.common.entity.helper;

import com.aqutheseal.celestisynth.common.entity.base.CSEffectEntity;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class CSVisualType {
   private final String name;
   private final String texture;
   private final CSVisualModel model;
   private final CSVisualAnimation animation;
   @Nullable
   private final SoundEvent effectSound;
   private final int frames;
   private final int framesSpeed;
   private final double scale;
   private final boolean rotateRandomly;
   private final boolean fadeOut;
   private final boolean specialProperties;

   public CSVisualType(
      String name,
      String texture,
      CSVisualModel model,
      CSVisualAnimation animation,
      @Nullable SoundEvent effectSound,
      int frames,
      int framesSpeed,
      double scale,
      boolean rotateRandomly,
      boolean fadeOut,
      boolean specialProperties
   ) {
      this.name = name;
      this.texture = texture;
      this.model = model;
      this.animation = animation;
      this.effectSound = effectSound;
      this.frames = frames;
      this.framesSpeed = framesSpeed;
      this.scale = scale;
      this.rotateRandomly = rotateRandomly;
      this.fadeOut = fadeOut;
      this.specialProperties = specialProperties;
   }

   public CSVisualType(
      String name,
      String texture,
      CSVisualModel model,
      CSVisualAnimation animation,
      int frames,
      int framesSpeed,
      double scale,
      boolean rotateRandomly,
      boolean fadeOut,
      boolean specialProperties
   ) {
      this(name, texture, model, animation, null, frames, framesSpeed, scale, rotateRandomly, fadeOut, specialProperties);
   }

   public CSVisualType(
      String texture,
      CSVisualModel model,
      CSVisualAnimation animation,
      int frames,
      int framesSpeed,
      double scale,
      boolean rotateRandomly,
      boolean fadeOut,
      boolean specialProperties
   ) {
      this(texture, texture, model, animation, null, frames, framesSpeed, scale, rotateRandomly, fadeOut, specialProperties);
   }

   public static void setSpecialProperties(
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
      float lerpBodyRot = Mth.m_14189_(partialTick, animatable.f_19860_, animatable.m_146909_()) - 165.0F;
      List<CSVisualType> rotatableEffectsZ = new ArrayList<>();
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE.get());
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.CRESCENTIA_STRIKE_INVERTED.get());
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.CRESCENTIA_THROW.get());
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.CRESCENTIA_THROW_INVERTED.get());
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.BREEZEBREAKER_SLASH.get());
      rotatableEffectsZ.add((CSVisualType)CSVisualTypes.BREEZEBREAKER_SLASH_INVERTED.get());
      if (rotatableEffectsZ.contains(animatable.getVisualType())) {
         poseStack.m_85845_(Vector3f.f_122227_.m_122240_((float)animatable.getRotationZ() / 360.0F * 45.0F - 22.5F));
      }

      List<CSVisualType> rotatableEffectsX = new ArrayList<>();
      rotatableEffectsX.add((CSVisualType)CSVisualTypes.BREEZEBREAKER_WHEEL_IMPACT.get());
      rotatableEffectsX.add((CSVisualType)CSVisualTypes.AQUAFLORA_PIERCE_START.get());
      rotatableEffectsX.add((CSVisualType)CSVisualTypes.AQUAFLORA_STAB.get());
      if (rotatableEffectsX.contains(animatable.getVisualType())) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F + lerpBodyRot));
      }

      List<CSVisualType> men = new ArrayList<>();
      men.add((CSVisualType)CSVisualTypes.RAINFALL_SHOOT.get());
      if (men.contains(animatable.getVisualType())) {
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F + lerpBodyRot - 15.0F));
      }
   }

   public String getName() {
      return this.name;
   }

   public String getTexture() {
      return this.texture;
   }

   public CSVisualModel getModel() {
      return this.model;
   }

   public CSVisualAnimation getAnimation() {
      return this.animation;
   }

   public SoundEvent getEffectSound() {
      return this.effectSound;
   }

   public int getFrames() {
      return this.frames;
   }

   public int getFramesSpeed() {
      return this.framesSpeed;
   }

   public double getScale() {
      return this.scale;
   }

   public boolean isRotateRandomly() {
      return this.rotateRandomly;
   }

   public boolean isFadeOut() {
      return this.fadeOut;
   }

   public boolean hasSpecialProperties() {
      return this.specialProperties;
   }
}
