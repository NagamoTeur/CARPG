package com.github.L_Ender.cataclysm.structures.jisaw;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.mixin.accessor.StructureTemplatePoolAccessor;
import com.github.L_Ender.cataclysm.structures.jisaw.assembler.JigsawStructureAssembler;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawPoolElement;
import com.github.L_Ender.cataclysm.util.BoxOctree;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ConcurrentModificationException;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;

public class JigsawManager {
   public static Optional<GenerationStub> assembleJigsawStructure(
      GenerationContext generationContext,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawNameOptional,
      int maxDepth,
      BlockPos locatePos,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      Optional<Integer> maxY,
      Optional<Integer> minY
   ) {
      RegistryAccess registryAccess = generationContext.f_226621_();
      ChunkGenerator chunkGenerator = generationContext.f_226622_();
      StructureTemplateManager structureManager = generationContext.f_226625_();
      LevelHeightAccessor levelHeightAccessor = generationContext.f_226629_();
      WorldgenRandom worldgenRandom = generationContext.f_226626_();
      Registry<StructureTemplatePool> registry = registryAccess.m_175515_(Registry.f_122884_);
      Optional<PoolElementStructurePiece> startPieceOptional = getStartPiece(startPool, startJigsawNameOptional, locatePos, structureManager, worldgenRandom);
      if (startPieceOptional.isEmpty()) {
         return Optional.empty();
      } else {
         PoolElementStructurePiece startPiece = startPieceOptional.get();
         Vec3i startingPosOffset = locatePos.m_121996_(startPiece.m_72646_());
         BoundingBox pieceBoundingBox = startPiece.m_73547_();
         int bbCenterX = (pieceBoundingBox.m_162399_() + pieceBoundingBox.m_162395_()) / 2;
         int bbCenterZ = (pieceBoundingBox.m_162401_() + pieceBoundingBox.m_162398_()) / 2;
         int bbCenterY = projectStartToHeightmap.<Integer>map(
               types -> locatePos.m_123342_() + chunkGenerator.m_223221_(bbCenterX, bbCenterZ, types, levelHeightAccessor, generationContext.f_226624_())
            )
            .orElseGet(() -> startPiece.m_72646_().m_123342_());
         int adjustedPieceCenterY = bbCenterY + startingPosOffset.m_123342_();
         int yAdjustment = pieceBoundingBox.m_162396_() + startPiece.m_72647_();
         startPiece.m_6324_(0, bbCenterY - yAdjustment, 0);
         AABB aABB = new AABB(
            (double)(bbCenterX - maxDistanceFromCenter),
            (double)(adjustedPieceCenterY - maxDistanceFromCenter),
            (double)(bbCenterZ - maxDistanceFromCenter),
            (double)(bbCenterX + maxDistanceFromCenter + 1),
            (double)(adjustedPieceCenterY + maxDistanceFromCenter + 1),
            (double)(bbCenterZ + maxDistanceFromCenter + 1)
         );
         BoxOctree maxStructureBounds = new BoxOctree(aABB);
         maxStructureBounds.addBox(AABB.m_82321_(pieceBoundingBox));
         return Optional.of(
            new GenerationStub(
               new BlockPos(bbCenterX, adjustedPieceCenterY, bbCenterZ),
               structurePiecesBuilder -> {
                  if (maxDepth > 0) {
                     JigsawStructureAssembler assembler = new JigsawStructureAssembler(
                        new JigsawStructureAssembler.Settings()
                           .poolRegistry(registry)
                           .maxDepth(maxDepth)
                           .chunkGenerator(chunkGenerator)
                           .structureTemplateManager(structureManager)
                           .randomState(generationContext.f_226624_())
                           .rand(worldgenRandom)
                           .maxY(maxY)
                           .minY(minY)
                           .useExpansionHack(useExpansionHack)
                           .levelHeightAccessor(levelHeightAccessor)
                     );
                     assembler.assembleStructure(startPiece, maxStructureBounds);
                     assembler.addAllPiecesToStructureBuilder(structurePiecesBuilder);
                  }
               }
            )
         );
      }
   }

