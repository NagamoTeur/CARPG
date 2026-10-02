package dev.latvian.mods.kubejs.level.gen.filter.mob;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;

public record IDFilter(ResourceLocation id) implements MobFilter {
   @Override
   public boolean test(MobCategory cat, SpawnerData data) {
      return Registry.f_122826_.m_7981_(data.f_48404_).equals(this.id);
   }
}
