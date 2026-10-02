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

public class ModelMazeRose<T extends Entity> extends EntityModel<T> {
   public final ModelPart main;

   public ModelMazeRose(ModelPart root) {
      this.main = root.m_171324_("maze_rose");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition maze_rose = partdefinition.m_171599_("maze_rose", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 23.0F, 0.0F));
      PartDefinition part1 = maze_rose.m_171599_(
         "part1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 8)
            .m_171488_(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 10)
            .m_171488_(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 8)
            .m_171488_(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 14)
            .m_171488_(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 26)
            .m_171488_(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 28)
            .m_171488_(1.5F, -1.0F, -0.5F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.5F, 0.0F)
      );
      PartDefinition part2 = maze_rose.m_171599_(
         "part2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 8)
            .m_171488_(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 10)
            .m_171488_(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 8)
            .m_171488_(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 14)
            .m_171488_(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 26)
            .m_171488_(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 28)
            .m_171488_(1.5F, -1.0F, -0.5F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition part3 = maze_rose.m_171599_(
         "part3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 8)
            .m_171488_(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 10)
            .m_171488_(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 8)
            .m_171488_(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 14)
            .m_171488_(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 26)
            .m_171488_(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 28)
            .m_171488_(1.5F, -1.0F, -0.5F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.5F, 0.0F, -3.1416F, 0.0F, 3.1416F)
      );
      PartDefinition part4 = maze_rose.m_171599_(
         "part4",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 6)
            .m_171488_(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 8)
            .m_171488_(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 10)
            .m_171488_(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 8)
            .m_171488_(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 14)
            .m_171488_(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 18)
            .m_171488_(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 22)
            .m_171488_(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 24)
            .m_171488_(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 26)
            .m_171488_(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 28)
            .m_171488_(1.5F, -1.0F, -0.5F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F)
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
