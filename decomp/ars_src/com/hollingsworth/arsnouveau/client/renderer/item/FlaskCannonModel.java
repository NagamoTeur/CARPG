package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.FlaskCannon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class FlaskCannonModel extends AnimatedGeoModel<FlaskCannon> {
   public ResourceLocation getModelResource(FlaskCannon object) {
      return new ResourceLocation("ars_nouveau", "geo/lingering_flask_cannon.geo.json");
   }

   public ResourceLocation getTextureResource(FlaskCannon object) {
      return new ResourceLocation("ars_nouveau", "textures/items/lingering_flask_cannon.png");
   }

   public ResourceLocation getAnimationResource(FlaskCannon animatable) {
      return new ResourceLocation("ars_nouveau", "animations/empty.json");
   }
}
