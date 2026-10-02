package dev.latvian.mods.kubejs.item;

import dev.latvian.mods.rhino.util.RemapForJS;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MutableToolTier implements Tier {
   public final Tier parent;
   private int uses;
   private float speed;
   private float attackDamageBonus;
   private int level;
   private int enchantmentValue;
   private Ingredient repairIngredient;

   public MutableToolTier(Tier p) {
      this.parent = p;
      this.uses = this.parent.m_6609_();
      this.speed = this.parent.m_6624_();
      this.attackDamageBonus = this.parent.m_6631_();
      this.level = this.parent.m_6604_();
      this.enchantmentValue = this.parent.m_6601_();
      this.repairIngredient = this.parent.m_6282_();
   }

   @RemapForJS("getUses")
   public int m_6609_() {
      return this.uses;
   }

   public void setUses(int i) {
      this.uses = i;
   }

   @RemapForJS("getSpeed")
   public float m_6624_() {
      return this.speed;
   }

   public void setSpeed(float f) {
      this.speed = f;
   }

   @RemapForJS("getAttackDamageBonus")
   public float m_6631_() {
      return this.attackDamageBonus;
   }

   public void setAttackDamageBonus(float f) {
      this.attackDamageBonus = f;
   }

   @RemapForJS("getLevel")
   public int m_6604_() {
      return this.level;
   }

   public void setLevel(int i) {
      this.level = i;
   }

   @RemapForJS("getEnchantmentValue")
   public int m_6601_() {
      return this.enchantmentValue;
   }

   public void setEnchantmentValue(int i) {
      this.enchantmentValue = i;
   }

   @RemapForJS("getVanillaRepairIngredient")
   public Ingredient m_6282_() {
      return this.repairIngredient;
   }

   public void setRepairIngredient(Ingredient in) {
      this.repairIngredient = in;
   }
}
