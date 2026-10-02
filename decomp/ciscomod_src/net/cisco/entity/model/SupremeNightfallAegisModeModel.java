package net.cisco.entity.model;

import net.cisco.entity.SupremeNightfallAegisModeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SupremeNightfallAegisModeModel extends AnimatedGeoModel<SupremeNightfallAegisModeEntity> {
   public ResourceLocation getAnimationResource(SupremeNightfallAegisModeEntity entity) {
      return new ResourceLocation("cisco_mod", "animations/supremenighfallentity.animation.json");
   }

   public ResourceLocation getModelResource(SupremeNightfallAegisModeEntity entity) {
      return new ResourceLocation("cisco_mod", "geo/supremenighfallentity.geo.json");
   }

   public ResourceLocation getTextureResource(SupremeNightfallAegisModeEntity entity) {
      return new ResourceLocation("cisco_mod", "textures/entities/" + entity.getTexture() + ".png");
   }
}
