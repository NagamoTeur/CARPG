package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.Eel;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Interpolations;
import com.obscuria.obscureapi.api.hekate.HekateLib.Mode;
import com.obscuria.obscureapi.api.hekate.HekateLib.math;
import com.obscuria.obscureapi.api.hekate.HekateLib.mod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelEel extends EntityModel<Eel> {
   public final ModelPart main;
   public final ModelPart body1;
   public final ModelPart body2;
   public final ModelPart body3;
   public final ModelPart body4;
   public final ModelPart body5;
   public final ModelPart body6;
   public final ModelPart body7;
   public final ModelPart body8;
   public final ModelPart body9;
   public final ModelPart body10;
   public final ModelPart head;
   public final ModelPart headUpper;
   public final ModelPart headLower;
   public final ModelPart leftFin;
   public final ModelPart rightFin;

   public ModelEel(ModelPart root) {
      this.main = root.m_171324_("main");
      this.body1 = this.main.m_171324_("body1");
      this.body2 = this.body1.m_171324_("body2");
      this.body3 = this.body2.m_171324_("body3");
      this.body4 = this.body3.m_171324_("body4");
      this.body5 = this.body4.m_171324_("body5");
      this.body6 = this.body5.m_171324_("body6");
      this.body7 = this.body6.m_171324_("body7");
      this.body8 = this.body7.m_171324_("body8");
      this.body9 = this.body8.m_171324_("body9");
      this.body10 = this.body9.m_171324_("body10");
      this.head = this.body10.m_171324_("head");
      this.headUpper = this.head.m_171324_("headTop");
      this.headLower = this.head.m_171324_("headBottom");
      this.leftFin = this.head.m_171324_("leftFinP").m_171324_("leftFin");
      this.rightFin = this.head.m_171324_("rightFinP").m_171324_("rightFin");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 300.0F));
      PartDefinition body1 = main.m_171599_("body1", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 0.0F, -300.0F, 0.5672F, 0.0F, 0.0F));
      PartDefinition body2 = body1.m_171599_("body2", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -5.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
      PartDefinition body3 = body2.m_171599_("body3", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, -0.6109F, 0.0F, 0.0F));
      PartDefinition body4 = body3.m_171599_("body4", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
      PartDefinition body5 = body4.m_171599_("body5", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
      PartDefinition body6 = body5.m_171599_("body6", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -7.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
      PartDefinition body7 = body6.m_171599_("body7", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.6109F, 0.0F, 0.0F));
      PartDefinition body8 = body7.m_171599_("body8", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
      PartDefinition body9 = body8.m_171599_("body9", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
      PartDefinition body10 = body9.m_171599_("body10", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
      PartDefinition head = body10.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
      PartDefinition headTop = head.m_171599_("headTop", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -11.0F, -6.0F, 0.6981F, 0.0F, 0.0F));
      PartDefinition bone3 = headTop.m_171599_(
         "bone3",
         CubeListBuilder.m_171558_()
            .m_171514_(84, 61)
            .m_171488_(-6.0F, -42.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 0)
            .m_171488_(6.0F, -40.0F, -3.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 8)
            .m_171488_(-3.0F, -40.0F, 6.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 82)
            .m_171488_(-6.0F, -46.5F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-0.5F))
            .m_171514_(84, 82)
            .m_171488_(-7.0F, -46.0F, -7.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-1.0F)),
         PartPose.m_171423_(0.0F, 42.5507F, 4.774F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition headBottom = head.m_171599_("headBottom", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -11.0F, -6.0F, 2.0944F, 0.0F, 0.0F));
      PartDefinition bone5 = headBottom.m_171599_(
         "bone5",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 23)
            .m_171488_(-9.0F, -42.0F, -9.0F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 0)
            .m_171488_(-9.0F, -40.5F, -9.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-0.5F))
            .m_171514_(12, 0)
            .m_171488_(-10.0F, -41.0F, -10.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-1.0F)),
         PartPose.m_171423_(0.0F, 40.2191F, 4.5237F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone2 = head.m_171599_(
         "bone2",
         CubeListBuilder.m_171558_().m_171514_(12, 40).m_171488_(-6.0F, -45.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 35.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone11 = bone2.m_171599_(
         "bone11",
         CubeListBuilder.m_171558_().m_171514_(169, -8).m_171488_(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -40.0F, 0.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition bone13 = bone2.m_171599_(
         "bone13",
         CubeListBuilder.m_171558_().m_171514_(169, -8).m_171488_(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -40.0F, 0.0F, 0.0F, 2.3562F, 0.0F)
      );
      PartDefinition bone15 = bone2.m_171599_(
         "bone15",
         CubeListBuilder.m_171558_().m_171514_(169, -8).m_171488_(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -40.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition leftFinP = head.m_171599_("leftFinP", CubeListBuilder.m_171558_(), PartPose.m_171423_(4.0F, -7.0F, -4.0F, 0.0F, 0.7854F, 0.0F));
      PartDefinition leftFin = leftFinP.m_171599_(
         "leftFin",
         CubeListBuilder.m_171558_()
            .m_171514_(137, -9)
            .m_171488_(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(158, 1)
            .m_171488_(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9599F)
      );
      PartDefinition rightFinP = head.m_171599_("rightFinP", CubeListBuilder.m_171558_(), PartPose.m_171423_(-5.0F, -7.0F, -4.0F, 0.0F, -0.7854F, 0.0F));
      PartDefinition rightFin = rightFinP.m_171599_(
         "rightFin",
         CubeListBuilder.m_171558_()
            .m_171514_(137, -9)
            .m_171488_(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(158, 1)
            .m_171488_(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0908F)
      );
      PartDefinition bone20 = body10.m_171599_(
         "bone20",
         CubeListBuilder.m_171558_().m_171514_(48, 52).m_171488_(-6.0F, -44.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone17 = bone20.m_171599_(
         "bone17",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -47.0F, 0.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition bone19 = bone20.m_171599_(
         "bone19",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -47.0F, 0.0F, 0.0F, 2.3562F, 0.0F)
      );
      PartDefinition bone21 = bone20.m_171599_(
         "bone21",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -47.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone18 = body9.m_171599_(
         "bone18",
         CubeListBuilder.m_171558_().m_171514_(60, 11).m_171488_(-6.0F, -34.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.15F)),
         PartPose.m_171423_(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone22 = bone18.m_171599_(
         "bone22",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -37.0F, 0.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition bone23 = bone18.m_171599_(
         "bone23",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -37.0F, 0.0F, 0.0F, 2.3562F, 0.0F)
      );
      PartDefinition bone24 = bone18.m_171599_(
         "bone24",
         CubeListBuilder.m_171558_().m_171514_(170, -8).m_171488_(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -37.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone16 = body8.m_171599_(
         "bone16",
         CubeListBuilder.m_171558_().m_171514_(12, 64).m_171488_(-6.0F, -24.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone14 = body7.m_171599_(
         "bone14",
         CubeListBuilder.m_171558_().m_171514_(48, 73).m_171488_(-6.0F, -4.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.25F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone12 = body6.m_171599_(
         "bone12",
         CubeListBuilder.m_171558_().m_171514_(84, 32).m_171488_(-6.0F, -3.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.3F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone10 = body5.m_171599_(
         "bone10",
         CubeListBuilder.m_171558_().m_171514_(2, 85).m_171488_(-5.0F, -44.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone8 = body4.m_171599_(
         "bone8",
         CubeListBuilder.m_171558_().m_171514_(32, 94).m_171488_(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.15F)),
         PartPose.m_171423_(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone6 = body3.m_171599_(
         "bone6",
         CubeListBuilder.m_171558_().m_171514_(96, 0).m_171488_(-5.0F, -24.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone4 = body2.m_171599_(
         "bone4",
         CubeListBuilder.m_171558_().m_171514_(72, 99).m_171488_(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.05F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition bone = body1.m_171599_(
         "bone",
         CubeListBuilder.m_171558_().m_171514_(0, 103).m_171488_(-5.0F, -3.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85836_();
      poseStack.m_85841_(1.8F, 1.8F, 1.8F);
      poseStack.m_85837_(0.0, -0.7F, 0.0);
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }

   public void setupAnim(Eel eel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(
         new ModelPart[]{
            this.main,
            this.body1,
            this.body2,
            this.body3,
            this.body4,
            this.body5,
            this.body6,
            this.body7,
            this.body8,
            this.body9,
            this.body10,
            this.head,
            this.headUpper,
            this.headLower,
            this.leftFin,
            this.rightFin
         }
      );
      HekateLib.push(ageInTicks, 0.1F, 1.0F, Mode.DEFINITION)
         .keyframe(this.body1, k -> k.xRot(-1.0F, -17.5F, -0.95F))
         .keyframe(this.body2, k -> k.xRot(1.0F, 20.0F, -0.9F))
         .keyframe(this.body3, k -> k.xRot(-1.0F, 22.5F, -0.85F))
         .keyframe(this.body4, k -> k.xRot(-1.0F, 20.0F, -0.8F))
         .keyframe(this.body5, k -> k.xRot(-2.0F, -22.5F, -0.75F))
         .keyframe(this.body6, k -> k.xRot(-2.0F, -22.5F, -0.7F))
         .keyframe(this.body7, k -> k.xRot(-2.0F, -27.5F, -0.65F))
         .keyframe(this.body8, k -> k.xRot(-2.0F, -25.0F, -0.6F))
         .keyframe(this.body9, k -> k.xRot(-3.0F, -27.5F, -0.55F))
         .keyframe(this.body10, k -> k.xRot(-3.0F, -22.5F, -0.5F))
         .keyframe(this.head, k -> k.xRot(-3.0F, -22.5F))
         .keyframe(this.headUpper, k -> k.xRot(-40.0F))
         .keyframe(this.headLower, k -> k.xRot(-10.0F, -105.0F))
         .keyframe(this.leftFin, k -> k.zRot(10.0F, -45.0F))
         .keyframe(this.rightFin, k -> k.zRot(-10.0F, 45.0F));
      HekateLib.push(60, 60, Interpolations.EASE_IN_OUT_BACK, Interpolations.EASE_IN_OUT_BACK)
         .pose(
            0,
            300,
            Interpolations.CEIL,
            ageInTicks,
            0.14F,
            builder -> builder.keyframe(this.body5, k -> k.rotation(-2.0F, -22.5F, 1.0F, 0.0F, 0.0F, 0.0F, 2.0F, -0.95F))
                  .keyframe(this.body6, k -> k.rotation(-2.0F, -22.5F, 2.0F, 0.0F, 0.0F, 0.0F, -0.9F))
                  .keyframe(this.body7, k -> k.rotation(-2.0F, -27.5F, 3.0F, 0.0F, 0.0F, 0.0F, -0.85F))
                  .keyframe(this.body8, k -> k.rotation(-2.0F, -25.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.8F))
                  .keyframe(this.body9, k -> k.rotation(-3.0F, -27.5F, 5.0F, 0.0F, 0.0F, 0.0F, -0.75F))
                  .keyframe(this.body10, k -> k.rotation(-3.0F, -22.5F, 6.0F, 0.0F, 0.0F, 0.0F, -0.7F))
                  .keyframe(this.head, k -> k.rotation(-3.0F, -22.5F, 7.0F, 0.0F, 0.0F, 0.0F, -0.65F))
                  .keyframe(this.headLower, k -> k.xRot(math.cycle(ageInTicks, 0.1F) * 8.0F, -105.0F, 10.0F, 0.0F))
         )
         .animate(eel.RARE_IDLE);
      HekateLib.push(12, 12, Interpolations.LINEAR, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            12,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(20.0F))
                  .keyframe(this.body3, k -> k.xRot(22.5F))
                  .keyframe(this.body4, k -> k.xRot(27.5F))
                  .keyframe(this.body5, k -> k.xRot(-2.5F))
                  .keyframe(this.body6, k -> k.xRot(-2.5F))
                  .keyframe(this.body7, k -> k.xRot(-30.0F))
                  .keyframe(this.body8, k -> k.xRot(-30.0F))
                  .keyframe(this.body9, k -> k.xRot(-35.0F))
                  .keyframe(this.body10, k -> k.xRot(-25.0F))
                  .keyframe(this.head, k -> k.xRot(-25.0F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.rotation(10.0F, -170.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4F, 0.0F))
                  .keyframe(this.leftFin, k -> k.zRot(-90.0F))
                  .keyframe(this.rightFin, k -> k.zRot(90.0F))
         )
         .pose(
            12,
            30,
            Interpolations.EASE_IN_OUT_CUBIC.scale(0.2F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(10.0F).scale(1.025F))
                  .keyframe(this.body3, k -> k.xRot(15.0F).scale(1.025F))
                  .keyframe(this.body4, k -> k.xRot(12.5F).scale(1.025F))
                  .keyframe(this.body5, k -> k.xRot(-35.0F).yRot(-1.0F).scale(1.025F))
                  .keyframe(this.body6, k -> k.xRot(-30.0F).yRot(-1.0F).scale(1.025F))
                  .keyframe(this.body7, k -> k.xRot(-35.0F).yRot(-2.0F).scale(1.025F))
                  .keyframe(this.body8, k -> k.xRot(-25.0F).yRot(-2.0F).scale(1.025F))
                  .keyframe(this.body9, k -> k.xRot(-12.5F).yRot(-3.0F).scale(1.025F))
                  .keyframe(this.body10, k -> k.xRot(-2.5F).yRot(-3.0F).scale(1.025F))
                  .keyframe(this.head, k -> k.xRot(-20.0F).yRot(-4.0F).scale(1.025F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.xRot(-75.0F))
                  .keyframe(this.leftFin, k -> k.zRot(-40.0F))
                  .keyframe(this.rightFin, k -> k.zRot(40.0F))
         )
         .animate(eel.ATTACK);
      HekateLib.push(12, 48, Interpolations.EASE_OUT_BACK, Interpolations.EASE_IN_OUT_BACK)
         .pose(
            0,
            12,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(20.0F))
                  .keyframe(this.body3, k -> k.xRot(22.5F))
                  .keyframe(this.body4, k -> k.xRot(27.5F))
                  .keyframe(this.body5, k -> k.xRot(-2.5F))
                  .keyframe(this.body6, k -> k.rotation(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.95F))
                  .keyframe(this.body7, k -> k.rotation(0.0F, -30.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.9F))
                  .keyframe(this.body8, k -> k.rotation(0.0F, -30.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.85F))
                  .keyframe(this.body9, k -> k.rotation(0.0F, -35.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.8F))
                  .keyframe(this.body10, k -> k.rotation(0.0F, -25.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.75F))
                  .keyframe(this.head, k -> k.rotation(0.0F, -25.0F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.7F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.rotation(5.0F, -90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4F, 0.0F))
                  .keyframe(this.leftFin, k -> k.zRot(-90.0F))
                  .keyframe(this.rightFin, k -> k.zRot(90.0F))
         )
         .pose(
            12,
            100,
            Interpolations.EASE_IN_OUT_CUBIC.scale(0.05F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(12.5F))
                  .keyframe(this.body3, k -> k.xRot(25.0F))
                  .keyframe(this.body4, k -> k.xRot(22.5F))
                  .keyframe(this.body5, k -> k.xRot(-30.0F))
                  .keyframe(this.body6, k -> k.rotation(0.0F, -32.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.95F))
                  .keyframe(this.body7, k -> k.rotation(0.0F, -27.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.9F))
                  .keyframe(this.body8, k -> k.rotation(0.0F, -27.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.85F))
                  .keyframe(this.body9, k -> k.rotation(0.0F, -22.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.8F))
                  .keyframe(this.body10, k -> k.rotation(0.0F, -7.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.75F))
                  .keyframe(this.head, k -> k.rotation(0.0F, -12.5F, 0.0F, 0.0F, 4.0F, 0.0F, 0.4F, -0.7F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.rotation(10.0F, -145.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5F, 0.0F))
                  .keyframe(this.leftFin, k -> k.zRot(-10.0F, -80.0F, 2.0F, 0.0F))
                  .keyframe(this.rightFin, k -> k.zRot(10.0F, 80.0F, 2.0F, 0.0F))
         )
         .animate(eel.ROAR);
      HekateLib.push(20, 0, Interpolations.EASE_OUT_CUBIC.scale(0.8F), Interpolations.CEIL)
         .pose(
            0,
            20,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(20.0F))
                  .keyframe(this.body3, k -> k.xRot(22.5F))
                  .keyframe(this.body4, k -> k.xRot(27.5F))
                  .keyframe(this.body5, k -> k.xRot(-2.5F))
                  .keyframe(this.body6, k -> k.rotation(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 0.4F, -0.95F))
                  .keyframe(this.body7, k -> k.rotation(0.0F, -30.0F, 0.0F, 0.0F, 10.0F, 0.0F, 0.4F, -0.9F))
                  .keyframe(this.body8, k -> k.rotation(0.0F, -30.0F, 0.0F, 0.0F, 15.0F, 0.0F, 0.4F, -0.85F))
                  .keyframe(this.body9, k -> k.rotation(0.0F, -35.0F, 0.0F, 0.0F, 20.0F, 0.0F, 0.4F, -0.8F))
                  .keyframe(this.body10, k -> k.rotation(0.0F, -25.0F, 0.0F, 0.0F, 20.0F, 0.0F, 0.4F, -0.75F))
                  .keyframe(this.head, k -> k.rotation(0.0F, -25.0F, 0.0F, 0.0F, 20.0F, 0.0F, 0.4F, -0.7F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.rotation(5.0F, -90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4F, 0.0F))
                  .keyframe(this.leftFin, k -> k.zRot(-90.0F))
                  .keyframe(this.rightFin, k -> k.zRot(90.0F))
         )
         .pose(
            20,
            60,
            Interpolations.EASE_OUT_BOUNCE.scale(0.8F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.body1, k -> k.xRot(-17.5F))
                  .keyframe(this.body2, k -> k.xRot(-30.0F))
                  .keyframe(this.body3, k -> k.xRot(-30.0F))
                  .keyframe(this.body4, k -> k.xRot(-20.0F))
                  .keyframe(this.body5, k -> k.xRot(-10.0F))
                  .keyframe(this.body6, k -> k.rotation(10.0F, 0.0F, 10.0F))
                  .keyframe(this.body7, k -> k.rotation(5.0F, 0.0F, 10.0F))
                  .keyframe(this.body8, k -> k.rotation(4.0F, 0.0F, 10.0F))
                  .keyframe(this.body9, k -> k.rotation(3.0F, 0.0F, 10.0F))
                  .keyframe(this.body10, k -> k.rotation(2.0F, 0.0F, 10.0F))
                  .keyframe(this.head, k -> k.rotation(1.0F, 0.0F, 10.0F))
                  .keyframe(this.headUpper, k -> k.xRot(-40.0F))
                  .keyframe(this.headLower, k -> k.rotation(-100.0F, 0.0F, 0.0F))
                  .keyframe(this.leftFin, k -> k.zRot(0.0F))
                  .keyframe(this.rightFin, k -> k.zRot(0.0F))
         )
         .animate(eel.DEATH);
      this.animateMove(eel.MOVE, ageInTicks);
      this.rotateHead(netHeadYaw);
   }

   private void animateMove(Animation move, float ageInTicks) {
      this.main.f_104207_ = move.getTick() < 24 || move.getTick() > 70;
      HekateLib.push(30, 30, Interpolations.EASE_IN_QUINT.scale(0.8F), Interpolations.EASE_OUT_CUBIC)
         .pose(0, 30, Interpolations.CEIL, ageInTicks, 1.0F, builder -> builder.keyframe(this.main, k -> k.rotation(-20.0F, 0.0F, 0.0F).scale(0.8F)))
         .pose(70, 100, Interpolations.EASE_OUT_CUBIC, ageInTicks, 1.0F, builder -> builder.keyframe(this.main, k -> k.rotation(0.0F, 0.0F, 0.0F).scale(1.0F)))
         .animate(move);
      HekateLib.push(20, 20, Interpolations.EASE_IN_QUINT, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            30,
            Interpolations.CEIL,
            ageInTicks,
            0.4F,
            builder -> builder.keyframe(this.body2, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.9F))
                  .keyframe(this.body3, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.85F))
                  .keyframe(this.body4, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.8F))
                  .keyframe(this.body5, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.75F))
                  .keyframe(this.body6, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.75F))
                  .keyframe(this.body7, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.65F))
                  .keyframe(this.body8, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.6F))
                  .keyframe(this.body9, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.55F))
                  .keyframe(this.body10, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.5F))
                  .keyframe(this.head, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.5F))
                  .keyframe(this.headUpper, k -> k.rotation(-40.0F, 0.0F, 0.0F))
                  .keyframe(this.headLower, k -> k.rotation(5.0F, -90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F))
                  .keyframe(this.leftFin, k -> k.rotation(0.0F, 0.0F, -90.0F))
                  .keyframe(this.rightFin, k -> k.rotation(0.0F, 0.0F, 90.0F))
         )
         .pose(
            70,
            100,
            Interpolations.EASE_OUT_CUBIC,
            ageInTicks,
            0.4F,
            builder -> builder.keyframe(this.body2, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.9F))
                  .keyframe(this.body3, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.85F))
                  .keyframe(this.body4, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.8F))
                  .keyframe(this.body5, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.75F))
                  .keyframe(this.body6, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.75F))
                  .keyframe(this.body7, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.65F))
                  .keyframe(this.body8, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.6F))
                  .keyframe(this.body9, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.55F))
                  .keyframe(this.body10, k -> k.rotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F, -0.5F))
                  .keyframe(this.head, k -> k.rotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -0.5F))
                  .keyframe(this.headUpper, k -> k.rotation(-40.0F, 0.0F, 0.0F))
                  .keyframe(this.headLower, k -> k.rotation(5.0F, -90.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 0.0F))
                  .keyframe(this.leftFin, k -> k.rotation(0.0F, 0.0F, -90.0F))
                  .keyframe(this.rightFin, k -> k.rotation(0.0F, 0.0F, 90.0F))
         )
         .animate(move);
   }

   private void rotateHead(float netHeadYaw) {
      float head = mod.head(netHeadYaw, 0.1F);
      this.body1.f_104204_ += head;
      this.body2.f_104204_ += head;
      this.body3.f_104204_ += head;
      this.body4.f_104204_ += head;
      this.body5.f_104204_ += head;
      this.body6.f_104204_ += head;
      this.body7.f_104204_ += head;
      this.body8.f_104204_ += head;
      this.body9.f_104204_ += head;
      this.body10.f_104204_ += head;
   }
}
