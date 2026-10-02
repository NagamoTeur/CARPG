package com.github.L_Ender.cataclysm.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Cursium_Armor_Model extends HumanoidModel {
   public Cursium_Armor_Model(ModelPart p_170677_) {
      super(p_170677_);
   }

   public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171597_("head");
      PartDefinition body = partdefinition.m_171597_("body");
      PartDefinition rightArm = partdefinition.m_171597_("right_arm");
      PartDefinition leftArm = partdefinition.m_171597_("left_arm");
      PartDefinition rightLeg = partdefinition.m_171597_("right_leg");
      PartDefinition leftLeg = partdefinition.m_171597_("left_leg");
      PartDefinition RightCustomArm = rightArm.m_171599_(
         "RightCustomArm",
         CubeListBuilder.m_171558_()
            .m_171514_(22, 89)
            .m_171488_(-3.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.525F))
            .m_171514_(22, 105)
            .m_171488_(-3.6F, 3.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.6F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition right_shoulder = RightCustomArm.m_171599_("right_shoulder", CubeListBuilder.m_171558_(), PartPose.m_171419_(-2.5F, 2.0F, 0.0F));
      PartDefinition rib = body.m_171599_(
         "rib",
         CubeListBuilder.m_171558_().m_171514_(100, 0).m_171488_(-4.0F, -2.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.6F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition chestplate_r1 = right_shoulder.m_171599_(
         "chestplate_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 92).m_171488_(-2.5F, -6.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.55F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F)
      );
      PartDefinition LeftCustomArm = leftArm.m_171599_(
         "LeftCustomArm",
         CubeListBuilder.m_171558_()
            .m_171514_(22, 89)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.525F))
            .m_171555_(false)
            .m_171514_(22, 105)
            .m_171480_()
            .m_171488_(-0.4F, 3.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.6F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition left_shoulder = LeftCustomArm.m_171599_("left_shoulder", CubeListBuilder.m_171558_(), PartPose.m_171419_(2.5F, 2.0F, 0.0F));
      PartDefinition chestplate_r2 = left_shoulder.m_171599_(
         "chestplate_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 92)
            .m_171480_()
            .m_171488_(-2.5F, -6.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.55F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F)
      );
      PartDefinition right_leggings_plate = rightLeg.m_171599_(
         "right_leggings_plate",
         CubeListBuilder.m_171558_().m_171514_(62, 108).m_171488_(-5.8F, -12.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.8F)),
         PartPose.m_171423_(1.9F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0873F)
      );
      PartDefinition left_leggings_plate = leftLeg.m_171599_(
         "left_leggings_plate",
         CubeListBuilder.m_171558_()
            .m_171514_(62, 108)
            .m_171480_()
            .m_171488_(2.8F, -12.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.8F))
            .m_171555_(false),
         PartPose.m_171423_(-1.9F, 11.0F, 0.0F, 0.0F, 0.0F, -0.0873F)
      );
      PartDefinition right_horn = head.m_171599_("right_horn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-6.2F, -10.0F, 0.0F, -0.2618F, 0.0F, -0.6545F));
      PartDefinition cube_r1 = right_horn.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 103)
            .m_171488_(-5.811F, -19.4301F, 0.1321F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 118)
            .m_171488_(-9.811F, -17.4301F, 0.1321F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 115)
            .m_171488_(-4.811F, -17.4301F, -1.8679F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, 12.0F, 2.0F, 0.1309F, 0.0F, -0.3054F)
      );
      PartDefinition cube_r2 = right_horn.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(1, 110).m_171488_(-0.811F, -17.4301F, -1.3679F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0196F, 12.0622F, 1.5043F, 0.1309F, 0.0F, -0.3054F)
      );
      PartDefinition left_horn = head.m_171599_("left_horn", CubeListBuilder.m_171558_(), PartPose.m_171423_(6.2F, -10.0F, 0.0F, -0.2618F, 0.0F, 0.6545F));
      PartDefinition cube_r3 = left_horn.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 103)
            .m_171480_()
            .m_171488_(-6.189F, -19.4301F, 0.1321F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 118)
            .m_171480_()
            .m_171488_(4.811F, -17.4301F, 0.1321F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(18, 115)
            .m_171480_()
            .m_171488_(0.811F, -17.4301F, -1.8679F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, 12.0F, 2.0F, 0.1309F, 0.0F, 0.3054F)
      );
      PartDefinition cube_r4 = left_horn.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(1, 110)
            .m_171480_()
            .m_171488_(-6.189F, -17.4301F, -1.3679F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0196F, 12.0622F, 1.5043F, 0.1309F, 0.0F, 0.3054F)
      );
      PartDefinition right_plate = head.m_171599_(
         "right_plate",
         CubeListBuilder.m_171558_().m_171514_(35, 114).m_171488_(-6.5F, -5.5F, -5.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_plate = head.m_171599_(
         "left_plate",
         CubeListBuilder.m_171558_().m_171514_(35, 114).m_171480_().m_171488_(2.5F, -5.5F, -5.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }
}
