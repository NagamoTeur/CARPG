package com.github.L_Ender.cataclysm.util;

import com.github.L_Ender.cataclysm.mixin.StructureManagerAccessor;
import com.github.L_Ender.cataclysm.mixin.WorldGenRegionAccessor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class MixinUtil {
   public static StructureStart getStructureAt(StructureManager structureManager, BlockPos pos, Structure structure) {
      for (StructureStart structurestart : startsForStructure(structureManager, SectionPos.m_123199_(pos), structure)) {
         if (structurestart.m_73601_().m_71051_(pos)) {
            return structurestart;
         }
      }

      return StructureStart.f_73561_;
   }

   private static List<StructureStart> startsForStructure(StructureManager structureManager, SectionPos sectionPos, Structure structure) {
      LongSet longset = ((StructureManagerAccessor)structureManager)
         .getLevel()
         .m_46819_(sectionPos.m_123170_(), sectionPos.m_123222_(), ChunkStatus.f_62316_)
         .m_213649_(structure);
      Builder<StructureStart> builder = ImmutableList.builder();
      fillStartsForStructure(structureManager, structure, longset, builder::add);
      return builder.build();
   }

   private static void fillStartsForStructure(StructureManager structureManager, Structure structure, LongSet longSet, Consumer<StructureStart> consumer) {
      LongIterator var4 = longSet.iterator();

      while (var4.hasNext()) {
         long i = (Long)var4.next();
         SectionPos sectionpos = SectionPos.m_123196_(new ChunkPos(i), ((StructureManagerAccessor)structureManager).getLevel().m_151560_());
         Optional<ChunkAccess> structureAccess = getChunk(
            (WorldGenRegion)((StructureManagerAccessor)structureManager).getLevel(), sectionpos.m_123170_(), sectionpos.m_123222_()
         );
         if (structureAccess.isPresent()) {
            StructureStart structurestart = structureManager.m_220512_(sectionpos, structure, (StructureAccess)structureAccess.get());
            if (structurestart != null && structurestart.m_73603_()) {
               consumer.accept(structurestart);
            }
         }
      }
   }

   private static Optional<ChunkAccess> getChunk(WorldGenRegion worldGenRegion, int chunkX, int chunkZ) {
      WorldGenRegionAccessor accessor = (WorldGenRegionAccessor)worldGenRegion;
      if (worldGenRegion.m_7232_(chunkX, chunkZ)) {
         int i = chunkX - accessor.getFirstPos().f_45578_;
         int j = chunkZ - accessor.getFirstPos().f_45579_;
         ChunkAccess chunkAccess = accessor.getCache().get(i + j * accessor.getSize());
         if (chunkAccess.m_6415_().m_62427_(ChunkStatus.f_62315_)) {
            return Optional.of(chunkAccess);
         }
      }

      return Optional.empty();
   }
}
