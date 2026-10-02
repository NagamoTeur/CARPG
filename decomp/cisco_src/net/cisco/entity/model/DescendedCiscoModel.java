package net.cisco.entity.model;

import net.cisco.entity.DescendedCiscoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DescendedCiscoModel extends AnimatedGeoModel<DescendedCiscoEntity> {
   public ResourceLocation getAnimationResource(DescendedCiscoEntity entity) {
      return new ResourceLocation("cisco_mod", "animations/darkcisco.animation.json");
   }

   public ResourceLocation getModelResource(DescendedCiscoEntity entity) {
      return new ResourceLocation("cisco_mod", "geo/darkcisco.geo.json");
   }

   public ResourceLocation getTextureResource(DescendedCiscoEntity entity) {
      return new ResourceLocation("cisco_mod", "textures/entities/" + entity.getTexture() + ".png");
   }
}
