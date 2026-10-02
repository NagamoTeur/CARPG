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

public class Modelforest_vines<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelforest_vines"), "main");
   public final ModelPart the;

   public Modelforest_vines(ModelPart root) {
      this.the = root.m_171324_("the");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition the = partdefinition.m_171599_("the", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition vine_r1 = the.m_171599_(
         "vine_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -10.5F, 0.0F, 16.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -10.5F, 0.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition vine_r2 = the.m_171599_(
         "vine_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(-8.0F, -10.5F, 0.0F, 16.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.the.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
