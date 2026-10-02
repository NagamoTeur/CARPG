package net.xylonity.knightquest.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.xylonity.knightquest.common.entity.entities.EldBombEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EldBombModel extends AnimatedGeoModel<EldBombEntity> {
   private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("knightquest", "textures/entity/eldbomb.png");
   private static final ResourceLocation WHITE_TEXTURE = new ResourceLocation("knightquest", "textures/entity/eldbomb_white.png");

   public ResourceLocation getModelResource(EldBombEntity animatable) {
      return new ResourceLocation("knightquest", "geo/eldbomb.geo.json");
   }

   public ResourceLocation getTextureResource(EldBombEntity animatable) {
      if (animatable.getSwell() > 10) {
         return animatable.f_19797_ / 5 % 2 == 0 ? WHITE_TEXTURE : DEFAULT_TEXTURE;
      } else {
         return DEFAULT_TEXTURE;
      }
   }

   public ResourceLocation getAnimationResource(EldBombEntity animatable) {
      return new ResourceLocation("knightquest", "animations/eldbomb.animation.json");
   }
}
