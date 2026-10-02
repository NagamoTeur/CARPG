package net.xylonity.knightquest.client.armor.leg;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeoItemArmorModelLeg extends AnimatedGeoModel<GeoItemArmorLeg> {
   public ResourceLocation getModelResource(GeoItemArmorLeg animatable) {
      return new ResourceLocation("knightquest", animatable.getModelResource());
   }

   public ResourceLocation getTextureResource(GeoItemArmorLeg animatable) {
      return new ResourceLocation("knightquest", animatable.getTextureResource());
   }

   public ResourceLocation getAnimationResource(GeoItemArmorLeg animatable) {
      return new ResourceLocation("knightquest", "animations/helmet.animation.json");
   }
}
