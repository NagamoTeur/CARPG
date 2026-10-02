package dev.latvian.mods.kubejs.recipe;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public class RecipeSchemaRegistryEventJS extends EventJS {
   private final Map<String, RecipeNamespace> namespaces;
   private final Map<String, ResourceLocation> mappedRecipes;

   public RecipeSchemaRegistryEventJS(Map<String, RecipeNamespace> namespaces, Map<String, ResourceLocation> mappedRecipes) {
      this.namespaces = namespaces;
      this.mappedRecipes = mappedRecipes;
   }

   public RecipeNamespace namespace(String namespace) {
      return this.namespaces.computeIfAbsent(namespace, RecipeNamespace::new);
   }

   public void register(ResourceLocation id, RecipeSchema schema) {
      this.namespace(id.m_135827_()).register(id.m_135815_(), schema);
   }

   public void mapRecipe(String name, ResourceLocation type) {
      this.mappedRecipes.put(name, type);
   }

   public void mapRecipe(String name, String type) {
      this.mapRecipe(name, new ResourceLocation(type));
   }
}
