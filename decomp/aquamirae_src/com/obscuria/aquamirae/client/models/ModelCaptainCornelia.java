package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.obscureapi.api.hekate.Animation;
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
import net.minecraft.world.entity.HumanoidArm;

public class ModelCaptainCornelia extends EntityModel<CaptainCornelia> {
   public final ModelPart main;
   public final ModelPart bodyTop;
   public final ModelPart bodyTop2;
   public final ModelPart bodyBottom;
   public final ModelPart head;
   public final ModelPart rightBooby;
   public final ModelPart leftBooby;
   public final ModelPart rightArm;
   public final ModelPart leftArm;
   public final ModelPart rightLeg;
   public final ModelPart leftLeg;
   public final ModelPart rightArmBottom;
   public final ModelPart leftArmBottom;
   public final ModelPart rightLegBottom;
   public final ModelPart leftLegBottom;
   public final ModelPart item;
   public final ModelPart ten1;
   public final ModelPart ten1_1;
   public final ModelPart ten1_2;
   public final ModelPart ten1_3;
   public final ModelPart ten1_4;
   public final ModelPart ten2;
   public final ModelPart ten2_1;
   public final ModelPart ten2_2;
   public final ModelPart ten2_3;
   public final ModelPart ten2_4;
   public final ModelPart ten3;
   public final ModelPart ten3_1;
   public final ModelPart ten3_2;
   public final ModelPart ten3_3;
   public final ModelPart ten3_4;

