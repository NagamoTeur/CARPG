package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class SporeBlossomCaveDecorator implements ICaveDecorator {
   private final int bottomOfWorld;
   private final RandomSource random;
   private final List<BlockPos> sporeBlossomPositions = new ArrayList<>();
   private final ConfiguredFeature<SimpleBlockConfiguration, Feature<SimpleBlockConfiguration>> sporeBlossom = new ConfiguredFeature(
      Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(Blocks.f_152540_))
   );
   private final PlacedFeature placedFeature = new PlacedFeature(
      Holder.m_205709_(this.sporeBlossom),
      Arrays.asList(
         CountPlacement.m_191628_(25),
         InSquarePlacement.m_191715_(),
         PlacementUtils.f_195360_,
         EnvironmentScanPlacement.m_191657_(Direction.UP, BlockPredicate.m_190432_(), BlockPredicate.f_190393_, 12),
         RandomOffsetPlacement.m_191877_(ConstantInt.m_146483_(-1))
      )
   );

   public SporeBlossomCaveDecorator(int bottomOfWorld, RandomSource random) {
      this.bottomOfWorld = bottomOfWorld;
      this.random = random;
   }

   @Override
   public void onBlockPlaced(BlockPos pos, Block block) {
      if (pos.m_123342_() > 20 + this.bottomOfWorld && this.random.m_188503_(20) == 0 && block != Blocks.f_50016_) {
         this.sporeBlossomPositions.add(pos);
      }
   }

   @Override
   public void generate(
      WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, BlockPos pos, IStructurePiece structurePiece
   ) {
      Map<Pair<Integer, Integer>, List<BlockPos>> groupedSporeBlossomPositions = this.sporeBlossomPositions
         .stream()
         .collect(Collectors.groupingBy(p -> new Pair(p.m_123341_() >> 3, p.m_123343_() >> 3)));

      for (BlockPos sporePos : groupedSporeBlossomPositions.values().stream().map(list -> list.get(0)).toList()) {
         if (boundingBox.m_71051_(sporePos)) {
            this.placedFeature.m_226357_(level, chunkGenerator, random, sporePos);
         }
      }
   }
}
