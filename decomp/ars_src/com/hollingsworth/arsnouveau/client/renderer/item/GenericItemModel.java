package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.AnimBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class GenericItemModel extends AnimatedGeoModel<AnimBlockItem> {
   AnimatedGeoModel model;

   public GenericItemModel(AnimatedGeoModel model) {
      this.model = model;
   }

   public ResourceLocation getModelResource(AnimBlockItem animBlockItem) {
      return this.model.getModelResource(null);
   }

   public ResourceLocation getTextureResource(AnimBlockItem animBlockItem) {
      return this.model.getTextureResource(null);
   }

   public ResourceLocation getAnimationResource(AnimBlockItem animBlockItem) {
      return this.model.getAnimationResource(null);
   }
}
