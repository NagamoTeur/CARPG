package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.BadPatchEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BadPatchModel extends AnimatedGeoModel<BadPatchEntity> {
   public ResourceLocation getModelResource(BadPatchEntity animatable) {
      return new ResourceLocation("knightquest", "geo/bad_patch.geo.json");
   }

   public ResourceLocation getTextureResource(BadPatchEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/bad_patch.png");
   }

   public ResourceLocation getAnimationResource(BadPatchEntity animatable) {
      return new ResourceLocation("knightquest", "animations/bad_patch.animation.json");
   }
}
