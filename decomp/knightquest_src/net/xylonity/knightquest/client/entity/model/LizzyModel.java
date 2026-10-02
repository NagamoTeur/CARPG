package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.LizzyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LizzyModel extends AnimatedGeoModel<LizzyEntity> {
   public ResourceLocation getModelResource(LizzyEntity animatable) {
      return new ResourceLocation("knightquest", "geo/lizzy.geo.json");
   }

   public ResourceLocation getTextureResource(LizzyEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/lizzy.png");
   }

   public ResourceLocation getAnimationResource(LizzyEntity animatable) {
      return new ResourceLocation("knightquest", "animations/lizzy.animation.json");
   }
}
