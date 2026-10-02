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

public class Modelfixedwind<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelfixedwind"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelfixedwind(ModelPart root) {
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
            .m_171514_(32, 0)
            .m_171488_(-5.1426F, -7.0019F, -4.001F, 0.2F, 1.0F, 9.1F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(4.3256F, -7.0048F, -4.001F, 0.3F, 1.0F, 9.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(4.5F, -8.0048F, -3.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(-5.2426F, -8.0019F, -3.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(4.294F, -9.0048F, -2.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(-5.1426F, -9.0019F, -2.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(32, 0)
            .m_171488_(4.28F, -10.0048F, -0.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(24, -7)
            .m_171488_(-5.0426F, -10.0019F, -0.001F, 0.1F, 1.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171514_(25, 0)
            .m_171488_(4.28F, -11.0048F, 1.999F, 0.1F, 1.0F, 7.0F, new CubeDeformation(0.2F))
            .m_171514_(25, 0)
            .m_171488_(-5.1426F, -11.0019F, 1.999F, 0.2F, 1.0F, 7.0F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(-2.98F, -7.0F, -5.0F, 1.68F, 2.0F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(1.32F, -7.0F, -5.0F, 1.68F, 2.0F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(-4.9F, -9.0F, -5.0F, 1.5F, 3.0F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(3.3F, -9.0F, -5.0F, 1.5F, 3.0F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -8.18F, -5.0F, 2.0F, 0.78F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -4.78F, -5.0F, 2.0F, 0.78F, 0.2F, new CubeDeformation(0.2F))
            .m_171514_(60, 30)
            .m_171488_(-1.0F, -7.0F, -5.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(1.5F, -2.0F, -11.1662F, 1.0F, 3.0F, 0.2F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(-6.3669F, -7.4857F, -6.9856F, 0.0F, -1.6144F, 0.0436F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(2.5F, -7.0F, 0.0F, 1.0F, 3.0F, 0.2F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(-4.9645F, -1.999F, -7.4976F, 0.0F, -1.6144F, 0.0436F)
      );
      PartDefinition cube_r3 = Head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(24, -4).m_171488_(0.0F, -1.0F, -4.85F, 0.0F, 2.0F, 9.6F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(-0.1244F, -7.0048F, 4.998F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(26, 0)
            .m_171488_(-4.7F, -4.5F, -1.001F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(41, 8)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(26, 0)
            .m_171488_(-4.8F, -3.5F, -2.001F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(22, 1)
            .m_171488_(-4.0F, -3.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.5F))
            .m_171514_(26, 1)
            .m_171488_(-4.0F, -2.0F, -2.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.5F))
            .m_171514_(26, 1)
            .m_171488_(-4.0F, -2.0F, 1.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.5F))
            .m_171514_(27, 0)
            .m_171488_(-4.0F, -2.0F, -1.8F, 1.0F, 2.0F, 3.3F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(26, 0)
            .m_171488_(4.3F, -4.5F, -1.001F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(26, 0)
            .m_171488_(4.4F, -3.5F, -2.001F, 0.2F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(29, 0)
            .m_171488_(3.0F, -2.0F, -1.8F, 1.0F, 2.0F, 3.3F, new CubeDeformation(0.5F))
            .m_171514_(27, 1)
            .m_171488_(-1.0F, -2.0F, 1.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.5F))
            .m_171514_(26, 1)
            .m_171488_(-1.0F, -2.0F, -2.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.5F))
            .m_171514_(24, 0)
            .m_171488_(-1.0F, -3.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.5F))
            .m_171514_(40, 8)
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(27, 0)
            .m_171488_(-2.45F, 6.5F, -1.001F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(27, 0)
            .m_171488_(-2.38F, 5.5F, -0.001F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(27, 0)
            .m_171488_(-2.55F, 7.5F, -2.001F, 0.3F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(48, 0)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(27, 0)
            .m_171488_(2.5F, 6.5F, -1.001F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(27, 0)
            .m_171488_(2.3F, 5.5F, -0.001F, 0.2F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(27, 0)
            .m_171488_(2.6F, 7.5F, -2.001F, 0.3F, 1.0F, 5.0F, new CubeDeformation(0.3F))
            .m_171514_(48, 0)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
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
