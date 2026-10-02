package io.redspace.ironsspellbooks.entity.spells.void_tentacle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidTentacleRenderer extends GeoEntityRenderer<VoidTentacle> {
   public VoidTentacleRenderer(Context context) {
      super(context, new VoidTentacleModel());
      this.addLayer(new VoidTentacleEmissiveLayer(this));
      this.f_114477_ = 1.0F;
   }

   public void render(VoidTentacle animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
