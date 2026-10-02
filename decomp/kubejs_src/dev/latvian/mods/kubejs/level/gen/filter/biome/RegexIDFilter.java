package dev.latvian.mods.kubejs.level.gen.filter.biome;

import dev.architectury.registry.level.biome.BiomeModifications.BiomeContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;

public record RegexIDFilter(Pattern pattern) implements BiomeFilter {
   @Override
   public boolean test(BiomeContext ctx) {
      return ctx.getKey().<String>map(ResourceLocation::toString).map(this.pattern::matcher).map(Matcher::find).orElse(false);
   }
}
