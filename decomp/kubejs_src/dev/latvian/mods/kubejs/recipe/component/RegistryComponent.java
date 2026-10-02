package dev.latvian.mods.kubejs.recipe.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.architectury.registry.registries.Registrar;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.typings.desc.DescriptionContext;
import dev.latvian.mods.kubejs.typings.desc.TypeDescJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

public record RegistryComponent<T>(ResourceKey<? extends Registry<T>> registry, Class<?> registryType) implements RecipeComponent<T> {
   @Override
   public String componentType() {
      return "registry_element";
   }

   @Override
   public TypeDescJS constructorDescription(DescriptionContext ctx) {
      return TypeDescJS.STRING.or(ctx.javaType(this.registryType));
   }

   @Override
   public Class<?> componentClass() {
      return this.registryType;
   }

   private Registrar<T> reg() {
      return KubeJSRegistries.genericRegistry(UtilsJS.cast(this.registry));
   }

   @Override
   public JsonElement write(RecipeJS recipe, T value) {
      return new JsonPrimitive(this.reg().getId(value).toString());
   }

   @Override
   public T read(RecipeJS recipe, Object from) {
      if (this.registryType.isInstance(from)) {
         return (T)from;
      } else if (this.registry.equals(Registry.f_122904_) && from instanceof ItemStack stack) {
         return (T)stack.m_41720_();
      } else if (this.registry.equals(Registry.f_122899_) && from instanceof FluidStackJS fluid) {
         return (T)fluid.getFluid();
      } else {
         String s = String.valueOf(from);
         return (T)this.reg().get(UtilsJS.getMCID(null, s));
      }
   }

   @Override
   public boolean hasPriority(RecipeJS recipe, Object from) {
      if (this.registryType.isInstance(from) || from instanceof CharSequence && UtilsJS.getMCID(null, from.toString()) != null) {
         return true;
      } else {
         if (from instanceof JsonPrimitive json && json.isString() && UtilsJS.getMCID(null, json.getAsString()) != null) {
            return true;
         }

         return false;
      }
   }

   @Override
   public String toString() {
      return "%s{%s}".formatted(this.componentType(), this.registry.m_135782_());
   }
}
