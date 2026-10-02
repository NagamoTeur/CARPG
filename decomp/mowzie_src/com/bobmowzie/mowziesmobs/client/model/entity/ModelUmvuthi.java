package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelUmvuthi extends MowzieAnimatedGeoModel<EntityUmvuthi> {
   public ResourceLocation getModelResource(EntityUmvuthi object) {
      return new ResourceLocation("mowziesmobs", "geo/umvuthi.geo.json");
   }

   public ResourceLocation getTextureResource(EntityUmvuthi object) {
      return new ResourceLocation("mowziesmobs", "textures/entity/umvuthi.png");
   }

   public ResourceLocation getAnimationResource(EntityUmvuthi object) {
      return new ResourceLocation("mowziesmobs", "animations/umvuthi.animation.json");
   }

   public void codeAnimations(EntityUmvuthi entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
      float frame = (float)entity.frame + customPredicate.getPartialTick();
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      float liftLegs = entity.legsUp.getAnimationProgressSinSqrt(customPredicate.getPartialTick());
      leftThigh.addRotationX(1.0F * liftLegs);
      rightThigh.addRotationX(1.0F * liftLegs);
      leftThigh.addRotationZ(1.5F * liftLegs);
      rightThigh.addRotationZ(-1.5F * liftLegs);
      leftThigh.addRotationY(-0.5F * liftLegs);
      rightThigh.addRotationY(0.5F * liftLegs);
      if (entity.m_6084_() && entity.active) {
         MowzieGeoBone neck1 = this.getMowzieBone("neck");
         MowzieGeoBone neck2 = this.getMowzieBone("neck2");
         MowzieGeoBone head = this.getMowzieBone("head");
         MowzieGeoBone[] lookPieces = new MowzieGeoBone[]{neck1, neck2, head};
         EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
         float headYaw = Mth.m_14177_(extraData.netHeadYaw);
         float headPitch = Mth.m_14177_(extraData.headPitch);
         float maxYaw = 140.0F;
         headYaw = Mth.m_14036_(headYaw, -maxYaw, maxYaw);

         for (MowzieGeoBone bone : lookPieces) {
            bone.addRotationX(headPitch * (float) (Math.PI / 180.0) / (float)lookPieces.length);
            bone.addRotationY(headYaw * (float) (Math.PI / 180.0) / (float)lookPieces.length);
         }

         MowzieGeoBone featherRaiseController = this.getMowzieBone("featherRaiseController");
         MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
         MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
         MowzieGeoBone rightCalf = this.getMowzieBone("rightCalf");
         MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
         MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
         MowzieGeoBone leftCalf = this.getMowzieBone("leftCalf");
         MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
         MowzieGeoBone rightLowerArm = this.getMowzieBone("rightLowerArm");
         MowzieGeoBone rightArmJoint = this.getMowzieBone("rightArmJoint");
         MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
         MowzieGeoBone leftLowerArm = this.getMowzieBone("leftLowerArm");
         MowzieGeoBone leftArmJoint = this.getMowzieBone("leftArmJoint");
         MowzieGeoBone chest = this.getMowzieBone("chest");
         MowzieGeoBone body = this.getMowzieBone("body");
         MowzieGeoBone headJoint = this.getMowzieBone("headJoint");
         float idleSpeed = 0.08F;
         featherRaiseController.addPositionX((float)(Math.sin(((double)frame - 1.2) * (double)idleSpeed) * 0.1));
         body.addRotationX((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.035));
         chest.addPositionY((float)(-0.7 + Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.014));
         chest.addRotationX((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * -0.017));
         neck1.addRotationX((float)(Math.cos(((double)frame - 0.3) * (double)idleSpeed) * -0.052));
         neck2.addRotationX((float)(Math.cos(((double)frame - 0.5) * (double)idleSpeed) * -0.052));
         headJoint.addRotationX((float)(Math.cos(((double)frame - 0.6) * (double)idleSpeed) * 0.1));
         leftArmJoint.addRotationX((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.052));
         leftLowerArm.addRotationY((float)(-Math.cos(((double)frame - 0.0) * (double)idleSpeed) * 0.035));
         leftHand.addRotationY((float)(-Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.087));
         rightArmJoint.addRotationX((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.052));
         rightLowerArm.addRotationY((float)(Math.cos(((double)frame - 0.0) * (double)idleSpeed) * 0.035));
         rightHand.addRotationY((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.087));
         leftThigh.addRotationY((float)(Math.cos(((double)frame - 0.0) * (double)idleSpeed) * -0.052) * (1.0F - liftLegs));
         leftThigh.addRotationZ((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * -0.052) * (1.0F - liftLegs));
         leftCalf.addRotationX((float)(Math.sin(((double)frame - 0.2) * (double)idleSpeed) * 0.087) * (1.0F - liftLegs));
         leftAnkle.addRotationX((float)(Math.cos(((double)frame - 0.2) * (double)idleSpeed) * -0.087) * (1.0F - liftLegs));
         leftFoot.addRotationX((float)(Math.cos(((double)frame - 0.4) * (double)idleSpeed) * -0.14));
         rightThigh.addRotationY((float)(Math.cos(((double)frame - 0.0) * (double)idleSpeed) * 0.052) * (1.0F - liftLegs));
         rightThigh.addRotationZ((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.052) * (1.0F - liftLegs));
         rightCalf.addRotationX((float)(Math.sin(((double)frame - 0.2) * (double)idleSpeed) * 0.087) * (1.0F - liftLegs));
         rightAnkle.addRotationX((float)(Math.cos(((double)frame - 0.2) * (double)idleSpeed) * -0.087) * (1.0F - liftLegs));
         rightFoot.addRotationX((float)(Math.cos(((double)frame - 0.4) * (double)idleSpeed) * -0.14));
         leftThigh.addRotationY((float)(Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.035) * liftLegs);
         rightThigh.addRotationY((float)(-Math.sin(((double)frame - 0.0) * (double)idleSpeed) * 0.035) * liftLegs);
         float armAimControl = this.getControllerValue("armAimController");
         leftArmJoint.addRotationX(headPitch * (float) (Math.PI / 180.0) * armAimControl);
         leftArmJoint.addRotationY(headYaw * (float) (Math.PI / 180.0) * armAimControl);
      }

      float bellyBounceControl = this.getControllerValue("bellyBounceController");
      float jiggleSpeed = 2.5F;
      float jiggleScale = (float)((double)bellyBounceControl * 0.1 * Math.cos((double)(jiggleSpeed * frame)));
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone tail = this.getMowzieBone("tail");
      stomach.setScale(stomach.getScaleX() + jiggleScale, stomach.getScaleY() + jiggleScale, stomach.getScaleZ() + jiggleScale);
      chest.addPositionY(jiggleScale * 3.0F);
      leftThigh.addPositionX(-jiggleScale * 5.0F);
      rightThigh.addPositionX(jiggleScale * 5.0F);
      tail.addPositionZ(jiggleScale * 4.0F);
      float featherShakeControl = this.getControllerValue("featherShakeController");
      float featherRaiseControl = this.getControllerValue("featherRaiseController");
      List<Triple<MowzieGeoBone, Axis, Boolean>> feathers = new ArrayList<>();
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersFront4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack1"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack2"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack4"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersBack5"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft1"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft2"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft3"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft4"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersLeft5"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight1"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight2"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight3"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight4"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("neckFeathersRight5"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersLeft3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestFeathersRight3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront2"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersFront3"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersRight5"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft1"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft2"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft3"), Axis.X, true));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft4"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("bellyFeathersLeft5"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyFront"), Axis.X, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyRight"), Axis.Z, false));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyLeft"), Axis.Z, true));
      feathers.add(Triple.of(this.getMowzieBone("chestDraperyBack"), Axis.X, true));

      for (Triple<MowzieGeoBone, Axis, Boolean> feather : feathers) {
         MowzieGeoBone bone = (MowzieGeoBone)feather.getLeft();
         float oscillation = (float)(
               (double)featherShakeControl * 0.13 * Math.cos(1.4 * (double)frame + (double)bone.getPivotY() * -0.15 + (double)bone.getPivotZ() * -0.1)
            )
            + featherRaiseControl;
         if ((Boolean)feather.getRight()) {
            oscillation *= -1.0F;
         }

         Axis axis = (Axis)feather.getMiddle();
         if (axis == Axis.X) {
            bone.addRotationX(oscillation);
         } else if (axis == Axis.Y) {
            bone.addRotationY(oscillation);
         } else {
            bone.addRotationZ(oscillation);
         }
      }

      MowzieGeoBone mask = this.getMowzieBone("mask");
      MowzieGeoBone body = this.getMowzieBone("body");
      mask.setScale(1.0F / (float)body.getScale().f_86214_, 1.0F / (float)body.getScale().f_86215_, 1.0F / (float)body.getScale().f_86216_);
   }
}
