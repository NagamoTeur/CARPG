package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.obscureapi.api.hekate.HekateLib.math;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ModelSpinefish<T extends Entity> extends EntityModel<T> {
   private final ModelPart main;
   private final ModelPart bodyTop;
   private final ModelPart bodyBottom;
   private final ModelPart head;
   private final ModelPart tail;

   public ModelSpinefish(ModelPart root) {
      this.main = root.m_171324_("main");
      this.bodyTop = this.main.m_171324_("bodyTop");
      this.bodyBottom = this.main.m_171324_("bodyBottom");
      this.head = this.bodyTop.m_171324_("head");
      this.tail = this.bodyBottom.m_171324_("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition main = partdefinition.m_171599_("main", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 17.5F, 0.0F));
      PartDefinition bodyTop = main.m_171599_(
         "bodyTop",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 8)
            .m_171488_(-1.0F, -2.5F, -3.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 0)
            .m_171488_(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 1.0F)
      );
      PartDefinition head = bodyTop.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(6, 13)
            .m_171488_(0.0F, -5.5F, -6.0303F, 0.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 16)
            .m_171488_(-1.0F, -2.5F, -3.0303F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 11)
            .m_171488_(0.0F, -6.5F, -3.0303F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -2.9697F)
      );
      PartDefinition cube_r1 = head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(16, 0).m_171488_(-1.0F, -3.5F, -0.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, 0.0F, -5.5303F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition bodyBottom = main.m_171599_(
         "bodyBottom",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 13)
            .m_171488_(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 24)
            .m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 1.0F)
      );
      PartDefinition tail = bodyBottom.m_171599_(
         "tail",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 3.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float speed = 0.5F;
      this.main.f_104205_ = entity.m_20069_() ? 0.0F : (float)Math.toRadians(-90.0);
      this.main.f_104201_ = entity.m_20069_() ? 17.5F : 22.0F;
      this.bodyTop.f_104204_ = math.idle(8.0F, 0.0F, 0.5F, 0.0F, ageInTicks, 1.0F);
      this.head.f_104204_ = math.idle(8.0F, 0.0F, 0.5F, 0.1F, ageInTicks, 1.0F);
      this.bodyBottom.f_104204_ = math.idle(-16.0F, 0.0F, 0.5F, 0.1F, ageInTicks, 1.0F);
      this.tail.f_104204_ = math.idle(-18.0F, 0.0F, 0.5F, 0.2F, ageInTicks, 1.0F);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.main.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
