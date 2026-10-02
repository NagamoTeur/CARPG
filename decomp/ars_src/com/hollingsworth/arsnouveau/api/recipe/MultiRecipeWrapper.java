package com.hollingsworth.arsnouveau.api.recipe;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.brewing.BrewingRecipe;
import org.jetbrains.annotations.Nullable;

public class MultiRecipeWrapper implements IRecipeWrapper {
   public Set<SingleRecipe> recipes = new HashSet<>();
   public static Map<Item, MultiRecipeWrapper> RECIPE_CACHE = new HashMap<>();

   public static MultiRecipeWrapper fromStack(ItemStack stack, Level level) {
      MultiRecipeWrapper wrapper = new MultiRecipeWrapper();
      if (stack.m_41720_() == Items.f_42589_) {
         for (BrewingRecipe r : ArsNouveauAPI.getInstance().getAllPotionRecipes()) {
            if (ItemStack.m_41728_(stack, r.getOutput())) {
               List<Ingredient> list = new ArrayList<>();
               list.add(new PotionIngredient(r.getInput().m_43908_()[0]));
               list.add(r.getIngredient());
               wrapper.addRecipe(list, r.getOutput(), null);
            }
         }
      } else {
         if (RECIPE_CACHE.containsKey(stack.m_41720_())) {
            return RECIPE_CACHE.get(stack.m_41720_());
         }

         for (Recipe rx : level.m_7654_().m_129894_().m_44051_()) {
            if (rx.m_8043_() != null && rx.m_8043_().m_41720_() == stack.m_41720_()) {
               if (rx instanceof ShapedRecipe) {
                  ShapedHelper helper = new ShapedHelper((ShapedRecipe)rx);

                  for (List<Ingredient> iList : helper.getPossibleRecipes()) {
                     wrapper.addRecipe(iList, rx.m_8043_(), rx);
                  }
               }

               if (rx instanceof ShapelessRecipe) {
                  wrapper.addRecipe(rx.m_7527_(), rx.m_8043_(), rx);
               }
            }
         }

         RECIPE_CACHE.put(stack.m_41720_(), wrapper);
      }

      return wrapper;
   }

   @Nullable
   @Override
   public IRecipeWrapper.InstructionsForRecipe canCraft(Map<Item, Integer> inventory, Level world, BlockPos pos) {
      for (SingleRecipe recipe : this.recipes) {
         List<ItemStack> itemsNeeded = this.getItemsNeeded(inventory, world, pos, recipe);
         if (itemsNeeded != null) {
            return new IRecipeWrapper.InstructionsForRecipe(recipe, itemsNeeded);
         }
      }

      return null;
   }

   @Nullable
   public List<ItemStack> getItemsNeeded(Map<Item, Integer> inventory, Level world, BlockPos pos, SingleRecipe recipe) {
      Map<Item, Integer> map = new HashMap<>(inventory);
      List<ItemStack> items = new ArrayList<>();

      for (Ingredient i : recipe.recipeIngredients) {
         boolean foundStack = false;

         for (ItemStack stack : i.m_43908_()) {
            if (stack.m_41720_() == Items.f_42589_) {
               Potion potion = PotionUtils.m_43579_(stack);
               if (potion == Potions.f_43599_ || WixieCauldronTile.findNeededPotion(potion, 300, world, pos) != null) {
                  foundStack = true;
                  break;
               }
            }

            if (inventory.containsKey(stack.m_41720_()) && map.get(stack.m_41720_()) > 0) {
               map.put(stack.m_41720_(), map.get(stack.m_41720_()) - 1);
               foundStack = true;
               items.add(stack.m_41777_());
               break;
            }
         }

         if (!foundStack) {
            return null;
         }
      }

      return items;
   }

   public boolean addRecipe(List<Ingredient> recipe, ItemStack outputStack, Recipe iRecipe) {
      return this.recipes.add(new SingleRecipe(recipe, outputStack, iRecipe));
   }

   public boolean isEmpty() {
      return this.recipes.isEmpty();
   }
}
