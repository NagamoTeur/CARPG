package net.xylonity.knightquest.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.client.entity.model.NethermanProjectileChargeEntityModel;
import net.xylonity.knightquest.common.entity.boss.NethermanProjectileChargeEntity;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class NethermanProjectileChargeRenderer extends GeoProjectilesRenderer<NethermanProjectileChargeEntity> {
   public NethermanProjectileChargeRenderer(Context renderManager) {
      super(renderManager, new NethermanProjectileChargeEntityModel());
   }

   public ResourceLocation getTextureLocation(NethermanProjectileChargeEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/netherman_projectile_charge.png");
   }

   public void render(
      NethermanProjectileChargeEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      poseStack.m_85841_(2.0F, 2.0F, 2.0F);
      super.m_7392_(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
   }
}
