package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Prowler_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.The_Prowler_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class The_Prowler_Model extends HierarchicalModel<The_Prowler_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart upperbody;
   private final ModelPart chestplate;
   private final ModelPart eye_blow;
   private final ModelPart chestplate2;
   private final ModelPart rocket_luncher;
   private final ModelPart missile;
   private final ModelPart missile2;
   private final ModelPart missile3;
   private final ModelPart right_arm;
   private final ModelPart right_arm_joint;
   private final ModelPart sholder_pad;
   private final ModelPart sholder_pad2;
   private final ModelPart right_arm2;
   private final ModelPart right_joint;
   private final ModelPart chainsaw;
   private final ModelPart saw;
   private final ModelPart blade5;
   private final ModelPart blade6;
   private final ModelPart blade7;
   private final ModelPart blade8;
   private final ModelPart blade;
   private final ModelPart blade2;
   private final ModelPart blade3;
   private final ModelPart blade4;
   private final ModelPart pelvis;
   private final ModelPart catapiller;
   private final ModelPart catapiller2;
   private final ModelPart pipe2;
   private final ModelPart pipe;

   public The_Prowler_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.upperbody = this.roots.m_171324_("upperbody");
      this.chestplate = this.upperbody.m_171324_("chestplate");
      this.eye_blow = this.chestplate.m_171324_("eye_blow");
      this.chestplate2 = this.chestplate.m_171324_("chestplate2");
      this.rocket_luncher = this.upperbody.m_171324_("rocket_luncher");
      this.missile = this.rocket_luncher.m_171324_("missile");
      this.missile2 = this.rocket_luncher.m_171324_("missile2");
      this.missile3 = this.rocket_luncher.m_171324_("missile3");
      this.right_arm = this.upperbody.m_171324_("right_arm");
      this.right_arm_joint = this.right_arm.m_171324_("right_arm_joint");
      this.sholder_pad = this.right_arm_joint.m_171324_("sholder_pad");
      this.sholder_pad2 = this.sholder_pad.m_171324_("sholder_pad2");
      this.right_arm2 = this.right_arm_joint.m_171324_("right_arm2");
      this.right_joint = this.right_arm2.m_171324_("right_joint");
      this.chainsaw = this.right_joint.m_171324_("chainsaw");
      this.saw = this.chainsaw.m_171324_("saw");
      this.blade5 = this.saw.m_171324_("blade5");
      this.blade6 = this.saw.m_171324_("blade6");
      this.blade7 = this.saw.m_171324_("blade7");
      this.blade8 = this.saw.m_171324_("blade8");
      this.blade = this.saw.m_171324_("blade");
      this.blade2 = this.saw.m_171324_("blade2");
      this.blade3 = this.saw.m_171324_("blade3");
      this.blade4 = this.saw.m_171324_("blade4");
      this.pelvis = this.roots.m_171324_("pelvis");
      this.catapiller = this.pelvis.m_171324_("catapiller");
      this.catapiller2 = this.pelvis.m_171324_("catapiller2");
      this.pipe2 = this.pelvis.m_171324_("pipe2");
      this.pipe = this.pelvis.m_171324_("pipe");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition upperbody = roots.m_171599_("upperbody", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -23.5F, 0.0F));
      PartDefinition chestplate = upperbody.m_171599_(
         "chestplate",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-7.0F, -13.0F, -3.0F, 14.0F, 13.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.5F, -7.0F)
      );
      PartDefinition eye_blow = chestplate.m_171599_(
         "eye_blow",
         CubeListBuilder.m_171558_().m_171514_(2, 172).m_171488_(-4.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -9.0F, -3.25F)
      );
      PartDefinition chestplate2 = chestplate.m_171599_(
         "chestplate2",
         CubeListBuilder.m_171558_().m_171514_(114, 110).m_171488_(-10.0F, -40.0F, 0.0F, 6.0F, 13.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-3.0F, 25.0F, 0.0F)
      );
      PartDefinition rocket_luncher = upperbody.m_171599_(
         "rocket_luncher",
         CubeListBuilder.m_171558_()
            .m_171514_(48, 45)
            .m_171488_(6.0F, -17.0F, -11.0F, 6.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 61)
            .m_171488_(6.0F, -17.0F, -12.0F, 6.0F, 13.0F, 17.0F, new CubeDeformation(0.3F))
            .m_171514_(37, 41)
            .m_171488_(0.0F, -2.0F, 5.0F, 10.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(9.0F, -13.0F, 5.0F, 0.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 50)
            .m_171488_(8.0F, -3.0F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 47)
            .m_171488_(2.0F, -3.0F, 7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 44)
            .m_171488_(8.0F, -13.0F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 118)
            .m_171488_(0.0F, -8.0F, -8.0F, 6.0F, 13.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(7.0F, -6.5F, 1.0F)
      );
      PartDefinition missile = rocket_luncher.m_171599_(
         "missile",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 2)
            .m_171488_(-1.0F, -1.0F, -5.9F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 30)
            .m_171488_(0.0F, -3.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.0F, 1.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 41)
            .m_171488_(-1.0F, -1.0F, -3.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(7, 16)
            .m_171488_(-1.0F, -1.0F, 2.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(9.0F, -14.0F, -7.6F)
      );
      PartDefinition missile2 = rocket_luncher.m_171599_(
         "missile2",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 2)
            .m_171488_(-1.0F, -1.0F, -5.9F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 30)
            .m_171488_(0.0F, -3.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.0F, 1.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 41)
            .m_171488_(-1.0F, -1.0F, -3.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(7, 16)
            .m_171488_(-1.0F, -1.0F, 2.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(9.0F, -7.0F, -7.6F)
      );
      PartDefinition missile3 = rocket_luncher.m_171599_(
         "missile3",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 2)
            .m_171488_(-1.0F, -1.0F, -5.9F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 30)
            .m_171488_(0.0F, -3.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 2)
            .m_171488_(0.0F, 1.0F, -0.9F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(14, 41)
            .m_171488_(-1.0F, -1.0F, -3.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(7, 16)
            .m_171488_(-1.0F, -1.0F, 2.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)),
         PartPose.m_171419_(9.0F, 0.0F, -7.6F)
      );
      PartDefinition right_arm = upperbody.m_171599_("right_arm", CubeListBuilder.m_171558_(), PartPose.m_171419_(-13.0F, -13.5F, 1.0F));
      PartDefinition right_arm_joint = right_arm.m_171599_(
         "right_arm_joint",
         CubeListBuilder.m_171558_()
            .m_171514_(67, 125)
            .m_171488_(-11.0F, -6.0F, -5.0F, 11.0F, 16.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 48)
            .m_171488_(-5.0F, 5.0F, -7.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-5.0F, 5.0F, 5.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition sholder_pad = right_arm_joint.m_171599_(
         "sholder_pad",
         CubeListBuilder.m_171558_().m_171514_(0, 151).m_171488_(-4.0F, -2.5F, -6.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-9.0F, 4.5F, 0.0F, 0.0F, 0.0F, 0.3054F)
      );
      PartDefinition sholder_pad2 = sholder_pad.m_171599_(
         "sholder_pad2",
         CubeListBuilder.m_171558_().m_171514_(0, 151).m_171488_(-4.0F, -2.5F, -6.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.0F, 5.0F, 0.0F)
      );
      PartDefinition right_arm2 = right_arm_joint.m_171599_(
         "right_arm2",
         CubeListBuilder.m_171558_()
            .m_171514_(96, 63)
            .m_171488_(-3.0F, -8.0F, -3.0F, 6.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 91)
            .m_171488_(-3.0F, -8.0F, 1.0F, 6.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, 7.0F, 0.0F)
      );
      PartDefinition right_joint = right_arm2.m_171599_(
         "right_joint",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 41)
            .m_171488_(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 70)
            .m_171488_(-4.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(62, 104)
            .m_171488_(-2.0F, -3.0F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.3F))
            .m_171514_(49, 0)
            .m_171488_(3.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 11.0F, 0.0F)
      );
      PartDefinition chainsaw = right_joint.m_171599_(
         "chainsaw",
         CubeListBuilder.m_171558_()
            .m_171514_(93, 78)
            .m_171488_(-3.0F, -3.0F, -21.0F, 2.0F, 6.0F, 21.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 91)
            .m_171488_(1.0F, -3.0F, -21.0F, 2.0F, 6.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -3.0F)
      );
      PartDefinition saw = chainsaw.m_171599_(
         "saw",
         CubeListBuilder.m_171558_()
            .m_171514_(74, 63)
            .m_171488_(-1.0714F, -9.0F, -9.0F, 2.0F, 18.0F, 18.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 25)
            .m_171488_(-0.0714F, -16.0F, -4.5F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-0.0714F, 9.0F, -4.5F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 54)
            .m_171488_(-0.0714F, -4.5F, -16.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 4)
            .m_171488_(-0.0714F, -4.5F, 9.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 41)
            .m_171488_(-4.0714F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 36)
            .m_171488_(1.9286F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0714F, 0.0F, -18.0F)
      );
      PartDefinition blade5 = saw.m_171599_(
         "blade5",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-0.8214F, 8.0F, -9.0F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition blade6 = saw.m_171599_(
         "blade6",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(0.6786F, 8.0F, 0.0F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 1.0F, 0.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition blade7 = saw.m_171599_(
         "blade7",
         CubeListBuilder.m_171558_().m_171514_(49, 4).m_171488_(-0.8214F, 0.0F, 8.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition blade8 = saw.m_171599_(
         "blade8",
         CubeListBuilder.m_171558_().m_171514_(49, 4).m_171488_(0.6786F, -9.0F, 8.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 1.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition blade = saw.m_171599_(
         "blade",
         CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-0.8214F, -15.0F, 0.0F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition blade2 = saw.m_171599_(
         "blade2",
         CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(0.6786F, -15.0F, -9.0F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, 0.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition blade3 = saw.m_171599_(
         "blade3",
         CubeListBuilder.m_171558_().m_171514_(0, 54).m_171488_(-0.8214F, -9.0F, -15.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition blade4 = saw.m_171599_(
         "blade4",
         CubeListBuilder.m_171558_().m_171514_(0, 54).m_171488_(0.6786F, 0.0F, -15.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -1.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition pelvis = roots.m_171599_(
         "pelvis",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 34)
            .m_171488_(-6.0F, 12.0F, -11.0F, 12.0F, 7.0F, 20.0F, new CubeDeformation(0.0F))
            .m_171514_(34, 138)
            .m_171488_(-3.0F, -4.0F, -4.0F, 6.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(49, 0)
            .m_171488_(-4.0F, 7.0F, -5.0F, 8.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(98, 0)
            .m_171488_(-5.0F, -1.0F, -7.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(66, 81)
            .m_171488_(3.0F, -1.0F, -7.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(37, 61)
            .m_171488_(2.0F, -1.0F, 4.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 61)
            .m_171488_(-4.0F, -1.0F, 4.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(54, 18)
            .m_171488_(-6.0F, 2.0F, -9.0F, 12.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -23.0F, 1.0F)
      );
      PartDefinition catapiller = pelvis.m_171599_(
         "catapiller",
         CubeListBuilder.m_171558_()
            .m_171514_(31, 104)
            .m_171488_(-9.0F, -4.0F, -14.5F, 8.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(92, 41)
            .m_171488_(-10.0F, -5.0F, -15.5F, 10.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
            .m_171514_(129, 35)
            .m_171488_(-9.0F, 0.0F, 0.5F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(118, 63)
            .m_171488_(-10.0F, -1.0F, -1.5F, 10.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-6.0F, 15.0F, -0.5F)
      );
      PartDefinition catapiller2 = pelvis.m_171599_(
         "catapiller2",
         CubeListBuilder.m_171558_()
            .m_171514_(94, 0)
            .m_171488_(1.0F, -4.0F, -14.5F, 8.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 82)
            .m_171488_(0.0F, -5.0F, -15.5F, 10.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
            .m_171514_(77, 105)
            .m_171488_(0.0F, -1.0F, -2.5F, 10.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(128, 15)
            .m_171488_(1.0F, 0.0F, 0.5F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(6.0F, 15.0F, -0.5F)
      );
      PartDefinition pipe2 = pelvis.m_171599_(
         "pipe2",
         CubeListBuilder.m_171558_()
            .m_171514_(56, 48)
            .m_171488_(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(8, 2)
            .m_171488_(-1.0F, -3.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 16)
            .m_171488_(-1.0F, -3.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.3F)),
         PartPose.m_171423_(5.0F, 11.0F, 8.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition pipe = pelvis.m_171599_(
         "pipe",
         CubeListBuilder.m_171558_()
            .m_171514_(75, 0)
            .m_171488_(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(63, 11)
            .m_171488_(-1.0F, -3.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 16)
            .m_171488_(-1.0F, -3.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.3F)),
         PartPose.m_171423_(-5.0F, 11.0F, 8.0F, -0.1745F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(The_Prowler_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.upperbody.f_104204_ += netHeadYaw * 0.6F * (float) (Math.PI / 180.0);
      float sawspeed = entity.getAttackState() == 3 ? 0.0F : 0.5F;
      this.m_233385_(entity.getAnimationState("death"), Prowler_Animation.DEATH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("idle"), Prowler_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("spin"), Prowler_Animation.SPIN, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("melee"), Prowler_Animation.MELEE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("strong_attack"), Prowler_Animation.STRONG_ATTACK, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("stun"), Prowler_Animation.STUN, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("laser"), Prowler_Animation.LASER, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("pierce"), Prowler_Animation.PIERCE, ageInTicks, 1.0F);
      this.saw.f_104203_ -= ageInTicks * sawspeed;
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
