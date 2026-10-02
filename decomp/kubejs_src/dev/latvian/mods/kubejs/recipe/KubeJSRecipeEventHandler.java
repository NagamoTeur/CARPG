package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.recipe.special.ShapedKubeJSRecipe;
import dev.latvian.mods.kubejs.recipe.special.ShapelessKubeJSRecipe;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class KubeJSRecipeEventHandler {
   public static Supplier<RecipeSerializer<?>> SHAPED;
   public static Supplier<RecipeSerializer<?>> SHAPELESS;

   public static void init() {
      if (!CommonProperties.get().serverOnly) {
         registry();
      }
   }

   private static void registry() {
      SHAPED = KubeJSRegistries.recipeSerializers().register(new ResourceLocation("kubejs", "shaped"), ShapedKubeJSRecipe.SerializerKJS::new);
      SHAPELESS = KubeJSRegistries.recipeSerializers().register(new ResourceLocation("kubejs", "shapeless"), ShapelessKubeJSRecipe.SerializerKJS::new);
   }
}
