package io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class AbstractSpellCastingMobModel extends AnimatedGeoModel<AbstractSpellCastingMob> {
   protected TransformStack transformStack = new TransformStack();

   public ResourceLocation getModelResource(AbstractSpellCastingMob object) {
      return AbstractSpellCastingMob.modelResource;
   }

   public abstract ResourceLocation getTextureResource(AbstractSpellCastingMob var1);

   public ResourceLocation getAnimationResource(AbstractSpellCastingMob animatable) {
      return AbstractSpellCastingMob.animationInstantCast;
   }

   public void setCustomAnimations(AbstractSpellCastingMob entity, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(entity, instanceId, animationEvent);
      if (!Minecraft.m_91087_().m_91104_() && entity.shouldBeExtraAnimated()) {
         float partialTick = animationEvent.getPartialTick();
         IBone head = this.getAnimationProcessor().getBone("head");
         IBone body = this.getAnimationProcessor().getBone("body");
         IBone torso = this.getAnimationProcessor().getBone("torso");
         IBone rightArm = this.getAnimationProcessor().getBone("right_arm");
         IBone leftArm = this.getAnimationProcessor().getBone("left_arm");
         IBone rightLeg = this.getAnimationProcessor().getBone("right_leg");
         IBone leftLeg = this.getAnimationProcessor().getBone("left_leg");
         if (!entity.isAnimating() || entity.shouldAlwaysAnimateHead()) {
            this.transformStack
               .pushRotation(
                  head,
                  Mth.m_14179_(partialTick, -entity.f_19860_, -entity.m_146909_()) * (float) (Math.PI / 180.0),
                  Mth.m_14179_(
                     partialTick,
                     Mth.m_14177_(-entity.f_20886_ + entity.f_20884_) * (float) (Math.PI / 180.0),
                     Mth.m_14177_(-entity.f_20885_ + entity.f_20883_) * (float) (Math.PI / 180.0)
                  ),
                  0.0F
               );
         }

         float pLimbSwingAmount = 0.0F;
         float pLimbSwing = 0.0F;
         if (entity.m_6084_()) {
            pLimbSwingAmount = Mth.m_14179_(partialTick, entity.f_20923_, entity.f_20924_);
            pLimbSwing = entity.f_20925_ - entity.f_20924_ * (1.0F - partialTick);
            if (entity.m_6162_()) {
               pLimbSwing *= 3.0F;
            }

            if (pLimbSwingAmount > 1.0F) {
               pLimbSwingAmount = 1.0F;
            }
         }

         float f = 1.0F;
         if (entity.m_21256_() > 4) {
            f = (float)entity.m_20184_().m_82556_();
            f /= 0.2F;
            f *= f * f;
         }

         if (f < 1.0F) {
            f = 1.0F;
         }

         if (entity.m_20159_() && entity.m_20202_().shouldRiderSit()) {
            this.transformStack.pushRotation(rightLeg, 1.4137167F, (float) (-Math.PI / 10), -0.07853982F);
            this.transformStack.pushRotation(leftLeg, 1.4137167F, (float) (Math.PI / 10), 0.07853982F);
         } else if (!entity.isAnimating() || entity.shouldAlwaysAnimateLegs()) {
            float strength = 0.75F;
            Vec3 facing = entity.m_20156_().m_82542_(1.0, 0.0, 1.0).m_82541_();
            Vec3 momentum = entity.m_20184_().m_82542_(1.0, 0.0, 1.0).m_82541_();
            Vec3 facingOrth = new Vec3(-facing.f_82481_, 0.0, facing.f_82479_);
            float directionForward = (float)facing.m_82526_(momentum);
            float directionSide = (float)facingOrth.m_82526_(momentum) * 0.35F;
            float rightLateral = -Mth.m_14031_(pLimbSwing * 0.6662F) * 4.0F * pLimbSwingAmount;
            float leftLateral = -Mth.m_14031_(pLimbSwing * 0.6662F - (float) Math.PI) * 4.0F * pLimbSwingAmount;
            this.transformStack
               .pushPosition(
                  rightLeg,
                  rightLateral * directionSide,
                  Mth.m_14089_(pLimbSwing * 0.6662F) * 4.0F * strength * pLimbSwingAmount,
                  rightLateral * directionForward
               );
            this.transformStack.pushRotation(rightLeg, Mth.m_14089_(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount * strength, 0.0F, 0.0F);
            this.transformStack
               .pushPosition(
                  leftLeg,
                  leftLateral * directionSide,
                  Mth.m_14089_(pLimbSwing * 0.6662F - (float) Math.PI) * 4.0F * strength * pLimbSwingAmount,
                  leftLateral * directionForward
               );
            this.transformStack.pushRotation(leftLeg, Mth.m_14089_(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount * strength, 0.0F, 0.0F);
            if (entity.bobBodyWhileWalking()) {
               this.transformStack
                  .pushPosition(
                     body, 0.0F, Mth.m_14154_(Mth.m_14089_((pLimbSwing * 1.2662F - (float) (Math.PI / 2)) * 0.5F)) * 2.0F * strength * pLimbSwingAmount, 0.0F
                  );
            }
         }

         if (!entity.isAnimating()) {
            this.transformStack
               .pushRotationWithBase(rightArm, Mth.m_14089_(pLimbSwing * 0.6662F + (float) Math.PI) * 2.0F * pLimbSwingAmount * 0.5F / f, 0.0F, 0.0F);
            this.transformStack.pushRotationWithBase(leftArm, Mth.m_14089_(pLimbSwing * 0.6662F) * 2.0F * pLimbSwingAmount * 0.5F / f, 0.0F, 0.0F);
            this.bobBone(rightArm, entity.f_19797_, 1.0F);
            this.bobBone(leftArm, entity.f_19797_, -1.0F);
            if (entity.isDrinkingPotion()) {
               this.transformStack
                  .pushRotation(
                     entity.m_21526_() ? leftArm : rightArm,
                     0.61086524F,
                     (float)(entity.m_21526_() ? -25 : 25) * (float) (Math.PI / 180.0),
                     (float)(entity.m_21526_() ? 15 : -15) * (float) (Math.PI / 180.0)
                  );
            }
         } else if (entity.shouldPointArmsWhileCasting() && entity.isCasting()) {
            this.transformStack.pushRotationWithBase(rightArm, -entity.m_146909_() * (float) (Math.PI / 180.0), 0.0F, 0.0F);
            this.transformStack.pushRotationWithBase(leftArm, -entity.m_146909_() * (float) (Math.PI / 180.0), 0.0F, 0.0F);
         }

         this.transformStack.popStack();
      }
   }

   protected void bobBone(IBone bone, int offset, float multiplier) {
      float z = multiplier * (Mth.m_14089_((float)offset * 0.09F) * 0.05F + 0.05F);
      float x = multiplier * Mth.m_14031_((float)offset * 0.067F) * 0.05F;
      this.transformStack.pushRotation(bone, x, 0.0F, z);
   }
}
