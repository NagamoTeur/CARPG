package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class BaseProcessor extends StructureProcessor {
   public static final BaseProcessor INSTANCE = new BaseProcessor();
   public static final Codec<BaseProcessor> CODEC = Codec.unit(() -> INSTANCE);

   protected StructureProcessorType<?> m_6953_() {
      return ProcessorHandler.BASE_PROCESSOR;
   }

   public StructureBlockInfo process(
      LevelReader levelReader,
      BlockPos jigsawPiecePos,
      BlockPos jigsawPieceBottomCenterPos,
      StructureBlockInfo blockInfoLocal,
      StructureBlockInfo blockInfoGlobal,
      StructurePlaceSettings structurePlacementData,
      StructureTemplate template
   ) {
      if (blockInfoGlobal.f_74676_.m_60713_(Blocks.f_152551_)) {
         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.m_143488_().equals(new ChunkPos(blockInfoGlobal.f_74675_))) {
            return blockInfoGlobal;
         }

         MutableBlockPos mutable = blockInfoGlobal.f_74675_.m_122032_().m_122173_(Direction.DOWN);
         BlockState currBlockState = levelReader.m_8055_(mutable);
         RandomSource random = structurePlacementData.m_230326_(blockInfoGlobal.f_74675_);

         for (blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.f_74675_, this.chooseRandomState(random), blockInfoGlobal.f_74677_);
            mutable.m_123342_() > levelReader.m_141937_() && mutable.m_123342_() < levelReader.m_151558_() && !currBlockState.m_60767_().m_76333_();
            currBlockState = levelReader.m_8055_(mutable)
         ) {
            levelReader.m_46865_(mutable).m_6978_(mutable, this.chooseRandomState(random), false);
            mutable.m_122173_(Direction.DOWN);
         }
      }

      return blockInfoGlobal;
   }

   public BlockState chooseRandomState(RandomSource random) {
      float v = random.m_188501_();
      return (double)v > 0.7 ? Blocks.f_152555_.m_49966_() : Blocks.f_152551_.m_49966_();
   }
}
