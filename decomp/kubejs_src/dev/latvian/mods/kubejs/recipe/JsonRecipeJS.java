package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.CommonProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class JsonRecipeJS extends RecipeJS {
   @Override
   public void deserialize(boolean merge) {
   }

   @Override
   public void serialize() {
   }

   @Override
   public boolean hasInput(ReplacementMatch match) {
      if (CommonProperties.get().matchJsonRecipes && match instanceof ItemMatch m && this.getOriginalRecipe() != null) {
         for (Ingredient ingredient : this.getOriginalRecipe().m_7527_()) {
            if (ingredient != Ingredient.f_43901_ && ingredient.kjs$canBeUsedForMatching() && m.contains(ingredient)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean replaceInput(ReplacementMatch match, InputReplacement with) {
      return false;
   }

   @Override
   public boolean hasOutput(ReplacementMatch match) {
      if (CommonProperties.get().matchJsonRecipes && match instanceof ItemMatch m && this.getOriginalRecipe() != null) {
         ItemStack r = this.getOriginalRecipe().m_8043_();
         return r != null && r != ItemStack.f_41583_ && m.contains(r);
      }

      return false;
   }

   @Override
   public boolean replaceOutput(ReplacementMatch match, OutputReplacement with) {
      return false;
   }
}
