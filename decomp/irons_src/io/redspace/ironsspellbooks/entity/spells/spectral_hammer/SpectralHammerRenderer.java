package io.redspace.ironsspellbooks.entity.spells.spectral_hammer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpectralHammerRenderer extends GeoEntityRenderer<SpectralHammer> {
   public SpectralHammerRenderer(Context renderManager) {
      super(renderManager, new SpectralHammerModel());
      this.f_114477_ = 0.3F;
   }

   public ResourceLocation getTextureLocation(SpectralHammer animatable) {
      return SpectralHammerModel.textureResource;
   }

   public void render(
      GeoModel model,
      SpectralHammer animatable,
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
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
      super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public RenderType getRenderType(
      SpectralHammer animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      Vec2 vec2 = getEnergySwirlOffset(animatable, partialTick);
      return RenderType.m_110436_(texture, vec2.f_82470_, vec2.f_82471_);
   }

   private static float shittyNoise(float f) {
      return (float)(Math.sin((double)(f / 4.0F)) + 2.0 * Math.sin((double)(f / 3.0F)) + 3.0 * Math.sin((double)(f / 2.0F)) + 4.0 * Math.sin((double)f))
         * 0.25F;
   }

   public static Vec2 getEnergySwirlOffset(SpectralHammer entity, float partialTicks, int offset) {
      float f = ((float)entity.f_19797_ + partialTicks) * 0.02F;
      return new Vec2(shittyNoise(1.2F * f + (float)offset), shittyNoise(f + 456.0F + (float)offset));
   }

   public static Vec2 getEnergySwirlOffset(SpectralHammer entity, float partialTicks) {
      return getEnergySwirlOffset(entity, partialTicks, 0);
   }
}
