package dev.latvian.mods.kubejs.level.gen.filter.biome;

import dev.architectury.registry.level.biome.BiomeModifications.BiomeContext;
import net.minecraft.resources.ResourceLocation;

public record IDFilter(ResourceLocation id) implements BiomeFilter {
   @Override
   public boolean test(BiomeContext ctx) {
      return ctx.getKey().<Boolean>map(this.id::equals).orElse(false);
   }
}
