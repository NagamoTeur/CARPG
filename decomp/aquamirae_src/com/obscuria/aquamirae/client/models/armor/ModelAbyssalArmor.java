package com.obscuria.aquamirae.client.models.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ModelAbyssalArmor<T extends Entity> extends EntityModel<T> {
   public final ModelPart tiara;
   public final ModelPart helmet;
   public final ModelPart body;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart left_leg;
   public final ModelPart right_leg;
   public final ModelPart left_boot;
   public final ModelPart right_boot;

   public ModelAbyssalArmor(ModelPart root) {
      this.tiara = root.m_171324_("tiara");
      this.helmet = root.m_171324_("helmet");
      this.body = root.m_171324_("body");
      this.left_arm = root.m_171324_("left_arm");
      this.right_arm = root.m_171324_("right_arm");
      this.left_leg = root.m_171324_("left_leg");
      this.right_leg = root.m_171324_("right_leg");
      this.left_boot = root.m_171324_("left_boot");
      this.right_boot = root.m_171324_("right_boot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition tiara = partdefinition.m_171599_(
         "tiara",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.55F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      PartDefinition helmet = partdefinition.m_171599_(
         "helmet",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(1.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(0.75F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = partdefinition.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(24, 10)
            .m_171488_(-1.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
            .m_171514_(16, 17)
            .m_171488_(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = partdefinition.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(24, 0)
            .m_171488_(-9.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
            .m_171514_(0, 17)
            .m_171488_(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition left_leg = partdefinition.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(16, 0).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.34F)),
         PartPose.m_171419_(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_leg = partdefinition.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)),
         PartPose.m_171419_(-2.0F, 12.0F, 0.0F)
      );
      PartDefinition left_boot = partdefinition.m_171599_(
         "left_boot",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 0)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.74F))
            .m_171514_(8, 16)
            .m_171488_(2.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)),
         PartPose.m_171419_(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_boot = partdefinition.m_171599_(
         "right_boot",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .m_171514_(0, 16)
            .m_171488_(-6.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)),
         PartPose.m_171419_(-2.0F, 12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.tiara.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.helmet.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_boot.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_boot.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
