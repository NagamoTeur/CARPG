package com.hollingsworth.arsnouveau.client.renderer.entity;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class DrygmyModel<T extends LivingEntity & IAnimatable> extends AnimatedGeoModel<T> {
   private static final ResourceLocation WILD_TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/drygmy.png");
   public static final ResourceLocation NORMAL_MODEL = new ResourceLocation("ars_nouveau", "geo/drygmy.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/drygmy_animations.json");

   public void setCustomAnimations(T entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations((T)entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
   }

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
