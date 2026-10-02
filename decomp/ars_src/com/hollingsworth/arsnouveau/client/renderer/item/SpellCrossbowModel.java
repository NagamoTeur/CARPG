package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.common.items.SpellCrossbow;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class SpellCrossbowModel extends AnimatedGeoModel<SpellCrossbow> {
   public ResourceLocation getModelResource(SpellCrossbow wand) {
      return new ResourceLocation("ars_nouveau", "geo/spell_crossbow.geo.json");
   }

   public ResourceLocation getTextureResource(SpellCrossbow wand) {
      return new ResourceLocation("ars_nouveau", "textures/items/spell_crossbow.png");
   }

   public ResourceLocation getAnimationResource(SpellCrossbow wand) {
      return new ResourceLocation("ars_nouveau", "animations/wand_animation.json");
   }
}
