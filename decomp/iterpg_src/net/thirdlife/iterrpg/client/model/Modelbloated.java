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

public class Modelbloated<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "modelbloated"), "main");
   public final ModelPart head;
   public final ModelPart body;
   public final ModelPart rightArm;
   public final ModelPart leftArm;
   public final ModelPart rightLeg;
   public final ModelPart leftLeg;

   public Modelbloated(ModelPart root) {
      this.head = root.m_171324_("head");
      this.body = root.m_171324_("body");
      this.rightArm = root.m_171324_("rightArm");
      this.leftArm = root.m_171324_("leftArm");
      this.rightLeg = root.m_171324_("rightLeg");
      this.leftLeg = root.m_171324_("leftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(25, 32)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 23)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-5.0F, -6.0F, -5.0F, 10.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 6.0F, 0.0F)
      );
      PartDefinition rightArm = partdefinition.m_171599_(
         "rightArm",
         CubeListBuilder.m_171558_().m_171514_(21, 49).m_171488_(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition leftArm = partdefinition.m_171599_(
         "leftArm",
         CubeListBuilder.m_171558_().m_171514_(40, 49).m_171488_(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition rightLeg = partdefinition.m_171599_(
         "rightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 40).m_171488_(-3.1F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition leftLeg = partdefinition.m_171599_(
         "leftLeg",
         CubeListBuilder.m_171558_().m_171514_(41, 0).m_171488_(-1.9F, 0.0F, -2.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.rightArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float) Math.PI) * limbSwingAmount;
      this.leftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * limbSwingAmount;
      this.rightLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
      this.leftLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
   }
}
