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

public class Modelgrim_boulder<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelgrim_boulder"), "main");
   public final ModelPart body;

   public Modelgrim_boulder(ModelPart root) {
      this.body = root.m_171324_("body");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(96, 0).m_171488_(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 16.0F, 0.0F)
      );
      PartDefinition chains_r1 = body.m_171599_(
         "chains_r1",
         CubeListBuilder.m_171558_().m_171514_(10, 81).m_171488_(-8.5F, -4.0F, -8.5F, 17.0F, 8.0F, 17.0F, new CubeDeformation(0.12F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 1.5708F, 0.0436F, -1.5708F)
      );
      PartDefinition chains_r2 = body.m_171599_(
         "chains_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 81).m_171488_(-8.5F, -4.0F, -8.5F, 17.0F, 8.0F, 17.0F, new CubeDeformation(0.11F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5272F)
      );
      PartDefinition chains_r3 = body.m_171599_(
         "chains_r3",
         CubeListBuilder.m_171558_().m_171514_(8, 81).m_171488_(-8.5F, -4.0F, -8.5F, 17.0F, 8.0F, 17.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.body.f_104203_ = ageInTicks / 10.0F;
      this.body.f_104204_ = ageInTicks / 10.0F;
      this.body.f_104205_ = ageInTicks / 10.0F;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
