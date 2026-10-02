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

public class Modelwitchmud_golem<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelwitchmud_golem"), "main");
   public final ModelPart body;
   public final ModelPart right_leg;
   public final ModelPart left_leg;

   public Modelwitchmud_golem(ModelPart root) {
      this.body = root.m_171324_("body");
      this.right_leg = root.m_171324_("right_leg");
      this.left_leg = root.m_171324_("left_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -19.0F, -8.0F, 16.0F, 19.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 17.0F, 0.0F)
      );
      PartDefinition plamt_r1 = body.m_171599_(
         "plamt_r1",
         CubeListBuilder.m_171558_().m_171514_(1, 22).m_171480_().m_171488_(0.0F, -8.5F, -6.5F, 0.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(0.0F, -27.5F, 0.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition plamt_r2 = body.m_171599_(
         "plamt_r2",
         CubeListBuilder.m_171558_().m_171514_(34, 22).m_171488_(0.0F, -8.5F, -6.5F, 0.0F, 17.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -27.5F, 0.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition right_leg = partdefinition.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 52).m_171488_(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-4.5F, 17.0F, 0.5F)
      );
      PartDefinition left_leg = partdefinition.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(48, 0).m_171480_().m_171488_(-2.5F, 0.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(4.5F, 17.0F, 0.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.right_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.left_leg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.left_leg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.right_leg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
      this.body.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.body.f_104205_ = headPitch / (180.0F / (float)Math.PI) + Mth.m_14089_(limbSwing * 1.0F) * 0.25F * limbSwingAmount;
      this.body.f_104203_ = 0.75F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
   }
}
