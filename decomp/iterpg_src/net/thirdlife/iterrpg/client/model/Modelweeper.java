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

public class Modelweeper<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelweeper"), "main");
   public final ModelPart head;
   public final ModelPart body;
   public final ModelPart arm_right;
   public final ModelPart arm_left;
   public final ModelPart leg_right;
   public final ModelPart leg_left;

   public Modelweeper(ModelPart root) {
      this.head = root.m_171324_("head");
      this.body = root.m_171324_("body");
      this.arm_right = root.m_171324_("arm_right");
      this.arm_left = root.m_171324_("arm_left");
      this.leg_right = root.m_171324_("leg_right");
      this.leg_left = root.m_171324_("leg_left");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-8.0F, -14.0F, -5.0F, 16.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 9.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-5.0F, -4.0F, -3.0F, 10.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 13.0F, 0.0F)
      );
      PartDefinition arm_right = partdefinition.m_171599_(
         "arm_right",
         CubeListBuilder.m_171558_().m_171514_(28, 36).m_171488_(-4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 11.0F, 0.0F)
      );
      PartDefinition arm_left = partdefinition.m_171599_(
         "arm_left",
         CubeListBuilder.m_171558_().m_171514_(32, 24).m_171488_(0.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 11.0F, 0.0F)
      );
      PartDefinition leg_right = partdefinition.m_171599_(
         "leg_right",
         CubeListBuilder.m_171558_().m_171514_(16, 44).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-3.0F, 17.0F, 0.0F)
      );
      PartDefinition leg_left = partdefinition.m_171599_(
         "leg_left",
         CubeListBuilder.m_171558_().m_171514_(0, 38).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.0F, 17.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.arm_right.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.arm_left.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leg_right.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leg_left.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI) + 1.0F * Mth.m_14089_(ageInTicks / 20.0F) / 10.0F;
      this.leg_right.f_104203_ = Mth.m_14089_(limbSwing * 0.75F) * 1.0F * limbSwingAmount;
      this.arm_right.f_104203_ = Mth.m_14089_(limbSwing * 0.75F + (float) Math.PI) * limbSwingAmount + -2.0F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.arm_right.f_104204_ = 0.75F * Mth.m_14031_(this.f_102608_ * (float) Math.PI * 1.5F);
      this.leg_left.f_104203_ = Mth.m_14089_(limbSwing * 0.75F) * -1.0F * limbSwingAmount;
      this.arm_left.f_104203_ = Mth.m_14089_(limbSwing * 0.75F) * limbSwingAmount;
   }
}
