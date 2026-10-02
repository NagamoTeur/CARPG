package io.redspace.ironsspellbooks.worldgen;

import com.mojang.serialization.Codec;
import io.redspace.ironsspellbooks.registries.StructureProcessorRegistry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.level.material.FluidState;

public class RemoveWaterProcessor extends StructureProcessor {
   public static final Codec<RemoveWaterProcessor> CODEC = Codec.unit(RemoveWaterProcessor::new);

   @Nullable
   public StructureBlockInfo process(
      @Nonnull LevelReader level,
      @Nonnull BlockPos jigsawPiecePos,
      @Nonnull BlockPos jigsawPieceBottomCenterPos,
      @Nonnull StructureBlockInfo blockInfoLocal,
      @Nonnull StructureBlockInfo blockInfoGlobal,
      @Nonnull StructurePlaceSettings settings,
      @Nullable StructureTemplate template
   ) {
      if (blockInfoGlobal.f_74676_.m_61138_(BlockStateProperties.f_61362_) && !(Boolean)blockInfoGlobal.f_74676_.m_61143_(BlockStateProperties.f_61362_)) {
         ChunkPos chunkPos = new ChunkPos(blockInfoGlobal.f_74675_);
         ChunkAccess chunk = level.m_6325_(chunkPos.f_45578_, chunkPos.f_45579_);
         int sectionIndex = chunk.m_151564_(blockInfoGlobal.f_74675_.m_123342_());
         if (sectionIndex >= 0) {
            LevelChunkSection section = chunk.m_183278_(sectionIndex);
            if (this.getFluidState(section, blockInfoGlobal.f_74675_).m_205070_(FluidTags.f_13131_)) {
               this.setBlock(section, blockInfoGlobal.f_74675_, blockInfoGlobal.f_74676_);
            }
         }
      }

      return blockInfoGlobal;
   }

   private void setBlock(LevelChunkSection section, BlockPos pos, BlockState state) {
      section.m_62986_(SectionPos.m_123207_(pos.m_123341_()), SectionPos.m_123207_(pos.m_123342_()), SectionPos.m_123207_(pos.m_123343_()), state);
   }

   private FluidState getFluidState(LevelChunkSection section, BlockPos pos) {
      return section.m_63007_(SectionPos.m_123207_(pos.m_123341_()), SectionPos.m_123207_(pos.m_123342_()), SectionPos.m_123207_(pos.m_123343_()));
   }

   @Nonnull
   protected StructureProcessorType<?> m_6953_() {
      return (StructureProcessorType<?>)StructureProcessorRegistry.REMOVE_WATER.get();
   }
}
