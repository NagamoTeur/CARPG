package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Interpolations;
import com.obscuria.obscureapi.api.hekate.HekateLib.Mode;
import com.obscuria.obscureapi.api.hekate.HekateLib.mod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelTorturedSoul extends EntityModel<TorturedSoul> {
   public final ModelPart main;
   public final ModelPart body;
   public final ModelPart heart;
   public final ModelPart head;
   public final ModelPart nose;
   public final ModelPart leftArm;
   public final ModelPart rightArm;
   public final ModelPart leftLeg;
   public final ModelPart rightLeg;
   public final ModelPart leftArmLower;
   public final ModelPart rightArmLower;
   public final ModelPart leftLegLower;
   public final ModelPart rightLegLower;

   public ModelTorturedSoul(ModelPart root) {
      this.main = root.m_171324_("main");
      this.body = this.main.m_171324_("body");
      this.heart = this.body.m_171324_("heart");
      this.head = this.body.m_171324_("head");
      this.nose = this.head.m_171324_("nose");
      this.leftArm = this.body.m_171324_("left_arm");
      this.rightArm = this.body.m_171324_("right_arm");
      this.leftLeg = this.main.m_171324_("left_leg");
      this.rightLeg = this.main.m_171324_("right_leg");
      this.leftArmLower = this.leftArm.m_171324_("left_arm_bottom");
      this.rightArmLower = this.rightArm.m_171324_("right_arm_bottom");
      this.leftLegLower = this.leftLeg.m_171324_("left_leg_bottom");
      this.rightLegLower = this.rightLeg.m_171324_("right_leg_bottom");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 6.0F, 150.0F));
      PartDefinition body = main.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 20)
            .m_171488_(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(-0.02F))
            .m_171514_(0, 39)
            .m_171488_(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 6.0F, -150.0F)
      );
      PartDefinition heart = body.m_171599_(
         "heart",
         CubeListBuilder.m_171558_().m_171514_(44, 21).m_171488_(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -8.0F, 0.0F)
      );
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 39)
            .m_171488_(-4.5F, -13.0F, -4.5F, 9.0F, 10.0F, 9.0F, new CubeDeformation(-0.4F)),
         PartPose.m_171419_(0.0F, -12.0F, 0.0F)
      );
      PartDefinition nose = head.m_171599_(
         "nose",
         CubeListBuilder.m_171558_().m_171514_(24, 0).m_171488_(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, -4.0F)
      );
      PartDefinition left_arm = body.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(38, 0)
            .m_171480_()
            .m_171488_(0.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(44, 30)
            .m_171480_()
            .m_171488_(0.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F))
            .m_171555_(false),
         PartPose.m_171423_(4.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_arm_bottom = left_arm.m_171599_(
         "left_arm_bottom",
         CubeListBuilder.m_171558_().m_171514_(38, 11).m_171480_().m_171488_(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false),
         PartPose.m_171419_(2.0F, 4.0F, 1.0F)
      );
      PartDefinition right_arm = body.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(38, 0)
            .m_171488_(-4.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 30)
            .m_171488_(-4.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(-4.0F, -10.0F, 0.0F)
      );
      PartDefinition right_arm_bottom = right_arm.m_171599_(
         "right_arm_bottom",
         CubeListBuilder.m_171558_().m_171514_(38, 11).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(-2.0F, 4.0F, 0.0F)
      );
      PartDefinition left_leg = main.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 18).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(2.0F, 6.0F, -150.0F)
      );
      PartDefinition left_leg_bottom = left_leg.m_171599_(
         "left_leg_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 29).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).m_171555_(false),
         PartPose.m_171419_(0.0F, 6.0F, 0.0F)
      );
      PartDefinition right_leg = main.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 18).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, 6.0F, -150.0F)
      );
      PartDefinition right_leg_bottom = right_leg.m_171599_(
         "right_leg_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 29).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(0.0F, 6.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setupAnim(TorturedSoul soul, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(
         new ModelPart[]{
            this.main,
            this.body,
            this.heart,
            this.head,
            this.nose,
            this.leftArm,
            this.rightArm,
            this.leftLeg,
            this.rightLeg,
            this.leftArmLower,
            this.rightArmLower,
            this.leftLegLower,
            this.rightLegLower
         }
      );
      HekateLib.push(ageInTicks, 0.1F, mod.idle(limbSwingAmount, 5.0F), Mode.DEFINITION)
         .keyframe(this.main, k -> k.xRot(-0.3F, -0.5F))
         .keyframe(this.body, k -> k.xRot(-3.0F, -10.0F))
         .keyframe(this.head, k -> k.xRot(3.0F, 16.0F))
         .keyframe(this.rightArm, k -> k.xRot(12.0F, 35.0F, -0.2F).zRot(-3.0F, 10.0F, -0.2F))
         .keyframe(this.rightArmLower, k -> k.xRot(18.0F, 69.0F, -0.4F))
         .keyframe(this.leftArm, k -> k.xRot(10.0F, 15.0F, -0.2F).zRot(3.0F, -10.0F, -0.2F))
         .keyframe(this.leftArmLower, k -> k.xRot(15.0F, 30.0F, -0.4F))
         .keyframe(this.rightLeg, k -> k.rotation(15.0F, 30.0F, -0.5F, -4.0F, 0.5F, 4.0F))
         .keyframe(this.rightLegLower, k -> k.xRot(-30.0F, -44.0F))
         .keyframe(this.leftLeg, k -> k.rotation(15.0F, 30.0F, -0.5F, 4.0F, 0.5F, -4.0F))
         .keyframe(this.leftLegLower, k -> k.xRot(-30.0F, -44.0F));
      HekateLib.push(ageInTicks, 0.4F, mod.move(limbSwingAmount, 5.0F), Mode.ADDITION)
         .keyframe(this.main, k -> k.xRot(0.5F, -0.3F, 2.0F, 0.0F))
         .keyframe(this.body, k -> k.xRot(-3.0F, -3.0F, 2.0F, -0.1F))
         .keyframe(this.head, k -> k.xRot(-3.0F, -3.0F, 2.0F, -0.2F))
         .keyframe(this.rightArm, k -> k.xRot(24.0F, -12.0F, -0.1F).zRot(-3.0F, 10.0F, -0.1F))
         .keyframe(this.rightArmLower, k -> k.xRot(20.0F, 34.0F, -0.2F))
         .keyframe(this.leftArm, k -> k.xRot(-24.0F, -12.0F, -0.1F).zRot(3.0F, -10.0F, -0.1F))
         .keyframe(this.leftArmLower, k -> k.xRot(-20.0F, 34.0F, -0.2F))
         .keyframe(this.rightLeg, k -> k.xRot(-30.0F, 14.0F, -0.1F))
         .keyframe(this.rightLegLower, k -> k.xRot(-34.0F, -34.0F, -0.2F))
         .keyframe(this.leftLeg, k -> k.xRot(30.0F, 14.0F, -0.1F))
         .keyframe(this.leftLegLower, k -> k.xRot(34.0F, -34.0F, -0.2F));
      HekateLib.push(8, 12, Interpolations.EASE_OUT_ELASTIC, Interpolations.EASE_OUT_BACK)
         .pose(
            0,
            20,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body, k -> k.rotation(-27.0F, 0.0F, 0.0F))
                  .keyframe(this.leftArm, k -> k.rotation(125.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(125.0F, 0.0F, 0.0F))
                  .keyframe(this.leftArmLower, k -> k.rotation(12.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArmLower, k -> k.rotation(12.0F, 0.0F, 0.0F))
         )
         .animate(soul.ATTACK);
      this.heart.f_104203_ = ageInTicks / 13.0F;
      this.heart.f_104204_ = ageInTicks / 9.0F;
      this.heart.f_104205_ = ageInTicks / 5.0F;
      this.head.f_104204_ = this.head.f_104204_ + mod.head(netHeadYaw, 0.5F);
      this.body.f_104204_ = this.body.f_104204_ + mod.head(netHeadYaw, 0.5F);
   }
}
