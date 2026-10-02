package net.mindoth.dreadsteel.client.models.armor;

import net.mindoth.shadowizardlib.client.models.ArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class DreadsteelModel extends ArmorModel {
   public DreadsteelModel(ModelPart part, EquipmentSlot slot) {
      super(part, slot);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition mesh = HumanoidModel.m_170681_(new CubeDeformation(0.0F), 0.0F);
      PartDefinition root = createHumanoidAlias(mesh);
      PartDefinition head = root.m_171597_("Head");
      PartDefinition body = root.m_171597_("Body");
      PartDefinition right_arm = root.m_171597_("RightArm");
      PartDefinition left_arm = root.m_171597_("LeftArm");
      PartDefinition right_leg = root.m_171597_("RightLeg");
      PartDefinition left_leg = root.m_171597_("LeftLeg");
      PartDefinition right_foot = root.m_171597_("RightBoot");
      PartDefinition left_foot = root.m_171597_("LeftBoot");
      PartDefinition head1 = head.m_171599_(
         "head1",
         CubeListBuilder.m_171558_()
            .m_171514_(2, 190)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.51F))
            .m_171514_(207, 71)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 190)
            .m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.85F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition HeardHornsR = head.m_171599_(
         "HeardHornsR",
         CubeListBuilder.m_171558_().m_171514_(212, 33).m_171488_(-3.0F, -5.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(4.1F, -5.5F, -2.1F, -0.4363F, 0.1536F, 0.6665F)
      );
      PartDefinition HeadHornR2_r1 = HeardHornsR.m_171599_(
         "HeadHornR2_r1",
         CubeListBuilder.m_171558_().m_171514_(214, 41).m_171488_(-2.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, -3.7F, -0.5F, -0.436F, 0.0184F, -0.0041F)
      );
      PartDefinition HeadHornR1_r1 = HeardHornsR.m_171599_(
         "HeadHornR1_r1",
         CubeListBuilder.m_171558_().m_171514_(216, 48).m_171488_(-1.0F, -3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -6.5F, 0.0F, -0.829F, 0.0F, 0.0F)
      );
      PartDefinition HeadHornsL = head.m_171599_(
         "HeadHornsL",
         CubeListBuilder.m_171558_().m_171514_(212, 33).m_171488_(-3.0F, -5.0F, -2.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.9F, -7.5F, -1.7F, -0.4363F, -0.1536F, -0.6665F)
      );
      PartDefinition HeadHornL2_r1 = HeadHornsL.m_171599_(
         "HeadHornL2_r1",
         CubeListBuilder.m_171558_().m_171514_(214, 41).m_171488_(-2.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, -3.7F, -0.5F, -0.436F, 0.0184F, -0.0041F)
      );
      PartDefinition HeadHornL1_r1 = HeadHornsL.m_171599_(
         "HeadHornL1_r1",
         CubeListBuilder.m_171558_().m_171514_(216, 48).m_171488_(-1.0F, -3.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -6.5F, 0.0F, -0.829F, 0.0F, 0.0F)
      );
      PartDefinition body1 = body.m_171599_(
         "body1",
         CubeListBuilder.m_171558_()
            .m_171514_(18, 206)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.49F))
            .m_171514_(155, 30)
            .m_171488_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.3F))
            .m_171514_(230, 134)
            .m_171488_(-4.0F, 1.0F, -3.0F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(233, 107)
            .m_171488_(-4.0F, 1.0F, 2.0F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(231, 125)
            .m_171488_(-3.0F, 7.0F, -2.8F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(236, 112)
            .m_171488_(-3.0F, 7.0F, 2.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(207, 65)
            .m_171488_(-4.5F, 10.6F, -2.9F, 9.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r1 = body.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(247, 84).m_171488_(-1.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.2F, 18.5F, -3.9F, -0.1309F, 0.0F, -0.3491F)
      );
      PartDefinition cube_r2 = body.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(247, 84).m_171488_(-1.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.3F, 18.8F, -3.9F, -0.1309F, 0.0F, 0.3491F)
      );
      PartDefinition cube_r3 = body.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(245, 70).m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.7F, 12.3F, -3.5F, -0.0928F, -0.0924F, -0.7811F)
      );
      PartDefinition Belt_r1 = body.m_171599_(
         "Belt_r1",
         CubeListBuilder.m_171558_().m_171514_(155, 46).m_171488_(-9.0F, -1.0F, -2.8F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(4.0F, 11.4F, 1.0F, 0.3054F, 0.0F, 0.0F)
      );
      PartDefinition RightArm = right_arm.m_171599_(
         "RightArm",
         CubeListBuilder.m_171558_()
            .m_171514_(42, 206)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F))
            .m_171514_(187, 53)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171514_(226, 51)
            .m_171488_(0.0F, -3.3F, -3.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(210, 51)
            .m_171488_(-1.0F, -3.3F, -3.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r4 = RightArm.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(209, 152).m_171488_(-16.0F, -0.6F, -2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, 4.8F, -13.9F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition RShoulderHorns3 = RightArm.m_171599_(
         "RShoulderHorns3",
         CubeListBuilder.m_171558_().m_171514_(213, 27).m_171488_(-3.5969F, -1.8727F, -1.2783F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.0F, -0.7F, 1.4F, 0.0F, 0.4363F, 0.6545F)
      );
      PartDefinition RShoulderHorns4 = RShoulderHorns3.m_171599_(
         "RShoulderHorns4",
         CubeListBuilder.m_171558_().m_171514_(214, 25).m_171488_(-3.5437F, -0.7226F, -0.2783F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, -0.2F, -0.5F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition RShoulderHorns1 = RightArm.m_171599_(
         "RShoulderHorns1",
         CubeListBuilder.m_171558_().m_171514_(213, 27).m_171488_(-3.3812F, -2.0554F, -0.8222F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.3F, -0.7F, -1.4F, 0.0F, -0.4363F, 0.6545F)
      );
      PartDefinition RShoulderHorns2 = RShoulderHorns1.m_171599_(
         "RShoulderHorns2",
         CubeListBuilder.m_171558_().m_171514_(214, 25).m_171488_(-3.3826F, -0.9548F, 0.1778F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, -0.2F, -0.5F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition LeftArm = left_arm.m_171599_(
         "LeftArm",
         CubeListBuilder.m_171558_()
            .m_171514_(2, 238)
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F))
            .m_171514_(187, 53)
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
            .m_171514_(210, 51)
            .m_171488_(0.1F, -3.3F, -3.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(226, 51)
            .m_171488_(-0.9F, -3.3F, -3.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition cube_r5 = LeftArm.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(209, 145).m_171488_(1.0F, -12.6F, 5.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(9.0F, 16.8F, -2.9F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition LShoulderHorns3 = LeftArm.m_171599_(
         "LShoulderHorns3",
         CubeListBuilder.m_171558_().m_171514_(213, 27).m_171488_(-3.5969F, -1.8728F, -1.2783F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0F, -0.7F, -1.4F, -3.1416F, -0.4363F, 2.4871F)
      );
      PartDefinition LShoulderHorns4 = LShoulderHorns3.m_171599_(
         "LShoulderHorns4",
         CubeListBuilder.m_171558_().m_171514_(214, 25).m_171488_(-3.5437F, -0.7226F, -0.2783F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, -0.2F, -0.5F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition LShoulderHorns1 = LeftArm.m_171599_(
         "LShoulderHorns1",
         CubeListBuilder.m_171558_().m_171514_(213, 27).m_171488_(-3.5969F, -1.8728F, -0.7217F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0F, -0.7F, 1.4F, 3.1416F, 0.4363F, 2.4871F)
      );
      PartDefinition LShoulderHorns2 = LShoulderHorns1.m_171599_(
         "LShoulderHorns2",
         CubeListBuilder.m_171558_().m_171514_(214, 25).m_171488_(-3.5437F, -0.7226F, 0.2783F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, -0.2F, -0.5F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition LeftLeg = left_leg.m_171599_(
         "LeftLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(18, 238)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
            .m_171514_(227, 147)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition RightLeg = right_leg.m_171599_(
         "RightLeg",
         CubeListBuilder.m_171558_()
            .m_171514_(235, 25)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
            .m_171514_(206, 159)
            .m_171488_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition CoatTailRight = LeftLeg.m_171599_(
         "CoatTailRight", CubeListBuilder.m_171558_(), PartPose.m_171423_(1.9F, 13.2F, -1.4F, 0.0F, -0.1309F, 0.0F)
      );
      PartDefinition RightCoatTailEx_r1 = CoatTailRight.m_171599_(
         "RightCoatTailEx_r1",
         CubeListBuilder.m_171558_().m_171514_(155, 53).m_171488_(-6.6172F, -12.4538F, 3.2715F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2607F, 0.0927F, 0.1602F)
      );
      PartDefinition RightCoatTail_r1 = CoatTailRight.m_171599_(
         "RightCoatTail_r1",
         CubeListBuilder.m_171558_().m_171514_(176, 54).m_171488_(-4.9044F, -12.4737F, 1.9621F, 4.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.3F, 4.8F, 0.2597F, -0.0338F, 0.1265F)
      );
      PartDefinition CoatTailLeft = RightLeg.m_171599_("CoatTailLeft", CubeListBuilder.m_171558_(), PartPose.m_171419_(-2.4F, 13.3F, -1.7F));
      PartDefinition LeftCoatTailEx_r1 = CoatTailLeft.m_171599_(
         "LeftCoatTailEx_r1",
         CubeListBuilder.m_171558_().m_171514_(155, 53).m_171488_(4.9268F, -12.4442F, 4.1603F, 1.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.8F, 0.1F, -0.3F, 0.2597F, 0.0338F, -0.1265F)
      );
      PartDefinition LeftCoatTail_r1 = CoatTailLeft.m_171599_(
         "LeftCoatTail_r1",
         CubeListBuilder.m_171558_().m_171514_(165, 54).m_171488_(0.9065F, -12.4442F, 7.1494F, 4.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.4F, 0.263F, 0.1603F, -0.0925F)
      );
      PartDefinition RightBoot = right_foot.m_171599_(
         "RightBoot",
         CubeListBuilder.m_171558_()
            .m_171514_(209, 152)
            .m_171488_(-4.0F, -6.2F, -2.9F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(58, 244)
            .m_171488_(-4.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(1.9F, 12.0F, 0.0F)
      );
      PartDefinition LeftBoot = left_foot.m_171599_(
         "LeftBoot",
         CubeListBuilder.m_171558_()
            .m_171514_(209, 145)
            .m_171488_(0.0F, -6.2F, -2.9F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(80, 244)
            .m_171488_(0.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(-1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(mesh, 256, 256);
   }

   public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
      modelRenderer.f_104203_ = x;
      modelRenderer.f_104204_ = y;
      modelRenderer.f_104205_ = z;
   }
}
