package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.PotionMelderTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class PotionMelderModel extends AnimatedGeoModel<PotionMelderTile> {
   public static final ResourceLocation model = new ResourceLocation("ars_nouveau", "geo/potion_melder.geo.json");
   public static final ResourceLocation texture = new ResourceLocation("ars_nouveau", "textures/blocks/potion_stirrer.png");
   public static final ResourceLocation anim = new ResourceLocation("ars_nouveau", "animations/potion_melder_animation.json");

   public ResourceLocation getModelResource(PotionMelderTile volcanicTile) {
      return model;
   }

   public ResourceLocation getTextureResource(PotionMelderTile volcanicTile) {
      return texture;
   }

   public ResourceLocation getAnimationResource(PotionMelderTile volcanicTile) {
      return anim;
   }
}
