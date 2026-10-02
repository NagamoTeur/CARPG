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

public class Modelbrightsteeltophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelbrightsteeltophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelbrightsteeltophalf(ModelPart root) {
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
            .m_171514_(59, 52)
            .m_171488_(-1.0F, -9.3F, -5.2F, 2.0F, 9.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(58, 36)
            .m_171488_(-1.0F, -9.3F, 4.8F, 2.0F, 10.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(-5.0F, -5.5F, -5.0F, 5.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(-5.0F, -3.5F, -5.0F, 5.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(0.0F, -5.5F, -5.0F, 5.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(0.0F, -3.5F, -5.0F, 5.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -7.5F, -5.0F, 0.0F, 0.0F, 0.7418F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(58, 37).m_171488_(-1.0F, -4.8F, -9.4F, 2.0F, 9.5F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.1F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(8, 52)
            .m_171488_(-4.5F, -0.5F, -3.0F, 9.0F, 7.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 32)
            .m_171488_(-4.5F, 7.0F, -3.0F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 61)
            .m_171488_(-4.5F, 10.0F, -3.6F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 0)
            .m_171488_(-4.5F, 10.0F, 2.4F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 52)
            .m_171488_(-4.0F, 0.0F, 2.0F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 55)
            .m_171488_(-5.0F, 10.0F, -3.6F, 0.5F, 2.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(15, 55)
            .m_171488_(4.3F, 10.0F, -3.6F, 0.5F, 2.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(-0.5F, 0.0F, -3.6F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(0.5F, 4.0F, -3.6F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(1.5F, 7.0F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(2.5F, 8.0F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(-3.5F, 8.0F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(-3.5F, 2.5F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(2.5F, 2.5F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(1.5F, 3.5F, -3.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(-2.5F, 3.5F, -3.6F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 44)
            .m_171488_(3.5F, 0.6F, -3.2F, 1.0F, 9.3F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 44)
            .m_171488_(-4.5F, 0.6F, -3.2F, 1.0F, 9.3F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 1)
            .m_171488_(-2.5F, 7.0F, -3.6F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(0.5F, 0.5F, -3.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-1.5F, 0.5F, -3.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-3.5F, 0.5F, -3.4F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(2.5F, 0.5F, -3.65F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-3.0F, 7.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-2.0F, 2.0F, 1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(1.0F, 2.0F, 1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-3.0F, 3.0F, 1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(2.0F, 3.0F, 1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(3.0F, 5.0F, 1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-4.0F, 5.0F, 1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, 3.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(3.0F, 3.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(2.0F, 1.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-3.0F, 1.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, 3.0F, 1.5F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(1.0F, 7.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-1.0F, 8.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 59)
            .m_171488_(-1.0F, 1.0F, 1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 6)
            .m_171488_(-4.5F, -0.5F, -3.4F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r3 = Body.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(58, 1).m_171488_(-1.5F, 4.0F, -3.6F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(41, 43)
            .m_171488_(-1.0F, -4.0F, -3.2F, 1.0F, 3.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(41, 42)
            .m_171488_(-2.0F, -3.5F, -3.2F, 1.0F, 3.5F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(39, 32)
            .m_171488_(-4.5F, -3.0F, -3.1F, 3.0F, 4.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(7, 42)
            .m_171488_(-4.5F, 5.0F, -3.1F, 3.0F, 4.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r4 = RightArm.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(45, 47).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.0F, -1.0F, 0.0F, -0.7418F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(41, 42)
            .m_171488_(0.0F, -4.0F, -3.2F, 1.0F, 3.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(41, 42)
            .m_171488_(1.0F, -3.5F, -3.2F, 1.0F, 3.5F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(8, 32)
            .m_171488_(1.5F, 5.0F, -3.1F, 3.0F, 4.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(36, 41)
            .m_171488_(1.5F, -3.0F, -3.1F, 3.0F, 4.0F, 6.5F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r5 = LeftArm.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(45, 47).m_171488_(17.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-14.0F, -1.0F, 0.0F, -0.7418F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).m_171555_(false),
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
