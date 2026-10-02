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
import net.minecraft.world.entity.Entity;

public class ModelThrowingSpear<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "model_throwing_spear"), "main");
   public final ModelPart spear;

   public ModelThrowingSpear(ModelPart root) {
      this.spear = root.m_171324_("spear");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition spear = partdefinition.m_171599_("spear", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, 10.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
      PartDefinition cube = spear.m_171599_(
         "cube",
         CubeListBuilder.m_171558_().m_171514_(20, 20).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 10.0F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube2 = spear.m_171599_(
         "cube2",
         CubeListBuilder.m_171558_().m_171514_(20, 16).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.6F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube3 = spear.m_171599_(
         "cube3",
         CubeListBuilder.m_171558_().m_171514_(12, 20).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 7.2F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube4 = spear.m_171599_(
         "cube4",
         CubeListBuilder.m_171558_().m_171514_(6, 20).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 5.8F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube5 = spear.m_171599_(
         "cube5",
         CubeListBuilder.m_171558_().m_171514_(0, 20).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 4.4F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube6 = spear.m_171599_(
         "cube6",
         CubeListBuilder.m_171558_().m_171514_(16, 18).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube7 = spear.m_171599_(
         "cube7",
         CubeListBuilder.m_171558_().m_171514_(12, 16).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 1.6F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube8 = spear.m_171599_(
         "cube8",
         CubeListBuilder.m_171558_().m_171514_(6, 16).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.2F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube9 = spear.m_171599_(
         "cube9",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.2F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube10 = spear.m_171599_(
         "cube10",
         CubeListBuilder.m_171558_().m_171514_(8, 10).m_171488_(-0.5F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.6F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube11 = spear.m_171599_(
         "cube11",
         CubeListBuilder.m_171558_().m_171514_(16, 10).m_171488_(-0.5F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -4.0F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube12 = spear.m_171599_(
         "cube12",
         CubeListBuilder.m_171558_().m_171514_(24, 10).m_171488_(-0.5F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.4F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition cube13 = spear.m_171599_(
         "cube13",
         CubeListBuilder.m_171558_().m_171514_(26, 20).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.2F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.spear.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
