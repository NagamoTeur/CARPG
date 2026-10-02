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

public class ModelFallenBottomHalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "model_fallen_bottom_half"), "main");
   public final ModelPart Body;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public ModelFallenBottomHalf(ModelPart root) {
      this.Body = root.m_171324_("Body");
      this.RightLeg = root.m_171324_("RightLeg");
      this.LeftLeg = root.m_171324_("LeftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(11, 32)
            .m_171488_(-2.2F, 13.0F, -3.7F, 1.0F, 6.0F, 7.2F, new CubeDeformation(0.0F))
            .m_171514_(24, 8)
            .m_171488_(-1.7F, 13.1F, -3.5F, 3.5F, 1.0F, 6.9F, new CubeDeformation(0.0F))
            .m_171514_(32, 43)
            .m_171488_(-1.4F, 13.5F, -3.3F, 2.8F, 6.5F, 6.6F, new CubeDeformation(0.0F))
            .m_171514_(34, 2)
            .m_171488_(1.2F, 13.0F, -3.7F, 1.0F, 6.0F, 7.2F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Body.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(47, 31).m_171488_(-0.5F, -1.0F, -3.75F, 1.0F, 2.0F, 7.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.1946F, 19.489F, 0.05F, 0.0F, 0.0F, 0.6545F)
      );
      PartDefinition cube_r2 = Body.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(47, 54).m_171488_(-0.5F, 0.0044F, -3.75F, 1.0F, 2.0F, 7.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.8033F, 18.6956F, 0.05F, 0.0F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r3 = Body.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(40, 15).m_171488_(-0.85F, -0.85F, -3.75F, 1.5F, 1.5F, 7.1F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.05F, 20.35F, 0.25F, 0.0F, 0.0F, 0.7418F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
            .m_171514_(0, 32)
            .m_171488_(-4.1F, 1.0F, -3.4F, 1.7F, 8.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 44)
            .m_171488_(-2.4F, 1.0F, -3.4F, 1.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 0)
            .m_171488_(-1.6F, 1.0F, -3.3F, 1.5F, 1.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 0)
            .m_171488_(-1.6F, 1.7F, -3.2F, 1.5F, 4.0F, 6.5F, new CubeDeformation(0.0F))
            .m_171514_(1, 5)
            .m_171488_(-4.4F, 2.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 7)
            .m_171488_(-4.2F, 3.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 26)
            .m_171488_(-4.6F, 8.0F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 26)
            .m_171488_(-4.6F, 1.5F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 25)
            .m_171488_(-4.6F, 6.5F, -1.5F, 1.0F, 1.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(26, 23)
            .m_171488_(-4.6F, 6.5F, 1.0F, 1.0F, 1.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(28, 28)
            .m_171488_(-4.6F, 6.0F, -2.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 27)
            .m_171488_(-4.6F, 2.5F, -2.0F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(27, 27)
            .m_171488_(-4.6F, 2.5F, 1.5F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(30, 24)
            .m_171488_(-4.6F, 2.0F, -1.5F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(27, 25)
            .m_171488_(-4.6F, 3.0F, -2.5F, 1.0F, 3.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(30, 25)
            .m_171488_(-4.6F, 3.0F, 2.0F, 1.0F, 3.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(27, 25)
            .m_171488_(-4.6F, 6.0F, 1.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 23)
            .m_171488_(-4.6F, 2.0F, 1.0F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(1, 7)
            .m_171488_(-4.2F, 3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r4 = RightLeg.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(45, 0).m_171488_(-1.25F, -0.5F, -3.5F, 2.5F, 1.0F, 6.7F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.65F, 5.5F, 0.2F, 0.0F, 0.0F, -0.48F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 7)
            .m_171488_(3.2F, 3.0F, 0.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 23)
            .m_171488_(3.6F, 6.5F, 1.0F, 1.0F, 1.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(26, 25)
            .m_171488_(3.6F, 6.5F, -1.5F, 1.0F, 1.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(32, 23)
            .m_171488_(3.6F, 2.0F, 1.0F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(1, 5)
            .m_171488_(3.4F, 2.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 28)
            .m_171488_(3.6F, 6.0F, -2.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 27)
            .m_171488_(3.6F, 2.5F, -2.0F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(27, 27)
            .m_171488_(3.6F, 2.5F, 1.5F, 1.0F, 0.5F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(27, 25)
            .m_171488_(3.6F, 6.0F, 1.0F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 25)
            .m_171488_(3.6F, 3.0F, -2.5F, 1.0F, 3.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(30, 24)
            .m_171488_(3.6F, 2.0F, -1.5F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(30, 25)
            .m_171488_(3.6F, 3.0F, 2.0F, 1.0F, 3.0F, 0.5F, new CubeDeformation(0.0F))
            .m_171514_(1, 7)
            .m_171488_(3.2F, 3.0F, -2.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 26)
            .m_171488_(3.6F, 1.5F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 0)
            .m_171488_(0.0F, 1.7F, -3.2F, 1.5F, 4.0F, 6.3F, new CubeDeformation(0.0F))
            .m_171514_(46, 22)
            .m_171488_(1.4F, 1.0F, -3.4F, 1.0F, 7.0F, 6.7F, new CubeDeformation(0.0F))
            .m_171514_(10, 8)
            .m_171488_(-0.1F, 1.0F, -3.3F, 1.5F, 1.0F, 6.7F, new CubeDeformation(0.0F))
            .m_171514_(15, 49)
            .m_171488_(2.3F, 1.0F, -3.4F, 1.7F, 8.0F, 6.7F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
            .m_171555_(false)
            .m_171514_(26, 26)
            .m_171488_(3.6F, 8.0F, -1.0F, 1.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r5 = LeftLeg.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(35, 32).m_171488_(-1.15F, -0.5F, -3.35F, 2.3F, 1.0F, 6.5F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.65F, 5.5F, 0.05F, 0.0F, 0.0F, 0.48F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.Body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.RightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.LeftLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.RightLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
