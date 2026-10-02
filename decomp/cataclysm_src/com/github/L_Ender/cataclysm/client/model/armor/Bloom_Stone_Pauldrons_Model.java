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
public class Bloom_Stone_Pauldrons_Model extends HumanoidModel {
   public Bloom_Stone_Pauldrons_Model(ModelPart p_170677_) {
      super(p_170677_);
   }

   public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = partdefinition.m_171597_("body");
      PartDefinition rightArm = partdefinition.m_171597_("right_arm");
      PartDefinition leftArm = partdefinition.m_171597_("left_arm");
      rightArm.m_171599_(
         "RightShoulder",
         CubeListBuilder.m_171558_().m_171514_(52, 76).m_171488_(-5.5F, 0.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(0.75F, -2.5F, 0.0F, 0.0F, 0.0F, -0.0873F)
      );
      PartDefinition LeftShoulder = leftArm.m_171599_(
         "LeftShoulder",
         CubeListBuilder.m_171558_()
            .m_171514_(65, 25)
            .m_171488_(0.5F, 0.5F, -3.5F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.5F))
            .m_171514_(28, 65)
            .m_171488_(6.5F, 0.5F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(63, 87)
            .m_171488_(1.5F, -4.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -3.5F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      LeftShoulder.m_171599_(
         "Amethyst",
         CubeListBuilder.m_171558_().m_171514_(22, 79).m_171488_(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3054F)
      );
      LeftShoulder.m_171599_(
         "Amethyst2",
         CubeListBuilder.m_171558_()
            .m_171514_(12, 83)
            .m_171488_(-1.1F, 0.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.5F))
            .m_171514_(0, 65)
            .m_171488_(1.3F, 0.7F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(4.3F, 5.0F, 0.0F, 0.0F, 0.0F, -0.1745F)
      );
      PartDefinition Chest = body.m_171599_(
         "Chest",
         CubeListBuilder.m_171558_()
            .m_171514_(65, 38)
            .m_171488_(-5.0F, -3.0F, -2.0F, 10.0F, 6.0F, 4.0F, new CubeDeformation(0.5F))
            .m_171514_(60, 60)
            .m_171488_(-6.0F, -4.0F, -2.0F, 12.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(73, 76)
            .m_171488_(-2.0F, -3.0F, 2.5F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 79)
            .m_171488_(-2.0F, -3.0F, 2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(48, 87)
            .m_171488_(-1.5F, 2.0F, 2.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 2.0F, 4.0F, 0.1745F, 0.0F, 0.0F)
      );
      Chest.m_171599_(
         "Chest_lush",
         CubeListBuilder.m_171558_().m_171514_(31, 76).m_171488_(-3.0F, 0.0F, -4.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0F, -3.8F, -6.4F, 0.1309F, 0.0F, 0.0F)
      );
      Chest.m_171599_(
         "Chest2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 65)
            .m_171488_(-5.0F, 0.5F, -8.0F, 10.0F, 6.0F, 7.0F, new CubeDeformation(0.6F))
            .m_171514_(65, 49)
            .m_171488_(-5.0F, 7.1F, -8.0F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(65, 0)
            .m_171488_(-4.0F, -3.9F, -6.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.6F)),
         PartPose.m_171423_(0.0F, -3.9F, -1.4F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition Waist = body.m_171599_(
         "Waist",
         CubeListBuilder.m_171558_().m_171514_(35, 65).m_171488_(-4.0F, -0.3F, 0.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(0.0F, 5.0F, 2.3F, -0.1745F, 0.0F, 0.0F)
      );
      Waist.m_171599_(
         "Waist_Lush",
         CubeListBuilder.m_171558_().m_171514_(65, 13).m_171488_(-5.0F, 0.0F, 0.0F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }
}
