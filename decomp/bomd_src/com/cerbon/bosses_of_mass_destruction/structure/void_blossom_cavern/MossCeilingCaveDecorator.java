package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class MossCeilingCaveDecorator implements ICaveDecorator {
   private final int bottomOfWorld;
   private final RandomSource random;
   private final List<BlockPos> mossCeilingPositions = new ArrayList<>();

   public MossCeilingCaveDecorator(int bottomOfWorld, RandomSource random) {
      this.bottomOfWorld = bottomOfWorld;
      this.random = random;
   }

   @Override
   public void onBlockPlaced(BlockPos pos, Block block) {
      if (pos.m_123342_() > 18 + this.bottomOfWorld && this.random.m_188503_(20) == 0 && block != Blocks.f_50016_) {
         this.mossCeilingPositions.add(pos);
      }
   }

   @Override
   public void generate(
      WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, BlockPos pos, IStructurePiece structurePiece
   ) {
      Map<Pair<Integer, Integer>, List<BlockPos>> groupedMossCeilingPositions = this.mossCeilingPositions
         .stream()
         .collect(Collectors.groupingBy(p -> new Pair(p.m_123341_() >> 3, p.m_123343_() >> 3)));

      for (BlockPos mossPoss : groupedMossCeilingPositions.values().stream().map(list -> list.get(0)).toList()) {
         if (boundingBox.m_71051_(mossPoss)) {
            ((ConfiguredFeature)CaveFeatures.f_194956_.get()).m_224953_(level, chunkGenerator, random, mossPoss);
         }
      }
   }
}
