package dev.latvian.mods.kubejs.recipe.filter;

import dev.latvian.mods.kubejs.core.RecipeKJS;

public record NotFilter(RecipeFilter original) implements RecipeFilter {
   @Override
   public boolean test(RecipeKJS r) {
      return !this.original.test(r);
   }

   @Override
   public String toString() {
      return "NotFilter{" + this.original + "}";
   }
}
