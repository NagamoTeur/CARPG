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

public class ModelGoldenMoth<T extends Entity> extends EntityModel<T> {
   public final ModelPart body;
   public final ModelPart wing1;
   public final ModelPart wing2;

   public ModelGoldenMoth(ModelPart root) {
      this.body = root.m_171324_("body");
      this.wing1 = root.m_171324_("wing1");
      this.wing2 = root.m_171324_("wing2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 10).m_171488_(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.0F, 0.0F)
      );
      PartDefinition wing1 = partdefinition.m_171599_(
         "wing1",
         CubeListBuilder.m_171558_().m_171514_(0, 7).m_171488_(0.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.0F, 0.5F)
      );
      PartDefinition wing2 = partdefinition.m_171599_(
         "wing2",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.0F, 0.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.wing1.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.wing2.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.wing1.f_104205_ = math.idle(75.0F, 0.0F, 1.5F, 0.0F, ageInTicks, 1.0F);
      this.wing2.f_104205_ = -this.wing1.f_104205_;
   }
}
