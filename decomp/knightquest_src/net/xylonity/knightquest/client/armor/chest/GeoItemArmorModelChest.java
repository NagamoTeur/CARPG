package net.xylonity.knightquest.client.armor.chest;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeoItemArmorModelChest extends AnimatedGeoModel<GeoItemArmorChest> {
   public ResourceLocation getModelResource(GeoItemArmorChest animatable) {
      return new ResourceLocation("knightquest", animatable.getModelResource());
   }

   public ResourceLocation getTextureResource(GeoItemArmorChest animatable) {
      return new ResourceLocation("knightquest", animatable.getTextureResource());
   }

   public ResourceLocation getAnimationResource(GeoItemArmorChest animatable) {
      return new ResourceLocation("knightquest", "animations/helmet.animation.json");
   }
}
