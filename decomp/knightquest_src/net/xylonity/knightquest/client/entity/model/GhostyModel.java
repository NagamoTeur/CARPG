package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.GhostyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GhostyModel extends AnimatedGeoModel<GhostyEntity> {
   public ResourceLocation getModelResource(GhostyEntity animatable) {
      return new ResourceLocation("knightquest", "geo/ghosty.geo.json");
   }

   public ResourceLocation getTextureResource(GhostyEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/ghosty.png");
   }

   public ResourceLocation getAnimationResource(GhostyEntity animatable) {
      return new ResourceLocation("knightquest", "animations/ghosty.animation.json");
   }
}
