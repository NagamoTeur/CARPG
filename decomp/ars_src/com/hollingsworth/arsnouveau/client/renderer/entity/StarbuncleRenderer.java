package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.api.client.CosmeticRenderUtil;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.client.ShaderRegistry;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
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

public class StarbuncleRenderer extends GeoEntityRenderer<Starbuncle> {
   Starbuncle starbuncle;
   MultiBufferSource buffer;
   ResourceLocation text;

   public StarbuncleRenderer(Context manager) {
      super(manager, new StarbuncleModel());
   }

   protected void applyRotations(Starbuncle entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
      super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
   }

   public void renderEarly(
      Starbuncle animatable,
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
      super.renderEarly(
         (LivingEntity)animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks
      );
   }

   public void render(Starbuncle entity, float entityYaw, float p_225623_3_, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int p_225623_6_) {
      super.render(entity, entityYaw, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
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
         if (this.starbuncle.dynamicBehavior != null) {
            itemstack = this.starbuncle.dynamicBehavior.getStackForRender();
         }

         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(
               itemstack, TransformType.GROUND, packedLightIn, OverlayTexture.f_118083_, stack, this.buffer, (int)this.starbuncle.m_20097_().m_121878_()
            );
         stack.m_85849_();
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.text));
      }

      if (this.starbuncle.getCosmeticItem().m_41720_() instanceof ICosmeticItem cosmetic && cosmetic.getBone().equals(bone.getName())) {
         CosmeticRenderUtil.renderCosmetic(bone, stack, this.buffer, this.starbuncle, packedLightIn);
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.text));
      }

      super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public ResourceLocation getTextureLocation(Starbuncle entity) {
      return entity.getTexture(entity);
   }

   public RenderType getRenderType(
      Starbuncle animatable,
      float partialTicks,
      PoseStack stack,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      if (animatable.m_7755_().getString().equals("Splonk")) {
         return ShaderRegistry.blamed(textureLocation, true);
      } else {
         return animatable.m_7755_().getString().equals("Bailey")
            ? ShaderRegistry.rainbowEntity(textureLocation, new ResourceLocation("ars_nouveau", "textures/entity/starbuncle_mask.png"), true)
            : RenderType.m_110458_(textureLocation);
      }
   }
}
