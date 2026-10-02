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

public class Modelbjorntophalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelbjorntophalf"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelbjorntophalf(ModelPart root) {
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
            .m_171514_(0, 32)
            .m_171488_(2.0F, 0.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, 0.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 41)
            .m_171488_(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(46, 41)
            .m_171488_(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(1, 32)
            .m_171488_(-5.0F, -3.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-5.0F, -3.0F, -5.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 33)
            .m_171488_(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 32)
            .m_171488_(-3.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 42)
            .m_171488_(2.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-4.0F, -4.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-5.0F, -4.0F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -4.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 34)
            .m_171488_(-4.0F, -7.6F, 5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(11, 34)
            .m_171488_(2.0F, -4.0F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
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
            .m_171488_(-4.0F, -8.0F, 4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(9, 41)
            .m_171488_(-3.0F, -8.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(8, 41)
            .m_171488_(1.0F, -8.0F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(8, 41)
            .m_171488_(1.0F, -8.0F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.01F))
            .m_171514_(5, 41)
            .m_171488_(1.0F, -5.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 37)
            .m_171488_(-2.0F, -5.0F, 3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 33)
            .m_171488_(3.0F, -8.0F, -5.0F, 1.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 39)
            .m_171488_(4.0F, -8.6F, -1.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 41)
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
            .m_171488_(-1.0F, -9.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
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
            .m_171514_(14, 32)
            .m_171488_(3.0F, -3.0F, 1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(2.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 37)
            .m_171488_(-4.0F, -2.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(2.0F, -3.0F, -5.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 38)
            .m_171488_(4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 41)
            .m_171488_(4.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -2.0F, 1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(-5.0F, -2.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 40)
            .m_171488_(1.0F, -4.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-2.0F, -4.0F, 3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 39)
            .m_171488_(-1.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 36)
            .m_171488_(-1.0F, -3.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 38)
            .m_171488_(0.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 36)
            .m_171488_(0.0F, -3.0F, 4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 25)
            .m_171488_(-4.0F, -8.3F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 24)
            .m_171488_(2.0F, -8.3F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(30, 22)
            .m_171488_(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
            .m_171514_(30, 22)
            .m_171488_(5.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F)),
         PartPose.m_171423_(-3.0F, -8.2829F, -8.2872F, -0.6545F, 0.0F, 0.0F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(34, 22)
            .m_171488_(5.0F, -2.0908F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F))
            .m_171514_(34, 22)
            .m_171488_(-1.0F, -2.0908F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.5F)),
         PartPose.m_171423_(-3.0F, -10.1092F, -10.6672F, -1.2654F, 0.0F, 0.0F)
      );
      PartDefinition cube_r3 = Head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(21, 11)
            .m_171488_(5.0F, -1.2F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
            .m_171514_(22, 10)
            .m_171488_(-1.0F, -1.2F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(-3.0F, -7.3F, -6.5F, -0.3054F, 0.0F, 0.0F)
      );
      PartDefinition cube_r4 = Head.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(57, 25).m_171488_(-1.0F, -1.0F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, -7.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r5 = Head.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(57, 29).m_171488_(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(0.0F, -7.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(26, 35)
            .m_171488_(-1.0F, 1.0F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 34)
            .m_171488_(-1.0F, 0.0F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 39)
            .m_171488_(-1.0F, 3.0F, -3.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 52)
            .m_171488_(-1.0F, 6.0F, 2.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 47)
            .m_171488_(-1.0F, 0.0F, 2.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 46)
            .m_171488_(-2.0F, 3.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 35)
            .m_171488_(-2.0F, 3.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 35)
            .m_171488_(1.0F, 3.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 43)
            .m_171488_(-2.0F, 8.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 43)
            .m_171488_(1.0F, 8.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 41)
            .m_171488_(-3.0F, 8.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 41)
            .m_171488_(-3.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 41)
            .m_171488_(2.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 35)
            .m_171488_(2.0F, 8.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 35)
            .m_171488_(-2.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 35)
            .m_171488_(-2.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 24)
            .m_171488_(-4.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 35)
            .m_171488_(1.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 35)
            .m_171488_(1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 24)
            .m_171488_(3.0F, 1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 39)
            .m_171488_(-5.0F, 4.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(46, 37)
            .m_171488_(3.0F, 4.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 37)
            .m_171488_(3.0F, 8.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(46, 37)
            .m_171488_(-5.0F, 8.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(26, 51)
            .m_171488_(-5.0F, 2.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(1, 35)
            .m_171488_(-5.0F, 5.0F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(1, 35)
            .m_171488_(3.0F, 5.0F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(31, 52)
            .m_171488_(-4.0F, 7.0F, 2.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(35, 53)
            .m_171488_(2.0F, 7.0F, 2.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.01F))
            .m_171514_(1, 35)
            .m_171488_(3.0F, 1.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 35)
            .m_171488_(2.0F, 6.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 35)
            .m_171488_(-4.0F, 6.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 35)
            .m_171488_(-4.0F, 1.0F, 2.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 47)
            .m_171488_(-4.0F, 5.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 36)
            .m_171488_(-5.0F, 5.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 36)
            .m_171488_(3.0F, 5.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 34)
            .m_171488_(-4.0F, 9.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 34)
            .m_171488_(3.0F, 9.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(25, 57)
            .m_171488_(-4.0F, 10.0F, -3.1F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.0F, 11.0F, -3.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
            .m_171514_(32, 37)
            .m_171488_(-4.0F, 7.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 35)
            .m_171488_(3.0F, 7.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 38)
            .m_171488_(3.0F, 5.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(35, 35)
            .m_171488_(3.0F, 2.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 47)
            .m_171488_(1.0F, 3.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(-2, 34)
            .m_171488_(2.0F, -0.9F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(-2, 34)
            .m_171488_(-4.0F, -0.9F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(57, 29)
            .m_171488_(-1.0F, 9.6F, -3.6F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(57, 29)
            .m_171488_(0.5F, 10.0848F, -3.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(57, 29)
            .m_171488_(-1.0F, 11.5F, -3.6F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(57, 29)
            .m_171488_(-1.5F, 10.0848F, -3.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(57, 25)
            .m_171488_(-1.0F, 10.0F, -3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
            .m_171514_(2, 34)
            .m_171488_(-4.0F, 11.9F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F))
            .m_171514_(2, 34)
            .m_171488_(1.0F, 11.9F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F))
            .m_171514_(2, 34)
            .m_171488_(-1.0F, 12.8F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(1.0F, 12.8F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-2.0F, 12.8F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 35)
            .m_171488_(-3.0F, 11.0F, -3.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r6 = Body.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(57, 25).m_171488_(8.0F, 7.3F, 1.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(0.0F, -7.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r7 = Body.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(57, 29)
            .m_171488_(9.0F, 3.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F))
            .m_171514_(57, 29)
            .m_171488_(10.0F, 10.0F, 1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F))
            .m_171514_(57, 29)
            .m_171488_(3.0F, 9.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F))
            .m_171514_(57, 29)
            .m_171488_(7.0F, 7.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -7.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(0, 33)
            .m_171488_(-4.6F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(26, 28)
            .m_171488_(-4.0F, 7.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(43, 55)
            .m_171488_(-4.0F, 8.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 56)
            .m_171488_(-2.0F, 4.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 56)
            .m_171488_(-4.0F, 6.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 37)
            .m_171488_(-3.5F, 4.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(13, 36)
            .m_171488_(-4.0F, 4.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(23, 55)
            .m_171488_(-4.0F, 2.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 55)
            .m_171488_(-1.0F, 1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.5F, 1.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(6, 33)
            .m_171488_(-4.6F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(1, 33)
            .m_171488_(-3.5F, 4.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(42, 46)
            .m_171488_(-4.0F, -1.0F, -3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F))
            .m_171514_(11, 34)
            .m_171488_(-3.0F, -2.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 32)
            .m_171488_(-6.0F, -3.5F, 2.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(0, 33)
            .m_171488_(-4.6F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(14, 36)
            .m_171488_(-4.6F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(26, 32)
            .m_171488_(-6.0F, -3.5F, -4.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(26, 54)
            .m_171488_(-6.0F, -6.5F, -3.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 52)
            .m_171488_(-5.0F, -5.5F, -3.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 52)
            .m_171488_(-5.0F, -4.5F, -3.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 52)
            .m_171488_(-5.0F, -5.5F, 2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 52)
            .m_171488_(-5.0F, -4.5F, 2.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-6.0F, -4.0F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 38)
            .m_171488_(-3.0F, -2.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 33)
            .m_171488_(-4.5F, -3.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 35)
            .m_171488_(-4.6F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(1, 33)
            .m_171488_(-4.6F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(0, 33)
            .m_171488_(-4.6F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r8 = RightArm.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(57, 29).m_171488_(0.0F, 10.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, -9.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r9 = RightArm.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_().m_171514_(57, 25).m_171488_(1.0F, 10.3F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(5.0F, -9.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r10 = RightArm.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(54, 32)
            .m_171488_(-3.5F, -9.5F, -9.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F))
            .m_171514_(56, 8)
            .m_171488_(-2.5F, -9.5F, -9.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 32)
            .m_171488_(-1.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F))
            .m_171514_(56, 8)
            .m_171488_(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.0F, 8.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(0, 37)
            .m_171488_(-1.0F, -4.0F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 35)
            .m_171488_(3.5F, -3.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 33)
            .m_171488_(3.7F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(2, 32)
            .m_171488_(3.7F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(1, 34)
            .m_171488_(3.8F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(1, 33)
            .m_171488_(3.8F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.04F))
            .m_171514_(16, 37)
            .m_171488_(3.8F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 40)
            .m_171488_(3.8F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(8, 37)
            .m_171488_(3.0F, 4.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(18, 35)
            .m_171488_(2.5F, 4.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(16, 36)
            .m_171488_(2.5F, 4.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(19, 36)
            .m_171488_(3.8F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(16, 40)
            .m_171488_(3.8F, 1.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 28)
            .m_171488_(1.0F, 7.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 56)
            .m_171488_(1.0F, 6.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 55)
            .m_171488_(1.0F, 4.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 55)
            .m_171488_(0.0F, 8.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(22, 55)
            .m_171488_(0.0F, 2.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 46)
            .m_171488_(-1.0F, -1.0F, -3.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(26, 54)
            .m_171488_(5.0F, -6.5F, -3.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(45, 52)
            .m_171488_(1.0F, -5.5F, 2.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 52)
            .m_171488_(1.0F, -5.5F, -3.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 52)
            .m_171488_(0.0F, -4.5F, 2.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 35)
            .m_171488_(0.0F, -4.5F, -3.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 32)
            .m_171488_(-1.0F, -3.5F, -4.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(26, 32)
            .m_171488_(-1.0F, -3.5F, 2.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
            .m_171514_(14, 35)
            .m_171488_(-1.0F, -2.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 36)
            .m_171488_(-1.0F, -2.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 55)
            .m_171488_(0.0F, 1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r11 = LeftArm.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_().m_171514_(57, 25).m_171488_(10.0F, 0.3F, 2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)),
         PartPose.m_171423_(-5.0F, -9.3F, -6.1F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r12 = LeftArm.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_().m_171514_(57, 29).m_171488_(9.5F, 0.3F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.0F, -9.3F, -5.1F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r13 = LeftArm.m_171599_(
         "cube_r13",
         CubeListBuilder.m_171558_()
            .m_171514_(54, 40)
            .m_171488_(19.5F, -9.5F, -9.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F))
            .m_171514_(54, 40)
            .m_171488_(17.5F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F))
            .m_171514_(56, 14)
            .m_171488_(19.5F, -9.5F, -9.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 14)
            .m_171488_(17.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-14.0F, 8.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(2, 34)
            .m_171488_(-2.1F, 8.2F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-3.1F, 9.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-1.1F, 8.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-1.1F, 11.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-3.1F, 12.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-0.1F, 11.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition RightLegThighGuard_r1 = RightLeg.m_171599_(
         "RightLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 53).m_171488_(-2.5F, -2.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(1.2F)),
         PartPose.m_171423_(-1.0F, 3.5F, 0.0F, 0.0F, 0.0F, 0.4014F)
      );
      PartDefinition cube_r14 = RightLeg.m_171599_(
         "cube_r14",
         CubeListBuilder.m_171558_().m_171514_(21, 56).m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.6F, 10.5F, -3.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition LeggingsR = RightLeg.m_171599_("LeggingsR", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(2, 34)
            .m_171488_(0.1F, 8.2F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-0.9F, 9.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-2.9F, 8.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(2, 34)
            .m_171488_(-2.9F, 11.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
            .m_171514_(2, 34)
            .m_171488_(-1.9F, 12.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 34)
            .m_171488_(-0.9F, 11.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLegThighGuard_r1 = LeftLeg.m_171599_(
         "LeftLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 53).m_171488_(-7.7F, 0.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(1.2F)),
         PartPose.m_171423_(-4.8F, 3.5F, 0.0F, 0.0F, 3.1416F, -0.4276F)
      );
      PartDefinition cube_r15 = LeftLeg.m_171599_(
         "cube_r15",
         CubeListBuilder.m_171558_().m_171514_(26, 43).m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.6F, 10.5F, -3.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition LeggingsL = LeftLeg.m_171599_("LeggingsL", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
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
