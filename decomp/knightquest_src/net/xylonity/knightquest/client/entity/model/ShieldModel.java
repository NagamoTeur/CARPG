package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.GhastlingEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShieldModel extends AnimatedGeoModel<GhastlingEntity> {
   public ResourceLocation getModelResource(GhastlingEntity animatable) {
      return new ResourceLocation("knightquest", "geo/shield.geo.json");
   }

   public ResourceLocation getTextureResource(GhastlingEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/shield.png");
   }

   public ResourceLocation getAnimationResource(GhastlingEntity animatable) {
      return new ResourceLocation("knightquest", "animations/helmet.animation.json");
   }
}
