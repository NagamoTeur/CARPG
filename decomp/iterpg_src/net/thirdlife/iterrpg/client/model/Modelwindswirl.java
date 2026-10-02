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

public class Modelwindswirl<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelwindswirl"), "main");
   public final ModelPart top;
   public final ModelPart middle;
   public final ModelPart bottom;

   public Modelwindswirl(ModelPart root) {
      this.top = root.m_171324_("top");
      this.middle = root.m_171324_("middle");
      this.bottom = root.m_171324_("bottom");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition top = partdefinition.m_171599_(
         "top",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-9.5F, -3.5F, -9.5F, 19.0F, 9.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 6.5F, 0.0F)
      );
      PartDefinition middle = partdefinition.m_171599_(
         "middle",
         CubeListBuilder.m_171558_().m_171514_(0, 28).m_171488_(-6.5F, -2.5F, -6.5F, 13.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 14.5F, 0.0F)
      );
      PartDefinition bottom = partdefinition.m_171599_(
         "bottom",
         CubeListBuilder.m_171558_().m_171514_(39, 28).m_171488_(-3.5F, -2.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 21.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.top.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.middle.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.bottom.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.top.f_104204_ = ageInTicks / 6.0F;
      this.middle.f_104204_ = ageInTicks / 5.0F;
      this.bottom.f_104204_ = ageInTicks / 4.0F;
   }
}
