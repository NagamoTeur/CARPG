package dev.latvian.mods.kubejs.level.gen.properties;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.level.gen.filter.biome.BiomeFilter;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class RemoveOresProperties {
   public Decoration worldgenLayer = Decoration.UNDERGROUND_ORES;
   public BlockStatePredicate blocks = BlockStatePredicate.Simple.NONE;
   public BiomeFilter biomes = BiomeFilter.ALWAYS_TRUE;
}
