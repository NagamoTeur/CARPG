package com.github.L_Ender.cataclysm.client.model.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ignitium_Elytra_chestplate_Model extends HumanoidModel {
   private final ModelPart rightWing;
   private final ModelPart leftWing;

   public ignitium_Elytra_chestplate_Model(ModelPart part) {
      super(part);
      this.leftWing = part.m_171324_("body").m_171324_("left_wing");
      this.rightWing = part.m_171324_("body").m_171324_("right_wing");
   }

   public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, 0.0F);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition body = meshdefinition.m_171576_().m_171597_("body");
      PartDefinition rightArm = partdefinition.m_171597_("right_arm");
      PartDefinition leftArm = partdefinition.m_171597_("left_arm");
      body.m_171599_(
         "left_wing",
         CubeListBuilder.m_171558_().m_171514_(0, 65).m_171480_().m_171488_(-11.0F, 0.0F, 1.5F, 11.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(6.0F, 0.0F, 1.5F, (float) (Math.PI / 12), 0.0F, (float) (-Math.PI / 12))
      );
      body.m_171599_(
         "right_wing",
         CubeListBuilder.m_171558_().m_171514_(0, 65).m_171488_(0.0F, 0.0F, 1.5F, 11.0F, 23.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.m_171423_(-5.0F, 0.0F, 1.5F, (float) (Math.PI / 12), 0.0F, (float) (Math.PI / 12))
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
      return LayerDefinition.m_171565_(meshdefinition, 128, 128);
   }

   public ignitium_Elytra_chestplate_Model withAnimations(LivingEntity entity) {
      float partialTick = Minecraft.m_91087_().m_91296_();
      float limbSwingAmount = Mth.m_14179_(partialTick, entity.f_20923_, entity.f_20924_);
      float limbSwing = entity.f_20925_ + partialTick;
      this.m_6973_(entity, limbSwing, limbSwingAmount, (float)entity.f_19797_ + partialTick, 0.0F, 0.0F);
      return this;
   }

   public void m_6973_(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float f = (float) (Math.PI / 12);
      float f1 = (float) (-Math.PI / 12);
      float f2 = 0.0F;
      float f3 = 0.0F;
      if (entityIn.m_21255_()) {
         float f4 = 1.0F;
         Vec3 vector3d = entityIn.m_20184_();
         if (vector3d.f_82480_ < 0.0) {
            Vec3 vector3d1 = vector3d.m_82541_();
            f4 = 1.0F - (float)Math.pow(-vector3d1.f_82480_, 1.5);
         }

         f = f4 * (float) (Math.PI / 9) + (1.0F - f4) * f;
         f1 = f4 * (float) (-Math.PI / 2) + (1.0F - f4) * f1;
      } else if (entityIn.m_6047_()) {
         f = (float) (Math.PI * 2.0 / 9.0);
         f1 = (float) (-Math.PI / 4);
         f2 = -1.0F;
         f3 = 0.08726646F;
      }

      this.leftWing.f_104200_ = 5.0F;
      this.leftWing.f_104201_ = f2;
      if (entityIn instanceof AbstractClientPlayer abstractclientplayerentity) {
         abstractclientplayerentity.f_108542_ = (float)((double)abstractclientplayerentity.f_108542_ + (double)(f - abstractclientplayerentity.f_108542_) * 0.1);
         abstractclientplayerentity.f_108543_ = (float)(
            (double)abstractclientplayerentity.f_108543_ + (double)(f3 - abstractclientplayerentity.f_108543_) * 0.1
         );
         abstractclientplayerentity.f_108544_ = (float)(
            (double)abstractclientplayerentity.f_108544_ + (double)(f1 - abstractclientplayerentity.f_108544_) * 0.1
         );
         this.leftWing.f_104203_ = abstractclientplayerentity.f_108542_;
         this.leftWing.f_104204_ = abstractclientplayerentity.f_108543_;
         this.leftWing.f_104205_ = abstractclientplayerentity.f_108544_;
      } else {
         this.leftWing.f_104203_ = f;
         this.leftWing.f_104205_ = f1;
         this.leftWing.f_104204_ = f3;
      }

      this.rightWing.f_104200_ = -this.leftWing.f_104200_;
      this.rightWing.f_104204_ = -this.leftWing.f_104204_;
      this.rightWing.f_104201_ = this.leftWing.f_104201_;
      this.rightWing.f_104203_ = this.leftWing.f_104203_;
      this.rightWing.f_104205_ = -this.leftWing.f_104205_;
   }
}
