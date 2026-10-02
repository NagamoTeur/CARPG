package com.bobmowzie.mowziesmobs.client.model.item;

import com.bobmowzie.mowziesmobs.server.item.ItemEarthboreGauntlet;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelEarthboreGauntlet extends AnimatedGeoModel<ItemEarthboreGauntlet> {
   public ResourceLocation getModelResource(ItemEarthboreGauntlet object) {
      return new ResourceLocation("mowziesmobs", "geo/earthbore_gauntlet.geo.json");
   }

   public ResourceLocation getTextureResource(ItemEarthboreGauntlet object) {
      return new ResourceLocation("mowziesmobs", "textures/item/earthbore_gauntlet.png");
   }

   public ResourceLocation getAnimationResource(ItemEarthboreGauntlet animatable) {
      return new ResourceLocation("mowziesmobs", "animations/earthbore_gauntlet.animation.json");
   }
}
