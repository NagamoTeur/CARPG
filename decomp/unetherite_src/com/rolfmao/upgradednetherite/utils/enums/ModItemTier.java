package com.rolfmao.upgradednetherite.utils.enums;

import java.util.function.Supplier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ModItemTier implements Tier {
   GOLD_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   FIRE_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   ENDER_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   WATER_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   WITHER_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   POISON_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   PHANTOM_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   FEATHER_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   CORRUPT_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_})),
   ECHO_UPGRADED_NETHERITE(4, 2031, 9.0F, 3.0F, 15, () -> Ingredient.m_43929_(new ItemLike[]{Items.f_42418_}));

   private final int harvestLevel;
   private final int maxUses;
   private final float efficiency;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<Ingredient> repairMaterial;

   private ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
      this.harvestLevel = harvestLevel;
      this.maxUses = maxUses;
      this.efficiency = efficiency;
      this.attackDamage = attackDamage;
      this.enchantability = enchantability;
      this.repairMaterial = repairMaterial;
   }

   public int m_6609_() {
      return this.maxUses;
   }

   public float m_6624_() {
      return this.efficiency;
   }

   public float m_6631_() {
      return this.attackDamage;
   }

   public int m_6604_() {
      return this.harvestLevel;
   }

   public int m_6601_() {
      return this.enchantability;
   }

   public Ingredient m_6282_() {
      return this.repairMaterial.get();
   }
}
