package com.hollingsworth.arsnouveau.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class BookwyrmModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {
   private static final ResourceLocation WILD_TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/book_wyrm_blue.png");
   public static final ResourceLocation NORMAL_MODEL = new ResourceLocation("ars_nouveau", "geo/book_wyrm.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/book_wyrm_animation.json");

   public void setCustomAnimations(T entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations((T)entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * 0.010453292F);
      head.setRotationY(extraData.netHeadYaw * 0.015453292F);
   }

   public ResourceLocation getModelResource(T wyrm) {
      return NORMAL_MODEL;
   }

   public ResourceLocation getTextureResource(T wyrm) {
      return WILD_TEXTURE;
   }

   public ResourceLocation getAnimationResource(T wyrm) {
      return ANIMATIONS;
   }
}
