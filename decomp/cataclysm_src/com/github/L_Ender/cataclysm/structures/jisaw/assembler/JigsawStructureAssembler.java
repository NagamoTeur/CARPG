package com.github.L_Ender.cataclysm.structures.jisaw.assembler;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.mixin.accessor.BoundingBoxAccessor;
import com.github.L_Ender.cataclysm.mixin.accessor.StructureTemplatePoolAccessor;
import com.github.L_Ender.cataclysm.structures.jisaw.PieceEntry;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawPoolElement;
import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawSinglePoolElement;
import com.github.L_Ender.cataclysm.structures.jisaw.element.IMaxCountJigsawPoolElement;
import com.github.L_Ender.cataclysm.util.BoxOctree;
import com.google.common.collect.Queues;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.MutableObject;

public class JigsawStructureAssembler {
   private final JigsawStructureAssembler.Settings settings;
   private final List<PieceEntry> pieces = new ArrayList<>();
   public Deque<PieceEntry> unprocessedPieceEntries = Queues.newArrayDeque();
   private final Map<String, Integer> pieceCounts = new HashMap<>();
   private final Map<String, Integer> maxPieceCounts = new HashMap<>();

   public JigsawStructureAssembler(JigsawStructureAssembler.Settings settings) {
      this.settings = settings;
   }

   public void assembleStructure(PoolElementStructurePiece startPiece, BoxOctree structureBounds) {
      PieceEntry startPieceEntry = new PieceEntry(startPiece, new MutableObject(structureBounds), null, 0, null, null, null);
      this.pieces.add(startPieceEntry);
      this.unprocessedPieceEntries.addLast(startPieceEntry);

      while (!this.unprocessedPieceEntries.isEmpty()) {
         PieceEntry entry = this.unprocessedPieceEntries.removeFirst();
         this.addChildrenForPiece(entry);
      }

      this.applyModifications();
   }

   public void addAllPiecesToStructureBuilder(StructurePiecesBuilder structurePiecesBuilder) {
      this.pieces.forEach(pieceEntry -> structurePiecesBuilder.m_142679_(pieceEntry.getPiece()));
   }

