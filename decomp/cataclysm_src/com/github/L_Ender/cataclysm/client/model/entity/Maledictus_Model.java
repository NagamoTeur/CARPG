package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Maledictus_Animation;
import com.github.L_Ender.cataclysm.client.animation.Maledictus_Attack_Animation;
import com.github.L_Ender.cataclysm.client.animation.Maledictus_Grab_Attack_Animation;
import com.github.L_Ender.cataclysm.client.animation.Maledictus_Halberd_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
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

public class Maledictus_Model extends HierarchicalModel<Maledictus_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart berserker;
   private final ModelPart legs;
   private final ModelPart right_leg;
   private final ModelPart cube_r1;
   private final ModelPart right_front_leg;
   private final ModelPart cube_r2;
   private final ModelPart left_leg;
   private final ModelPart cube_r3;
   private final ModelPart left_front_leg;
   private final ModelPart cube_r4;
   private final ModelPart pelvis;
   private final ModelPart front_cloth1;
   private final ModelPart front_cloth2;
   private final ModelPart body;
   private final ModelPart right_shoulder;
   private final ModelPart cube_r5;
   private final ModelPart cube_r6;
   private final ModelPart right_arm;
   private final ModelPart right_front_arm;
   private final ModelPart bow;
   private final ModelPart cube_r7;
   private final ModelPart cube_r8;
   private final ModelPart cube_r9;
   private final ModelPart bow_string;
   private final ModelPart string1;
   private final ModelPart string2;
   private final ModelPart right_mace;
   private final ModelPart cube_r10;
   private final ModelPart cube_r11;
   private final ModelPart cube_r12;
   private final ModelPart halberd;
   private final ModelPart cube_r13;
   private final ModelPart halberd2;
   private final ModelPart cube_r14;
   private final ModelPart cube_r15;
   private final ModelPart bone;
   private final ModelPart right_particle;
   private final ModelPart left_shoulder;
   private final ModelPart cube_r16;
   private final ModelPart cube_r17;
   private final ModelPart left_arm;
   private final ModelPart left_front_arm;
   private final ModelPart left_mace;
   private final ModelPart cube_r18;
   private final ModelPart cube_r19;
   private final ModelPart cube_r20;
   private final ModelPart left_particle;
   private final ModelPart head;
   private final ModelPart right_horn;
   private final ModelPart cube_r21;
   private final ModelPart left_horn;
   private final ModelPart cube_r22;
   private final ModelPart left_wing;
   private final ModelPart left_wing2;
   private final ModelPart right_wing;
   private final ModelPart right_wing2;

   public Maledictus_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.berserker = this.roots.m_171324_("berserker");
      this.legs = this.berserker.m_171324_("legs");
      this.right_leg = this.legs.m_171324_("right_leg");
      this.cube_r1 = this.right_leg.m_171324_("cube_r1");
      this.right_front_leg = this.right_leg.m_171324_("right_front_leg");
      this.cube_r2 = this.right_front_leg.m_171324_("cube_r2");
      this.left_leg = this.legs.m_171324_("left_leg");
      this.cube_r3 = this.left_leg.m_171324_("cube_r3");
      this.left_front_leg = this.left_leg.m_171324_("left_front_leg");
      this.cube_r4 = this.left_front_leg.m_171324_("cube_r4");
      this.pelvis = this.berserker.m_171324_("pelvis");
      this.front_cloth1 = this.pelvis.m_171324_("front_cloth1");
      this.front_cloth2 = this.front_cloth1.m_171324_("front_cloth2");
      this.body = this.pelvis.m_171324_("body");
      this.right_shoulder = this.body.m_171324_("right_shoulder");
      this.cube_r5 = this.right_shoulder.m_171324_("cube_r5");
      this.cube_r6 = this.right_shoulder.m_171324_("cube_r6");
      this.right_arm = this.right_shoulder.m_171324_("right_arm");
      this.right_front_arm = this.right_arm.m_171324_("right_front_arm");
      this.bow = this.right_front_arm.m_171324_("bow");
      this.cube_r7 = this.bow.m_171324_("cube_r7");
      this.cube_r8 = this.bow.m_171324_("cube_r8");
      this.cube_r9 = this.bow.m_171324_("cube_r9");
      this.bow_string = this.bow.m_171324_("bow_string");
      this.string1 = this.bow_string.m_171324_("string1");
      this.string2 = this.bow_string.m_171324_("string2");
      this.right_mace = this.right_front_arm.m_171324_("right_mace");
      this.cube_r10 = this.right_mace.m_171324_("cube_r10");
      this.cube_r11 = this.right_mace.m_171324_("cube_r11");
      this.cube_r12 = this.right_mace.m_171324_("cube_r12");
      this.halberd = this.right_front_arm.m_171324_("halberd");
      this.cube_r13 = this.halberd.m_171324_("cube_r13");
      this.halberd2 = this.halberd.m_171324_("halberd2");
      this.cube_r14 = this.halberd2.m_171324_("cube_r14");
      this.cube_r15 = this.halberd2.m_171324_("cube_r15");
      this.bone = this.halberd.m_171324_("bone");
      this.right_particle = this.right_front_arm.m_171324_("right_particle");
      this.left_shoulder = this.body.m_171324_("left_shoulder");
      this.cube_r16 = this.left_shoulder.m_171324_("cube_r16");
      this.cube_r17 = this.left_shoulder.m_171324_("cube_r17");
      this.left_arm = this.left_shoulder.m_171324_("left_arm");
      this.left_front_arm = this.left_arm.m_171324_("left_front_arm");
      this.left_mace = this.left_front_arm.m_171324_("left_mace");
      this.cube_r18 = this.left_mace.m_171324_("cube_r18");
      this.cube_r19 = this.left_mace.m_171324_("cube_r19");
      this.cube_r20 = this.left_mace.m_171324_("cube_r20");
      this.left_particle = this.left_front_arm.m_171324_("left_particle");
      this.head = this.body.m_171324_("head");
      this.right_horn = this.head.m_171324_("right_horn");
      this.cube_r21 = this.right_horn.m_171324_("cube_r21");
      this.left_horn = this.head.m_171324_("left_horn");
      this.cube_r22 = this.left_horn.m_171324_("cube_r22");
      this.left_wing = this.body.m_171324_("left_wing");
      this.left_wing2 = this.left_wing.m_171324_("left_wing2");
      this.right_wing = this.body.m_171324_("right_wing");
      this.right_wing2 = this.right_wing.m_171324_("right_wing2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition berserker = roots.m_171599_("berserker", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -24.0F, -3.0F));
      PartDefinition legs = berserker.m_171599_("legs", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 1.0F, 0.0F));
      PartDefinition right_leg = legs.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(95, 68).m_171488_(-4.0F, -0.0428F, -1.3474F, 6.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, 0.0F, 0.0F, 0.0097F, 0.218F, 0.0447F)
      );
      PartDefinition cube_r1 = right_leg.m_171599_(
         "cube_r1",
         CubeListBuilder.m_171558_().m_171514_(74, 148).m_171488_(-8.0F, -16.0F, -1.5F, 6.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5557F, 14.9936F, -0.8474F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition right_front_leg = right_leg.m_171599_(
         "right_front_leg",
         CubeListBuilder.m_171558_().m_171514_(95, 97).m_171488_(-4.1F, 0.9829F, -1.2611F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 9.9572F, 0.6526F)
      );
      PartDefinition cube_r2 = right_front_leg.m_171599_(
         "cube_r2",
         CubeListBuilder.m_171558_().m_171514_(101, 155).m_171488_(-5.0F, 1.0F, -3.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0171F, -2.2611F, 0.5672F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = legs.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(41, 41).m_171488_(-2.0F, -0.0428F, -1.3474F, 6.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.0F, 0.0F, 0.0F, 0.0097F, -0.218F, -0.0447F)
      );
      PartDefinition cube_r3 = left_leg.m_171599_(
         "cube_r3",
         CubeListBuilder.m_171558_().m_171514_(0, 147).m_171488_(2.0F, -16.0F, -1.5F, 6.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5557F, 14.9936F, -0.8474F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition left_front_leg = left_leg.m_171599_(
         "left_front_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 41).m_171488_(-0.9F, 0.9829F, -1.2611F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 9.9572F, 0.6526F)
      );
      PartDefinition cube_r4 = left_front_leg.m_171599_(
         "cube_r4",
         CubeListBuilder.m_171558_().m_171514_(100, 126).m_171488_(-1.0F, 1.0F, -3.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0171F, -2.2611F, 0.5672F, 0.0F, 0.0F)
      );
      PartDefinition pelvis = berserker.m_171599_(
         "pelvis",
         CubeListBuilder.m_171558_().m_171514_(146, 34).m_171488_(-6.0F, -3.0F, -2.0F, 12.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, 0.0F)
      );
      PartDefinition front_cloth1 = pelvis.m_171599_(
         "front_cloth1",
         CubeListBuilder.m_171558_().m_171514_(119, 68).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.0F, -1.6F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition front_cloth2 = front_cloth1.m_171599_(
         "front_cloth2",
         CubeListBuilder.m_171558_().m_171514_(22, 127).m_171488_(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 9.0F, 0.0F, 0.2618F, 0.0F, 0.0F)
      );
      PartDefinition body = pelvis.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(119, 139).m_171488_(-5.5F, -15.6F, -6.6743F, 11.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 1.6F, 3.6F)
      );
      PartDefinition right_shoulder = body.m_171599_(
         "right_shoulder", CubeListBuilder.m_171558_(), PartPose.m_171423_(-6.7084F, -16.0F, -1.0743F, 0.132F, 0.1298F, 0.1917F)
      );
      PartDefinition cube_r5 = right_shoulder.m_171599_(
         "cube_r5",
         CubeListBuilder.m_171558_().m_171514_(143, 86).m_171488_(-8.0F, -15.0F, 0.0F, 11.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(7.7209F, 5.9143F, -4.5994F, 0.0F, 0.0F, -1.2217F)
      );
      PartDefinition cube_r6 = right_shoulder.m_171599_(
         "cube_r6",
         CubeListBuilder.m_171558_().m_171514_(148, 63).m_171488_(-16.0F, -12.0F, 0.0F, 10.0F, 4.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(7.7209F, 10.9143F, -4.5994F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition right_arm = right_shoulder.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_().m_171514_(22, 163).m_171488_(-3.0F, -3.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.3791F, 1.9143F, -1.5994F)
      );
      PartDefinition right_front_arm = right_arm.m_171599_(
         "right_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(162, 99)
            .m_171488_(-3.0F, 0.0F, -3.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 154)
            .m_171488_(-4.0F, -3.0F, -3.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.1F))
            .m_171514_(151, 155)
            .m_171480_()
            .m_171488_(-3.0F, 0.0F, -3.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-0.025F, 7.0F, 1.0F, -0.3054F, 0.0F, 0.0F)
      );
      PartDefinition bow = right_front_arm.m_171599_(
         "bow",
         CubeListBuilder.m_171558_()
            .m_171514_(119, 37)
            .m_171488_(-1.0F, -1.0F, -11.945F, 2.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(0.0F, 2.0F, -6.945F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.9401F, 9.0F, 0.0F)
      );
      PartDefinition cube_r7 = bow.m_171599_(
         "cube_r7",
         CubeListBuilder.m_171558_().m_171514_(119, 63).m_171488_(-2.5F, 6.0F, -25.0F, 5.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 1.0F, 5.0065F, -0.5672F, 0.0F, 0.0F)
      );
      PartDefinition cube_r8 = bow.m_171599_(
         "cube_r8",
         CubeListBuilder.m_171558_().m_171514_(95, 97).m_171488_(0.5F, 3.0F, -29.9F, 0.0F, 5.0F, 23.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, 0.7688F, 2.6724F, -0.5672F, 0.0F, 0.0F)
      );
      PartDefinition cube_r9 = bow.m_171599_(
         "cube_r9",
         CubeListBuilder.m_171558_()
            .m_171514_(95, 68)
            .m_171488_(0.0F, 4.0F, 9.0F, 0.0F, 5.0F, 23.0F, new CubeDeformation(0.0F))
            .m_171514_(124, 116)
            .m_171488_(-1.5F, 6.0F, 7.0F, 3.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 1.0F, -6.945F, 0.5672F, 0.0F, 0.0F)
      );
      PartDefinition bow_string = bow.m_171599_("bow_string", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.5F, -9.5F, 0.0F));
      PartDefinition string1 = bow_string.m_171599_(
         "string1",
         CubeListBuilder.m_171558_().m_171514_(22, 128).m_171488_(-1.001F, -0.1325F, -0.0242F, 1.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, -1.0F)
      );
      PartDefinition string2 = bow_string.m_171599_(
         "string2",
         CubeListBuilder.m_171558_().m_171514_(0, 127).m_171488_(-0.001F, -0.1325F, -18.9758F, 1.0F, 0.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-1.0F, 0.0F, -1.0485F)
      );
      PartDefinition right_mace = right_front_arm.m_171599_(
         "right_mace",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 126)
            .m_171480_()
            .m_171488_(-1.0F, -1.0F, -13.0F, 2.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(26, 0)
            .m_171480_()
            .m_171488_(-1.5F, -1.5F, -27.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 41)
            .m_171480_()
            .m_171488_(0.5188F, 0.001F, -35.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 41)
            .m_171480_()
            .m_171488_(0.5188F, 0.001F, -35.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(-1.0F, 8.0F, -1.0F)
      );
      PartDefinition cube_r10 = right_mace.m_171599_(
         "cube_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 41)
            .m_171480_()
            .m_171488_(0.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, 1.5708F)
      );
      PartDefinition cube_r11 = right_mace.m_171599_(
         "cube_r11",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 41)
            .m_171480_()
            .m_171488_(0.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, 3.1416F)
      );
      PartDefinition cube_r12 = right_mace.m_171599_(
         "cube_r12",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 41)
            .m_171480_()
            .m_171488_(0.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition halberd = right_front_arm.m_171599_(
         "halberd",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-0.9901F, -1.2083F, -32.3333F, 2.0F, 2.0F, 65.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 0)
            .m_171488_(-0.4901F, -0.7083F, -37.3333F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(46, 131)
            .m_171488_(0.0099F, -2.7083F, -53.3333F, 0.0F, 5.0F, 17.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 127)
            .m_171488_(-1.4901F, -1.7083F, -31.3333F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 21)
            .m_171488_(2.0099F, -0.7083F, -16.8333F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(18, 21)
            .m_171488_(-0.4901F, -4.2083F, -16.8333F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 0)
            .m_171488_(-1.9901F, -2.2083F, -17.3333F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(15, 21)
            .m_171488_(-0.4901F, 1.7917F, -16.8333F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(10, 21)
            .m_171480_()
            .m_171488_(-4.1089F, -0.7083F, -16.8333F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 0)
            .m_171488_(0.0099F, 1.2917F, -37.3333F, 0.0F, 15.0F, 25.0F, new CubeDeformation(0.0F))
            .m_171514_(123, 86)
            .m_171488_(0.0099F, -11.7083F, -33.3333F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-0.3911F, 8.2083F, -0.6667F)
      );
      PartDefinition cube_r13 = halberd.m_171599_(
         "cube_r13",
         CubeListBuilder.m_171558_().m_171514_(46, 131).m_171488_(0.0F, -2.5F, -8.5F, 0.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0099F, -0.2083F, -42.8333F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition halberd2 = halberd.m_171599_(
         "halberd2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 21)
            .m_171488_(1.5238F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 6)
            .m_171488_(-1.4762F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 21)
            .m_171480_()
            .m_171488_(-3.595F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-0.0139F, -0.2083F, -33.8333F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition cube_r14 = halberd2.m_171599_(
         "cube_r14",
         CubeListBuilder.m_171558_().m_171514_(5, 21).m_171488_(1.5F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0238F, 5.0F, 0.0F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition cube_r15 = halberd2.m_171599_(
         "cube_r15",
         CubeListBuilder.m_171558_().m_171514_(0, 21).m_171488_(1.5F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0238F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition bone = halberd.m_171599_("bone", CubeListBuilder.m_171558_(), PartPose.m_171423_(-0.0139F, -0.2083F, -16.8333F, 0.0F, 0.0F, 0.7854F));
      PartDefinition right_particle = right_front_arm.m_171599_("right_particle", CubeListBuilder.m_171558_(), PartPose.m_171419_(-0.828F, 5.3443F, -1.302F));
      PartDefinition left_shoulder = body.m_171599_(
         "left_shoulder", CubeListBuilder.m_171558_(), PartPose.m_171423_(6.7084F, -16.0F, -2.0743F, 0.132F, -0.1298F, -0.1917F)
      );
      PartDefinition cube_r16 = left_shoulder.m_171599_(
         "cube_r16",
         CubeListBuilder.m_171558_()
            .m_171514_(143, 86)
            .m_171480_()
            .m_171488_(-3.0F, -15.0F, 0.0F, 11.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-7.7209F, 5.9143F, -3.5994F, 0.0F, 0.0F, 1.2217F)
      );
      PartDefinition cube_r17 = left_shoulder.m_171599_(
         "cube_r17",
         CubeListBuilder.m_171558_().m_171514_(146, 44).m_171488_(6.0F, -12.0F, 0.0F, 10.0F, 4.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(-7.7209F, 10.9143F, -3.5994F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition left_arm = left_shoulder.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_().m_171514_(124, 160).m_171488_(-2.0F, -3.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.3791F, 1.9143F, -0.5994F)
      );
      PartDefinition left_front_arm = left_arm.m_171599_(
         "left_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(151, 155)
            .m_171488_(-2.0F, 0.0F, -3.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(52, 154)
            .m_171480_()
            .m_171488_(-1.0F, -3.0F, -3.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.1F))
            .m_171555_(false),
         PartPose.m_171423_(0.025F, 7.0F, 1.0F, -0.3054F, 0.0F, 0.0F)
      );
      PartDefinition left_mace = left_front_arm.m_171599_(
         "left_mace",
         CubeListBuilder.m_171558_()
            .m_171514_(76, 126)
            .m_171488_(-1.0F, -1.0F, -13.0F, 2.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 0)
            .m_171488_(-1.5F, -1.5F, -27.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 41)
            .m_171488_(-9.5188F, 0.001F, -35.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 41)
            .m_171488_(-9.5188F, 0.001F, -35.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(1.0F, 8.0F, -1.0F)
      );
      PartDefinition cube_r18 = left_mace.m_171599_(
         "cube_r18",
         CubeListBuilder.m_171558_().m_171514_(0, 41).m_171488_(-9.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition cube_r19 = left_mace.m_171599_(
         "cube_r19",
         CubeListBuilder.m_171558_().m_171514_(0, 41).m_171488_(-9.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, -3.1416F)
      );
      PartDefinition cube_r20 = left_mace.m_171599_(
         "cube_r20",
         CubeListBuilder.m_171558_().m_171514_(0, 41).m_171488_(-9.525F, 0.0F, -15.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0062F, 0.001F, -20.0F, 0.0F, 0.0F, 1.5708F)
      );
      PartDefinition left_particle = left_front_arm.m_171599_("left_particle", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.4468F, 5.3443F, -1.302F));
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(141, 17)
            .m_171488_(-4.0F, -8.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(141, 0)
            .m_171488_(-4.0F, -8.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .m_171514_(149, 116)
            .m_171488_(-6.5F, -5.5F, -4.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(27, 148)
            .m_171488_(2.5F, -5.5F, -4.0F, 4.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -16.6F, -4.0F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition right_horn = head.m_171599_("right_horn", CubeListBuilder.m_171558_(), PartPose.m_171423_(-6.2F, -10.0F, 0.0F, -0.2618F, 0.0F, -0.6545F));
      PartDefinition cube_r21 = right_horn.m_171599_(
         "cube_r21",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 74)
            .m_171488_(-7.811F, -23.4301F, 0.1321F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(-9.811F, -17.4301F, 0.1321F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(156, 139)
            .m_171488_(-4.811F, -21.4301F, -1.3679F, 11.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 127)
            .m_171488_(-4.811F, -17.4301F, -1.8679F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, 12.0F, 2.0F, 0.1309F, 0.0F, -0.3054F)
      );
      PartDefinition left_horn = head.m_171599_("left_horn", CubeListBuilder.m_171558_(), PartPose.m_171423_(6.2F, -10.0F, 0.0F, -0.2618F, 0.0F, 0.6545F));
      PartDefinition cube_r22 = left_horn.m_171599_(
         "cube_r22",
         CubeListBuilder.m_171558_()
            .m_171514_(148, 74)
            .m_171480_()
            .m_171488_(-4.189F, -23.4301F, 0.1321F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 0)
            .m_171480_()
            .m_171488_(4.811F, -17.4301F, 0.1321F, 5.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(156, 139)
            .m_171480_()
            .m_171488_(-6.189F, -21.4301F, -1.3679F, 11.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 127)
            .m_171480_()
            .m_171488_(0.811F, -17.4301F, -1.8679F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, 12.0F, 2.0F, 0.1309F, 0.0F, 0.3054F)
      );
      PartDefinition left_wing = body.m_171599_(
         "left_wing",
         CubeListBuilder.m_171558_().m_171514_(70, 0).m_171488_(-35.0F, -30.0F, 0.0F, 35.0F, 58.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.0F, -10.0F, 1.0F, -0.0181F, 0.3923F, -0.0472F)
      );
      PartDefinition left_wing2 = left_wing.m_171599_(
         "left_wing2",
         CubeListBuilder.m_171558_().m_171514_(0, 68).m_171488_(-47.0F, -38.0F, 0.0F, 47.0F, 58.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-35.0F, 8.0F, 0.0F)
      );
      PartDefinition right_wing = body.m_171599_(
         "right_wing",
         CubeListBuilder.m_171558_().m_171514_(70, 0).m_171480_().m_171488_(0.0F, -38.0F, 0.0F, 35.0F, 58.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(3.0F, -2.0F, 1.0F, -0.0181F, -0.3923F, 0.0472F)
      );
      PartDefinition right_wing2 = right_wing.m_171599_(
         "right_wing2",
         CubeListBuilder.m_171558_().m_171514_(0, 68).m_171480_().m_171488_(0.0F, -38.0F, 0.0F, 47.0F, 58.0F, 0.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(35.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 256, 256);
   }

   public void setupAnim(Maledictus_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      this.m_233385_(entity.getAnimationState("idle"), Maledictus_Animation.IDLE, ageInTicks, 0.75F);
      this.m_233385_(entity.getAnimationState("swing"), Maledictus_Animation.SWING, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("shoot"), Maledictus_Animation.SHOOT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flying_shoot"), Maledictus_Animation.FLYING_SHOOT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("fall_loop"), Maledictus_Animation.FALL_LOOP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("fall_end"), Maledictus_Animation.FALL_END, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("mass_effect"), Maledictus_Animation.MASS_EFFECT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flying_smash_1"), Maledictus_Animation.FLYING_SMASH_1, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flying_smash_2"), Maledictus_Animation.FLYING_SMASH_2, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flying_halberd_smash_1"), Maledictus_Halberd_Animation.FLYING_HALBERD_SMASH_1, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flying_halberd_smash_2"), Maledictus_Halberd_Animation.FLYING_HALBERD_SMASH_2, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("death"), Maledictus_Animation.DEATH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("back_step"), Maledictus_Animation.CHARGE_BACKSTEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("back_step_dash"), Maledictus_Halberd_Animation.DASH1, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("radagon"), Maledictus_Halberd_Animation.RADAGON, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("halberd_swing"), Maledictus_Halberd_Animation.HALBERD_SLASH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("dash"), Maledictus_Halberd_Animation.DASH1, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("back_step_dash_no_back_step"), Maledictus_Halberd_Animation.DASH1_NO_BACK_STEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("dash_no_back_step"), Maledictus_Halberd_Animation.DASH1_NO_BACK_STEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("dash2"), Maledictus_Halberd_Animation.DASH2, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("dash2_no_back_step"), Maledictus_Halberd_Animation.DASH2_NO_BACK_STEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("dash3"), Maledictus_Halberd_Animation.DASH3, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("spin_slashes"), Maledictus_Attack_Animation.SPIN_SLASHES, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("combo_first"), Maledictus_Attack_Animation.COMBO_FIRST, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("combo_first_end"), Maledictus_Attack_Animation.COMBO_FIRST_END, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("combo_second"), Maledictus_Attack_Animation.COMBO_SECOND, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("uppercut_right"), Maledictus_Attack_Animation.UPPERCUT_RIGHT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("uppercut_left"), Maledictus_Attack_Animation.UPPERCUT_LEFT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_start"), Maledictus_Grab_Attack_Animation.GRAB_START, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_loop"), Maledictus_Grab_Attack_Animation.GRAB_LOOP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_fail"), Maledictus_Grab_Attack_Animation.GRAB_FAIL, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_success"), Maledictus_Grab_Attack_Animation.GRAB_SUCCESS_FLY, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_success_loop"), Maledictus_Grab_Attack_Animation.GRAB_DIVE_LOOP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("grab_success_end"), Maledictus_Grab_Attack_Animation.GRAB_LAND, ageInTicks, 1.0F);
      if (entity.getAttackState() != 10
         && entity.getAttackState() != 11
         && entity.getAttackState() != 12
         && entity.getAttackState() != 13
         && entity.getAttackState() != 14
         && entity.getAttackState() != 18
         && entity.getAttackState() != 29
         && entity.getAttackState() != 33
         && entity.getAttackState() != 32
         && entity.getAttackState() != 31
         && limbSwing != 0.0F) {
         this.animateWalk(Maledictus_Animation.WALK, limbSwing, limbSwingAmount, 1.0F, 4.0F);
      }

      this.right_mace.f_104207_ = entity.getWeapon() == 0;
      this.left_mace.f_104207_ = entity.getWeapon() == 0;
      this.bow.f_104207_ = entity.getWeapon() == 1;
      this.halberd.f_104207_ = entity.getWeapon() == 2;
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ += xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ += yRot * (float) (Math.PI / 180.0);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   public void translateToHand(PoseStack matrixStack, boolean right) {
      this.root.m_104299_(matrixStack);
      this.roots.m_104299_(matrixStack);
      this.berserker.m_104299_(matrixStack);
      this.pelvis.m_104299_(matrixStack);
      this.body.m_104299_(matrixStack);
      if (right) {
         this.right_shoulder.m_104299_(matrixStack);
         this.right_arm.m_104299_(matrixStack);
         this.right_front_arm.m_104299_(matrixStack);
         this.right_particle.m_104299_(matrixStack);
      } else {
         this.left_shoulder.m_104299_(matrixStack);
         this.left_arm.m_104299_(matrixStack);
         this.left_front_arm.m_104299_(matrixStack);
         this.left_particle.m_104299_(matrixStack);
      }
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
