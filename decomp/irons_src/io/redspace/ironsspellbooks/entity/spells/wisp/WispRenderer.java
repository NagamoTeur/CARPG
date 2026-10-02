package io.redspace.ironsspellbooks.entity.spells.wisp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WispRenderer extends GeoEntityRenderer<WispEntity> {
   public static final ResourceLocation textureLocation = new ResourceLocation("irons_spellbooks", "textures/entity/wisp/wisp.png");

   public WispRenderer(Context renderManager) {
      super(renderManager, new WispModel());
      this.f_114477_ = 0.3F;
   }

   public ResourceLocation getTextureLocation(WispEntity animatable) {
      return textureLocation;
   }

   public RenderType getRenderType(
      WispEntity animatable,
      float partialTick,
      PoseStack poseStack,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      int packedLight,
      ResourceLocation texture
   ) {
      return RenderType.m_110436_(texture, 0.0F, 0.0F);
   }

   public void render(
      GeoModel model,
      WispEntity animatable,
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
   }
}
