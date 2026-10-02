package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.util.IPieceGenerator;
import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class VoidBlossomCavernPieceGenerator implements IPieceGenerator {
   @Override
   public void generate(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPos,
      BlockPos pos,
      IStructurePiece structurePiece
   ) {
      int minY = chunkGenerator.m_142062_();
      List<ICaveDecorator> caveDecorators = Arrays.asList(
         new SpikeCaveDecorator(minY, random),
         new MossFloorCaveDecorator(minY, random),
         new MossCeilingCaveDecorator(minY, random),
         new SporeBlossomCaveDecorator(minY, random),
         new BossBlockDecorator(minY)
      );
      this.generateCave(level, pos.m_6630_(17), structurePiece, random, boundingBox, caveDecorators, chunkGenerator.m_142062_());

      for (ICaveDecorator caveDecorator : caveDecorators) {
         caveDecorator.generate(level, chunkGenerator, random, boundingBox, pos, structurePiece);
      }
   }

   private void generateCave(
      WorldGenLevel level,
      BlockPos pos,
      IStructurePiece structurePiece,
      RandomSource random,
      BoundingBox boundingBox,
      List<ICaveDecorator> caveDecorators,
      int bottomOfWorld
   ) {
      double noiseMultiplier = 0.005;
      UniformInt outerWallDistance = UniformInt.m_146622_(3, 4);
      UniformInt pointOffset = UniformInt.m_146622_(1, 2);
      int minY = bottomOfWorld - pos.m_123342_();
      int maxY = minY + 32;
      int minXZ = -32;
      int maxXZ = 32;
      double verticalSquish = 2.0;
      int distributionPoints = 5;
      List<Pair<BlockPos, Integer>> randoms = new LinkedList<>();
      WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(level.m_7328_()));
      NormalNoise normalNoise = NormalNoise.m_230504_(worldgenRandom, -4, new double[]{1.0});
      double d = (double)distributionPoints / (double)outerWallDistance.m_142737_();
      double airThickness = 1.0 / Math.sqrt(25.2 + d);
      double outerLayerThickness = 1.0 / Math.sqrt(30.2 + d);

      for (int r = 0; r < distributionPoints; r++) {
         BlockPos randomPos = pos.m_7918_(outerWallDistance.m_214085_(random), outerWallDistance.m_214085_(random), outerWallDistance.m_214085_(random));
         randoms.add(Pair.of(randomPos, pointOffset.m_214085_(random)));
      }

      Predicate<BlockState> predicate = Feature.m_204735_(BlockTags.f_144287_);

      for (BlockPos samplePos : BlockPos.m_121940_(pos.m_7918_(minXZ, minY, minXZ), pos.m_7918_(maxXZ, maxY, maxXZ))) {
         double noise = normalNoise.m_75380_((double)samplePos.m_123341_(), (double)samplePos.m_123342_(), (double)samplePos.m_123343_()) * noiseMultiplier;
         double noisedDistance = 0.0;

         for (Pair<BlockPos, Integer> pair : randoms) {
            BlockPos distancePos = new BlockPos(
               samplePos.m_123341_(), (int)((double)(samplePos.m_123342_() - bottomOfWorld) * verticalSquish + (double)bottomOfWorld), samplePos.m_123343_()
            );
            noisedDistance += Mth.m_14193_(distancePos.m_123331_((Vec3i)pair.getFirst()) + (double)((Integer)pair.getSecond()).intValue()) + noise;
         }

         if (!(noisedDistance < outerLayerThickness)) {
            Supplier<Boolean> canReplace = () -> predicate.test(level.m_8055_(samplePos))
                  && !level.m_8055_(samplePos).m_60795_()
                  && level.m_8055_(samplePos).m_60734_() != Blocks.f_50752_
                  && samplePos.m_123342_() > 4 + bottomOfWorld;
            if (noisedDistance >= airThickness) {
               this.replaceBlock(canReplace, level, samplePos, structurePiece, boundingBox, Blocks.f_50016_.m_49966_(), caveDecorators);
            } else if (noisedDistance >= outerLayerThickness) {
               this.replaceBlock(
                  canReplace,
                  level,
                  samplePos,
                  structurePiece,
                  boundingBox,
                  samplePos.m_123342_() > 0 ? Blocks.f_50069_.m_49966_() : Blocks.f_152550_.m_49966_(),
                  caveDecorators
               );
            }
         }
      }
   }

   private void replaceBlock(
      Supplier<Boolean> predicate,
      WorldGenLevel level,
      BlockPos samplePos,
      IStructurePiece structurePiece,
      BoundingBox boundingBox,
      BlockState blockState,
      List<ICaveDecorator> caveDecorators
   ) {
      if (predicate.get()) {
         structurePiece.placeBlock(level, blockState, samplePos, boundingBox);
      }

      for (ICaveDecorator decorator : caveDecorators) {
         decorator.onBlockPlaced(new BlockPos(samplePos), blockState.m_60734_());
      }
   }
}
