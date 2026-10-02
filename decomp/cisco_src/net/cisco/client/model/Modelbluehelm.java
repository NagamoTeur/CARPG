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
import net.minecraft.world.entity.Entity;

public class Modelbluehelm<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelbluehelm"), "main");
   public final ModelPart Head;

   public Modelbluehelm(ModelPart root) {
      this.Head = root.m_171324_("Head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition Head = partdefinition.m_171599_(
         "Head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 16)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(36, 0)
            .m_171488_(-5.1426F, -7.0019F, -4.001F, 0.2F, 1.0F, 9.1F, new CubeDeformation(0.0F))
            .m_171514_(36, 0)
            .m_171488_(4.3256F, -7.0048F, -4.001F, 0.3F, 1.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 0)
            .m_171488_(4.55F, -8.0048F, -3.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 0)
            .m_171488_(-5.2426F, -8.0019F, -3.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 0)
            .m_171488_(4.294F, -9.0048F, -2.001F, 0.3F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 0)
            .m_171488_(-5.1426F, -9.0019F, -2.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 0)
            .m_171488_(4.28F, -10.0048F, -0.001F, 0.2F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 0)
            .m_171488_(-5.0426F, -10.0019F, -0.001F, 0.1F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 0)
            .m_171488_(4.28F, -11.0048F, 1.999F, 0.1F, 1.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 0)
            .m_171488_(-5.1426F, -11.0019F, 1.999F, 0.2F, 1.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-3.4F, -7.0F, -5.0F, 2.4F, 2.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(0.9F, -7.0F, -5.0F, 2.4F, 2.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-4.9F, -9.0F, -5.0F, 1.5F, 3.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.3F, -9.0F, -5.0F, 1.5F, 3.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -8.0F, -5.0F, 2.0F, 1.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -5.0F, -5.0F, 2.0F, 1.0F, 0.2F, new CubeDeformation(0.0F))
            .m_171514_(60, 30)
            .m_171488_(-1.0F, -7.0F, -5.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(1.5F, -2.0F, -11.1662F, 1.0F, 3.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-6.3669F, -7.4857F, -6.9856F, 0.0F, -1.6144F, 0.0436F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(2.5F, -7.0F, 0.0F, 1.0F, 3.0F, 0.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.9645F, -1.999F, -7.4976F, 0.0F, -1.6144F, 0.0436F)
      );
      PartDefinition cube_r3 = Head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(32, -1).m_171488_(-0.2F, -1.0F, -4.85F, 0.2F, 2.0F, 9.6F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.1244F, -7.0048F, 4.998F, 0.0F, 1.5708F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.Head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.Head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.Head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
   }
}
