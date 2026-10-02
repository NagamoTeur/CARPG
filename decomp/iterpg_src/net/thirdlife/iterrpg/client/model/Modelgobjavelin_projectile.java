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

public class Modelgobjavelin_projectile<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelgobjavelin_projectile"), "main");
   public final ModelPart javelin;

   public Modelgobjavelin_projectile(ModelPart root) {
      this.javelin = root.m_171324_("javelin");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition javelin = partdefinition.m_171599_(
         "javelin",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(0.0F, 2.5F, -2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 7)
            .m_171488_(-0.5F, 0.5F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 4)
            .m_171488_(-0.5F, -1.5F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 12)
            .m_171488_(-0.5F, -5.5F, 0.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 0)
            .m_171488_(-0.5F, -9.5F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-0.5F, 2.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 14.5F, 0.0F, 0.0F, -1.5708F, 3.1416F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.javelin.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
