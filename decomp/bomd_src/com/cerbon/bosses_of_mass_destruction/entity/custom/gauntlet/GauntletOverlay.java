package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.IOverlayOverride;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class GauntletOverlay implements IRenderer<GauntletEntity>, IOverlayOverride {
   private GauntletEntity entity;
   private float partialTicks;

   @Override
   public int getOverlay() {
      if (this.entity == null) {
         return OverlayTexture.m_118093_(OverlayTexture.m_118088_(0.0F), OverlayTexture.m_118096_(false));
      } else {
         float partialTicks = this.partialTicks != 0.0F ? this.partialTicks : 0.0F;
         float deathProgress = this.entity.f_20919_ == 0 ? 0.0F : ((float)this.entity.f_20919_ + partialTicks) / 50.0F;
         float flash = (float)Math.sin((double)deathProgress * 50.0) * 0.1F + deathProgress * 0.9F;
         return OverlayTexture.m_118093_(OverlayTexture.m_118088_(flash), OverlayTexture.m_118096_(this.entity.f_20916_ > 0));
      }
   }

   public void render(GauntletEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
      this.partialTicks = partialTicks;
   }
}
