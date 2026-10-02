package dev.latvian.mods.kubejs.level.gen.filter.mob;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public record CategoryFilter(MobCategory category) implements MobFilter {
   @Override
   public boolean test(MobCategory cat, SpawnerData data) {
      return cat == this.category;
   }
}
