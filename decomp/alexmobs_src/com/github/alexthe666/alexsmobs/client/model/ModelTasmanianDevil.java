package com.github.alexthe666.alexsmobs.client.model;

import com.github.alexthe666.alexsmobs.entity.EntityTasmanianDevil;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;

public class ModelTasmanianDevil extends AdvancedEntityModel<EntityTasmanianDevil> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox tail;
   private final AdvancedModelBox head;
   private final AdvancedModelBox ear_left;
   private final AdvancedModelBox ear_right;
   private final AdvancedModelBox upper_jaw;
   private final AdvancedModelBox lower_jaw;
   private final AdvancedModelBox arm_left;
   private final AdvancedModelBox arm_right;
   private final AdvancedModelBox leg_left;
   private final AdvancedModelBox leg_right;
   private ModelAnimator animator;

   public ModelTasmanianDevil() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this, "root");
      this.root.setPos(0.0F, 24.0F, 0.0F);
      this.body = new AdvancedModelBox(this, "body");
      this.body.setPos(0.0F, -5.5F, 0.0F);
      this.root.addChild(this.body);
      this.body.setTextureOffset(0, 0).addBox(-2.5F, -2.5F, -6.0F, 5.0F, 5.0F, 12.0F, 0.0F, false);
      this.tail = new AdvancedModelBox(this, "tail");
      this.tail.setPos(0.0F, -0.5F, 6.0F);
      this.body.addChild(this.tail);
      this.tail.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this, "head");
      this.head.setPos(-0.5F, -1.5F, -6.0F);
      this.body.addChild(this.head);
      this.head.setTextureOffset(15, 18).addBox(-1.5F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
      this.ear_left = new AdvancedModelBox(this, "ear_left");
      this.ear_left.setPos(2.5F, -1.0F, -1.0F);
      this.head.addChild(this.ear_left);
      this.ear_left.setTextureOffset(2, 9).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, false);
      this.ear_right = new AdvancedModelBox(this, "ear_right");
      this.ear_right.setPos(-1.5F, -2.0F, -1.0F);
      this.head.addChild(this.ear_right);
      this.ear_right.setTextureOffset(2, 9).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, 0.0F, true);
      this.upper_jaw = new AdvancedModelBox(this, "upper_jaw");
      this.upper_jaw.setPos(0.5F, 0.0F, -4.0F);
      this.head.addChild(this.upper_jaw);
      this.upper_jaw.setTextureOffset(23, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.lower_jaw = new AdvancedModelBox(this, "lower_jaw");
      this.lower_jaw.setPos(0.5F, 1.0F, -4.0F);
      this.head.addChild(this.lower_jaw);
      this.lower_jaw.setTextureOffset(23, 6).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
      this.arm_left = new AdvancedModelBox(this, "arm_left");
      this.arm_left.setPos(1.4F, 1.5F, -4.0F);
      this.body.addChild(this.arm_left);
      this.arm_left.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
      this.arm_right = new AdvancedModelBox(this, "arm_right");
      this.arm_right.setPos(-1.4F, 1.5F, -4.0F);
      this.body.addChild(this.arm_right);
      this.arm_right.setTextureOffset(0, 18).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, true);
      this.leg_left = new AdvancedModelBox(this, "leg_left");
      this.leg_left.setPos(1.4F, 2.5F, 4.5F);
      this.body.addChild(this.leg_left);
      this.leg_left.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, false);
      this.leg_right = new AdvancedModelBox(this, "leg_right");
      this.leg_right.setPos(-1.4F, 2.5F, 4.5F);
      this.body.addChild(this.leg_right);
      this.leg_right.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 3.0F, 0.0F, true);
      this.updateDefaultPose();
      this.animator = ModelAnimator.create();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.body,
         this.head,
         this.ear_right,
         this.ear_left,
         this.lower_jaw,
         this.upper_jaw,
         this.tail,
         this.arm_left,
         this.arm_right,
         this.leg_left,
         this.leg_right,
         new AdvancedModelBox[0]
      );
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(EntityTasmanianDevil.ANIMATION_ATTACK);
      this.animator.startKeyframe(3);
      this.animator.rotate(this.head, (float)Math.toRadians(10.0), 0.0F, 0.0F);
      this.animator.move(this.lower_jaw, 0.0F, 0.0F, 0.5F);
      this.animator.rotate(this.lower_jaw, (float)Math.toRadians(15.0), 0.0F, 0.0F);
      this.animator.move(this.head, 0.0F, -1.0F, 1.5F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.move(this.lower_jaw, 0.0F, 0.0F, 1.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.upper_jaw, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.lower_jaw, (float)Math.toRadians(65.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(3);
      this.animator.endKeyframe();
      this.animator.setAnimation(EntityTasmanianDevil.ANIMATION_HOWL);
      this.animator.startKeyframe(10);
      this.animator.move(this.lower_jaw, 0.0F, 0.0F, 1.0F);
      this.animator.rotate(this.upper_jaw, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.lower_jaw, (float)Math.toRadians(45.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-45.0), (float)Math.toRadians(45.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.startKeyframe(20);
      this.animator.move(this.lower_jaw, 0.0F, 0.0F, 1.0F);
      this.animator.rotate(this.upper_jaw, (float)Math.toRadians(-5.0), 0.0F, 0.0F);
      this.animator.rotate(this.lower_jaw, (float)Math.toRadians(45.0), 0.0F, 0.0F);
      this.animator.rotate(this.head, (float)Math.toRadians(-45.0), (float)Math.toRadians(-45.0), 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(5);
      this.animator.resetKeyframe(5);
   }

   public void setupAnim(EntityTasmanianDevil entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float walkSpeed = 1.0F;
      float walkDegree = 0.5F;
      float idleSpeed = 0.1F;
      float idleDegree = 0.1F;
      float stillProgress = 1.0F - limbSwingAmount;
      float partialTick = Minecraft.m_91087_().m_91296_();
      float baskProgress0 = entity.prevBaskProgress + (entity.baskProgress - entity.prevBaskProgress) * partialTick;
      float sitProgress = entity.prevSitProgress + (entity.sitProgress - entity.prevSitProgress) * partialTick;
      float baskProgress = Math.max(0.0F, baskProgress0 - sitProgress);
      this.progressRotationPrev(this.tail, stillProgress, (float)Math.toRadians(-23.0), 0.0F, 0.0F, 1.0F);
      this.progressRotationPrev(this.body, sitProgress, (float)Math.toRadians(-25.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.arm_right, sitProgress, (float)Math.toRadians(25.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.arm_left, sitProgress, (float)Math.toRadians(25.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.tail, sitProgress, (float)Math.toRadians(40.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.head, sitProgress, (float)Math.toRadians(25.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_right, sitProgress, (float)Math.toRadians(-40.0), (float)Math.toRadians(40.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_left, sitProgress, (float)Math.toRadians(-40.0), (float)Math.toRadians(-40.0), 0.0F, 5.0F);
      this.progressPositionPrev(this.arm_right, sitProgress, 0.0F, 1.5F, 1.2F, 5.0F);
      this.progressPositionPrev(this.arm_left, sitProgress, 0.0F, 1.5F, 1.2F, 5.0F);
      this.progressPositionPrev(this.leg_left, sitProgress, 1.0F, -1.5F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_right, sitProgress, -1.0F, -1.5F, 0.0F, 5.0F);
      this.progressRotationPrev(this.arm_right, baskProgress, (float)Math.toRadians(-80.0), (float)Math.toRadians(40.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.arm_left, baskProgress, (float)Math.toRadians(-80.0), (float)Math.toRadians(-40.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_right, baskProgress, (float)Math.toRadians(80.0), (float)Math.toRadians(-20.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.leg_left, baskProgress, (float)Math.toRadians(80.0), (float)Math.toRadians(20.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.tail, baskProgress, (float)Math.toRadians(10.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.head, baskProgress, (float)Math.toRadians(10.0), 0.0F, 0.0F, 5.0F);
      this.progressRotationPrev(this.ear_right, baskProgress, 0.0F, (float)Math.toRadians(40.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.ear_left, baskProgress, 0.0F, (float)Math.toRadians(-40.0), 0.0F, 5.0F);
      this.progressPositionPrev(this.body, baskProgress, 0.0F, 3.0F, 0.0F, 5.0F);
      this.progressPositionPrev(this.head, baskProgress, 0.0F, 1.2F, 0.0F, 5.0F);
      this.progressPositionPrev(this.arm_right, baskProgress, 0.0F, -0.2F, -1.2F, 5.0F);
      this.progressPositionPrev(this.arm_left, baskProgress, 0.0F, -0.2F, -1.2F, 5.0F);
      this.progressPositionPrev(this.leg_left, baskProgress, 1.0F, -1.5F, 0.0F, 5.0F);
      this.progressPositionPrev(this.leg_right, baskProgress, -1.0F, -1.5F, 0.0F, 5.0F);
      this.walk(this.arm_right, walkSpeed, walkDegree * 1.1F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.arm_right, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.arm_left, walkSpeed, walkDegree * 1.1F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.arm_left, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.leg_right, walkSpeed, walkDegree * 1.1F, false, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_right, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.walk(this.leg_left, walkSpeed, walkDegree * 1.1F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.leg_left, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.swing(this.body, walkSpeed, walkDegree * 0.6F, true, 1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.swing(this.tail, walkSpeed, walkDegree * 0.6F, false, 1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.swing(this.head, walkSpeed, walkDegree * 0.6F, false, 1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.body, walkSpeed, walkDegree * 0.05F, true, 0.0F, 0.0F, limbSwing, limbSwingAmount);
      this.walk(this.tail, walkSpeed, walkDegree * 0.6F, true, -1.0F, 0.0F, limbSwing, limbSwingAmount);
      this.bob(this.body, walkSpeed, walkDegree, false, limbSwing, limbSwingAmount);
      this.bob(this.head, walkSpeed, walkDegree * 0.6F, false, limbSwing, limbSwingAmount);
      this.swing(this.ear_right, walkSpeed, walkDegree * 0.6F, false, -1.0F, 0.3F, limbSwing, limbSwingAmount);
      this.swing(this.ear_left, walkSpeed, walkDegree * 0.6F, true, -1.0F, 0.3F, limbSwing, limbSwingAmount);
      this.swing(this.tail, idleSpeed, idleDegree * 0.9F, false, 1.0F, 0.0F, ageInTicks, 1.0F);
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
   }

   public void m_7695_(
      PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      if (this.f_102610_) {
         float f = 1.65F;
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

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox advancedModelBox, float x, float y, float z) {
      advancedModelBox.rotateAngleX = x;
      advancedModelBox.rotateAngleY = y;
      advancedModelBox.rotateAngleZ = z;
   }
}
