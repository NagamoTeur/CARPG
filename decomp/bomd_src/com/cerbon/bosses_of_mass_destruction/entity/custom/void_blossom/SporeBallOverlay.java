package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class SporeBallOverlay implements IRenderer<SporeBallProjectile>, IOverlayOverride {
   private SporeBallProjectile entity;
   private float partialTicks;

   public void render(SporeBallProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
      this.partialTicks = partialTicks;
   }

   @Override
   public int getOverlay() {
      if (this.entity == null) {
         return OverlayTexture.m_118093_(OverlayTexture.m_118088_(0.0F), OverlayTexture.m_118096_(false));
      } else {
         float partialTicks = this.partialTicks != 0.0F ? this.partialTicks : 0.0F;
         float deathProgress = this.entity.impacted && !this.entity.m_213877_() ? (this.entity.impactedTicks + partialTicks) / 30.0F : 0.0F;
         float scaledDeathProgress = (float)Math.pow((double)deathProgress, 2.0);
         return OverlayTexture.m_118093_(OverlayTexture.m_118088_(scaledDeathProgress), OverlayTexture.m_118096_(false));
      }
   }
}
