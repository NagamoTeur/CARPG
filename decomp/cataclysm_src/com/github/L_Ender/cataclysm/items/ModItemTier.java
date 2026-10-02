package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public enum ModItemTier implements Tier {
   TOOL_WITHERITE(5, 6666, 11.0F, 7.0F, 12, ModItems.WITHERITE_INGOT),
   TOOL_ENDERITE(5, 6666, 11.0F, 7.0F, 12, ModItems.ENDERITE_INGOT);

   private final int harvestLevel;
   private final int maxUses;
   private final float efficiency;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<Item> repairMaterial;

   private ModItemTier(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Supplier<Item> repairMaterial) {
      this.harvestLevel = harvestLevel;
      this.maxUses = maxUses;
      this.efficiency = efficiency;
      this.attackDamage = damage;
      this.enchantability = enchantability;
      this.repairMaterial = repairMaterial;
   }

   public int m_6604_() {
      return this.harvestLevel;
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

   public int m_6601_() {
      return this.enchantability;
   }

   public Ingredient m_6282_() {
      return Ingredient.m_43929_(new ItemLike[]{(ItemLike)this.repairMaterial.get()});
   }
}
