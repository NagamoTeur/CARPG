package com.aizistral.enigmaticlegacy.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ValidationBrewingRecipe implements IBrewingRecipe {
   private final Ingredient input;
   private final Ingredient ingredient;

   public ValidationBrewingRecipe(Ingredient input, Ingredient ingredient) {
      this.input = input;
      this.ingredient = ingredient;
   }

   public boolean isInput(ItemStack stack) {
      return stack != null && this.input != null && this.input.test(stack);
   }

   public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
      return ItemStack.f_41583_;
   }

   public Ingredient getInput() {
      return this.input;
   }

   public Ingredient getIngredient() {
      return this.ingredient;
   }

   public ItemStack getOutput() {
      return ItemStack.f_41583_;
   }

   public boolean isIngredient(ItemStack ingredient) {
      return ingredient != null && this.ingredient != null ? this.ingredient.test(ingredient) : false;
   }
}
