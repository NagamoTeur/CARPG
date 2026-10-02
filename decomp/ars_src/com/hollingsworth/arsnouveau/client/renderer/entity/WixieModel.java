package com.hollingsworth.arsnouveau.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class WixieModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {
   private static final ResourceLocation WILD_TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/wixie.png");

   public void setCustomAnimations(T entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations((T)entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * 0.010453292F);
      head.setRotationY(extraData.netHeadYaw * 0.015453292F);
   }

   public ResourceLocation getModelResource(T entityWixie) {
      return new ResourceLocation("ars_nouveau", "geo/wixie.geo.json");
   }

   public ResourceLocation getTextureResource(T entityWixie) {
      return WILD_TEXTURE;
   }

   public ResourceLocation getAnimationResource(T entityWixie) {
      return new ResourceLocation("ars_nouveau", "animations/wixie_animations.json");
   }
}
