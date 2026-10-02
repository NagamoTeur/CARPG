package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entities.Maw;
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

public class ModelMaw extends EntityModel<Maw> {
   public final ModelPart main;
   public final ModelPart head;
   public final ModelPart headUpper;
   public final ModelPart headLower;
   public final ModelPart body;
   public final ModelPart body2;
   public final ModelPart body3;
   public final ModelPart rightFin;
   public final ModelPart leftFin;

   public ModelMaw(ModelPart root) {
      this.main = root.m_171324_("main");
      this.head = this.main.m_171324_("head");
      this.headUpper = this.head.m_171324_("head_upper");
      this.headLower = this.head.m_171324_("head_lower");
      this.body = this.main.m_171324_("body");
      this.body2 = this.body.m_171324_("body2");
      this.body3 = this.body2.m_171324_("body3");
      this.rightFin = this.body.m_171324_("right_fin");
      this.leftFin = this.body.m_171324_("left_fin");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 15.0F, 150.0F));
      PartDefinition head = main.m_171599_("head", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, -147.0F));
      PartDefinition head_upper = head.m_171599_("head_upper", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition cube_r1 = head_upper.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 66)
            .m_171488_(-10.0F, 0.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 60)
            .m_171488_(-10.0F, -6.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F)
      );
      PartDefinition cube_r2 = head_upper.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(66, 19).m_171488_(0.01F, -14.4226F, -20.0937F, 0.0F, 8.0F, 23.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, -3.75F, -0.4363F, 0.0F, 0.0F)
      );
      PartDefinition head_lower = head.m_171599_("head_lower", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition cube_r3 = head_lower.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-10.5F, -6.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 30)
            .m_171488_(-10.5F, 0.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition body = main.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 16)
            .m_171488_(-9.0F, -6.0F, 2.0F, 18.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 98)
            .m_171488_(0.0F, -14.0F, 2.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -150.0F)
      );
      PartDefinition body2 = body.m_171599_("body2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 16.0F));
      PartDefinition cube_r4 = body2.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.0F, -11.0F, -1.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 90)
            .m_171488_(-7.0F, -3.0F, -3.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F)
      );
      PartDefinition body3 = body2.m_171599_("body3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 12.0F));
      PartDefinition cube_r5 = body3.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 76)
            .m_171488_(0.0F, -2.0F, 5.0F, 0.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
            .m_171514_(82, 96)
            .m_171488_(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_fin = body.m_171599_("left_fin", CubeListBuilder.m_171558_(), PartPose.m_171419_(9.0F, 3.0F, 10.0F));
      PartDefinition cube_r6 = left_fin.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(82, 52).m_171488_(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F)
      );
      PartDefinition right_fin = body.m_171599_("right_fin", CubeListBuilder.m_171558_(), PartPose.m_171419_(-9.0F, 3.0F, 10.0F));
      PartDefinition cube_r7 = right_fin.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(66, 0).m_171488_(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void translate(PoseStack pose) {
      this.main.m_104299_(pose);
      this.head.m_104299_(pose);
      this.headLower.m_104299_(pose);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void setupAnim(Maw maw, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      HekateLib.reset(new ModelPart[]{this.main, this.head, this.headUpper, this.headLower, this.body, this.body2, this.body3, this.rightFin, this.leftFin});
      HekateLib.push(ageInTicks, 0.06F, mod.idle(limbSwingAmount, 10.0F), Mode.DEFINITION)
         .keyframe(this.headUpper, k -> k.rotation(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F))
         .keyframe(this.body2, k -> k.xRot(-8.0F, -8.0F).yRot(-10.0F, 0.0F, 2.0F, 0.0F).zRot(4.0F, 0.0F))
         .keyframe(this.body3, k -> k.xRot(-8.0F, -8.0F, -0.1F).yRot(-10.0F, 0.0F, 2.0F, -0.1F).zRot(4.0F, 0.0F, -0.1F));
      HekateLib.push(limbSwing, 0.6F, mod.move(limbSwingAmount, 10.0F), Mode.ADDITION)
         .keyframe(this.main, k -> k.zRot(-4.0F, 0.0F))
         .keyframe(this.headUpper, k -> k.xRot(-4.0F, 12.0F))
         .keyframe(this.body, k -> k.yRot(-12.0F, 0.0F))
         .keyframe(this.body2, k -> k.yRot(-12.0F, 0.0F, -0.1F))
         .keyframe(this.body3, k -> k.yRot(-12.0F, 0.0F, -0.2F))
         .keyframe(this.rightFin, k -> k.yRot(40.0F, 0.0F).zRot(-12.0F, 10.0F, -0.3F))
         .keyframe(this.leftFin, k -> k.yRot(40.0F, 0.0F).zRot(-12.0F, -10.0F, -0.3F));
      HekateLib.push(1, 13, Interpolations.EASE_IN_CUBIC, Interpolations.EASE_OUT_CUBIC)
         .pose(
            0,
            2,
            Interpolations.EASE_OUT_CUBIC,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.head, k -> k.xRot(0.0F, -10.0F)).keyframe(this.headUpper, k -> k.xRot(0.0F, 50.0F))
         )
         .pose(
            2,
            20,
            Interpolations.EASE_IN_QUART.scale(0.15F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.head, k -> k.xRot(0.0F, 10.0F)).keyframe(this.headUpper, k -> k.xRot(0.0F, -22.0F))
         )
         .animate(maw.ATTACK);
      HekateLib.push(6, 0, Interpolations.EASE_OUT_CIRCLE, Interpolations.CEIL)
         .pose(
            0,
            6,
            Interpolations.CEIL,
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.xRot(8.0F))
                  .keyframe(this.head, k -> k.xRot(0.0F, -50.0F))
                  .keyframe(this.body2, k -> k.xRot(20.0F))
                  .keyframe(this.body3, k -> k.xRot(40.0F))
                  .keyframe(this.headUpper, k -> k.xRot(0.0F, 50.0F))
         )
         .pose(
            6,
            40,
            Interpolations.EASE_OUT_BOUNCE.scale(0.6F),
            ageInTicks,
            1.0F,
            builder -> builder.keyframe(this.main, k -> k.xRot(0.0F))
                  .keyframe(this.head, k -> k.xRot(0.0F, 10.0F))
                  .keyframe(this.body2, k -> k.xRot(0.0F))
                  .keyframe(this.body3, k -> k.xRot(0.0F))
                  .keyframe(this.headUpper, k -> k.xRot(0.0F, -22.0F))
         )
         .animate(maw.DEATH);
   }
}
