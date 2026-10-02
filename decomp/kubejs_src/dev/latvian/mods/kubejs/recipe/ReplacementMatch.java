package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import net.minecraft.world.item.crafting.Ingredient;

public interface ReplacementMatch {
   ReplacementMatch NONE = new ReplacementMatch() {
      @Override
      public String toString() {
         return "NONE";
      }
   };

   static ReplacementMatch of(Object o) {
      if (o == null) {
         return NONE;
      } else if (o instanceof ReplacementMatch) {
         return (ReplacementMatch)o;
      } else {
         Ingredient in = IngredientJS.of(o);
         if (in.m_43947_()) {
            return NONE;
         } else {
            return (ReplacementMatch)(in.m_43908_().length == 1 ? new SingleItemMatch(in.m_43908_()[0]) : new IngredientMatch(in, false));
         }
      }
   }
}
