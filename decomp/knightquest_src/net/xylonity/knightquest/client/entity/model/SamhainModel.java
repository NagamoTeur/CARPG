package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SamhainModel extends AnimatedGeoModel<SamhainEntity> {
   public ResourceLocation getModelResource(SamhainEntity animatable) {
      return new ResourceLocation("knightquest", "geo/samhain.geo.json");
   }

   public ResourceLocation getTextureResource(SamhainEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/samhain.png");
   }

   public ResourceLocation getAnimationResource(SamhainEntity animatable) {
      return new ResourceLocation("knightquest", "animations/samhain.animation.json");
   }
}
