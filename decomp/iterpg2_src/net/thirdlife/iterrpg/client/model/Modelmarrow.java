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

public class Modelmarrow<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelmarrow"), "main");
   public final ModelPart arrow;

   public Modelmarrow(ModelPart root) {
      this.arrow = root.m_171324_("arrow");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition arrow = partdefinition.m_171599_(
         "arrow",
         CubeListBuilder.m_171558_()
            .m_171514_(10, 0)
            .m_171488_(0.0F, -13.0F, -2.5F, 0.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.0F, 0.0F)
      );
      PartDefinition plane_r1 = arrow.m_171599_(
         "plane_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.0F, -7.0F, -2.5F, 0.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.arrow.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
