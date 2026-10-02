package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@OnlyIn(Dist.CLIENT)
public class ModelGeckoBiped extends MowzieAnimatedGeoModel<GeckoPlayer> {
   private ResourceLocation textureLocation;
   public boolean isSitting = false;
   public boolean isChild = true;
   public float swingProgress;
   public boolean isSneak;
   public float swimAnimation;
   public ArmPose leftArmPose = ArmPose.EMPTY;
   public ArmPose rightArmPose = ArmPose.EMPTY;
   protected boolean useSmallArms;

   public ResourceLocation getAnimationResource(GeckoPlayer animatable) {
      return new ResourceLocation("mowziesmobs", "animations/animated_player.animation.json");
   }

   public ResourceLocation getModelResource(GeckoPlayer animatable) {
      return new ResourceLocation("mowziesmobs", "geo/animated_player.geo.json");
   }

   public ResourceLocation getTextureResource(GeckoPlayer animatable) {
      return this.textureLocation;
   }

   public void setTextureFromPlayer(AbstractClientPlayer player) {
      this.textureLocation = player.m_108560_();
   }

   public void setUseSmallArms(boolean useSmallArms) {
      this.useSmallArms = useSmallArms;
   }

   public boolean isUsingSmallArms() {
      return this.useSmallArms;
   }

   public MowzieGeoBone bipedHead() {
      return this.getMowzieBone("Head");
   }

   public MowzieGeoBone bipedHeadwear() {
      return this.getMowzieBone("HatLayer");
   }

   public MowzieGeoBone bipedBody() {
      return this.getMowzieBone("Body");
   }

   public MowzieGeoBone bipedRightArm() {
      return this.getMowzieBone("RightArm");
   }

   public MowzieGeoBone bipedLeftArm() {
      return this.getMowzieBone("LeftArm");
   }

   public MowzieGeoBone bipedRightLeg() {
      return this.getMowzieBone("RightLeg");
   }

   public MowzieGeoBone bipedLeftLeg() {
      return this.getMowzieBone("LeftLeg");
   }

   public void setVisible(boolean visible) {
      this.bipedHead().setHidden(!visible);
      this.bipedHeadwear().setHidden(!visible);
      this.bipedBody().setHidden(!visible);
      this.bipedRightArm().setHidden(!visible);
      this.bipedLeftArm().setHidden(!visible);
      this.bipedRightLeg().setHidden(!visible);
      this.bipedLeftLeg().setHidden(!visible);
   }

   public void setRotationAngles() {
      MowzieGeoBone head = this.getMowzieBone("Head");
      MowzieGeoBone neck = this.getMowzieBone("Neck");
      float yaw = 0.0F;
      float pitch = 0.0F;
      float roll = 0.0F;

      for (GeoBone parent = neck.parent; parent != null; parent = parent.parent) {
         pitch += parent.getRotationX();
         yaw += parent.getRotationY();
         roll += parent.getRotationZ();
      }

      neck.addRotation(-yaw, -pitch, -roll);
   }

