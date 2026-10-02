package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.common.entity.WildenGuardian;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;
import software.bernie.ars_nouveau.geckolib3.model.provider.data.EntityModelData;

public class WildenGuardianModel extends AnimatedGeoModel<WildenGuardian> {
   public static final ResourceLocation WARDER_NEUTRAL = new ResourceLocation("ars_nouveau", "geo/wilden_guardian.geo.json");
   public static final ResourceLocation TEXT = new ResourceLocation("ars_nouveau", "textures/entity/wilden_guardian.png");
   public static final ResourceLocation ANIM = new ResourceLocation("ars_nouveau", "animations/wilden_defender_animations.json");

   public void setCustomAnimations(WildenGuardian entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
      super.setCustomAnimations(entity, uniqueID, customPredicate);
      IBone head = this.getAnimationProcessor().getBone("head");
      EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(extraData.netHeadYaw * (float) (Math.PI / 180.0));
      this.getBone("body_spines_retracted").setHidden(entity.isArmored());
      this.getBone("left_arm_spines_retracted").setHidden(entity.isArmored());
      this.getBone("right_arm_spines_retracted").setHidden(entity.isArmored());
      this.getBone("right_leg_spines_retracted").setHidden(entity.isArmored());
      this.getBone("left_leg_spines_retracted").setHidden(entity.isArmored());
      this.getBone("body_spines_extended").setHidden(!entity.isArmored());
      this.getBone("left_arm_spines_extended").setHidden(!entity.isArmored());
      this.getBone("right_arm_spines_extended").setHidden(!entity.isArmored());
      this.getBone("right_leg_spines_extended").setHidden(!entity.isArmored());
      this.getBone("left_leg_spines_extended").setHidden(!entity.isArmored());
   }

   public ResourceLocation getModelResource(WildenGuardian wildenStalker) {
      return WARDER_NEUTRAL;
   }

   public ResourceLocation getTextureResource(WildenGuardian wildenStalker) {
      return TEXT;
   }

   public ResourceLocation getAnimationResource(WildenGuardian wildenStalker) {
      return ANIM;
   }
}
