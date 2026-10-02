package dev.latvian.mods.kubejs.script;

import dev.architectury.registry.registries.Registrar;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.Lazy;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.wrap.TypeWrapperFactory;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record RegistryTypeWrapperFactory<T>(RegistryInfo info, ResourceKey<Registry<T>> key, Registrar<T>[] registrar) implements TypeWrapperFactory<T> {
   public static final Lazy<List<RegistryTypeWrapperFactory<?>>> ALL = Lazy.of(() -> {
      ArrayList<RegistryTypeWrapperFactory<?>> all = new ArrayList<>();

      for (RegistryInfo ri : RegistryInfo.MAP.values()) {
         if (ri.autoWrap) {
            all.add(new RegistryTypeWrapperFactory(ri, UtilsJS.cast(ri.key), new Registrar[1]));
         }
      }

      return all;
   });

   public T wrap(Context cx, Object o) {
      if (o == null) {
         return null;
      } else if (this.info.objectBaseClass.isInstance(o)) {
         return (T)o;
      } else {
         if (this.registrar[0] == null) {
            this.registrar[0] = KubeJSRegistries.genericRegistry(this.key);
         }

         ResourceLocation id = UtilsJS.getMCID(cx, o);
         T value = (T)this.registrar[0].get(id);
         if (value == null) {
            NullPointerException npe = new NullPointerException("No such element with id %s in registry %s!".formatted(id, this.info));
            ConsoleJS.getCurrent(cx).error("Error while wrapping registry element type!", npe);
            throw npe;
         } else {
            return value;
         }
      }
   }

   @Override
   public String toString() {
      return "RegistryTypeWrapperFactory{type=" + this.info.objectBaseClass.getName() + ", registry=" + this.info + "}";
   }
}
