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

public class Modelfellkingnew<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelfellkingnew"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelfellkingnew(ModelPart root) {
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
            .m_171514_(0, 17)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(33, 0)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
            .m_171514_(29, 30)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171514_(75, 63)
            .m_171488_(-4.1F, 11.2F, -2.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(75, 63)
            .m_171488_(-4.1F, 11.2F, 2.6F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(50, 17)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 47)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition Amethyst_r1 = RightArm.m_171599_(
         "Amethyst_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 1)
            .m_171488_(-2.0F, -1.0F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
            .m_171514_(1, 1)
            .m_171488_(-2.0F, -1.0F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
         PartPose.m_171423_(-2.0F, -1.0F, -3.5F, 0.0F, 0.0F, 0.5672F)
      );
      PartDefinition RightArmShoulderPad_r1 = RightArm.m_171599_(
         "RightArmShoulderPad_r1",
         CubeListBuilder.m_171558_().m_171514_(58, 0).m_171488_(-3.0F, -0.75F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(-1.5F, -2.0F, 0.0F, 0.0F, 0.0F, -0.384F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(50, 17)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(47, 47)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .m_171555_(false),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r1 = LeftArm.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 1)
            .m_171488_(-0.5923F, -1.0718F, 4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
            .m_171514_(1, 1)
            .m_171488_(-0.5923F, -1.0718F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
         PartPose.m_171423_(2.3923F, -1.9282F, -2.5F, 0.0F, 0.0F, 1.0472F)
      );
      PartDefinition LeftArmShoulderPad_r1 = LeftArm.m_171599_(
         "LeftArmShoulderPad_r1",
         CubeListBuilder.m_171558_().m_171514_(58, 0).m_171480_().m_171488_(-2.0F, -0.75F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)).m_171555_(false),
         PartPose.m_171423_(1.5F, -2.0F, 0.0F, 0.0F, 0.0F, 0.384F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(30, 47).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition Amethyst_r2 = RightLeg.m_171599_(
         "Amethyst_r2",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(-1.0914F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)),
         PartPose.m_171423_(-0.5086F, 9.7104F, -2.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightLegThighGuard_r1 = RightLeg.m_171599_(
         "RightLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(54, 34).m_171488_(-1.9F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.41F)),
         PartPose.m_171423_(-1.0F, 3.5F, 0.0F, 0.0F, 0.0F, 0.2705F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(30, 47).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition Amethyst_r3 = LeftLeg.m_171599_(
         "Amethyst_r3",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(1.6086F, -4.7104F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)),
         PartPose.m_171423_(-4.3086F, 9.7104F, -2.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition LeftLegThighGuard_r1 = LeftLeg.m_171599_(
         "LeftLegThighGuard_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(54, 34)
            .m_171480_()
            .m_171488_(-1.3F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.41F))
            .m_171555_(false),
         PartPose.m_171423_(1.2F, 3.5F, 0.0F, 0.0F, 0.0F, -0.2705F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
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
