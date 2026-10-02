package com.github.L_Ender.cataclysm.mixin;

import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyBlocks;
import com.min01.archaeology.init.ArchaeologyLootTables;
import com.min01.archaeology.misc.IDesertPyramidPiece;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SortedArraySet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Structure.class})
public abstract class MixinStructure {
   @Inject(
      method = {"afterPlace"},
      at = {@At("TAIL")}
   )
   protected void afterPlace(
      WorldGenLevel level,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource random,
      BoundingBox boundingBox,
      ChunkPos chunkPosition,
      PiecesContainer pieces,
      CallbackInfo callback
   ) {
      if (this instanceof DesertPyramidStructure) {
         Set<BlockPos> positions = SortedArraySet.m_144976_(Vec3i::compareTo);

         for (StructurePiece piece : pieces.f_192741_()) {
            if (piece instanceof IDesertPyramidPiece pyramidPiece) {
               positions.addAll(pyramidPiece.archeology$getPotentialSuspiciousSandWorldPositions());
               archaeology$setSuspiciousSand(boundingBox, level, pyramidPiece.archeology$getRandomCollapsedRoofPos());
            }
         }

         ObjectArrayList<BlockPos> shuffledPositions = new ObjectArrayList(positions.stream().toList());
         RandomSource newRandom = RandomSource.m_216335_(level.m_7328_()).m_188582_().m_224542_(pieces.m_192756_().m_162394_());
         Util.m_214673_(shuffledPositions, newRandom);
         int suspiciousAmount = Math.min(positions.size(), newRandom.m_216339_(5, 8));
         ObjectListIterator var13 = shuffledPositions.iterator();

         while (var13.hasNext()) {
            BlockPos blockpos = (BlockPos)var13.next();
            if (suspiciousAmount > 0) {
               suspiciousAmount--;
               archaeology$setSuspiciousSand(boundingBox, level, blockpos);
            } else if (boundingBox.m_71051_(blockpos)) {
               level.m_7731_(blockpos, Blocks.f_49992_.m_49966_(), 2);
            }
         }
      }
   }

   @Unique
   private static void archaeology$setSuspiciousSand(BoundingBox boundingBox, WorldGenLevel level, BlockPos position) {
      if (boundingBox.m_71051_(position)) {
         level.m_7731_(position, ((Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get()).m_49966_(), 2);
         level.m_141902_(position, (BlockEntityType)ArchaeologyBlockEntityType.BRUSHABLE_BLOCK.get())
            .ifPresent(brushableEntity -> brushableEntity.setLootTable(ArchaeologyLootTables.DESERT_PYRAMID_ARCHAEOLOGY, position.m_121878_()));
      }
   }
}
