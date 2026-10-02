package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.Lily;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class LilyModel extends AnimatedGeoModel<Lily> {
   public void setCustomAnimations(Lily entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
   }

   public ResourceLocation getModelResource(Lily whirlisprig) {
      return new ResourceLocation("ars_nouveau", "geo/lily.geo.json");
   }

   public ResourceLocation getTextureResource(Lily whirlisprig) {
      return new ResourceLocation("ars_nouveau", "textures/entity/lily.png");
   }

   public ResourceLocation getAnimationResource(Lily whirlisprig) {
      return new ResourceLocation("ars_nouveau", "animations/lily_animations.json");
   }
}