   private void addChildrenForPiece(PieceEntry pieceEntry) {
      PoolElementStructurePiece piece = pieceEntry.getPiece();
      MutableObject<BoxOctree> parentOctree = new MutableObject();
      List<StructureBlockInfo> pieceJigsawBlocks = piece.m_209918_()
         .m_213638_(this.settings.structureTemplateManager, piece.m_72646_(), piece.m_6830_(), this.settings.rand);
      boolean generatedAtLeastOneChildPiece = false;

      for (StructureBlockInfo jigsawBlockInfo : pieceJigsawBlocks) {
         ResourceKey<StructureTemplatePool> poolKey = readPoolName(jigsawBlockInfo);
         Optional<? extends Holder<StructureTemplatePool>> optionalPoolHolder = this.settings.poolRegistry.m_203636_(poolKey);
         if (optionalPoolHolder.isEmpty()) {
            Cataclysm.LOGGER.warn("Empty or nonexistent pool: {}", poolKey.m_135782_());
         } else {
            Holder<StructureTemplatePool> targetPoolHolder = (Holder<StructureTemplatePool>)optionalPoolHolder.get();
            StructureTemplatePool targetPool = (StructureTemplatePool)targetPoolHolder.m_203334_();
            if (targetPool.m_210590_() == 0 && !targetPoolHolder.m_203565_(Pools.f_127186_)) {
               Cataclysm.LOGGER.warn("Empty or nonexistent pool: {}", poolKey.m_135782_());
            } else {
               ResourceLocation fallbackPoolId = ((StructureTemplatePool)targetPoolHolder.get()).m_210573_();
               Optional<StructureTemplatePool> fallbackPool = this.getPoolFromId(fallbackPoolId);
               if (!fallbackPool.isEmpty()) {
                  PieceContext pieceContext = this.createPieceContextForJigsawBlock(jigsawBlockInfo, pieceEntry, parentOctree);
                  Optional<StructurePoolElement> newlyGeneratedPiece = Optional.empty();
                  if (pieceEntry.getDepth() != this.settings.maxDepth) {
                     pieceContext.candidatePoolElements = new ObjectArrayList(((StructureTemplatePoolAccessor)targetPool).getRawTemplates());
                     newlyGeneratedPiece = this.chooseCandidateFromPool(pieceContext);
                  }

                  if (newlyGeneratedPiece.isEmpty()) {
                     pieceContext.candidatePoolElements = new ObjectArrayList(((StructureTemplatePoolAccessor)fallbackPool.get()).getRawTemplates());
                     newlyGeneratedPiece = this.chooseCandidateFromPool(pieceContext);
                  }

                  if (newlyGeneratedPiece.isPresent()) {
                     generatedAtLeastOneChildPiece = true;
                  }
               }
            }
         }
      }

      if (pieceEntry.getDeadendPool().isPresent() && !generatedAtLeastOneChildPiece && pieceJigsawBlocks.size() > 1) {
         ResourceLocation deadendPoolId = pieceEntry.getDeadendPool().get();
         Optional<StructureTemplatePool> deadendPool = this.settings.poolRegistry.m_6612_(deadendPoolId);
         if (deadendPool.isEmpty()) {
            Cataclysm.LOGGER.error("Unable to find deadend pool {} for element {}", deadendPoolId, piece.m_209918_());
            return;
         }

         PieceEntry parentEntry = pieceEntry.getParentEntry();
         PieceContext newContext = pieceEntry.getSourcePieceContext().copy();
         newContext.candidatePoolElements = new ObjectArrayList(((StructureTemplatePoolAccessor)deadendPool.get()).getRawTemplates());
         AABB pieceAabb = pieceEntry.getPieceAabb();
         if (parentEntry != null && pieceAabb != null) {
            parentEntry.getPiece().m_72648_().remove(pieceEntry.getParentJunction());
            ((BoxOctree)pieceEntry.getBoxOctree().getValue()).removeBox(pieceAabb);
            this.pieces.remove(pieceEntry);
            if (pieceEntry.getPiece().m_209918_() instanceof CataclysmJigsawPoolElement yungElement
               && yungElement.maxCount.isPresent()
               && yungElement.name.isPresent()
               && this.pieceCounts.containsKey(yungElement.name.get())) {
               String pieceName = yungElement.name.get();
               this.pieceCounts.put(pieceName, this.pieceCounts.get(pieceName) - 1);
            }

            if (pieceEntry.getPiece().m_209918_() instanceof IMaxCountJigsawPoolElement maxCountJigsawPoolElement
               && this.pieceCounts.containsKey(maxCountJigsawPoolElement.getName())) {
               String pieceName = maxCountJigsawPoolElement.getName();
               this.pieceCounts.put(pieceName, this.pieceCounts.get(pieceName) - 1);
            }

            this.chooseCandidateFromPool(newContext);
         }
      }
   }

   private Optional<StructureTemplatePool> getPoolFromId(ResourceLocation id) {
      Optional<StructureTemplatePool> pool = this.settings.poolRegistry.m_6612_(id);
      if (!pool.isEmpty() && (pool.get().m_210590_() != 0 || Objects.equals(id, Pools.f_127186_.m_135782_()))) {
         return pool;
      } else {
         Cataclysm.LOGGER.warn("Empty or nonexistent pool: {}", id);
         return Optional.empty();
      }
   }

