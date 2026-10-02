package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.RigUtils;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelSculptor extends MowzieAnimatedGeoModel<EntitySculptor> {
   public ResourceLocation getModelResource(EntitySculptor object) {
      return new ResourceLocation("mowziesmobs", "geo/sculptor.geo.json");
   }

   public ResourceLocation getTextureResource(EntitySculptor object) {
      return new ResourceLocation("mowziesmobs", "textures/entity/sculptor.png");
   }

   public ResourceLocation getAnimationResource(EntitySculptor object) {
      return new ResourceLocation("mowziesmobs", "animations/sculptor.animation.json");
   }

   public void codeAnimations(EntitySculptor entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
      IBone head = this.getBone("head");
      IBone handClosedL = this.getBone("handClosedLeft");
      IBone handClosedR = this.getBone("handClosedRight");
      IBone handOpenL = this.getBone("handOpenLeft");
      IBone handOpenR = this.getBone("handOpenRight");
      IBone backCloth = this.getBone("clothBack");
      EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
      head.setRotationX(head.getRotationX() + extraData.headPitch * (float) (Math.PI / 180.0));
      head.setRotationY(head.getRotationY() + extraData.netHeadYaw * (float) (Math.PI / 180.0));
      handClosedL.setHidden(entity.handLOpen);
      handClosedR.setHidden(entity.handROpen);
      handOpenL.setHidden(!entity.handLOpen);
      handOpenR.setHidden(!entity.handROpen);
      backCloth.setHidden(false);
      this.beadsCorrections(entity);
      this.skirtCorrections(entity);
      this.staffRendering(entity);
   }

   private void beadsCorrections(EntitySculptor entity) {
      Map<MowzieGeoBone, Vec3> beadsDirections = new HashMap<>();
      beadsDirections.put(this.getMowzieBone("bead1"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead2"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead3"), new Vec3(0.0, -1.0, 0.0));
      beadsDirections.put(this.getMowzieBone("bead4"), new Vec3(0.0, -0.5, 0.25));
      beadsDirections.put(this.getMowzieBone("bead5"), new Vec3(0.0, -0.5, 0.25));
      beadsDirections.put(this.getMowzieBone("bead6"), new Vec3(0.0, -0.5, 1.0));
      beadsDirections.put(this.getMowzieBone("bead7"), new Vec3(0.0, -0.5, 1.0));
      beadsDirections.put(this.getMowzieBone("bead8"), new Vec3(0.0, -0.25, 0.75));
      beadsDirections.put(this.getMowzieBone("bead9"), new Vec3(0.0, -0.25, 0.75));
      IBone headJoint = this.getBone("head_joint");
      Vec3 headPos = new Vec3((double)headJoint.getPivotX(), (double)headJoint.getPivotY(), (double)headJoint.getPivotZ());
      IBone head = this.getBone("head");
      Vec3 headDir = new Vec3(0.0, 0.0, -1.0).m_82541_();
      headDir = headDir.m_82524_(head.getRotationY()).m_82496_(head.getRotationX());

      for (Entry<MowzieGeoBone, Vec3> beadDir : beadsDirections.entrySet()) {
         MowzieGeoBone bead = beadDir.getKey();
         Vec3 beadPos = new Vec3((double)bead.getPivotX(), (double)bead.getPivotY(), (double)bead.getPivotZ());
         Vec3 dir = beadPos.m_82546_(headPos).m_82541_().m_82542_(1.0, -1.0, 1.0);
         double dot = dir.m_82526_(headDir);
         dot = Math.pow(Math.max(dot, 0.0), 3.5);
         Vec3 moveDir = beadDir.getValue().m_82541_();
         bead.addPosition(moveDir.m_82490_(dot * 3.0));
      }
   }

   private void skirtCorrections(EntitySculptor entity) {
      MowzieGeoBone headJoint = this.getMowzieBone("head_joint");
      MowzieGeoBone thighR = this.getMowzieBone("thighRight");
      MowzieGeoBone thighJointR = this.getMowzieBone("thighJointRight");
      MowzieGeoBone thighJointL = this.getMowzieBone("thighJointLeft");
      MowzieGeoBone calfR = this.getMowzieBone("calfRight");
      MowzieGeoBone thighL = this.getMowzieBone("thighLeft");
      MowzieGeoBone calfL = this.getMowzieBone("calfLeft");
      MowzieGeoBone footR = this.getMowzieBone("footRight");
      MowzieGeoBone skirtBack = this.getMowzieBone("skirtBack");
      MowzieGeoBone skirtFront = this.getMowzieBone("skirtFront");
      MowzieGeoBone skirtL = this.getMowzieBone("skirtLeft");
      MowzieGeoBone skirtR = this.getMowzieBone("skirtRight");
      MowzieGeoBone skirtJointL = this.getMowzieBone("skirtJointLeft");
      MowzieGeoBone skirtJointR = this.getMowzieBone("skirtJointRight");
      MowzieGeoBone skirtJoint2L = this.getMowzieBone("skirtJoint2Left");
      MowzieGeoBone skirtJoint2R = this.getMowzieBone("skirtJoint2Right");
      MowzieGeoBone skirtEndR = this.getMowzieBone("skirtEndRight");
      MowzieGeoBone skirtEndL = this.getMowzieBone("skirtEndLeft");
      MowzieGeoBone skirtLocFrontR = this.getMowzieBone("skirtFrontLocRight");
      MowzieGeoBone skirtLocFrontL = this.getMowzieBone("skirtFrontLocLeft");
      MowzieGeoBone skirtLocBackR = this.getMowzieBone("skirtBackLocRight");
      MowzieGeoBone skirtLocBackL = this.getMowzieBone("skirtBackLocLeft");
      headJoint.setHidden(false);
      Vec3 thighToKneeR = Vec3(calfR.getModelPosition()).m_82546_(Vec3(thighR.getModelPosition())).m_82541_();
      Vec3 thighToKneeL = Vec3(calfL.getModelPosition()).m_82546_(Vec3(thighL.getModelPosition())).m_82541_();
      skirtEndL.addPosition(-0.2F, -1.5F, 0.0F);
      skirtEndR.addPosition(0.2F, -1.5F, 0.0F);
      Vec3 thighToSkirtEndR = Vec3(skirtEndR.getModelPosition()).m_82546_(Vec3(thighR.getModelPosition())).m_82541_();
      Vec3 thighToSkirtEndL = Vec3(skirtEndL.getModelPosition()).m_82546_(Vec3(thighL.getModelPosition())).m_82541_();
      float rightDot = (float)thighToKneeR.m_82526_(new Vec3(0.0, -1.0, 0.0));
      rightDot = (float)Math.pow((double)Math.max(rightDot, 0.0F), 3.0);
      float leftDot = (float)thighToKneeL.m_82526_(new Vec3(0.0, -1.0, 0.0));
      leftDot = (float)Math.pow((double)Math.max(leftDot, 0.0F), 3.0);
      skirtJointR.addPosition(Math.max(-0.9F * rightDot, -0.7F), 0.0F, Math.max(-0.7F * rightDot, -0.5F));
      skirtJointL.addPosition(-Math.max(-0.9F * leftDot, -0.7F), 0.0F, Math.max(-0.7F * leftDot, -0.5F));
      Quaternion rightRot = RigUtils.betweenVectors(thighToSkirtEndR, thighToKneeR);
      Quaternion leftRot = RigUtils.betweenVectors(thighToSkirtEndL, thighToKneeL);
      Matrix4f rightMat = new Matrix4f(rightRot);
      Matrix4f leftMat = new Matrix4f(leftRot);
      skirtJoint2R.setModelRotationMat(rightMat);
      skirtJoint2L.setModelRotationMat(leftMat);
      Vec3 average = thighToKneeL.m_82549_(thighToKneeR).m_82490_(2.0).m_82542_(0.0, 1.0, 1.0).m_82541_();
      float angleAv = (float)Mth.m_14136_(average.m_7098_(), average.m_7094_());
      skirtBack.setRotationX(skirtBack.getRotationX() - angleAv + 3.48F);
      skirtFront.setRotationX(skirtFront.getRotationX() - Math.min(angleAv, -2.0F) + 3.48F);
      Vec3 skirtFrontDiff = Vec3(skirtLocFrontL.getModelPosition()).m_82546_(Vec3(skirtLocFrontR.getModelPosition()));
      skirtFront.setScaleX(Math.max((float)(0.3F + 0.15F * skirtFrontDiff.m_82553_()), 0.4F));
      Vec3 skirtBackDiff = Vec3(skirtLocBackL.getModelPosition()).m_82546_(Vec3(skirtLocBackR.getModelPosition()));
      skirtBack.setScaleX((float)(0.15F + 0.1F * skirtBackDiff.m_82553_()));
      float angleF = (float)Mth.m_14136_(skirtFrontDiff.m_82541_().m_7094_(), skirtFrontDiff.m_82541_().m_7096_());
      if ((double)angleF < 0.001 || (double)angleF > 3.141) {
         angleF = 0.0F;
      }

      skirtFront.setRotationY(angleF * 0.6F);
      skirtFront.addPosition(0.5F * (float)Vec3(skirtLocFrontR.getModelPosition()).m_82549_(skirtFrontDiff.m_82490_(0.5)).m_7096_(), 0.0F, 0.0F);
      float angleB = (float)Mth.m_14136_(skirtBackDiff.m_82541_().m_7094_(), skirtBackDiff.m_82541_().m_7096_());
      skirtBack.setRotationY(angleB * 0.5F);
      skirtBack.addPosition(0.5F * (float)Vec3(skirtLocBackR.getModelPosition()).m_82549_(skirtBackDiff.m_82490_(0.5)).m_7096_(), 0.0F, 0.0F);
      float bothDots = (float)Math.pow((double)(rightDot * leftDot), 1.0);
      float f = Math.min(1.0F, bothDots * 2.0F);
      skirtR.addRotation(0.0F, Mth.m_14036_(angleF, -0.5F, 0.5F) * (1.0F - f) - bothDots * 0.4F, 0.0F);
      skirtL.addRotation(0.0F, Mth.m_14036_(angleF, -0.5F, 0.5F) * (1.0F - f) + bothDots * 0.4F, 0.0F);
      MowzieGeoBone frontCloth = this.getMowzieBone("clothFront");
      MowzieGeoBone frontCloth2 = this.getMowzieBone("clothFront2");
      frontCloth.setRotation(skirtFront.getRotation());
      Matrix4f mat = frontCloth.getModelRotationMat().m_27658_();
      mat.m_27657_();
      frontCloth2.setModelRotationMat(mat);
   }

   private void staffRendering(EntitySculptor entity) {
      MowzieGeoBone itemHandLeft = this.getMowzieBone("itemHandLeft");
      MowzieGeoBone itemHandRight = this.getMowzieBone("itemHandRight");
      MowzieGeoBone backItem = this.getMowzieBone("backItem");
      itemHandLeft.setHidden(true);
      itemHandRight.setHidden(true);
      backItem.setHidden(true);
      MowzieGeoBone staffController = this.getMowzieBone("staffController");
      switch ((int)staffController.getPositionX()) {
         case -1:
            itemHandRight.setHidden(false);
            break;
         case 0:
            backItem.setHidden(false);
            break;
         case 1:
            itemHandLeft.setHidden(false);
      }
   }

   private static Vec3 Vec3(Vector3d vec) {
      return new Vec3(vec.f_86214_, vec.f_86215_, vec.f_86216_);
   }
}
