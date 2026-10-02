package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Kobolediator_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Kobolediator_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class Kobolediator_Model extends HierarchicalModel<Kobolediator_Entity> {
   private final ModelPart root;
   private final ModelPart everything;
   private final ModelPart mid_root;
   private final ModelPart pelvis;
   private final ModelPart lower_body;
   private final ModelPart body;
   private final ModelPart right_shoulder;
   private final ModelPart right_arm;
   private final ModelPart right_front_arm;
   private final ModelPart golden_greatsword;
   private final ModelPart left_shoulder;
   private final ModelPart left_arm;
   private final ModelPart left_front_arm;
   private final ModelPart head;
   private final ModelPart head_cube1;
   private final ModelPart head_cube2;
   private final ModelPart head_cube3;
   private final ModelPart head_cube4;
   private final ModelPart right_horn;
   private final ModelPart left_horn;
   private final ModelPart jaw;
   private final ModelPart tail1;
   private final ModelPart tail2;
   private final ModelPart legs;
   private final ModelPart right_leg;
   private final ModelPart right_front_leg;
   private final ModelPart left_leg;
   private final ModelPart left_front_leg;

   public Kobolediator_Model(ModelPart root) {
      this.root = root;
      this.everything = this.root.m_171324_("everything");
      this.mid_root = this.everything.m_171324_("mid_root");
      this.pelvis = this.mid_root.m_171324_("pelvis");
      this.lower_body = this.pelvis.m_171324_("lower_body");
      this.body = this.lower_body.m_171324_("body");
      this.right_shoulder = this.body.m_171324_("right_shoulder");
      this.right_arm = this.right_shoulder.m_171324_("right_arm");
      this.right_front_arm = this.right_arm.m_171324_("right_front_arm");
      this.golden_greatsword = this.right_front_arm.m_171324_("golden_greatsword");
      this.left_shoulder = this.body.m_171324_("left_shoulder");
      this.left_arm = this.left_shoulder.m_171324_("left_arm");
      this.left_front_arm = this.left_arm.m_171324_("left_front_arm");
      this.head = this.body.m_171324_("head");
      this.head_cube1 = this.head.m_171324_("head_cube1");
      this.head_cube2 = this.head.m_171324_("head_cube2");
      this.head_cube3 = this.head.m_171324_("head_cube3");
      this.head_cube4 = this.head.m_171324_("head_cube4");
      this.right_horn = this.head.m_171324_("right_horn");
      this.left_horn = this.head.m_171324_("left_horn");
      this.jaw = this.head.m_171324_("jaw");
      this.tail1 = this.pelvis.m_171324_("tail1");
      this.tail2 = this.tail1.m_171324_("tail2");
      this.legs = this.mid_root.m_171324_("legs");
      this.right_leg = this.legs.m_171324_("right_leg");
      this.right_front_leg = this.right_leg.m_171324_("right_front_leg");
      this.left_leg = this.legs.m_171324_("left_leg");
      this.left_front_leg = this.left_leg.m_171324_("left_front_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition everything = partdefinition.m_171599_("everything", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 25.0F, -1.0F));
      PartDefinition mid_root = everything.m_171599_("mid_root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition pelvis = mid_root.m_171599_(
         "pelvis",
         CubeListBuilder.m_171558_().m_171514_(94, 72).m_171488_(-9.0F, -3.0F, -6.0513F, 18.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -22.0F, 0.0F, 0.1745F, 0.1309F, 0.0F)
      );
      PartDefinition lower_body = pelvis.m_171599_(
         "lower_body",
         CubeListBuilder.m_171558_()
            .m_171514_(86, 0)
            .m_171488_(-6.2168F, -14.0F, -15.0F, 14.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 146)
            .m_171488_(-2.2168F, -14.0F, -3.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.7832F, 0.0F, 8.9487F)
      );
      PartDefinition body = lower_body.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-12.2168F, -21.0F, -19.0F, 24.0F, 21.0F, 19.0F, new CubeDeformation(0.0F))
            .m_171514_(126, 130)
            .m_171488_(-3.2168F, -21.0F, -3.0F, 6.0F, 21.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 40)
            .m_171488_(-10.2168F, -21.0F, -21.0F, 4.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(5.7832F, -21.0F, -21.0F, 4.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 40)
            .m_171488_(-13.2168F, -21.0F, -19.0F, 26.0F, 7.0F, 19.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(1.0F, -14.0F, 0.0F, 0.1372F, -0.3027F, -0.0411F)
      );
      PartDefinition right_shoulder = body.m_171599_(
         "right_shoulder",
         CubeListBuilder.m_171558_().m_171514_(0, 66).m_171488_(-16.0F, -4.0F, -9.0F, 17.0F, 15.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-13.0F, -20.0F, -8.0F, -0.6109F, 0.2618F, 0.2182F)
      );
      PartDefinition right_arm = right_shoulder.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_().m_171514_(94, 124).m_171488_(-5.0F, -1.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.0F, 9.0F, 2.0F, -0.4904F, 0.0334F, 0.0769F)
      );
      PartDefinition right_front_arm = right_arm.m_171599_(
         "right_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 100)
            .m_171488_(-6.025F, 0.0F, -6.5F, 9.0F, 22.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(94, 93)
            .m_171488_(-8.025F, 2.0F, -8.5F, 8.0F, 18.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(67, 0)
            .m_171488_(1.0F, 16.0F, -1.5F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 69)
            .m_171488_(1.0F, 17.0F, 1.5F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 69)
            .m_171488_(1.0F, 17.0F, -4.5F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 13.0F, 2.0F, -1.5708F, 0.0F, 0.0F)
      );
      PartDefinition golden_greatsword = right_front_arm.m_171599_(
         "golden_greatsword",
         CubeListBuilder.m_171558_()
            .m_171514_(80, 146)
            .m_171488_(-2.2168F, -12.0F, -2.1F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(72, 72)
            .m_171488_(-1.7168F, -78.0F, -4.1F, 3.0F, 66.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(71, 40)
            .m_171488_(-2.2168F, -12.0F, 1.9F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 66)
            .m_171488_(-2.2168F, -12.0F, -7.1F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, 18.9F, 1.0F, 1.3963F, 0.0F, 0.0F)
      );
      PartDefinition left_shoulder = body.m_171599_(
         "left_shoulder",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 66)
            .m_171480_()
            .m_171488_(-1.0F, -4.0F, -9.0F, 17.0F, 15.0F, 19.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(12.5663F, -20.0F, -8.0F, 0.0436F, -0.0873F, -0.1309F)
      );
      PartDefinition left_arm = left_shoulder.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(94, 124)
            .m_171480_()
            .m_171488_(-3.0F, -1.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(5.0F, 9.0F, 2.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_front_arm = left_arm.m_171599_(
         "left_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 100)
            .m_171480_()
            .m_171488_(-2.975F, 0.0F, -6.5F, 9.0F, 22.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(94, 93)
            .m_171480_()
            .m_171488_(0.025F, 2.0F, -8.5F, 8.0F, 18.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(53, 69)
            .m_171480_()
            .m_171488_(-5.0F, 17.0F, -4.5F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(67, 0)
            .m_171480_()
            .m_171488_(-5.0F, 16.0F, -1.5F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(53, 69)
            .m_171480_()
            .m_171488_(-5.0F, 17.0F, 1.5F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 13.0F, 2.0F, -0.829F, 0.0F, 0.0F)
      );
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(24, 119).m_171488_(-4.2168F, -3.0F, -10.0F, 10.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -30.0F, -9.0F, 0.0F, 0.1309F, 0.0F)
      );
      PartDefinition head_cube1 = head.m_171599_(
         "head_cube1",
         CubeListBuilder.m_171558_().m_171514_(36, 100).m_171488_(0.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.5663F, -2.0F, -2.0F, 0.1616F, 0.1866F, -0.0568F)
      );
      PartDefinition head_cube2 = head.m_171599_(
         "head_cube2",
         CubeListBuilder.m_171558_().m_171514_(62, 38).m_171488_(1.0F, -6.0F, -12.0F, 6.0F, 6.0F, 28.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.2168F, -3.0F, -2.0F, 0.48F, 0.0F, 0.0F)
      );
      PartDefinition head_cube3 = head.m_171599_(
         "head_cube3",
         CubeListBuilder.m_171558_().m_171514_(125, 113).m_171488_(-6.8F, -5.0F, -8.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, -2.0F, 0.1616F, -0.1866F, 0.0568F)
      );
      PartDefinition head_cube4 = head.m_171599_(
         "head_cube4",
         CubeListBuilder.m_171558_().m_171514_(102, 49).m_171488_(-3.0F, -34.0F, -23.0F, 9.0F, 7.0F, 10.0F, new CubeDeformation(-0.01F)),
         PartPose.m_171423_(-0.7168F, 30.0F, 5.0F, 0.0436F, 0.0F, 0.0F)
      );
      PartDefinition right_horn = head.m_171599_(
         "right_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 105)
            .m_171488_(-9.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(148, 40)
            .m_171488_(-9.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(129, 0)
            .m_171488_(-9.2168F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, -5.0F, -3.0F)
      );
      PartDefinition left_horn = head.m_171599_(
         "left_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 52)
            .m_171488_(4.2168F, -9.0F, 4.0513F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(96, 146)
            .m_171488_(3.2168F, -9.0F, -1.9487F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(123, 93)
            .m_171488_(-2.7832F, -3.0F, -1.9487F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.5663F, -5.0F, -3.0F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_().m_171514_(102, 29).m_171488_(-2.7168F, -4.0F, -12.0F, 7.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 6.0F, -6.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition tail1 = pelvis.m_171599_(
         "tail1",
         CubeListBuilder.m_171558_().m_171514_(130, 54).m_171488_(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -0.1231F, 11.3724F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition tail2 = tail1.m_171599_(
         "tail2",
         CubeListBuilder.m_171558_().m_171514_(67, 0).m_171488_(-2.0F, -1.1888F, -1.1585F, 2.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.0F, -0.5F, 10.0F, 0.4799F, 0.0F, 0.0F)
      );
      PartDefinition legs = mid_root.m_171599_("legs", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -22.0F, 0.0F));
      PartDefinition right_leg = legs.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(145, 70).m_171488_(-3.2168F, -2.0F, -3.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-6.7832F, 4.0F, 0.9487F, -0.1295F, 0.5275F, 0.3306F)
      );
      PartDefinition right_front_leg = right_leg.m_171599_(
         "right_front_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(137, 22)
            .m_171488_(-2.2168F, 0.0F, -1.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 62)
            .m_171488_(-1.2168F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 62)
            .m_171488_(3.7832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 62)
            .m_171488_(1.2832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.975F, 8.0F, -2.0F, 0.3927F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = legs.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(28, 138).m_171488_(-3.7832F, -2.0F, -3.0F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.7832F, 3.0F, 0.9487F, -0.1295F, -0.5275F, -0.3306F)
      );
      PartDefinition left_front_leg = left_leg.m_171599_(
         "left_front_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 131)
            .m_171488_(-4.7832F, 0.0F, -1.0F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 62)
            .m_171480_()
            .m_171488_(1.2168F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(53, 62)
            .m_171480_()
            .m_171488_(-1.2832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(53, 62)
            .m_171480_()
            .m_171488_(-3.7832F, 8.0F, -5.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.975F, 8.0F, -2.0F, 0.3927F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(Kobolediator_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entity.getAttackState() != 6 && !entity.isSleep()) {
         this.animateWalk(Kobolediator_Animation.WALK, limbSwing, limbSwingAmount, 1.0F, 4.0F);
      }

      this.m_233385_(entity.getAnimationState("idle"), Kobolediator_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sleep"), Kobolediator_Animation.SLEEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("awake"), Kobolediator_Animation.AWAKE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sword1"), Kobolediator_Animation.SWORD1, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sword2"), Kobolediator_Animation.SWORD2, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge"), Kobolediator_Animation.CHARGE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge_prepare"), Kobolediator_Animation.CHARGE_PREPARE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge_end"), Kobolediator_Animation.CHARGE_END, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("death"), Kobolediator_Animation.DEATH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("block"), Kobolediator_Animation.BLOCK, ageInTicks, 1.0F);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ += xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ = yRot * (float) (Math.PI / 180.0);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