   private PieceContext createPieceContextForJigsawBlock(StructureBlockInfo jigsawBlockInfo, PieceEntry pieceEntry, MutableObject<BoxOctree> parentOctree) {
      BoundingBox pieceBoundingBox = pieceEntry.getPiece().m_73547_();
      MutableObject<BoxOctree> pieceOctree = pieceEntry.getBoxOctree();
      Direction direction = JigsawBlock.m_54250_(jigsawBlockInfo.f_74676_);
      BlockPos jigsawBlockTargetPos = jigsawBlockInfo.f_74675_.m_121945_(direction);
      boolean isTargetInsideCurrentPiece = pieceBoundingBox.m_71051_(jigsawBlockTargetPos);
      if (isTargetInsideCurrentPiece) {
         pieceOctree = parentOctree;
         if (parentOctree.getValue() == null) {
            parentOctree.setValue(new BoxOctree(AABB.m_82321_(pieceBoundingBox)));
         }
      }

      return new PieceContext(
         null, jigsawBlockInfo, jigsawBlockTargetPos, pieceBoundingBox.m_162396_(), jigsawBlockInfo.f_74675_, pieceOctree, pieceEntry, pieceEntry.getDepth()
      );
   }

   private Optional<StructurePoolElement> chooseCandidateFromPool(PieceContext context) {
      ObjectArrayList<Pair<StructurePoolElement, Integer>> candidatePoolElements = context.candidatePoolElements;
      PoolElementStructurePiece piece = context.pieceEntry.getPiece();
      boolean isPieceRigid = piece.m_209918_().m_210539_() == Projection.RIGID;
      int jigsawBlockRelativeY = context.jigsawBlockPos.m_123342_() - context.pieceMinY;
      int surfaceHeight = -1;
      Util.m_214673_(candidatePoolElements, this.settings.rand);
      int totalWeightSum = candidatePoolElements.stream().mapToInt(Pair::getSecond).reduce(0, Integer::sum);

      label201:
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
            int chosenWeight = this.settings.rand.m_188503_(totalWeightSum) + 1;
            ObjectListIterator var42 = candidatePoolElements.iterator();

            while (var42.hasNext()) {
               Pair<StructurePoolElement, Integer> candidate = (Pair<StructurePoolElement, Integer>)var42.next();
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

         if (chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElement && yungElement.maxCount.isPresent()) {
            int pieceMaxCount = yungElement.maxCount.get();
            if (yungElement.name.isEmpty()) {
               Cataclysm.LOGGER.error("Found YUNG Jigsaw piece with max_count={} missing \"name\" property.", pieceMaxCount);
               Cataclysm.LOGGER.error("Max count pieces must be named in order to work properly!");
               Cataclysm.LOGGER.error("Ignoring max_count for this piece...");
            } else {
               String pieceName = yungElement.name.get();
               if (this.maxPieceCounts.containsKey(pieceName) && this.maxPieceCounts.get(pieceName) != pieceMaxCount) {
                  Cataclysm.LOGGER
                     .error(
                        "YUNG Jigsaw Piece with name {} and max_count {} does not match stored max_count of {}!",
                        pieceName,
                        pieceMaxCount,
                        this.maxPieceCounts.get(pieceName)
                     );
                  Cataclysm.LOGGER.error("This can happen when multiple pieces across pools use the same name, but have different max_count values.");
                  Cataclysm.LOGGER.error("Please change these max_count values to match. Using max_count={} for now...", pieceMaxCount);
               }

               this.maxPieceCounts.put(pieceName, pieceMaxCount);
               if (this.pieceCounts.getOrDefault(pieceName, 0) >= pieceMaxCount) {
                  totalWeightSum -= chosenPieceWeight;
                  candidatePoolElements.remove(chosenPoolElementPair);
                  continue;
               }
            }
         }

         if (chosenPoolElementx instanceof IMaxCountJigsawPoolElement) {
            String pieceNamex = ((IMaxCountJigsawPoolElement)chosenPoolElementx).getName();
            int maxCount = ((IMaxCountJigsawPoolElement)chosenPoolElementx).getMaxCount();
            if (this.maxPieceCounts.containsKey(pieceNamex) && this.maxPieceCounts.get(pieceNamex) != maxCount) {
               Cataclysm.LOGGER
                  .error(
                     "Max Count Jigsaw Piece with name {} and max_count {} does not match stored max_count of {}!",
                     pieceNamex,
                     maxCount,
                     this.maxPieceCounts.get(pieceNamex)
                  );
               Cataclysm.LOGGER.error("This can happen when multiple pieces across pools use the same name, but have different max_count values.");
               Cataclysm.LOGGER.error("Please change these max_count values to match. Using max_count={} for now...", maxCount);
            }

            this.maxPieceCounts.put(pieceNamex, maxCount);
            if (this.pieceCounts.getOrDefault(pieceNamex, 0) >= maxCount) {
               totalWeightSum -= chosenPoolElementPair.getSecond();
               candidatePoolElements.remove(chosenPoolElementPair);
               continue;
            }
         }

         if (chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElementx && !yungElementx.isAtValidDepth(context.depth)) {
            totalWeightSum -= chosenPieceWeight;
            candidatePoolElements.remove(chosenPoolElementPair);
            continue;
         }

         Iterator var48 = Rotation.m_221992_(this.settings.rand).iterator();

         int candidateJigsawBlockRelativeY;
         int candidateJigsawYOffsetNeeded;
         int candidateJigsawBlockY;
         PoolElementStructurePiece newPiece;
         JigsawJunction newJunctionOnParent;
         PieceEntry newPieceEntry;
         AABB aabb;
         int groundLevelDelta;
         label177:
         while (true) {
            if (!var48.hasNext()) {
               totalWeightSum -= chosenPieceWeight;
               candidatePoolElements.remove(chosenPoolElementPair);
               continue label201;
            }

            Rotation rotation = (Rotation)var48.next();
            List<StructureBlockInfo> candidateJigsawBlocks = chosenPoolElementx.m_213638_(
               this.settings.structureTemplateManager, BlockPos.f_121853_, rotation, this.settings.rand
            );
            BoundingBox tempCandidateBoundingBox = chosenPoolElementx.m_214015_(this.settings.structureTemplateManager, BlockPos.f_121853_, rotation);
            int candidateHeightAdjustments = 0;
            if (this.settings.useExpansionHack && tempCandidateBoundingBox.m_71057_() <= 16) {
               candidateHeightAdjustments = candidateJigsawBlocks.stream()
                  .mapToInt(
                     pieceCandidateJigsawBlock -> {
                        if (!tempCandidateBoundingBox.m_71051_(
                           pieceCandidateJigsawBlock.f_74675_.m_121945_(JigsawBlock.m_54250_(pieceCandidateJigsawBlock.f_74676_))
                        )) {
                           return 0;
                        } else {
                           ResourceLocation candidateTargetPool = new ResourceLocation(pieceCandidateJigsawBlock.f_74677_.m_128461_("pool"));
                           Optional<StructureTemplatePool> candidateTargetPoolOptional = this.settings.poolRegistry.m_6612_(candidateTargetPool);
                           Optional<StructureTemplatePool> candidateTargetFallbackOptional = candidateTargetPoolOptional.flatMap(
                              StructureTemplatePool -> this.settings.poolRegistry.m_6612_(StructureTemplatePool.m_210573_())
                           );
                           int tallestCandidateTargetPoolPieceHeight = candidateTargetPoolOptional.<Integer>map(
                                 structureTemplatePool -> structureTemplatePool.m_227357_(this.settings.structureTemplateManager)
                              )
                              .orElse(0);
                           int tallestCandidateTargetFallbackPieceHeight = candidateTargetFallbackOptional.<Integer>map(
                                 structureTemplatePool -> structureTemplatePool.m_227357_(this.settings.structureTemplateManager)
                              )
                              .orElse(0);
                           return Math.max(tallestCandidateTargetPoolPieceHeight, tallestCandidateTargetFallbackPieceHeight);
                        }
                     }
                  )
                  .max()
                  .orElse(0);
            }

            for (StructureBlockInfo candidateJigsawBlock : candidateJigsawBlocks) {
               if (JigsawBlock.m_54245_(context.jigsawBlock, candidateJigsawBlock)) {
                  BlockPos candidateJigsawBlockPos = candidateJigsawBlock.f_74675_;
                  BlockPos candidateJigsawBlockRelativePos = context.jigsawBlockTargetPos.m_121996_(candidateJigsawBlockPos);
                  BoundingBox rotatedCandidateBoundingBox = chosenPoolElementx.m_214015_(
                     this.settings.structureTemplateManager, candidateJigsawBlockRelativePos, rotation
                  );
                  Projection candidateProjection = chosenPoolElementx.m_210539_();
                  boolean isCandidateRigid = candidateProjection == Projection.RIGID;
                  candidateJigsawBlockRelativeY = candidateJigsawBlockPos.m_123342_();
                  candidateJigsawYOffsetNeeded = jigsawBlockRelativeY
                     - candidateJigsawBlockRelativeY
                     + JigsawBlock.m_54250_(context.jigsawBlock.f_74676_).m_122430_();
                  int adjustedCandidatePieceMinY;
                  if (isPieceRigid && isCandidateRigid) {
                     adjustedCandidatePieceMinY = context.pieceMinY + candidateJigsawYOffsetNeeded;
                  } else {
                     if (surfaceHeight == -1) {
                        surfaceHeight = this.settings
                           .chunkGenerator
                           .m_223221_(
                              context.jigsawBlockPos.m_123341_(),
                              context.jigsawBlockPos.m_123343_(),
                              Types.WORLD_SURFACE_WG,
                              this.settings.levelHeightAccessor,
                              this.settings.randomState
                           );
                     }

                     adjustedCandidatePieceMinY = surfaceHeight - candidateJigsawBlockRelativeY;
                  }

                  int candidatePieceYOffsetNeeded = adjustedCandidatePieceMinY - rotatedCandidateBoundingBox.m_162396_();
                  BoundingBox adjustedCandidateBoundingBox = rotatedCandidateBoundingBox.m_71045_(0, candidatePieceYOffsetNeeded, 0);
                  BlockPos adjustedCandidateJigsawBlockRelativePos = candidateJigsawBlockRelativePos.m_7918_(0, candidatePieceYOffsetNeeded, 0);
                  if (candidateHeightAdjustments > 0) {
                     int k2 = Math.max(candidateHeightAdjustments + 1, adjustedCandidateBoundingBox.m_162400_() - adjustedCandidateBoundingBox.m_162396_());
                     ((BoundingBoxAccessor)adjustedCandidateBoundingBox).setMaxY(adjustedCandidateBoundingBox.m_162396_() + k2);
                  }

                  if ((!this.settings.maxY.isPresent() || adjustedCandidateBoundingBox.m_162400_() <= this.settings.maxY.get())
                     && (!this.settings.minY.isPresent() || adjustedCandidateBoundingBox.m_162396_() >= this.settings.minY.get())) {
                     aabb = AABB.m_82321_(adjustedCandidateBoundingBox);
                     AABB aabbDeflated = aabb.m_82406_(0.25);
                     boolean pieceIgnoresBounds = false;
                     if (chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElementx) {
                        pieceIgnoresBounds = yungElementx.ignoresBounds();
                     }

                     if (!pieceIgnoresBounds) {
                        boolean pieceIntersectsExistingPieces = ((BoxOctree)context.boxOctree.getValue()).intersectsAnyBox(aabbDeflated);
                        boolean pieceIsContainedWithinStructureBoundaries = ((BoxOctree)context.boxOctree.getValue()).boundaryContains(aabbDeflated);
                        if (pieceIntersectsExistingPieces || !pieceIsContainedWithinStructureBoundaries) {
                           continue;
                        }
                     }

                     int newPieceGroundLevelDelta = piece.m_72647_();
                     if (isCandidateRigid) {
                        groundLevelDelta = newPieceGroundLevelDelta - candidateJigsawYOffsetNeeded;
                     } else {
                        groundLevelDelta = chosenPoolElementx.m_210540_();
                     }

                     if (isPieceRigid) {
                        candidateJigsawBlockY = context.pieceMinY + jigsawBlockRelativeY;
                     } else if (isCandidateRigid) {
                        candidateJigsawBlockY = adjustedCandidatePieceMinY + candidateJigsawBlockRelativeY;
                     } else {
                        if (surfaceHeight == -1) {
                           surfaceHeight = this.settings
                              .chunkGenerator
                              .m_223221_(
                                 context.jigsawBlockPos.m_123341_(),
                                 context.jigsawBlockPos.m_123343_(),
                                 Types.WORLD_SURFACE_WG,
                                 this.settings.levelHeightAccessor,
                                 this.settings.randomState
                              );
                        }

                        candidateJigsawBlockY = surfaceHeight + candidateJigsawYOffsetNeeded / 2;
                     }

                     newPiece = new PoolElementStructurePiece(
                        this.settings.structureTemplateManager,
                        chosenPoolElementx,
                        adjustedCandidateJigsawBlockRelativePos,
                        groundLevelDelta,
                        rotation,
                        adjustedCandidateBoundingBox
                     );
                     newJunctionOnParent = new JigsawJunction(
                        context.jigsawBlockTargetPos.m_123341_(),
                        candidateJigsawBlockY - jigsawBlockRelativeY + newPieceGroundLevelDelta,
                        context.jigsawBlockTargetPos.m_123343_(),
                        candidateJigsawYOffsetNeeded,
                        candidateProjection
                     );
                     newPieceEntry = new PieceEntry(
                        newPiece, context.boxOctree, aabb, context.depth + 1, context.pieceEntry, context.copy(), newJunctionOnParent
                     );
                     if (!(chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElementx)) {
                        break label177;
                     }

                     StructureContext ctx = new StructureContext.Builder()
                        .structureTemplateManager(this.settings.structureTemplateManager)
                        .pieces(this.pieces)
                        .pieceEntry(newPieceEntry)
                        .pos(adjustedCandidateJigsawBlockRelativePos)
                        .rotation(rotation)
                        .pieceMinY(adjustedCandidateBoundingBox.m_162396_())
                        .pieceMaxY(adjustedCandidateBoundingBox.m_162400_())
                        .depth(context.depth + 1)
                        .random(this.settings.rand)
                        .build();
                     if (yungElementx.passesConditions(ctx)) {
                        break label177;
                     }
                  }
               }
            }
         }

         piece.m_209916_(newJunctionOnParent);
         newPiece.m_209916_(
            new JigsawJunction(
               context.jigsawBlockPos.m_123341_(),
               candidateJigsawBlockY - candidateJigsawBlockRelativeY + groundLevelDelta,
               context.jigsawBlockPos.m_123343_(),
               -candidateJigsawYOffsetNeeded,
               piece.m_209918_().m_210539_()
            )
         );
         ((BoxOctree)context.boxOctree.getValue()).addBox(aabb);
         this.pieces.add(newPieceEntry);
         context.pieceEntry.addChildEntry(newPieceEntry);
         if (context.depth + 1 <= this.settings.maxDepth) {
            this.unprocessedPieceEntries.addLast(newPieceEntry);
         }

         if (chosenPoolElementx instanceof CataclysmJigsawPoolElement yungElementx && yungElementx.maxCount.isPresent()) {
            if (yungElementx.name.isEmpty()) {
               return Optional.of(chosenPoolElementx);
            }

            String pieceNamexx = yungElementx.name.get();
            this.pieceCounts.put(pieceNamexx, this.pieceCounts.getOrDefault(pieceNamexx, 0) + 1);
         }

         if (chosenPoolElementx instanceof IMaxCountJigsawPoolElement) {
            String pieceNamexx = ((IMaxCountJigsawPoolElement)chosenPoolElementx).getName();
            this.pieceCounts.put(pieceNamexx, this.pieceCounts.getOrDefault(pieceNamexx, 0) + 1);
         }

         return Optional.of(chosenPoolElementx);
      }

      return Optional.empty();
   }

