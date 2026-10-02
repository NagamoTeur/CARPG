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

public class Modeldarksteeltop<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "modeldarksteeltop"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public Modeldarksteeltop(ModelPart root) {
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
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.51F))
            .m_171514_(32, 10)
            .m_171488_(-4.0F, -6.5F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 10)
            .m_171488_(-3.0F, -4.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 10)
            .m_171488_(-2.0F, -4.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 10)
            .m_171488_(-2.0F, -3.5F, -5.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 10)
            .m_171488_(1.0F, -3.5F, -5.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 0)
            .m_171488_(-2.0F, -7.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 0)
            .m_171488_(1.0F, -7.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 4)
            .m_171488_(3.0F, -6.5F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 4)
            .m_171488_(2.0F, -4.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 4)
            .m_171488_(1.0F, -4.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(43, 5)
            .m_171488_(-3.0F, -7.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(25, 44)
            .m_171488_(-1.0F, -7.5F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.03F))
            .m_171514_(25, 44)
            .m_171488_(-1.0F, -8.5F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.03F))
            .m_171514_(25, 44)
            .m_171488_(-1.0F, -9.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.03F))
            .m_171514_(37, 0)
            .m_171488_(-1.0F, -8.5F, 4.0F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 0)
            .m_171488_(2.0F, -7.5F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(34, 53)
            .m_171488_(1.0F, 1.0F, 9.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.03F))
            .m_171514_(34, 53)
            .m_171488_(1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.03F)),
         PartPose.m_171423_(0.0F, -9.5858F, -4.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171514_(0, 32)
            .m_171488_(-4.5F, -1.0F, -3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 45)
            .m_171488_(-3.5F, -1.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 51)
            .m_171488_(2.5F, -1.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-3.5F, 5.0F, -3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-3.5F, 5.0F, 1.8F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-4.5F, 4.0F, 1.8F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-4.5F, -1.0F, 1.8F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(3.5F, 4.0F, 1.8F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(3.5F, -1.0F, 1.8F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-3.5F, -1.0F, 1.8F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-2.5F, 2.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-2.5F, -0.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-1.5F, 2.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-1.5F, -0.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(0.5F, 2.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(0.5F, -0.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-0.5F, 2.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(-0.5F, -0.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(2.5F, 5.0F, 1.8F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(2.5F, -1.0F, 1.8F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(4, 32)
            .m_171488_(2.5F, 5.0F, -3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(-2.5F, 6.0F, -3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(1.5F, 6.0F, 1.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(-2.5F, 6.0F, 1.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(1.5F, 9.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(-2.5F, 9.0F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 32)
            .m_171488_(1.5F, 6.0F, -3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 45)
            .m_171488_(-1.5F, 8.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 25)
            .m_171488_(-1.5F, 10.0F, -3.3F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 61)
            .m_171488_(1.5F, 10.0F, -3.1F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 7)
            .m_171488_(3.7F, 10.0F, -3.25F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.08F))
            .m_171514_(34, 7)
            .m_171488_(-4.7F, 10.0F, -3.25F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.08F))
            .m_171514_(50, 61)
            .m_171488_(-4.5F, 10.0F, -3.1F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 25)
            .m_171488_(-4.5F, 10.0F, 1.8F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(38, 25)
            .m_171488_(1.5F, 10.0F, 1.85F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 48)
            .m_171488_(-2.5F, -1.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 53)
            .m_171488_(1.5F, -1.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 47)
            .m_171488_(-1.5F, 0.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 32)
            .m_171488_(3.5F, -1.0F, -3.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(75, 63)
            .m_171488_(-4.1F, 11.2F, -3.3F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.1F))
            .m_171514_(75, 63)
            .m_171488_(-4.1F, 11.2F, 3.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition bone = Body.m_171599_(
         "bone",
         CubeListBuilder.m_171558_().m_171514_(4, 32).m_171488_(1.5F, 1.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, 0.0F)
      );
      PartDefinition bone2 = Body.m_171599_(
         "bone2",
         CubeListBuilder.m_171558_().m_171514_(4, 32).m_171488_(1.5F, 2.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(48, 43)
            .m_171488_(0.0F, -4.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(16, 32)
            .m_171488_(-1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(-2.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 32)
            .m_171488_(-2.0F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(42, 8)
            .m_171488_(-1.0F, 8.0F, -3.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.04F))
            .m_171514_(42, 8)
            .m_171488_(-2.0F, 7.0F, -3.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.03F))
            .m_171514_(41, 9)
            .m_171488_(-1.0F, 8.0F, 3.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.04F))
            .m_171514_(33, 23)
            .m_171488_(-2.0F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 23)
            .m_171488_(-2.0F, -4.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 23)
            .m_171488_(-2.0F, -4.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(-5.0F, 0.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 33)
            .m_171488_(-4.0F, 7.0F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 14)
            .m_171488_(-4.0F, 7.0F, 3.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.04F))
            .m_171514_(36, 40)
            .m_171488_(-4.0F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.03F))
            .m_171514_(42, 14)
            .m_171488_(-4.0F, 10.0F, 3.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.05F))
            .m_171514_(32, 32)
            .m_171488_(-5.0F, -5.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 52)
            .m_171488_(-5.0F, -5.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(36, 9)
            .m_171488_(-5.0F, -5.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 41)
            .m_171488_(-5.0F, -6.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 41)
            .m_171488_(-5.0F, -6.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 37)
            .m_171488_(-5.0F, 1.0F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 54)
            .m_171488_(-5.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 54)
            .m_171488_(-4.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 43)
            .m_171488_(-4.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 54)
            .m_171488_(-3.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 54)
            .m_171488_(-3.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 44)
            .m_171488_(-2.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(20, 44)
            .m_171488_(-2.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 8)
            .m_171488_(-1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 43)
            .m_171488_(-3.0F, 0.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 45)
            .m_171488_(-5.0F, 0.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 50)
            .m_171488_(-4.0F, 7.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.03F))
            .m_171514_(15, 45)
            .m_171488_(-4.0F, 10.0F, -2.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.05F))
            .m_171514_(58, 17)
            .m_171488_(-4.5F, 8.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r2 = RightArm.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(58, 17).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.0F, -2.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171555_(false)
            .m_171514_(56, 21)
            .m_171488_(-1.0F, -4.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(56, 21)
            .m_171488_(-10.0F, -4.0F, -3.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(44, 42)
            .m_171488_(-1.0F, -4.0F, 2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(16, 32)
            .m_171488_(0.0F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 32)
            .m_171488_(1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 40)
            .m_171488_(0.0F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 23)
            .m_171488_(0.0F, -4.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 23)
            .m_171488_(0.0F, -4.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 32)
            .m_171488_(2.0F, 0.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 32)
            .m_171488_(2.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 32)
            .m_171488_(2.0F, -5.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 32)
            .m_171488_(2.0F, -5.0F, 2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 41)
            .m_171488_(4.0F, -5.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 41)
            .m_171488_(4.0F, -6.0F, -3.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 41)
            .m_171488_(4.0F, -6.0F, 1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 36)
            .m_171488_(3.0F, 1.0F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 53)
            .m_171488_(4.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 53)
            .m_171488_(3.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 54)
            .m_171488_(3.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 54)
            .m_171488_(2.0F, -4.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 54)
            .m_171488_(2.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 56)
            .m_171488_(1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 56)
            .m_171488_(1.0F, 8.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(16, 8)
            .m_171488_(0.0F, -3.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 37)
            .m_171488_(3.0F, 0.0F, -2.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(57, 13)
            .m_171488_(3.5F, 8.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
            .m_171514_(42, 8)
            .m_171488_(0.0F, 8.0F, -3.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.05F))
            .m_171514_(13, 34)
            .m_171488_(1.0F, 10.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.04F))
            .m_171514_(36, 34)
            .m_171488_(1.0F, 7.0F, -3.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
            .m_171514_(35, 9)
            .m_171488_(4.0F, 10.0F, -2.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.04F))
            .m_171514_(42, 14)
            .m_171488_(0.0F, 10.0F, 3.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.05F))
            .m_171514_(41, 9)
            .m_171488_(0.0F, 8.0F, 3.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 14)
            .m_171488_(1.0F, 7.0F, 3.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
            .m_171514_(36, 9)
            .m_171488_(4.0F, 7.0F, -2.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.04F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r3 = LeftArm.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(57, 13).m_171488_(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, -2.0F, 0.0F, 0.7854F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition RightLegThighGuard_r1 = RightLeg.m_171599_(
         "RightLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(33, 53).m_171488_(-2.5F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.7F)),
         PartPose.m_171423_(-1.0F, 3.5F, 0.0F, 0.0F, 0.0F, 0.3578F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_().m_171514_(0, 16).m_171480_().m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)).m_171555_(false),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLegThighGuard_r1 = LeftLeg.m_171599_(
         "LeftLegThighGuard_r1",
         CubeListBuilder.m_171558_().m_171514_(50, 48).m_171480_().m_171488_(-0.5F, -3.5F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.7F)).m_171555_(false),
         PartPose.m_171423_(1.2F, 3.5F, 0.0F, 0.0F, 0.0F, -0.3578F)
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
