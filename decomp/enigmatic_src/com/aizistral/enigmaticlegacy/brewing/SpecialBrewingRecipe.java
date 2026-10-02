package com.aizistral.enigmaticlegacy.brewing;

import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

public class SpecialBrewingRecipe extends AbstractBrewingRecipe {
   @Nonnull
   private final Ingredient input;
   @Nonnull
   private final Ingredient ingredient;
   @Nonnull
   private final ItemStack output;

   public SpecialBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
      this(input, ingredient, output, new ResourceLocation("enigmaticlegacy", ItemNBTHelper.getString(output, "EnigmaticPotion", "unknown")));
   }

   public SpecialBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output, ResourceLocation name) {
      super(name);
      this.input = input;
      this.ingredient = ingredient;
      this.output = output;
   }

   public boolean isInput(@Nonnull ItemStack stack) {
      if (stack != null) {
         for (ItemStack testStack : this.getInput().m_43908_()) {
            if (testStack.m_41720_() == stack.m_41720_() && PotionUtils.m_43579_(testStack) == PotionUtils.m_43579_(stack)) {
               return true;
            }
         }
      }

      return false;
   }

   public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
      return this.isInput(input) && this.isIngredient(ingredient) ? this.getOutput().m_41777_() : ItemStack.f_41583_;
   }

   public Ingredient getInput() {
      return this.input;
   }

   public Ingredient getIngredient() {
      return this.ingredient;
   }

   public ItemStack getOutput() {
      return this.output;
   }

   public boolean isIngredient(ItemStack ingredient) {
      return ingredient != null ? this.ingredient.test(ingredient) : false;
   }
}
