package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.EnchantersShield;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ShieldModel extends AnimatedGeoModel<EnchantersShield> {
   public ResourceLocation getModelResource(EnchantersShield wand) {
      return new ResourceLocation("ars_nouveau", "geo/shield.geo.json");
   }

   public ResourceLocation getTextureResource(EnchantersShield wand) {
      return new ResourceLocation("ars_nouveau", "textures/items/enchanters_shield.png");
   }

   public ResourceLocation getAnimationResource(EnchantersShield wand) {
      return new ResourceLocation("ars_nouveau", "animations/shield.json");
   }
}
