package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.SwampmanEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwampmanModel extends AnimatedGeoModel<SwampmanEntity> {
   public ResourceLocation getModelResource(SwampmanEntity animatable) {
      return new ResourceLocation("knightquest", "geo/swampman.geo.json");
   }

   public ResourceLocation getTextureResource(SwampmanEntity animatable) {
      return animatable.getPhase() == 2
         ? new ResourceLocation("knightquest", "textures/entity/swampman_2.png")
         : new ResourceLocation("knightquest", "textures/entity/swampman.png");
   }

   public ResourceLocation getAnimationResource(SwampmanEntity animatable) {
      return new ResourceLocation("knightquest", "animations/swampman.animation.json");
   }
}
