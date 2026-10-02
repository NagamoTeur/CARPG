package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.AmethystGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class AmethystGolemRenderer extends GeoEntityRenderer<AmethystGolem> {
   AmethystGolem golem;
   MultiBufferSource buffer;
   ResourceLocation text;

   public AmethystGolemRenderer(Context renderManager) {
      super(renderManager, new AmethystGolemModel());
   }

   public RenderType getRenderType(
      AmethystGolem animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110458_(textureLocation);
   }

   public void renderEarly(
      AmethystGolem animatable,
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
      this.golem = animatable;
      this.buffer = renderTypeBuffer;
      this.text = this.getTextureLocation(animatable);
      super.renderEarly(
         (LivingEntity)animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks
      );
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("item")) {
         stack.m_85836_();
         RenderUtils.translateToPivotPoint(stack, bone);
         stack.m_85837_(0.0, -0.1, 0.0);
         ItemStack itemstack = this.golem.getHeldStack();
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(itemstack, TransformType.GROUND, packedLightIn, OverlayTexture.f_118083_, stack, this.buffer, (int)this.golem.m_20097_().m_121878_());
         stack.m_85849_();
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.text));
      }

      super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }
}
