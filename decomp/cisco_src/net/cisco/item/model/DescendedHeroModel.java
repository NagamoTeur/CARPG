package net.cisco.item.model;

import net.cisco.item.DescendedHeroItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DescendedHeroModel extends AnimatedGeoModel<DescendedHeroItem> {
   public ResourceLocation getAnimationResource(DescendedHeroItem object) {
      return new ResourceLocation("cisco_mod", "animations/pos.animation.json");
   }

   public ResourceLocation getModelResource(DescendedHeroItem object) {
      return new ResourceLocation("cisco_mod", "geo/pos.geo.json");
   }

   public ResourceLocation getTextureResource(DescendedHeroItem object) {
      return new ResourceLocation("cisco_mod", "textures/items/descendedfinal_layer_1.png");
   }
}
