package com.hollingsworth.arsnouveau.common.world.processors;

import com.hollingsworth.arsnouveau.setup.StructureRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class WaterloggingFixProcessor extends StructureProcessor {
   public static final Codec<WaterloggingFixProcessor> CODEC = Codec.unit(WaterloggingFixProcessor::new);

   public StructureBlockInfo m_7382_(
      LevelReader levelReader, BlockPos pos, BlockPos pos2, StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings
   ) {
      if (!infoIn2.f_74676_.m_60819_().m_76178_()) {
         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.m_143488_().equals(new ChunkPos(infoIn2.f_74675_))) {
            return infoIn2;
         }

         ChunkAccess chunk = levelReader.m_46865_(infoIn2.f_74675_);
         int minY = chunk.m_141937_();
         int maxY = chunk.m_151558_();
         int currentY = infoIn2.f_74675_.m_123342_();
         if (currentY >= minY && currentY <= maxY) {
            ((LevelAccessor)levelReader).m_186460_(infoIn2.f_74675_, infoIn2.f_74676_.m_60734_(), 0);
         }
      }

      return infoIn2;
   }

   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType<?>)StructureRegistry.WATERLOGGING_FIX_PROCESSOR.get();
   }
}
