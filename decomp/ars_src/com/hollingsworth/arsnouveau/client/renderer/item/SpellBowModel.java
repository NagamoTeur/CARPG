package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.SpellBow;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class SpellBowModel extends AnimatedGeoModel<SpellBow> {
   public ResourceLocation getModelResource(SpellBow wand) {
      return new ResourceLocation("ars_nouveau", "geo/spellbow.geo.json");
   }

   public ResourceLocation getTextureResource(SpellBow wand) {
      return new ResourceLocation("ars_nouveau", "textures/items/spellbow.png");
   }

   public ResourceLocation getAnimationResource(SpellBow wand) {
      return new ResourceLocation("ars_nouveau", "animations/wand_animation.json");
   }
}
