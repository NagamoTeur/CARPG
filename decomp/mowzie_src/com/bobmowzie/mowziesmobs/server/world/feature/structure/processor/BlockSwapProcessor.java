package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class BlockSwapProcessor extends StructureProcessor {
   public static final Codec<BlockSwapProcessor> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               BlockState.f_61039_.listOf().fieldOf("to_replace").forGetter(config -> config.toReplace),
               BlockStateRandomizer.CODEC.fieldOf("replace_with").forGetter(config -> config.replaceWith),
               Codec.BOOL.optionalFieldOf("copy_properties", true).forGetter(config -> config.copyProperties)
            )
            .apply(instance, instance.stable(BlockSwapProcessor::new))
   );
   List<BlockState> toReplace;
   BlockStateRandomizer replaceWith;
   boolean copyProperties;

   public BlockSwapProcessor(List<BlockState> toReplace, BlockStateRandomizer replaceWith, boolean copyProperties) {
      this.toReplace = toReplace;
      this.replaceWith = replaceWith;
      this.copyProperties = copyProperties;
   }

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
      for (BlockState toReplaceState : this.toReplace) {
         if (blockInfoGlobal.f_74676_.m_60713_(toReplaceState.m_60734_())) {
            if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.m_143488_().equals(new ChunkPos(blockInfoGlobal.f_74675_))) {
               return blockInfoGlobal;
            }

            RandomSource random = structurePlacementData.m_230326_(blockInfoGlobal.f_74675_);
            BlockState newState = this.replaceWith.chooseRandomState(random);
            if (this.copyProperties) {
               newState = newState.m_60734_().m_152465_(blockInfoGlobal.f_74676_);
            }

            blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.f_74675_, newState, blockInfoGlobal.f_74677_);
            break;
         }
      }

      return blockInfoGlobal;
   }
}
