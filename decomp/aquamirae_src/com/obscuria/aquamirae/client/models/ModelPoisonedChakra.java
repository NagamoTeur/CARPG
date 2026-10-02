package com.obscuria.aquamirae.client.models;

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

public class ModelPoisonedChakra<T extends Entity> extends EntityModel<T> {
   public final ModelPart main;

   public ModelPoisonedChakra(ModelPart root) {
      this.main = root.m_171324_("chakram");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition chakram = partdefinition.m_171599_("chakram", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition part1 = chakram.m_171599_(
         "part1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 3)
            .m_171488_(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 15)
            .m_171488_(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 21)
            .m_171488_(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 27)
            .m_171488_(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 30)
            .m_171488_(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 36)
            .m_171488_(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.5F, 0.5F, -5.5F)
      );
      PartDefinition part2 = chakram.m_171599_(
         "part2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 3)
            .m_171488_(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 15)
            .m_171488_(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 21)
            .m_171488_(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 27)
            .m_171488_(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 30)
            .m_171488_(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 36)
            .m_171488_(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.5F, 0.5F, 5.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition part3 = chakram.m_171599_(
         "part3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 3)
            .m_171488_(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 15)
            .m_171488_(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 21)
            .m_171488_(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 27)
            .m_171488_(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 30)
            .m_171488_(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 36)
            .m_171488_(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.5F, 0.5F, 5.5F, -3.1416F, 0.0F, 3.1416F)
      );
      PartDefinition part4 = chakram.m_171599_(
         "part4",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 3)
            .m_171488_(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 9)
            .m_171488_(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 15)
            .m_171488_(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 21)
            .m_171488_(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 27)
            .m_171488_(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 30)
            .m_171488_(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 36)
            .m_171488_(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.5F, 0.5F, -5.5F, 0.0F, -1.5708F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.main.f_104204_ = ageInTicks * 0.8F;
   }
}
