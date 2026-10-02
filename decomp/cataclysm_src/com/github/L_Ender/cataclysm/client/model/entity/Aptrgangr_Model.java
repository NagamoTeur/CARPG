package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Aptrgangr_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class Aptrgangr_Model extends HierarchicalModel<Aptrgangr_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart l_leg;
   private final ModelPart l_leg_armor;
   private final ModelPart left_leg_r1;
   private final ModelPart left_leg_r2;
   private final ModelPart r_leg;
   private final ModelPart r_leg_armor;
   private final ModelPart right_leg_r1;
   private final ModelPart right_leg_r2;
   private final ModelPart body;
   private final ModelPart chest;
   private final ModelPart neck;
   private final ModelPart head;
   private final ModelPart helmet;
   private final ModelPart head_r1;
   private final ModelPart head_r2;
   private final ModelPart head_r3;
   private final ModelPart head_r4;
   private final ModelPart head_r5;
   private final ModelPart head_r6;
   private final ModelPart jaw;
   private final ModelPart head_r7;
   private final ModelPart chestplate;
   private final ModelPart body_r1;
   private final ModelPart body_r2;
   private final ModelPart body_r3;
   private final ModelPart body_r4;
   private final ModelPart body_r5;
   private final ModelPart body_r6;
   private final ModelPart body_r7;
   private final ModelPart l_arm;
   private final ModelPart l_arm_armor;
   private final ModelPart right_arm_r1;
   private final ModelPart right_arm_r2;
   private final ModelPart right_arm_r3;
   private final ModelPart right_arm_r4;
   private final ModelPart right_arm_r5;
   private final ModelPart right_arm_r6;
   private final ModelPart arrow;
   private final ModelPart arrow2;
   private final ModelPart left_arm2;
   private final ModelPart l_arm_cloth;
   private final ModelPart hold;
   private final ModelPart r_arm;
   private final ModelPart right_arm2;
   private final ModelPart r_arm_cloth;
   private final ModelPart axe;
   private final ModelPart cube_r1;
   private final ModelPart cube_r2;
   private final ModelPart axe_head;
   private final ModelPart cube_r3;
   private final ModelPart cube_r4;
   private final ModelPart cube_r5;
   private final ModelPart cube_r6;
   private final ModelPart cube_r7;
   private final ModelPart cube_r8;
   private final ModelPart emblem3;
   private final ModelPart right_arm_r7;
   private final ModelPart emblem4;
   private final ModelPart r_arm_armor;
   private final ModelPart left_arm_r1;
   private final ModelPart left_arm_r2;
   private final ModelPart left_arm_r3;
   private final ModelPart left_arm_r4;
   private final ModelPart left_arm_r5;
   private final ModelPart left_arm_r6;
   private final ModelPart belt;
   private final ModelPart body_r8;
   private final ModelPart emblem2;
   private final ModelPart emblem;
   private final ModelPart cloth2;
   private final ModelPart cloth;

   public Aptrgangr_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.l_leg = this.roots.m_171324_("l_leg");
      this.l_leg_armor = this.l_leg.m_171324_("l_leg_armor");
      this.left_leg_r1 = this.l_leg_armor.m_171324_("left_leg_r1");
      this.left_leg_r2 = this.l_leg_armor.m_171324_("left_leg_r2");
      this.r_leg = this.roots.m_171324_("r_leg");
      this.r_leg_armor = this.r_leg.m_171324_("r_leg_armor");
      this.right_leg_r1 = this.r_leg_armor.m_171324_("right_leg_r1");
      this.right_leg_r2 = this.r_leg_armor.m_171324_("right_leg_r2");
      this.body = this.roots.m_171324_("body");
      this.chest = this.body.m_171324_("chest");
      this.neck = this.chest.m_171324_("neck");
      this.head = this.neck.m_171324_("head");
      this.helmet = this.head.m_171324_("helmet");
      this.head_r1 = this.helmet.m_171324_("head_r1");
      this.head_r2 = this.helmet.m_171324_("head_r2");
      this.head_r3 = this.helmet.m_171324_("head_r3");
      this.head_r4 = this.helmet.m_171324_("head_r4");
      this.head_r5 = this.helmet.m_171324_("head_r5");
      this.head_r6 = this.helmet.m_171324_("head_r6");
      this.jaw = this.head.m_171324_("jaw");
      this.head_r7 = this.jaw.m_171324_("head_r7");
      this.chestplate = this.chest.m_171324_("chestplate");
      this.body_r1 = this.chestplate.m_171324_("body_r1");
      this.body_r2 = this.chestplate.m_171324_("body_r2");
      this.body_r3 = this.chestplate.m_171324_("body_r3");
      this.body_r4 = this.chestplate.m_171324_("body_r4");
      this.body_r5 = this.chestplate.m_171324_("body_r5");
      this.body_r6 = this.chestplate.m_171324_("body_r6");
      this.body_r7 = this.chestplate.m_171324_("body_r7");
      this.l_arm = this.chest.m_171324_("l_arm");
      this.l_arm_armor = this.l_arm.m_171324_("l_arm_armor");
      this.right_arm_r1 = this.l_arm_armor.m_171324_("right_arm_r1");
      this.right_arm_r2 = this.l_arm_armor.m_171324_("right_arm_r2");
      this.right_arm_r3 = this.l_arm_armor.m_171324_("right_arm_r3");
      this.right_arm_r4 = this.l_arm_armor.m_171324_("right_arm_r4");
      this.right_arm_r5 = this.l_arm_armor.m_171324_("right_arm_r5");
      this.right_arm_r6 = this.l_arm_armor.m_171324_("right_arm_r6");
      this.arrow = this.l_arm_armor.m_171324_("arrow");
      this.arrow2 = this.l_arm_armor.m_171324_("arrow2");
      this.left_arm2 = this.l_arm.m_171324_("left_arm2");
      this.l_arm_cloth = this.left_arm2.m_171324_("l_arm_cloth");
      this.hold = this.l_arm_cloth.m_171324_("hold");
      this.r_arm = this.chest.m_171324_("r_arm");
      this.right_arm2 = this.r_arm.m_171324_("right_arm2");
      this.r_arm_cloth = this.right_arm2.m_171324_("r_arm_cloth");
      this.axe = this.right_arm2.m_171324_("axe");
      this.cube_r1 = this.axe.m_171324_("cube_r1");
      this.cube_r2 = this.axe.m_171324_("cube_r2");
      this.axe_head = this.axe.m_171324_("axe_head");
      this.cube_r3 = this.axe_head.m_171324_("cube_r3");
      this.cube_r4 = this.axe_head.m_171324_("cube_r4");
      this.cube_r5 = this.axe_head.m_171324_("cube_r5");
      this.cube_r6 = this.axe_head.m_171324_("cube_r6");
      this.cube_r7 = this.axe_head.m_171324_("cube_r7");
      this.cube_r8 = this.axe_head.m_171324_("cube_r8");
      this.emblem3 = this.axe_head.m_171324_("emblem3");
      this.right_arm_r7 = this.emblem3.m_171324_("right_arm_r7");
      this.emblem4 = this.axe_head.m_171324_("emblem4");
      this.r_arm_armor = this.r_arm.m_171324_("r_arm_armor");
      this.left_arm_r1 = this.r_arm_armor.m_171324_("left_arm_r1");
      this.left_arm_r2 = this.r_arm_armor.m_171324_("left_arm_r2");
      this.left_arm_r3 = this.r_arm_armor.m_171324_("left_arm_r3");
      this.left_arm_r4 = this.r_arm_armor.m_171324_("left_arm_r4");
      this.left_arm_r5 = this.r_arm_armor.m_171324_("left_arm_r5");
      this.left_arm_r6 = this.r_arm_armor.m_171324_("left_arm_r6");
      this.belt = this.body.m_171324_("belt");
      this.body_r8 = this.belt.m_171324_("body_r8");
      this.emblem2 = this.belt.m_171324_("emblem2");
      this.emblem = this.belt.m_171324_("emblem");
      this.cloth2 = this.belt.m_171324_("cloth2");
      this.cloth = this.belt.m_171324_("cloth");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition l_leg = roots.m_171599_(
         "l_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 69).m_171488_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(6.0F, -13.0F, 0.0F)
      );
      PartDefinition l_leg_armor = l_leg.m_171599_(
         "l_leg_armor",
         CubeListBuilder.m_171558_()
            .m_171514_(39, 91)
            .m_171488_(-3.5F, -1.0F, -3.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.4F))
            .m_171514_(0, 90)
            .m_171488_(-3.5F, 7.0F, -3.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.4F))
            .m_171514_(0, 109)
            .m_171488_(-0.5F, 3.0F, -6.5F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 141)
            .m_171480_()
            .m_171488_(-4.5F, 11.0F, -4.0F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, -2.0F, 0.0F)
      );
      PartDefinition left_leg_r1 = l_leg_armor.m_171599_(
         "left_leg_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 105)
            .m_171488_(-1.0F, -1.0F, -1.5F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 99)
            .m_171488_(-2.0F, -2.0F, -1.9F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, 6.0F, -2.6F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_leg_r2 = l_leg_armor.m_171599_(
         "left_leg_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 128).m_171488_(-0.5F, -0.5F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0F, -1.5F, -0.5F, 0.0F, 0.0F, -0.0873F)
      );
      PartDefinition r_leg = roots.m_171599_(
         "r_leg",
         CubeListBuilder.m_171558_().m_171514_(22, 72).m_171488_(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-5.0F, -13.0F, 0.0F)
      );
      PartDefinition r_leg_armor = r_leg.m_171599_(
         "r_leg_armor",
         CubeListBuilder.m_171558_()
            .m_171514_(90, 71)
            .m_171488_(-3.0F, -1.0F, 2.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.4F))
            .m_171514_(88, 62)
            .m_171488_(-3.0F, 7.0F, 2.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.4F))
            .m_171514_(0, 141)
            .m_171488_(-4.0F, 11.0F, 1.0F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 109)
            .m_171480_()
            .m_171488_(0.0F, 3.0F, -1.5F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(-0.5F, -2.0F, -5.0F)
      );
      PartDefinition right_leg_r1 = r_leg_armor.m_171599_(
         "right_leg_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 105)
            .m_171480_()
            .m_171488_(-5.0F, -1.0F, -1.5F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 99)
            .m_171480_()
            .m_171488_(-2.0F, -2.0F, -1.9F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 6.0F, 2.4F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition right_leg_r2 = r_leg_armor.m_171599_(
         "right_leg_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 128).m_171480_().m_171488_(-0.5F, -0.5F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-3.5F, -1.5F, 4.5F, 0.0F, 0.0F, 0.0873F)
      );
      PartDefinition body = roots.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(32, 60).m_171488_(-5.5F, -6.0F, -3.0F, 11.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -16.0F, 0.0F)
      );
      PartDefinition chest = body.m_171599_(
         "chest",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-9.0F, -14.0F, -6.0F, 18.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -6.0F, 0.0F, 0.0436F, 0.0F, 0.0F)
      );
      PartDefinition neck = chest.m_171599_(
         "neck",
         CubeListBuilder.m_171558_()
            .m_171514_(80, 165)
            .m_171488_(-2.5F, -4.0F, -2.55F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(80, 165)
            .m_171488_(-2.5F, 0.0F, -2.55F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(100, 168)
            .m_171488_(0.0F, -4.0F, 2.45F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -14.0F, -2.45F, 0.4363F, 0.0F, 0.0F)
      );
      PartDefinition head = neck.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 111).m_171488_(-4.0F, -7.0F, -5.5F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -4.0F, -0.55F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition helmet = head.m_171599_(
         "helmet",
         CubeListBuilder.m_171558_()
            .m_171514_(32, 113)
            .m_171488_(-4.0F, -2.0F, -3.5F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(102, 110)
            .m_171488_(-1.5F, -2.8F, -4.3F, 3.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 120)
            .m_171480_()
            .m_171488_(-5.5F, -2.0F, -1.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(116, 20)
            .m_171488_(-10.5F, -3.5F, 0.5F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 120)
            .m_171488_(4.5F, -2.0F, -1.5F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(116, 0)
            .m_171488_(4.5F, -9.5F, 0.5F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(88, 98)
            .m_171488_(-5.0F, 3.2F, -4.3F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.001F))
            .m_171514_(62, 91)
            .m_171488_(-4.0F, 5.0F, -3.5F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -5.0F, -2.0F)
      );
      PartDefinition head_r1 = helmet.m_171599_(
         "head_r1",
         CubeListBuilder.m_171558_().m_171514_(28, 104).m_171480_().m_171488_(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(2.4F, 3.5F, -3.8F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r2 = helmet.m_171599_(
         "head_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(42, 111)
            .m_171480_()
            .m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(44, 106)
            .m_171480_()
            .m_171488_(0.0F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(43, 109)
            .m_171480_()
            .m_171488_(-1.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(29, 115)
            .m_171480_()
            .m_171488_(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.4F, 3.5F, -4.1F, 0.0F, 0.0F, -0.2618F)
      );
      PartDefinition head_r3 = helmet.m_171599_(
         "head_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(43, 108)
            .m_171488_(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(32, 104)
            .m_171488_(-2.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.4F, 3.5F, -4.1F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition head_r4 = helmet.m_171599_(
         "head_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(25, 108)
            .m_171480_()
            .m_171488_(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 4.8F, -4.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r5 = helmet.m_171599_(
         "head_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(31, 108)
            .m_171480_()
            .m_171488_(-0.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 6.2F, -4.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition head_r6 = helmet.m_171599_(
         "head_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(30, 111)
            .m_171480_()
            .m_171488_(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 1.7F, -3.9F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_()
            .m_171514_(34, 26)
            .m_171488_(-3.0F, 0.0F, -2.5F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 0)
            .m_171488_(3.0F, 3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171488_(3.0F, -2.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(48, 5)
            .m_171480_()
            .m_171488_(-5.0F, -2.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(48, 0)
            .m_171480_()
            .m_171488_(-9.0F, 3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(84, 1)
            .m_171488_(3.0F, 8.0F, -2.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(84, 1)
            .m_171480_()
            .m_171488_(-8.0F, 8.0F, -2.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(52, 33)
            .m_171488_(-3.0F, 0.0F, 0.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -2.0F, -3.0F)
      );
      PartDefinition head_r7 = jaw.m_171599_(
         "head_r7",
         CubeListBuilder.m_171558_().m_171514_(92, 12).m_171488_(-3.0F, 0.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 8.0F, -2.5F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition chestplate = chest.m_171599_(
         "chestplate",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 150)
            .m_171488_(-6.0F, -3.0F, -9.0F, 12.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 159)
            .m_171488_(-4.0F, 4.0F, -9.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 180)
            .m_171488_(-9.0F, 3.0F, -7.0F, 18.0F, 2.0F, 12.0F, new CubeDeformation(0.2F))
            .m_171514_(68, 174)
            .m_171488_(-5.0F, -5.0F, -7.0F, 10.0F, 6.0F, 12.0F, new CubeDeformation(0.1F))
            .m_171514_(0, 165)
            .m_171488_(-6.0F, -3.0F, -9.0F, 12.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F))
            .m_171514_(0, 174)
            .m_171488_(-4.0F, 4.0F, -9.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171419_(0.0F, -9.0F, 1.0F)
      );
      PartDefinition body_r1 = chestplate.m_171599_(
         "body_r1",
         CubeListBuilder.m_171558_().m_171514_(48, 182).m_171488_(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 2.0F, 6.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition body_r2 = chestplate.m_171599_(
         "body_r2",
         CubeListBuilder.m_171558_().m_171514_(0, 188).m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.5F, 4.0F, -7.3F, 0.0F, 0.2618F, 0.1309F)
      );
      PartDefinition body_r3 = chestplate.m_171599_(
         "body_r3",
         CubeListBuilder.m_171558_().m_171514_(0, 188).m_171480_().m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-6.5F, 4.0F, -7.3F, 0.0F, -0.2618F, -0.1309F)
      );
      PartDefinition body_r4 = chestplate.m_171599_(
         "body_r4",
         CubeListBuilder.m_171558_().m_171514_(0, 188).m_171480_().m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-6.5F, -3.0F, -7.3F, 0.0F, -0.2618F, 0.5236F)
      );
      PartDefinition body_r5 = chestplate.m_171599_(
         "body_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 194)
            .m_171480_()
            .m_171488_(-0.5F, -1.0F, -6.0F, 9.0F, 2.0F, 12.0F, new CubeDeformation(0.1F))
            .m_171555_(false),
         PartPose.m_171423_(-9.0F, -4.5F, -1.0F, 0.0F, 0.0F, 0.5236F)
      );
      PartDefinition body_r6 = chestplate.m_171599_(
         "body_r6",
         CubeListBuilder.m_171558_().m_171514_(0, 188).m_171488_(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.5F, -3.0F, -7.3F, 0.0F, 0.2618F, -0.5236F)
      );
      PartDefinition body_r7 = chestplate.m_171599_(
         "body_r7",
         CubeListBuilder.m_171558_().m_171514_(0, 194).m_171488_(-8.5F, -1.0F, -6.0F, 9.0F, 2.0F, 12.0F, new CubeDeformation(0.1F)),
         PartPose.m_171423_(9.0F, -4.5F, -1.0F, 0.0F, 0.0F, -0.5236F)
      );
      PartDefinition l_arm = chest.m_171599_(
         "l_arm",
         CubeListBuilder.m_171558_().m_171514_(0, 49).m_171488_(-3.0F, -2.5F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(12.0F, -12.5F, 0.0F, 0.0F, 0.0F, -0.0873F)
      );
      PartDefinition l_arm_armor = l_arm.m_171599_(
         "l_arm_armor",
         CubeListBuilder.m_171558_()
            .m_171514_(112, 56)
            .m_171480_()
            .m_171488_(-4.5086F, -5.3695F, -7.0F, 2.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(130, 66)
            .m_171480_()
            .m_171488_(-4.5086F, 4.6305F, -7.0F, 12.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 26)
            .m_171480_()
            .m_171488_(-4.5086F, -4.3695F, -6.0F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(138, 36)
            .m_171480_()
            .m_171488_(6.4914F, -3.3695F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(106, 36)
            .m_171480_()
            .m_171488_(-1.5086F, -4.3695F, -6.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.2F))
            .m_171555_(false)
            .m_171514_(154, 40)
            .m_171480_()
            .m_171488_(0.4914F, -3.3695F, -7.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(166, 38)
            .m_171480_()
            .m_171488_(2.4914F, -4.3695F, -11.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(162, 46)
            .m_171480_()
            .m_171488_(1.4914F, -1.3695F, -11.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(142, 55)
            .m_171480_()
            .m_171488_(8.4914F, -0.3695F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(146, 49)
            .m_171480_()
            .m_171488_(8.4914F, -4.3695F, 0.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(1.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition right_arm_r1 = l_arm_armor.m_171599_(
         "right_arm_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 68)
            .m_171480_()
            .m_171488_(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F))
            .m_171555_(false)
            .m_171514_(168, 74)
            .m_171480_()
            .m_171488_(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(182, 75)
            .m_171480_()
            .m_171488_(-6.5F, -0.5F, 0.5F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-3.0086F, 5.1305F, -8.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition right_arm_r2 = l_arm_armor.m_171599_(
         "right_arm_r2",
         CubeListBuilder.m_171558_().m_171514_(130, 82).m_171480_().m_171488_(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(1.4914F, 6.6305F, 6.0F, 0.3491F, 0.0F, 0.0F)
      );
      PartDefinition right_arm_r3 = l_arm_armor.m_171599_(
         "right_arm_r3",
         CubeListBuilder.m_171558_().m_171514_(130, 82).m_171480_().m_171488_(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(1.4914F, 6.6305F, -6.0F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition right_arm_r4 = l_arm_armor.m_171599_(
         "right_arm_r4",
         CubeListBuilder.m_171558_().m_171514_(154, 68).m_171480_().m_171488_(0.0F, 0.0F, -7.0F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(6.4914F, 6.6305F, 0.0F, 0.0F, 0.0F, -0.3491F)
      );
      PartDefinition right_arm_r5 = l_arm_armor.m_171599_(
         "right_arm_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(162, 46)
            .m_171480_()
            .m_171488_(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(166, 38)
            .m_171480_()
            .m_171488_(0.0F, -3.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.4914F, -1.3695F, 9.0F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition right_arm_r6 = l_arm_armor.m_171599_(
         "right_arm_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(154, 40)
            .m_171480_()
            .m_171488_(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.4914F, -1.3695F, 6.0F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition arrow = l_arm_armor.m_171599_(
         "arrow",
         CubeListBuilder.m_171558_()
            .m_171514_(128, 100)
            .m_171488_(-8.0F, -2.5F, 0.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(128, 100)
            .m_171488_(-7.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(123, 100)
            .m_171488_(-8.0F, 0.0F, -2.5F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.5F, -3.0F, -5.0F, 0.0F, -0.4363F, 1.7453F)
      );
      PartDefinition arrow2 = l_arm_armor.m_171599_(
         "arrow2",
         CubeListBuilder.m_171558_()
            .m_171514_(128, 100)
            .m_171488_(-8.0F, -2.5F, 0.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(128, 100)
            .m_171488_(-7.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(123, 100)
            .m_171488_(-8.0F, 0.0F, -2.5F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.5F, -6.0F, 1.0F, 0.0385F, 0.2148F, 1.924F)
      );
      PartDefinition left_arm2 = l_arm.m_171599_(
         "left_arm2",
         CubeListBuilder.m_171558_()
            .m_171514_(70, 75)
            .m_171488_(-3.0F, 0.0F, -3.0F, 7.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(80, 49)
            .m_171488_(-4.0F, 10.0F, -3.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(3.0F, 9.5F, 0.0F)
      );
      PartDefinition l_arm_cloth = left_arm2.m_171599_(
         "l_arm_cloth",
         CubeListBuilder.m_171558_()
            .m_171514_(88, 31)
            .m_171488_(-3.5F, -4.25F, -3.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .m_171514_(109, 129)
            .m_171480_()
            .m_171488_(-4.5F, 0.75F, -4.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.5F, 7.25F, 0.0F)
      );
      PartDefinition hold = l_arm_cloth.m_171599_("hold", CubeListBuilder.m_171558_(), PartPose.m_171419_(10.5F, -2.0F, 2.0F));
      PartDefinition r_arm = chest.m_171599_(
         "r_arm",
         CubeListBuilder.m_171558_().m_171514_(60, 0).m_171488_(-5.0F, -2.5F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-12.0F, -12.5F, 0.0F, -1.044F, 0.2117F, 0.25F)
      );
      PartDefinition right_arm2 = r_arm.m_171599_(
         "right_arm2",
         CubeListBuilder.m_171558_()
            .m_171514_(44, 75)
            .m_171488_(-4.0F, 0.0F, -3.0F, 7.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(68, 36)
            .m_171488_(-3.0F, 10.0F, -3.0F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.0F, 9.5F, 0.0F, -1.9635F, 0.0F, 0.0F)
      );
      PartDefinition r_arm_cloth = right_arm2.m_171599_(
         "r_arm_cloth",
         CubeListBuilder.m_171558_()
            .m_171514_(84, 20)
            .m_171488_(-3.5F, -4.25F, -3.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.5F))
            .m_171514_(109, 129)
            .m_171488_(-4.5F, 0.75F, -4.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.5F, 7.25F, 0.0F)
      );
      PartDefinition axe = right_arm2.m_171599_(
         "axe",
         CubeListBuilder.m_171558_()
            .m_171514_(3, 205)
            .m_171488_(-1.5F, -1.5F, -32.0F, 3.0F, 3.0F, 48.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 241)
            .m_171488_(-3.5F, 0.0F, -31.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(3, 205)
            .m_171488_(-1.5F, -1.5F, -32.0F, 3.0F, 3.0F, 48.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 241)
            .m_171488_(-1.5F, -1.5F, -25.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.2F))
            .m_171514_(26, 241)
            .m_171488_(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.2F))
            .m_171514_(25, 245)
            .m_171488_(-1.5F, -1.5F, -18.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.4F))
            .m_171514_(25, 245)
            .m_171488_(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.4F))
            .m_171514_(25, 245)
            .m_171488_(-1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.4F))
            .m_171514_(57, 245)
            .m_171488_(-2.5F, -2.5F, -28.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(57, 245)
            .m_171488_(-2.5F, -2.5F, 15.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(60, 228)
            .m_171488_(-3.5F, 0.0F, 17.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 228)
            .m_171488_(1.4F, 0.0F, 17.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(60, 231)
            .m_171488_(-0.1F, 1.5F, 17.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(60, 233)
            .m_171488_(-0.1F, -3.5F, 17.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(60, 241)
            .m_171488_(1.5F, 0.0F, -31.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.5F, 13.5F, 0.0F)
      );
      PartDefinition cube_r1 = axe.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(60, 241)
            .m_171488_(1.5F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(56, 241)
            .m_171488_(-3.5F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -29.0F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition cube_r2 = axe.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(68, 231).m_171488_(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 20.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition axe_head = axe.m_171599_(
         "axe_head",
         CubeListBuilder.m_171558_()
            .m_171514_(111, 249)
            .m_171488_(-1.5F, -1.5F, -14.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(68, 231)
            .m_171488_(-1.5F, -1.5F, -13.8F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(122, 203)
            .m_171488_(-0.5F, 7.4F, -8.8F, 1.0F, 10.0F, 9.0F, new CubeDeformation(-0.001F))
            .m_171514_(0, 236)
            .m_171488_(-1.5F, -5.5F, -5.8F, 3.0F, 11.0F, 6.0F, new CubeDeformation(0.3F))
            .m_171514_(97, 232)
            .m_171488_(-1.5F, -7.5F, -9.6F, 3.0F, 15.0F, 6.0F, new CubeDeformation(0.2F))
            .m_171514_(73, 229)
            .m_171488_(-1.5F, -7.5F, -8.8F, 3.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -32.2F)
      );
      PartDefinition cube_r3 = axe_head.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(81, 211).m_171488_(-0.5F, -3.5F, -5.5F, 1.0F, 9.0F, 9.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, -10.5F, -4.3F, 0.48F, 0.0F, 0.0F)
      );
      PartDefinition cube_r4 = axe_head.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(156, 198).m_171488_(0.5F, -18.5F, -7.0F, 0.0F, 33.0F, 14.0F, new CubeDeformation(0.001F)),
         PartPose.m_171423_(-0.5F, 19.1267F, -0.0182F, -1.0036F, 0.0F, 0.0F)
      );
      PartDefinition cube_r5 = axe_head.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(142, 206)
            .m_171488_(-0.5F, -13.5F, -1.0F, 1.0F, 16.0F, 6.0F, new CubeDeformation(0.001F))
            .m_171514_(104, 208)
            .m_171488_(-0.5F, 2.5F, -9.0F, 1.0F, 6.0F, 14.0F, new CubeDeformation(0.001F)),
         PartPose.m_171423_(0.0F, 14.0647F, -3.2431F, -1.0036F, 0.0F, 0.0F)
      );
      PartDefinition cube_r6 = axe_head.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(109, 214).m_171488_(0.5F, 2.5F, -9.0F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.001F)),
         PartPose.m_171423_(-0.5F, 17.2895F, -8.3051F, -1.0036F, 0.0F, 0.0F)
      );
      PartDefinition cube_r7 = axe_head.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(64, 228).m_171488_(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -15.8F, 0.0F, 0.0F, 2.3562F)
      );
      PartDefinition cube_r8 = axe_head.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(64, 228).m_171488_(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -15.8F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition emblem3 = axe_head.m_171599_(
         "emblem3",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 74)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(168, 68)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(-2.0F, 0.0F, -5.3F, 1.5708F, -0.7854F, 1.5708F)
      );
      PartDefinition right_arm_r7 = emblem3.m_171599_(
         "right_arm_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(169, 204)
            .m_171480_()
            .m_171488_(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.5F, 0.5F, -0.5F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition emblem4 = axe_head.m_171599_(
         "emblem4",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 74)
            .m_171480_()
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(169, 204)
            .m_171480_()
            .m_171488_(-4.5F, -3.5F, -0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(168, 68)
            .m_171480_()
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F))
            .m_171555_(false),
         PartPose.m_171423_(2.0F, 0.0F, -5.3F, 1.5708F, 0.7854F, -1.5708F)
      );
      PartDefinition r_arm_armor = r_arm.m_171599_(
         "r_arm_armor",
         CubeListBuilder.m_171558_()
            .m_171514_(112, 56)
            .m_171488_(2.5086F, -5.3695F, -7.0F, 2.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(130, 86)
            .m_171488_(2.5086F, -3.3695F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
            .m_171514_(130, 66)
            .m_171488_(-7.4914F, 4.6305F, -7.0F, 12.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 26)
            .m_171488_(-6.4914F, -4.3695F, -6.0F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(138, 36)
            .m_171488_(-8.4914F, -3.3695F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171514_(106, 36)
            .m_171488_(-6.4914F, -4.3695F, -6.0F, 8.0F, 8.0F, 12.0F, new CubeDeformation(0.2F))
            .m_171514_(154, 40)
            .m_171488_(-4.4914F, -3.3695F, -7.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(166, 38)
            .m_171488_(-2.4914F, -4.3695F, -11.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(162, 46)
            .m_171488_(-3.4914F, -1.3695F, -11.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(142, 55)
            .m_171488_(-14.4914F, -0.3695F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(146, 49)
            .m_171488_(-14.4914F, -4.3695F, 0.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition left_arm_r1 = r_arm_armor.m_171599_(
         "left_arm_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 68)
            .m_171488_(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F))
            .m_171514_(168, 74)
            .m_171488_(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(182, 75)
            .m_171488_(-0.5F, -0.5F, 0.5F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0086F, 5.1305F, -8.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_arm_r2 = r_arm_armor.m_171599_(
         "left_arm_r2",
         CubeListBuilder.m_171558_().m_171514_(130, 82).m_171488_(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.4914F, 6.6305F, 6.0F, 0.3491F, 0.0F, 0.0F)
      );
      PartDefinition left_arm_r3 = r_arm_armor.m_171599_(
         "left_arm_r3",
         CubeListBuilder.m_171558_().m_171514_(130, 82).m_171488_(-6.0F, 0.0F, 0.0F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.4914F, 6.6305F, -6.0F, -0.3491F, 0.0F, 0.0F)
      );
      PartDefinition left_arm_r4 = r_arm_armor.m_171599_(
         "left_arm_r4",
         CubeListBuilder.m_171558_().m_171514_(154, 68).m_171488_(0.0F, 0.0F, -7.0F, 0.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-6.4914F, 6.6305F, 0.0F, 0.0F, 0.0F, 0.3491F)
      );
      PartDefinition left_arm_r5 = r_arm_armor.m_171599_(
         "left_arm_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(162, 46)
            .m_171488_(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(166, 38)
            .m_171488_(0.0F, -3.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.4914F, -1.3695F, 9.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition left_arm_r6 = r_arm_armor.m_171599_(
         "left_arm_r6",
         CubeListBuilder.m_171558_().m_171514_(154, 40).m_171488_(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.4914F, -1.3695F, 6.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition belt = body.m_171599_(
         "belt",
         CubeListBuilder.m_171558_()
            .m_171514_(95, 131)
            .m_171488_(3.5F, -19.0F, -4.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(100, 144)
            .m_171488_(6.5F, -17.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(100, 144)
            .m_171480_()
            .m_171488_(-9.5F, -17.0F, -2.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(95, 131)
            .m_171480_()
            .m_171488_(-6.5F, -19.0F, -4.0F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(60, 66)
            .m_171488_(-5.5F, -17.0F, -3.0F, 11.0F, 3.0F, 6.0F, new CubeDeformation(0.5F))
            .m_171514_(157, 86)
            .m_171488_(-5.5F, -19.0F, -3.0F, 11.0F, 3.0F, 6.0F, new CubeDeformation(0.3F)),
         PartPose.m_171419_(0.0F, 14.0F, 0.0F)
      );
      PartDefinition body_r8 = belt.m_171599_(
         "body_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(100, 144)
            .m_171488_(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(100, 144)
            .m_171480_()
            .m_171488_(-17.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(8.0F, -17.0F, -0.5F, -1.5708F, 0.0F, 0.0F)
      );
      PartDefinition emblem2 = belt.m_171599_(
         "emblem2",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 74)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(168, 68)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(0.0F, -15.5F, 3.5F, 3.1416F, 0.0F, 2.3562F)
      );
      PartDefinition emblem = belt.m_171599_(
         "emblem",
         CubeListBuilder.m_171558_()
            .m_171514_(168, 74)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(168, 68)
            .m_171488_(-2.5F, -2.5F, -0.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(-0.1F)),
         PartPose.m_171423_(0.0F, -15.5F, -3.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cloth2 = belt.m_171599_(
         "cloth2",
         CubeListBuilder.m_171558_().m_171514_(46, 127).m_171488_(-4.5F, 0.0F, 0.0F, 9.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -13.5F, 3.5F)
      );
      PartDefinition cloth = belt.m_171599_(
         "cloth",
         CubeListBuilder.m_171558_().m_171514_(46, 127).m_171488_(-4.5F, 0.0F, 0.0F, 9.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -13.5F, -3.5F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(Aptrgangr_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entity.getAttackState() != 4) {
         this.animateWalk(Aptrgangr_Animation.WALK, limbSwing, limbSwingAmount, 2.5F, 4.0F);
      }

      this.m_233385_(entity.getAnimationState("idle"), Aptrgangr_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("swing_right"), Aptrgangr_Animation.SWING_RIGHT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("smash"), Aptrgangr_Animation.SMASH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge_start"), Aptrgangr_Animation.RUSH_START, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge"), Aptrgangr_Animation.RUSHING, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge_end"), Aptrgangr_Animation.RUSH_END, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("charge_hit"), Aptrgangr_Animation.RUSH_HIT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("death"), Aptrgangr_Animation.DEATH, ageInTicks, 1.0F);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ += xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ += yRot * (float) (Math.PI / 180.0);
   }

   public void translateToHand(PoseStack matrixStack) {
      this.root.m_104299_(matrixStack);
      this.roots.m_104299_(matrixStack);
      this.body.m_104299_(matrixStack);
      this.chest.m_104299_(matrixStack);
      this.l_arm.m_104299_(matrixStack);
      this.left_arm2.m_104299_(matrixStack);
      this.l_arm_cloth.m_104299_(matrixStack);
      this.hold.m_104299_(matrixStack);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