   private static Optional<PoolElementStructurePiece> getStartPiece(
      Holder<StructureTemplatePool> startPoolHolder,
      Optional<ResourceLocation> startJigsawNameOptional,
      BlockPos locatePos,
      StructureTemplateManager structureTemplateManager,
      RandomSource rand
   ) {
      StructureTemplatePool startPool = (StructureTemplatePool)startPoolHolder.m_203334_();
      ObjectArrayList<Pair<StructurePoolElement, Integer>> candidatePoolElements = new ObjectArrayList(
         ((StructureTemplatePoolAccessor)startPool).getRawTemplates()
      );
      Util.m_214673_(candidatePoolElements, rand);
      Rotation rotation = Rotation.m_221990_(rand);
      int totalWeightSum = candidatePoolElements.stream().mapToInt(Pair::getSecond).reduce(0, Integer::sum);

      while (candidatePoolElements.size() > 0 && totalWeightSum > 0) {
         Pair<StructurePoolElement, Integer> chosenPoolElementPair = null;
         ObjectListIterator chosenPoolElement = candidatePoolElements.iterator();

         while (chosenPoolElement.hasNext()) {
            Pair<StructurePoolElement, Integer> candidatePiecePair = (Pair<StructurePoolElement, Integer>)chosenPoolElement.next();
            StructurePoolElement candidatePiece = (StructurePoolElement)candidatePiecePair.getFirst();
            if (candidatePiece instanceof CataclysmJigsawPoolElement yungElement && yungElement.isPriorityPiece()) {
               chosenPoolElementPair = candidatePiecePair;
               break;
            }
         }

         if (chosenPoolElementPair == null) {
            int chosenWeight = rand.m_188503_(totalWeightSum) + 1;
            ObjectListIterator var19 = candidatePoolElements.iterator();

            while (var19.hasNext()) {
               Pair<StructurePoolElement, Integer> candidate = (Pair<StructurePoolElement, Integer>)var19.next();
               chosenWeight -= candidate.getSecond();
               if (chosenWeight <= 0) {
                  chosenPoolElementPair = candidate;
                  break;
               }
            }
         }

         StructurePoolElement chosenPoolElementx = (StructurePoolElement)chosenPoolElementPair.getFirst();
         int chosenPieceWeight = (Integer)chosenPoolElementPair.getSecond();
         if (chosenPoolElementx == EmptyPoolElement.f_210175_) {
            return Optional.empty();
         }

         BlockPos anchorPos;
         if (startJigsawNameOptional.isPresent()) {
            ResourceLocation name = startJigsawNameOptional.get();
            Optional<BlockPos> optional = getPosOfJigsawBlockWithName(chosenPoolElementx, name, locatePos, rotation, structureTemplateManager, rand);
            if (optional.isEmpty()) {
               Cataclysm.LOGGER
                  .error(
                     "No starting jigsaw with Name {} found in start pool {}",
                     name,
                     startPoolHolder.m_203543_().map(pool -> pool.m_135782_().toString()).orElse("<unregistered>")
                  );
               return Optional.empty();
            }

            anchorPos = optional.get();
         } else {
            anchorPos = locatePos;
         }

         Vec3i startingPosOffset = anchorPos.m_121996_(locatePos);
         BlockPos adjustedStartPos = locatePos.m_121996_(startingPosOffset);
         if (chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElement) {
            StructureContext ctx = new StructureContext.Builder()
               .structureTemplateManager(structureTemplateManager)
               .pos(adjustedStartPos)
               .rotation(rotation)
               .depth(0)
               .random(rand)
               .build();
            if (!yungElement.passesConditions(ctx)) {
               totalWeightSum -= chosenPieceWeight;
               candidatePoolElements.remove(chosenPoolElementPair);
               continue;
            }
         }

         return Optional.of(
            new PoolElementStructurePiece(
               structureTemplateManager,
               chosenPoolElementx,
               adjustedStartPos,
               chosenPoolElementx.m_210540_(),
               rotation,
               chosenPoolElementx.m_214015_(structureTemplateManager, adjustedStartPos, rotation)
            )
         );
      }

      return Optional.empty();
   }

   private static Optional<BlockPos> getPosOfJigsawBlockWithName(
      StructurePoolElement structurePoolElement,
      ResourceLocation name,
      BlockPos startPos,
      Rotation rotation,
      StructureTemplateManager structureTemplateManager,
      RandomSource rand
   ) {
      try {
         for (StructureBlockInfo jigsawBlockInfo : structurePoolElement.m_213638_(structureTemplateManager, startPos, rotation, rand)) {
            ResourceLocation jigsawBlockName = ResourceLocation.m_135820_(jigsawBlockInfo.f_74677_.m_128461_("name"));
            if (name.equals(jigsawBlockName)) {
               return Optional.of(jigsawBlockInfo.f_74675_);
            }
         }
      } catch (ConcurrentModificationException var10) {
         Cataclysm.LOGGER
            .error(
               "Encountered unexpected ConcurrentModException while trying to get jigsaw block with name {} from structure pool element {}",
               name,
               structurePoolElement
            );
         Cataclysm.LOGGER.error("Ignoring - the structure will still generate, but /locate will not point to the structure's anchor block.");
         return Optional.empty();
      }

      return Optional.empty();
   }
}
