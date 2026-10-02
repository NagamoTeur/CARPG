package net.mindoth.dreadsteel.item.weapon;

import java.util.function.Supplier;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum DreadsteelTier implements Tier {
   DREADSTEEL(7.0F, 22, 4, 9.0F, 0, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)DreadsteelItems.DREADSTEEL_INGOT.get()}));

   private final int level;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final Supplier<Ingredient> repaierMaterial;

   private DreadsteelTier(float damage, int enchantmentValue, int level, float speed, int uses, Supplier<Ingredient> repaierMaterial) {
      this.damage = damage;
      this.enchantmentValue = enchantmentValue;
      this.level = level;
      this.speed = speed;
      this.uses = uses;
      this.repaierMaterial = repaierMaterial;
   }

   public int m_6609_() {
      return this.uses;
   }

   public float m_6624_() {
      return this.speed;
   }

   public float m_6631_() {
      return this.damage;
   }

   public int m_6604_() {
      return this.level;
   }

   public int m_6601_() {
      return this.enchantmentValue;
   }

   public Ingredient m_6282_() {
      return this.repaierMaterial.get();
   }
}
