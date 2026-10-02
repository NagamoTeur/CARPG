package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.items.Wand;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;

public class WandRenderer extends FixedGeoItemRenderer<Wand> {
   public WandRenderer() {
      super(new WandModel());
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("gem")) {
         super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      } else {
         super.renderRecursively(
            bone,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            (float)Color.WHITE.getRed() / 255.0F,
            (float)Color.WHITE.getGreen() / 255.0F,
            (float)Color.WHITE.getBlue() / 255.0F,
            (float)Color.WHITE.getAlpha() / 255.0F
         );
      }
   }

   @Override
   public Color getRenderColor(
      Object animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight
   ) {
      ParticleColor color = ParticleColor.defaultParticleColor();
      if (this.currentItemStack.m_41782_()) {
         color = ((Wand)animatable).getSpellCaster(this.currentItemStack).getColor();
      }

      return Color.ofRGBA(color.toWrapper().r, color.toWrapper().g, color.toWrapper().b, 200);
   }

   @Override
   public RenderType getRenderType(
      Object animatable,
      float partialTicks,
      PoseStack stack,
      @javax.annotation.Nullable MultiBufferSource renderTypeBuffer,
      @javax.annotation.Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110473_(textureLocation);
   }
}
