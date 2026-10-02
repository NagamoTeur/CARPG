package com.cerbon.bosses_of_mass_destruction.client.render;

import com.cerbon.bosses_of_mass_destruction.projectile.PetalBladeProjectile;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

public class PetalBladeRenderer implements IRenderer<PetalBladeProjectile> {
   private final EntityRenderDispatcher dispatcher;
   private final RenderType renderType;

   public PetalBladeRenderer(EntityRenderDispatcher dispatcher, RenderType renderType) {
      this.dispatcher = dispatcher;
      this.renderType = renderType;
   }

   public void render(PetalBladeProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      float scale = 0.5F;
      poseStack.m_85836_();
      poseStack.m_85841_(scale, scale, scale);
      VanillaCopies.renderBillboard(
         poseStack,
         buffer,
         light,
         this.dispatcher,
         this.renderType,
         Vector3f.f_122227_.m_122240_(-(Float)entity.m_20088_().m_135370_(PetalBladeProjectile.renderRotation))
      );
      poseStack.m_85849_();
   }
}
