package dev.latvian.mods.kubejs.recipe.schema;

import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSchemaType {
   public final RecipeNamespace namespace;
   public final ResourceLocation id;
   public final RecipeSchema schema;
   public RecipeSchemaType parent;
   protected Optional<RecipeSerializer<?>> serializer;

   public RecipeSchemaType(RecipeNamespace namespace, ResourceLocation id, RecipeSchema schema) {
      this.namespace = namespace;
      this.id = id;
      this.schema = schema;
   }

   public RecipeSerializer<?> getSerializer() {
      if (this.serializer == null) {
         this.serializer = Optional.ofNullable((RecipeSerializer<?>)KubeJSRegistries.recipeSerializers().get(this.id));
      }

      RecipeSerializer<?> s = this.serializer.orElse(null);
      if (s == null) {
         throw new RecipeExceptionJS("Serializer for type " + this.id + " is not found!");
      } else {
         return s;
      }
   }

   @Override
   public String toString() {
      return this.id.toString();
   }
}
