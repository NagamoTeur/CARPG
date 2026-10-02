package com.hollingsworth.arsnouveau.common.world.biome;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class ArchwoodRegion extends Region {
   public ArchwoodRegion(ResourceLocation name, int weight) {
      super(name, RegionType.OVERWORLD, weight);
   }

   public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper) {
      this.addModifiedVanillaOverworldBiomes(mapper, builder -> builder.replaceBiome(Biomes.f_48205_, ModBiomes.ARCHWOOD_FOREST));
   }
}
