package com.hollingsworth.arsnouveau.client.renderer.entity.familiar;

import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class FamiliarStarbyModel<T extends FamiliarStarbuncle> extends AnimatedGeoModel<T> {
   public void setCustomAnimations(T entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
   }

   public ResourceLocation getModelResource(FamiliarStarbuncle carbuncle) {
      return new ResourceLocation("ars_nouveau", "geo/starbuncle.geo.json");
   }

   public ResourceLocation getTextureResource(FamiliarStarbuncle carbuncle) {
      return carbuncle.getTexture((FamiliarEntity)carbuncle);
   }

   public ResourceLocation getAnimationResource(FamiliarStarbuncle carbuncle) {
      return new ResourceLocation("ars_nouveau", "animations/starbuncle_animations.json");
   }
}
