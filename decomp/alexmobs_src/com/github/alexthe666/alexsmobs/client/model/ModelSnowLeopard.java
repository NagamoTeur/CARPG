package com.github.alexthe666.alexsmobs.client.model;

import com.github.alexthe666.alexsmobs.entity.EntitySnowLeopard;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;

public class ModelSnowLeopard extends AdvancedEntityModel<EntitySnowLeopard> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox tail1;
   private final AdvancedModelBox tail2;
   private final AdvancedModelBox tail3;
   private final AdvancedModelBox leg_front_left;
   private final AdvancedModelBox leg_front_right;
   private final AdvancedModelBox leg_back_left;
   private final AdvancedModelBox leg_back_right;
   private final AdvancedModelBox neck;
   private final AdvancedModelBox head;
   private final AdvancedModelBox whisker_left;
   private final AdvancedModelBox whisker_right;
   private final AdvancedModelBox ear_left;
   private final AdvancedModelBox ear_right;
   private final AdvancedModelBox snout;
   private ModelAnimator animator;

   public ModelSnowLeopard() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this, "root");
      this.root.setPos(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this, "body");
      this.body.setPos(0.0F, -11.0F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 32).addBox(-4.5F, 2.0F, -11.0F, 9.0F, 2.0F, 22.0F, 0.0F, false);
      this.body.setTextureOffset(0, 0).addBox(-4.5F, -7.0F, -11.0F, 9.0F, 9.0F, 22.0F, 0.0F, false);
      this.tail1 = new AdvancedModelBox(this, "tail1");
      this.tail1.setPos(0.0F, -6.5F, 11.0F);
      this.body.addChild(this.tail1);
      this.setRotationAngle(this.tail1, -0.9599F, 0.0F, 0.0F);
      this.tail1.setTextureOffset(41, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 17.0F, 0.0F, false);
      this.tail2 = new AdvancedModelBox(this, "tail2");
      this.tail2.setPos(0.0F, 2.9F, 17.0F);
      this.tail1.addChild(this.tail2);
      this.setRotationAngle(this.tail2, 0.7854F, 0.0F, 0.0F);
      this.tail2.setTextureOffset(52, 52).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 11.0F, 0.1F, false);
      this.tail3 = new AdvancedModelBox(this, "tail3");
      this.tail3.setPos(0.0F, -0.2F, 11.1F);
      this.tail2.addChild(this.tail3);
      this.setRotationAngle(this.tail3, 0.6545F, 0.0F, 0.0F);
      this.tail3.setTextureOffset(41, 32).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 11.0F, 0.2F, false);
      this.leg_front_left = new AdvancedModelBox(this, "leg_front_left");
      this.leg_front_left.setPos(3.0F, 1.0F, -8.0F);
      this.body.addChild(this.leg_front_left);
      this.leg_front_left.setTextureOffset(0, 32).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, false);
      this.leg_front_right = new AdvancedModelBox(this, "leg_front_right");
      this.leg_front_right.setPos(-3.0F, 1.0F, -8.0F);
      this.body.addChild(this.leg_front_right);
      this.leg_front_right.setTextureOffset(0, 32).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 13.0F, 4.0F, 0.0F, true);
      this.leg_back_left = new AdvancedModelBox(this, "leg_back_left");
      this.leg_back_left.setPos(3.0F, 0.0F, 8.0F);
      this.body.addChild(this.leg_back_left);
      this.leg_back_left.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);
      this.leg_back_right = new AdvancedModelBox(this, "leg_back_right");
      this.leg_back_right.setPos(-3.0F, 0.0F, 8.0F);
      this.body.addChild(this.leg_back_right);
      this.leg_back_right.setTextureOffset(0, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, true);
      this.neck = new AdvancedModelBox(this, "neck");
      this.neck.setPos(0.0F, -4.0F, -11.0F);
      this.body.addChild(this.neck);
      this.setRotationAngle(this.neck, -0.3054F, 0.0F, 0.0F);
      this.neck.setTextureOffset(27, 57).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 5.0F, 0.1F, false);
      this.head = new AdvancedModelBox(this, "head");
      this.head.setPos(0.0F, 0.1F, -3.1F);
      this.neck.addChild(this.head);
      this.setRotationAngle(this.head, 0.3054F, 0.0F, 0.0F);
      this.head.setTextureOffset(0, 57).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 7.0F, 0.0F, false);
      this.whisker_left = new AdvancedModelBox(this, "whisker_left");
      this.whisker_left.setPos(3.0F, 2.0F, -4.0F);
      this.head.addChild(this.whisker_left);
      this.setRotationAngle(this.whisker_left, 0.0F, -0.8727F, 0.0F);
      this.whisker_left.setTextureOffset(17, 17).addBox(0.0F, -3.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, false);
      this.whisker_right = new AdvancedModelBox(this, "whisker_right");
      this.whisker_right.setPos(-3.0F, 2.0F, -4.0F);
      this.head.addChild(this.whisker_right);
      this.setRotationAngle(this.whisker_right, 0.0F, 0.8727F, 0.0F);
      this.whisker_right.setTextureOffset(17, 17).addBox(-2.0F, -3.0F, 0.0F, 2.0F, 4.0F, 0.0F, 0.0F, true);
      this.ear_left = new AdvancedModelBox(this, "ear_left");
      this.ear_left.setPos(3.0F, -3.0F, -2.0F);
      this.head.addChild(this.ear_left);
      this.ear_left.setTextureOffset(41, 7).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
      this.ear_right = new AdvancedModelBox(this, "ear_right");
      this.ear_right.setPos(-3.0F, -3.0F, -2.0F);
      this.head.addChild(this.ear_right);
      this.ear_right.setTextureOffset(41, 7).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, 0.0F, true);
      this.snout = new AdvancedModelBox(this, "snout");
      this.snout.setPos(0.0F, 0.1F, -6.4F);
      this.head.addChild(this.snout);
      this.setRotationAngle(this.snout, 0.1745F, 0.0F, 0.0F);
      this.snout.setTextureOffset(41, 0).addBox(-2.0F, 0.0F, -2.2F, 4.0F, 3.0F, 3.0F, 0.0F, false);
      this.updateDefaultPose();
      this.animator = new ModelAnimator();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.whisker_left,
         this.whisker_right,
         this.snout,
         this.root,
         this.body,
         this.neck,
         this.head,
         this.ear_left,
         this.ear_right,
         this.leg_back_left,
         this.leg_back_right,
         this.leg_front_left,
         new AdvancedModelBox[]{this.leg_front_right, this.tail1, this.tail2, this.tail3}
      );
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.update(entity);
      this.animator.setAnimation(EntitySnowLeopard.ANIMATION_ATTACK_R);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.body, 0.0F, (float)Math.toRadians(-10.0), 0.0F);
      this.animator.rotate(this.neck, 0.0F, (float)Math.toRadians(-10.0), (float)Math.toRadians(-10.0));
      this.animator.rotate(this.leg_front_right, (float)Math.toRadians(25.0), (float)Math.toRadians(-20.0), 0.0F);
      this.animator.move(this.leg_front_right, 0.0F, 1.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.rotate(this.neck, 0.0F, 0.0F, (float)Math.toRadians(0.0));
      this.animator.rotate(this.leg_front_right, (float)Math.toRadians(-90.0), (float)Math.toRadians(-30.0), 0.0F);
      this.animator.move(this.leg_front_right, 0.0F, 1.0F, -2.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(5);
      this.animator.setAnimation(EntitySnowLeopard.ANIMATION_ATTACK_L);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.body, 0.0F, (float)Math.toRadians(10.0), 0.0F);
      this.animator.rotate(this.neck, 0.0F, (float)Math.toRadians(10.0), (float)Math.toRadians(10.0));
      this.animator.rotate(this.leg_front_left, (float)Math.toRadians(25.0), (float)Math.toRadians(20.0), 0.0F);
      this.animator.move(this.leg_front_left, 0.0F, 1.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(5);
      this.animator.rotate(this.neck, 0.0F, 0.0F, (float)Math.toRadians(0.0));
      this.animator.rotate(this.leg_front_left, (float)Math.toRadians(-90.0), (float)Math.toRadians(30.0), 0.0F);
      this.animator.move(this.leg_front_left, 0.0F, 1.0F, -2.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(5);
   }

   public void setupAnim(EntitySnowLeopard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float walkSpeed = 0.7F;
      float walkDegree = 0.6F;
      float idleSpeed = 0.1F;
      float idleDegree = 0.1F;
      float runProgress = 5.0F * limbSwingAmount;
      float partialTick = Minecraft.m_91087_().m_91296_();
      float stalkProgress = entity.prevSneakProgress + (entity.sneakProgress - entity.prevSneakProgress) * partialTick;
      float tackleProgress = entity.prevTackleProgress + (entity.tackleProgress - entity.prevTackleProgress) * partialTick;
      float sitProgress = entity.prevSitProgress + (entity.sitProgress - entity.prevSitProgress) * partialTick;
      float sleepProgress = entity.prevSleepProgress + (entity.sleepProgress - entity.prevSleepProgress) * partialTick;
      float sitSleepProgress = Math.max(sitProgress, sleepProgress);
      this.swing(this.tail1, idleSpeed, idleDegree * 2.0F, false, 2.0F, 0.0F, ageInTicks, 1.0F - limbSwingAmount);
      this.swing(this.tail2, idleSpeed, idleDegree * 1.5F, false, 2.0F, 0.0F, ageInTicks, 1.0F - limbSwingAmount);
      this.flap(this.tail3, idleSpeed * 1.2F, idleDegree * 1.5F, false, 2.0F, 0.0F, ageInTicks, 1.0F - limbSwingAmount);
      this.swing(this.tail3, idleSpeed * 1.2F, idleDegree * 1.5F, false, 2.0F, 0.0F, ageInTicks, 1.0F - limbSwingAmount);
      this.walk(this.neck, idleSpeed * 0.3F, idleDegree, false, 0.0F, 0.0F, ageInTicks, 1.0F);
      this.walk(this.head, idleSpeed * 0.3F, -idleDegree, false, 0.5F, 0.0F, ageInTicks, 1.0F);
      this.walk(this.leg_front_right, walkSpeed, walkDegree * 1.1F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_front_right, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.leg_front_left, walkSpeed, walkDegree * 1.1F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_front_left, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.leg_back_right, walkSpeed, walkDegree * 1.1F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_back_right, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.leg_back_left, walkSpeed, walkDegree * 1.1F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_back_left, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.bob(this.body, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      AdvancedModelBox[] tailBoxes = new AdvancedModelBox[]{this.tail1, this.tail2, this.tail3};
      this.chainSwing(tailBoxes, walkSpeed, walkDegree * 0.5F, -2.5, limbSwing, limbSwingAmount);
      this.progressRotationPrev(this.tail1, runProgress, (float)Math.toRadians(40.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.tail2, runProgress, (float)Math.toRadians(-20.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.tail3, runProgress, (float)Math.toRadians(-20.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.body, stalkProgress, (float)Math.toRadians(15.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_back_left, stalkProgress, (float)Math.toRadians(-15.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_back_right, stalkProgress, (float)Math.toRadians(-15.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_front_left, stalkProgress, (float)Math.toRadians(-15.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_front_right, stalkProgress, (float)Math.toRadians(-15.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.neck, stalkProgress, (float)Math.toRadians(5.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.head, stalkProgress, (float)Math.toRadians(-20.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.tail1, stalkProgress, (float)Math.toRadians(-20.0), 0.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_back_left, stalkProgress, 0.0F, 2.1F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_back_right, stalkProgress, 0.0F, 2.1F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_front_left, stalkProgress, 0.0F, -1.9F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_front_right, stalkProgress, 0.0F, -1.9F, 0.0F, 5.0F);
      this.progressRotationPrev(this.body, tackleProgress, (float)Math.toRadians(-45.0), 0.0F, 0.0F, 3.0F);
      this.progressRotationPrev(this.neck, tackleProgress, (float)Math.toRadians(6.0), 0.0F, 0.0F, 3.0F);
      this.progressRotationPrev(this.head, tackleProgress, (float)Math.toRadians(45.0), 0.0F, 0.0F, 3.0F);
      this.progressRotationPrev(this.tail1, tackleProgress, (float)Math.toRadians(80.0), 0.0F, 0.0F, 3.0F);
      this.progressRotationPrev(this.leg_front_right, tackleProgress, (float)Math.toRadians(-25.0), 0.0F, (float)Math.toRadians(45.0), 3.0F);
      this.progressRotationPrev(this.leg_front_left, tackleProgress, (float)Math.toRadians(-25.0), 0.0F, (float)Math.toRadians(-45.0), 3.0F);
      this.progressRotationPrev(this.leg_back_left, tackleProgress, (float)Math.toRadians(-15.0), 0.0F, (float)Math.toRadians(-25.0), 3.0F);
      this.progressRotationPrev(this.leg_back_right, tackleProgress, (float)Math.toRadians(-15.0), 0.0F, (float)Math.toRadians(25.0), 3.0F);
      this.progressPositionPrev(this.body, tackleProgress, 0.0F, -5.0F, 0.0F, 3.0F);
      this.progressPositionPrev(this.leg_front_left, tackleProgress, 1.0F, 2.0F, 0.0F, 3.0F);
      this.progressPositionPrev(this.leg_front_right, tackleProgress, -1.0F, 2.0F, 0.0F, 3.0F);
      this.progressPositionPrev(this.tail1, tackleProgress, 0.0F, 0.0F, -1.0F, 3.0F);
      float tailAngle = entity.m_19879_() % 2 == 0 ? 1.0F : -1.0F;
      this.progressRotationPrev(this.leg_back_left, sitSleepProgress, (float)Math.toRadians(-90.0), (float)Math.toRadians(-20.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_back_right, sitSleepProgress, (float)Math.toRadians(-90.0), (float)Math.toRadians(20.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_front_left, sitSleepProgress, (float)Math.toRadians(-90.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_front_right, sitSleepProgress, (float)Math.toRadians(-90.0), 0.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.body, sitSleepProgress, 0.0F, 5.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_front_right, sitSleepProgress, 0.0F, 2.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_front_left, sitSleepProgress, 0.0F, 2.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_back_right, sitSleepProgress, 0.0F, 2.8F, -0.5F, 5.0F);
      this.progressPositionPrev(this.leg_back_left, sitSleepProgress, 0.0F, 2.8F, -0.5F, 5.0F);
      this.progressRotationPrev(this.tail1, sitProgress, (float)Math.toRadians(20.0), (float)Math.toRadians((double)(tailAngle * 30.0F)), 0.0F, 5.0F);
      this.progressRotationPrev(this.tail2, sitProgress, (float)Math.toRadians(-5.0), (float)Math.toRadians((double)(tailAngle * 50.0F)), 0.0F, 5.0F);
      this.progressRotationPrev(
         this.tail3,
         sitProgress,
         (float)Math.toRadians(10.0),
         (float)Math.toRadians((double)(tailAngle * 20.0F)),
         (float)Math.toRadians((double)(tailAngle * 20.0F)),
         5.0F
      );
      this.progressRotationPrev(this.neck, sleepProgress, (float)Math.toRadians(20.0), tailAngle * (float)Math.toRadians(50.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.head, sleepProgress, (float)Math.toRadians(5.0), tailAngle * (float)Math.toRadians(20.0), 0.0F, 5.0F);
      this.progressPositionPrev(this.head, sleepProgress, tailAngle * 0.5F, -1.0F, 1.0F, 5.0F);
      this.progressPositionPrev(this.neck, sleepProgress, 0.0F, 1.0F, -1.0F, 5.0F);
      this.progressRotationPrev(this.tail1, sleepProgress, (float)Math.toRadians(20.0), (float)Math.toRadians((double)(tailAngle * -60.0F)), 0.0F, 5.0F);
      this.progressRotationPrev(
         this.tail2,
         sleepProgress,
         (float)Math.toRadians(10.0),
         (float)Math.toRadians((double)(tailAngle * -70.0F)),
         (float)Math.toRadians((double)(tailAngle * -50.0F)),
         5.0F
      );
      this.progressRotationPrev(
         this.tail3,
         sleepProgress,
         (float)Math.toRadians(-30.0),
         (float)Math.toRadians((double)(tailAngle * -50.0F)),
         (float)Math.toRadians((double)(tailAngle * -30.0F)),
         5.0F
      );
      if (sleepProgress <= 0.0F) {
         this.faceTarget(netHeadYaw, headPitch, 2.0F, new AdvancedModelBox[]{this.neck, this.head});
      }
   }

   public void m_7695_(
      PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (this.f_102610_) {
         float f = 1.45F;
         this.head.setScale(f, f, f);
         this.head.setShouldScaleChildren(true);
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
         matrixStackIn.m_85837_(0.0, 1.5, 0.0);
         this.parts().forEach(p_228292_8_ -> p_228292_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
         matrixStackIn.m_85849_();
         this.head.setScale(1.0F, 1.0F, 1.0F);
      } else {
         matrixStackIn.m_85836_();
         this.parts().forEach(p_228290_8_ -> p_228290_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha));
         matrixStackIn.m_85849_();
      }
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
