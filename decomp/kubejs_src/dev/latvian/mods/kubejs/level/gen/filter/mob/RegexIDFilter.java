package dev.latvian.mods.kubejs.level.gen.filter.mob;

import java.util.regex.Pattern;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public record RegexIDFilter(Pattern pattern) implements MobFilter {
   @Override
   public boolean test(MobCategory cat, SpawnerData data) {
      return this.pattern.matcher(Registry.f_122826_.m_7981_(data.f_48404_).toString()).find();
   }
}
