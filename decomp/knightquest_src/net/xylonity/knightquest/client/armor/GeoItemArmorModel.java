package net.xylonity.knightquest.client.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeoItemArmorModel extends AnimatedGeoModel<GeoItemArmor> {
   public ResourceLocation getModelResource(GeoItemArmor animatable) {
      return new ResourceLocation("knightquest", animatable.getModelResource());
   }

   public ResourceLocation getTextureResource(GeoItemArmor animatable) {
      return new ResourceLocation("knightquest", animatable.getTextureResource());
   }

   public ResourceLocation getAnimationResource(GeoItemArmor animatable) {
      return new ResourceLocation("knightquest", "animations/helmet.animation.json");
   }
}
