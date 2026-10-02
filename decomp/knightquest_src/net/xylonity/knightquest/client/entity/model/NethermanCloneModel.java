package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.boss.NethermanCloneEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NethermanCloneModel extends AnimatedGeoModel<NethermanCloneEntity> {
   public ResourceLocation getModelResource(NethermanCloneEntity animatable) {
      return new ResourceLocation("knightquest", "geo/netherman_clone.geo.json");
   }

   public ResourceLocation getTextureResource(NethermanCloneEntity animatable) {
      return new ResourceLocation("knightquest", "textures/entity/netherman_clone.png");
   }

   public ResourceLocation getAnimationResource(NethermanCloneEntity animatable) {
      return new ResourceLocation("knightquest", "animations/netherman_clone.animation.json");
   }
}
