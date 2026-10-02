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

public class Modelcarcass<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelcarcass"), "main");
   public final ModelPart head;
   public final ModelPart body;
   public final ModelPart right_arm;
   public final ModelPart left_arm;
   public final ModelPart right_leg;
   public final ModelPart left_leg;

   public Modelcarcass(ModelPart root) {
      this.head = root.m_171324_("head");
      this.body = root.m_171324_("body");
      this.right_arm = root.m_171324_("right_arm");
      this.left_arm = root.m_171324_("left_arm");
      this.right_leg = root.m_171324_("right_leg");
      this.left_leg = root.m_171324_("left_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-6.0F, -11.0F, -6.0F, 12.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 9)
            .m_171488_(-4.0F, 0.0F, -6.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -12.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(35, 0)
            .m_171488_(-3.0F, -10.0F, -3.375F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(-7.0F, -8.0F, -4.375F, 14.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 35)
            .m_171488_(-5.0F, 2.0F, -3.375F, 10.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 15)
            .m_171488_(-6.0F, 6.0F, -3.375F, 12.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, 0.375F)
      );
      PartDefinition right_arm = partdefinition.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 42)
            .m_171488_(-5.0F, -2.5F, -2.5F, 5.0F, 19.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 67)
            .m_171488_(-6.0F, 6.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(-7.0F, -7.5F, 0.5F)
      );
      PartDefinition left_arm = partdefinition.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_().m_171514_(21, 42).m_171488_(0.0F, -2.5F, -2.5F, 5.0F, 19.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(7.0F, -7.5F, 0.5F)
      );
      PartDefinition right_leg = partdefinition.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(63, 47).m_171488_(-2.5F, 0.0F, -2.5F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-3.5F, 8.0F, 0.5F)
      );
      PartDefinition left_leg = partdefinition.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(42, 47).m_171488_(-2.5F, 0.0F, -2.5F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.5F, 8.0F, 0.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_arm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.right_arm.f_104203_ = Mth.m_14089_(limbSwing * 0.666F + (float) Math.PI) * limbSwingAmount - 1.6F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.right_arm.f_104204_ = 0.6F * Mth.m_14031_(this.f_102608_ * (float) Math.PI * 2.0F);
      this.left_arm.f_104203_ = Mth.m_14089_(limbSwing * 0.666F) * limbSwingAmount;
      this.right_leg.f_104203_ = Mth.m_14089_(limbSwing * 0.666F) * 1.0F * limbSwingAmount;
      this.left_leg.f_104203_ = Mth.m_14089_(limbSwing * 0.666F) * -1.0F * limbSwingAmount;
   }
}