   private void applyModifications() {
      for (PieceEntry pieceEntry : this.pieces) {
         StructurePoolElement piece = pieceEntry.getPiece().m_209918_();
         if (piece instanceof CataclysmJigsawSinglePoolElement) {
            CataclysmJigsawSinglePoolElement yungElement = (CataclysmJigsawSinglePoolElement)piece;
            if (yungElement.hasModifiers()) {
               PoolElementStructurePiece piecex = pieceEntry.getPiece();
               StructureContext structureContext = new StructureContext.Builder()
                  .pos(piecex.m_72646_())
                  .rotation(piecex.m_6830_())
                  .depth(pieceEntry.getDepth())
                  .structureTemplateManager(this.settings.structureTemplateManager)
                  .pieceEntry(pieceEntry)
                  .pieces(this.pieces)
                  .pieceMaxY(piecex.m_73547_().m_162400_())
                  .pieceMinY(piecex.m_73547_().m_162396_())
                  .random(this.settings.rand)
                  .build();
               yungElement.modifiers.forEach(modifier -> modifier.apply(structureContext));
            }
         }
      }

      List<PieceEntry> delayedEntries = this.pieces.stream().filter(PieceEntry::isDelayGeneration).toList();
      this.pieces.removeAll(delayedEntries);
      this.pieces.addAll(delayedEntries);
   }

