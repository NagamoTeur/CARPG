package net.cisco.item.model;

import net.cisco.item.SovereignAscendantItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SovereignAscendantModel extends AnimatedGeoModel<SovereignAscendantItem> {
   public ResourceLocation getAnimationResource(SovereignAscendantItem object) {
      return new ResourceLocation("cisco_mod", "animations/sovereign.animation.json");
   }

   public ResourceLocation getModelResource(SovereignAscendantItem object) {
      return new ResourceLocation("cisco_mod", "geo/sovereign.geo.json");
   }

   public ResourceLocation getTextureResource(SovereignAscendantItem object) {
      return new ResourceLocation("cisco_mod", "textures/items/sovereignascendant_layer_1.png");
   }
}
