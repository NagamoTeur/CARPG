package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WildenHunter;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class WildenHunterModel extends AnimatedGeoModel<WildenHunter> {
   public void setCustomAnimations(WildenHunter entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
   }

   public ResourceLocation getModelResource(WildenHunter hunter) {
      return new ResourceLocation("ars_nouveau", "geo/wilden_hunter.geo.json");
   }

   public ResourceLocation getTextureResource(WildenHunter hunter) {
      return new ResourceLocation("ars_nouveau", "textures/entity/wilden_hunter.png");
   }

   public ResourceLocation getAnimationResource(WildenHunter hunter) {
      return new ResourceLocation("ars_nouveau", "animations/wilden_hunter_animations.json");
   }
}
