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

public class Modeleagletophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modeleagletophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modeleagletophalf(ModelPart root) {
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
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(21, 58)
            .m_171488_(-4.5F, 10.0F, -3.1F, 9.0F, 2.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(31, 61)
            .m_171488_(-4.0F, 10.0F, 2.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 0)
            .m_171488_(4.0F, 10.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 0)
            .m_171488_(-5.0F, 10.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 20)
            .m_171488_(-4.0F, 1.0F, -3.0F, 8.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 19)
            .m_171488_(-4.0F, 0.0F, -3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 21)
            .m_171488_(-4.0F, 0.0F, 1.6F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Body.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(28, 40)
            .m_171488_(-4.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 40)
            .m_171488_(0.0F, -4.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 40)
            .m_171488_(4.5F, 5.0F, -0.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 3.0F, 2.5F, 0.0F, 0.0F, 0.7418F)
      );
      PartDefinition cube_r2 = Body.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(58, 19).m_171488_(-1.0F, -1.0F, -0.35F, 2.0F, 2.0F, 0.7F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 11.0F, -3.45F, -0.0436F, 0.0F, 0.7854F)
      );
      PartDefinition dagger = Body.m_171599_(
         "dagger",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 34)
            .m_171488_(-5.0F, 7.5F, 3.25F, 7.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.5F, 8.0F, 3.25F, 7.5F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-6.0F, 8.5F, 3.25F, 8.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.0F, 7.0F, 3.25F, 6.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(44, 39)
            .m_171488_(1.5F, 6.5F, 3.0F, 0.5F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 33)
            .m_171488_(2.0F, 7.0F, 3.0F, 0.5F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 35)
            .m_171488_(2.5F, 7.5F, 3.0F, 2.5F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(38, 0)
            .m_171488_(-1.0F, -3.0F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 52)
            .m_171488_(-4.0F, 6.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F))
            .m_171514_(12, 32)
            .m_171488_(-4.0F, 4.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 54)
            .m_171488_(-2.0F, -3.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 40)
            .m_171488_(-1.0F, -3.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 37)
            .m_171488_(0.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 32)
            .m_171488_(-4.0F, -3.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(50, 0)
            .m_171488_(3.0F, 6.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 43)
            .m_171488_(-1.0F, 4.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 0)
            .m_171488_(-1.0F, -3.0F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F))
            .m_171555_(false)
            .m_171514_(48, 32)
            .m_171488_(2.0F, -3.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 42)
            .m_171488_(1.0F, -3.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 55)
            .m_171488_(0.0F, -3.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 50)
            .m_171488_(-1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
            .m_171514_(23, 48)
            .m_171488_(-1.3F, 7.2F, -2.8F, 2.5F, 1.0F, 0.1F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F))
            .m_171555_(false)
            .m_171514_(23, 48)
            .m_171488_(-1.25F, 7.2F, -2.8F, 2.5F, 1.0F, 0.1F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
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
