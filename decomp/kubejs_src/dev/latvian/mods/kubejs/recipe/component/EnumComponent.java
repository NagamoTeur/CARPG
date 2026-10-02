package dev.latvian.mods.kubejs.recipe.component;

import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.util.function.BiFunction;
import java.util.function.Function;

public record EnumComponent<T extends Enum<T>>(Class<T> enumType, Function<T, String> toStringFunc, BiFunction<Class<T>, String, T> toEnumFunc)
   implements RecipeComponent<T> {
   private static final Function<Enum<?>, String> DEFAULT_TO_STRING = e -> e.name().toLowerCase();
   private static final BiFunction<Class<? extends Enum<?>>, String, Enum<?>> DEFAULT_TO_ENUM = (c, s) -> {
      for (Enum<?> e : c.getEnumConstants()) {
         if (e.name().equalsIgnoreCase(s)) {
            return e;
         }
      }

      return null;
   };

   public EnumComponent(Class<T> enumType) {
      this(enumType, UtilsJS.cast(DEFAULT_TO_STRING), UtilsJS.cast(DEFAULT_TO_ENUM));
   }

   @Override
   public String componentType() {
      return "enum";
   }

   @Override
   public Class<?> componentClass() {
      return this.enumType;
   }

   public JsonPrimitive write(RecipeJS recipe, T value) {
      return new JsonPrimitive(this.toStringFunc.apply(value));
   }

   public T read(RecipeJS recipe, Object from) {
      if (this.enumType.isInstance(from)) {
         return (T)from;
      } else {
         T e = from == null ? null : this.toEnumFunc.apply(this.enumType, String.valueOf(from));
         if (e == null) {
            throw new RecipeExceptionJS("Enum value '" + from + "' of " + this.enumType.getName() + " not found");
         } else {
            return e;
         }
      }
   }

   @Override
   public String toString() {
      return this.componentType();
   }
}
