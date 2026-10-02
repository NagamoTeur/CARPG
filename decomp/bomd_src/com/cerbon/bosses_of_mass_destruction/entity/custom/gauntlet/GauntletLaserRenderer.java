package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class GauntletLaserRenderer implements IRenderer<GauntletEntity> {
   private final ResourceLocation laserTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/gauntlet_beam.png");
   private final RenderType type = RenderType.m_110458_(this.laserTexture);

   public void render(GauntletEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      if (entity.laserHandler.shouldRenderLaser()) {
         Pair<Vec3, Vec3> beamPos = entity.laserHandler.getLaserRenderPos();
         VanillaCopies.renderBeam(entity, (Vec3)beamPos.getFirst(), (Vec3)beamPos.getSecond(), partialTicks, BMDColors.LASER_RED, poseStack, buffer, this.type);
      }
   }
}
