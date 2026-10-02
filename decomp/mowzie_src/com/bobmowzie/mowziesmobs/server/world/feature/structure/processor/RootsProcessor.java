package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class RootsProcessor extends StructureProcessor {
   public static final RootsProcessor INSTANCE = new RootsProcessor();
   public static final Codec<RootsProcessor> CODEC = Codec.unit(() -> INSTANCE);

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
      if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.m_143488_().equals(new ChunkPos(blockInfoGlobal.f_74675_))) {
         return blockInfoGlobal;
      }

      RandomSource random = structurePlacementData.m_230326_(blockInfoGlobal.f_74675_);
      if ((double)random.m_188501_() < 0.15) {
         if (blockInfoGlobal.f_74676_.m_60713_(Blocks.f_50745_)
            || blockInfoGlobal.f_74676_.m_60713_(Blocks.f_50403_) && blockInfoGlobal.f_74676_.m_61143_(SlabBlock.f_56353_) != SlabType.TOP) {
            BlockPos pos = blockInfoGlobal.f_74675_.m_7495_();
            BlockState belowState = levelReader.m_8055_(pos);
            if (belowState.m_60795_()) {
               levelReader.m_46865_(pos).m_6978_(pos, Blocks.f_152548_.m_49966_(), false);
            }
         } else if (blockInfoGlobal.f_74676_.m_60713_(Blocks.f_50221_)
            && blockInfoGlobal.f_74676_.m_61143_(TrapDoorBlock.f_57515_) == Half.TOP
            && !(Boolean)blockInfoGlobal.f_74676_.m_61143_(TrapDoorBlock.f_57514_)) {
            blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.f_74675_, Blocks.f_152548_.m_49966_(), blockInfoGlobal.f_74677_);
         }
      }

      return blockInfoGlobal;
   }
}
