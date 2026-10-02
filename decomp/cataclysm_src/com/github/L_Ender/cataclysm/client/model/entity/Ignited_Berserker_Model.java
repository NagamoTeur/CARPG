package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Ignited_Berserker_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
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

public class Ignited_Berserker_Model<T extends Ignited_Berserker_Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart everything;
   private final ModelPart mid_root;
   private final ModelPart rod;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart jaw;
   private final ModelPart right_shoulder;
   private final ModelPart left_shoulder;
   private final ModelPart edges;
   private final ModelPart right_f_blade;
   private final ModelPart right_b_blade;
   private final ModelPart left_b_blade;
   private final ModelPart left_f_blade;

   public Ignited_Berserker_Model(ModelPart root) {
      this.root = root;
      this.everything = root.m_171324_("everything");
      this.mid_root = this.everything.m_171324_("mid_root");
      this.rod = this.mid_root.m_171324_("rod");
      this.body = this.rod.m_171324_("body");
      this.head = this.body.m_171324_("head");
      this.jaw = this.head.m_171324_("jaw");
      this.right_shoulder = this.body.m_171324_("right_shoulder");
      this.left_shoulder = this.body.m_171324_("left_shoulder");
      this.edges = this.body.m_171324_("edges");
      this.right_f_blade = this.edges.m_171324_("right_f_blade");
      this.right_b_blade = this.edges.m_171324_("right_b_blade");
      this.left_b_blade = this.edges.m_171324_("left_b_blade");
      this.left_f_blade = this.edges.m_171324_("left_f_blade");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition everything = partdefinition.m_171599_("everything", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition mid_root = everything.m_171599_("mid_root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition rod = mid_root.m_171599_("rod", CubeListBuilder.m_171558_(), PartPose.m_171423_(0.0F, -16.0F, -0.5F, 0.0873F, 0.0F, 0.0F));
      PartDefinition guard1 = rod.m_171599_(
         "guard1",
         CubeListBuilder.m_171558_().m_171514_(13, 40).m_171488_(-1.5F, -9.0F, -1.5F, 3.0F, 19.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.0F, 0.0F, 0.5F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition body = rod.m_171599_(
         "body",
         CubeListBuilder.m_171558_().m_171514_(0, 17).m_171488_(-4.0F, -5.0F, -3.5F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -5.0F, 2.0F)
      );
      PartDefinition head = body.m_171599_(
         "head",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -8.0F, -1.5F)
      );
      PartDefinition jaw = head.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_().m_171514_(46, 47).m_171488_(-3.0F, 0.0F, -4.0F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -1.0F, 0.0F)
      );
      PartDefinition right_shoulder = body.m_171599_(
         "right_shoulder",
         CubeListBuilder.m_171558_().m_171514_(51, 31).m_171488_(-3.0F, -2.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-3.0F, -4.0F, -2.0F, 0.0F, 0.0F, -0.1309F)
      );
      PartDefinition left_shoulder = body.m_171599_(
         "left_shoulder",
         CubeListBuilder.m_171558_().m_171514_(51, 24).m_171488_(-1.0F, -2.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(3.0F, -4.0F, -2.0F, 0.0F, 0.0F, 0.1309F)
      );
      PartDefinition edges = body.m_171599_("edges", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 5.0F, -1.5F));
      PartDefinition right_f_blade = edges.m_171599_(
         "right_f_blade",
         CubeListBuilder.m_171558_()
            .m_171514_(26, 57)
            .m_171488_(-0.9899F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 26)
            .m_171488_(-2.5F, -25.25F, -0.5F, 5.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-11.0F, -2.0F, -11.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition guard2 = right_f_blade.m_171599_(
         "guard2",
         CubeListBuilder.m_171558_()
            .m_171514_(18, 64)
            .m_171488_(-2.4F, 3.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 60)
            .m_171488_(-3.4F, 4.0F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, -0.5F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard3 = right_f_blade.m_171599_(
         "guard3",
         CubeListBuilder.m_171558_()
            .m_171514_(60, 38)
            .m_171488_(0.4F, 4.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 22)
            .m_171488_(0.4F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 63)
            .m_171488_(-1.6F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard4 = right_f_blade.m_171599_(
         "guard4",
         CubeListBuilder.m_171558_().m_171514_(50, 64).m_171488_(-0.4F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard5 = right_f_blade.m_171599_(
         "guard5",
         CubeListBuilder.m_171558_().m_171514_(51, 16).m_171488_(0.4F, -1.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition right_b_blade = edges.m_171599_(
         "right_b_blade",
         CubeListBuilder.m_171558_()
            .m_171514_(48, 55)
            .m_171488_(-0.9899F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(38, 0)
            .m_171488_(-2.5F, -25.25F, -0.5F, 5.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-11.0F, -2.0F, 11.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition guard6 = right_b_blade.m_171599_(
         "guard6",
         CubeListBuilder.m_171558_()
            .m_171514_(64, 6)
            .m_171488_(-2.4F, 3.0F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(57, 55)
            .m_171488_(-3.4F, 4.0F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.5F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard7 = right_b_blade.m_171599_(
         "guard7",
         CubeListBuilder.m_171558_()
            .m_171514_(57, 59)
            .m_171488_(0.4F, 4.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 14)
            .m_171488_(0.4F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(64, 46)
            .m_171488_(-1.6F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard8 = right_b_blade.m_171599_(
         "guard8",
         CubeListBuilder.m_171558_().m_171514_(45, 64).m_171488_(-0.4F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard9 = right_b_blade.m_171599_(
         "guard9",
         CubeListBuilder.m_171558_().m_171514_(51, 8).m_171488_(0.4F, -1.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition left_b_blade = edges.m_171599_(
         "left_b_blade",
         CubeListBuilder.m_171558_()
            .m_171514_(39, 55)
            .m_171488_(-1.0101F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 34)
            .m_171488_(-2.5F, -25.25F, -0.5F, 5.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(11.0F, -2.0F, 11.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition guard10 = left_b_blade.m_171599_(
         "guard10",
         CubeListBuilder.m_171558_()
            .m_171514_(55, 63)
            .m_171488_(0.4F, 3.0F, -1.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(13, 34)
            .m_171488_(0.4F, 4.0F, -1.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard11 = left_b_blade.m_171599_(
         "guard11",
         CubeListBuilder.m_171558_()
            .m_171514_(26, 43)
            .m_171488_(-3.4F, 4.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(0, 64)
            .m_171488_(-2.4F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(40, 64)
            .m_171488_(-0.4F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard12 = left_b_blade.m_171599_(
         "guard12",
         CubeListBuilder.m_171558_().m_171514_(35, 64).m_171488_(-1.6F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard13 = left_b_blade.m_171599_(
         "guard13",
         CubeListBuilder.m_171558_().m_171514_(51, 0).m_171488_(-4.4F, -1.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition left_f_blade = edges.m_171599_(
         "left_f_blade",
         CubeListBuilder.m_171558_()
            .m_171514_(51, 38)
            .m_171488_(-1.0101F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(25, 17)
            .m_171488_(-2.5F, -25.25F, -0.5F, 5.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(11.0F, -2.0F, -11.0F, 0.0F, -0.7854F, 0.0F)
      );
      PartDefinition guard14 = left_f_blade.m_171599_(
         "guard14",
         CubeListBuilder.m_171558_()
            .m_171514_(60, 42)
            .m_171488_(0.4F, 3.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(25, 0)
            .m_171488_(0.4F, 4.0F, -0.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, -0.5F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard15 = left_f_blade.m_171599_(
         "guard15",
         CubeListBuilder.m_171558_()
            .m_171514_(25, 4)
            .m_171488_(-3.4F, 4.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(9, 63)
            .m_171488_(-2.4F, 3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .m_171514_(33, 8)
            .m_171488_(-0.4F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      PartDefinition guard16 = left_f_blade.m_171599_(
         "guard16",
         CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-1.6F, -1.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition guard17 = left_f_blade.m_171599_(
         "guard17",
         CubeListBuilder.m_171558_().m_171514_(26, 49).m_171488_(-4.4F, -1.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(0.9899F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entity.getAttackState() == 0) {
         if (limbSwing != 0.0F) {
            this.animateWalk(Ignited_Berserker_Animation.WALK, limbSwing, limbSwingAmount, 1.0F, 2.0F);
         }

         this.edges.f_104204_ -= ageInTicks * 0.1F;
      }

      this.m_233385_(entity.getAnimationState("idle"), Ignited_Berserker_Animation.IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("x_slash"), Ignited_Berserker_Animation.X_SLASH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("mixer_start"), Ignited_Berserker_Animation.MIXER_START, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("mixer_idle"), Ignited_Berserker_Animation.MIXER_IDLE, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("mixer_finish"), Ignited_Berserker_Animation.MIXER_FINISH, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sword_dance_left"), Ignited_Berserker_Animation.SWORD_DANCE_LEFT, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("sword_dance_right"), Ignited_Berserker_Animation.SWORD_DANCE_RIGHT, ageInTicks, 1.0F);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.f_104203_ = xRot * (float) (Math.PI / 180.0);
      this.head.f_104204_ = yRot * (float) (Math.PI / 180.0);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
