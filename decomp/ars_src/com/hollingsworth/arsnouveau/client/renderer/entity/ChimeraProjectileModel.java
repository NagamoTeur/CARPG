package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.EntityChimeraProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class ChimeraProjectileModel extends AnimatedGeoModel<EntityChimeraProjectile> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/spike.png");
   public static final ResourceLocation NORMAL_MODEL = new ResourceLocation("ars_nouveau", "geo/spike.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/spike_animations.json");

   public ResourceLocation getModelResource(EntityChimeraProjectile object) {
      return NORMAL_MODEL;
   }

   public ResourceLocation getTextureResource(EntityChimeraProjectile object) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(EntityChimeraProjectile animatable) {
      return ANIMATIONS;
   }
}
