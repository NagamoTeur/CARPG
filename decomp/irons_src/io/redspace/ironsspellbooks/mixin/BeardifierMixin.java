package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.worldgen.IndividualTerrainStructurePoolElement;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.Beardifier.Rigid;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Beardifier.class})
public class BeardifierMixin {
   @Inject(
      method = {"lambda$forStructuresInChunk$2", "m_223930_"},
      remap = false,
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void injectCustomTerrainAdaptation(
      ChunkPos pChunkPos, ObjectList<Rigid> list, int i, int j, ObjectList<JigsawJunction> junctions, StructureStart p_223936_, CallbackInfo ci
   ) {
      for (StructurePiece structurepiece : p_223936_.m_73602_()) {
         if (structurepiece.m_73411_(pChunkPos, 12) && structurepiece instanceof PoolElementStructurePiece) {
            PoolElementStructurePiece poolelementstructurepiece = (PoolElementStructurePiece)structurepiece;
            StructurePoolElement element = poolelementstructurepiece.m_209918_();
            if (element instanceof IndividualTerrainStructurePoolElement) {
               IndividualTerrainStructurePoolElement ironElement = (IndividualTerrainStructurePoolElement)element;
               Projection structuretemplatepool$projection = ironElement.m_210539_();
               if (structuretemplatepool$projection == Projection.RIGID) {
                  list.add(new Rigid(poolelementstructurepiece.m_73547_(), ironElement.getTerrainAdjustment(), ironElement.m_210540_()));
               }

               for (JigsawJunction jigsawjunction : poolelementstructurepiece.m_72648_()) {
                  int k = jigsawjunction.m_210252_();
                  int l = jigsawjunction.m_210258_();
                  if (k > i - 12 && l > j - 12 && k < i + 15 + 12 && l < j + 15 + 12) {
                     junctions.add(jigsawjunction);
                  }
               }

               ci.cancel();
            }
         }
      }
   }
}
