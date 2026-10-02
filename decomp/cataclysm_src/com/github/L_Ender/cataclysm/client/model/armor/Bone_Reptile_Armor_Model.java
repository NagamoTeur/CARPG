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
public class Bone_Reptile_Armor_Model extends HumanoidModel {
   public Bone_Reptile_Armor_Model(ModelPart p_170677_) {
      super(p_170677_);
   }

   public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition head = partdefinition.m_171597_("head");
      PartDefinition body = partdefinition.m_171597_("body");
      PartDefinition rightArm = partdefinition.m_171597_("right_arm");
      PartDefinition leftArm = partdefinition.m_171597_("left_arm");
      head.m_171599_(
         "Helmet_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-4.0F, -1.5F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(2.0F, -6.4224F, -5.7783F, 0.0873F, 0.0F, 0.0F)
      );
      head.m_171599_(
         "Helmet_r2",
         CubeListBuilder.m_171558_().m_171514_(20, 36).m_171480_().m_171488_(-3.9F, 1.0F, -6.2F, 3.0F, 4.0F, 5.0F, new CubeDeformation(1.0F)).m_171555_(false),
         PartPose.m_171423_(4.9F, -5.0F, 0.2F, 0.2618F, -0.3054F, 0.0F)
      );
      head.m_171599_(
         "Helmet_r3",
         CubeListBuilder.m_171558_().m_171514_(20, 36).m_171488_(0.9F, 1.0F, -6.2F, 3.0F, 4.0F, 5.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(-4.9F, -5.0F, 0.2F, 0.2618F, 0.3054F, 0.0F)
      );
      head.m_171599_(
         "Helmet_r4",
         CubeListBuilder.m_171558_().m_171514_(0, 45).m_171488_(-4.5F, -2.4F, -4.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(1.0F)),
         PartPose.m_171423_(0.0F, -7.0F, 0.0F, 0.3054F, 0.0F, 0.0F)
      );
      head.m_171599_(
         "left_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 39)
            .m_171480_()
            .m_171488_(-3.0F, -3.3F, -2.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(1.0F))
            .m_171555_(false)
            .m_171514_(8, 40)
            .m_171480_()
            .m_171488_(-3.0F, -3.3F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(1.0F))
            .m_171555_(false),
         PartPose.m_171419_(-6.0F, -8.0F, 5.0F)
      );
      head.m_171599_(
         "right_horn",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 39)
            .m_171488_(0.0F, -3.3F, -2.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(1.0F))
            .m_171514_(8, 40)
            .m_171488_(0.0F, -3.3F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(1.0F)),
         PartPose.m_171419_(6.0F, -8.0F, 5.0F)
      );
      head.m_171599_(
         "mid_horn",
         CubeListBuilder.m_171558_().m_171514_(53, 111).m_171488_(-2.5F, -1.7F, -6.0F, 5.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -9.8F, 4.4F, 0.5236F, 0.0F, 0.0F)
      );
      body.m_171599_(
         "body_bone",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 108)
            .m_171488_(-6.0F, 0.0F, -1.85F, 10.0F, 14.0F, 6.0F, new CubeDeformation(0.45F))
            .m_171514_(0, 87)
            .m_171488_(-2.0F, 0.0F, 5.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.5F))
            .m_171514_(32, 92)
            .m_171488_(-1.0F, 0.0F, 6.5F, 0.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.0F, -1.0F, -1.0F)
      );
      rightArm.m_171599_(
         "right_shoulder",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 95)
            .m_171488_(-8.0F, -4.0F, -4.0F, 9.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 78)
            .m_171488_(-8.0F, -6.0F, -4.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.5F)
      );
      rightArm.m_171599_(
         "right_fist",
         CubeListBuilder.m_171558_().m_171514_(44, 105).m_171488_(-5.5F, -2.0F, -3.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.0F, 6.0F, -0.5F)
      );
      leftArm.m_171599_(
         "left_shoulder",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 95)
            .m_171480_()
            .m_171488_(-1.0F, -4.0F, -4.0F, 9.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 78)
            .m_171480_()
            .m_171488_(2.0F, -6.0F, -4.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 0.0F, 0.5F)
      );
      leftArm.m_171599_(
         "left_fist",
         CubeListBuilder.m_171558_().m_171514_(44, 105).m_171480_().m_171488_(2.5F, -2.0F, -3.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(-3.0F, 6.0F, -0.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }
}
