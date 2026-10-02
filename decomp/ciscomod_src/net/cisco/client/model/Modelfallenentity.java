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

public class Modelfallenentity<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modelfallenentity"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modelfallenentity(ModelPart root) {
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
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
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
         CubeListBuilder.m_171558_().m_171514_(58, 16).m_171488_(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(56, 8)
            .m_171488_(-1.1F, 1.0F, -3.0F, 2.3F, 7.0F, 1.0F, new CubeDeformation(0.0F))
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
            .m_171488_(-4.0F, 1.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 8)
            .m_171488_(2.0F, 1.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 8)
            .m_171488_(-4.0F, 6.0F, 2.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
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
            .m_171488_(3.5F, 8.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(19, 29)
            .m_171488_(-4.6F, 9.5F, -3.0F, 9.5F, 2.5F, 1.0F, new CubeDeformation(0.0F))
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
            .m_171514_(57, 8)
            .m_171488_(-3.4F, 3.0F, -3.0F, 1.3F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 8)
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
            .m_171488_(-2.0F, 10.0F, -3.2F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 9)
            .m_171488_(3.0F, 9.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 10)
            .m_171488_(3.0F, 10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 9)
            .m_171488_(-4.0F, 9.0F, 1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 10)
            .m_171488_(-4.0F, 10.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(21, 37)
            .m_171488_(-1.08F, 11.1F, -3.1F, 2.18F, 7.2F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 52)
            .m_171488_(-2.0F, 11.0F, -3.2F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 38)
            .m_171488_(1.0F, 11.0F, -3.2F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r2 = Body.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 34)
            .m_171488_(18.5F, 18.7F, 1.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 16)
            .m_171488_(6.0F, 6.0F, 1.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 16)
            .m_171488_(12.0F, 12.0F, 1.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -4.7F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r3 = Body.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(6, 53).m_171488_(-1.85F, -1.366F, -3.2F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.0F, 18.366F, 0.0F, 0.0F, 0.0F, 0.6109F)
      );
      PartDefinition cube_r4 = Body.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(20, 60).m_171488_(0.936F, -1.0F, 2.8F, 1.0F, 3.0F, 0.1F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.366F, 18.0F, -6.0F, 0.0F, 0.0F, -0.5236F)
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
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
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
            .m_171514_(53, 9)
            .m_171488_(-4.0F, 1.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-4.0F, 0.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 6)
            .m_171488_(-4.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(-4.0F, 1.0F, -3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r5 = RightArm.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(58, 15).m_171488_(-21.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
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
            .m_171488_(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
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
            .m_171514_(54, 9)
            .m_171488_(1.0F, 1.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 6)
            .m_171488_(1.0F, 5.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 9)
            .m_171488_(1.0F, 1.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 9)
            .m_171488_(1.0F, 1.0F, -3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r6 = LeftArm.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(56, 15).m_171488_(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.5F, -3.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(36, 32)
            .m_171488_(-2.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 32)
            .m_171488_(-2.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition RightLeg2 = RightLeg.m_171599_(
         "RightLeg2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(48, 9)
            .m_171488_(-3.0F, 3.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 9)
            .m_171488_(-3.0F, 6.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r7 = RightLeg2.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(38, 38)
            .m_171488_(-9.5F, -3.5F, -1.0F, 4.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 36)
            .m_171488_(-10.5F, -3.5F, -5.0F, 1.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 56)
            .m_171488_(-10.4491F, -3.9694F, -0.5F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 56)
            .m_171488_(-10.4491F, 5.5F, -0.5F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 52)
            .m_171488_(-10.8491F, -3.9694F, -5.5F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(51, 53)
            .m_171488_(-10.8491F, 5.5F, -5.5F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(7.2491F, 2.3694F, 4.0F, 0.3054F, 0.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(45, 32)
            .m_171488_(-1.0F, 7.0F, -3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 32)
            .m_171488_(-1.0F, 7.0F, 2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLeg2 = LeftLeg.m_171599_(
         "LeftLeg2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 16)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(48, 9)
            .m_171488_(-1.2F, 3.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 9)
            .m_171488_(-1.2F, 6.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r8 = LeftLeg2.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(51, 56)
            .m_171488_(-5.4491F, -3.9694F, -0.5F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 56)
            .m_171488_(-5.4491F, 5.5F, -0.5F, 5.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(39, 40)
            .m_171488_(-5.3491F, -3.5F, -1.0F, 4.7F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 51)
            .m_171488_(-0.8491F, -3.9694F, -5.5F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(51, 52)
            .m_171488_(-0.8491F, 5.5F, -5.5F, 1.0F, 1.0F, 5.5F, new CubeDeformation(0.0F))
            .m_171514_(38, 35)
            .m_171488_(-0.8491F, -3.5F, -5.0F, 0.5F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.4491F, 2.3694F, 4.0F, 0.3054F, 0.0F, 0.0F)
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
