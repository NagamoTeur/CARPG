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

public class Modelfallgenxtophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelfallgenxtophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelfallgenxtophalf(ModelPart root) {
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
            .m_171514_(32, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.1F))
            .m_171514_(51, 9)
            .m_171488_(-5.0F, -10.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-5.0F, -10.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-1.0F, -10.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-1.0F, -10.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(3.0F, -10.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(3.0F, -10.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 8)
            .m_171488_(4.0F, -10.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 8)
            .m_171488_(-5.0F, -10.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(58, 29).m_171488_(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.51F))
            .m_171514_(57, 21)
            .m_171488_(-1.1F, 1.0F, -3.0F, 2.3F, 7.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(56, 8)
            .m_171488_(-1.1F, 1.0F, 2.0F, 2.3F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 8)
            .m_171488_(-1.1F, 8.0F, 2.0F, 2.1F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 10)
            .m_171488_(1.0F, 0.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 10)
            .m_171488_(1.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 10)
            .m_171488_(1.0F, 1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 10)
            .m_171488_(-2.0F, 1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 8)
            .m_171488_(-2.0F, 3.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 8)
            .m_171488_(1.0F, 3.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 8)
            .m_171488_(1.0F, 9.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 8)
            .m_171488_(-2.0F, 9.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 10)
            .m_171488_(-4.0F, 0.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 10)
            .m_171488_(-4.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 8)
            .m_171488_(-4.0F, 1.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(50, 8)
            .m_171488_(2.0F, 1.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(50, 8)
            .m_171488_(-4.0F, 6.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.02F))
            .m_171514_(50, 8)
            .m_171488_(2.0F, 6.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 6)
            .m_171488_(-4.5F, -1.0F, -3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 6)
            .m_171488_(-4.5F, -1.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 8)
            .m_171488_(-4.5F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 9)
            .m_171488_(-4.5F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 27)
            .m_171488_(-4.5F, 8.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 9)
            .m_171488_(-4.5F, 8.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 27)
            .m_171488_(3.5F, 8.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 9)
            .m_171488_(3.5F, 8.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(19, 29)
            .m_171488_(-4.6F, 9.5F, -3.0F, 9.5F, 2.5F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(18, 30)
            .m_171488_(-4.6F, 9.5F, 1.9F, 9.5F, 2.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171488_(4.0F, 9.5F, -2.8F, 1.0F, 2.5F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(50, 6)
            .m_171488_(-4.8F, 9.5F, -3.0F, 1.0F, 2.5F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 9)
            .m_171488_(-2.4F, 1.0F, -3.0F, 1.4F, 1.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(59, 10)
            .m_171488_(0.9F, 1.0F, -3.0F, 1.4F, 1.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(57, 1)
            .m_171488_(-3.4F, 3.0F, -3.0F, 1.3F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 0)
            .m_171488_(2.3F, 3.0F, -3.0F, 1.3F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 6)
            .m_171488_(3.5F, -1.0F, -3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 6)
            .m_171488_(3.5F, -1.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 8)
            .m_171488_(3.5F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 6)
            .m_171488_(-4.5F, 6.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 10)
            .m_171488_(3.5F, 6.0F, 2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 10)
            .m_171488_(-2.0F, 10.0F, -3.7F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 9)
            .m_171488_(3.0F, 9.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 10)
            .m_171488_(3.0F, 10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 9)
            .m_171488_(-4.0F, 9.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 10)
            .m_171488_(-4.0F, 10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 39)
            .m_171488_(-4.1F, 10.5F, -3.1F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r2 = Body.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 33)
            .m_171488_(13.5F, 13.7F, 1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 29)
            .m_171488_(6.0F, 6.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 29)
            .m_171488_(12.0F, 12.0F, 0.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(47, 4)
            .m_171488_(-1.0F, -3.0F, -3.2F, 2.0F, 3.0F, 6.4F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171488_(0.0F, -1.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171488_(0.0F, 5.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(48, 6)
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(49, 4)
            .m_171488_(-1.0F, 3.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 56)
            .m_171488_(-4.5F, 4.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 6)
            .m_171488_(-4.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 8)
            .m_171488_(-3.0F, -3.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 5)
            .m_171488_(-5.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 6)
            .m_171488_(-4.0F, -4.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 42)
            .m_171488_(-4.0F, 1.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-4.0F, 0.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 47)
            .m_171488_(-4.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(65, 41)
            .m_171488_(-4.0F, 1.0F, -3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 68)
            .m_171488_(-5.0F, -1.5F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r3 = RightArm.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(0, 68).m_171488_(5.0F, -1.5F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.21F)),
         PartPose.m_171423_(5.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r4 = RightArm.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(58, 17).m_171488_(-21.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(15.5F, -3.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(47, 5)
            .m_171488_(-1.0F, -3.0F, -3.2F, 2.0F, 3.0F, 6.4F, new CubeDeformation(0.0F))
            .m_171514_(48, 6)
            .m_171488_(-1.6F, -1.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(48, 6)
            .m_171488_(-1.6F, 5.0F, -2.9F, 1.6F, 2.0F, 5.8F, new CubeDeformation(0.0F))
            .m_171514_(48, 6)
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(49, 3)
            .m_171488_(0.0F, 1.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(31, 56)
            .m_171488_(3.5F, 4.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 6)
            .m_171488_(1.0F, -2.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 8)
            .m_171488_(1.0F, -3.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 5)
            .m_171488_(4.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 6)
            .m_171488_(3.0F, -4.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 8)
            .m_171488_(1.0F, 0.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 32)
            .m_171488_(1.0F, 1.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 46)
            .m_171488_(1.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 60)
            .m_171488_(1.0F, 1.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(54, 41)
            .m_171488_(1.0F, 1.0F, -3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r5 = LeftArm.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 68)
            .m_171488_(-6.0F, -1.5F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.21F))
            .m_171514_(0, 68)
            .m_171488_(-10.0F, 0.5F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(-5.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition cube_r6 = LeftArm.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(58, 37).m_171488_(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.5F, -3.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r7 = RightLeg.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(41, 32)
            .m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 32)
            .m_171488_(-1.5F, -1.5F, -5.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, 8.5F, 2.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)).m_171555_(false),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition cube_r8 = LeftLeg.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(41, 32)
            .m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 32)
            .m_171488_(-1.5F, -1.5F, -5.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, 8.5F, 2.5F, 0.0F, 0.0F, 0.7854F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
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
