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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class Modelsylvitophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelsylvitophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelsylvitophalf(ModelPart root) {
      this.Head = root.m_171324_("Head");
      this.Body = root.m_171324_("Body");
      this.RightArm = root.m_171324_("RightArm");
      this.LeftArm = root.m_171324_("LeftArm");
      this.RightLeg = root.m_171324_("RightLeg");
      this.LeftLeg = root.m_171324_("LeftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition Head = partdefinition.m_171599_(
         "Head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 48)
            .m_171488_(8.8F, 3.0F, -6.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
            .m_171514_(56, 48)
            .m_171488_(8.8F, -1.0F, -2.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
            .m_171514_(56, 48)
            .m_171488_(8.8F, 1.0F, -4.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
         PartPose.m_171423_(5.0F, -6.0F, -3.0F, -0.7854F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 48)
            .m_171488_(-1.2F, -6.0F, 2.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
            .m_171514_(56, 48)
            .m_171488_(-1.2F, -4.0F, 0.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
            .m_171514_(56, 48)
            .m_171488_(-1.2F, -2.0F, -1.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
         PartPose.m_171423_(5.0F, -6.0F, -3.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition Head2 = Head.m_171599_(
         "Head2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -1.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(4.0F, -1.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(3.0F, 0.0F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, 0.0F, -5.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(7, 41)
            .m_171488_(-1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
            .m_171514_(2, 34)
            .m_171488_(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
            .m_171514_(1, 32)
            .m_171488_(-5.0F, -3.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.0F, -3.0F, -5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 37)
            .m_171488_(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 36)
            .m_171488_(-3.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 32)
            .m_171488_(-1.0F, -2.0F, -5.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 37)
            .m_171488_(2.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 37)
            .m_171488_(1.0F, -2.0F, -5.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 33)
            .m_171488_(2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-4.0F, -4.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-5.0F, -4.0F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -4.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 34)
            .m_171488_(-4.0F, -7.6F, 5.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 34)
            .m_171488_(3.0F, -4.0F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(3.0F, -4.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 33)
            .m_171488_(3.0F, -4.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(4.0F, -6.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.01F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -6.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 34)
            .m_171488_(-4.0F, -8.0F, -5.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 41)
            .m_171488_(-4.0F, -8.0F, 4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(9, 41)
            .m_171488_(-4.0F, -8.0F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(8, 41)
            .m_171488_(1.0F, -8.0F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(8, 41)
            .m_171488_(1.0F, -8.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(5, 41)
            .m_171488_(1.0F, -6.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 41)
            .m_171488_(3.0F, -6.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 37)
            .m_171488_(-3.0F, -6.0F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 33)
            .m_171488_(3.0F, -8.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(4.0F, -8.6F, -1.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 37)
            .m_171488_(4.0F, -8.6F, -5.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(-5.0F, -8.6F, -5.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
            .m_171514_(5, 37)
            .m_171488_(-5.0F, -8.6F, 0.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, -8.6F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 36)
            .m_171488_(1.0F, -8.6F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -8.7F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 41)
            .m_171488_(2.0F, -8.7F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(9, 41)
            .m_171488_(2.0F, -8.7F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(9, 41)
            .m_171488_(-4.0F, -8.7F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(9, 41)
            .m_171488_(-4.0F, -8.7F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -7.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -8.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -6.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -5.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, -4.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(3.0F, -3.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(2.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 37)
            .m_171488_(-4.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(2.0F, -3.0F, -5.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 38)
            .m_171488_(4.0F, -2.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(4.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -2.0F, 0.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 40)
            .m_171488_(1.0F, -4.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-2.0F, -4.0F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(21, 32)
            .m_171488_(-1.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 40)
            .m_171488_(-1.0F, -3.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 32)
            .m_171488_(0.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 36)
            .m_171488_(0.0F, -3.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r3 = Head2.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(57, 24).m_171488_(-1.0F, -1.0F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, -7.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r4 = Head2.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 28)
            .m_171488_(2.0F, -3.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(56, 28)
            .m_171488_(-3.0F, 2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(56, 28)
            .m_171488_(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, -7.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r5 = Head2.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 28)
            .m_171488_(0.0F, 1.0F, -10.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(56, 28)
            .m_171488_(-3.0F, 1.0F, -10.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(56, 28)
            .m_171488_(-3.0F, -2.0F, -10.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(56, 28)
            .m_171488_(-1.0F, -1.0F, -10.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, -7.3F, -5.1F, 0.0F, 3.1416F, 0.7854F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(22, 44)
            .m_171488_(-3.0F, 0.0F, -3.2F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(-3.0F, 0.0F, 1.8F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(-1.0F, 2.0F, -3.4F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(-1.0F, 2.0F, 2.3F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(-1.0F, 1.0F, 2.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 41)
            .m_171488_(1.0F, 0.0F, -3.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 40)
            .m_171488_(1.0F, 0.0F, 0.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 40)
            .m_171488_(2.0F, 4.0F, -3.2F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 40)
            .m_171488_(2.0F, 4.0F, 0.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 43)
            .m_171488_(-3.0F, 4.0F, -3.2F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 43)
            .m_171488_(-3.0F, 4.0F, 0.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 36)
            .m_171488_(-2.0F, 5.0F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(-2.0F, 5.0F, 2.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(1.0F, 5.0F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(1.0F, 5.0F, 2.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 37)
            .m_171488_(2.0F, 8.0F, -3.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(7, 39)
            .m_171488_(2.0F, 8.0F, 0.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.0F, 8.0F, -3.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(7, 36)
            .m_171488_(-4.0F, 8.0F, 0.8F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(-4.0F, 3.0F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 41)
            .m_171488_(-4.0F, 3.0F, 1.8F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 36)
            .m_171488_(3.0F, 3.0F, -3.2F, 1.0F, 3.0F, 6.4F, new CubeDeformation(0.0F))
            .m_171514_(7, 39)
            .m_171488_(-2.0F, 8.0F, 2.1F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 39)
            .m_171488_(2.0F, 11.0F, 2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 39)
            .m_171488_(2.0F, 11.0F, -3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 39)
            .m_171488_(-3.0F, 11.0F, -3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 39)
            .m_171488_(-3.0F, 11.0F, 2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(3.0F, 10.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.01F))
            .m_171514_(0, 32)
            .m_171488_(-4.6F, 10.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.01F))
            .m_171514_(0, 32)
            .m_171488_(-4.6F, 3.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.01F))
            .m_171514_(0, 32)
            .m_171488_(3.4F, 3.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r6 = Body.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(57, 24).m_171488_(8.4F, 7.6F, 1.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, -7.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r7 = Body.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(56, 28).m_171488_(8.0F, 8.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, -7.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Adornments = Body.m_171599_(
         "Adornments",
         CubeListBuilder.m_171558_()
            .m_171514_(31, 32)
            .m_171488_(-2.0F, 11.0F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(31, 32)
            .m_171488_(-3.0F, 11.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 32)
            .m_171488_(1.0F, 11.0F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 32)
            .m_171488_(2.0F, 11.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 36)
            .m_171488_(-1.0F, 13.0F, -3.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 54)
            .m_171488_(-2.0F, 10.0F, -3.9F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 32)
            .m_171488_(-1.0F, 11.0F, -3.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 32)
            .m_171488_(-1.0F, 11.0F, 2.6F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 37)
            .m_171488_(-4.1F, 10.5F, -3.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 57)
            .m_171488_(1.0F, 11.0F, 2.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 52)
            .m_171488_(-2.0F, 10.0F, 2.9F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 62)
            .m_171488_(-1.0F, 13.0F, 2.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 37)
            .m_171488_(-4.1F, 10.5F, 3.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r8 = Adornments.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(24, 54).m_171488_(1.0F, 11.0F, -3.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r9 = Adornments.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_().m_171514_(42, 32).m_171488_(-14.0F, 12.0F, -8.65F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 3.1416F, 0.7854F)
      );
      PartDefinition cube_r10 = Adornments.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(57, 25)
            .m_171488_(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 24)
            .m_171488_(-5.5F, 4.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.5F, 10.5F, -3.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r11 = Adornments.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_().m_171514_(42, 45).m_171488_(12.0F, 12.0F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(42, 32)
            .m_171488_(-5.5F, -2.4F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(43, 36)
            .m_171488_(-5.5F, -2.4F, 1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.18F))
            .m_171514_(0, 34)
            .m_171488_(-5.5F, -5.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(-4.5F, -4.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(-3.5F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 48)
            .m_171488_(-1.5F, -3.7F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(-2.5F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 43)
            .m_171488_(-3.0F, 1.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(6, 39)
            .m_171488_(0.4F, 5.0F, -2.9F, 1.6F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 39)
            .m_171488_(0.4F, 5.0F, 2.1F, 1.6F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r12 = RightArm.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_()
            .m_171514_(32, 49)
            .m_171488_(15.0F, 4.3F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
            .m_171514_(31, 49)
            .m_171488_(13.0F, 11.3F, 7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(10.0F, -8.0F, -3.0F, -0.7854F, 3.1416F, 0.0F)
      );
      PartDefinition RightArm2 = RightArm.m_171599_(
         "RightArm2",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(5, 36)
            .m_171488_(-0.3F, -3.0F, -3.2F, 2.0F, 3.0F, 6.4F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(0.0F, 5.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(0, 34)
            .m_171488_(-1.0F, 3.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(-3.0F, 4.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 40)
            .m_171488_(-4.0F, -1.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 33)
            .m_171488_(-4.0F, 1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 36)
            .m_171488_(-4.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(-4.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, 1.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, 3.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-4.0F, 3.0F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 33)
            .m_171488_(-3.0F, 1.0F, 1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r13 = RightArm2.m_171599_(
         "cube_r13",
         CubeListBuilder.m_171558_().m_171514_(54, 56).m_171488_(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.0F, 6.5F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(0, 34)
            .m_171488_(2.0F, 4.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(1.0F, 3.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(1.0F, 3.0F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(4.5F, -5.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 41)
            .m_171488_(0.5F, -2.4F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(46, 48)
            .m_171488_(0.5F, -3.7F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(3.5F, -4.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(1.5F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 37)
            .m_171488_(2.5F, -3.5F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-1.5F, 5.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.01F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r14 = LeftArm.m_171599_(
         "cube_r14",
         CubeListBuilder.m_171558_()
            .m_171514_(32, 49)
            .m_171488_(-6.0F, 4.3F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
            .m_171514_(32, 49)
            .m_171488_(-5.0F, 11.3F, 7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(0.0F, -8.0F, -3.0F, -0.7854F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r15 = LeftArm.m_171599_(
         "cube_r15",
         CubeListBuilder.m_171558_().m_171514_(31, 56).m_171488_(17.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-14.0F, 6.5F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm2 = LeftArm.m_171599_(
         "LeftArm2",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(1, 40)
            .m_171488_(-1.6F, -3.0F, -3.2F, 2.0F, 3.0F, 6.4F, new CubeDeformation(0.02F))
            .m_171514_(6, 39)
            .m_171488_(-1.6F, 5.0F, -2.9F, 1.6F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 39)
            .m_171488_(-1.6F, 5.0F, 2.1F, 1.6F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 35)
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(1, 37)
            .m_171488_(0.0F, 1.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(1.0F, -1.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 32)
            .m_171488_(1.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 34)
            .m_171488_(1.0F, 1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 43)
            .m_171488_(1.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 37)
            .m_171488_(1.0F, 1.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(2, 33)
            .m_171488_(1.0F, 1.0F, -3.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 33)
            .m_171488_(1.0F, 1.0F, 1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition RightLegThighGuard_r1 = RightLeg.m_171599_(
         "RightLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 53).m_171488_(-2.5F, -1.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(1.2F)),
         PartPose.m_171423_(-1.0F, 3.5F, 0.0F, 0.0F, 0.0F, 0.4014F)
      );
      PartDefinition cube_r16 = RightLeg.m_171599_(
         "cube_r16",
         CubeListBuilder.m_171558_().m_171514_(57, 24).m_171488_(18.3F, 19.3F, 2.7F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(1.9F, -19.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).m_171555_(false),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r17 = LeftLeg.m_171599_(
         "cube_r17",
         CubeListBuilder.m_171558_().m_171514_(57, 24).m_171488_(21.0F, 16.6F, 2.7F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(-1.9F, -19.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition LeftLegThighGuard_r1 = LeftLeg.m_171599_(
         "LeftLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(14, 53).m_171488_(-7.7F, 1.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(1.2F)),
         PartPose.m_171423_(-4.8F, 3.5F, 0.0F, 0.0F, 3.1416F, -0.4276F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.Head.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.Body.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.RightArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftArm.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.RightLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
      this.LeftLeg.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }

   public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.RightArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
      this.LeftLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.Head.f_104204_ = netHeadYaw / (180.0F / (float)Math.PI);
      this.Head.f_104203_ = headPitch / (180.0F / (float)Math.PI);
      this.LeftArm.f_104203_ = Mth.m_14089_(limbSwing * 0.6662F) * limbSwingAmount;
      this.RightLeg.f_104203_ = Mth.m_14089_(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
