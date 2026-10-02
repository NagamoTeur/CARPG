package net.cisco.entity.model;

import net.cisco.entity.VengefulAfterImageEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VengefulAfterImageModel extends AnimatedGeoModel<VengefulAfterImageEntity> {
   public ResourceLocation getAnimationResource(VengefulAfterImageEntity entity) {
      return new ResourceLocation("cisco_mod", "animations/darkcisco.animation.json");
   }

   public ResourceLocation getModelResource(VengefulAfterImageEntity entity) {
      return new ResourceLocation("cisco_mod", "geo/darkcisco.geo.json");
   }

   public ResourceLocation getTextureResource(VengefulAfterImageEntity entity) {
      return new ResourceLocation("cisco_mod", "textures/entities/" + entity.getTexture() + ".png");
   }
}
