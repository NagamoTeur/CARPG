package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.items.EnchantersSword;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.util.Color;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class SwordRenderer extends FixedGeoItemRenderer<EnchantersSword> {
   public SwordRenderer() {
      super(new AnimatedGeoModel<EnchantersSword>() {
         public ResourceLocation getModelResource(EnchantersSword wand) {
            return new ResourceLocation("ars_nouveau", "geo/sword.geo.json");
         }

         public ResourceLocation getTextureResource(EnchantersSword wand) {
            return new ResourceLocation("ars_nouveau", "textures/items/enchanters_sword.png");
         }

         public ResourceLocation getAnimationResource(EnchantersSword wand) {
            return new ResourceLocation("ars_nouveau", "animations/sword.json");
         }
      });
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("blade")) {
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
      if (this.currentItemStack.m_41782_() && this.currentItemStack.m_41784_().m_128441_("ars_nouveau:caster")) {
         color = ((EnchantersSword)animatable).getSpellCaster(this.currentItemStack).getColor();
      }

      return Color.ofRGB(color.toWrapper().r, color.toWrapper().g, color.toWrapper().b);
   }

   @Override
   public RenderType getRenderType(
      Object animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110473_(textureLocation);
   }
}
