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

public class Modelaura_boulder<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelaura_boulder"), "main");
   public final ModelPart base;
   public final ModelPart addition;

   public Modelaura_boulder(ModelPart root) {
      this.base = root.m_171324_("base");
      this.addition = root.m_171324_("addition");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition base = partdefinition.m_171599_(
         "base",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-15.0F, -0.15F, -15.0F, 30.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.9F, 0.0F)
      );
      PartDefinition addition = partdefinition.m_171599_(
         "addition",
         CubeListBuilder.m_171558_().m_171514_(0, 30).m_171488_(-10.0F, -0.5F, -10.0F, 20.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 23.5F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.base.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.addition.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.base.f_104204_ = ageInTicks / 10.0F;
      this.addition.f_104204_ = ageInTicks / -10.0F;
   }
}
