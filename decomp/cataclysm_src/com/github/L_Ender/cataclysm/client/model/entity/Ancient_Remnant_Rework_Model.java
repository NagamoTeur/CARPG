package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Ancient_Remnant_Animation;
import com.github.L_Ender.cataclysm.client.animation.Ancient_Remnant_Power_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.lionfishapi.server.animation.LegSolver;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
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

public class Ancient_Remnant_Rework_Model extends HierarchicalModel<Ancient_Remnant_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart mid_pivot;
   private final ModelPart pelvis;
   private final ModelPart left_long_bone;
   private final ModelPart right_long_bone;
   private final ModelPart spine_sail2;
   private final ModelPart left_bone;
   private final ModelPart right_bone;
   private final ModelPart left_big_bone;
   private final ModelPart right_big_bone;
   private final ModelPart tail1;
   private final ModelPart tail2;
   private final ModelPart tail3;
   private final ModelPart tail4;
   private final ModelPart spine1;
   private final ModelPart spine2;
   private final ModelPart spine_sail1;
   private final ModelPart right_shoulder;
   private final ModelPart left_shoulder;
   private final ModelPart neck1;
   private final ModelPart neck2;
   private final ModelPart head;
   private final ModelPart jaw;
   private final ModelPart crown;
   private final ModelPart under_crown;
   private final ModelPart right_crown1;
   private final ModelPart right_crown2;
   private final ModelPart left_crown;
   private final ModelPart left_crown2;
   private final ModelPart snake;
   private final ModelPart upper_crown;
   private final ModelPart desert_necklace;
   private final ModelPart chain1;
   private final ModelPart chain2;
   private final ModelPart chain3;
   private final ModelPart chain4;
   private final ModelPart chain5;
   private final ModelPart desert_eye;
   private final ModelPart eye;
   private final ModelPart left_arm;
   private final ModelPart left_front_arm;
   private final ModelPart left_hand;
   private final ModelPart left_finger3;
   private final ModelPart left_finger1;
   private final ModelPart left_finger2;
   private final ModelPart right_arm;
   private final ModelPart right_front_arm;
   private final ModelPart right_hand;
   private final ModelPart right_finger1;
   private final ModelPart right_finger2;
   private final ModelPart right_finger3;
   private final ModelPart spine_deco;
   private final ModelPart legs;
   private final ModelPart left_leg;
   private final ModelPart left_deco1;
   private final ModelPart left_front_leg;
   private final ModelPart left_ankel_joint;
   private final ModelPart left_mini_bone;
   private final ModelPart left_deco2;
   private final ModelPart left_deco3;
   private final ModelPart left_ankel;
   private final ModelPart left_foot;
   private final ModelPart left_toe;
   private final ModelPart left_toe2;
   private final ModelPart left_toe3;
   private final ModelPart right_leg;
   private final ModelPart right_deco1;
   private final ModelPart right_front_leg;
   private final ModelPart right_ankel_joint;
   private final ModelPart right_mini_bone;
   private final ModelPart right_deco2;
   private final ModelPart right_deco3;
   private final ModelPart right_ankel;
   private final ModelPart right_foot;
   private final ModelPart right_toe;
   private final ModelPart right_toe2;
   private final ModelPart right_toe3;

   public Ancient_Remnant_Rework_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.mid_pivot = this.roots.m_171324_("mid_pivot");
      this.pelvis = this.mid_pivot.m_171324_("pelvis");
      this.left_long_bone = this.pelvis.m_171324_("left_long_bone");
      this.right_long_bone = this.pelvis.m_171324_("right_long_bone");
      this.spine_sail2 = this.pelvis.m_171324_("spine_sail2");
      this.left_bone = this.pelvis.m_171324_("left_bone");
      this.right_bone = this.pelvis.m_171324_("right_bone");
      this.left_big_bone = this.pelvis.m_171324_("left_big_bone");
      this.right_big_bone = this.pelvis.m_171324_("right_big_bone");
      this.tail1 = this.pelvis.m_171324_("tail1");
      this.tail2 = this.tail1.m_171324_("tail2");
      this.tail3 = this.tail2.m_171324_("tail3");
      this.tail4 = this.tail3.m_171324_("tail4");
      this.spine1 = this.pelvis.m_171324_("spine1");
      this.spine2 = this.spine1.m_171324_("spine2");
      this.spine_sail1 = this.spine2.m_171324_("spine_sail1");
      this.right_shoulder = this.spine2.m_171324_("right_shoulder");
      this.left_shoulder = this.spine2.m_171324_("left_shoulder");
      this.neck1 = this.spine2.m_171324_("neck1");
      this.neck2 = this.neck1.m_171324_("neck2");
      this.head = this.neck2.m_171324_("head");
      this.jaw = this.head.m_171324_("jaw");
      this.crown = this.head.m_171324_("crown");
      this.under_crown = this.crown.m_171324_("under_crown");
      this.right_crown1 = this.under_crown.m_171324_("right_crown1");
      this.right_crown2 = this.right_crown1.m_171324_("right_crown2");
      this.left_crown = this.under_crown.m_171324_("left_crown");
      this.left_crown2 = this.left_crown.m_171324_("left_crown2");
      this.snake = this.crown.m_171324_("snake");
      this.upper_crown = this.crown.m_171324_("upper_crown");
      this.desert_necklace = this.neck2.m_171324_("desert_necklace");
      this.chain1 = this.desert_necklace.m_171324_("chain1");
      this.chain2 = this.chain1.m_171324_("chain2");
      this.chain3 = this.chain2.m_171324_("chain3");
      this.chain4 = this.chain3.m_171324_("chain4");
      this.chain5 = this.chain4.m_171324_("chain5");
      this.desert_eye = this.chain5.m_171324_("desert_eye");
      this.eye = this.desert_eye.m_171324_("eye");
      this.left_arm = this.spine2.m_171324_("left_arm");
      this.left_front_arm = this.left_arm.m_171324_("left_front_arm");
      this.left_hand = this.left_front_arm.m_171324_("left_hand");
      this.left_finger3 = this.left_hand.m_171324_("left_finger3");
      this.left_finger1 = this.left_hand.m_171324_("left_finger1");
      this.left_finger2 = this.left_hand.m_171324_("left_finger2");
      this.right_arm = this.spine2.m_171324_("right_arm");
      this.right_front_arm = this.right_arm.m_171324_("right_front_arm");
      this.right_hand = this.right_front_arm.m_171324_("right_hand");
      this.right_finger1 = this.right_hand.m_171324_("right_finger1");
      this.right_finger2 = this.right_hand.m_171324_("right_finger2");
      this.right_finger3 = this.right_hand.m_171324_("right_finger3");
      this.spine_deco = this.spine2.m_171324_("spine_deco");
      this.legs = this.mid_pivot.m_171324_("legs");
      this.left_leg = this.legs.m_171324_("left_leg");
      this.left_deco1 = this.left_leg.m_171324_("left_deco1");
      this.left_front_leg = this.left_leg.m_171324_("left_front_leg");
      this.left_ankel_joint = this.left_front_leg.m_171324_("left_ankel_joint");
      this.left_mini_bone = this.left_ankel_joint.m_171324_("left_mini_bone");
      this.left_deco2 = this.left_ankel_joint.m_171324_("left_deco2");
      this.left_deco3 = this.left_ankel_joint.m_171324_("left_deco3");
      this.left_ankel = this.left_ankel_joint.m_171324_("left_ankel");
      this.left_foot = this.left_ankel_joint.m_171324_("left_foot");
      this.left_toe = this.left_foot.m_171324_("left_toe");
      this.left_toe2 = this.left_foot.m_171324_("left_toe2");
      this.left_toe3 = this.left_foot.m_171324_("left_toe3");
      this.right_leg = this.legs.m_171324_("right_leg");
      this.right_deco1 = this.right_leg.m_171324_("right_deco1");
      this.right_front_leg = this.right_leg.m_171324_("right_front_leg");
      this.right_ankel_joint = this.right_front_leg.m_171324_("right_ankel_joint");
      this.right_mini_bone = this.right_ankel_joint.m_171324_("right_mini_bone");
      this.right_deco2 = this.right_ankel_joint.m_171324_("right_deco2");
      this.right_deco3 = this.right_ankel_joint.m_171324_("right_deco3");
      this.right_ankel = this.right_ankel_joint.m_171324_("right_ankel");
      this.right_foot = this.right_ankel_joint.m_171324_("right_foot");
      this.right_toe = this.right_foot.m_171324_("right_toe");
      this.right_toe2 = this.right_foot.m_171324_("right_toe2");
      this.right_toe3 = this.right_foot.m_171324_("right_toe3");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition mid_pivot = roots.m_171599_("mid_pivot", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -69.0F, -10.0F));
      PartDefinition pelvis = mid_pivot.m_171599_(
         "pelvis",
         CubeListBuilder.m_171558_().m_171514_(111, 42).m_171488_(-5.0F, -4.2211F, -9.2432F, 10.0F, 12.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 2.0F, -0.2182F, 0.0F, 0.0F)
      );
      PartDefinition left_long_bone = pelvis.m_171599_(
         "left_long_bone",
         CubeListBuilder.m_171558_().m_171514_(50, 0).m_171488_(0.0F, -4.0F, 6.0F, 5.0F, 28.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.5F, -0.2211F, -8.2432F, -0.4185F, 0.1274F, 0.2783F)
      );
      PartDefinition right_long_bone = pelvis.m_171599_(
         "right_long_bone",
         CubeListBuilder.m_171558_().m_171514_(50, 0).m_171480_().m_171488_(-5.0F, -4.0F, 6.0F, 5.0F, 28.0F, 7.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-6.5F, -0.2211F, -8.2432F, -0.4185F, -0.1274F, -0.2783F)
      );
      PartDefinition spine_sail2 = pelvis.m_171599_(
         "spine_sail2",
         CubeListBuilder.m_171558_().m_171514_(65, 50).m_171488_(0.0F, -28.916F, -25.98F, 0.0F, 30.0F, 32.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, -0.2211F, 12.7568F, 0.1745F, 0.0F, 0.0F)
      );
      PartDefinition left_bone = pelvis.m_171599_(
         "left_bone",
         CubeListBuilder.m_171558_().m_171514_(194, 215).m_171488_(1.0F, 3.0F, 0.0F, 7.0F, 33.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.5F, -0.2211F, -8.2432F, 0.7811F, -0.0924F, 0.0928F)
      );
      PartDefinition right_bone = pelvis.m_171599_(
         "right_bone",
         CubeListBuilder.m_171558_()
            .m_171514_(194, 215)
            .m_171480_()
            .m_171488_(-8.0F, 3.0F, 0.0F, 7.0F, 33.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-3.5F, -0.2211F, -8.2432F, 0.7811F, 0.0924F, -0.0928F)
      );
      PartDefinition left_big_bone = pelvis.m_171599_(
         "left_big_bone",
         CubeListBuilder.m_171558_().m_171514_(112, 124).m_171488_(1.0F, -2.0F, -1.0F, 9.0F, 14.0F, 27.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -0.2211F, -8.2432F, 0.3186F, 0.1451F, -0.413F)
      );
      PartDefinition right_big_bone = pelvis.m_171599_(
         "right_big_bone",
         CubeListBuilder.m_171558_()
            .m_171514_(112, 124)
            .m_171480_()
            .m_171488_(-10.0F, -2.0F, -1.0F, 9.0F, 14.0F, 27.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -0.2211F, -8.2432F, 0.3186F, -0.1451F, 0.413F)
      );
      PartDefinition tail1 = pelvis.m_171599_(
         "tail1",
         CubeListBuilder.m_171558_()
            .m_171514_(98, 81)
            .m_171488_(-4.0F, -5.916F, 1.02F, 8.0F, 10.0F, 32.0F, new CubeDeformation(0.0F))
            .m_171514_(47, 117)
            .m_171488_(0.0F, -24.916F, -0.98F, 0.0F, 19.0F, 32.0F, new CubeDeformation(0.0F))
            .m_171514_(114, 197)
            .m_171488_(0.0F, 4.084F, 6.02F, 0.0F, 6.0F, 25.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 6.7789F, 10.7568F, 0.0436F, 0.0F, 0.0F)
      );
      PartDefinition tail2 = tail1.m_171599_(
         "tail2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 109)
            .m_171488_(-3.5F, -3.7067F, -0.3098F, 7.0F, 7.0F, 32.0F, new CubeDeformation(0.0F))
            .m_171514_(148, 47)
            .m_171488_(0.0F, 3.2933F, -0.3098F, 0.0F, 4.0F, 32.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 149)
            .m_171488_(0.0F, -6.7067F, -0.3098F, 0.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -2.8F, 32.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition tail3 = tail2.m_171599_(
         "tail3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-3.0F, -2.7686F, -0.1825F, 6.0F, 6.0F, 37.0F, new CubeDeformation(0.0F))
            .m_171514_(156, 13)
            .m_171488_(0.0F, -6.7686F, -0.1825F, 0.0F, 4.0F, 29.0F, new CubeDeformation(0.0F))
            .m_171514_(28, 198)
            .m_171488_(0.0F, 3.2314F, -0.1825F, 0.0F, 4.0F, 25.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.5762F, 31.5254F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition tail4 = tail3.m_171599_(
         "tail4",
         CubeListBuilder.m_171558_().m_171514_(50, 7).m_171488_(-3.0F, -2.8005F, 0.2055F, 5.0F, 5.0F, 37.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, 1.3649F, 34.6065F, -0.2618F, 0.0F, 0.0F)
      );
      PartDefinition spine1 = pelvis.m_171599_(
         "spine1",
         CubeListBuilder.m_171558_()
            .m_171514_(162, 143)
            .m_171488_(-4.5F, -5.0F, -19.0F, 9.0F, 8.0F, 23.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 185)
            .m_171488_(3.0F, -3.0F, -19.0F, 11.0F, 18.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(145, 175)
            .m_171488_(-14.0F, -3.0F, -19.0F, 11.0F, 18.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 5.0F, -5.0F)
      );
      PartDefinition spine2 = spine1.m_171599_(
         "spine2",
         CubeListBuilder.m_171558_()
            .m_171514_(158, 101)
            .m_171488_(-4.5F, -5.0F, -23.0F, 9.0F, 8.0F, 23.0F, new CubeDeformation(0.0F))
            .m_171514_(200, 73)
            .m_171488_(3.0F, -3.0F, -18.0F, 10.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(88, 190)
            .m_171488_(-13.0F, -3.0F, -18.0F, 10.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(128, 229)
            .m_171488_(1.8F, -7.1199F, -17.5887F, 4.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(93, 229)
            .m_171488_(-5.8F, -7.1199F, -17.5887F, 4.0F, 5.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, -19.0F, 0.3491F, 0.0F, 0.0F)
      );
      PartDefinition spine_sail1 = spine2.m_171599_(
         "spine_sail1",
         CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(0.0F, -18.916F, -52.98F, 0.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -5.2211F, 36.7568F, -0.0436F, 0.0F, 0.0F)
      );
      PartDefinition right_shoulder = spine2.m_171599_(
         "right_shoulder",
         CubeListBuilder.m_171558_()
            .m_171514_(176, 187)
            .m_171480_()
            .m_171488_(-23.0F, -3.0F, -17.0F, 5.0F, 5.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.5314F, -0.5844F, -0.3136F)
      );
      PartDefinition left_shoulder = spine2.m_171599_(
         "left_shoulder",
         CubeListBuilder.m_171558_().m_171514_(176, 187).m_171488_(18.0F, -3.0F, -17.0F, 5.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, 0.5314F, 0.5844F, 0.3136F)
      );
      PartDefinition neck1 = spine2.m_171599_(
         "neck1",
         CubeListBuilder.m_171558_()
            .m_171514_(204, 137)
            .m_171488_(-3.5F, -4.0937F, -15.5774F, 8.0F, 9.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171514_(186, 0)
            .m_171488_(-3.5F, -4.0937F, -17.5774F, 8.0F, 18.0F, 16.0F, new CubeDeformation(0.7F))
            .m_171514_(235, 0)
            .m_171488_(0.5F, -10.0937F, -15.5774F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.5F, -1.0F, -20.0F, -0.4363F, 0.0F, 0.0F)
      );
      PartDefinition neck2 = neck1.m_171599_(
         "neck2",
         CubeListBuilder.m_171558_()
            .m_171514_(217, 35)
            .m_171488_(-3.0F, -4.0038F, -10.0872F, 7.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 157)
            .m_171488_(0.5F, -10.0038F, -10.0872F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -0.0937F, -17.5774F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition head = neck2.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(80, 124)
            .m_171488_(-6.0F, -10.0F, -14.0F, 13.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(209, 175)
            .m_171488_(-6.0F, -9.0F, -14.0F, 13.0F, 2.0F, 14.0F, new CubeDeformation(0.5F))
            .m_171514_(44, 169)
            .m_171488_(-3.5F, -7.0F, -35.0F, 8.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 222)
            .m_171488_(-3.5F, -7.0F, -20.0F, 8.0F, 32.0F, 6.0F, new CubeDeformation(0.5F))
            .m_171514_(33, 50)
            .m_171488_(-3.5F, 0.0F, -35.0F, 8.0F, 4.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, -8.0F, 0.3054F, 0.0F, 0.0F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_()
            .m_171514_(29, 228)
            .m_171488_(-4.0F, -2.0F, -6.0F, 9.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
            .m_171514_(181, 47)
            .m_171488_(-3.0F, 0.0F, -27.0F, 7.0F, 4.0F, 21.0F, new CubeDeformation(0.0F))
            .m_171514_(216, 198)
            .m_171488_(-3.0F, 4.0F, -27.0F, 7.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
            .m_171514_(202, 112)
            .m_171488_(-2.5F, -3.0F, -27.0F, 6.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 2.0F, -7.0F)
      );
      PartDefinition crown = head.m_171599_("crown", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition under_crown = crown.m_171599_(
         "under_crown",
         CubeListBuilder.m_171558_().m_171514_(223, 218).m_171488_(-7.5F, 0.0F, -4.0F, 15.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, -7.9063F, 1.5774F, -0.3054F, 0.0F, 0.0F)
      );
      PartDefinition right_crown1 = under_crown.m_171599_(
         "right_crown1",
         CubeListBuilder.m_171558_()
            .m_171514_(13, 149)
            .m_171488_(-5.0F, -2.0F, 0.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(23, 0)
            .m_171488_(-5.0F, -4.0F, 1.5F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 19)
            .m_171488_(1.0F, 0.0F, 1.5F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(158, 133)
            .m_171488_(-5.0F, 6.0F, 0.0F, 9.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(80, 113)
            .m_171488_(-5.0F, 15.0F, 0.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 0)
            .m_171488_(0.0F, 15.0F, 1.5F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(12.5F, 2.0F, -7.0F)
      );
      PartDefinition right_crown2 = right_crown1.m_171599_(
         "right_crown2",
         CubeListBuilder.m_171558_().m_171514_(84, 50).m_171488_(-1.5F, 0.0F, -3.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.5F, 19.0F, 3.0F, -0.2618F, 0.0F, 0.0F)
      );
      PartDefinition left_crown = under_crown.m_171599_(
         "left_crown",
         CubeListBuilder.m_171558_()
            .m_171514_(13, 149)
            .m_171480_()
            .m_171488_(-1.0F, -2.0F, 0.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(23, 0)
            .m_171480_()
            .m_171488_(1.0F, -4.0F, 1.5F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(29, 19)
            .m_171480_()
            .m_171488_(-3.0F, 0.0F, 1.5F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(158, 133)
            .m_171480_()
            .m_171488_(-4.0F, 6.0F, 0.0F, 9.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(80, 113)
            .m_171480_()
            .m_171488_(0.0F, 15.0F, 0.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 0)
            .m_171480_()
            .m_171488_(-2.0F, 15.0F, 1.5F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(-12.5F, 2.0F, -7.0F)
      );
      PartDefinition left_crown2 = left_crown.m_171599_(
         "left_crown2",
         CubeListBuilder.m_171558_().m_171514_(84, 50).m_171480_().m_171488_(-1.5F, 0.0F, -3.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(3.5F, 19.0F, 3.0F, -0.2618F, 0.0F, 0.0F)
      );
      PartDefinition snake = crown.m_171599_(
         "snake",
         CubeListBuilder.m_171558_()
            .m_171514_(172, 84)
            .m_171488_(0.0F, -83.0937F, -99.5774F, 0.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 10)
            .m_171480_()
            .m_171488_(-4.5F, -81.5937F, -93.5774F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(29, 10)
            .m_171488_(1.5F, -81.5937F, -93.5774F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 32)
            .m_171488_(1.0F, -80.0937F, -96.5774F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.5F))
            .m_171514_(71, 50)
            .m_171488_(-1.0F, -81.0937F, -94.5774F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.5F))
            .m_171514_(29, 26)
            .m_171488_(-1.5F, -77.5937F, -100.0774F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(26, 32)
            .m_171488_(-2.0F, -80.0937F, -96.5774F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.5F))
            .m_171514_(98, 0)
            .m_171488_(-1.0F, -81.0937F, -99.5774F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.m_171419_(0.5F, 66.0937F, 77.5774F)
      );
      PartDefinition upper_crown = crown.m_171599_(
         "upper_crown",
         CubeListBuilder.m_171558_().m_171514_(95, 166).m_171488_(-7.0F, -6.0F, -17.0F, 15.0F, 6.0F, 17.0F, new CubeDeformation(0.01F)),
         PartPose.m_171423_(0.0F, -7.9063F, 1.5774F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition desert_necklace = neck2.m_171599_(
         "desert_necklace",
         CubeListBuilder.m_171558_().m_171514_(82, 169).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.5F, -4.9063F, -0.9226F, 0.2182F, 0.0F, 0.0F)
      );
      PartDefinition chain1 = desert_necklace.m_171599_(
         "chain1",
         CubeListBuilder.m_171558_().m_171514_(147, 104).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 4.0F, 0.0F)
      );
      PartDefinition chain2 = chain1.m_171599_(
         "chain2",
         CubeListBuilder.m_171558_().m_171514_(147, 104).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 5.0F, 0.0F)
      );
      PartDefinition chain3 = chain2.m_171599_(
         "chain3",
         CubeListBuilder.m_171558_().m_171514_(147, 104).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 5.0F, 0.0F)
      );
      PartDefinition chain4 = chain3.m_171599_(
         "chain4",
         CubeListBuilder.m_171558_().m_171514_(147, 104).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 5.0F, 0.0F)
      );
      PartDefinition chain5 = chain4.m_171599_(
         "chain5",
         CubeListBuilder.m_171558_().m_171514_(147, 104).m_171488_(-4.0F, 0.0F, -1.5F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 5.0F, 0.0F)
      );
      PartDefinition desert_eye = chain5.m_171599_(
         "desert_eye",
         CubeListBuilder.m_171558_()
            .m_171514_(167, 0)
            .m_171480_()
            .m_171488_(-12.0F, -7.1811F, 0.2836F, 11.0F, 11.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(167, 0)
            .m_171488_(1.0F, -7.1811F, 0.2836F, 11.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 8.7811F, -0.2836F)
      );
      PartDefinition eye = desert_eye.m_171599_(
         "eye",
         CubeListBuilder.m_171558_().m_171514_(98, 50).m_171488_(-5.0F, -5.0F, -1.0F, 10.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -0.7811F, 0.2836F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_arm = spine2.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(47, 109)
            .m_171488_(-6.0F, -4.0637F, -3.0436F, 6.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 109)
            .m_171488_(-6.0F, -4.0637F, -3.0436F, 6.0F, 20.0F, 5.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(13.2F, 10.0F, -17.0F, 0.3491F, 0.0F, 0.0F)
      );
      PartDefinition left_front_arm = left_arm.m_171599_(
         "left_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(156, 47)
            .m_171488_(-3.0F, -0.1465F, -2.2077F, 6.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 169)
            .m_171488_(-3.0F, 2.8535F, -2.2077F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(-4.0F, 15.2977F, -1.9036F, -0.829F, 0.0F, 0.0F)
      );
      PartDefinition left_hand = left_front_arm.m_171599_(
         "left_hand", CubeListBuilder.m_171558_(), PartPose.m_171423_(1.0F, 10.1551F, -0.5133F, 1.6144F, 0.0F, 0.0F)
      );
      PartDefinition left_finger3 = left_hand.m_171599_(
         "left_finger3",
         CubeListBuilder.m_171558_().m_171514_(0, 149).m_171488_(-0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(1.0F, 0.0049F, 0.3079F, -0.7216F, -0.2324F, 0.2F)
      );
      PartDefinition left_finger1 = left_hand.m_171599_(
         "left_finger1",
         CubeListBuilder.m_171558_().m_171514_(147, 84).m_171488_(0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.0F, 0.0049F, 0.3079F, -0.7216F, 0.2324F, -0.2F)
      );
      PartDefinition left_finger2 = left_hand.m_171599_(
         "left_finger2",
         CubeListBuilder.m_171558_().m_171514_(0, 149).m_171488_(-0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, 0.0049F, 0.3079F, -0.7258F, 0.0F, 0.0F)
      );
      PartDefinition right_arm = spine2.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(47, 109)
            .m_171480_()
            .m_171488_(0.0F, -4.0637F, -3.0436F, 6.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-13.2F, 10.0F, -17.0F, 0.3491F, 0.0F, 0.0F)
      );
      PartDefinition right_front_arm = right_arm.m_171599_(
         "right_front_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(156, 47)
            .m_171480_()
            .m_171488_(-3.0F, -0.1465F, -2.2077F, 6.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 169)
            .m_171480_()
            .m_171488_(-3.0F, 2.8535F, -2.2077F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.5F))
            .m_171555_(false),
         PartPose.m_171423_(4.0F, 15.2977F, -1.9036F, -0.829F, 0.0F, 0.0F)
      );
      PartDefinition right_hand = right_front_arm.m_171599_(
         "right_hand", CubeListBuilder.m_171558_(), PartPose.m_171423_(-1.0F, 10.1551F, -0.5133F, 1.6144F, 0.0F, 0.0F)
      );
      PartDefinition right_finger1 = right_hand.m_171599_(
         "right_finger1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 149)
            .m_171480_()
            .m_171488_(0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-1.0F, 0.0049F, 0.3079F, -0.7216F, 0.2324F, -0.2F)
      );
      PartDefinition right_finger2 = right_hand.m_171599_(
         "right_finger2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 149)
            .m_171480_()
            .m_171488_(0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(1.0F, 0.0049F, 0.3079F, -0.7258F, 0.0F, 0.0F)
      );
      PartDefinition right_finger3 = right_hand.m_171599_(
         "right_finger3",
         CubeListBuilder.m_171558_()
            .m_171514_(147, 84)
            .m_171480_()
            .m_171488_(-0.2215F, -3.2106F, -9.8849F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(3.0F, 0.0049F, 0.3079F, -0.7216F, -0.2324F, 0.2F)
      );
      PartDefinition spine_deco = spine2.m_171599_(
         "spine_deco",
         CubeListBuilder.m_171558_().m_171514_(98, 0).m_171488_(-13.2F, 0.0F, -16.5F, 26.0F, 25.0F, 16.0F, new CubeDeformation(0.5F)),
         PartPose.m_171423_(0.0F, -7.3F, -6.7F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition legs = mid_pivot.m_171599_("legs", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 4.0F, 7.0F));
      PartDefinition left_leg = legs.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(165, 215).m_171488_(-6.0F, -2.0261F, -4.1809F, 8.0F, 34.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(15.0F, 0.0F, 6.0F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition left_deco1 = left_leg.m_171599_(
         "left_deco1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-3.0F, -8.0F, 1.0F, 8.0F, 28.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.0F, 10.9739F, -8.1809F, 0.0959F, -0.4349F, -0.0329F)
      );
      PartDefinition left_front_leg = left_leg.m_171599_(
         "left_front_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 44).m_171488_(-5.0F, -2.5649F, -1.6913F, 8.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, 32.9739F, -2.1809F, 1.0908F, 0.0F, 0.0F)
      );
      PartDefinition left_ankel_joint = left_front_leg.m_171599_("left_ankel_joint", CubeListBuilder.m_171558_(), PartPose.m_171419_(-1.0F, 20.6148F, 1.661F));
      PartDefinition left_mini_bone = left_ankel_joint.m_171599_(
         "left_mini_bone",
         CubeListBuilder.m_171558_().m_171514_(29, 0).m_171488_(0.0209F, -3.1113F, 0.0913F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-1.0F, 11.0F, -6.0F, -0.3928F, -0.0035F, 1.0E-4F)
      );
      PartDefinition left_deco2 = left_ankel_joint.m_171599_(
         "left_deco2",
         CubeListBuilder.m_171558_().m_171514_(61, 210).m_171488_(-2.0F, -2.7487F, 0.1981F, 4.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 6.0F, -4.9602F, 0.5236F, 0.0F, 0.0F)
      );
      PartDefinition left_deco3 = left_ankel_joint.m_171599_(
         "left_deco3",
         CubeListBuilder.m_171558_().m_171514_(33, 45).m_171488_(-0.2F, -11.7017F, -4.0722F, 4.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, 9.0F, -7.0F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition left_ankel = left_ankel_joint.m_171599_(
         "left_ankel",
         CubeListBuilder.m_171558_().m_171514_(54, 198).m_171488_(-3.0F, -0.2489F, -2.0393F, 6.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.0F, 0.0F, -0.5672F, 0.0F, 0.0F)
      );
      PartDefinition left_foot = left_ankel_joint.m_171599_("left_foot", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 12.0F, -8.0F));
      PartDefinition left_toe = left_foot.m_171599_(
         "left_toe",
         CubeListBuilder.m_171558_().m_171514_(71, 50).m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -1.8218F, -0.2377F, -0.6981F, 0.0F, 0.0F)
      );
      PartDefinition left_toe2 = left_foot.m_171599_(
         "left_toe2",
         CubeListBuilder.m_171558_().m_171514_(71, 50).m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-2.0F, -1.8218F, -0.2377F, -0.7216F, 0.2324F, -0.2F)
      );
      PartDefinition left_toe3 = left_foot.m_171599_(
         "left_toe3",
         CubeListBuilder.m_171558_().m_171514_(71, 50).m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(2.0F, -1.8218F, -0.2377F, -0.7216F, -0.2324F, 0.2F)
      );
      PartDefinition right_leg = legs.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(165, 215)
            .m_171480_()
            .m_171488_(-2.0F, -2.0261F, -4.1809F, 8.0F, 34.0F, 6.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-15.0F, 0.0F, 6.0F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition right_deco1 = right_leg.m_171599_(
         "right_deco1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-5.0F, -8.0F, 1.0F, 8.0F, 28.0F, 6.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(-2.0F, 10.9739F, -8.1809F, 0.0959F, 0.4349F, 0.0329F)
      );
      PartDefinition right_front_leg = right_leg.m_171599_(
         "right_front_leg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 44)
            .m_171480_()
            .m_171488_(-3.0F, -2.5649F, -1.6913F, 8.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.0F, 32.9739F, -2.1809F, 1.0908F, 0.0F, 0.0F)
      );
      PartDefinition right_ankel_joint = right_front_leg.m_171599_("right_ankel_joint", CubeListBuilder.m_171558_(), PartPose.m_171419_(1.0F, 20.6148F, 1.661F));
      PartDefinition right_mini_bone = right_ankel_joint.m_171599_(
         "right_mini_bone",
         CubeListBuilder.m_171558_()
            .m_171514_(29, 0)
            .m_171480_()
            .m_171488_(-0.0209F, -3.1113F, 0.0913F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(1.0F, 11.0F, -6.0F, -0.3928F, 0.0035F, -1.0E-4F)
      );
      PartDefinition right_deco2 = right_ankel_joint.m_171599_(
         "right_deco2",
         CubeListBuilder.m_171558_()
            .m_171514_(61, 210)
            .m_171480_()
            .m_171488_(-2.0F, -2.7487F, 0.1981F, 4.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 6.0F, -4.9602F, 0.5236F, 0.0F, 0.0F)
      );
      PartDefinition right_deco3 = right_ankel_joint.m_171599_(
         "right_deco3",
         CubeListBuilder.m_171558_()
            .m_171514_(33, 45)
            .m_171480_()
            .m_171488_(-3.8F, -11.7017F, -4.0722F, 4.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.0F, 9.0F, -7.0F, -0.3927F, 0.0F, 0.0F)
      );
      PartDefinition right_ankel = right_ankel_joint.m_171599_(
         "right_ankel",
         CubeListBuilder.m_171558_()
            .m_171514_(54, 198)
            .m_171480_()
            .m_171488_(-3.0F, -0.2489F, -2.0393F, 6.0F, 15.0F, 5.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -1.0F, 0.0F, -0.5672F, 0.0F, 0.0F)
      );
      PartDefinition right_foot = right_ankel_joint.m_171599_("right_foot", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 12.0F, -8.0F));
      PartDefinition right_toe = right_foot.m_171599_(
         "right_toe",
         CubeListBuilder.m_171558_()
            .m_171514_(71, 50)
            .m_171480_()
            .m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -1.8218F, -0.2377F, -0.6981F, 0.0F, 0.0F)
      );
      PartDefinition right_toe2 = right_foot.m_171599_(
         "right_toe2",
         CubeListBuilder.m_171558_()
            .m_171514_(71, 50)
            .m_171480_()
            .m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(2.0F, -1.8218F, -0.2377F, -0.7216F, -0.2324F, 0.2F)
      );
      PartDefinition right_toe3 = right_foot.m_171599_(
         "right_toe3",
         CubeListBuilder.m_171558_()
            .m_171514_(71, 50)
            .m_171480_()
            .m_171488_(0.0F, 0.2465F, -9.1823F, 0.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-2.0F, -1.8218F, -0.2377F, -0.7216F, 0.2324F, -0.2F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 512, 512);
   }

   public void setupAnim(Ancient_Remnant_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entityIn.getAttackState() != 10 && entityIn.getAttackState() != 11 && entityIn.getAttackState() != 12 && !entityIn.isSleep()) {
         this.animateWalk(Ancient_Remnant_Animation.WALK, limbSwing, limbSwingAmount, 1.0F, 4.0F);
      }

      this.m_233385_(entityIn.getAnimationState("idle"), Ancient_Remnant_Animation.IDLE, ageInTicks, entityIn.getNecklace() ? 1.0F : 0.15F);
      this.m_233385_(entityIn.getAnimationState("death"), Ancient_Remnant_Animation.DEATH, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("right_bite"), Ancient_Remnant_Animation.RIGHT_BITE, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("sandstorm_roar"), Ancient_Remnant_Animation.SAND_STORM_ROAR, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("phase_roar"), Ancient_Remnant_Animation.PHASE_ROAR, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("charge"), Ancient_Remnant_Animation.CHARGE, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("sleep"), Ancient_Remnant_Animation.SLEEP, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("awaken"), Ancient_Remnant_Animation.AWAKEN, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("left_double_stomp"), Ancient_Remnant_Power_Animation.DOUBLE_STOMP2, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("right_double_stomp"), Ancient_Remnant_Power_Animation.DOUBLE_STOMP1, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("ground_tail"), Ancient_Remnant_Power_Animation.GROUND_TAIL, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("tail_swing"), Ancient_Remnant_Power_Animation.TAIL_SWING, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("monolith"), Ancient_Remnant_Power_Animation.MONOLITH, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("right_stomp"), Ancient_Remnant_Animation.STOMP1, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("left_stomp"), Ancient_Remnant_Animation.STOMP2, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("charge_prepare"), Ancient_Remnant_Animation.CHARGE_PREPARE, ageInTicks, 1.0F);
      this.m_233385_(entityIn.getAnimationState("charge_stun"), Ancient_Remnant_Animation.CHARGE_STUN, ageInTicks, 1.0F);
      float partialTick = Minecraft.m_91087_().m_91296_();
      if (!entityIn.isSleep()) {
         this.articulateLegs(entityIn.legSolver, partialTick);
      }

      this.desert_necklace.f_104207_ = entityIn.getNecklace();
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ += xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ = yRot * (float) (Math.PI / 180.0);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void articulateLegs(LegSolver legs, float partialTick) {
      float heightBackLeft = legs.legs[0].getHeight(partialTick);
      float heightBackRight = legs.legs[1].getHeight(partialTick);
      float max = (1.0F - smin(1.0F - heightBackLeft, 1.0F - heightBackRight, 0.1F)) * 0.8F;
      this.roots.f_104201_ += max * 16.0F;
      this.right_leg.f_104201_ += (heightBackRight - max) * 16.0F;
      this.left_leg.f_104201_ += (heightBackLeft - max) * 16.0F;
   }

   private static float smin(float a, float b, float k) {
      float h = Math.max(k - Math.abs(a - b), 0.0F) / k;
      return Math.min(a, b) - h * h * k * 0.25F;
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
