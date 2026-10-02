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

public class ModelHobGoblin<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("iter_rpg", "model_hob_goblin"), "main");
   public final ModelPart head;
   public final ModelPart body;
   public final ModelPart leftArm;
   public final ModelPart rightArm;
   public final ModelPart leftLeg;
   public final ModelPart rightLeg;

   public ModelHobGoblin(ModelPart root) {
      this.head = root.m_171324_("head");
      this.body = root.m_171324_("body");
      this.leftArm = root.m_171324_("leftArm");
      this.rightArm = root.m_171324_("rightArm");
      this.leftLeg = root.m_171324_("leftLeg");
      this.rightLeg = root.m_171324_("rightLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(53, 21)
            .m_171488_(-5.0F, -2.0F, -8.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 23)
            .m_171488_(-5.0F, -3.0F, -8.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(3.0F, -3.0F, -8.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 57)
            .m_171488_(-5.0F, -9.0F, -7.0F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 4)
            .m_171488_(-9.0F, -6.0F, -2.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(5.0F, -6.0F, -2.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(43, 0)
            .m_171488_(-2.0F, -5.0F, -9.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -6.0F, 0.0F)
      );
      PartDefinition body = partdefinition.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 45)
            .m_171488_(-7.0F, 3.0F, -4.0F, 14.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-8.0F, -6.0F, -5.0F, 16.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 20)
            .m_171488_(-8.0F, 3.0F, -5.0F, 16.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-8.0F, -7.0F, -5.0F, 16.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 1.0F)
      );
      PartDefinition leftArm = partdefinition.m_171599_(
         "leftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(68, 67)
            .m_171488_(-1.0F, 7.0F, -3.4F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 0)
            .m_171488_(-1.0F, -4.0F, -4.4F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 12)
            .m_171488_(-1.0F, 7.0F, -4.4F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(82, 35)
            .m_171488_(-1.0F, -6.0F, -4.4F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(9.0F, -2.0F, 1.0F)
      );
      PartDefinition rightArm = partdefinition.m_171599_(
         "rightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 63)
            .m_171488_(-6.0F, 8.0F, -3.5F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 36)
            .m_171488_(-8.0F, -3.0F, -4.5F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(73, 48)
            .m_171488_(-8.0F, 8.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 74)
            .m_171488_(-8.0F, -5.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-9.0F, -3.0F, 1.0F)
      );
      PartDefinition leftLeg = partdefinition.m_171599_(
         "leftLeg",
         CubeListBuilder.m_171558_().m_171514_(48, 86).m_171488_(-4.0F, 0.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 12.0F, 1.0F)
      );
      PartDefinition rightLeg = partdefinition.m_171599_(
         "rightLeg",
         CubeListBuilder.m_171558_().m_171514_(23, 86).m_171488_(-2.0F, 0.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 12.0F, 1.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.leftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.rightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.rightLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * 1.0F * limbSwingAmount;
      this.rightArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6F + (float) Math.PI) * limbSwingAmount - 2.2F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.rightArm.f_104204_ = -0.2F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.leftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * limbSwingAmount - 2.2F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.leftArm.f_104204_ = 0.2F * Mth.m_14031_(this.f_102608_ * (float) Math.PI);
      this.leftLeg.f_104203_ = Mth.m_14089_(limbSwing * 0.6F) * -1.0F * limbSwingAmount;
   }
}
