package com.aizistral.enigmaticlegacy.api.materials;

import com.aizistral.etherium.core.IEtheriumConfig;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.TierSortingRegistry;

public enum EnigmaticMaterials implements Tier {
   FORBIDDENAXE(0, 2000, 6.0F, 3.0F, 16, () -> Ingredient.f_43901_),
   ENDERSLAYER(0, 2000, 6.0F, 3.0F, 16, () -> Ingredient.m_43929_(new ItemLike[]{Blocks.f_50080_})),
   ETHERIUM(5, 3000, 8.0F, 5.0F, 32, () -> getEtheriumConfig().getRepairMaterial());

   private static IEtheriumConfig etheriumConfig;
   private final int harvestLevel;
   private final int maxUses;
   private final float efficiency;
   private final float attackDamage;
   private final int enchantability;
   private final Supplier<Ingredient> repairMaterial;

   private EnigmaticMaterials(
      int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn
   ) {
      this.harvestLevel = harvestLevelIn;
      this.maxUses = maxUsesIn;
      this.efficiency = efficiencyIn;
      this.attackDamage = attackDamageIn;
      this.enchantability = enchantabilityIn;
      this.repairMaterial = repairMaterialIn;
      if (harvestLevelIn == 5) {
         TierSortingRegistry.registerTier(this, new ResourceLocation("enigmaticlegacy", "etherium"), List.of(Tiers.values()), List.of());
      }
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

   public static IEtheriumConfig getEtheriumConfig() {
      return etheriumConfig;
   }

   public static void setEtheriumConfig(IEtheriumConfig config) {
      etheriumConfig = Objects.requireNonNull(config);
   }
}
