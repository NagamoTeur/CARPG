package com.aqutheseal.celestisynth.api.item;

public class CSArmorProperties {
   private final String identifier;
   private boolean stunImmune;
   private double damageReflectionPercent;
   private double damageReflectionAddition;
   private double mobEffectDurationMultiplier;
   private double skillDamageMultiplier;

   public CSArmorProperties(String identifier) {
      this.identifier = identifier;
   }

   public CSArmorProperties setStunImmunity(boolean stunImmune) {
      this.stunImmune = stunImmune;
      return this;
   }

   public CSArmorProperties setDamageReflectionPercent(double damageReflectionPercent) {
      this.damageReflectionPercent = damageReflectionPercent;
      return this;
   }

   public CSArmorProperties setDamageReflectionAddition(double damageReflectionAddition) {
      this.damageReflectionAddition = damageReflectionAddition;
      return this;
   }

   public CSArmorProperties setMobEffectDurationMultiplier(double mobEffectDurationMultiplier) {
      this.mobEffectDurationMultiplier = mobEffectDurationMultiplier;
      return this;
   }

   public CSArmorProperties setSkillDamageMultiplier(double skillDamageAddMultiplier) {
      this.skillDamageMultiplier = skillDamageAddMultiplier;
      return this;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public boolean isStunImmune() {
      return this.stunImmune;
   }

   public double getDamageReflectionPercent() {
      return this.damageReflectionPercent;
   }

   public double getDamageReflectionAddition() {
      return this.damageReflectionAddition;
   }

   public double getMobEffectDurationMultiplier() {
      return this.mobEffectDurationMultiplier;
   }

   public double getSkillDamageMultiplier() {
      return this.skillDamageMultiplier;
   }

   @Override
   public String toString() {
      return "CSArmorProperties{identifier='"
         + this.identifier
         + "', stunImmune="
         + this.stunImmune
         + ", damageReflectionPercent="
         + this.damageReflectionPercent
         + ", damageReflectionAddition="
         + this.damageReflectionAddition
         + ", mobEffectDurationMultiplier="
         + this.mobEffectDurationMultiplier
         + ", skillDamageMultiplier="
         + this.skillDamageMultiplier
         + "}";
   }
}
