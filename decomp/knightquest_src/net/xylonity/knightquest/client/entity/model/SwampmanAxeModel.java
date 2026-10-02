package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.SwampmanAxeEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwampmanAxeModel extends AnimatedGeoModel<SwampmanAxeEntity> {
   public ResourceLocation getModelResource(SwampmanAxeEntity animatable) {
      return new ResourceLocation("knightquest", "geo/swampman_axe.geo.json");
   }

   public ResourceLocation getTextureResource(SwampmanAxeEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/swampman.png");
   }

   public ResourceLocation getAnimationResource(SwampmanAxeEntity animatable) {
      return new ResourceLocation("knightquest", "animations/swampman.animation.json");
   }
}
