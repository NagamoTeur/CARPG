package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.Wand;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class WandModel extends AnimatedGeoModel<Wand> {
   public ResourceLocation getModelResource(Wand wand) {
      return new ResourceLocation("ars_nouveau", "geo/wand.geo.json");
   }

   public ResourceLocation getTextureResource(Wand wand) {
      return new ResourceLocation("ars_nouveau", "textures/items/wand.png");
   }

   public ResourceLocation getAnimationResource(Wand wand) {
      return new ResourceLocation("ars_nouveau", "animations/wand_animation.json");
   }
}