   public ModelCaptainCornelia(ModelPart root) {
      this.main = root.m_171324_("main");
      this.bodyTop = this.main.m_171324_("body_top_Z").m_171324_("body_top");
      this.bodyTop2 = this.bodyTop.m_171324_("body_top2");
      this.bodyBottom = this.main.m_171324_("body_bottom_Z").m_171324_("body_bottom");
      this.head = this.bodyTop2.m_171324_("head");
      this.rightBooby = this.bodyTop2.m_171324_("right_booby");
      this.leftBooby = this.bodyTop2.m_171324_("left_booby");
      this.rightArm = this.bodyTop2.m_171324_("right_arm");
      this.leftArm = this.bodyTop2.m_171324_("left_arm");
      this.rightArmBottom = this.rightArm.m_171324_("right_arm_bottom");
      this.leftArmBottom = this.leftArm.m_171324_("left_arm_bottom");
      this.rightLeg = this.bodyBottom.m_171324_("right_leg");
      this.leftLeg = this.bodyBottom.m_171324_("left_leg");
      this.rightLegBottom = this.rightLeg.m_171324_("right_leg_bottom");
      this.leftLegBottom = this.leftLeg.m_171324_("left_leg_bottom");
      this.item = this.rightArmBottom.m_171324_("bone");
      this.ten1 = this.bodyTop2.m_171324_("ten1");
      this.ten1_1 = this.ten1.m_171324_("ten1_1");
      this.ten1_2 = this.ten1_1.m_171324_("ten1_2");
      this.ten1_3 = this.ten1_2.m_171324_("ten1_3");
      this.ten1_4 = this.ten1_3.m_171324_("ten1_4");
      this.ten2 = this.bodyTop2.m_171324_("ten2");
      this.ten2_1 = this.ten2.m_171324_("ten2_1");
      this.ten2_2 = this.ten2_1.m_171324_("ten2_2");
      this.ten2_3 = this.ten2_2.m_171324_("ten2_3");
      this.ten2_4 = this.ten2_3.m_171324_("ten2_4");
      this.ten3 = this.bodyTop2.m_171324_("ten3");
      this.ten3_1 = this.ten3.m_171324_("ten3_1");
      this.ten3_2 = this.ten3_1.m_171324_("ten3_2");
      this.ten3_3 = this.ten3_2.m_171324_("ten3_3");
      this.ten3_4 = this.ten3_3.m_171324_("ten3_4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 4.0F, 150.0F));
      PartDefinition body_top_Z = main.m_171599_("body_top_Z", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -25.5F, -150.0F));
      PartDefinition body_top = body_top_Z.m_171599_(
         "body_top",
         CubeListBuilder.m_171558_().m_171514_(32, 41).m_171488_(-2.5F, -6.5F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(0.0F, 30.0F, 0.0F)
      );
      PartDefinition body_top2 = body_top.m_171599_("body_top2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -5.0F, 0.0F));
      PartDefinition head = body_top2.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
            .m_171514_(24, 8)
            .m_171488_(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 54)
            .m_171488_(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(72, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -6.0F, 1.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition head1 = head.m_171599_(
         "head1",
         CubeListBuilder.m_171558_().m_171514_(28, 53).m_171488_(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -9.5F, 7.0F, -1.5708F, 0.0F, 0.0F)
      );
      PartDefinition head2 = head.m_171599_(
         "head2",
         CubeListBuilder.m_171558_().m_171514_(22, 22).m_171488_(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -9.5F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      PartDefinition head3 = head.m_171599_(
         "head3",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 49)
            .m_171488_(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 57)
            .m_171488_(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 33)
            .m_171488_(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 31)
            .m_171488_(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 48)
            .m_171488_(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.25F, -4.0F, 0.05F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition head4 = head.m_171599_(
         "head4",
         CubeListBuilder.m_171558_()
            .m_171514_(46, 17)
            .m_171488_(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 0)
            .m_171488_(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 0)
            .m_171488_(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 27)
            .m_171488_(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 25)
            .m_171488_(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.25F, -4.0F, 0.05F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition head5 = head.m_171599_(
         "head5",
         CubeListBuilder.m_171558_()
            .m_171514_(24, 0)
            .m_171488_(-3.5F, -31.5F, -6.25F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 0)
            .m_171488_(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 19)
            .m_171488_(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 17)
            .m_171488_(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      PartDefinition body = body_top2.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(32, 31).m_171488_(-4.0F, -1.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)),
         PartPose.m_171419_(0.0F, -4.5F, 0.0F)
      );
      PartDefinition left_booby = body_top2.m_171599_("left_booby", CubeListBuilder.m_171558_(), PartPose.m_171419_(2.0F, -0.75F, 0.25F));
      PartDefinition left_boobyF = left_booby.m_171599_(
         "left_boobyF",
         CubeListBuilder.m_171558_().m_171514_(50, 41).m_171488_(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, -4.0F, -0.5236F, -0.2269F, 0.0175F)
      );
      PartDefinition right_booby = body_top2.m_171599_("right_booby", CubeListBuilder.m_171558_(), PartPose.m_171419_(-2.0F, -0.75F, 0.25F));
      PartDefinition right_boobyF = right_booby.m_171599_(
         "right_boobyF",
         CubeListBuilder.m_171558_().m_171514_(47, 50).m_171488_(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, -4.0F, -0.5236F, 0.2269F, -0.0175F)
      );
      PartDefinition left_arm = body_top2.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(48, 0)
            .m_171488_(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(77, 16)
            .m_171488_(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.2F))
            .m_171514_(89, 16)
            .m_171488_(0.25F, -5.0F, -0.5F, 7.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(77, 16)
            .m_171488_(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(4.0F, -3.5F, 1.0F)
      );
      PartDefinition left_arm_bottom = left_arm.m_171599_(
         "left_arm_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 64).m_171488_(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(1.75F, 5.0F, -0.5F)
      );
      PartDefinition right_arm = body_top2.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(-3.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-4.25F, -3.5F, 0.0F)
      );
      PartDefinition right_arm_bottom = right_arm.m_171599_(
         "right_arm_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 64).m_171488_(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(-1.5F, 5.0F, 0.5F)
      );
      PartDefinition bone = right_arm_bottom.m_171599_(
         "bone",
         CubeListBuilder.m_171558_().m_171514_(84, 106).m_171488_(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.5F, 0.0F)
      );
      PartDefinition ten1 = body_top2.m_171599_(
         "ten1",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -5.5F, 3.5F)
      );
      PartDefinition ten1_1 = ten1.m_171599_(
         "ten1_1",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten1_2 = ten1_1.m_171599_(
         "ten1_2",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten1_3 = ten1_2.m_171599_(
         "ten1_3",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten1_4 = ten1_3.m_171599_(
         "ten1_4",
         CubeListBuilder.m_171558_().m_171514_(60, 12).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten2 = body_top2.m_171599_(
         "ten2",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.75F, -7.5F, 3.0F)
      );
      PartDefinition ten2_1 = ten2.m_171599_(
         "ten2_1",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten2_2 = ten2_1.m_171599_(
         "ten2_2",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten2_3 = ten2_2.m_171599_(
         "ten2_3",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten2_4 = ten2_3.m_171599_(
         "ten2_4",
         CubeListBuilder.m_171558_().m_171514_(60, 12).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten3 = body_top2.m_171599_(
         "ten3",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.75F, -7.5F, 3.0F)
      );
      PartDefinition ten3_1 = ten3.m_171599_(
         "ten3_1",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten3_2 = ten3_1.m_171599_(
         "ten3_2",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten3_3 = ten3_2.m_171599_(
         "ten3_3",
         CubeListBuilder.m_171558_().m_171514_(60, 6).m_171488_(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition ten3_4 = ten3_3.m_171599_(
         "ten3_4",
         CubeListBuilder.m_171558_().m_171514_(60, 12).m_171488_(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition body_bottom_Z = main.m_171599_("body_bottom_Z", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 34.5F, -150.0F));
      PartDefinition body_bottom = body_bottom_Z.m_171599_(
         "body_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-4.5F, -0.5F, -2.75F, 9.0F, 8.0F, 6.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(0.0F, -30.0F, 0.0F)
      );
      PartDefinition right_leg = body_bottom.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(16, 31).m_171488_(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.5F, 3.5F, 0.0F)
      );
      PartDefinition right_leg_bottom = right_leg.m_171599_(
         "right_leg_bottom",
         CubeListBuilder.m_171558_().m_171514_(12, 64).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(0.0F, 5.5F, 0.0F)
      );
      PartDefinition left_leg = body_bottom.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.5F, 3.5F, 0.0F)
      );
      PartDefinition left_leg_bottom = left_leg.m_171599_(
         "left_leg_bottom",
         CubeListBuilder.m_171558_().m_171514_(12, 64).m_171488_(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(0.0F, 5.5F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void translateToHand(HumanoidArm arm, PoseStack pose) {
      this.main.m_104299_(pose);
      this.main.m_171324_("body_top_Z").m_104299_(pose);
      this.bodyTop.m_104299_(pose);
      this.bodyTop2.m_104299_(pose);
      if (arm == HumanoidArm.LEFT) {
         this.leftArm.m_104299_(pose);
         this.leftArmBottom.m_104299_(pose);
      } else {
         this.rightArm.m_104299_(pose);
         this.rightArmBottom.m_104299_(pose);
         this.item.m_104299_(pose);
      }
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setupAnim(CaptainCornelia entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(
         new ModelPart[]{
            this.main,
            this.bodyTop,
            this.bodyTop2,
            this.bodyBottom,
            this.head,
            this.rightBooby,
            this.leftBooby,
            this.rightArm,
            this.leftArm,
            this.rightLeg,
            this.leftLeg,
            this.rightArmBottom,
            this.leftArmBottom,
            this.rightLegBottom,
            this.leftLegBottom,
            this.item,
            this.ten1,
            this.ten1_1,
            this.ten1_2,
            this.ten1_3,
            this.ten1_4,
            this.ten2,
            this.ten2_1,
            this.ten2_2,
            this.ten2_3,
            this.ten2_4,
            this.ten3,
            this.ten3_1,
            this.ten3_2,
            this.ten3_3,
            this.ten3_4
         }
      );
      this.animateIdleAndMove(ageInTicks, limbSwingAmount);
      this.animateAttack(entity.SWITCH_WEAPON, entity.PULL_ATTACK, entity.SWING_ATTACK, entity.THRUST_ATTACK, ageInTicks);
      this.animateDeath(entity.DEATH, ageInTicks);
      this.head.f_104204_ = this.head.f_104204_ + mod.head(netHeadYaw, 0.333F);
      this.bodyTop.f_104204_ = this.bodyTop.f_104204_ + mod.head(netHeadYaw, 0.333F);
      this.bodyTop2.f_104204_ = this.bodyTop2.f_104204_ + mod.head(netHeadYaw, 0.333F);
   }

   private void animateIdleAndMove(float ageInTicks, float limbSwingAmount) {
      HekateLib.push(ageInTicks, 0.1F, mod.idle(limbSwingAmount, 5.0F), Mode.DEFINITION)
         .keyframe(this.main, k -> k.xRot(0.6F, 1.2F))
         .keyframe(this.bodyBottom, k -> k.xRot(15.0F, 20.0F, -0.9F))
         .keyframe(this.bodyTop2, k -> k.xRot(6.0F, -6.0F, 0.9F))
         .keyframe(this.head, k -> k.xRot(4.0F, -6.0F, 0.8F))
         .keyframe(this.rightArm, k -> k.rotation(12.0F, -9.0F, 9.0F, -27.0F, 6.0F, 20.0F, -0.95F))
         .keyframe(this.rightArmBottom, k -> k.xRot(-16.0F, 20.0F, -0.9F))
         .keyframe(this.item, k -> k.xRot(-5.0F, -35.0F, 0.85F))
         .keyframe(this.leftArm, k -> k.rotation(12.0F, -9.0F, -9.0F, 27.0F, -9.0F, -27.0F, -0.95F))
         .keyframe(this.leftArmBottom, k -> k.xRot(-24.0F, 30.0F, -0.9F))
         .keyframe(this.rightLeg, k -> k.rotation(12.0F, 24.0F, 0.6F, -3.0F, 0.6F, -3.0F, -0.8F))
         .keyframe(this.rightLegBottom, k -> k.xRot(-12.0F, -48.0F, -0.6F))
         .keyframe(this.leftLeg, k -> k.rotation(6.0F, -12.0F, 0.6F, 3.0F, 0.6F, 3.0F, -0.8F))
         .keyframe(this.leftLegBottom, k -> k.xRot(-24.0F, -27.0F, -0.7F))
         .keyframe(this.ten1, k -> k.xRot(16.0F, -20.0F, 0.35F))
         .keyframe(this.ten1_1, k -> k.xRot(16.0F, -12.0F, 0.3F))
         .keyframe(this.ten1_2, k -> k.xRot(16.0F, -6.0F, 0.25F))
         .keyframe(this.ten1_3, k -> k.xRot(16.0F, 0.0F, 0.2F))
         .keyframe(this.ten1_4, k -> k.xRot(16.0F, 0.0F, 0.15F))
         .keyframe(this.ten2, k -> k.xRot(16.0F, -20.0F, 0.25F).yRot(-30.0F))
         .keyframe(this.ten2_1, k -> k.xRot(16.0F, -12.0F, 0.2F))
         .keyframe(this.ten2_2, k -> k.xRot(16.0F, -6.0F, 0.15F))
         .keyframe(this.ten2_3, k -> k.xRot(16.0F, 0.0F, 0.1F))
         .keyframe(this.ten2_4, k -> k.xRot(16.0F, 0.0F, 0.05F))
         .keyframe(this.ten3, k -> k.xRot(16.0F, -20.0F, 0.45F).yRot(30.0F))
         .keyframe(this.ten3_1, k -> k.xRot(16.0F, -12.0F, 0.4F))
         .keyframe(this.ten3_2, k -> k.xRot(16.0F, -6.0F, 0.35F))
         .keyframe(this.ten3_3, k -> k.xRot(16.0F, 0.0F, 0.3F))
         .keyframe(this.ten3_4, k -> k.xRot(16.0F, 0.0F, 0.25F));
      HekateLib.push(ageInTicks, 0.2F, mod.move(limbSwingAmount, 5.0F), Mode.ADDITION)
         .keyframe(this.main, k -> k.xRot(-1.4F, 1.8F))
         .keyframe(this.bodyBottom, k -> k.xRot(6.0F, -36.0F))
         .keyframe(this.bodyTop, k -> k.xRot(6.0F, -24.0F))
         .keyframe(this.bodyTop2, k -> k.xRot(6.0F, 12.0F))
         .keyframe(this.rightArm, k -> k.rotation(12.0F, -9.0F, 9.0F, -27.0F, -9.0F, 27.0F, 0.05F))
         .keyframe(this.rightArmBottom, k -> k.xRot(24.0F, 30.0F))
         .keyframe(this.leftArm, k -> k.rotation(12.0F, -9.0F, -9.0F, 27.0F, 9.0F, -27.0F, 0.05F))
         .keyframe(this.leftArmBottom, k -> k.xRot(24.0F, 30.0F))
         .keyframe(this.rightLeg, k -> k.rotation(24.0F, 12.0F, 0.6F, -6.0F, 0.6F, -6.0F, 0.5F))
         .keyframe(this.rightLegBottom, k -> k.xRot(24.0F, -74.0F, 0.7F))
         .keyframe(this.leftLeg, k -> k.rotation(24.0F, 0.0F, -0.6F, 6.0F, -0.6F, 6.0F, 0.5F))
         .keyframe(this.leftLegBottom, k -> k.xRot(24.0F, -30.0F, 0.8F))
         .keyframe(this.ten1, k -> k.xRot(-14.0F, -29.0F, 0.25F))
         .keyframe(this.ten1_1, k -> k.xRot(-14.0F, -18.0F, 0.2F))
         .keyframe(this.ten1_2, k -> k.xRot(-14.0F, -12.0F, 0.15F))
         .keyframe(this.ten1_3, k -> k.xRot(-14.0F, -6.0F, 0.1F))
         .keyframe(this.ten1_4, k -> k.xRot(-14.0F, -6.0F, 0.05F))
         .keyframe(this.ten2, k -> k.xRot(-14.0F, -29.0F, 0.25F).yRot(-30.0F))
         .keyframe(this.ten2_1, k -> k.xRot(-14.0F, -18.0F, 0.2F))
         .keyframe(this.ten2_2, k -> k.xRot(-14.0F, -12.0F, 0.15F))
         .keyframe(this.ten2_3, k -> k.xRot(-14.0F, -6.0F, 0.1F))
         .keyframe(this.ten2_4, k -> k.xRot(-14.0F, -6.0F, 0.05F))
         .keyframe(this.ten3, k -> k.xRot(-14.0F, -29.0F, 0.25F).yRot(30.0F))
         .keyframe(this.ten3_1, k -> k.xRot(-14.0F, -18.0F, 0.2F))
         .keyframe(this.ten3_2, k -> k.xRot(-14.0F, -12.0F, 0.15F))
         .keyframe(this.ten3_3, k -> k.xRot(-14.0F, -6.0F, 0.1F))
         .keyframe(this.ten3_4, k -> k.xRot(-14.0F, -6.0F, 0.05F));
   }

   private void animateAttack(Animation weapon, Animation pull, Animation swing, Animation thrust, float ageInTicks) {
      HekateLib.push(10, 10, Interpolations.EASE_OUT_BACK.scale(0.95F), Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            20,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.bodyTop, k -> k.rotation(-20.0F, 22.0F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(-12.0F, 20.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(-12.0F, 0.0F, 0.0F))
                  .keyframe(this.leftArm, k -> k.rotation(-20.0F, 0.0F, -17.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(55.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(90.0F, 45.0F, 90.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(40.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-45.0F, 0.0F, 0.0F))
         )
         .animate(weapon);
      HekateLib.push(10, 10, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            10,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(2.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(0.0F, -17.5F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(0.0F, -32.5F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(-10.0F, 46.0F, -3.5F))
                  .keyframe(this.leftArm, k -> k.rotation(30.0F, 35.0F, -32.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(25.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(5.5F, 28.0F, 19.5F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(25.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-50.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(27.5F, 0.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(25.0F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-60.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-2.5F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-17.5F, 0.0F, 0.0F))
         )
         .pose(
            10,
            30,
            Interpolations.EASE_OUT_EXPO.scale(0.5F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(1.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(0.0F, -7.5F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(10.0F, -15.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(-15.0F, 27.0F, 0.0F))
                  .keyframe(this.leftArm, k -> k.rotation(-19.0F, 12.5F, -31.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(105.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(-10.0F, 1.0F, 20.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(52.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-50.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(-22.5F, 0.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(25.0F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-70.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-2.5F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-17.5F, 0.0F, 0.0F))
         )
         .animate(pull);
      HekateLib.push(12, 20, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            12,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(2.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(-14.0F, 42.0F, -2.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(0.0F, 25.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(3.0F, -56.0F, -8.0F))
                  .keyframe(this.leftArm, k -> k.rotation(-21.0F, 12.0F, -18.5F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(70.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(57.0F, 25.0F, 74.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(75.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-35.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(25.0F, 0.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(57.0F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-90.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-2.5F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-17.5F, 0.0F, 0.0F))
         )
         .pose(
            12,
            40,
            Interpolations.EASE_OUT_EXPO.scale(0.2F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(1.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(-10.0F, -4.0F, 8.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(0.0F, -20.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(2.0F, 24.0F, -4.5F))
                  .keyframe(this.leftArm, k -> k.rotation(-50.0F, 30.0F, -35.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(100.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(35.0F, 12.0F, 47.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(10.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-82.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(-32.0F, 2.0F, -4.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(57.0F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-90.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-2.5F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-17.5F, 0.0F, 0.0F))
         )
         .animate(swing);
      HekateLib.push(12, 20, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            12,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(2.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(0.0F, -17.5F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(0.0F, -27.5F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(-22.0F, 42.0F, -9.0F))
                  .keyframe(this.leftArm, k -> k.rotation(33.5F, 12.0F, -18.5F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(67.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(-48.0F, -10.5F, 57.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(75.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-45.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(25.0F, 0.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(32.4F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-90.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(0.0F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-38.0F, 0.0F, 0.0F))
         )
         .pose(
            12,
            40,
            Interpolations.EASE_OUT_EXPO.scale(0.2F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(1.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(-12.5F, 35.0F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(0.0F, 7.5F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(1.0F, -38.0F, 0.0F))
                  .keyframe(this.leftArm, k -> k.rotation(-21.5F, 12.0F, -18.5F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(70.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(24.0F, -10.5F, 57.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(12.5F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-60.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(-28.0F, 10.0F, -2.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(57.0F, 2.6F, -4.2F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-90.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-2.5F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(-17.5F, 0.0F, 0.0F))
         )
         .animate(thrust);
   }

   private void animateDeath(Animation death, float ageInTicks) {
      HekateLib.push(10, 0, Interpolations.EASE_OUT_BACK, Interpolations.CEIL)
         .pose(
            0,
            10,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(5.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(10.0F, 0.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(10.0F, 0.0F, 0.0F))
                  .keyframe(this.leftArm, k -> k.rotation(-35.0F, 0.0F, -30.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(45.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(-35.0F, 0.0F, 30.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(45.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(-5.0F, 0.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(0.0F, 0.0F, -3.0F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(-23.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(-8.0F, 0.0F, 3.0F))
         )
         .pose(
            10,
            60,
            Interpolations.EASE_OUT_BOUNCE.scale(0.5F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.rotation(-5.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop, k -> k.rotation(-82.5F, 0.0F, 0.0F))
                  .keyframe(this.bodyTop2, k -> k.rotation(12.5F, 0.0F, 0.0F))
                  .keyframe(this.head, k -> k.rotation(-27.0F, 34.0F, -16.0F))
                  .keyframe(this.leftArm, k -> k.rotation(47.0F, 55.0F, 5.0F))
                  .keyframe(this.leftArmBottom, k -> k.rotation(82.0F, 0.0F, 0.0F))
                  .keyframe(this.rightArm, k -> k.rotation(37.0F, -56.0F, 22.0F))
                  .keyframe(this.rightArmBottom, k -> k.rotation(62.0F, 0.0F, 0.0F))
                  .keyframe(this.item, k -> k.rotation(-40.0F, 0.0F, 0.0F))
                  .keyframe(this.bodyBottom, k -> k.rotation(-85.0F, -5.0F, 0.0F))
                  .keyframe(this.rightLeg, k -> k.rotation(0.0F, 0.0F, -5.0F))
                  .keyframe(this.rightLegBottom, k -> k.rotation(0.0F, 0.0F, 0.0F))
                  .keyframe(this.leftLeg, k -> k.rotation(0.0F, 0.0F, 2.5F))
                  .keyframe(this.leftLegBottom, k -> k.rotation(0.0F, 0.0F, 0.0F))
         )
         .animate(death);
   }
}
