package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.boss.NethermanProjectileChargeEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NethermanProjectileChargeEntityModel extends AnimatedGeoModel<NethermanProjectileChargeEntity> {
   public ResourceLocation getModelResource(NethermanProjectileChargeEntity animatable) {
      return new ResourceLocation("knightquest", "geo/netherman_projectile_charge.geo.json");
   }

   public ResourceLocation getTextureResource(NethermanProjectileChargeEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/netherman_projectile_charge.png");
   }

   public ResourceLocation getAnimationResource(NethermanProjectileChargeEntity animatable) {
      return new ResourceLocation("knightquest", "animations/netherman_projectile_charge.animation.json");
   }
}
