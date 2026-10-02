package dev.latvian.mods.kubejs.level.gen.filter.biome;

import dev.architectury.registry.level.biome.BiomeModifications.BiomeContext;

public record NotFilter(BiomeFilter original) implements BiomeFilter {
   @Override
   public boolean test(BiomeContext ctx) {
      return !this.original.test(ctx);
   }
}
