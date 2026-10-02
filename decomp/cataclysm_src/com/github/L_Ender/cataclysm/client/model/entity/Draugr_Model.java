package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Draugar_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Draugr_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

public class Draugr_Model extends HierarchicalModel<Draugr_Entity> implements ArmedModel {
   private final ModelPart everything;
   private final ModelPart root;
   private final ModelPart right_leg;
   private final ModelPart left_leg;
   private final ModelPart body;
   private final ModelPart left_arm;
   private final ModelPart left_arm_r1;
   private final ModelPart left_arm_r2;
   private final ModelPart right_arm;
   private final ModelPart right_arm_r1;
   private final ModelPart right_arm_r2;
   private final ModelPart head;
   private final ModelPart maw;
   private final ModelPart body_r1;

   public Draugr_Model(ModelPart root) {
      this.everything = root;
      this.root = this.everything.m_171324_("root");
      this.right_leg = this.root.m_171324_("right_leg");
      this.left_leg = this.root.m_171324_("left_leg");
      this.body = this.root.m_171324_("body");
      this.left_arm = this.body.m_171324_("left_arm");
      this.left_arm_r1 = this.left_arm.m_171324_("left_arm_r1");
      this.left_arm_r2 = this.left_arm.m_171324_("left_arm_r2");
      this.right_arm = this.body.m_171324_("right_arm");
      this.right_arm_r1 = this.right_arm.m_171324_("right_arm_r1");
      this.right_arm_r2 = this.right_arm.m_171324_("right_arm_r2");
      this.head = this.body.m_171324_("head");
      this.maw = this.head.m_171324_("maw");
      this.body_r1 = this.maw.m_171324_("body_r1");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition root = partdefinition.m_171599_("root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition right_leg = root.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 0)
            .m_171488_(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(-2.0F, -12.0F, 0.1F)
      );
      PartDefinition left_leg = root.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(76, 0)
            .m_171480_()
            .m_171488_(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171555_(false),
         PartPose.m_171419_(2.0F, -12.0F, 0.1F)
      );
      PartDefinition body = root.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 0)
            .m_171488_(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 36)
            .m_171488_(-4.0F, -3.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(0.0F, -12.0F, 0.0F, -0.0873F, -0.2182F, 0.0F)
      );
      PartDefinition left_arm = body.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(76, 0)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171555_(false)
            .m_171514_(0, 48)
            .m_171480_()
            .m_171488_(-1.0F, 3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(5.0F, -9.0F, 0.0F, 0.1309F, 0.0F, -0.0436F)
      );
      PartDefinition left_arm_r1 = left_arm.m_171599_(
         "left_arm_r1",
         CubeListBuilder.m_171558_().m_171514_(58, 17).m_171480_().m_171488_(-1.0F, -2.0F, -3.5F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(1.9763F, -0.7164F, 0.0F, 0.0F, 0.0F, -0.2182F)
      );
      PartDefinition left_arm_r2 = left_arm.m_171599_(
         "left_arm_r2",
         CubeListBuilder.m_171558_().m_171514_(48, 23).m_171480_().m_171488_(-1.0F, -2.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(1.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.2182F)
      );
      PartDefinition right_arm = body.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 0)
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171514_(0, 48)
            .m_171488_(-1.0F, 2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(-5.0F, -8.0F, 0.0F, 0.1309F, 0.0F, 0.0436F)
      );
      PartDefinition right_arm_r1 = right_arm.m_171599_(
         "right_arm_r1",
         CubeListBuilder.m_171558_().m_171514_(48, 18).m_171488_(-1.0F, -3.0F, -1.5F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.7526F, -1.3329F, 1.5F, 0.0F, 0.0F, 0.2182F)
      );
      PartDefinition right_arm_r2 = right_arm.m_171599_(
         "right_arm_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(58, 23)
            .m_171488_(0.0F, -2.0F, -3.5F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 23)
            .m_171488_(-1.0F, -2.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.2182F)
      );
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(92, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(58, 36)
            .m_171488_(0.0F, -16.0F, 0.0F, 10.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 55)
            .m_171480_()
            .m_171488_(-10.0F, -13.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -12.0F, -1.0F, 0.5079F, 0.1287F, -0.137F)
      );
      PartDefinition maw = head.m_171599_("maw", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -2.5F, -1.0F, -0.0873F, 0.0F, 0.2182F));
      PartDefinition body_r1 = maw.m_171599_(
         "body_r1",
         CubeListBuilder.m_171558_().m_171514_(32, 6).m_171488_(-3.0F, 0.0F, -4.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 64);
   }

   public void setupAnim(Draugr_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (limbSwing != 0.0F) {
         this.animateWalk(Draugar_Animation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
      }

      this.m_233385_(entity.getAnimationState("idle"), Draugar_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("attack"), Draugar_Animation.ATTACK, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("attack2"), Draugar_Animation.ATTACK2, ageInTicks, 1.0F);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ = xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ = yRot * (float) (Math.PI / 180.0);
   }

   public void m_6002_(HumanoidArm arm, PoseStack poseStack) {
      this.root.m_104299_(poseStack);
      this.body.m_104299_(poseStack);
      if (arm == HumanoidArm.RIGHT) {
         this.right_arm.m_104299_(poseStack);
         poseStack.m_85837_(0.0, 0.0, 0.0);
      } else {
         this.left_arm.m_104299_(poseStack);
         poseStack.m_85837_(0.0, 0.0, 0.0);
      }
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
