package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RenderGeomancyBase<T extends EntityGeomancyBase & IAnimatable> extends EntityRenderer<T> implements IGeoRenderer<T> {
   private static final ResourceLocation TEXTURE_STONE = new ResourceLocation("textures/blocks/stone.png");
   private MultiBufferSource rtb;
   private T entity;
   protected final MowzieAnimatedGeoModel<T> modelProvider;

   protected RenderGeomancyBase(Context context, MowzieAnimatedGeoModel<T> modelProvider) {
      super(context);
      this.modelProvider = modelProvider;
   }

   public GeoModelProvider getGeoModelProvider() {
      return this.modelProvider;
   }

   public ResourceLocation getTextureLocation(EntityGeomancyBase entity) {
      return TEXTURE_STONE;
   }

   public void renderEarly(
      T animatable,
      PoseStack stackIn,
      float partialTicks,
      @Nullable MultiBufferSource renderTypeBuffer,
      @Nullable VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      this.rtb = renderTypeBuffer;
      this.entity = animatable;
      super.renderEarly(animatable, stackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
      stack.m_85836_();
      GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(entity));
      this.modelProvider.setLivingAnimations(entity, this.getInstanceId(entity));
      RenderType renderType = this.getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn, this.getTextureLocation(entity));
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(renderType);
      this.render(model, entity, partialTicks, renderType, stack, bufferIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      stack.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
   }

   public void setCurrentRTB(MultiBufferSource rtb) {
      this.rtb = rtb;
   }

   public MultiBufferSource getCurrentRTB() {
      return this.rtb;
   }

   public T getEntity() {
      return this.entity;
   }
}
