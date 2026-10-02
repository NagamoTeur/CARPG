package com.hollingsworth.arsnouveau.client.patchouli;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantmentRecipe;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class EnchantmentProcessor implements IComponentProcessor {
   EnchantmentRecipe recipe;

   public void setup(IVariableProvider variables) {
      RecipeManager manager = Minecraft.m_91087_().f_91073_.m_7465_();
      String recipeID = variables.get("recipe").asString();
      this.recipe = (EnchantmentRecipe)manager.m_44043_(new ResourceLocation(recipeID)).orElse(null);
   }

   public IVariable process(String key) {
      if (this.recipe == null) {
         return null;
      } else if (key.equals("enchantment")) {
         return IVariable.wrap(this.recipe.enchantment.m_44704_());
      } else if (key.equals("level")) {
         return IVariable.wrap(this.recipe.enchantLevel);
      } else if (key.startsWith("item")) {
         int index = Integer.parseInt(key.substring(4)) - 1;
         if (this.recipe.pedestalItems.size() <= index) {
            return IVariable.from(ItemStack.f_41583_);
         } else {
            Ingredient ingredient = this.recipe.pedestalItems.get(Integer.parseInt(key.substring(4)) - 1);
            return IVariable.wrapList(Arrays.stream(ingredient.m_43908_()).map(IVariable::from).collect(Collectors.toList()));
         }
      } else {
         return null;
      }
   }
}
