package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class MossFloorCaveDecorator implements ICaveDecorator {
   private final int bottomOfWorld;
   private final RandomSource random;
   private final List<BlockPos> mossFloorPositions = new ArrayList<>();

   public MossFloorCaveDecorator(int bottomOfWorld, RandomSource random) {
      this.bottomOfWorld = bottomOfWorld;
      this.random = random;
   }

   @Override
   public void onBlockPlaced(BlockPos pos, Block block) {
      if (pos.m_123342_() == 4 + this.bottomOfWorld && this.random.m_188503_(80) == 0) {
         this.mossFloorPositions.add(pos);
      }
   }

   @Override
   public void generate(
      WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, BlockPos pos, IStructurePiece structurePiece
   ) {
      Map<Pair<Integer, Integer>, List<BlockPos>> groupedMossPositions = this.mossFloorPositions
         .stream()
         .collect(Collectors.groupingBy(p -> new Pair(p.m_123341_() >> 3, p.m_123343_() >> 3)));

      for (BlockPos mossPos : groupedMossPositions.values().stream().map(list -> list.get(0)).toList()) {
         if (boundingBox.m_71051_(mossPos)) {
            ConfiguredFeature<?, ?> configuredFeature = (ConfiguredFeature<?, ?>)CaveFeatures.f_194950_.get();
            Feature.f_159734_
               .m_142674_(
                  new FeaturePlaceContext(
                     Optional.of(configuredFeature), level, chunkGenerator, random, mossPos, (VegetationPatchConfiguration)configuredFeature.f_65378_()
                  )
               );
         }
      }
   }
}
