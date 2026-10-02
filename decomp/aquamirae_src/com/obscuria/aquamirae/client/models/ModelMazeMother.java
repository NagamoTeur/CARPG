package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.MazeMother;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.HekateLib.Mode;
import com.obscuria.obscureapi.api.hekate.HekateLib.math;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelMazeMother extends EntityModel<MazeMother> {
   public final ModelPart main;
   public final ModelPart main2;
   public final ModelPart bodyTop;
   public final ModelPart bodyBottom;
   public final ModelPart tail1;
   public final ModelPart tail2;
   public final ModelPart tail3;
   public final ModelPart tail4;
   public final ModelPart tail5;
   public final ModelPart jaw1;
   public final ModelPart jaw2;
   public final ModelPart jaw3;
   public final ModelPart jaw4;
   public final ModelPart wing1LeftTop;
   public final ModelPart wing1LeftBottom;
   public final ModelPart wing1RightTop;
   public final ModelPart wing1RightBottom;
   public final ModelPart wing2LeftTop;
   public final ModelPart wing2LeftBottom;
   public final ModelPart wing2RightTop;
   public final ModelPart wing2RightBottom;

   public ModelMazeMother(ModelPart root) {
      this.main = root.m_171324_("main");
      this.main2 = this.main.m_171324_("main2");
      this.bodyTop = this.main2.m_171324_("body_top");
      this.bodyBottom = this.main2.m_171324_("body_bottom");
      this.jaw1 = this.bodyTop.m_171324_("jaw1");
      this.jaw2 = this.bodyTop.m_171324_("jaw2");
      this.jaw3 = this.bodyTop.m_171324_("jaw3");
      this.jaw4 = this.bodyTop.m_171324_("jaw4");
      this.tail1 = this.bodyBottom.m_171324_("tail1");
      this.tail2 = this.tail1.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
      this.wing1LeftTop = this.bodyTop.m_171324_("left_wing1_top");
      this.wing1RightTop = this.bodyTop.m_171324_("right_wing1_top");
      this.wing2LeftTop = this.wing1LeftTop.m_171324_("left_wing2_top");
      this.wing2RightTop = this.wing1RightTop.m_171324_("right_wing2_top");
      this.wing1LeftBottom = this.bodyBottom.m_171324_("left_wing1_bottom");
      this.wing1RightBottom = this.bodyBottom.m_171324_("right_wing1_bottom");
      this.wing2LeftBottom = this.wing1LeftBottom.m_171324_("left_wing2_bottom");
      this.wing2RightBottom = this.wing1RightBottom.m_171324_("right_wing2_bottom");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -126.0F, 0.0F));
      PartDefinition main2 = main.m_171599_("main2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 147.0F, 0.0F));
      PartDefinition body_top = main2.m_171599_(
         "body_top",
         CubeListBuilder.m_171558_().m_171514_(0, 45).m_171488_(-8.0F, -1.5F, -16.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition jaw1 = body_top.m_171599_(
         "jaw1",
         CubeListBuilder.m_171558_().m_171514_(37, 26).m_171488_(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-7.0F, 0.0F, -14.5F)
      );
      PartDefinition jaw2 = body_top.m_171599_(
         "jaw2",
         CubeListBuilder.m_171558_().m_171514_(49, 26).m_171488_(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(7.0F, 0.0F, -14.5F)
      );
      PartDefinition jaw3 = body_top.m_171599_(
         "jaw3",
         CubeListBuilder.m_171558_().m_171514_(65, 26).m_171488_(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-3.5F, 0.0F, -15.5F)
      );
      PartDefinition jaw4 = body_top.m_171599_(
         "jaw4",
         CubeListBuilder.m_171558_().m_171514_(73, 26).m_171488_(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.5F, 0.0F, -15.5F)
      );
      PartDefinition crystal1_top = body_top.m_171599_("crystal1_top", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -16.0F));
      PartDefinition cube_r1 = crystal1_top.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(32, 58).m_171488_(-2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition crystal2_top = body_top.m_171599_("crystal2_top", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -16.0F));
      PartDefinition cube_r2 = crystal2_top.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(64, 45).m_171488_(-1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.3491F)
      );
      PartDefinition crystal3_top = body_top.m_171599_("crystal3_top", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -16.0F));
      PartDefinition cube_r3 = crystal3_top.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(64, 65).m_171488_(1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.3491F)
      );
      PartDefinition crystal4_top = body_top.m_171599_("crystal4_top", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -16.0F));
      PartDefinition cube_r4 = crystal4_top.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(32, 48).m_171488_(2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_wing1_top = body_top.m_171599_(
         "left_wing1_top",
         CubeListBuilder.m_171558_().m_171514_(48, 45).m_171488_(-9.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-8.0F, 0.0F, 0.0F)
      );
      PartDefinition left_wing2_top = left_wing1_top.m_171599_(
         "left_wing2_top",
         CubeListBuilder.m_171558_().m_171514_(92, 0).m_171488_(-5.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-9.0F, 0.0F, 0.0F)
      );
      PartDefinition right_wing1_top = body_top.m_171599_(
         "right_wing1_top",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(0.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(8.0F, 0.0F, 0.0F)
      );
      PartDefinition right_wing2_top = right_wing1_top.m_171599_(
         "right_wing2_top",
         CubeListBuilder.m_171558_().m_171514_(47, 91).m_171488_(0.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(9.0F, 0.0F, 0.0F)
      );
      PartDefinition body_bottom = main2.m_171599_(
         "body_bottom",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 26)
            .m_171488_(-8.0F, 0.5F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(72, 26)
            .m_171488_(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, 0.0F)
      );
      PartDefinition crystal1_bottom = body_bottom.m_171599_("crystal1_bottom", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, 0.0F));
      PartDefinition cube_r5 = crystal1_bottom.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(0, 58).m_171488_(-2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition crystal2_bottom = body_bottom.m_171599_("crystal2_bottom", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, 0.0F));
      PartDefinition cube_r6 = crystal2_bottom.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F)
      );
      PartDefinition crystal3_bottom = body_bottom.m_171599_("crystal3_bottom", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, 0.0F));
      PartDefinition cube_r7 = crystal3_bottom.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(64, 55).m_171488_(1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F)
      );
      PartDefinition crystal4_bottom = body_bottom.m_171599_("crystal4_bottom", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 2.0F, 0.0F));
      PartDefinition cube_r8 = crystal4_bottom.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(0, 48).m_171488_(2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_wing1_bottom = body_bottom.m_171599_(
         "left_wing1_bottom",
         CubeListBuilder.m_171558_().m_171514_(80, 45).m_171488_(-9.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-8.0F, 2.0F, 0.0F)
      );
      PartDefinition left_wing2_bottom = left_wing1_bottom.m_171599_(
         "left_wing2_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-9.0F, 0.0F, 0.0F)
      );
      PartDefinition right_wing1_bottom = body_bottom.m_171599_(
         "right_wing1_bottom",
         CubeListBuilder.m_171558_().m_171514_(20, 84).m_171488_(0.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(8.0F, 2.0F, 0.0F)
      );
      PartDefinition right_wing2_bottom = right_wing1_bottom.m_171599_(
         "right_wing2_bottom",
         CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(0.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(9.0F, 0.0F, 0.0F)
      );
      PartDefinition tail1 = body_bottom.m_171599_(
         "tail1",
         CubeListBuilder.m_171558_().m_171514_(96, 57).m_171488_(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, 16.0F)
      );
      PartDefinition tail2 = tail1.m_171599_(
         "tail2",
         CubeListBuilder.m_171558_().m_171514_(98, 69).m_171488_(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 9.0F)
      );
      PartDefinition tail3 = tail2.m_171599_(
         "tail3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 87)
            .m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 22)
            .m_171488_(-8.5F, 0.0F, 5.0F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 7.0F)
      );
      PartDefinition tail4 = tail3.m_171599_(
         "tail4",
         CubeListBuilder.m_171558_()
            .m_171514_(2, 98)
            .m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 15)
            .m_171488_(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 9.0F)
      );
      PartDefinition tail5 = tail4.m_171599_(
         "tail5",
         CubeListBuilder.m_171558_()
            .m_171514_(11, 0)
            .m_171488_(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 14)
            .m_171488_(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 7.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85836_();
      poseStack.m_85841_(5.0F, 5.0F, 5.0F);
      poseStack.m_85837_(0.0, -1.0, 0.0);
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }

   public void setupAnim(MazeMother entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(
         new ModelPart[]{
            this.main,
            this.main2,
            this.bodyTop,
            this.bodyBottom,
            this.tail1,
            this.tail2,
            this.tail3,
            this.tail4,
            this.tail5,
            this.wing1LeftTop,
            this.wing1LeftBottom,
            this.wing1RightTop,
            this.wing1RightBottom,
            this.wing2LeftTop,
            this.wing2LeftBottom,
            this.wing2RightTop,
            this.wing2RightBottom,
            this.jaw1,
            this.jaw2,
            this.jaw3,
            this.jaw4
         }
      );
      HekateLib.push(ageInTicks, 0.1F, 1.0F, Mode.DEFINITION)
         .keyframe(this.bodyTop, k -> k.xRot(-10.0F, 0.0F))
         .keyframe(this.bodyBottom, k -> k.xRot(-10.0F, 0.0F, -0.1F))
         .keyframe(this.tail1, k -> k.xRot(-10.0F, 0.0F, -0.2F))
         .keyframe(this.tail2, k -> k.xRot(-10.0F, 0.0F, -0.3F))
         .keyframe(this.tail3, k -> k.xRot(-10.0F, 0.0F, -0.4F))
         .keyframe(this.tail4, k -> k.xRot(-10.0F, 0.0F, -0.5F))
         .keyframe(this.tail5, k -> k.xRot(-10.0F, 0.0F, -0.6F))
         .keyframe(this.wing1RightTop, k -> k.zRot(15.0F, 0.0F, -0.1F))
         .keyframe(this.wing1LeftTop, k -> k.zRot(-15.0F, 0.0F, -0.1F))
         .keyframe(this.wing2RightTop, k -> k.zRot(15.0F, 0.0F, -0.2F))
         .keyframe(this.wing2LeftTop, k -> k.zRot(-15.0F, 0.0F, -0.2F));
      this.wing1RightBottom.f_104205_ = this.wing1RightTop.f_104205_;
      this.wing1LeftBottom.f_104205_ = this.wing1LeftTop.f_104205_;
      this.wing2RightBottom.f_104205_ = this.wing2RightTop.f_104205_;
      this.wing2LeftBottom.f_104205_ = this.wing2LeftTop.f_104205_;
      math.i(this.jaw1, 0.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.4F, 0.0F, ageInTicks, math.cycle(ageInTicks, 0.02F, -1.0F));
      math.i(this.jaw2, 0.0F, 0.0F, -10.0F, -10.0F, 0.0F, 0.0F, 0.4F, 0.0F, ageInTicks, math.cycle(ageInTicks, 0.02F, -1.0F));
      math.i(this.jaw3, 0.0F, 0.0F, 15.0F, 5.0F, 0.0F, 0.0F, 0.6F, 0.0F, ageInTicks, math.cycle(ageInTicks, 0.02F, 0.0F));
      math.i(this.jaw4, 0.0F, 0.0F, -15.0F, -5.0F, 0.0F, 0.0F, 0.6F, 0.0F, ageInTicks, math.cycle(ageInTicks, 0.02F, 0.0F));
   }
}
