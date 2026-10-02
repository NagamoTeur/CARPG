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

public class Modelgildedlower<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelgildedlower"), "main");
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelgildedlower(ModelPart root) {
      this.RightLeg = root.m_171324_("RightLeg");
      this.LeftLeg = root.m_171324_("LeftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(47, 12)
            .m_171488_(-3.0F, 4.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-3.0F, 2.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r1 = RightLeg.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(33, 37)
            .m_171488_(-1.5754F, -4.7653F, 0.375F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 33)
            .m_171488_(-2.5754F, -4.7653F, -3.625F, 1.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 59)
            .m_171488_(-2.5246F, -5.2347F, 0.875F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 41)
            .m_171488_(-2.5246F, 4.2347F, 0.875F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 8)
            .m_171488_(-2.9246F, -5.2347F, -4.125F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(-2.9246F, 4.2347F, -4.125F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.6754F, 3.9896F, 3.0691F, 0.3054F, 0.0F, 0.1745F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(19, 4)
            .m_171488_(-1.2F, 4.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 3)
            .m_171488_(0.8F, 2.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r2 = LeftLeg.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(51, 47)
            .m_171488_(-2.775F, -5.2347F, 0.875F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 20)
            .m_171488_(-2.775F, 4.2347F, 0.875F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 37)
            .m_171488_(-2.675F, -4.7653F, 0.375F, 4.7F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 56)
            .m_171488_(1.825F, -5.2347F, -4.125F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(33, 9)
            .m_171488_(1.825F, 4.2347F, -4.125F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(29, 33)
            .m_171488_(1.825F, -4.7653F, -3.625F, 0.5F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.775F, 3.9896F, 3.0691F, 0.3054F, 0.0F, -0.1309F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.RightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.LeftLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.RightLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
