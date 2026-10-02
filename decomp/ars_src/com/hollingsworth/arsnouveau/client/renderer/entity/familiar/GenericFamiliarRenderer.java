package com.hollingsworth.arsnouveau.client.renderer.entity.familiar;

import com.hollingsworth.arsnouveau.api.client.CosmeticRenderUtil;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.client.renderer.entity.GenericRenderer;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.geo.render.built.GeoBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class GenericFamiliarRenderer<T extends FamiliarEntity> extends GenericRenderer<T> {
   private T familiar;
   private MultiBufferSource buffer;

   public GenericFamiliarRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      super(renderManager, modelProvider);
   }

   public void renderEarly(
      T familiar,
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
      this.familiar = familiar;
      this.buffer = renderTypeBuffer;
      super.renderEarly(familiar, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
   }

   @Override
   public void renderRecursively(
      GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (this.familiar.getCosmeticItem().m_41720_() instanceof ICosmeticItem cosmetic && cosmetic.getBone().equals(bone.getName())) {
         CosmeticRenderUtil.renderCosmetic(bone, stack, this.buffer, this.familiar, packedLightIn);
         bufferIn = this.buffer.m_6299_(RenderType.m_110458_(this.getTextureLocation(this.familiar)));
      }

      super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public ResourceLocation getTextureLocation(T entity) {
      return entity.getTexture(entity) == null ? super.getTextureLocation(entity) : entity.getTexture(entity);
   }
}
