package io.redspace.ironsspellbooks.entity.mobs.keeper;

import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class KeeperModel extends AbstractSpellCastingMobModel {
   public static final ResourceLocation TEXTURE = new ResourceLocation("irons_spellbooks", "textures/entity/keeper/keeper.png");
   public static final ResourceLocation modelResource = new ResourceLocation("irons_spellbooks", "geo/citadel_keeper.geo.json");
   private int lastTick;
   private float legTween = 1.0F;

   @Override
   public ResourceLocation getTextureResource(AbstractSpellCastingMob object) {
      return TEXTURE;
   }

   @Override
   public ResourceLocation getModelResource(AbstractSpellCastingMob object) {
      return modelResource;
   }

   @Override
   public void setCustomAnimations(AbstractSpellCastingMob entity, int instanceId, AnimationEvent animationEvent) {
      super.setCustomAnimations(entity, instanceId, animationEvent);
      if (!Minecraft.m_91087_().m_91104_()) {
         float partialTick = animationEvent.getPartialTick();
         IBone rightLeg = this.getAnimationProcessor().getBone("right_leg");
         IBone leftLeg = this.getAnimationProcessor().getBone("left_leg");
         IBone rightArm = this.getAnimationProcessor().getBone("right_arm");
         IBone leftArm = this.getAnimationProcessor().getBone("left_arm");
         IBone body = this.getAnimationProcessor().getBone("body");
         boolean tick = this.lastTick != entity.f_19797_;
         this.lastTick = entity.f_19797_;
         float pLimbSwingAmount = 0.0F;
         float pLimbSwing = 0.0F;
         if (entity.m_6084_()) {
            pLimbSwingAmount = Mth.m_14179_(partialTick, entity.f_20923_, entity.f_20924_);
            pLimbSwing = entity.f_20925_ - entity.f_20924_ * (1.0F - partialTick);
            if (pLimbSwingAmount > 1.0F) {
               pLimbSwingAmount = 1.0F;
            }

            if (entity.f_20916_ > 0) {
               pLimbSwingAmount *= 0.25F;
            }
         }

         if (!entity.m_20159_() || !entity.m_20202_().shouldRiderSit()) {
            float strength = 0.75F;
            updatePosition(
               rightLeg,
               0.0F,
               Mth.m_14089_(pLimbSwing * 0.6662F) * 4.0F * strength * pLimbSwingAmount,
               -Mth.m_14031_(pLimbSwing * 0.6662F) * 4.0F * pLimbSwingAmount
            );
            updatePosition(
               leftLeg,
               0.0F,
               Mth.m_14089_(pLimbSwing * 0.6662F - (float) Math.PI) * 4.0F * strength * pLimbSwingAmount,
               -Mth.m_14031_(pLimbSwing * 0.6662F - (float) Math.PI) * 4.0F * pLimbSwingAmount
            );
            updatePosition(
               body, 0.0F, Mth.m_14154_(Mth.m_14089_((pLimbSwing * 1.2662F - (float) (Math.PI / 2)) * 0.5F)) * 2.0F * strength * pLimbSwingAmount, 0.0F
            );
            if (tick) {
               if (entity.isAnimating() && !entity.shouldAlwaysAnimateLegs()) {
                  this.legTween = Mth.m_14179_(0.9F, 1.0F, 0.0F);
               } else {
                  this.legTween = Mth.m_14179_(0.9F, 0.0F, 1.0F);
               }
            }

            rightLeg.setRotationX(Mth.m_14089_(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount * this.legTween * strength);
            leftLeg.setRotationX(Mth.m_14089_(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount * this.legTween * strength);
         }
      }
   }

   protected static void updatePosition(IBone bone, float x, float y, float z) {
      bone.setPositionX(x);
      bone.setPositionY(y);
      bone.setPositionZ(z);
   }

   protected static void updateRotation(IBone bone, float x, float y, float z) {
      bone.setRotationX(x);
      bone.setRotationY(y);
      bone.setRotationZ(z);
   }
}
