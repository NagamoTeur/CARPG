package io.redspace.ironsspellbooks.entity.mobs.wizards.alchemist;

import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class ApothecaristModel extends AbstractSpellCastingMobModel {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/apothecarist.png");
   public static final ResourceLocation MODEL = new ResourceLocation("irons_spellbooks", "geo/piglin_casting_mob.geo.json");
   private static final float tilt = (float) (Math.PI / 18);
   private static final Vector3f forward = new Vector3f(0.0F, 0.0F, Mth.m_14031_((float) (Math.PI / 18)) * -12.0F);

   @Override
   public ResourceLocation getModelResource(AbstractSpellCastingMob object) {
      return MODEL;
   }

   @Override
   public ResourceLocation getTextureResource(AbstractSpellCastingMob object) {
      return TEXTURE;
   }

   @Override
   public void setCustomAnimations(AbstractSpellCastingMob entity, int instanceId, AnimationEvent animationEvent) {
      float partialTick = animationEvent.getPartialTick();
      IBone leftEar = this.getAnimationProcessor().getBone("left_ear");
      IBone rightEar = this.getAnimationProcessor().getBone("right_ear");
      IBone head = this.getAnimationProcessor().getBone("head");
      IBone body = this.getAnimationProcessor().getBone("body");
      IBone torso = this.getAnimationProcessor().getBone("torso");
      IBone rightArm = this.getAnimationProcessor().getBone("right_arm");
      IBone leftArm = this.getAnimationProcessor().getBone("left_arm");
      IBone rightLeg = this.getAnimationProcessor().getBone("right_leg");
      IBone leftLeg = this.getAnimationProcessor().getBone("left_leg");
      this.transformStack.pushPosition(head, forward);
      this.transformStack.pushPosition(rightArm, forward);
      this.transformStack.pushPosition(leftArm, forward);
      this.transformStack.pushPosition(torso, forward);
      this.transformStack.pushRotation(torso, (float) (-Math.PI / 18), 0.0F, 0.0F);
      this.transformStack.pushPosition(rightLeg, forward);
      this.transformStack.pushPosition(leftLeg, new Vector3f(0.0F, 0.0F, 1.0F));
      if (entity.f_20913_ > 0) {
         float rot = Mth.m_14179_(((float)entity.f_20913_ - partialTick) / 10.0F, 0.0F, (float) Math.PI);
         this.transformStack.pushRotation(rightArm, rot, 0.0F, 0.0F);
      }

      if (leftEar != null && rightEar != null) {
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

         float r = Mth.m_14089_(pLimbSwing * 0.6662F + (float) Math.PI) * 2.0F * pLimbSwingAmount * 0.5F / f;
         r *= 0.3F;
         r += 0.25132743F;
         this.transformStack.pushRotation(leftEar, 0.0F, 0.0F, -r);
         this.transformStack.pushRotation(rightEar, 0.0F, 0.0F, r);
      }

      super.setCustomAnimations(entity, instanceId, animationEvent);
   }
}
