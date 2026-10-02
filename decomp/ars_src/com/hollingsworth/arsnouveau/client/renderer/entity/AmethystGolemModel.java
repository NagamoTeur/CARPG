package com.hollingsworth.arsnouveau.client.renderer.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class AmethystGolemModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {
   private static final ResourceLocation WILD_TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/amethyst_golem.png");
   public static final ResourceLocation NORMAL_MODEL = new ResourceLocation("ars_nouveau", "geo/amethyst_golem.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/amethyst_golem_animations.json");

   public ResourceLocation getModelResource(T drygmy) {
      return NORMAL_MODEL;
   }

   public ResourceLocation getTextureResource(T drygmy) {
      return WILD_TEXTURE;
   }

   public ResourceLocation getAnimationResource(T drygmy) {
      return ANIMATIONS;
   }
}
