package dev.latvian.mods.kubejs.level.gen.filter.biome;

import dev.architectury.registry.level.biome.BiomeModifications.BiomeContext;
import java.util.List;

public record OrFilter(List<BiomeFilter> list) implements BiomeFilter {
   @Override
   public boolean test(BiomeContext ctx) {
      for (BiomeFilter filter : this.list) {
         if (filter.test(ctx)) {
            return true;
         }
      }

      return false;
   }
}
