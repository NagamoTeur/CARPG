package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.RatmanEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RatmanModel extends AnimatedGeoModel<RatmanEntity> {
   public ResourceLocation getModelResource(RatmanEntity animatable) {
      return new ResourceLocation("knightquest", "geo/ratman.geo.json");
   }

   public ResourceLocation getTextureResource(RatmanEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/ratman" + animatable.getVariation() + ".png");
   }

   public ResourceLocation getAnimationResource(RatmanEntity animatable) {
      return new ResourceLocation("knightquest", "animations/ratman.animation.json");
   }
}
