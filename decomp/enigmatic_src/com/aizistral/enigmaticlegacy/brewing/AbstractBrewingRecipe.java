package com.aizistral.enigmaticlegacy.brewing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public abstract class AbstractBrewingRecipe implements IBrewingRecipe {
   public static HashMap<ResourceLocation, List<AbstractBrewingRecipe>> recipeMap = new HashMap<>();
   public static final AbstractBrewingRecipe EMPTY_RECIPE = new SpecialBrewingRecipe(
      Ingredient.m_43927_(new ItemStack[]{ItemStack.f_41583_}),
      Ingredient.m_43927_(new ItemStack[]{ItemStack.f_41583_}),
      ItemStack.f_41583_,
      new ResourceLocation("enigmaticlegacy", "empty_recipe")
   );

   public AbstractBrewingRecipe(ResourceLocation registryName) {
      if (recipeMap.containsKey(registryName)) {
         List<AbstractBrewingRecipe> list = recipeMap.get(registryName);
         list.add(this);
         recipeMap.put(registryName, list);
      } else {
         List<AbstractBrewingRecipe> list = new ArrayList<>();
         list.add(this);
         recipeMap.put(registryName, list);
      }
   }
}