   public void setRotationAngles(
      Player entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick
   ) {
      if (this.isInitialized()) {
         if (!Minecraft.m_91087_().m_91104_()) {
            MowzieGeoBone rightArmClassic = this.getMowzieBone("RightArmClassic");
            MowzieGeoBone leftArmClassic = this.getMowzieBone("LeftArmClassic");
            MowzieGeoBone rightArmSlim = this.getMowzieBone("RightArmSlim");
            MowzieGeoBone leftArmSlim = this.getMowzieBone("LeftArmSlim");
            if (this.useSmallArms) {
               rightArmClassic.setHidden(true);
               leftArmClassic.setHidden(true);
               rightArmSlim.setHidden(false);
               leftArmSlim.setHidden(false);
            } else {
               rightArmSlim.setHidden(true);
               leftArmSlim.setHidden(true);
               rightArmClassic.setHidden(false);
               leftArmClassic.setHidden(false);
            }

            this.swimAnimation = entityIn.m_20998_(partialTick);
            float headLookAmount = this.getControllerValueInverted("HeadLookController");
            float armLookAmount = 1.0F - this.getControllerValueInverted("ArmPitchController");
            float armLookAmountRight = this.getBone("ArmPitchController").getPositionY();
            float armLookAmountLeft = this.getBone("ArmPitchController").getPositionZ();
            boolean flag = entityIn.m_21256_() > 4;
            boolean flag1 = entityIn.m_6067_();
            this.bipedHead().addRotationY(headLookAmount * -netHeadYaw * (float) (Math.PI / 180.0));
            this.getMowzieBone("LeftClavicle").addRotationY(Math.min(armLookAmount + armLookAmountLeft, 1.0F) * -netHeadYaw * (float) (Math.PI / 180.0));
            this.getMowzieBone("RightClavicle").addRotationY(Math.min(armLookAmount + armLookAmountRight, 1.0F) * -netHeadYaw * (float) (Math.PI / 180.0));
            if (flag) {
               this.bipedHead().addRotationX((float) (-Math.PI / 4));
            } else if (this.swimAnimation > 0.0F) {
               if (flag1) {
                  this.bipedHead().addRotationX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotationX(), (float) (-Math.PI / 4)));
               } else {
                  this.bipedHead()
                     .addRotationX(headLookAmount * this.rotLerpRad(this.swimAnimation, this.bipedHead().getRotationX(), headPitch * (float) (Math.PI / 180.0)));
               }
            } else {
               this.bipedHead().addRotationX(headLookAmount * -headPitch * (float) (Math.PI / 180.0));
               this.getMowzieBone("LeftClavicle").addRotationX(Math.min(armLookAmount + armLookAmountLeft, 1.0F) * -headPitch * (float) (Math.PI / 180.0));
               this.getMowzieBone("RightClavicle").addRotationX(Math.min(armLookAmount + armLookAmountRight, 1.0F) * -headPitch * (float) (Math.PI / 180.0));
            }

            float f = 1.0F;
            if (flag) {
               f = (float)entityIn.m_20184_().m_82556_();
               f /= 0.2F;
               f = f * f * f;
            }

            if (f < 1.0F) {
               f = 1.0F;
            }

            float legWalkAmount = this.getControllerValueInverted("LegWalkController");
            float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
            float armSwingAmountRight = 1.0F - this.getBone("ArmSwingController").getPositionY();
            float armSwingAmountLeft = 1.0F - this.getBone("ArmSwingController").getPositionZ();
            this.bipedRightArm()
               .addRotationX(armSwingAmount * armSwingAmountRight * Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f);
            this.bipedLeftArm().addRotationX(armSwingAmount * armSwingAmountLeft * Mth.m_14089_(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f);
            this.bipedRightLeg().addRotationX(legWalkAmount * Mth.m_14089_(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f);
            this.bipedLeftLeg().addRotationX(legWalkAmount * Mth.m_14089_(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f);
            if (this.isSitting) {
               this.bipedRightArm().setRotationX(this.bipedRightArm().getRotationX() + (float) (-Math.PI / 5));
               this.bipedLeftArm().setRotationX(this.bipedRightArm().getRotationX() + (float) (-Math.PI / 5));
               this.bipedRightLeg().setRotationX(-1.4137167F);
               this.bipedRightLeg().setRotationY((float) (Math.PI / 10));
               this.bipedRightLeg().setRotationZ(0.07853982F);
               this.bipedLeftLeg().setRotationX(-1.4137167F);
               this.bipedLeftLeg().setRotationY((float) (-Math.PI / 10));
               this.bipedLeftLeg().setRotationZ(-0.07853982F);
               this.getMowzieBone("Waist").setRotation(0.0F, 0.0F, 0.0F);
               this.getMowzieBone("Root").setRotation(0.0F, 0.0F, 0.0F);
            }

            boolean flag2 = entityIn.m_5737_() == HumanoidArm.RIGHT;
            boolean flag3 = flag2 ? this.leftArmPose.m_102897_() : this.rightArmPose.m_102897_();
            if (flag2 != flag3) {
               this.poseLeftArm(entityIn);
               this.poseRightArm(entityIn);
            } else {
               this.poseRightArm(entityIn);
               this.poseLeftArm(entityIn);
            }

            float sneakController = this.getControllerValueInverted("CrouchController");
            if (this.isSneak) {
               this.bipedBody().addRotationX(-0.5F * sneakController);
               this.getMowzieBone("Neck").addRotationX(0.5F * sneakController);
               this.bipedRightArm().addRotation(0.4F * sneakController, 0.0F, 0.0F);
               this.bipedLeftArm().addRotation(0.4F * sneakController, 0.0F, 0.0F);
               this.bipedHead().addPositionY(-1.0F * sneakController);
               this.bipedBody().addPosition(0.0F, -1.5F * sneakController, 1.7F * sneakController);
               this.getMowzieBone("Waist").addPosition(0.0F, -0.2F * sneakController, 4.0F * sneakController);
               this.bipedLeftArm().addRotationX(-0.4F * sneakController);
               this.bipedLeftArm().addPosition(0.0F, 0.2F * sneakController, -1.0F * sneakController);
               this.bipedRightArm().addRotationX(-0.4F * sneakController);
               this.bipedRightArm().addPosition(0.0F, 0.2F * sneakController, -1.0F * sneakController);
               this.getMowzieBone("Waist").addPositionY(2.0F * (1.0F - sneakController));
            }

            float armBreathAmount = this.getControllerValueInverted("ArmBreathController");
            breathAnim(this.bipedRightArm(), this.bipedLeftArm(), ageInTicks, armBreathAmount);
            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(entityIn);
            if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
               abilityCapability.codeAnimations(this, partialTick);
            }
         }
      }
   }

