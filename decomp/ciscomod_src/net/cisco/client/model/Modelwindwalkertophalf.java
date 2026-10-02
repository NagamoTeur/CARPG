package net.cisco.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelwindwalkertophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelwindwalkertophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelwindwalkertophalf(ModelPart root) {
      this.Head = root.m_171324_("Head");
      this.Body = root.m_171324_("Body");
      this.RightArm = root.m_171324_("RightArm");
      this.LeftArm = root.m_171324_("LeftArm");
      this.RightLeg = root.m_171324_("RightLeg");
      this.LeftLeg = root.m_171324_("LeftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition Head = partdefinition.m_171599_(
         "Head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(24, 16)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(23, -8)
            .m_171488_(4.3256F, -7.0048F, -4.4F, 0.3F, 1.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(23, -8)
            .m_171488_(-4.6744F, -7.0048F, -4.4F, 0.3F, 1.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(4.5F, -8.0048F, -3.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(-4.8F, -8.0019F, -3.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(4.294F, -9.0048F, -2.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(-4.8F, -9.0019F, -2.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(4.28F, -10.0048F, -0.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, -7)
            .m_171488_(-4.8F, -10.0019F, -0.001F, 0.1F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.1F, -7.0F, -5.0F, 2.4F, 2.0F, 0.2F, new CubeDeformation(-0.01F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(58, 16)
            .m_171488_(-2.0F, -2.0F, 7.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 2)
            .m_171488_(0.0F, 0.0F, 8.1F, 2.0F, 2.0F, 0.0F, new CubeDeformation(-0.2F))
            .m_171514_(59, 29)
            .m_171488_(-1.0F, -1.0F, 7.7F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -6.0F, -13.0F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(8, 53)
            .m_171488_(-0.5F, -1.5F, -0.1F, 4.0F, 1.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(42, 10)
            .m_171488_(-2.5F, -0.5F, -0.1F, 5.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, -7.5F, -4.9F, 0.0F, 0.0F, -0.1745F)
      );
      PartDefinition cube_r3 = Head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(47, 10)
            .m_171488_(-3.5F, -1.5F, -0.1F, 4.0F, 1.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(47, 10)
            .m_171488_(-2.5F, -0.5F, -0.1F, 5.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, -7.5F, -4.9F, 0.0F, 0.0F, 0.1745F)
      );
      PartDefinition cube_r4 = Head.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(28, 5).m_171488_(0.4F, -6.5F, -5.0F, 5.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F)
      );
      PartDefinition cube_r5 = Head.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(28, 5).m_171488_(-1.7F, -0.5F, -0.1F, 5.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, -6.5F, -4.9F, 0.0F, 0.0F, 0.1745F)
      );
      PartDefinition cube_r6 = Head.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(28, 5).m_171488_(-3.0F, -0.7F, -0.1F, 3.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.8F, -5.5F, -4.9F, 0.0F, 0.0F, -0.2182F)
      );
      PartDefinition cube_r7 = Head.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(28, 5).m_171488_(-0.7F, -0.5F, -0.1F, 3.0F, 1.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.2F, -5.5F, -4.9F, 0.0F, 0.0F, 0.1745F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, 11.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 61)
            .m_171488_(-5.0F, 11.5F, -2.7F, 10.0F, 1.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 9)
            .m_171488_(-5.0F, 11.5F, 1.9F, 10.0F, 1.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 48)
            .m_171488_(-5.0F, 11.5F, -2.7F, 1.0F, 1.5F, 5.4F, new CubeDeformation(0.0F))
            .m_171514_(0, 48)
            .m_171488_(4.0F, 11.5F, -2.7F, 1.0F, 1.5F, 5.4F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(48, 48)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(50, 5)
            .m_171488_(-2.0F, -3.0F, -2.5F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.6F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r8 = RightArm.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(39, 0)
            .m_171488_(-0.075F, -1.0F, -2.5F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.1F))
            .m_171514_(0, 48)
            .m_171488_(-0.125F, 0.0F, -3.5F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(-4.475F, -3.5F, 1.499F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition cube_r9 = RightArm.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_().m_171514_(12, 54).m_171488_(-2.5F, -1.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(-1.5F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 54)
            .m_171488_(-1.0F, -3.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.5F))
            .m_171514_(50, 5)
            .m_171488_(1.0F, -3.0F, -2.5F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.6F))
            .m_171514_(32, 48)
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r10 = LeftArm.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 48)
            .m_171488_(18.875F, 0.0F, -3.5F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.1F))
            .m_171514_(39, 0)
            .m_171488_(18.925F, -1.0F, -2.5F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(-14.475F, -3.5F, 1.499F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(17, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r11 = RightLeg.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_()
            .m_171514_(27, -4)
            .m_171488_(0.425F, 0.5F, -3.0995F, 0.3F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(27, -4)
            .m_171488_(0.325F, -1.5F, -1.9005F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(27, -4)
            .m_171488_(0.425F, -0.5F, -2.5995F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.425F, 7.0F, 1.8995F, 0.2182F, 0.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r12 = LeftLeg.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_()
            .m_171514_(27, -4)
            .m_171488_(-0.075F, 0.5F, -3.0995F, 0.3F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(27, -4)
            .m_171488_(0.225F, -1.5F, -1.9005F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(27, -4)
            .m_171488_(0.125F, -0.5F, -2.5995F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.775F, 7.0F, 1.8995F, 0.2182F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.Head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.Body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.RightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.RightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.RightArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
      this.LeftLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.Head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.Head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.LeftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * limbSwingAmount;
      this.RightLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
