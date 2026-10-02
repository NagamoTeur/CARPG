package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.MommaLizzyEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MommaLizzyModel extends AnimatedGeoModel<MommaLizzyEntity> {
   public ResourceLocation getModelResource(MommaLizzyEntity animatable) {
      return new ResourceLocation("knightquest", "geo/momma_lizzy.geo.json");
   }

   public ResourceLocation getTextureResource(MommaLizzyEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/momma_lizzy.png");
   }

   public ResourceLocation getAnimationResource(MommaLizzyEntity animatable) {
      return new ResourceLocation("knightquest", "animations/momma_lizzy.animation.json");
   }
}
