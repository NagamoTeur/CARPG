package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class WealdWalkerModel<W extends WealdWalker> extends AnimatedGeoModel<W> {
   String type;

   public WealdWalkerModel(String type) {
      this.type = type;
   }

   public void setCustomAnimations(W entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * 0.010453292F);
      head.setRotationY(extraData.netHeadYaw * 0.015453292F);
      if ((Boolean)entity.m_20088_().m_135370_(WealdWalker.CASTING)) {
         IBone frontLeftLeg = this.getAnimationProcessor().getBone("leg_right");
         IBone frontRightLeg = this.getAnimationProcessor().getBone("leg_left");
         frontLeftLeg.setRotationX(Mth.m_14089_(entity.f_20925_ * 0.6662F) * 1.4F * entity.f_20924_);
         frontRightLeg.setRotationX(Mth.m_14089_(entity.f_20925_ * 0.6662F + (float) Math.PI) * 1.4F * entity.f_20924_);
      }
   }

   public ResourceLocation getModelResource(WealdWalker walker) {
      return walker.m_6162_()
         ? new ResourceLocation("ars_nouveau", "geo/" + this.type + "_waddler.geo.json")
         : new ResourceLocation("ars_nouveau", "geo/" + this.type + "_walker.geo.json");
   }

   public ResourceLocation getTextureResource(WealdWalker walker) {
      return walker.m_6162_()
         ? new ResourceLocation("ars_nouveau", "textures/entity/" + this.type + "_waddler.png")
         : new ResourceLocation("ars_nouveau", "textures/entity/" + this.type + "_walker.png");
   }

   public ResourceLocation getAnimationResource(WealdWalker walker) {
      return walker.m_6162_()
         ? new ResourceLocation("ars_nouveau", "animations/weald_waddler_animations.json")
         : new ResourceLocation("ars_nouveau", "animations/weald_walker_animations.json");
   }
}
