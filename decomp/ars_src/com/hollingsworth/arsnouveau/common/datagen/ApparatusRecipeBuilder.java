package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantmentRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

public class ApparatusRecipeBuilder {
   EnchantingApparatusRecipe recipe = new EnchantingApparatusRecipe();

   public static ApparatusRecipeBuilder builder() {
      return new ApparatusRecipeBuilder();
   }

   public ApparatusRecipeBuilder withResult(ItemLike result) {
      this.recipe.result = new ItemStack(result);
      return this;
   }

   public ApparatusRecipeBuilder withResult(ItemLike result, int count) {
      this.recipe.result = new ItemStack(result, count);
      return this;
   }

   public ApparatusRecipeBuilder withResult(RegistryObject<? extends ItemLike> result) {
      return this.withResult((ItemLike)result.get());
   }

   public ApparatusRecipeBuilder withResult(ItemStack result) {
      this.recipe.result = result;
      return this;
   }

   public ApparatusRecipeBuilder withReagent(ItemLike provider) {
      this.recipe.reagent = Ingredient.m_43929_(new ItemLike[]{provider});
      return this;
   }

   public ApparatusRecipeBuilder withReagent(RegistryObject<? extends ItemLike> provider) {
      return this.withReagent((ItemLike)provider.get());
   }

   public ApparatusRecipeBuilder withReagent(Ingredient ingredient) {
      this.recipe.reagent = ingredient;
      return this;
   }

   public ApparatusRecipeBuilder withPedestalItem(Ingredient i) {
      this.recipe.pedestalItems.add(i);
      return this;
   }

   public ApparatusRecipeBuilder withPedestalItem(RegistryObject<? extends ItemLike> i) {
      return this.withPedestalItem((ItemLike)i.get());
   }

   public ApparatusRecipeBuilder withPedestalItem(ItemLike i) {
      return this.withPedestalItem(Ingredient.m_43929_(new ItemLike[]{i}));
   }

   public ApparatusRecipeBuilder withPedestalItem(int count, RegistryObject<? extends ItemLike> i) {
      return this.withPedestalItem(count, (ItemLike)i.get());
   }

   public ApparatusRecipeBuilder withPedestalItem(int count, ItemLike item) {
      for (int i = 0; i < count; i++) {
         this.withPedestalItem(item);
      }

      return this;
   }

   public ApparatusRecipeBuilder withPedestalItem(int count, Ingredient ingred) {
      for (int i = 0; i < count; i++) {
         this.withPedestalItem(ingred);
      }

      return this;
   }

   public ApparatusRecipeBuilder withPedestalItem(int count, TagKey<Item> ingred) {
      return this.withPedestalItem(count, Ingredient.m_204132_(ingred));
   }

   public ApparatusRecipeBuilder keepNbtOfReagent(boolean keepEnchantmentsOfReagent) {
      this.recipe.keepNbtOfReagent = keepEnchantmentsOfReagent;
      return this;
   }

   public ApparatusRecipeBuilder withSourceCost(int cost) {
      this.recipe.sourceCost = cost;
      return this;
   }

   public ApparatusRecipeBuilder withId(ResourceLocation id) {
      this.recipe.id = id;
      return this;
   }

   public EnchantingApparatusRecipe build() {
      if (this.recipe.id.m_135815_().equals("empty")) {
         this.recipe.id = new ResourceLocation("ars_nouveau", RegistryHelper.getRegistryName(this.recipe.result.m_41720_()).m_135815_());
      }

      if (this.recipe.result.m_41619_()) {
         throw new IllegalStateException("Enchanting Apparatus Recipe has no result");
      } else {
         return this.recipe;
      }
   }

   public EnchantmentRecipe buildEnchantmentRecipe(Enchantment enchantment, int level, int mana) {
      return new EnchantmentRecipe(this.recipe.pedestalItems, enchantment, level, mana);
   }
}