   protected MowzieGeoBone getArmForSide(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.bipedLeftArm() : this.bipedRightArm();
   }

   protected float rotLerpRad(float angleIn, float maxAngleIn, float mulIn) {
      float f = (mulIn - maxAngleIn) % (float) (Math.PI * 2);
      if (f < (float) -Math.PI) {
         f += (float) (Math.PI * 2);
      }

      if (f >= (float) Math.PI) {
         f -= (float) (Math.PI * 2);
      }

      return maxAngleIn + angleIn * f;
   }

   private float getArmAngleSq(float limbSwing) {
      return -65.0F * limbSwing + limbSwing * limbSwing;
   }

   protected HumanoidArm getMainHand(Player entityIn) {
      HumanoidArm handside = entityIn.m_5737_();
      return entityIn.f_20912_ == InteractionHand.MAIN_HAND ? handside : handside.m_20828_();
   }

   public static void breathAnim(MowzieGeoBone rightArm, MowzieGeoBone leftArm, float ageInTicks, float armBreathAmount) {
      rightArm.addRotationZ(armBreathAmount * Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F);
      leftArm.addRotationZ(armBreathAmount * -Mth.m_14089_(ageInTicks * 0.09F) * 0.05F - 0.05F);
      rightArm.addRotationX(armBreathAmount * Mth.m_14031_(ageInTicks * 0.067F) * 0.05F);
      leftArm.addRotationX(armBreathAmount * -Mth.m_14031_(ageInTicks * 0.067F) * 0.05F);
   }

   private void poseRightArm(Player p_241654_1_) {
      float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
      switch (this.rightArmPose) {
         case EMPTY:
         default:
            break;
         case BLOCK:
            this.bipedRightArm().addRotationX(0.9424779F * armSwingAmount);
            break;
         case ITEM:
            this.bipedRightArm().addRotationX((float) (Math.PI / 10) * armSwingAmount);
      }
   }

   private void poseLeftArm(Player p_241655_1_) {
      float armSwingAmount = this.getControllerValueInverted("ArmSwingController");
      switch (this.leftArmPose) {
         case EMPTY:
         default:
            break;
         case BLOCK:
            this.bipedLeftArm().addRotationX(0.9424779F * armSwingAmount);
            break;
         case ITEM:
            this.bipedLeftArm().addRotationX((float) (Math.PI / 10) * armSwingAmount);
      }
   }
}
