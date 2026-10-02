package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.GiftStarbuncle;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class GiftStarbyModel extends AnimatedGeoModel<GiftStarbuncle> {
   private static final ResourceLocation WILD_TEXTURE = new ResourceLocation("ars_nouveau", "textures/entity/gift_starby.png");
   public static final ResourceLocation NORMAL_MODEL = new ResourceLocation("ars_nouveau", "geo/gift_starby.geo.json");
   public static final ResourceLocation ANIMATIONS = new ResourceLocation("ars_nouveau", "animations/starbuncle_animations.json");

   public void setCustomAnimations(GiftStarbuncle entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      if (!entity.isTaming()) {
         IBone head = this.getAnimationProcessor().getBone("head");
         if (customPredicate != null) {
            EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
            head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
         }
      }
   }

   public ResourceLocation getModelResource(GiftStarbuncle drygmy) {
      return NORMAL_MODEL;
   }

   public ResourceLocation getTextureResource(GiftStarbuncle drygmy) {
      return WILD_TEXTURE;
   }

   public ResourceLocation getAnimationResource(GiftStarbuncle drygmy) {
      return ANIMATIONS;
   }
}
