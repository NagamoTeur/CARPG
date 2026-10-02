package com.bobmowzie.mowziesmobs.client.model.armor;

import com.bobmowzie.mowziesmobs.server.item.ItemSolVisage;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SolVisageModel extends AnimatedGeoModel<ItemSolVisage> {
   public ResourceLocation getModelResource(ItemSolVisage object) {
      return new ResourceLocation("mowziesmobs", "geo/sol_visage.geo.json");
   }

   public ResourceLocation getTextureResource(ItemSolVisage object) {
      return new ResourceLocation("mowziesmobs", "textures/entity/umvuthi.png");
   }

   public ResourceLocation getAnimationResource(ItemSolVisage animatable) {
      return new ResourceLocation("mowziesmobs", "animations/sol_visage.animation.json");
   }
}