   private static ResourceKey<StructureTemplatePool> readPoolName(StructureBlockInfo jigsawBlockInfo) {
      return ResourceKey.m_135785_(Registry.f_122884_, new ResourceLocation(jigsawBlockInfo.f_74677_.m_128461_("pool")));
   }

   public static class Settings {
      private Registry<StructureTemplatePool> poolRegistry;
      private int maxDepth;
      private ChunkGenerator chunkGenerator;
      private StructureTemplateManager structureTemplateManager;
      private LevelHeightAccessor levelHeightAccessor;
      private RandomSource rand;
      private boolean useExpansionHack;
      public RandomState randomState;
      private Optional<Integer> maxY;
      private Optional<Integer> minY;

      public JigsawStructureAssembler.Settings poolRegistry(Registry<StructureTemplatePool> poolRegistry) {
         this.poolRegistry = poolRegistry;
         return this;
      }

      public JigsawStructureAssembler.Settings maxDepth(int maxDepth) {
         this.maxDepth = maxDepth;
         return this;
      }

      public JigsawStructureAssembler.Settings chunkGenerator(ChunkGenerator chunkGenerator) {
         this.chunkGenerator = chunkGenerator;
         return this;
      }

      public JigsawStructureAssembler.Settings structureTemplateManager(StructureTemplateManager structureTemplateManager) {
         this.structureTemplateManager = structureTemplateManager;
         return this;
      }

      public JigsawStructureAssembler.Settings randomState(RandomState randomState) {
         this.randomState = randomState;
         return this;
      }

      public JigsawStructureAssembler.Settings rand(RandomSource rand) {
         this.rand = rand;
         return this;
      }

      public JigsawStructureAssembler.Settings useExpansionHack(boolean useExpansionHack) {
         this.useExpansionHack = useExpansionHack;
         return this;
      }

      public JigsawStructureAssembler.Settings levelHeightAccessor(LevelHeightAccessor levelHeightAccessor) {
         this.levelHeightAccessor = levelHeightAccessor;
         return this;
      }

      public JigsawStructureAssembler.Settings maxY(Optional<Integer> maxY) {
         this.maxY = maxY;
         return this;
      }

      public JigsawStructureAssembler.Settings minY(Optional<Integer> minY) {
         this.minY = minY;
         return this;
      }
   }
}
