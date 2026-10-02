package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.brewing.AbstractBrewingRecipe;
import com.aizistral.enigmaticlegacy.brewing.ComplexBrewingRecipe;
import com.aizistral.enigmaticlegacy.brewing.SpecialBrewingRecipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class AdvancedBrewingRecipeProcessor implements IComponentProcessor {
   private AbstractBrewingRecipe recipe;

   public static List<IVariable> wrapStackList(ItemStack... stackList) {
      List<IVariable> variableList = new ArrayList<>();

      for (ItemStack catalyst : stackList) {
         variableList.add(IVariable.from(catalyst));
      }

      return variableList;
   }

   public static List<IVariable> wrapIngredientSet(Iterable<Ingredient> ingredientSet) {
      List<IVariable> variableList = new ArrayList<>();

      for (Ingredient ingredient : ingredientSet) {
         variableList.addAll(wrapStackList(ingredient.m_43908_()));
      }

      return variableList;
   }

   public void setup(IVariableProvider variables) {
      ResourceLocation recipeId = new ResourceLocation(variables.get("recipe").asString());
      int index = variables.get("index").asNumber().intValue();
      this.recipe = AbstractBrewingRecipe.recipeMap.containsKey(recipeId)
         ? AbstractBrewingRecipe.recipeMap.get(recipeId).get(index)
         : AbstractBrewingRecipe.EMPTY_RECIPE;
   }

   public IVariable process(String key) {
      if (this.recipe instanceof SpecialBrewingRecipe special) {
         if (key.startsWith("catalyst")) {
            return IVariable.wrapList(wrapStackList(special.getIngredient().m_43908_()));
         }

         if (key.startsWith("input")) {
            return IVariable.wrapList(wrapStackList(special.getInput().m_43908_()));
         }

         if (key.startsWith("output")) {
            return IVariable.from(special.getOutput());
         }
      } else if (this.recipe instanceof ComplexBrewingRecipe complex) {
         HashMap<Ingredient, Ingredient> processingMappings = complex.getProcessingMappings();
         if (key.startsWith("catalyst")) {
            return IVariable.wrapList(wrapIngredientSet(processingMappings.values()));
         }

         if (key.startsWith("input")) {
            return IVariable.wrapList(wrapIngredientSet(processingMappings.keySet()));
         }

         if (key.startsWith("output")) {
            return IVariable.from(complex.getOutput());
         }
      }

      return null;
   }
}
