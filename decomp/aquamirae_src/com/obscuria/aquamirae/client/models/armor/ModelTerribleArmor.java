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

public class ModelTerribleArmor<T extends Entity> extends EntityModel<T> {
   public final ModelPart left_shoe;
   public final ModelPart right_shoe;
   public final ModelPart body;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart head;
   public final ModelPart body2;
   public final ModelPart left_shoe2;
   public final ModelPart right_shoe2;

   public ModelTerribleArmor(ModelPart root) {
      this.left_shoe = root.m_171324_("left_shoe");
      this.right_shoe = root.m_171324_("right_shoe");
      this.body = root.m_171324_("body");
      this.left_arm = root.m_171324_("left_arm");
      this.right_arm = root.m_171324_("right_arm");
      this.head = root.m_171324_("head");
      this.body2 = root.m_171324_("body2");
      this.left_shoe2 = root.m_171324_("left_shoe2");
      this.right_shoe2 = root.m_171324_("right_shoe2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition left_shoe = partdefinition.m_171599_(
         "left_shoe",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .m_171514_(0, 28)
            .m_171488_(-3.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_shoe = partdefinition.m_171599_(
         "right_shoe",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 12)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .m_171514_(16, 0)
            .m_171488_(-4.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, 12.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
            .m_171514_(0, 8)
            .m_171488_(0.0F, -1.0F, 0.0F, 0.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = partdefinition.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 32)
            .m_171488_(-1.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 16)
            .m_171488_(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition right_arm = partdefinition.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(24, 0)
            .m_171488_(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
            .m_171514_(0, 29)
            .m_171488_(-7.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
            .m_171514_(0, 16)
            .m_171488_(-8.0F, -14.0F, 0.0F, 16.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition body2 = partdefinition.m_171599_(
         "body2",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_shoe2 = partdefinition.m_171599_(
         "left_shoe2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(0, 32)
            .m_171488_(2.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.0F, 12.0F, 0.0F)
      );
      PartDefinition right_shoe2 = partdefinition.m_171599_(
         "right_shoe2",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(24, 0)
            .m_171488_(-6.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, 12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.left_shoe.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_shoe.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_shoe2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_shoe2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
