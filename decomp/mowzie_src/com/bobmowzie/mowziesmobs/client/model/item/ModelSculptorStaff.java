package com.bobmowzie.mowziesmobs.client.model.item;

import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSculptorStaff extends AnimatedGeoModel<ItemSculptorStaff> {
   public ResourceLocation getModelResource(ItemSculptorStaff object) {
      return new ResourceLocation("mowziesmobs", "geo/sculptor_staff.geo.json");
   }

   public ResourceLocation getTextureResource(ItemSculptorStaff object) {
      return new ResourceLocation("mowziesmobs", "textures/item/sculptor_staff.png");
   }

   public ResourceLocation getAnimationResource(ItemSculptorStaff animatable) {
      return new ResourceLocation("mowziesmobs", "animations/sculptor_staff.animation.json");
   }
}
