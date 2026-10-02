package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.Anglerfish;
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
import net.minecraft.util.Mth;

public class ModelAnglerfish extends EntityModel<Anglerfish> {
   public final ModelPart main;
   public final ModelPart head;
   public final ModelPart headUpper;
   public final ModelPart headLower;
   public final ModelPart body;
   public final ModelPart tail1;
   public final ModelPart tail2;
   public final ModelPart tail3;
   public final ModelPart tail4;
   public final ModelPart tail5;
   public final ModelPart tail6;
   public final ModelPart leftFin;
   public final ModelPart rightFin;
   public final ModelPart lamp1;
   public final ModelPart lamp2;
   public final ModelPart lamp3;
   public final ModelPart lamp4;
   public final ModelPart lamp5;

   public ModelAnglerfish(ModelPart root) {
      this.main = root.m_171324_("main");
      this.head = this.main.m_171324_("head");
      this.body = this.main.m_171324_("body");
      this.headUpper = this.head.m_171324_("headTop");
      this.headLower = this.head.m_171324_("headBottom");
      this.leftFin = this.body.m_171324_("leftFin");
      this.rightFin = this.body.m_171324_("rightFin");
      this.tail1 = this.body.m_171324_("tail1");
      this.tail2 = this.tail1.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.tail5 = this.tail4.m_171324_("tail5");
      this.tail6 = this.tail5.m_171324_("tail6");
      this.lamp1 = this.headUpper.m_171324_("lamp1");
      this.lamp2 = this.lamp1.m_171324_("lamp2");
      this.lamp3 = this.lamp2.m_171324_("lamp3");
      this.lamp4 = this.lamp3.m_171324_("lamp4");
      this.lamp5 = this.lamp4.m_171324_("lamp5");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 5.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
      PartDefinition head = main.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 0.0F, -5.0F, 0.6109F, 0.0F, 0.0F));
      PartDefinition headTop = head.m_171599_(
         "headTop",
         CubeListBuilder.m_171558_()
            .m_171514_(50, 45)
            .m_171488_(-7.0F, -16.0F, 0.0F, 14.0F, 16.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(59, 10)
            .m_171488_(1.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 0)
            .m_171488_(-6.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 55)
            .m_171488_(-7.0F, -16.0F, -8.0F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, -5.0F)
      );
      PartDefinition lamp1 = headTop.m_171599_(
         "lamp1",
         CubeListBuilder.m_171558_().m_171514_(107, 0).m_171488_(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -16.0F, 9.0F, -0.2182F, 0.0F, 0.0F)
      );
      PartDefinition lamp2 = lamp1.m_171599_(
         "lamp2",
         CubeListBuilder.m_171558_().m_171514_(107, 5).m_171488_(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition lamp3 = lamp2.m_171599_(
         "lamp3",
         CubeListBuilder.m_171558_().m_171514_(107, 10).m_171488_(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F)
      );
      PartDefinition lamp4 = lamp3.m_171599_(
         "lamp4",
         CubeListBuilder.m_171558_().m_171514_(107, 15).m_171488_(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.9163F, 0.0F, 0.0F)
      );
      PartDefinition lamp5 = lamp4.m_171599_(
         "lamp5",
         CubeListBuilder.m_171558_().m_171514_(104, 20).m_171488_(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F)
      );
      PartDefinition headBottom = head.m_171599_(
         "headBottom",
         CubeListBuilder.m_171558_()
            .m_171514_(44, 16)
            .m_171488_(-7.0F, -2.0F, -14.0F, 14.0F, 3.0F, 16.0F, new CubeDeformation(0.11F))
            .m_171514_(0, 32)
            .m_171488_(-7.0F, -9.0F, -14.0F, 14.0F, 7.0F, 16.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(0.0F, 2.0F, -5.0F)
      );
      PartDefinition body = main.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-6.0F, -8.0F, -11.0F, 12.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 55)
            .m_171488_(0.0F, -18.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 19)
            .m_171488_(0.0F, 8.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition tail1 = body.m_171599_(
         "tail1",
         CubeListBuilder.m_171558_()
            .m_171514_(68, 73)
            .m_171488_(-4.0F, -7.0F, 0.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(76, 27)
            .m_171488_(0.0F, -17.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(0.0F, 7.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 5.0F)
      );
      PartDefinition tail2 = tail1.m_171599_(
         "tail2",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 79)
            .m_171488_(-2.0F, -6.0F, 0.0F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 0)
            .m_171488_(0.0F, -15.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 0)
            .m_171488_(0.0F, 6.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 8.0F)
      );
      PartDefinition tail3 = tail2.m_171599_(
         "tail3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 73)
            .m_171488_(0.0F, -12.0F, 0.0F, 0.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 6.0F)
      );
      PartDefinition tail4 = tail3.m_171599_(
         "tail4",
         CubeListBuilder.m_171558_().m_171514_(56, 75).m_171488_(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 6.0F)
      );
      PartDefinition tail5 = tail4.m_171599_(
         "tail5",
         CubeListBuilder.m_171558_().m_171514_(44, 75).m_171488_(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 6.0F)
      );
      PartDefinition tail6 = tail5.m_171599_(
         "tail6",
         CubeListBuilder.m_171558_().m_171514_(32, 73).m_171488_(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 6.0F)
      );
      PartDefinition leftFin = body.m_171599_(
         "leftFin",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 103)
            .m_171488_(0.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 103)
            .m_171488_(0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.0F, 0.0F, -4.5F, 0.0F, 0.5672F, 0.0F)
      );
      PartDefinition rightFin = body.m_171599_(
         "rightFin",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 103)
            .m_171488_(-1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 103)
            .m_171488_(-0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-6.0F, 0.0F, -4.5F, 0.0F, -0.6981F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      poseStack.m_85836_();
      poseStack.m_85841_(1.5F, 1.5F, 1.5F);
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }

   public void setupAnim(Anglerfish anglerfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(
         new ModelPart[]{
            this.main,
            this.head,
            this.headUpper,
            this.headLower,
            this.body,
            this.tail1,
            this.tail2,
            this.tail3,
            this.tail4,
            this.tail5,
            this.tail6,
            this.leftFin,
            this.rightFin,
            this.lamp1,
            this.lamp2,
            this.lamp3,
            this.lamp4,
            this.lamp5
         }
      );
      HekateLib.push(ageInTicks, 0.1F, mod.idle(limbSwingAmount, 5.0F), Mode.DEFINITION)
         .keyframe(this.main, k -> k.xRot(1.0F, -15.0F))
         .keyframe(this.head, k -> k.xRot(-1.0F, -35.0F))
         .keyframe(this.body, k -> k.yRot(5.0F, 0.0F))
         .keyframe(this.tail1, k -> k.yRot(7.0F, 0.0F, -0.05F))
         .keyframe(this.tail2, k -> k.yRot(9.0F, 0.0F, -0.1F))
         .keyframe(this.tail3, k -> k.yRot(11.0F, 0.0F, -0.15F))
         .keyframe(this.tail4, k -> k.yRot(13.0F, 0.0F, -0.2F))
         .keyframe(this.tail5, k -> k.yRot(15.0F, 0.0F, -0.25F))
         .keyframe(this.tail6, k -> k.yRot(15.0F, 0.0F, -0.3F))
         .keyframe(this.leftFin, k -> k.yRot(15.0F, -40.0F, -0.1F).zRot(0.0F, -30.0F, -0.1F))
         .keyframe(this.rightFin, k -> k.yRot(-15.0F, 40.0F, -0.1F).zRot(0.0F, 30.0F, -0.1F))
         .keyframe(this.lamp1, k -> k.xRot(2.0F, -5.0F))
         .keyframe(this.lamp2, k -> k.xRot(4.0F, -10.0F, -0.05F))
         .keyframe(this.lamp3, k -> k.xRot(6.0F, -30.0F, -0.1F))
         .keyframe(this.lamp4, k -> k.xRot(8.0F, -50.0F, -0.15F))
         .keyframe(this.lamp5, k -> k.xRot(10.0F, -30.0F, -0.2F));
      HekateLib.push(ageInTicks, 0.7F, mod.move(limbSwingAmount, 5.0F), Mode.ADDITION)
         .keyframe(this.main, k -> k.xRot(1.0F, -15.0F))
         .keyframe(this.head, k -> k.xRot(-1.0F, -35.0F).yRot(-10.0F, 0.0F))
         .keyframe(this.body, k -> k.yRot(5.0F, 0.0F))
         .keyframe(this.tail1, k -> k.yRot(10.0F, 0.0F, -0.05F))
         .keyframe(this.tail2, k -> k.yRot(12.5F, 0.0F, -0.1F))
         .keyframe(this.tail3, k -> k.yRot(15.0F, 0.0F, -0.15F))
         .keyframe(this.tail4, k -> k.yRot(20.0F, 0.0F, -0.2F))
         .keyframe(this.tail5, k -> k.yRot(25.0F, 0.0F, -0.25F))
         .keyframe(this.tail6, k -> k.yRot(30.0F, 0.0F, -0.3F))
         .keyframe(this.leftFin, k -> k.yRot(15.0F, -40.0F, -0.1F).zRot(0.0F, -30.0F, -0.1F))
         .keyframe(this.rightFin, k -> k.yRot(-15.0F, 40.0F, -0.1F).zRot(0.0F, 30.0F, -0.1F))
         .keyframe(this.lamp1, k -> k.xRot(2.0F, -5.0F))
         .keyframe(this.lamp2, k -> k.xRot(4.0F, -10.0F, -0.05F))
         .keyframe(this.lamp3, k -> k.xRot(6.0F, -30.0F, -0.1F))
         .keyframe(this.lamp4, k -> k.xRot(8.0F, -50.0F, -0.15F))
         .keyframe(this.lamp5, k -> k.xRot(10.0F, -30.0F, -0.2F));
      HekateLib.push(14, 10, Interpolations.EASE_OUT_CUBIC, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            20,
            Interpolations.CEIL,
            ageInTicks,
            0.7F,
            builder -> builder.keyframe(this.headUpper, k -> k.xRot(1.0F, 15.0F, 4.0F, 0.0F))
                  .keyframe(this.headLower, k -> k.xRot(-1.0F, -15.0F, 4.0F, 0.0F))
                  .keyframe(this.body, k -> k.yRot(10.0F, 0.0F))
                  .keyframe(this.tail1, k -> k.yRot(15.0F, 0.0F, -0.05F))
                  .keyframe(this.tail2, k -> k.yRot(20.0F, 0.0F, -0.1F))
                  .keyframe(this.tail3, k -> k.yRot(25.0F, 0.0F, -0.15F))
                  .keyframe(this.tail4, k -> k.yRot(30.0F, 0.0F, -0.2F))
                  .keyframe(this.tail5, k -> k.yRot(35.0F, 0.0F, -0.25F))
                  .keyframe(this.tail6, k -> k.yRot(40.0F, 0.0F, -0.3F))
         )
         .pose(
            20,
            40,
            Interpolations.EASE_OUT_CUBIC.scale(0.15F),
            ageInTicks,
            0.7F,
            builder -> builder.keyframe(this.headUpper, k -> k.xRot(-25.0F)).keyframe(this.headLower, k -> k.xRot(25.0F))
         )
         .animate(anglerfish.ATTACK);
      float groundMod = Mth.m_14179_(HekateLib.getPartialTicks(), anglerfish.groundModLerp, anglerfish.groundMod);
      float headMod = 1.0F - groundMod;
      this.main.f_104201_ = 10.0F * groundMod;
      this.main.f_104205_ = this.main.f_104205_ + (float)Math.toRadians((double)(90.0F * groundMod));
      this.main.f_104203_ = this.main.f_104203_ + mod.head(headPitch, headMod);
      this.main.f_104204_ = this.main.f_104204_ + mod.head(netHeadYaw, headMod);
      this.main.f_104203_ = this.main.f_104203_ + mod.head(netHeadYaw, 1.0F - headMod);
   }
}
