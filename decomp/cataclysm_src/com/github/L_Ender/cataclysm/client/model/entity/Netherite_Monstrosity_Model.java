package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Netherite_Monstrosity_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
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

public class Netherite_Monstrosity_Model extends HierarchicalModel<Netherite_Monstrosity_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart lowerbody;
   private final ModelPart upperbody;
   private final ModelPart head;
   private final ModelPart horns;
   private final ModelPart jaw;
   private final ModelPart leftarmjoint;
   private final ModelPart leftarm;
   private final ModelPart leftarm2;
   private final ModelPart lefthand;
   private final ModelPart l_hand_blast_4;
   private final ModelPart l_hand_blast_3;
   private final ModelPart leftfinger1;
   private final ModelPart l_hand_blast_2;
   private final ModelPart leftfinger2;
   private final ModelPart l_hand_blast_1;
   private final ModelPart leftfinger3;
   private final ModelPart l_cannon;
   private final ModelPart l_core;
   private final ModelPart l_flame_2;
   private final ModelPart l_flame_1;
   private final ModelPart rightarmjoint;
   private final ModelPart rightarm;
   private final ModelPart rightarm2;
   private final ModelPart righthand;
   private final ModelPart r_hand_blast_4;
   private final ModelPart r_hand_blast_3;
   private final ModelPart rightfinger1;
   private final ModelPart r_hand_blast_2;
   private final ModelPart rightfinger2;
   private final ModelPart r_hand_blast_1;
   private final ModelPart rightfinger3;
   private final ModelPart r_cannon;
   private final ModelPart r_core;
   private final ModelPart r_flame_1;
   private final ModelPart r_flame_2;
   private final ModelPart rightleg;
   private final ModelPart leftleg;

   public Netherite_Monstrosity_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.lowerbody = this.roots.m_171324_("lowerbody");
      this.upperbody = this.lowerbody.m_171324_("upperbody");
      this.head = this.upperbody.m_171324_("head");
      this.horns = this.head.m_171324_("horns");
      this.jaw = this.head.m_171324_("jaw");
      this.leftarmjoint = this.upperbody.m_171324_("leftarmjoint");
      this.leftarm = this.leftarmjoint.m_171324_("leftarm");
      this.leftarm2 = this.leftarm.m_171324_("leftarm2");
      this.lefthand = this.leftarm2.m_171324_("lefthand");
      this.l_hand_blast_4 = this.lefthand.m_171324_("l_hand_blast_4");
      this.l_hand_blast_3 = this.lefthand.m_171324_("l_hand_blast_3");
      this.leftfinger1 = this.l_hand_blast_3.m_171324_("leftfinger1");
      this.l_hand_blast_2 = this.lefthand.m_171324_("l_hand_blast_2");
      this.leftfinger2 = this.l_hand_blast_2.m_171324_("leftfinger2");
      this.l_hand_blast_1 = this.lefthand.m_171324_("l_hand_blast_1");
      this.leftfinger3 = this.l_hand_blast_1.m_171324_("leftfinger3");
      this.l_cannon = this.lefthand.m_171324_("l_cannon");
      this.l_core = this.lefthand.m_171324_("l_core");
      this.l_flame_2 = this.l_core.m_171324_("l_flame_2");
      this.l_flame_1 = this.l_core.m_171324_("l_flame_1");
      this.rightarmjoint = this.upperbody.m_171324_("rightarmjoint");
      this.rightarm = this.rightarmjoint.m_171324_("rightarm");
      this.rightarm2 = this.rightarm.m_171324_("rightarm2");
      this.righthand = this.rightarm2.m_171324_("righthand");
      this.r_hand_blast_4 = this.righthand.m_171324_("r_hand_blast_4");
      this.r_hand_blast_3 = this.righthand.m_171324_("r_hand_blast_3");
      this.rightfinger1 = this.r_hand_blast_3.m_171324_("rightfinger1");
      this.r_hand_blast_2 = this.righthand.m_171324_("r_hand_blast_2");
      this.rightfinger2 = this.r_hand_blast_2.m_171324_("rightfinger2");
      this.r_hand_blast_1 = this.righthand.m_171324_("r_hand_blast_1");
      this.rightfinger3 = this.r_hand_blast_1.m_171324_("rightfinger3");
      this.r_cannon = this.righthand.m_171324_("r_cannon");
      this.r_core = this.righthand.m_171324_("r_core");
      this.r_flame_1 = this.r_core.m_171324_("r_flame_1");
      this.r_flame_2 = this.r_core.m_171324_("r_flame_2");
      this.rightleg = this.roots.m_171324_("rightleg");
      this.leftleg = this.roots.m_171324_("leftleg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition lowerbody = roots.m_171599_(
         "lowerbody",
         CubeListBuilder.m_171558_().m_171514_(175, 193).m_171488_(-14.0F, -11.0F, -10.5F, 28.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -24.0F, 2.0F)
      );
      PartDefinition upperbody = lowerbody.m_171599_(
         "upperbody",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-37.0F, -57.0F, -15.0F, 74.0F, 57.0F, 30.0F, new CubeDeformation(0.0F))
            .m_171514_(209, 226)
            .m_171488_(-14.0F, -50.0F, 15.0F, 28.0F, 16.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -11.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition head = upperbody.m_171599_(
         "head",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 139)
            .m_171488_(-14.0F, -18.0F, -20.5F, 28.0F, 31.0F, 22.0F, new CubeDeformation(0.0F))
            .m_171514_(246, 112)
            .m_171488_(-34.0F, -12.5F, -16.0F, 20.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(246, 112)
            .m_171480_()
            .m_171488_(14.0F, -12.5F, -16.0F, 20.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(253, 184)
            .m_171488_(-34.0F, -27.5F, -16.0F, 8.0F, 15.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(169, 171)
            .m_171488_(26.0F, -15.5F, -16.0F, 8.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
            .m_171514_(12, 0)
            .m_171488_(-2.5F, -2.0F, -20.7F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 5)
            .m_171488_(-14.25F, 1.5F, -20.7F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
            .m_171514_(17, 5)
            .m_171488_(10.25F, 1.5F, -20.7F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -33.0F, -16.5F)
      );
      PartDefinition horns = head.m_171599_("horns", CubeListBuilder.m_171558_(), PartPose.m_171423_(-4.5F, 47.0F, -3.5F, 1.0472F, 0.0F, 0.0F));
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_()
            .m_171514_(209, 2)
            .m_171488_(-13.5F, -10.0F, -21.9F, 27.0F, 16.0F, 21.0F, new CubeDeformation(0.0F))
            .m_171514_(305, 8)
            .m_171488_(-13.5F, 6.0F, -21.9F, 27.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(209, 40)
            .m_171488_(-13.5F, 3.0F, -21.9F, 27.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 11.0F, 1.5F, 0.1309F, 0.0F, 0.0F)
      );
      PartDefinition leftarmjoint = upperbody.m_171599_("leftarmjoint", CubeListBuilder.m_171558_(), PartPose.m_171419_(37.0F, -38.5F, -2.5F));
      PartDefinition leftarm = leftarmjoint.m_171599_(
         "leftarm",
         CubeListBuilder.m_171558_()
            .m_171514_(101, 163)
            .m_171488_(0.0F, -33.5F, -13.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 88)
            .m_171488_(0.0F, -10.5F, -13.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition leftarm2 = leftarm.m_171599_(
         "leftarm2",
         CubeListBuilder.m_171558_().m_171514_(132, 226).m_171488_(-11.0F, -4.5F, -8.0F, 22.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(18.0F, 12.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition lefthand = leftarm2.m_171599_(
         "lefthand",
         CubeListBuilder.m_171558_()
            .m_171514_(136, 264)
            .m_171480_()
            .m_171488_(-12.0F, -5.0F, -12.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 17.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition righthand_r1 = lefthand.m_171599_(
         "righthand_r1",
         CubeListBuilder.m_171558_()
            .m_171514_(102, 260)
            .m_171480_()
            .m_171488_(-2.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(74, 260)
            .m_171480_()
            .m_171488_(-19.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171555_(false)
            .m_171514_(88, 260)
            .m_171480_()
            .m_171488_(-19.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(61, 314)
            .m_171480_()
            .m_171488_(-16.5F, -4.5F, -2.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(60, 270)
            .m_171480_()
            .m_171488_(-16.5F, -13.5F, -2.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(5.0F, 13.5F, 9.5F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition lefthand_r1 = lefthand.m_171599_(
         "lefthand_r1",
         CubeListBuilder.m_171558_().m_171514_(88, 260).m_171488_(-103.0F, -3.0F, -10.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-110.0F, 3.0F, -8.5F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition righthand_r2 = lefthand.m_171599_(
         "righthand_r2",
         CubeListBuilder.m_171558_()
            .m_171514_(88, 260)
            .m_171480_()
            .m_171488_(-10.0F, -3.0F, -10.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 3.0F, -8.5F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition lefthand_r2 = lefthand.m_171599_(
         "lefthand_r2",
         CubeListBuilder.m_171558_().m_171514_(74, 260).m_171488_(-111.5F, -3.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(-118.5F, 3.0F, 0.0F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition righthand_r3 = lefthand.m_171599_(
         "righthand_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(74, 260)
            .m_171480_()
            .m_171488_(-10.0F, -3.0F, -2.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 3.0F, -0.5F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition righthand_r4 = lefthand.m_171599_(
         "righthand_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(74, 260)
            .m_171480_()
            .m_171488_(-18.5F, -3.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 3.0F, -8.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition l_hand_blast_4 = lefthand.m_171599_("l_hand_blast_4", CubeListBuilder.m_171558_(), PartPose.m_171419_(-10.0F, 0.0F, 10.0F));
      PartDefinition lefthand_r3 = l_hand_blast_4.m_171599_(
         "lefthand_r3",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 304)
            .m_171488_(-0.5F, -13.5F, -5.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 329)
            .m_171488_(2.5F, 1.5F, -2.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 269)
            .m_171488_(-5.5F, -13.5F, -10.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, 13.5F, -0.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition l_hand_blast_3 = lefthand.m_171599_("l_hand_blast_3", CubeListBuilder.m_171558_(), PartPose.m_171419_(10.0F, 0.0F, 10.0F));
      PartDefinition righthand_r5 = l_hand_blast_3.m_171599_(
         "righthand_r5",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 304)
            .m_171480_()
            .m_171488_(-9.5F, -13.5F, -5.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 329)
            .m_171480_()
            .m_171488_(-9.5F, 1.5F, -2.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 269)
            .m_171480_()
            .m_171488_(-9.5F, -13.5F, -10.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, 13.5F, -0.5F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition leftfinger1 = l_hand_blast_3.m_171599_(
         "leftfinger1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 20.0F, -4.0F)
      );
      PartDefinition l_hand_blast_2 = lefthand.m_171599_(
         "l_hand_blast_2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 269)
            .m_171480_()
            .m_171488_(-10.0F, 0.0F, -5.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 329)
            .m_171480_()
            .m_171488_(-10.0F, 15.0F, 3.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 304)
            .m_171480_()
            .m_171488_(-10.0F, 0.0F, 0.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(10.0F, 0.0F, -10.0F)
      );
      PartDefinition leftfinger2 = l_hand_blast_2.m_171599_(
         "leftfinger2",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 20.0F, 2.0F)
      );
      PartDefinition l_hand_blast_1 = lefthand.m_171599_(
         "l_hand_blast_1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 269)
            .m_171488_(-5.0F, 0.0F, -5.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 329)
            .m_171488_(3.0F, 15.0F, 3.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 304)
            .m_171488_(0.0F, 0.0F, 0.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-10.0F, 0.0F, -10.0F)
      );
      PartDefinition leftfinger3 = l_hand_blast_1.m_171599_("leftfinger3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 20.0F, 8.5F));
      PartDefinition leftfinger3_r1 = leftfinger3.m_171599_(
         "leftfinger3_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.5F, -7.5F, -2.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 5.0F, 1.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition l_cannon = lefthand.m_171599_("l_cannon", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 13.75F, 0.0F));
      PartDefinition righthand_r6 = l_cannon.m_171599_(
         "righthand_r6",
         CubeListBuilder.m_171558_()
            .m_171514_(61, 294)
            .m_171480_()
            .m_171488_(-7.0F, 7.5F, -7.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(69, 331)
            .m_171480_()
            .m_171488_(-6.0F, 2.5F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -8.25F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition l_core = lefthand.m_171599_("l_core", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 13.0F, 0.0F));
      PartDefinition righthand_r7 = l_core.m_171599_(
         "righthand_r7",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 341)
            .m_171480_()
            .m_171488_(-4.0F, 6.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F))
            .m_171555_(false)
            .m_171514_(0, 357)
            .m_171480_()
            .m_171488_(-4.0F, 6.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, -10.5F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition l_flame_2 = l_core.m_171599_("l_flame_2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition righthand_r8 = l_flame_2.m_171599_(
         "righthand_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(-16, 373)
            .m_171480_()
            .m_171488_(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -3.0663F, -1.0163F, -2.2196F)
      );
      PartDefinition l_flame_1 = l_core.m_171599_("l_flame_1", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition righthand_r9 = l_flame_1.m_171599_(
         "righthand_r9",
         CubeListBuilder.m_171558_()
            .m_171514_(-16, 373)
            .m_171480_()
            .m_171488_(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -3.1416F, -1.1781F, 2.7489F)
      );
      PartDefinition rightarmjoint = upperbody.m_171599_("rightarmjoint", CubeListBuilder.m_171558_(), PartPose.m_171419_(-37.0F, -38.5F, -2.5F));
      PartDefinition rightarm = rightarmjoint.m_171599_(
         "rightarm",
         CubeListBuilder.m_171558_()
            .m_171514_(101, 163)
            .m_171480_()
            .m_171488_(-20.0F, -33.5F, -13.5F, 20.0F, 23.0F, 27.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 88)
            .m_171480_()
            .m_171488_(-37.0F, -10.5F, -13.5F, 37.0F, 23.0F, 27.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition rightarm2 = rightarm.m_171599_(
         "rightarm2",
         CubeListBuilder.m_171558_().m_171514_(132, 226).m_171488_(-11.0F, -4.5F, -8.0F, 22.0F, 22.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-19.0F, 12.0F, 0.0F, -0.1309F, 0.0F, 0.0F)
      );
      PartDefinition righthand = rightarm2.m_171599_(
         "righthand",
         CubeListBuilder.m_171558_().m_171514_(136, 264).m_171488_(-12.0F, -5.0F, -12.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 17.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition lefthand_r4 = righthand.m_171599_(
         "lefthand_r4",
         CubeListBuilder.m_171558_()
            .m_171514_(102, 260)
            .m_171488_(-0.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(74, 260)
            .m_171488_(16.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171514_(88, 260)
            .m_171488_(16.5F, -13.5F, 3.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(61, 314)
            .m_171488_(2.5F, -4.5F, -2.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(60, 270)
            .m_171488_(2.5F, -13.5F, -2.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.0F, 13.5F, 9.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition righthand_r10 = righthand.m_171599_(
         "righthand_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(88, 260)
            .m_171480_()
            .m_171488_(100.0F, -3.0F, -10.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(110.0F, 3.0F, -8.5F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition lefthand_r5 = righthand.m_171599_(
         "lefthand_r5",
         CubeListBuilder.m_171558_().m_171514_(88, 260).m_171488_(7.0F, -3.0F, -10.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 3.0F, -8.5F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition righthand_r11 = righthand.m_171599_(
         "righthand_r11",
         CubeListBuilder.m_171558_()
            .m_171514_(74, 260)
            .m_171480_()
            .m_171488_(108.5F, -3.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F))
            .m_171555_(false),
         PartPose.m_171423_(118.5F, 3.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
      );
      PartDefinition lefthand_r6 = righthand.m_171599_(
         "lefthand_r6",
         CubeListBuilder.m_171558_().m_171514_(74, 260).m_171488_(7.0F, -3.0F, -2.5F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(0.0F, 3.0F, -0.5F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition lefthand_r7 = righthand.m_171599_(
         "lefthand_r7",
         CubeListBuilder.m_171558_().m_171514_(74, 260).m_171488_(15.5F, -3.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(0.0F, 3.0F, -8.5F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition r_hand_blast_4 = righthand.m_171599_("r_hand_blast_4", CubeListBuilder.m_171558_(), PartPose.m_171419_(10.0F, 0.0F, 10.0F));
      PartDefinition righthand_r12 = r_hand_blast_4.m_171599_(
         "righthand_r12",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 304)
            .m_171480_()
            .m_171488_(-9.5F, -13.5F, -5.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 329)
            .m_171480_()
            .m_171488_(-9.5F, 1.5F, -2.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 269)
            .m_171480_()
            .m_171488_(-9.5F, -13.5F, -10.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(-5.0F, 13.5F, -0.5F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition r_hand_blast_3 = righthand.m_171599_("r_hand_blast_3", CubeListBuilder.m_171558_(), PartPose.m_171419_(-10.0F, 0.0F, 10.0F));
      PartDefinition lefthand_r8 = r_hand_blast_3.m_171599_(
         "lefthand_r8",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 304)
            .m_171488_(-0.5F, -13.5F, -5.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 329)
            .m_171488_(2.5F, 1.5F, -2.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 269)
            .m_171488_(-5.5F, -13.5F, -10.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(5.0F, 13.5F, -0.5F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition rightfinger1 = r_hand_blast_3.m_171599_(
         "rightfinger1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(0.0F, 20.0F, -4.0F)
      );
      PartDefinition r_hand_blast_2 = righthand.m_171599_(
         "r_hand_blast_2",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 269)
            .m_171488_(-5.0F, 0.0F, -5.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 329)
            .m_171488_(3.0F, 15.0F, 3.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 304)
            .m_171488_(0.0F, 0.0F, 0.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-10.0F, 0.0F, -10.0F)
      );
      PartDefinition rightfinger2 = r_hand_blast_2.m_171599_(
         "rightfinger2",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.5F, -2.5F, -1.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171419_(0.0F, 20.0F, 2.0F)
      );
      PartDefinition r_hand_blast_1 = righthand.m_171599_(
         "r_hand_blast_1",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 269)
            .m_171480_()
            .m_171488_(-10.0F, 0.0F, -5.0F, 15.0F, 20.0F, 15.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 329)
            .m_171480_()
            .m_171488_(-10.0F, 15.0F, 3.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171555_(false)
            .m_171514_(0, 304)
            .m_171480_()
            .m_171488_(-10.0F, 0.0F, 0.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171419_(10.0F, 0.0F, -10.0F)
      );
      PartDefinition rightfinger3 = r_hand_blast_1.m_171599_("rightfinger3", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 20.0F, 8.5F));
      PartDefinition rightfinger3_r1 = rightfinger3.m_171599_(
         "rightfinger3_r1",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171480_().m_171488_(-1.5F, -7.5F, -2.5F, 3.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).m_171555_(false),
         PartPose.m_171423_(0.0F, 5.0F, 1.0F, 0.0F, -3.1416F, 0.0F)
      );
      PartDefinition r_cannon = righthand.m_171599_("r_cannon", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 13.75F, 0.0F));
      PartDefinition lefthand_r9 = r_cannon.m_171599_(
         "lefthand_r9",
         CubeListBuilder.m_171558_()
            .m_171514_(61, 294)
            .m_171488_(-7.0F, 7.5F, -7.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
            .m_171514_(69, 331)
            .m_171488_(-6.0F, 2.5F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, -8.25F, 0.0F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition r_core = righthand.m_171599_("r_core", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 13.0F, 0.0F));
      PartDefinition lefthand_r10 = r_core.m_171599_(
         "lefthand_r10",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 341)
            .m_171488_(-4.0F, 6.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 357)
            .m_171488_(-4.0F, 6.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)),
         PartPose.m_171423_(0.0F, -10.5F, 0.0F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition r_flame_1 = r_core.m_171599_("r_flame_1", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition lefthand_r11 = r_flame_1.m_171599_(
         "lefthand_r11",
         CubeListBuilder.m_171558_().m_171514_(-16, 373).m_171488_(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -3.0663F, 1.0163F, 2.2196F)
      );
      PartDefinition r_flame_2 = r_core.m_171599_("r_flame_2", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition lefthand_r12 = r_flame_2.m_171599_(
         "lefthand_r12",
         CubeListBuilder.m_171558_().m_171514_(-16, 373).m_171488_(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.0F, -3.1416F, 1.1781F, -2.7489F)
      );
      PartDefinition rightleg = roots.m_171599_(
         "rightleg",
         CubeListBuilder.m_171558_().m_171514_(0, 193).m_171488_(-19.0F, -2.0F, -7.5F, 24.0F, 29.0F, 19.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-14.0F, -27.0F, 0.0F, 0.0F, 0.0873F, 0.0F)
      );
      PartDefinition leftleg = roots.m_171599_(
         "leftleg",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 193)
            .m_171480_()
            .m_171488_(-5.0F, -2.0F, -7.5F, 24.0F, 29.0F, 19.0F, new CubeDeformation(0.0F))
            .m_171555_(false),
         PartPose.m_171423_(14.0F, -27.0F, 0.0F, 0.0F, -0.0873F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 512, 512);
   }

   public void setupAnim(Netherite_Monstrosity_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entity.getAttackState() != 8 || entity.attackTicks <= 19 || entity.attackTicks >= 49) {
         this.animateWalk(Netherite_Monstrosity_Animation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
      }

      this.m_233385_(entity.getAnimationState("idle"), Netherite_Monstrosity_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("smash"), Netherite_Monstrosity_Animation.SMASH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sleep"), Netherite_Monstrosity_Animation.SLEEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("awake"), Netherite_Monstrosity_Animation.AWAKE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("phase_two"), Netherite_Monstrosity_Animation.PHASE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("death"), Netherite_Monstrosity_Animation.DEATH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("fire"), Netherite_Monstrosity_Animation.FIRE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("drain"), Netherite_Monstrosity_Animation.DRAIN, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("shoulder_check"), Netherite_Monstrosity_Animation.SHOULDER_CHECK, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("overpower"), Netherite_Monstrosity_Animation.OVERPOWER, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("flare_shot"), Netherite_Monstrosity_Animation.FLARE_SHOT, ageInTicks, 1.0F);
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

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
