package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.api.items.IHidden;
import java.util.ArrayList;
import java.util.Collection;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class EnigmaticJEIPlugin implements IModPlugin {
   private static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "common_plugin");

   public ResourceLocation getPluginUid() {
      return ID;
   }

   public void registerIngredients(IModIngredientRegistration registration) {
   }

   public void registerRecipes(IRecipeRegistration registration) {
      IIngredientManager manager = registration.getIngredientManager();
      Collection<ItemStack> ingredients = manager.getAllIngredients(VanillaTypes.ITEM_STACK);
      Collection<ItemStack> removals = new ArrayList<>();
      ingredients.forEach(stack -> {
         if (stack != null && stack.m_41720_() instanceof IHidden) {
            removals.add(stack);
         }
      });
      manager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, removals);
   }
}
