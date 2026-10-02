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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelmussel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelmussel"), "main");
   public final ModelPart lower_shell;
   public final ModelPart upper_shell;
   public final ModelPart pearl;

   public Modelmussel(ModelPart root) {
      this.lower_shell = root.m_171324_("lower_shell");
      this.upper_shell = root.m_171324_("upper_shell");
      this.pearl = root.m_171324_("pearl");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition lower_shell = partdefinition.m_171599_(
         "lower_shell",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 21)
            .m_171488_(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-5.0F, -4.0F, 8.0F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 24.0F, 0.0F)
      );
      PartDefinition upper_shell = partdefinition.m_171599_(
         "upper_shell",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -4.0F, -16.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 20.0F, 8.0F)
      );
      PartDefinition pearl = partdefinition.m_171599_(
         "pearl",
         CubeListBuilder.m_171558_().m_171514_(29, 42).m_171488_(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171419_(0.0F, 20.0F, 3.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.lower_shell.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.upper_shell.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.pearl.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.upper_shell.f_104203_ = 0.0F - Mth.m_14154_(Mth.m_14089_(limbSwing * (float) Math.PI / 2.0F) * limbSwingAmount * 1.5F);
   }
}
