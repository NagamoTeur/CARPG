package com.aizistral.enigmaticlegacy.objects;

import java.util.Random;

public class Perhaps {
   private static final Random theySeeMeRollin = new Random();
   private int probability;

   public Perhaps(int probability) {
      this.probability = probability;
   }

   public boolean roll() {
      return Math.random() <= this.asMultiplier(false);
   }

   public int asPercentage() {
      return this.probability;
   }

   public double asMultiplier(boolean baseOne) {
      return baseOne ? 1.0 + (double)this.probability / 100.0 : (double)this.probability / 100.0;
   }

   public double asMultiplierInverted() {
      return 1.0 - (double)this.probability / 100.0;
   }

   public float asModifier(boolean baseOne) {
      return baseOne ? 1.0F + (float)this.probability / 100.0F : (float)this.probability / 100.0F;
   }

   public float asModifierInverted() {
      return 1.0F - (float)this.probability / 100.0F;
   }

   public float asModifier() {
      return this.asModifier(false);
   }

   public double asMultiplier() {
      return this.asMultiplier(false);
   }

   @Override
   public String toString() {
      return this.probability + "";
   }
}
