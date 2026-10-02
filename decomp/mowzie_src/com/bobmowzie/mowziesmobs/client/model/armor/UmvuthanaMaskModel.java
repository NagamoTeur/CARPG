package com.bobmowzie.mowziesmobs.client.model.armor;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UmvuthanaMaskModel extends AnimatedGeoModel<ItemUmvuthanaMask> {
   public ResourceLocation getModelResource(ItemUmvuthanaMask object) {
      return new ResourceLocation("mowziesmobs", "geo/mask_" + object.getType().name + ".geo.json");
   }

   public ResourceLocation getTextureResource(ItemUmvuthanaMask object) {
      return new ResourceLocation("mowziesmobs", "textures/item/umvuthana_mask_" + object.getType().name + ".png");
   }

   public ResourceLocation getAnimationResource(ItemUmvuthanaMask animatable) {
      return new ResourceLocation("mowziesmobs", "animations/umvuthana_mask.animation.json");
   }
}
