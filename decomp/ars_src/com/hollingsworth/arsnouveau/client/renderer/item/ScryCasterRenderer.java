package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.ScryCaster;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ScryCasterRenderer extends FixedGeoItemRenderer<ScryCaster> {
   public ScryCasterRenderer() {
      super(new AnimatedGeoModel<ScryCaster>() {
         public ResourceLocation getModelResource(ScryCaster wand) {
            return new ResourceLocation("ars_nouveau", "geo/enchanters_eye.geo.json");
         }

         public ResourceLocation getTextureResource(ScryCaster wand) {
            return new ResourceLocation("ars_nouveau", "textures/items/enchanters_eye.png");
         }

         public ResourceLocation getAnimationResource(ScryCaster wand) {
            return new ResourceLocation("ars_nouveau", "animations/enchanters_eye.json");
         }
      });
   }
}
