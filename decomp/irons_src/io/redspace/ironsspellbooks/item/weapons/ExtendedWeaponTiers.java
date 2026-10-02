package io.redspace.ironsspellbooks.item.weapons;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import java.util.function.Supplier;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ExtendedWeaponTiers implements Tier {
   KEEPER_FLAMBERGE(1000, 4, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42419_})),
   DREADSWORD(1061, 14, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.ARCANE_INGOT.get()})),
   METAL_MAGEHUNTER(1561, 12, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.ARCANE_INGOT.get()})),
   CRYSTAL_MAGEHUNTER(1561, 12, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42415_})),
   TRUTHSEEKER(2031, 10, () -> Ingredient.m_43929_(new ItemLike[]{(ItemLike)ItemRegistry.ARCANE_INGOT.get()})),
   CLAYMORE(1000, 8, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42416_})),
   AMETHYST(1561, 16, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_151049_}));

   private final int level;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final LazyLoadedValue<Ingredient> repairIngredient;

   private ExtendedWeaponTiers(int pLevel, int pUses, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
      this.level = pLevel;
      this.uses = pUses;
      this.speed = 0.0F;
      this.damage = 0.0F;
      this.enchantmentValue = pEnchantmentValue;
      this.repairIngredient = new LazyLoadedValue(pRepairIngredient);
   }

   private ExtendedWeaponTiers(int pUses, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
      this(0, pUses, pEnchantmentValue, pRepairIngredient);
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
      return (Ingredient)this.repairIngredient.m_13971_();
   }
}
