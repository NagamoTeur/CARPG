package com.hollingsworth.arsnouveau.client.renderer.tile;

import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class GenericModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
   public String path;
   public ResourceLocation modelLocation;
   public ResourceLocation textLoc;
   public ResourceLocation animationLoc;
   public String textPathRoot = "blocks";
   public String name;

   public GenericModel(String name) {
      this.modelLocation = new ResourceLocation("ars_nouveau", "geo/" + name + ".geo.json");
      this.textLoc = new ResourceLocation("ars_nouveau", "textures/" + this.textPathRoot + "/" + name + ".png");
      this.animationLoc = new ResourceLocation("ars_nouveau", "animations/" + name + "_animations.json");
      this.name = name;
   }

   public GenericModel(String name, String textPath) {
      this(name);
      this.textPathRoot = textPath;
      this.textLoc = new ResourceLocation("ars_nouveau", "textures/" + this.textPathRoot + "/" + name + ".png");
   }

   public GenericModel withEmptyAnim() {
      this.animationLoc = new ResourceLocation("ars_nouveau", "animations/empty.json");
      return this;
   }

   public ResourceLocation getModelResource(T iAnimatable) {
      return this.modelLocation;
   }

   public ResourceLocation getTextureResource(T iAnimatable) {
      return this.textLoc;
   }

   public ResourceLocation getAnimationResource(T iAnimatable) {
      return this.animationLoc;
   }
}
