package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.GremlinEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GremlinModel extends AnimatedGeoModel<GremlinEntity> {
   public ResourceLocation getModelResource(GremlinEntity animatable) {
      return new ResourceLocation("knightquest", "geo/gremlin.geo.json");
   }

   public ResourceLocation getTextureResource(GremlinEntity animatable) {
      return animatable.getPhase() == 2
         ? new ResourceLocation("knightquest", "textures/entity/gremlin_angry.png")
         : new ResourceLocation("knightquest", "textures/entity/gremlin.png");
   }

   public ResourceLocation getAnimationResource(GremlinEntity animatable) {
      return new ResourceLocation("knightquest", "animations/gremlin.animation.json");
   }
}
