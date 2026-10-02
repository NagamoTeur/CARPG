package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Netherite_Ministrosity_Animation;
import com.github.L_Ender.cataclysm.entity.Pet.Netherite_Ministrosity_Entity;
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

public class Netherite_Ministrosity_Model extends HierarchicalModel<Netherite_Ministrosity_Entity> {
   private final ModelPart root;
   private final ModelPart roots;
   private final ModelPart mid_root;
   private final ModelPart legs;
   private final ModelPart body;
   private final ModelPart jaw;
   private final ModelPart right_arm;
   private final ModelPart left_arm;
   private final ModelPart right_leg;
   private final ModelPart left_leg;

   public Netherite_Ministrosity_Model(ModelPart root) {
      this.root = root;
      this.roots = this.root.m_171324_("roots");
      this.mid_root = this.roots.m_171324_("mid_root");
      this.legs = this.mid_root.m_171324_("legs");
      this.body = this.legs.m_171324_("body");
      this.jaw = this.body.m_171324_("jaw");
      this.right_arm = this.body.m_171324_("right_arm");
      this.left_arm = this.body.m_171324_("left_arm");
      this.right_leg = this.legs.m_171324_("right_leg");
      this.left_leg = this.legs.m_171324_("left_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition roots = partdefinition.m_171599_("roots", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      PartDefinition mid_root = roots.m_171599_("mid_root", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      PartDefinition legs = mid_root.m_171599_("legs", CubeListBuilder.m_171558_(), PartPose.m_171419_(0.0F, -4.0F, 0.0F));
      PartDefinition body = legs.m_171599_(
         "body",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 0)
            .m_171488_(-3.5F, -5.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(1, 28)
            .m_171488_(-3.5F, -6.0F, -3.5F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, 0.0F, 0.0F)
      );
      PartDefinition jaw = body.m_171599_(
         "jaw",
         CubeListBuilder.m_171558_()
            .m_171514_(0, 14)
            .m_171488_(-3.5F, -6.0F, -7.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
            .m_171514_(44, 16)
            .m_171488_(-1.0F, -3.0F, -7.1F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 10)
            .m_171488_(-6.5F, -6.0F, -3.5F, 13.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .m_171514_(29, 0)
            .m_171488_(-3.5F, -1.0F, -7.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(0.0F, -5.0F, 3.5F)
      );
      PartDefinition right_arm = body.m_171599_(
         "right_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(29, 16)
            .m_171488_(-4.5F, -1.0F, -2.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(30, 34)
            .m_171488_(-5.5F, -1.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-4.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.7418F)
      );
      PartDefinition left_arm = body.m_171599_(
         "left_arm",
         CubeListBuilder.m_171558_()
            .m_171514_(29, 25)
            .m_171488_(-0.5F, -1.0F, -2.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .m_171514_(41, 34)
            .m_171488_(4.5F, -1.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(4.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.7418F)
      );
      PartDefinition right_leg = legs.m_171599_(
         "right_leg",
         CubeListBuilder.m_171558_().m_171514_(0, 36).m_171488_(-1.475F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(-2.0F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = legs.m_171599_(
         "left_leg",
         CubeListBuilder.m_171558_().m_171514_(15, 37).m_171488_(-1.525F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.m_171419_(2.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void setupAnim(Netherite_Ministrosity_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.m_142109_().m_171331_().forEach(ModelPart::m_233569_);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      this.m_233385_(entity.getAnimationState("idle"), Netherite_Ministrosity_Animation.IDLE, ageInTicks, 1.0F);
      this.animateWalk(Netherite_Ministrosity_Animation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
      this.m_233385_(entity.getAnimationState("sleep"), Netherite_Ministrosity_Animation.SLEEP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("operation"), Netherite_Ministrosity_Animation.OPERATION, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("chest_open"), Netherite_Ministrosity_Animation.CHEST_OPEN, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("chest_loop"), Netherite_Ministrosity_Animation.CHEST_LOOP, ageInTicks, 1.0F);
      this.m_233385_(entity.getAnimationState("chest_close"), Netherite_Ministrosity_Animation.CHEST_CLOSE, ageInTicks, 1.0F);
   }

   protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
      long i = (long)(p_268057_ * 50.0F * p_268138_);
      float f = Math.min(p_268347_ * p_268165_, 1.0F);
      KeyframeAnimations.m_232319_(this, p_268159_, i, f, new Vector3f());
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.roots.f_104204_ += yRot * (float) (Math.PI / 180.0);
   }

   public ModelPart m_142109_() {
      return this.root;
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      this.root.m_104306_(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
   }
}
