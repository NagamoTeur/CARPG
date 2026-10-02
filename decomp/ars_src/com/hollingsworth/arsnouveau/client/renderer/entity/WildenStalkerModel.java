package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WildenStalker;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class WildenStalkerModel extends AnimatedGeoModel<WildenStalker> {
   public void setCustomAnimations(WildenStalker entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
      this.getBone("wing_fly_left").setHidden(!entity.isFlying());
      this.getBone("wing_fly_right").setHidden(!entity.isFlying());
      this.getBone("wing_run_left").setHidden(entity.isFlying());
      this.getBone("wing_run_right").setHidden(entity.isFlying());
   }

   public ResourceLocation getModelResource(WildenStalker wildenStalker) {
      return new ResourceLocation("ars_nouveau", "geo/wilden_stalker.geo.json");
   }

   public ResourceLocation getTextureResource(WildenStalker wildenStalker) {
      return new ResourceLocation("ars_nouveau", "textures/entity/wilden_stalker.png");
   }

   public ResourceLocation getAnimationResource(WildenStalker wildenStalker) {
      return new ResourceLocation("ars_nouveau", "animations/wilden_stalker_animations.json");
   }
}
