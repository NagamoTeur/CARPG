package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.structure.util.IStructurePiece;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

public class SpikeCaveDecorator implements ICaveDecorator {
   private final int bottomOfWorld;
   private final RandomSource random;
   private final List<BlockPos> spikePositions = new ArrayList<>();
   private final List<Vec3> baseBlocks = MathUtils.buildBlockCircle(4.2);

   public SpikeCaveDecorator(int bottomOfWorld, RandomSource random) {
      this.bottomOfWorld = bottomOfWorld;
      this.random = random;
   }

   @Override
   public void onBlockPlaced(BlockPos pos, Block block) {
      double spikeSpacing = Math.pow((double)this.random.m_188503_(20) + 10.0, 2.0);
      if (pos.m_123342_() == 5 + this.bottomOfWorld
         && block != Blocks.f_50016_
         && this.spikePositions.stream().allMatch(it -> it.m_123331_(pos) > spikeSpacing)) {
         this.spikePositions.add(pos);
      }
   }

   @Override
   public void generate(
      WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, BlockPos pos, IStructurePiece structurePiece
   ) {
      Map<Pair<Integer, Integer>, List<BlockPos>> groupedSpikePositions = this.spikePositions
         .stream()
         .collect(Collectors.groupingBy(p -> new Pair(p.m_123341_() >> 2, p.m_123343_() >> 2)));

      for (BlockPos outerPos : groupedSpikePositions.values().stream().map(list -> list.get(0)).toList()) {
         Vec3 centerDirection = VecUtils.planeProject(VecUtils.asVec3(pos.m_121996_(outerPos)), VecUtils.yAxis).m_82541_();
         Vec3 tip = centerDirection.m_82490_((double)(5 + random.m_188503_(3))).m_82549_(VecUtils.yAxis.m_82490_((double)(7 + random.m_188503_(5))));
         this.generateSpike(outerPos, outerPos.m_121955_(new BlockPos(tip)), structurePiece, level, boundingBox, centerDirection);
      }
   }

   private void generateSpike(BlockPos origin, BlockPos tip, IStructurePiece structurePiece, WorldGenLevel level, BoundingBox boundingBox, Vec3 centerDirection) {
      Vec3 centerDirectionPos = centerDirection.m_82490_(3.0);
      Set<BlockPos> blockSet = this.baseBlocks.stream().<BlockPos>map(BlockPos::new).collect(Collectors.toSet());
      Set<BlockPos> innerBlockSet = blockSet.stream().filter(posx -> posx.m_203193_(centerDirectionPos) < Math.pow(2.0, 2.0)).collect(Collectors.toSet());
      Set<BlockPos> middleBlockSet = blockSet.stream()
         .filter(block -> !innerBlockSet.contains(block))
         .filter(block -> block.m_203193_(centerDirectionPos) < Math.pow(3.7, 2.0))
         .collect(Collectors.toSet());
      Set<BlockPos> outerBlockSet = blockSet.stream()
         .filter(posx -> !middleBlockSet.contains(posx) && !innerBlockSet.contains(posx))
         .collect(Collectors.toSet());

      for (BlockPos blockPos : innerBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.m_121955_(origin), tip)) {
            BlockState blockState = this.random.m_188503_(16) == 0 ? Blocks.f_152491_.m_49966_() : Blocks.f_152490_.m_49966_();
            structurePiece.placeBlock(level, blockState, pos, boundingBox);
         }
      }

      for (BlockPos blockPos : middleBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.m_121955_(origin), tip)) {
            structurePiece.placeBlock(level, Blocks.f_152497_.m_49966_(), pos, boundingBox);
         }
      }

      for (BlockPos blockPos : outerBlockSet) {
         for (BlockPos pos : MathUtils.getBlocksInLine(blockPos.m_121955_(origin), tip)) {
            structurePiece.placeBlock(level, Blocks.f_152597_.m_49966_(), pos, boundingBox);
         }
      }
   }
}
