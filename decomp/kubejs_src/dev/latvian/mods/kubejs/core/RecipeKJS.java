package dev.latvian.mods.kubejs.core;

import dev.latvian.mods.kubejs.recipe.InputReplacement;
import dev.latvian.mods.kubejs.recipe.ItemMatch;
import dev.latvian.mods.kubejs.recipe.OutputReplacement;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

@RemapPrefixForJS("kjs$")
public interface RecipeKJS {
   default String kjs$getGroup() {
      return ((Recipe)this).m_6076_();
   }

   default void kjs$setGroup(String group) {
   }

   default ResourceLocation kjs$getOrCreateId() {
      return ((Recipe)this).m_6423_();
   }

   default RecipeSchema kjs$getSchema() {
      ResourceLocation s = KubeJSRegistries.recipeSerializers().getId(((Recipe)this).m_7707_());
      return RecipeNamespace.getAll().get(s.m_135827_()).get(s.m_135815_()).schema;
   }

   default String kjs$getMod() {
      return this.kjs$getOrCreateId().m_135827_();
   }

   default ResourceLocation kjs$getType() {
      return KubeJSRegistries.recipeSerializers().getId(((Recipe)this).m_7707_());
   }

   default boolean hasInput(ReplacementMatch match) {
      if (match instanceof ItemMatch m) {
         for (Ingredient in : ((Recipe)this).m_7527_()) {
            if (m.contains(in)) {
               return true;
            }
         }
      }

      return false;
   }

   default boolean replaceInput(ReplacementMatch match, InputReplacement with) {
      return false;
   }

   default boolean hasOutput(ReplacementMatch match) {
      if (!(match instanceof ItemMatch m)) {
         return false;
      } else {
         ItemStack result = ((Recipe)this).m_8043_();
         return result != null && result != ItemStack.f_41583_ && !result.m_41619_() && m.contains(result);
      }
   }

   default boolean replaceOutput(ReplacementMatch match, OutputReplacement with) {
      return false;
   }
}
