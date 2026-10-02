package com.hollingsworth.arsnouveau.client.renderer.tile;

import com.hollingsworth.arsnouveau.common.block.tile.SourcelinkTile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class SourcelinkModel<T extends SourcelinkTile> extends AnimatedGeoModel<SourcelinkTile> {
   public ResourceLocation modelLocation;
   public ResourceLocation textLoc;
   public ResourceLocation animationLoc = new ResourceLocation("ars_nouveau", "animations/volcanic_sourcelink_animations.json");

   public SourcelinkModel(String name) {
      this.modelLocation = new ResourceLocation("ars_nouveau", "geo/" + name + "_sourcelink.geo.json");
      this.textLoc = new ResourceLocation("ars_nouveau", "textures/blocks/" + name + "_sourcelink.png");
   }

   public ResourceLocation getModelResource(SourcelinkTile agronomicSourcelink) {
      return this.modelLocation;
   }

   public ResourceLocation getTextureResource(SourcelinkTile agronomicSourcelink) {
      return this.textLoc;
   }

   public ResourceLocation getAnimationResource(SourcelinkTile agronomicSourcelink) {
      return this.animationLoc;
   }
}
