package com.bobmowzie.mowziesmobs.server.ability.abilities.mob;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

public class MeleeAttackAbility<T extends MowzieGeckoEntity> extends Ability<T> {
   protected SoundEvent attackSound;
   protected float knockBackMultiplier = 0.0F;
   protected float range;
   protected float damageMultiplier;
   protected SoundEvent hitSound;
   protected boolean hurtInterrupts;
   protected String[] animationNames;

   public MeleeAttackAbility(
      AbilityType<T, ? extends MeleeAttackAbility<T>> abilityType,
      T user,
      String[] animationNames,
      SoundEvent attackSound,
      SoundEvent hitSound,
      float knockBackMultiplier,
      float range,
      float damageMultiplier,
      int startup,
      int recovery,
      boolean hurtInterrupts
   ) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, startup),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, recovery)
         },
         0
      );
      this.attackSound = attackSound;
      this.hitSound = hitSound;
      this.knockBackMultiplier = knockBackMultiplier;
      this.damageMultiplier = damageMultiplier;
      this.range = range;
      this.hurtInterrupts = hurtInterrupts;
      this.animationNames = animationNames;
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getUser().m_5448_() != null) {
         this.getUser().m_21391_(this.getUser().m_5448_(), 30.0F, 30.0F);
         this.getUser().m_21563_().m_24960_(this.getUser().m_5448_(), 30.0F, 30.0F);
      }

      this.getUser().m_21573_().m_26573_();
      this.getUser().m_21566_().m_24988_(0.0F, 0.0F);
      this.getUser().setStrafing(false);
   }

   @Override
   public void start() {
      super.start();
      String animationName = this.animationNames[this.getUser().m_217043_().m_188503_(this.animationNames.length)];
      this.playAnimation(animationName, false);
   }

   @Override
   protected void beginSection(AbilitySection section) {
      super.beginSection(section);
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         LivingEntity entityTarget = this.getUser().m_5448_();
         if (entityTarget != null && this.getUser().targetDistance <= this.range) {
            this.getUser().doHurtTarget(entityTarget, this.damageMultiplier, this.knockBackMultiplier);
            this.onAttack(entityTarget, this.damageMultiplier, this.knockBackMultiplier);
            if (this.hitSound != null) {
               this.getUser().m_5496_(this.hitSound, 1.0F, 1.0F);
            }
         }

         if (this.attackSound != null) {
            this.getUser().m_5496_(this.attackSound, 1.0F, 1.0F);
         }
      }
   }

   protected void onAttack(LivingEntity entityTarget, float damageMultiplier, float applyKnockbackMultiplier) {
   }

   @Override
   public boolean damageInterrupts() {
      return this.hurtInterrupts;
   }
}
