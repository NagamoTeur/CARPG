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
import org.jetbrains.annotations.NotNull;

public class ModelLuminousJelly<T extends Entity> extends EntityModel<T> {
   private final ModelPart main;

   public ModelLuminousJelly(ModelPart root) {
      this.main = root.m_171324_("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition head = main.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(4, 13)
            .m_171488_(-2.0F, -17.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -19.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 20)
            .m_171488_(-2.0F, -14.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition ten1_1 = main.m_171599_(
         "ten1_1",
         CubeListBuilder.m_171558_().m_171514_(24, 13).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.5F, -12.5F, 0.0F)
      );
      PartDefinition ten1_2 = ten1_1.m_171599_(
         "ten1_2",
         CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      PartDefinition ten1_3 = ten1_2.m_171599_(
         "ten1_3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten1_4 = ten1_3.m_171599_(
         "ten1_4",
         CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten1_5 = ten1_4.m_171599_(
         "ten1_5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 22)
            .m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 19)
            .m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten1_6 = ten1_5.m_171599_(
         "ten1_6",
         CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten2_1 = main.m_171599_(
         "ten2_1",
         CubeListBuilder.m_171558_().m_171514_(24, 13).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.5F, -12.5F, 0.0F)
      );
      PartDefinition ten2_2 = ten2_1.m_171599_(
         "ten2_2",
         CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      PartDefinition ten2_3 = ten2_2.m_171599_(
         "ten2_3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten2_4 = ten2_3.m_171599_(
         "ten2_4",
         CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten2_5 = ten2_4.m_171599_(
         "ten2_5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 22)
            .m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 19)
            .m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten2_6 = ten2_5.m_171599_(
         "ten2_6",
         CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten3_1 = main.m_171599_(
         "ten3_1",
         CubeListBuilder.m_171558_().m_171514_(24, 13).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, -12.5F, -1.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition ten3_2 = ten3_1.m_171599_(
         "ten3_2",
         CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      PartDefinition ten3_3 = ten3_2.m_171599_(
         "ten3_3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten3_4 = ten3_3.m_171599_(
         "ten3_4",
         CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten3_5 = ten3_4.m_171599_(
         "ten3_5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 22)
            .m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 19)
            .m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten3_6 = ten3_5.m_171599_(
         "ten3_6",
         CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten4_1 = main.m_171599_(
         "ten4_1",
         CubeListBuilder.m_171558_().m_171514_(24, 13).m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, -12.5F, 1.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition ten4_2 = ten4_1.m_171599_(
         "ten4_2",
         CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.0F, 0.0F)
      );
      PartDefinition ten4_3 = ten4_2.m_171599_(
         "ten4_3",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten4_4 = ten4_3.m_171599_(
         "ten4_4",
         CubeListBuilder.m_171558_().m_171514_(0, 19).m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten4_5 = ten4_4.m_171599_(
         "ten4_5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 22)
            .m_171488_(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 19)
            .m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition ten4_6 = ten4_5.m_171599_(
         "ten4_6",
         CubeListBuilder.m_171558_().m_171514_(20, 21).m_171488_(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void m_7695_(
      @NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
