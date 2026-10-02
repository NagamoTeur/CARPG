package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Elite_Draugr_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Elite_Draugr_Entity;
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

public class Elite_Draugr_Model extends HierarchicalModel<Elite_Draugr_Entity> implements ArmedModel {
   private final ModelPart everything;
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart front_cloth1;
   private final ModelPart front_cloth2;
   private final ModelPart back_cloth1;
   private final ModelPart back_cloth2;
   private final ModelPart waist;
   private final ModelPart chest;
   private final ModelPart neck;
   private final ModelPart head;
   private final ModelPart maw;
   private final ModelPart l_arm;
   private final ModelPart cube_r1;
   private final ModelPart l_arm2;
   private final ModelPart r_arm;
   private final ModelPart cube_r2;
   private final ModelPart r_arm2;
   private final ModelPart right_leg;
   private final ModelPart left_leg;

   public Elite_Draugr_Model(ModelPart root) {
      this.everything = root;
      this.root = this.everything.m_171324_("root");
      this.body = this.root.m_171324_("body");
      this.front_cloth1 = this.body.m_171324_("front_cloth1");
      this.front_cloth2 = this.front_cloth1.m_171324_("front_cloth2");
      this.back_cloth1 = this.body.m_171324_("back_cloth1");
      this.back_cloth2 = this.back_cloth1.m_171324_("back_cloth2");
      this.waist = this.body.m_171324_("waist");
      this.chest = this.waist.m_171324_("chest");
      this.neck = this.chest.m_171324_("neck");
      this.head = this.neck.m_171324_("head");
      this.maw = this.head.m_171324_("maw");
      this.l_arm = this.chest.m_171324_("l_arm");
      this.cube_r1 = this.l_arm.m_171324_("cube_r1");
      this.l_arm2 = this.l_arm.m_171324_("l_arm2");
      this.r_arm = this.chest.m_171324_("r_arm");
      this.cube_r2 = this.r_arm.m_171324_("cube_r2");
      this.r_arm2 = this.r_arm.m_171324_("r_arm2");
      this.right_leg = this.root.m_171324_("right_leg");
      this.left_leg = this.root.m_171324_("left_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition root = partdefinition.m_171599_("root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition body = root.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(52, 0).m_171488_(-5.0F, -4.0F, -2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -12.0F, 0.0F)
      );
      PartDefinition front_cloth1 = body.m_171599_(
         "front_cloth1",
         CubeListBuilder.m_171558_().m_171514_(60, 8).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -2.0F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition front_cloth2 = front_cloth1.m_171599_(
         "front_cloth2",
         CubeListBuilder.m_171558_().m_171514_(56, 44).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 4.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition back_cloth1 = body.m_171599_(
         "back_cloth1",
         CubeListBuilder.m_171558_().m_171514_(38, 16).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 2.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition back_cloth2 = back_cloth1.m_171599_(
         "back_cloth2",
         CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition waist = body.m_171599_(
         "waist",
         CubeListBuilder.m_171558_().m_171514_(80, 50).m_171488_(-1.5F, -8.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -4.0F, 1.0F)
      );
      PartDefinition chest = waist.m_171599_(
         "chest",
         CubeListBuilder.m_171558_()
            .m_171514_(80, 60)
            .m_171488_(-1.5F, -8.0F, -1.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 50)
            .m_171488_(-6.0F, -8.0F, -4.0F, 12.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(96, 0)
            .m_171488_(-6.0F, -8.0F, -4.0F, 12.0F, 10.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(0.0F, -8.0F, 0.0F)
      );
      PartDefinition neck = chest.m_171599_(
         "neck",
         CubeListBuilder.m_171558_().m_171514_(16, 18).m_171488_(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -8.0F, 0.0F)
      );
      PartDefinition head = neck.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 48)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(96, 112)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F))
            .m_171514_(0, 64)
            .m_171488_(-11.0F, -15.0F, 0.0F, 9.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(80, 0)
            .m_171488_(4.0F, -12.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.4F)),
         PartPose.m_171419_(0.0F, -2.0F, -1.0F)
      );
      PartDefinition maw = head.m_171599_(
         "maw",
         CubeListBuilder.m_171558_().m_171514_(60, 60).m_171488_(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, 0.5F, -3.0F, 0.2102F, 0.0504F, -0.3014F)
      );
      PartDefinition l_arm = chest.m_171599_(
         "l_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(72, 32)
            .m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 76)
            .m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)),
         PartPose.m_171419_(7.0F, -6.0F, -1.0F)
      );
      PartDefinition cube_r1 = l_arm.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -2.0F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, -2.0F, 0.0F, 0.0F, 0.0F, -0.2618F)
      );
      PartDefinition l_arm2 = l_arm.m_171599_(
         "l_arm2",
         CubeListBuilder.m_171558_()
            .m_171514_(52, 69)
            .m_171488_(-2.0F, 2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(68, 69)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 6.0F, 0.0F)
      );
      PartDefinition r_arm = chest.m_171599_(
         "r_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(72, 20)
            .m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 50)
            .m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)),
         PartPose.m_171419_(-7.0F, -6.0F, -1.0F)
      );
      PartDefinition cube_r2 = r_arm.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 12)
            .m_171488_(-4.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 11)
            .m_171488_(-5.0F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 9)
            .m_171488_(0.0F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-2.0F, -4.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -5.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-3.0F, -2.0F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, -2.0F, 0.0F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition r_arm2 = r_arm.m_171599_(
         "r_arm2",
         CubeListBuilder.m_171558_()
            .m_171514_(68, 69)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 64)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(0.0F, 6.0F, 0.0F)
      );
      PartDefinition right_leg = root.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(56, 28).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-3.0F, -12.0F, 0.0F)
      );
      PartDefinition left_leg = root.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(56, 12).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.0F, -12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(Elite_Draugr_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (limbSwing != 0.0F) {
         this.animateWalk(Elite_Draugr_Animation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
      }

      this.m_233385_(entity.getAnimationState("idle"), Elite_Draugr_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("re_load"), Elite_Draugr_Animation.RE_LOAD, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("shoot"), Elite_Draugr_Animation.SHOOT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("shoot2"), Elite_Draugr_Animation.SHOOT2, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("swing"), Elite_Draugr_Animation.SWING, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("attack"), Elite_Draugr_Animation.ATTACK, ageInTicks, 1.5F);
      this.m_233385_(entity.getAnimationState("attack2"), Elite_Draugr_Animation.ATTACK2, ageInTicks, 1.5F);
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
      this.waist.m_104299_(poseStack);
      this.chest.m_104299_(poseStack);
      if (arm == HumanoidArm.RIGHT) {
         this.r_arm.m_104299_(poseStack);
         this.r_arm2.m_104299_(poseStack);
         poseStack.m_85837_(0.0, 0.0, 0.0);
      } else {
         this.l_arm.m_104299_(poseStack);
         this.l_arm2.m_104299_(poseStack);
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
