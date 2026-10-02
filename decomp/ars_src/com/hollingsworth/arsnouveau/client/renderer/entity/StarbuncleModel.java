package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class StarbuncleModel extends AnimatedGeoModel<Starbuncle> {
   public void setCustomAnimations(Starbuncle entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      if (!entity.partyCarby) {
         IBone head = this.getAnimationProcessor().getBone("head");
         if (customPredicate != null) {
            this.getBone("basket").setHidden(!entity.isTamed());
            EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
            head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
         }
      }
   }

   public ResourceLocation getModelResource(Starbuncle carbuncle) {
      return new ResourceLocation("ars_nouveau", "geo/starbuncle.geo.json");
   }

   public ResourceLocation getTextureResource(Starbuncle carbuncle) {
      return carbuncle.getTexture(carbuncle);
   }

   public ResourceLocation getAnimationResource(Starbuncle carbuncle) {
      return new ResourceLocation("ars_nouveau", "animations/starbuncle_animations.json");
   }
}
