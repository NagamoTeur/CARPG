package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;

public class BillboardRenderer<T extends Entity> implements IRenderer<T> {
   private final EntityRenderDispatcher dispatcher;
   private final RenderType renderType;
   private final BillboardRenderer.ScaleFunction<T> scale;

   public BillboardRenderer(EntityRenderDispatcher dispatcher, RenderType renderLayer, BillboardRenderer.ScaleFunction<T> scale) {
      this.dispatcher = dispatcher;
      this.renderType = renderLayer;
      this.scale = scale;
   }

   @Override
   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float scaleValue = this.scale.apply(entity);
      poseStack.m_85836_();
      poseStack.m_85841_(scaleValue, scaleValue, scaleValue);
      VanillaCopies.renderBillboard(poseStack, buffer, light, this.dispatcher, this.renderType, Quaternion.f_80118_);
      poseStack.m_85849_();
   }

   public interface ScaleFunction<T> {
      float apply(T var1);
   }
}
