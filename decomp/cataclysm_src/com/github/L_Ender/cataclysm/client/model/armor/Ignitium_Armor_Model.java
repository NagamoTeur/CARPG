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
public class Ignitium_Armor_Model extends HumanoidModel {
   public Ignitium_Armor_Model(ModelPart p_170677_) {
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
         "right_helmet",
         CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.75F, 0.3F, -4.75F, 0.0F, -0.829F, 0.0F)
      );
      head.m_171599_(
         "left_helmet",
         CubeListBuilder.m_171558_().m_171514_(0, 35).m_171488_(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(4.75F, 0.3F, -4.75F, 0.0F, 0.829F, 0.0F)
      );
      head.m_171599_(
         "headplate",
         CubeListBuilder.m_171558_().m_171514_(48, 34).m_171488_(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.5F, -4.25F, -0.2618F, 0.0F, 0.0F)
      );
      PartDefinition right_horn = head.m_171599_(
         "right_horn",
         CubeListBuilder.m_171558_().m_171514_(54, 43).m_171488_(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-3.6F, -6.5F, -3.6F, 0.3927F, 0.2182F, -0.1309F)
      );
      PartDefinition right_horn2 = right_horn.m_171599_(
         "right_horn2",
         CubeListBuilder.m_171558_().m_171514_(13, 41).m_171488_(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(0.0F, -5.0F, -1.0F, -1.3526F, 0.0F, 0.0F)
      );
      right_horn2.m_171599_(
         "right_horn3",
         CubeListBuilder.m_171558_().m_171514_(53, 37).m_171488_(-0.5F, 0.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)).m_171555_(false),
         PartPose.m_171423_(0.0F, -7.0F, 2.0F, -0.5236F, 0.0F, 0.0F)
      );
      PartDefinition left_horn = head.m_171599_(
         "left_horn",
         CubeListBuilder.m_171558_().m_171514_(54, 43).m_171488_(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.6F, -6.5F, -3.6F, 0.3927F, -0.2182F, 0.1309F)
      );
      PartDefinition left_horn2 = left_horn.m_171599_(
         "left_horn2",
         CubeListBuilder.m_171558_().m_171514_(13, 41).m_171488_(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.0F, -1.0F, -1.3526F, 0.0F, 0.0F)
      );
      left_horn2.m_171599_(
         "left_horn3",
         CubeListBuilder.m_171558_().m_171514_(53, 37).m_171488_(-0.5F, 0.0F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)),
         PartPose.m_171423_(0.0F, -7.0F, 2.0F, -0.5236F, 0.0F, 0.0F)
      );
      body.m_171599_(
         "outer_body",
         CubeListBuilder.m_171558_().m_171514_(30, 47).m_171488_(-4.5F, 1.0F, -2.5F, 9.0F, 12.0F, 5.0F, new CubeDeformation(0.4F)),
         PartPose.m_171419_(0.0F, -1.0F, 0.0F)
      );
      body.m_171599_(
         "inner_body",
         CubeListBuilder.m_171558_().m_171514_(0, 51).m_171488_(-4.0F, -6.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.0F, 11.0F, 0.0F)
      );
      PartDefinition left_shoulderpad = leftArm.m_171599_(
         "left_shoulderpad",
         CubeListBuilder.m_171558_().m_171514_(30, 33).m_171488_(-6.0F, -7.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.3F)),
         PartPose.m_171419_(5.0F, 4.0F, 0.0F)
      );
      PartDefinition left_spike = left_shoulderpad.m_171599_(
         "left_spike",
         CubeListBuilder.m_171558_().m_171514_(21, 43).m_171488_(-1.0F, -3.5F, 0.0F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, -8.5F, 0.0F, 0.0F, 0.0F, 0.6109F)
      );
      left_spike.m_171599_(
         "left_side_spike",
         CubeListBuilder.m_171558_().m_171514_(30, 47).m_171488_(0.5F, -3.5F, -0.5F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, 3.0F, 0.5F, 0.0F, 0.0F, 0.829F)
      );
      PartDefinition right_shoulderpad = rightArm.m_171599_(
         "right_shoulderpad",
         CubeListBuilder.m_171558_().m_171514_(30, 33).m_171480_().m_171488_(0.0F, -7.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.3F)).m_171555_(false),
         PartPose.m_171419_(-4.0F, 4.0F, 0.0F)
      );
      PartDefinition right_spike = right_shoulderpad.m_171599_(
         "right_spike",
         CubeListBuilder.m_171558_().m_171514_(21, 43).m_171480_().m_171488_(-3.0F, -3.5F, 0.0F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(0.0F, -8.5F, 0.0F, 0.0F, 0.0F, -0.6109F)
      );
      right_spike.m_171599_(
         "right_side_spike",
         CubeListBuilder.m_171558_().m_171514_(30, 47).m_171480_().m_171488_(-2.5F, -3.5F, -0.5F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-2.5F, 3.0F, 0.5F, 0.0F, 0.0F, -0.829F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }
}
