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

public class ModelFallenTopHalf<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cisco_mod", "model_fallen_top_half"), "main");
   public final ModelPart Head;
   public final ModelPart Body;
   public final ModelPart RightArm;
   public final ModelPart LeftArm;
   public final ModelPart RightLeg;
   public final ModelPart LeftLeg;

   public ModelFallenTopHalf(ModelPart root) {
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
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
            .m_171514_(32, 0)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.5F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -12.0F, 5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, -12.0F, -6.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-6.0F, -12.0F, 5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-6.0F, -12.0F, -5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.0F, -12.0F, -5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(4.0F, -12.0F, 5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = Head.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(57, 28).m_171488_(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.0F, -5.5F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition cube_r2 = Head.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 1)
            .m_171488_(-2.0F, -12.0F, -6.0F, 3.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-2.0F, -12.0F, 5.0F, 3.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.0F, -12.0F, -6.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.0F, -12.0F, 5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-6.0F, -12.0F, -6.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-6.0F, -12.0F, 5.0F, 2.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 1.6144F, 0.0F)
      );
      PartDefinition Body = partdefinition.m_171599_(
         "Body",
         CubeListBuilder.m_171558_()
            .m_171514_(16, 16)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F))
            .m_171514_(23, 59)
            .m_171488_(-5.0F, 11.0F, -3.6F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 46)
            .m_171488_(-5.0F, 11.0F, 1.4F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 49)
            .m_171488_(-2.5F, 3.8F, -3.5F, 1.2F, 3.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 48)
            .m_171488_(-1.1F, 1.3F, -3.5F, 2.4F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 40)
            .m_171488_(-3.7F, 0.2F, -3.5F, 7.7F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 61)
            .m_171488_(-3.7F, 9.8F, -3.5F, 7.7F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 42)
            .m_171488_(-1.4F, 8.4F, -3.5F, 2.7F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 38)
            .m_171488_(2.6F, -0.9F, -3.5F, 3.7F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 57)
            .m_171488_(-6.4F, -0.9F, -3.5F, 3.7F, 1.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(59, 53)
            .m_171488_(-5.0F, 0.3F, -3.3F, 1.2F, 3.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(53, 48)
            .m_171488_(3.8F, 0.3F, -3.3F, 1.2F, 3.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(51, 55)
            .m_171488_(3.8F, 7.3F, -3.3F, 1.2F, 3.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 39)
            .m_171488_(-5.0F, 7.3F, -3.3F, 1.2F, 3.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 56)
            .m_171488_(1.4F, 3.8F, -3.5F, 1.2F, 3.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(50, 49)
            .m_171488_(2.6F, 2.5F, -3.6F, 1.1F, 2.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 49)
            .m_171488_(-3.6F, 2.5F, -3.5F, 1.1F, 2.2F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(5, 48)
            .m_171488_(2.6F, 6.1F, -3.5F, 1.1F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 49)
            .m_171488_(2.6F, 2.3F, -3.5F, 1.1F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 37)
            .m_171488_(-3.7F, 6.1F, -3.5F, 1.1F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 47)
            .m_171488_(-3.8F, 2.5F, -3.5F, 1.3F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.3F, 1.2F, 2.4F, 2.4F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(1.1F, 0.2F, 2.4F, 1.3F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-2.6F, 0.2F, 2.4F, 1.3F, 2.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.2F, 9.5F, 2.4F, 2.3F, 1.4F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(1.3F, 3.8F, 2.4F, 1.1F, 5.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-2.5F, 3.8F, 2.4F, 1.1F, 5.5F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-3.7F, -0.8F, 2.4F, 1.3F, 11.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(2.3F, -0.8F, 2.4F, 1.3F, 11.7F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.5F, -1.0F, 2.3F, 1.3F, 4.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-5.1F, -1.0F, 2.3F, 1.3F, 4.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-5.1F, 7.0F, 2.3F, 1.3F, 4.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(3.9F, 7.0F, 2.3F, 1.3F, 4.3F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(-4, -4)
            .m_171488_(-5.5F, 11.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(-4, -4)
            .m_171488_(4.5F, 11.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 36)
            .m_171488_(1.2F, 13.0F, -3.7F, 1.0F, 6.0F, 7.2F, new CubeDeformation(0.0F))
            .m_171514_(6, 35)
            .m_171488_(-2.2F, 13.0F, -3.7F, 1.0F, 6.0F, 7.2F, new CubeDeformation(0.0F))
            .m_171514_(16, 46)
            .m_171488_(-1.4F, 13.0F, -3.3F, 2.8F, 7.0F, 6.6F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r3 = Body.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(5, 38).m_171488_(-0.85F, -0.85F, -3.75F, 1.5F, 1.5F, 7.1F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.05F, 20.35F, 0.25F, 0.0F, 0.0F, 0.7418F)
      );
      PartDefinition cube_r4 = Body.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(6, 38).m_171488_(-0.5F, -1.0F, -3.75F, 1.0F, 2.0F, 7.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.1946F, 19.489F, 0.05F, 0.0F, 0.0F, 0.6545F)
      );
      PartDefinition cube_r5 = Body.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(5, 37).m_171488_(-0.5F, 0.0044F, -3.75F, 1.0F, 2.0F, 7.2F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.8033F, 18.6956F, 0.05F, 0.0F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r6 = Body.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-3.0F, 2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-2.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 12.0F, -3.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition RightArm = partdefinition.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
            .m_171514_(6, 35)
            .m_171488_(-6.0F, -5.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-5.0F, -3.0F, 3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-6.0F, -3.0F, -4.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 38)
            .m_171488_(-5.0F, -4.0F, -4.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(-6.0F, -4.0F, -3.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-5.0F, -4.6F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r7 = RightArm.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(24, 0)
            .m_171488_(3.0F, 2.0F, -5.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(-2, -2)
            .m_171488_(3.0F, 7.0F, -4.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition cube_r8 = RightArm.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 15)
            .m_171488_(4.0F, 6.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(4.5F, 7.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(4.0F, 2.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 3.098F, 0.0F)
      );
      PartDefinition cube_r9 = RightArm.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_().m_171514_(57, 31).m_171488_(6.0F, -2.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -2.2689F, -0.0436F, 3.1416F)
      );
      PartDefinition LeftArm = partdefinition.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(40, 16)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F))
            .m_171555_(false)
            .m_171514_(0, 15)
            .m_171488_(4.0F, -3.0F, -3.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(0.0F, -3.0F, 3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(1.0F, -3.0F, -4.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(6, 35)
            .m_171488_(5.0F, -5.0F, -4.0F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(2, 39)
            .m_171488_(1.0F, -4.0F, -4.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(-4, -4)
            .m_171488_(3.0F, -4.6F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(5.0F, 2.0F, 0.0F)
      );
      PartDefinition cube_r10 = LeftArm.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(-3, -3)
            .m_171488_(3.0F, 2.0F, 10.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(-2, -2)
            .m_171488_(3.0F, 7.0F, 10.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-10.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition cube_r11 = LeftArm.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-15.5F, 7.0F, -0.3F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-14.0F, 6.0F, -2.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(-1, -1)
            .m_171488_(-15.0F, 6.0F, -2.1F, 1.0F, 4.0F, 5.6F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-15.0F, 2.0F, -2.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-10.0F, 0.0F, 0.0F, 0.0F, 3.098F, 0.0F)
      );
      PartDefinition cube_r12 = LeftArm.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_().m_171514_(57, 35).m_171488_(-17.0F, -3.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-10.0F, 0.0F, 0.0F, -2.2689F, -0.0436F, 3.1416F)
      );
      PartDefinition RightLeg = partdefinition.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 1)
            .m_171488_(-1.2F, 8.0F, -3.2F, 2.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.2F, 8.0F, 1.95F, 2.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-2.4F, 7.0F, -3.1F, 1.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-3.5F, 8.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(0.7F, 7.0F, -3.1F, 1.7F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(24, 33)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftLeg = partdefinition.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(35, 48)
            .m_171480_()
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
            .m_171555_(false)
            .m_171514_(1, 1)
            .m_171488_(-1.0F, 8.0F, -3.2F, 2.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-1.0F, 8.0F, 1.95F, 2.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(1.0F, 7.0F, -3.1F, 1.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 1)
            .m_171488_(-2.1F, 7.0F, -3.1F, 1.7F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(2.5F, 8.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
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
