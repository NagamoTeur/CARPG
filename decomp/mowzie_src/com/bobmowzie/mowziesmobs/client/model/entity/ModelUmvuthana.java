package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelUmvuthana extends MowzieAnimatedGeoModel<EntityUmvuthana> {
   public ResourceLocation getModelResource(EntityUmvuthana object) {
      return new ResourceLocation("mowziesmobs", "geo/umvuthana.geo.json");
   }

   public ResourceLocation getTextureResource(EntityUmvuthana entity) {
      boolean isElite = entity.getMaskType() == MaskType.FAITH || entity.getMaskType() == MaskType.FURY;
      return new ResourceLocation("mowziesmobs", isElite ? "textures/entity/umvuthana_elite.png" : "textures/entity/umvuthana.png");
   }

   public ResourceLocation getAnimationResource(EntityUmvuthana object) {
      return new ResourceLocation("mowziesmobs", "animations/umvuthana.animation.json");
   }

   public void codeAnimations(EntityUmvuthana entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
      boolean isRaptor = entity.getMaskType() == MaskType.FURY;
      boolean isElite = entity.getMaskType() == MaskType.FAITH || isRaptor;
      this.getMowzieBone("crestRight").isHidden = !isElite;
      this.getMowzieBone("crestLeft").isHidden = !isElite;
      this.getMowzieBone("crest1").isHidden = !isElite;
      this.getMowzieBone("leftIndexTalon").isHidden = !isRaptor;
      this.getMowzieBone("leftIndexClaw").isHidden = isRaptor;
      this.getMowzieBone("rightIndexTalon").isHidden = !isRaptor;
      this.getMowzieBone("rightIndexClaw").isHidden = isRaptor;
      MowzieGeoBone root = this.getMowzieBone("root");
      if (isElite) {
         root.multiplyScale(0.93F, 0.93F, 0.93F);
      } else {
         root.multiplyScale(0.83F, 0.83F, 0.83F);
      }

      MowzieGeoBone mask = this.getMowzieBone("mask");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      if (entity.getActiveAbilityType() != EntityUmvuthana.TELEPORT_ABILITY) {
         mask.setScale(1.0F / (float)hips.getScale().f_86214_, 1.0F / (float)hips.getScale().f_86215_, 1.0F / (float)hips.getScale().f_86216_);
      }

      if (entity.m_6084_() && entity.active) {
         MowzieGeoBone head = this.getMowzieBone("head");
         MowzieGeoBone neck = this.getMowzieBone("neck");
         EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
         float headYaw = Mth.m_14177_(extraData.netHeadYaw);
         float headPitch = Mth.m_14177_(extraData.headPitch);
         head.addRotationX(headPitch * (float) (Math.PI / 180.0) / 2.0F);
         head.addRotationY(headYaw * (float) (Math.PI / 180.0) / 2.0F);
         neck.addRotationX(headPitch * (float) (Math.PI / 180.0) / 2.0F);
         neck.addRotationY(headYaw * (float) (Math.PI / 180.0) / 2.0F);
      }

      MowzieGeoBone maskHand = this.getMowzieBone("maskHand");
      MowzieGeoBone maskTwitcher = this.getMowzieBone("maskTwitcher");
      float maskPlaceSwitch = this.getControllerValue("maskPlacementSwitchController");
      if ((double)maskPlaceSwitch == 1.0) {
         maskTwitcher.isHidden = true;
         maskHand.isHidden = false;
      } else {
         maskTwitcher.isHidden = false;
         maskHand.isHidden = true;
      }

      float animSpeed = 1.4F;
      float limbSwing = customPredicate.getLimbSwing();
      float limbSwingAmount = customPredicate.getLimbSwingAmount();
      Vec3 moveVec = entity.m_20184_().m_82541_().m_82524_((float)Math.toRadians((double)entity.f_20883_ + 90.0));
      float forward = (float)Math.max(0.0, new Vec3(1.0, 0.0, 0.0).m_82526_(moveVec));
      float backward = (float)Math.max(0.0, new Vec3(-1.0, 0.0, 0.0).m_82526_(moveVec));
      float left = (float)Math.max(0.0, new Vec3(0.0, 0.0, -1.0).m_82526_(moveVec));
      float right = (float)Math.max(0.0, new Vec3(0.0, 0.0, 1.0).m_82526_(moveVec));
      limbSwingAmount *= 2.0F;
      limbSwingAmount = Math.min(0.7F, limbSwingAmount);
      float locomotionAnimController = this.getControllerValue("locomotionAnimController");
      float runAnim = this.getControllerValue("walkRunSwitchController");
      float walkAnim = 1.0F - runAnim;
      this.walkForwardAnim(forward * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkBackwardAnim(backward * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkLeftAnim(left * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.walkRightAnim(right * locomotionAnimController * walkAnim, limbSwing, limbSwingAmount, animSpeed);
      this.runAnim(locomotionAnimController * runAnim, limbSwing, limbSwingAmount, animSpeed);
   }

   private void runAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.7F;
      speed = (float)((double)speed * 0.8);
      hips.addPositionY(
         blend * (float)(Math.cos((double)(limbSwing * speed) - 1.7) * 2.0 * (double)globalHeight + (double)(4.0F * globalHeight)) * limbSwingAmount
      );
      hips.addRotationX(blend * -0.4F * limbSwingAmount * globalHeight);
      hips.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      chest.addRotationY(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.2F * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) + 1.4 - 1.7) * 0.025 * (double)globalHeight)) * limbSwingAmount);
      neck.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      neck.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) - 1.5) * 0.25 * (double)globalHeight - 0.2 * (double)globalHeight)) * limbSwingAmount
      );
      head.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) + 0.175 - 1.7) * 0.25 * (double)globalHeight + 0.2 * (double)globalHeight) * limbSwingAmount
      );
      leftThigh.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree) * limbSwingAmount);
      leftThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree - 0.2F) * limbSwingAmount);
      leftShin.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.7F * (double)globalDegree) * limbSwingAmount);
      leftAnkle.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.5) * -1.0 * (double)globalDegree - (double)(1.1F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree + (double)(1.8F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationX(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree + 0.2F) * limbSwingAmount);
      rightShin.addRotationX(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.7F * (double)globalDegree) * limbSwingAmount);
      rightAnkle.addRotationX(
         blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      rightFoot.addRotationX(
         blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 3.5) * -1.0 * (double)globalDegree - (double)(1.1F * globalDegree)) * limbSwingAmount
      );
      rightToesBack.addRotationX(
         blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree + (double)(1.8F * globalDegree)) * limbSwingAmount
      );
      rightToesBack.addRotationX(blend * (float)(-Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      leftArm.addRotationY(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.09 * (double)globalHeight)) * limbSwingAmount);
      leftArm.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.09 * (double)globalHeight) * limbSwingAmount);
      leftForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.05 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.09 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationZ(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.09 * (double)globalHeight)) * limbSwingAmount);
      rightForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.05 * (double)globalHeight) * limbSwingAmount);
   }

   private void walkForwardAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPositionY(blend * (float)(Math.cos((double)(limbSwing * speed)) * 1.5 * (double)globalHeight) * limbSwingAmount);
      hips.addRotationX(blend * -0.18F * limbSwingAmount * globalHeight);
      hips.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      chest.addRotationY(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.2F * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) + 1.4) * 0.025 * (double)globalHeight)) * limbSwingAmount);
      neck.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      neck.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed)) * 0.175 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) + 0.175) * 0.175 * (double)globalHeight + 0.18 * (double)globalHeight) * limbSwingAmount
      );
      leftThigh.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree) * limbSwingAmount);
      leftThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree - 0.15F) * limbSwingAmount);
      leftShin.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree) * limbSwingAmount);
      leftAnkle.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree - (double)(0.4F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      leftToesBack.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree + (double)(1.4F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree)) * limbSwingAmount);
      rightThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree + 0.15F) * limbSwingAmount);
      rightShin.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree)) * limbSwingAmount);
      rightAnkle.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree - (double)(0.1F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree + (double)(0.4F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      rightToesBack.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree - (double)(1.4F * globalDegree))) * limbSwingAmount
      );
      rightToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      leftArm.addRotationY(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      leftArm.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      leftForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationZ(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      rightForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
   }

   private void walkBackwardAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPositionY(blend * (float)(Math.cos((double)(limbSwing * speed)) * 1.5 * (double)globalHeight) * limbSwingAmount);
      hips.addRotationX(blend * 0.18F * limbSwingAmount * globalHeight);
      hips.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * 0.1F * (double)globalHeight) * limbSwingAmount);
      chest.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.2F * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) + 1.4) * 0.025 * (double)globalHeight)) * limbSwingAmount);
      neck.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * 0.1F * (double)globalHeight) * limbSwingAmount);
      neck.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed)) * 0.175 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) + 0.175) * 0.175 * (double)globalHeight - 0.18 * (double)globalHeight) * limbSwingAmount
      );
      leftThigh.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 1.5) * 0.55F * (double)globalDegree - 0.3 * (double)globalDegree) * limbSwingAmount
      );
      leftThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 1.5) * 0.1F * (double)globalDegree - 0.15F) * limbSwingAmount);
      leftShin.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.4) * -0.7F * (double)globalDegree) * limbSwingAmount);
      leftAnkle.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.4) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.5) * -1.3F * (double)globalDegree - (double)(0.4F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      leftToesBack.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 3.1) * 1.6F * (double)globalDegree + (double)(1.4F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 - 1.5) * 0.55F * (double)globalDegree + 0.3 * (double)globalDegree)) * limbSwingAmount
      );
      rightThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 - 1.5) * 0.1F * (double)globalDegree + 0.15F) * limbSwingAmount);
      rightShin.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.4) * -0.7F * (double)globalDegree)) * limbSwingAmount);
      rightAnkle.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.4) * 1.1F * (double)globalDegree - (double)(0.1F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 - 2.5) * -1.3F * (double)globalDegree + (double)(0.4F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      rightToesBack.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 - 3.1) * 1.6F * (double)globalDegree - (double)(1.4F * globalDegree))) * limbSwingAmount
      );
      rightToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      leftArm.addRotationY(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      leftArm.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      leftForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationZ(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      rightForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
   }

   private void walkLeftAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPositionY(blend * (float)(Math.cos((double)(limbSwing * speed)) * 1.5 * (double)globalHeight) * limbSwingAmount);
      hips.addRotationX(blend * -0.1F * limbSwingAmount * globalHeight);
      hips.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      hips.addRotationZ(blend * 0.08F * limbSwingAmount * globalHeight);
      chest.addRotationY(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.2F * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) + 1.4) * 0.025 * (double)globalHeight)) * limbSwingAmount);
      stomach.addRotationZ(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.4) * 0.02 * (double)globalHeight)) * limbSwingAmount);
      stomach.addRotationZ(blend * -((float)(Math.cos((double)(limbSwing * speed) - 0.5) * 0.02 * (double)globalHeight)) * limbSwingAmount);
      neck.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      neck.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed)) * 0.175 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) + 0.175) * 0.175 * (double)globalHeight + 0.1 * (double)globalHeight) * limbSwingAmount
      );
      head.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.4) * 0.02 * (double)globalHeight) * limbSwingAmount);
      head.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) - 0.5) * 0.02 * (double)globalHeight) * limbSwingAmount);
      head.addRotationZ(blend * -0.03F * limbSwingAmount * globalHeight);
      leftThigh.addRotationX(blend * -0.05F * limbSwingAmount * globalHeight);
      leftThigh.addRotationZ(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree + 0.05 * (double)globalDegree)) * limbSwingAmount
      );
      leftThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree - 0.15) * limbSwingAmount);
      leftShin.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree) * limbSwingAmount);
      leftAnkle.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree - (double)(0.6F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      leftFoot.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.4F * (double)globalDegree) * limbSwingAmount);
      leftFoot.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      leftToesBack.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree + (double)(1.4F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationX(blend * 0.05F * limbSwingAmount * globalHeight);
      rightThigh.addRotationZ(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree - 0.05 * (double)globalDegree) * limbSwingAmount
      );
      rightThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree + 0.15) * limbSwingAmount);
      rightShin.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree)) * limbSwingAmount);
      rightAnkle.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree - (double)(0.1F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree + (double)(0.6F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      rightFoot.addRotationY(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.4F * (double)globalDegree)) * limbSwingAmount);
      rightFoot.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      rightToesBack.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree - (double)(1.4F * globalDegree))) * limbSwingAmount
      );
      rightToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      leftShin.addRotationX(
         blend * -((float)(Math.pow(Math.cos((double)limbSwing * 0.25 * (double)speed - 0.6), 12.0) * 0.6F * (double)globalHeight)) * limbSwingAmount
      );
      leftAnkle.addRotationX(
         blend * (float)(Math.pow(Math.cos((double)limbSwing * 0.25 * (double)speed - 0.6), 12.0) * 0.6F * (double)globalHeight) * limbSwingAmount
      );
      leftArm.addRotationY(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      leftArm.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      leftForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationZ(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      rightForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
   }

   private void walkRightAnim(float blend, float limbSwing, float limbSwingAmount, float speed) {
      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone neck = this.getMowzieBone("neck");
      MowzieGeoBone hips = this.getMowzieBone("hips");
      MowzieGeoBone stomach = this.getMowzieBone("stomach");
      MowzieGeoBone chest = this.getMowzieBone("chest");
      MowzieGeoBone leftThigh = this.getMowzieBone("leftThigh");
      MowzieGeoBone leftShin = this.getMowzieBone("leftShin");
      MowzieGeoBone leftAnkle = this.getMowzieBone("leftAnkle");
      MowzieGeoBone leftFoot = this.getMowzieBone("leftFoot");
      MowzieGeoBone leftToesBack = this.getMowzieBone("leftToesBack");
      MowzieGeoBone rightThigh = this.getMowzieBone("rightThigh");
      MowzieGeoBone rightShin = this.getMowzieBone("rightShin");
      MowzieGeoBone rightAnkle = this.getMowzieBone("rightAnkle");
      MowzieGeoBone rightFoot = this.getMowzieBone("rightFoot");
      MowzieGeoBone rightToesBack = this.getMowzieBone("rightToesBack");
      MowzieGeoBone leftArm = this.getMowzieBone("leftArm");
      MowzieGeoBone leftForeArm = this.getMowzieBone("leftForeArm");
      MowzieGeoBone leftHand = this.getMowzieBone("leftHand");
      MowzieGeoBone rightArm = this.getMowzieBone("rightArm");
      MowzieGeoBone rightForeArm = this.getMowzieBone("rightForeArm");
      MowzieGeoBone rightHand = this.getMowzieBone("rightHand");
      float globalHeight = 1.5F;
      float globalDegree = 1.5F;
      hips.addPositionY(blend * (float)(Math.cos((double)(limbSwing * speed)) * 1.5 * (double)globalHeight) * limbSwingAmount);
      hips.addRotationX(blend * -0.1F * limbSwingAmount * globalHeight);
      hips.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      hips.addRotationZ(blend * -0.08F * limbSwingAmount * globalHeight);
      chest.addRotationY(blend * (float)(-Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.2F * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) + 1.4) * 0.025 * (double)globalHeight)) * limbSwingAmount);
      stomach.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.4) * 0.02 * (double)globalHeight) * limbSwingAmount);
      stomach.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) - 0.5) * 0.02 * (double)globalHeight) * limbSwingAmount);
      neck.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.0) * -0.1F * (double)globalHeight) * limbSwingAmount);
      neck.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed)) * 0.175 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) + 0.175) * 0.175 * (double)globalHeight + 0.1 * (double)globalHeight) * limbSwingAmount
      );
      head.addRotationZ(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.4) * 0.02 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationZ(blend * -((float)(Math.cos((double)(limbSwing * speed) - 0.5) * 0.02 * (double)globalHeight)) * limbSwingAmount);
      head.addRotationZ(blend * 0.03F * limbSwingAmount * globalHeight);
      leftThigh.addRotationX(blend * 0.05F * limbSwingAmount * globalHeight);
      leftThigh.addRotationZ(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree + 0.05 * (double)globalDegree) * limbSwingAmount
      );
      leftThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree - 0.15) * limbSwingAmount);
      leftShin.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree) * limbSwingAmount);
      leftAnkle.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree + (double)(0.1F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree - (double)(0.6F * globalDegree)) * limbSwingAmount
      );
      leftFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      leftFoot.addRotationY(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.4F * (double)globalDegree)) * limbSwingAmount);
      leftFoot.addRotationY(blend * -((float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree)) * limbSwingAmount);
      leftToesBack.addRotationX(
         blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree + (double)(1.4F * globalDegree)) * limbSwingAmount
      );
      leftToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightThigh.addRotationX(blend * -0.05F * limbSwingAmount * globalHeight);
      rightThigh.addRotationZ(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.55F * (double)globalDegree - 0.05 * (double)globalDegree)) * limbSwingAmount
      );
      rightThigh.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 1.5) * 0.1F * (double)globalDegree + 0.15) * limbSwingAmount);
      rightShin.addRotationX(blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * -0.7F * (double)globalDegree)) * limbSwingAmount);
      rightAnkle.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.4) * 1.1F * (double)globalDegree - (double)(0.1F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -1.3F * (double)globalDegree + (double)(0.6F * globalDegree))) * limbSwingAmount
      );
      rightFoot.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree) * limbSwingAmount);
      rightFoot.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) * 0.5 + 2.5) * -0.4F * (double)globalDegree) * limbSwingAmount);
      rightFoot.addRotationY(blend * -((float)(Math.cos((double)(limbSwing * speed * 1.0F) - 0.2) * -0.2F * (double)globalDegree)) * limbSwingAmount);
      rightToesBack.addRotationX(
         blend * -((float)(Math.cos((double)(limbSwing * speed) * 0.5 + 3.1) * 1.6F * (double)globalDegree - (double)(1.4F * globalDegree))) * limbSwingAmount
      );
      rightToesBack.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed * 1.0F) + 0.1) * 0.3F * (double)globalDegree) * limbSwingAmount);
      rightShin.addRotationX(
         blend
            * -((float)(Math.pow(Math.cos((double)limbSwing * 0.25 * (double)speed - 0.6 + (Math.PI / 2)), 12.0) * 0.6F * (double)globalHeight))
            * limbSwingAmount
      );
      rightAnkle.addRotationX(
         blend
            * (float)(Math.pow(Math.cos((double)limbSwing * 0.25 * (double)speed - 0.6 + (Math.PI / 2)), 12.0) * 0.6F * (double)globalHeight)
            * limbSwingAmount
      );
      leftArm.addRotationY(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      leftArm.addRotationZ(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      leftForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationY(blend * (float)(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight) * limbSwingAmount);
      rightArm.addRotationZ(blend * (float)(-(Math.cos((double)(limbSwing * speed) + 0.52) * 0.0707 * (double)globalHeight)) * limbSwingAmount);
      rightForeArm.addRotationX(blend * (float)(Math.cos((double)(limbSwing * speed) - 1.0) * 0.03 * (double)globalHeight) * limbSwingAmount);
   }
}
