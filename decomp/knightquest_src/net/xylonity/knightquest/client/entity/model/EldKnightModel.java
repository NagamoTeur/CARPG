package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.EldKnightEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EldKnightModel extends AnimatedGeoModel<EldKnightEntity> {
   public ResourceLocation getModelResource(EldKnightEntity animatable) {
      return new ResourceLocation("knightquest", "geo/eldknight.geo.json");
   }

   public ResourceLocation getTextureResource(EldKnightEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/eldknight.png");
   }

   public ResourceLocation getAnimationResource(EldKnightEntity animatable) {
      return new ResourceLocation("knightquest", "animations/eldknight.animation.json");
   }
}
