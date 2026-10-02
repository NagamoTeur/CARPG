package io.redspace.ironsspellbooks.block.alchemist_cauldron;

import com.google.common.collect.ImmutableList;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.PotionRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class AlchemistCauldronRecipeRegistry {
   private static final Map<ResourceLocation, AlchemistCauldronRecipe> recipes = new HashMap<>();

   public static AlchemistCauldronRecipe registerRecipe(ResourceLocation resourceLocation, AlchemistCauldronRecipe recipe) {
      recipes.put(resourceLocation, recipe);
      return recipe;
   }

   private static AlchemistCauldronRecipe addRecipe(AlchemistCauldronRecipe recipe) {
      recipes.put(IronsSpellbooks.id(ForgeRegistries.ITEMS.getKey(recipe.getResult().m_41720_()).m_135815_()), recipe);
      return recipe;
   }

   public static ItemStack getOutput(ItemStack input, ItemStack ingredient, boolean consumeOnSucces) {
      if (!input.m_41619_() && !ingredient.m_41619_()) {
         for (AlchemistCauldronRecipe recipe : recipes.values()) {
            ItemStack result = recipe.createOutput(input, ingredient, false, consumeOnSucces);
            if (!result.m_41619_()) {
               return result;
            }
         }

         return ItemStack.f_41583_;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public static ItemStack getOutput(ItemStack input, ItemStack ingredient, boolean ignoreCount, boolean consumeOnSucces) {
      if (!input.m_41619_() && !ingredient.m_41619_()) {
         for (AlchemistCauldronRecipe recipe : recipes.values()) {
            ItemStack result = recipe.createOutput(input, ingredient, ignoreCount, consumeOnSucces);
            if (!result.m_41619_()) {
               return result;
            }
         }

         return ItemStack.f_41583_;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public static boolean isValidIngredient(ItemStack itemStack) {
      for (AlchemistCauldronRecipe recipe : recipes.values()) {
         if (CauldronPlatformHelper.itemMatches(recipe.getIngredient(), itemStack)) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasOutput(ItemStack input, ItemStack ingredient) {
      return !getOutput(input, ingredient, true, false).m_41619_();
   }

   @Nullable
   public static AlchemistCauldronRecipe getRecipeForResult(ItemStack result) {
      for (AlchemistCauldronRecipe recipe : recipes.values()) {
         if (CauldronPlatformHelper.itemMatches(result, recipe.getResult())) {
            return recipe;
         }
      }

      return null;
   }

   @Nullable
   public static AlchemistCauldronRecipe getRecipeForInputs(ItemStack base, ItemStack reagent) {
      for (AlchemistCauldronRecipe recipe : recipes.values()) {
         if (CauldronPlatformHelper.itemMatches(base, recipe.getInput()) && CauldronPlatformHelper.itemMatches(reagent, recipe.getIngredient())) {
            return recipe;
         }
      }

      return null;
   }

   public static ImmutableList<AlchemistCauldronRecipe> getRecipes() {
      return ImmutableList.copyOf(recipes.values());
   }

   static {
      addRecipe(new AlchemistCauldronRecipe(Potions.f_43581_, Items.f_41837_, (Item)ItemRegistry.OAKSKIN_ELIXIR.get()).setBaseRequirement(2).setResultLimit(1));
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.OAKSKIN_ELIXIR.get(), Items.f_151049_, (Item)ItemRegistry.GREATER_OAKSKIN_ELIXIR.get())
            .setBaseRequirement(2)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe(Potions.f_43581_, Items.f_151049_, (Item)ItemRegistry.GREATER_HEALING_POTION.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe(Potions.f_43605_, (Item)ItemRegistry.SHRIVING_STONE.get(), (Item)ItemRegistry.INVISIBILITY_ELIXIR.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe(Potions.f_43606_, (Item)ItemRegistry.SHRIVING_STONE.get(), (Item)ItemRegistry.INVISIBILITY_ELIXIR.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.INVISIBILITY_ELIXIR.get(), Items.f_151086_, (Item)ItemRegistry.GREATER_INVISIBILITY_ELIXIR.get())
      );
      addRecipe(
         new AlchemistCauldronRecipe((Potion)PotionRegistry.INSTANT_MANA_THREE.get(), Items.f_42584_, (Item)ItemRegistry.EVASION_ELIXIR.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(new AlchemistCauldronRecipe((Item)ItemRegistry.EVASION_ELIXIR.get(), Items.f_42735_, (Item)ItemRegistry.GREATER_EVASION_ELIXIR.get()));
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.INK_COMMON.get(), Items.f_151052_, (Item)ItemRegistry.INK_UNCOMMON.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.INK_UNCOMMON.get(), Items.f_42416_, (Item)ItemRegistry.INK_RARE.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.INK_RARE.get(), Items.f_42417_, (Item)ItemRegistry.INK_EPIC.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
      addRecipe(
         new AlchemistCauldronRecipe((Item)ItemRegistry.INK_EPIC.get(), Items.f_151049_, (Item)ItemRegistry.INK_LEGENDARY.get())
            .setBaseRequirement(4)
            .setResultLimit(1)
      );
   }
}
