package net.thirdlife.iterrpg.client.model;

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

public class Modelspider_hatchling<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelspider_hatchling"), "main");
   public final ModelPart body;
   public final ModelPart head;
   public final ModelPart right_leg_1;
   public final ModelPart right_leg_2;
   public final ModelPart right_leg_3;
   public final ModelPart right_leg_4;
   public final ModelPart left_leg_1;
   public final ModelPart left_leg_2;
   public final ModelPart left_leg_3;
   public final ModelPart left_leg_4;

   public Modelspider_hatchling(ModelPart root) {
      this.body = root.m_171324_("body");
      this.head = root.m_171324_("head");
      this.right_leg_1 = root.m_171324_("right_leg_1");
      this.right_leg_2 = root.m_171324_("right_leg_2");
      this.right_leg_3 = root.m_171324_("right_leg_3");
      this.right_leg_4 = root.m_171324_("right_leg_4");
      this.left_leg_1 = root.m_171324_("left_leg_1");
      this.left_leg_2 = root.m_171324_("left_leg_2");
      this.left_leg_3 = root.m_171324_("left_leg_3");
      this.left_leg_4 = root.m_171324_("left_leg_4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -3.0F, -3.5F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 19.0F, 0.5F)
      );
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 13)
            .m_171488_(-2.0F, -2.5F, -4.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 18)
            .m_171488_(-2.5F, 0.5F, -5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 13)
            .m_171488_(0.5F, 0.5F, -5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 19.5F, -3.0F)
      );
      PartDefinition right_leg_1 = partdefinition.m_171599_(
         "right_leg_1",
         CubeListBuilder.m_171558_().m_171514_(16, 23).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, 21.5F, -2.5F, -0.6109F, 0.0F, 1.0472F)
      );
      PartDefinition right_leg_2 = partdefinition.m_171599_(
         "right_leg_2",
         CubeListBuilder.m_171558_().m_171514_(12, 22).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, 21.5F, -0.5F, -0.1745F, 0.0F, 1.0472F)
      );
      PartDefinition right_leg_3 = partdefinition.m_171599_(
         "right_leg_3",
         CubeListBuilder.m_171558_().m_171514_(8, 22).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, 21.5F, 1.5F, 0.1745F, 0.0F, 1.0472F)
      );
      PartDefinition right_leg_4 = partdefinition.m_171599_(
         "right_leg_4",
         CubeListBuilder.m_171558_().m_171514_(4, 22).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, 21.5F, 3.5F, 0.6109F, 0.0F, 1.0472F)
      );
      PartDefinition left_leg_1 = partdefinition.m_171599_(
         "left_leg_1",
         CubeListBuilder.m_171558_().m_171514_(23, 0).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, 21.5F, -2.5F, -0.6109F, 0.0F, -1.0472F)
      );
      PartDefinition left_leg_2 = partdefinition.m_171599_(
         "left_leg_2",
         CubeListBuilder.m_171558_().m_171514_(0, 22).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, 21.5F, -0.5F, -0.1745F, 0.0F, -1.0472F)
      );
      PartDefinition left_leg_3 = partdefinition.m_171599_(
         "left_leg_3",
         CubeListBuilder.m_171558_().m_171514_(19, 0).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, 21.5F, 1.5F, 0.1745F, 0.0F, -1.0472F)
      );
      PartDefinition left_leg_4 = partdefinition.m_171599_(
         "left_leg_4",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, 21.5F, 3.5F, 0.6109F, 0.0F, -1.0472F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg_1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg_2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg_3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg_4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg_1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg_2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg_3.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg_4.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.right_leg_1.f_104205_ = (float) (Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * 0.75F * limbSwingAmount;
      this.right_leg_1.f_104203_ = -0.6108653F + Mth.m_14089_(limbSwing * 2.0F) * 0.5F * limbSwingAmount;
      this.right_leg_2.f_104205_ = (float) (Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * -0.75F * limbSwingAmount;
      this.right_leg_2.f_104203_ = -0.17453294F + Mth.m_14089_(limbSwing * 2.0F) * -0.5F * limbSwingAmount;
      this.right_leg_3.f_104205_ = (float) (Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * 0.75F * limbSwingAmount;
      this.right_leg_3.f_104203_ = 0.17453294F + Mth.m_14089_(limbSwing * 2.0F) * 0.5F * limbSwingAmount;
      this.right_leg_4.f_104205_ = (float) (Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * -0.75F * limbSwingAmount;
      this.right_leg_4.f_104203_ = 0.6108653F + Mth.m_14089_(limbSwing * 2.0F) * -0.5F * limbSwingAmount;
      this.left_leg_1.f_104205_ = (float) (-Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * -0.75F * limbSwingAmount;
      this.left_leg_1.f_104203_ = -0.6108653F + Mth.m_14089_(limbSwing * 2.0F) * 0.5F * limbSwingAmount;
      this.left_leg_2.f_104205_ = (float) (-Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * 0.75F * limbSwingAmount;
      this.left_leg_2.f_104203_ = -0.17453294F + Mth.m_14089_(limbSwing * 2.0F) * -0.5F * limbSwingAmount;
      this.left_leg_3.f_104205_ = (float) (-Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * -0.75F * limbSwingAmount;
      this.left_leg_3.f_104203_ = 0.17453294F + Mth.m_14089_(limbSwing * 2.0F) * 0.5F * limbSwingAmount;
      this.left_leg_4.f_104205_ = (float) (-Math.PI / 3) + Mth.m_14031_(limbSwing * 2.0F) * 0.75F * limbSwingAmount;
      this.left_leg_4.f_104203_ = 0.6108653F + Mth.m_14089_(limbSwing * 2.0F) * -0.5F * limbSwingAmount;
   }
}
