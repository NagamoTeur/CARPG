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

public class Modelfelltophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelfelltophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelfelltophalf(ModelPart root) {
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
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(9, 45)
            .m_171488_(-2.4F, -4.0F, -5.0F, 1.2F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(7, 47)
            .m_171488_(-1.3F, -6.7F, -4.8F, 2.5F, 1.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 44)
            .m_171488_(-2.4F, -8.6F, -5.0F, 1.2F, 3.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 32)
            .m_171488_(1.1F, -4.0F, -5.0F, 1.2F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 37)
            .m_171488_(1.1F, -8.6F, -5.0F, 1.2F, 3.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 32)
            .m_171488_(-3.4F, -5.0F, -5.0F, 1.0F, 5.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 56)
            .m_171488_(-3.4F, -9.0F, -5.0F, 1.0F, 2.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(7, 57)
            .m_171488_(2.3F, -5.0F, -5.0F, 1.0F, 5.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 39)
            .m_171488_(2.3F, -9.0F, -5.0F, 1.0F, 2.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.9F, -10.1F, -5.0F, 1.5F, 10.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 42)
            .m_171488_(-4.9F, -10.1F, 3.7F, 1.5F, 10.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 32)
            .m_171488_(3.9F, -9.0F, -5.0F, 0.7F, 2.0F, 9.7F, new CubeDeformation(0.0F))
            .m_171514_(13, 32)
            .m_171488_(-4.7F, -9.0F, -5.0F, 0.7F, 2.0F, 9.7F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-5.0F, -10.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 41)
            .m_171488_(4.0F, -10.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 44)
            .m_171488_(-1.0F, -10.0F, 4.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 41)
            .m_171488_(-4.0F, -9.0F, 4.0F, 8.0F, 2.0F, 0.7F, new CubeDeformation(0.0F))
            .m_171514_(16, 41)
            .m_171488_(-4.5F, -0.5F, 4.0F, 9.0F, 1.0F, 0.7F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(24, 1).m_171488_(-3.5F, -1.933F, -0.3F, 2.5F, 1.0F, 0.6F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.416F, 0.933F, -4.7F, 0.0F, 0.0F, -0.5236F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(24, 3).m_171488_(-0.7107F, -0.7722F, 4.0F, 2.5F, 1.0F, 0.6F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.2893F, 0.8722F, -9.0F, 0.0F, 0.0F, 0.3491F)
      );
      PartDefinition cube_r3 = Head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 38)
            .m_171488_(-1.0F, -2.0F, -9.9F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 38)
            .m_171488_(-3.0F, 0.0F, -9.9F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
            .m_171514_(11, 61)
            .m_171488_(-0.5F, -1.5F, -10.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 61)
            .m_171488_(-2.5F, 0.5F, -10.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)),
         PartPose.m_171423_(0.0F, -7.0F, -4.5F, 0.0F, 3.1416F, 0.7854F)
      );
      PartDefinition cube_r4 = Head.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 38)
            .m_171488_(-2.0F, -2.0F, -0.8F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-0.5F, -0.5F, -0.6F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 61)
            .m_171488_(-1.5F, -1.5F, -1.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -7.0F, -4.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r5 = Head.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(4, 52)
            .m_171488_(2.25F, -4.6508F, 8.5F, 1.5F, 10.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 52)
            .m_171488_(2.25F, -4.6508F, -0.5F, 1.5F, 10.2F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(7.05F, -4.5492F, -4.7029F, 0.0F, 0.0F, -3.1416F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(32, 21)
            .m_171488_(-5.0F, -1.0F, -3.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-5.0F, 6.0F, -3.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(3.0F, 6.0F, -3.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(3.0F, -1.0F, -3.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(1.0F, 0.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-3.0F, 0.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-2.0F, 2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(1.0F, 2.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-3.0F, 10.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-2.0F, 7.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(1.0F, 7.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(2.0F, 4.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-3.0F, 4.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(1.0F, 10.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-1.0F, 9.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 21)
            .m_171488_(-1.0F, 1.0F, -3.0F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 9)
            .m_171488_(3.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 9)
            .m_171488_(-5.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 30)
            .m_171488_(-1.0F, 10.5F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 30)
            .m_171488_(-1.0F, 10.5F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(-4.0F, 10.5F, -3.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(-4.0F, 10.5F, 1.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 33)
            .m_171488_(-1.0F, 12.5F, -3.8F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 41)
            .m_171488_(-2.0F, 12.5F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 36)
            .m_171488_(1.0F, 12.5F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(3.0F, 10.5F, -3.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(3.0F, 10.5F, 1.8F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 40)
            .m_171488_(-5.0F, 11.0F, -3.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 44)
            .m_171488_(-5.1F, 11.0F, 2.5F, 6.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 40)
            .m_171488_(1.0F, 11.0F, -3.5F, 4.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 40)
            .m_171488_(4.2F, 11.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 40)
            .m_171488_(-5.2F, 11.0F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 44)
            .m_171488_(1.0F, 11.0F, 2.5F, 4.1F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 39)
            .m_171488_(-1.0F, 0.2F, 2.4F, 2.0F, 4.8F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 23)
            .m_171488_(2.0F, 1.0F, 2.1F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 23)
            .m_171488_(1.0F, 1.0F, 2.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 23)
            .m_171488_(1.0F, 5.0F, 2.1F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(21, 23)
            .m_171488_(-4.0F, 1.0F, 2.1F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 23)
            .m_171488_(-2.0F, 1.0F, 2.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(21, 23)
            .m_171488_(-3.0F, 5.0F, 2.1F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(21, 27)
            .m_171488_(-2.0F, 9.0F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 27)
            .m_171488_(1.0F, 9.0F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 39)
            .m_171488_(-1.0F, 5.0F, 2.1F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 44)
            .m_171488_(-2.5F, 10.0F, 2.2F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(1.0F, 0.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 55)
            .m_171488_(1.0F, 4.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(1.0F, 8.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(-5.0F, 0.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 54)
            .m_171488_(-4.0F, 4.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(-3.0F, 8.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(-3.0F, 9.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 45)
            .m_171488_(2.0F, 9.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r6 = Body.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(31, 56)
            .m_171488_(-6.5F, -7.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 56)
            .m_171488_(-2.5F, -6.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 56)
            .m_171488_(-2.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 56)
            .m_171488_(-6.5F, 1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.5F, 7.5F, 2.5F, 0.0F, 0.0F, 1.5708F)
      );
      PartDefinition cube_r7 = Body.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(35, 36)
            .m_171488_(1.0F, 12.5F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 43)
            .m_171488_(-2.0F, 12.5F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 33)
            .m_171488_(-1.0F, 12.5F, -3.8F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r8 = Body.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(28, 61).m_171488_(-1.0F, -1.0F, -0.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 3.0F, -3.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r9 = Body.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_()
            .m_171514_(51, 39)
            .m_171488_(0.8F, 0.8F, -0.4F, 1.7F, 1.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 22)
            .m_171488_(-0.5F, -0.5F, 5.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 38)
            .m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 2.8995F, -2.8F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(47, 38)
            .m_171488_(-1.0F, -3.5F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(46, 0)
            .m_171488_(-4.0F, -3.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(39, 37)
            .m_171488_(-4.5F, -2.0F, -1.0F, 1.5F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 32)
            .m_171488_(-4.5F, -4.0F, -1.0F, 1.5F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 32)
            .m_171488_(-4.5F, -5.0F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 51)
            .m_171488_(-4.0F, 5.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 53)
            .m_171488_(-4.0F, 7.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 39)
            .m_171488_(-4.0F, 3.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 40)
            .m_171488_(0.6F, 3.0F, -2.4F, 1.0F, 1.0F, 4.8F, new CubeDeformation(0.0F))
            .m_171514_(47, 40)
            .m_171488_(0.6F, 6.0F, -2.4F, 1.0F, 1.0F, 4.8F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(39, 36)
            .m_171488_(3.0F, -2.0F, -1.0F, 1.5F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 38)
            .m_171488_(0.0F, -3.5F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 5)
            .m_171488_(1.0F, -3.0F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(30, 32)
            .m_171488_(3.1F, -4.0844F, -1.0F, 1.5F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 32)
            .m_171488_(4.1F, -5.0844F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 44)
            .m_171488_(1.0F, 7.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 44)
            .m_171488_(2.0F, 5.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 39)
            .m_171488_(2.0F, 3.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 40)
            .m_171488_(-1.6F, 3.0F, -2.4F, 1.0F, 1.0F, 4.8F, new CubeDeformation(0.0F))
            .m_171514_(47, 40)
            .m_171488_(-1.6F, 6.0F, -2.4F, 1.0F, 1.0F, 4.8F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition RightLegThighGuard_r1 = RightLeg.m_171599_(
         "RightLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(9, 49).m_171488_(-2.5F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171423_(-1.0F, 3.5F, 0.0F, 0.0F, 0.0F, 0.3578F)
      );
      PartDefinition cube_r10 = RightLeg.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_().m_171514_(59, 54).m_171488_(-2.0F, -2.0F, -0.05F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.6F, 10.0F, -3.25F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).m_171555_(false),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r11 = LeftLeg.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_().m_171514_(38, 51).m_171488_(-0.68F, -0.3F, 0.1F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.16F)),
         PartPose.m_171423_(6.1F, 5.5F, 1.5F, 0.0F, 0.0F, -0.3491F)
      );
      PartDefinition LeftLegThighGuard_r1 = LeftLeg.m_171599_(
         "LeftLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(22, 44).m_171480_().m_171488_(-1.5F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.8F)).m_171555_(false),
         PartPose.m_171423_(2.1744F, 3.275F, 0.0F, 0.0F, 0.0F, -0.3578F)
      );
      PartDefinition cube_r12 = LeftLeg.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_().m_171514_(59, 54).m_171488_(-2.0F, -2.0F, 0.95F, 2.0F, 2.0F, 0.5F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.6F, 10.0F, -4.25F, 0.0F, 0.0F, 0.7854F)
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
