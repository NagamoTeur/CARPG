package com.hollingsworth.arsnouveau.client.patchouli.component;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import vazkii.patchouli.api.IVariable;

public class RotatingItemListComponent extends RotatingItemListComponentBase {
   @SerializedName("recipe_name")
   public String recipeName;
   @SerializedName("recipe_type")
   public String recipeType;

   @Override
   protected List<Ingredient> makeIngredients() {
      ClientLevel world = Minecraft.m_91087_().f_91073_;
      if (world == null) {
         return new ArrayList<>();
      } else if (!"enchanting_apparatus".equals(this.recipeType)) {
         if ("imbuement_chamber".equals(this.recipeType)) {
            ImbuementRecipe recipe = world.m_7465_()
               .m_44013_((RecipeType)RecipeRegistry.IMBUEMENT_TYPE.get())
               .stream()
               .filter(f -> f.id.toString().equals(this.recipeName))
               .findFirst()
               .orElse(null);
            return (List<Ingredient>)(recipe == null ? ImmutableList.of() : recipe.pedestalItems);
         } else if ("glyph_recipe".equals(this.recipeType)) {
            GlyphRecipe recipe = (GlyphRecipe)world.m_7465_().m_44043_(new ResourceLocation(this.recipeName)).orElse(null);
            return (List<Ingredient>)(recipe == null ? ImmutableList.of() : recipe.inputs);
         } else {
            throw new IllegalArgumentException("Type must be 'enchanting_apparatus', 'glyph_recipe', or 'imbuement_chamber'!");
         }
      } else {
         EnchantingApparatusRecipe recipe = world.m_7465_()
            .m_44013_((RecipeType)RecipeRegistry.APPARATUS_TYPE.get())
            .stream()
            .filter(f -> f.id.toString().equals(this.recipeName))
            .findFirst()
            .orElse(null);

         for (RecipeType type : ArsNouveauAPI.getInstance().getEnchantingRecipeTypes()) {
            Recipe<?> recipe1 = world.m_7465_().m_44013_(type).stream().filter(f -> f.m_6423_().toString().equals(this.recipeName)).findFirst().orElse(null);
            if (recipe1 instanceof EnchantingApparatusRecipe enchantingApparatusRecipe) {
               recipe = enchantingApparatusRecipe;
               break;
            }
         }

         return (List<Ingredient>)(recipe == null ? ImmutableList.of() : recipe.pedestalItems);
      }
   }

   public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
      this.recipeName = lookup.apply(IVariable.wrap(this.recipeName)).asString();
      this.recipeType = lookup.apply(IVariable.wrap(this.recipeType)).asString();
   }
}
