package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.util.RenderUtils;

public class BookwyrmRenderer extends TextureVariantRenderer<EntityBookwyrm> {
   public static ResourceLocation BLUE = new ResourceLocation("ars_nouveau", "textures/entity/book_wyrm_blue.png");
   EntityBookwyrm starbuncle;
   MultiBufferSource buffer;
   ResourceLocation text;

   public BookwyrmRenderer(Context manager) {
      super(manager, new BookwyrmModel());
   }

   public void renderEarly(
      EntityBookwyrm animatable,
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
      this.starbuncle = animatable;
      this.buffer = renderTypeBuffer;
      this.text = this.getTextureLocation(animatable);
      super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (bone.getName().equals("item")) {
         stack.m_85836_();
         RenderUtils.translateToPivotPoint(stack, bone);
         stack.m_85837_(0.0, -0.1, 0.0);
         stack.m_85841_(0.75F, 0.75F, 0.75F);
         ItemStack itemstack = this.starbuncle.getHeldStack();
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               itemstack, TransformType.GROUND, packedLightIn, OverlayTexture.f_118083_, stack, this.buffer, (int)this.starbuncle.m_20097_().m_121878_()
            );
         stack.m_85849_();
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.text));
      }

      super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public void render(EntityBookwyrm entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      stack.m_85836_();
      stack.m_85841_(0.6F, 0.6F, 0.6F);
      super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
      stack.m_85849_();
   }
}
