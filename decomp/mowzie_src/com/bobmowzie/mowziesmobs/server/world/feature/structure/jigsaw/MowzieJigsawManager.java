package com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class MowzieJigsawManager {
   static final Logger LOGGER = LogUtils.getLogger();
   private static Map<String, Integer> poolPlaceOrder = new HashMap<>();
   public static Comparator<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> placeOrderComparator = (p1, p2) -> {
      int p1Order = poolPlaceOrder.getOrDefault(((StructureBlockInfo)p1.getFirst()).f_74677_.m_128461_("pool"), 0);
      int p2Order = poolPlaceOrder.getOrDefault(((StructureBlockInfo)p2.getFirst()).f_74677_.m_128461_("pool"), 0);
      int result = Integer.compare(p1Order, p2Order);
      if (result == 0) {
         result = Integer.compare(((MowzieJigsawManager.PieceState)p1.getSecond()).depth, ((MowzieJigsawManager.PieceState)p2.getSecond()).depth);
      }

      if (result == 0) {
         result = Integer.compare(p1.hashCode(), p2.hashCode());
      }

      return result;
   };

   public static Optional<GenerationStub> addPieces(
      GenerationContext context,
      Holder<StructureTemplatePool> pool,
      BlockPos genPos,
      boolean villageBoundaryAdjust,
      boolean useTerrainHeight,
      int maxDistFromStart,
      String pathJigsawName,
      String interiorJigsawName,
      Set<String> mustConnectPools,
      Set<String> replacePools,
      String deadEndConnectorPool,
      int maxDepth
   ) {
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.m_190068_(context.f_226627_(), context.f_226628_().f_45578_, context.f_226628_().f_45579_);
      RegistryAccess registryaccess = context.f_226621_();
      ChunkGenerator chunkgenerator = context.f_226622_();
      StructureTemplateManager structuremanager = context.f_226625_();
      LevelHeightAccessor levelheightaccessor = context.f_226629_();
      Predicate<Holder<Biome>> predicate = context.f_226630_();
      Registry<StructureTemplatePool> registry = registryaccess.m_175515_(Registry.f_122884_);
      Rotation rotation = Rotation.m_221990_(worldgenrandom);
      StructureTemplatePool structuretemplatepool = (StructureTemplatePool)pool.m_203334_();
      StructurePoolElement structurepoolelement = structuretemplatepool.m_227355_(worldgenrandom);
      if (structurepoolelement == EmptyPoolElement.f_210175_) {
         return Optional.empty();
      } else {
         PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(
            structuremanager,
            structurepoolelement,
            genPos,
            structurepoolelement.m_210540_(),
            rotation,
            structurepoolelement.m_214015_(structuremanager, genPos, rotation)
         );
         BoundingBox pieceBoundingBox = poolelementstructurepiece.m_73547_();
         BlockPos offset = BlockPos.f_121853_;
         if (structurepoolelement instanceof MowziePoolElement) {
            offset = ((MowziePoolElement)structurepoolelement).bounds.offset;
            offset = offset.m_7954_(rotation);
         }

         int centerX = (pieceBoundingBox.m_162399_() + pieceBoundingBox.m_162395_()) / 2;
         int centerZ = (pieceBoundingBox.m_162401_() + pieceBoundingBox.m_162398_()) / 2;
         int height;
         if (useTerrainHeight) {
            height = genPos.m_123342_()
               + chunkgenerator.m_223221_(
                  centerX + offset.m_123341_(), centerZ + offset.m_123343_(), Types.WORLD_SURFACE_WG, levelheightaccessor, context.f_226624_()
               )
               + offset.m_123342_();
         } else {
            height = genPos.m_123342_();
         }

         if (!predicate.test(
            chunkgenerator.m_62218_()
               .m_203407_(QuartPos.m_175400_(centerX), QuartPos.m_175400_(height), QuartPos.m_175400_(centerZ), context.f_226624_().m_224579_())
         )) {
            return Optional.empty();
         } else {
            int l = pieceBoundingBox.m_162396_() + poolelementstructurepiece.m_72647_();
            poolelementstructurepiece.m_6324_(0, height - l, 0);
            return Optional.of(
               new GenerationStub(
                  new BlockPos(centerX, height, centerZ),
                  builder -> {
                     List<PoolElementStructurePiece> list = Lists.newArrayList();
                     list.add(poolelementstructurepiece);
                     if (maxDepth >= 0) {
                        AABB aabb = new AABB(
                           (double)(centerX - maxDistFromStart),
                           (double)(height - maxDistFromStart),
                           (double)(centerZ - maxDistFromStart),
                           (double)(centerX + maxDistFromStart + 1),
                           (double)(height + maxDistFromStart + 1),
                           (double)(centerZ + maxDistFromStart + 1)
                        );
                        VoxelShape shape = Shapes.m_83113_(Shapes.m_83064_(aabb), Shapes.m_83064_(AABB.m_82321_(pieceBoundingBox)), BooleanOp.f_82685_);
                        MutableObject<VoxelShape> free = new MutableObject(shape);
                        MutableObject<VoxelShape> interiorFree = new MutableObject(Shapes.m_83040_());
                        MutableObject<Map<String, VoxelShape>> specialBounds = new MutableObject(new HashMap());
                        MowzieJigsawManager.Placer placer = new MowzieJigsawManager.Placer(
                           registry,
                           maxDepth,
                           chunkgenerator,
                           structuremanager,
                           list,
                           worldgenrandom,
                           pathJigsawName,
                           interiorJigsawName,
                           free,
                           interiorFree,
                           specialBounds
                        );
                        String tag = null;
                        if (poolelementstructurepiece.m_209918_() instanceof MowziePoolElement) {
                           tag = ((MowziePoolElement)poolelementstructurepiece.m_209918_()).getRandomTag(worldgenrandom);
                        }

                        MowzieJigsawManager.PieceState startingPiece = new MowzieJigsawManager.PieceState(poolelementstructurepiece, 0, null, tag);

                        for (StructureBlockInfo structureBlockInfo : placer.getJigsawBlocksFromPieceState(startingPiece)) {
                           placer.placing.add(new Pair(structureBlockInfo, startingPiece));
                        }

                        while (!placer.placing.isEmpty()) {
                           Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = placer.placing.first();
                           placer.placing.remove(nextJigsawBlock);
                           if (((MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond()).depth > maxDepth) {
                              break;
                           }

                           placer.tryPlacingChildren(
                              (StructureBlockInfo)nextJigsawBlock.getFirst(),
                              (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                              villageBoundaryAdjust,
                              levelheightaccessor,
                              context.f_226624_()
                           );
                        }

                        MowzieJigsawManager.Placer fallbackPlacer = new MowzieJigsawManager.FallbackPlacer(
                           registry,
                           maxDepth,
                           chunkgenerator,
                           structuremanager,
                           list,
                           worldgenrandom,
                           pathJigsawName,
                           interiorJigsawName,
                           free,
                           interiorFree,
                           specialBounds,
                           placer
                        );

                        while (!fallbackPlacer.placing.isEmpty()) {
                           Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = fallbackPlacer.placing.first();
                           fallbackPlacer.placing.remove(nextJigsawBlock);
                           fallbackPlacer.tryPlacingChildren(
                              (StructureBlockInfo)nextJigsawBlock.getFirst(),
                              (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                              villageBoundaryAdjust,
                              levelheightaccessor,
                              context.f_226624_()
                           );
                        }

                        MowzieJigsawManager.Placer interiorPlacer = new MowzieJigsawManager.InteriorPlacer(
                           registry,
                           maxDepth,
                           chunkgenerator,
                           structuremanager,
                           list,
                           worldgenrandom,
                           pathJigsawName,
                           interiorJigsawName,
                           free,
                           interiorFree,
                           specialBounds,
                           fallbackPlacer
                        );

                        while (!interiorPlacer.placing.isEmpty()) {
                           Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = interiorPlacer.placing.first();
                           interiorPlacer.placing.remove(nextJigsawBlock);
                           interiorPlacer.tryPlacingChildren(
                              (StructureBlockInfo)nextJigsawBlock.getFirst(),
                              (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                              villageBoundaryAdjust,
                              levelheightaccessor,
                              context.f_226624_()
                           );
                        }

                        list.sort((p1, p2) -> {
                           int i2 = 0;
                           int i1 = 0;
                           if (p1.m_209918_() instanceof MowziePoolElement) {
                              i1 = ((MowziePoolElement)p1.m_209918_()).genOrder;
                           }

                           if (p2.m_209918_() instanceof MowziePoolElement) {
                              i2 = ((MowziePoolElement)p2.m_209918_()).genOrder;
                           }

                           return Integer.compare(i1, i2);
                        });
                        list.forEach(builder::m_142679_);
                     }
                  }
               )
            );
         }
      }
   }

   static {
      poolPlaceOrder.put("mowziesmobs:monastery/interior/wall_corner_pool", -2);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/wall_middle_pool", -2);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/center_pool", -3);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/blocker_pool", -4);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/tower_stairs_1_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/tower_stairs_2_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/room_stairs_1_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/room_stairs_2_pool", -5);
   }

   static final class FallbackPlacer extends MowzieJigsawManager.Placer {
      FallbackPlacer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds,
         MowzieJigsawManager.Placer previousPlacer
      ) {
         super(p_210323_, p_210324_, p_210326_, p_210327_, p_210328_, p_210329_, pathJigsawName, interiorJigsawName, free, interiorFree, specialBounds);
         this.placing.addAll(previousPlacer.placing);
         this.placing.addAll(previousPlacer.fallbacks);
         this.interior.addAll(previousPlacer.interior);
         this.numPaths = previousPlacer.numPaths;
      }

      @Override
      List<StructurePoolElement> addPoolElements(MowzieJigsawManager.PieceState pieceState, StructureTemplatePool pool, StructureTemplatePool fallbackPool) {
         List<StructurePoolElement> structurePoolElements = Lists.newArrayList();
         structurePoolElements.addAll(fallbackPool.m_227362_(this.random));
         return structurePoolElements;
      }
   }

   static final class InteriorPlacer extends MowzieJigsawManager.Placer {
      InteriorPlacer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds,
         MowzieJigsawManager.Placer previousPlacer
      ) {
         super(p_210323_, p_210324_, p_210326_, p_210327_, p_210328_, p_210329_, pathJigsawName, interiorJigsawName, free, interiorFree, specialBounds);
         this.placing.addAll(previousPlacer.interior);
         this.free = interiorFree;
         this.numPaths = previousPlacer.numPaths;
      }

      @Override
      protected boolean skipJigsawBlock(StructureBlockInfo thisPieceJigsawBlock, MowzieJigsawManager.PieceState pieceState) {
         return false;
      }

      @Override
      protected boolean checkBounds(
         StructurePoolElement nextPieceCandidate,
         MowzieJigsawManager.PieceState pieceState,
         BoundingBox nextPieceBoundingBoxPlaced,
         BlockPos nextPiecePos,
         Rotation nextPieceRotation,
         int i2
      ) {
         boolean ignoreBounds = false;
         BoundingBox spaceCheckBounds = nextPieceBoundingBoxPlaced;
         if (nextPieceCandidate instanceof MowziePoolElement) {
            ignoreBounds = ((MowziePoolElement)nextPieceCandidate).ignoresBounds();
            spaceCheckBounds = ((MowziePoolElement)nextPieceCandidate)
               .getCheckBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation)
               .m_71045_(0, i2, 0);
         }

         return ignoreBounds
            || !Shapes.m_83157_((VoxelShape)this.free.getValue(), Shapes.m_83064_(AABB.m_82321_(spaceCheckBounds).m_82406_(0.25)), BooleanOp.f_82683_);
      }
   }

   public interface PieceFactory {
      PoolElementStructurePiece create(StructureTemplateManager var1, StructurePoolElement var2, BlockPos var3, int var4, Rotation var5, BoundingBox var6);
   }

   public static record PieceSelection(
      MowzieJigsawManager.PieceState pieceState,
      StructurePoolElement origPiece,
      StructurePoolElement nextPiece,
      PoolElementStructurePiece poolelementstructurepiece,
      StructureBlockInfo origJigsaw,
      StructureBlockInfo connectedJigsaw,
      StructureBlockInfo nextJigsaw,
      BoundingBox nextPieceBoundingBoxPlaced,
      BoundingBox nextPieceInteriorBoundingBox,
      int l2
   ) {
   }

   static class PieceState {
      final PoolElementStructurePiece piece;
      final int depth;
      final MowzieJigsawManager.PieceState parent;
      final Set<MowzieJigsawManager.PieceState> children;
      final String tag;

      PieceState(PoolElementStructurePiece p_210311_, int p_210313_, MowzieJigsawManager.PieceState parent, String tag) {
         this.piece = p_210311_;
         this.depth = p_210313_;
         this.parent = parent;
         this.children = new HashSet<>();
         this.tag = tag;
      }
   }

   static class Placer {
      final Registry<StructureTemplatePool> pools;
      final int maxDepth;
      final ChunkGenerator chunkGenerator;
      final StructureTemplateManager structureManager;
      final List<? super PoolElementStructurePiece> pieces;
      protected final RandomSource random;
      protected final String pathJigsawName;
      protected final String interiorJigsawName;
      MutableObject<VoxelShape> free;
      MutableObject<VoxelShape> interiorFree;
      MutableObject<Map<String, VoxelShape>> specialBounds;
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> placing = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> fallbacks = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> interior = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      protected int numPaths;

      Placer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds
      ) {
         this.pools = p_210323_;
         this.maxDepth = p_210324_;
         this.chunkGenerator = p_210326_;
         this.structureManager = p_210327_;
         this.pieces = p_210328_;
         this.random = p_210329_;
         this.numPaths = 0;
         this.pathJigsawName = pathJigsawName;
         this.interiorJigsawName = interiorJigsawName;
         this.free = free;
         this.interiorFree = interiorFree;
         this.specialBounds = specialBounds;
      }

      void tryPlacingChildren(
         StructureBlockInfo thisPieceJigsawBlock,
         MowzieJigsawManager.PieceState pieceState,
         boolean villageBoundaryAdjust,
         LevelHeightAccessor heightAccessor,
         RandomState state
      ) {
         if (!this.skipJigsawBlock(thisPieceJigsawBlock, pieceState)) {
            if (thisPieceJigsawBlock.f_74677_.m_128461_("target").equals(this.pathJigsawName) && this.numPaths > 0) {
               this.numPaths--;
            }

            String pool = this.selectPool(thisPieceJigsawBlock);
            ResourceLocation poolResourceLocation = new ResourceLocation(pool);
            Optional<StructureTemplatePool> poolOptional = this.pools.m_6612_(poolResourceLocation);
            if (poolOptional.isPresent() && (poolOptional.get().m_210590_() != 0 || Objects.equals(poolResourceLocation, Pools.f_127186_.m_135782_()))) {
               ResourceLocation fallbackPoolResourceLocation = poolOptional.get().m_210573_();
               Optional<StructureTemplatePool> fallbackPoolOptional = this.pools.m_6612_(fallbackPoolResourceLocation);
               if (fallbackPoolOptional.isPresent()
                  && (fallbackPoolOptional.get().m_210590_() != 0 || Objects.equals(fallbackPoolResourceLocation, Pools.f_127186_.m_135782_()))) {
                  MowzieJigsawManager.PieceSelection pieceSelection = this.selectPiece(
                     pieceState, poolOptional.get(), fallbackPoolOptional.get(), villageBoundaryAdjust, thisPieceJigsawBlock, heightAccessor, state
                  );
                  if (pieceSelection != null) {
                     this.addNextPieceState(pieceSelection);
                  }
               } else {
                  MowzieJigsawManager.LOGGER.warn("Empty or non-existent fallback pool: {}", fallbackPoolResourceLocation);
               }
            } else {
               MowzieJigsawManager.LOGGER.warn("Empty or non-existent pool: {}", poolResourceLocation);
            }
         }
      }

      protected boolean skipJigsawBlock(StructureBlockInfo thisPieceJigsawBlock, MowzieJigsawManager.PieceState pieceState) {
         if (thisPieceJigsawBlock.f_74677_.m_128461_("target").equals(this.interiorJigsawName)) {
            this.interior.add(Pair.of(thisPieceJigsawBlock, pieceState));
            return true;
         } else {
            return false;
         }
      }

      List<StructureBlockInfo> getJigsawBlocksFromPieceState(MowzieJigsawManager.PieceState pieceState) {
         StructurePoolElement structurepoolelement = pieceState.piece.m_209918_();
         BlockPos blockpos = pieceState.piece.m_72646_();
         Rotation rotation = pieceState.piece.m_6830_();
         return structurepoolelement.m_213638_(this.structureManager, blockpos, rotation, this.random);
      }

      String selectPool(StructureBlockInfo thisPieceJigsawBlock) {
         return thisPieceJigsawBlock.f_74677_.m_128461_("pool");
      }

      List<StructurePoolElement> addPoolElements(MowzieJigsawManager.PieceState pieceState, StructureTemplatePool pool, StructureTemplatePool fallbackPool) {
         List<StructurePoolElement> structurePoolElements = Lists.newArrayList();
         structurePoolElements.addAll(pool.m_227362_(this.random));
         return structurePoolElements;
      }

      boolean isJigsawBlockFacingFreeSpace(StructureBlockInfo jigsawBlockInfo, MutableObject<VoxelShape> freeSpace) {
         Direction direction = JigsawBlock.m_54250_(jigsawBlockInfo.f_74676_);
         BlockPos thisJigsawBlockPos = jigsawBlockInfo.f_74675_;
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.m_121945_(direction);
         AABB aabb = new AABB(nextJigsawBlockPos);
         return !Shapes.m_83157_((VoxelShape)freeSpace.getValue(), Shapes.m_83064_(aabb.m_82406_(0.25)), BooleanOp.f_82683_);
      }

      boolean canJigsawBlockFitNextPiece(StructureBlockInfo jigsawBlockInfo, MutableObject<VoxelShape> freeSpace) {
         Direction direction = JigsawBlock.m_54250_(jigsawBlockInfo.f_74676_);
         BlockPos thisJigsawBlockPos = jigsawBlockInfo.f_74675_;
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.m_121945_(direction);
         AABB aabb = new AABB(nextJigsawBlockPos);
         Vec3i offset = direction.m_122436_();
         aabb = aabb.m_82400_(2.0).m_82386_((double)(offset.m_123341_() * 2), (double)(offset.m_123342_() * 2), (double)(offset.m_123343_() * 2));
         return !Shapes.m_83157_((VoxelShape)freeSpace.getValue(), Shapes.m_83064_(aabb.m_82406_(0.25)), BooleanOp.f_82683_);
      }

      MowzieJigsawManager.PieceSelection selectPiece(
         MowzieJigsawManager.PieceState pieceState,
         StructureTemplatePool poolOptional,
         StructureTemplatePool fallbackPoolOptional,
         boolean villageBoundaryAdjust,
         StructureBlockInfo thisPieceJigsawBlock,
         LevelHeightAccessor heightAccessor,
         RandomState state
      ) {
         BoundingBox thisPieceBoundingBox = pieceState.piece.m_73547_();
         int thisPieceMinY = thisPieceBoundingBox.m_162396_();
         Direction direction = JigsawBlock.m_54250_(thisPieceJigsawBlock.f_74676_);
         BlockPos thisJigsawBlockPos = thisPieceJigsawBlock.f_74675_;
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.m_121945_(direction);
         int thisPieceHeightFromBottomToJigsawBlock = thisJigsawBlockPos.m_123342_() - thisPieceMinY;
         StructurePoolElement structurepoolelement = pieceState.piece.m_209918_();
         Projection structuretemplatepool$projection = structurepoolelement.m_210539_();
         boolean thisPieceIsRigid = structuretemplatepool$projection == Projection.RIGID;
         int k = -1;
         List<StructurePoolElement> structurePoolElements = this.addPoolElements(pieceState, poolOptional, fallbackPoolOptional);
         structurePoolElements.sort((p1, p2) -> {
            int i2x = 0;
            int i1 = 0;
            if (p1 instanceof MowziePoolElement) {
               i1 = ((MowziePoolElement)p1).priority;
            }

            if (p2 instanceof MowziePoolElement) {
               i2x = ((MowziePoolElement)p2).priority;
            }

            return Integer.compare(i1, i2x);
         });

         label169:
         for (StructurePoolElement nextPieceCandidate : structurePoolElements) {
            if (nextPieceCandidate != FallbackPoolElement.INSTANCE) {
               if (nextPieceCandidate != EmptyPoolElement.f_210175_
                  && (
                     !(nextPieceCandidate instanceof SinglePoolElement)
                        || !((SinglePoolElement)nextPieceCandidate).toString().equals("Single[Left[minecraft:empty]]")
                  )) {
                  if (nextPieceCandidate instanceof MowziePoolElement mowziePoolElement && !mowziePoolElement.checkCriteria(pieceState, this)) {
                     continue;
                  }

                  Iterator var46 = Rotation.m_221992_(this.random).iterator();

                  Rotation nextPieceRotation;
                  StructureBlockInfo nextPieceJigsawBlock;
                  BlockPos nextPiecePos;
                  boolean nextPieceIsRigid;
                  int nextPieceJigsawBlockY;
                  int k1;
                  int l1;
                  int i2;
                  BoundingBox nextPieceBoundingBoxPlaced;
                  BlockPos blockpos5;
                  label155:
                  while (true) {
                     if (!var46.hasNext()) {
                        continue label169;
                     }

                     nextPieceRotation = (Rotation)var46.next();
                     List<StructureBlockInfo> nextPieceJigsawBlocks = nextPieceCandidate.m_213638_(
                        this.structureManager, BlockPos.f_121853_, nextPieceRotation, this.random
                     );
                     BoundingBox nextPieceBoundingBoxOrigin = nextPieceCandidate.m_214015_(this.structureManager, BlockPos.f_121853_, nextPieceRotation);
                     int largestSizeOfNextNextPiece;
                     if (villageBoundaryAdjust && nextPieceBoundingBoxOrigin.m_71057_() <= 16) {
                        largestSizeOfNextNextPiece = nextPieceJigsawBlocks.stream().mapToInt(blockInfo -> {
                           if (!nextPieceBoundingBoxOrigin.m_71051_(blockInfo.f_74675_.m_121945_(JigsawBlock.m_54250_(blockInfo.f_74676_)))) {
                              return 0;
                           } else {
                              ResourceLocation resourcelocation2 = new ResourceLocation(blockInfo.f_74677_.m_128461_("pool"));
                              Optional<StructureTemplatePool> optional2 = this.pools.m_6612_(resourcelocation2);
                              Optional<StructureTemplatePool> optional3 = optional2.flatMap(p_210344_ -> this.pools.m_6612_(p_210344_.m_210573_()));
                              int j3 = optional2.<Integer>map(structureTemplatePool -> structureTemplatePool.m_227357_(this.structureManager)).orElse(0);
                              int k3 = optional3.<Integer>map(structureTemplatePool -> structureTemplatePool.m_227357_(this.structureManager)).orElse(0);
                              return Math.max(j3, k3);
                           }
                        }).max().orElse(0);
                     } else {
                        largestSizeOfNextNextPiece = 0;
                     }

                     for (nextPieceJigsawBlock : nextPieceJigsawBlocks) {
                        boolean canAttach;
                        if (nextPieceCandidate instanceof MowziePoolElement && ((MowziePoolElement)nextPieceCandidate).twoWay()) {
                           canAttach = MowziePoolElement.canAttachTwoWays(thisPieceJigsawBlock, nextPieceJigsawBlock);
                        } else {
                           canAttach = JigsawBlock.m_54245_(thisPieceJigsawBlock, nextPieceJigsawBlock);
                        }

                        if (canAttach) {
                           BlockPos nextPieceJigsawBlockPos = nextPieceJigsawBlock.f_74675_;
                           nextPiecePos = nextJigsawBlockPos.m_121996_(nextPieceJigsawBlockPos);
                           BoundingBox nextPieceBoundingBox = nextPieceCandidate.m_214015_(this.structureManager, nextPiecePos, nextPieceRotation);
                           int nextPieceMinY = nextPieceBoundingBox.m_162396_();
                           if (nextPieceCandidate instanceof MowziePoolElement) {
                              nextPieceMinY -= ((MowziePoolElement)nextPieceCandidate).bounds.boundsMinOffset.m_123342_();
                           }

                           Projection structuretemplatepool$projection1 = nextPieceCandidate.m_210539_();
                           nextPieceIsRigid = structuretemplatepool$projection1 == Projection.RIGID;
                           nextPieceJigsawBlockY = nextPieceJigsawBlockPos.m_123342_();
                           k1 = thisPieceHeightFromBottomToJigsawBlock
                              - nextPieceJigsawBlockY
                              + JigsawBlock.m_54250_(thisPieceJigsawBlock.f_74676_).m_122430_();
                           if (thisPieceIsRigid && nextPieceIsRigid) {
                              l1 = thisPieceMinY + k1;
                           } else {
                              if (k == -1) {
                                 k = this.chunkGenerator
                                    .m_223221_(thisJigsawBlockPos.m_123341_(), thisJigsawBlockPos.m_123343_(), Types.WORLD_SURFACE_WG, heightAccessor, state);
                              }

                              l1 = k - nextPieceJigsawBlockY;
                           }

                           i2 = l1 - nextPieceMinY;
                           nextPieceBoundingBoxPlaced = nextPieceBoundingBox.m_71045_(0, i2, 0);
                           blockpos5 = nextPiecePos.m_7918_(0, i2, 0);
                           if (largestSizeOfNextNextPiece > 0) {
                              int j2 = Math.max(largestSizeOfNextNextPiece + 1, nextPieceBoundingBoxPlaced.m_162400_() - nextPieceBoundingBoxPlaced.m_162396_());
                              nextPieceBoundingBoxPlaced.m_162371_(
                                 new BlockPos(
                                    nextPieceBoundingBoxPlaced.m_162395_(), nextPieceBoundingBoxPlaced.m_162396_() + j2, nextPieceBoundingBoxPlaced.m_162398_()
                                 )
                              );
                           }

                           if (nextPieceCandidate instanceof MowziePoolElement) {
                              Optional<Integer> maxHeight = ((MowziePoolElement)nextPieceCandidate).conditions.maxHeight;
                              Optional<Integer> minHeight = ((MowziePoolElement)nextPieceCandidate).conditions.minHeight;
                              if (maxHeight.isPresent() || minHeight.isPresent()) {
                                 int freeHeight = this.chunkGenerator
                                    .m_223221_(
                                       nextPieceBoundingBoxPlaced.m_162395_() + nextPieceBoundingBoxPlaced.m_71056_() / 2,
                                       nextPieceBoundingBoxPlaced.m_162398_() + nextPieceBoundingBoxPlaced.m_71058_() / 2,
                                       Types.WORLD_SURFACE_WG,
                                       heightAccessor,
                                       state
                                    );
                                 if (maxHeight.isPresent() && nextPieceMinY - freeHeight > maxHeight.get()
                                    || minHeight.isPresent() && nextPieceMinY - freeHeight < minHeight.get()) {
                                    continue;
                                 }
                              }
                           }

                           if (this.checkBounds(nextPieceCandidate, pieceState, nextPieceBoundingBoxPlaced, nextPiecePos, nextPieceRotation, i2)) {
                              if (!(nextPieceCandidate instanceof MowziePoolElement mowziePoolElement)) {
                                 break label155;
                              }

                              Optional<String> needsOverlapBounds = mowziePoolElement.bounds.needsOverlapBounds;
                              if (!needsOverlapBounds.isPresent()
                                 || ((Map)this.specialBounds.getValue()).containsKey(needsOverlapBounds.get())
                                    && Shapes.m_83157_(
                                       (VoxelShape)((Map)this.specialBounds.getValue()).get(needsOverlapBounds.get()),
                                       Shapes.m_83064_(AABB.m_82321_(nextPieceBoundingBoxPlaced).m_82406_(0.25)),
                                       BooleanOp.f_82689_
                                    )) {
                                 Optional<String> forbiddenOverlapBounds = mowziePoolElement.bounds.forbiddenOverlapBounds;
                                 if (!forbiddenOverlapBounds.isPresent()
                                    || !((Map)this.specialBounds.getValue()).containsKey(forbiddenOverlapBounds.get())
                                    || !Shapes.m_83157_(
                                       (VoxelShape)((Map)this.specialBounds.getValue()).get(forbiddenOverlapBounds.get()),
                                       Shapes.m_83064_(AABB.m_82321_(nextPieceBoundingBoxPlaced).m_82406_(0.25)),
                                       BooleanOp.f_82689_
                                    )) {
                                    break label155;
                                 }
                              }
                           }
                        }
                     }
                  }

                  int pieceGroundLevelDelta = pieceState.piece.m_72647_();
                  int k2;
                  if (nextPieceIsRigid) {
                     k2 = pieceGroundLevelDelta - k1;
                  } else {
                     k2 = nextPieceCandidate.m_210540_();
                  }

                  PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(
                     this.structureManager, nextPieceCandidate, blockpos5, k2, nextPieceRotation, nextPieceBoundingBoxPlaced
                  );
                  int l2;
                  if (thisPieceIsRigid) {
                     l2 = thisPieceMinY + thisPieceHeightFromBottomToJigsawBlock;
                  } else if (nextPieceIsRigid) {
                     l2 = l1 + nextPieceJigsawBlockY;
                  } else {
                     if (k == -1) {
                        k = this.chunkGenerator
                           .m_223221_(thisJigsawBlockPos.m_123341_(), thisJigsawBlockPos.m_123343_(), Types.WORLD_SURFACE_WG, heightAccessor, state);
                     }

                     l2 = k + k1 / 2;
                  }

                  BoundingBox interiorBounds = null;
                  if (nextPieceCandidate instanceof MowziePoolElement) {
                     interiorBounds = ((MowziePoolElement)nextPieceCandidate).getInteriorBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation);
                     if (interiorBounds != null) {
                        interiorBounds = interiorBounds.m_71045_(0, i2, 0);
                     }
                  }

                  return new MowzieJigsawManager.PieceSelection(
                     pieceState,
                     structurepoolelement,
                     nextPieceCandidate,
                     poolelementstructurepiece,
                     thisPieceJigsawBlock,
                     nextPieceJigsawBlock,
                     null,
                     nextPieceBoundingBoxPlaced,
                     interiorBounds,
                     l2
                  );
               }

               return null;
            }
            break;
         }

         this.fallbacks.add(new Pair(thisPieceJigsawBlock, pieceState));
         return null;
      }

      protected boolean checkBounds(
         StructurePoolElement nextPieceCandidate,
         MowzieJigsawManager.PieceState pieceState,
         BoundingBox nextPieceBoundingBoxPlaced,
         BlockPos nextPiecePos,
         Rotation nextPieceRotation,
         int i2
      ) {
         boolean ignoreBounds = false;
         BoundingBox spaceCheckBounds = nextPieceBoundingBoxPlaced;
         if (nextPieceCandidate instanceof MowziePoolElement) {
            ignoreBounds = ((MowziePoolElement)nextPieceCandidate).ignoresBounds();
            spaceCheckBounds = ((MowziePoolElement)nextPieceCandidate)
               .getCheckBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation)
               .m_71045_(0, i2, 0);
         }

         VoxelShape freeSpace = Shapes.m_83148_(
            (VoxelShape)this.free.getValue(), Shapes.m_83064_(AABB.m_82321_(pieceState.piece.m_73547_())), BooleanOp.f_82695_
         );
         return ignoreBounds || !Shapes.m_83157_(freeSpace, Shapes.m_83064_(AABB.m_82321_(spaceCheckBounds).m_82406_(0.25)), BooleanOp.f_82683_);
      }

      void addNextPieceState(MowzieJigsawManager.PieceSelection pieceSelection) {
         if (!(pieceSelection.nextPiece instanceof MowziePoolElement)
            || !((MowziePoolElement)pieceSelection.nextPiece).ignoresBounds() && ((MowziePoolElement)pieceSelection.nextPiece).placeBounds()) {
            this.free
               .setValue(
                  Shapes.m_83148_(
                     (VoxelShape)this.free.getValue(), Shapes.m_83064_(AABB.m_82321_(pieceSelection.nextPieceBoundingBoxPlaced)), BooleanOp.f_82685_
                  )
               );
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement && pieceSelection.nextPieceInteriorBoundingBox != null) {
            this.interiorFree
               .setValue(
                  Shapes.m_83148_(
                     (VoxelShape)this.interiorFree.getValue(), Shapes.m_83064_(AABB.m_82321_(pieceSelection.nextPieceInteriorBoundingBox)), BooleanOp.f_82695_
                  )
               );
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement mowziePoolElement) {
            Optional<String> nextPieceSpecialBounds = mowziePoolElement.bounds.specialBounds;
            if (nextPieceSpecialBounds.isPresent()) {
               if (((Map)this.specialBounds.getValue()).containsKey(nextPieceSpecialBounds.get())) {
                  ((Map)this.specialBounds.getValue())
                     .put(
                        nextPieceSpecialBounds.get(),
                        Shapes.m_83148_(
                           (VoxelShape)((Map)this.specialBounds.getValue()).get(nextPieceSpecialBounds.get()),
                           Shapes.m_83064_(AABB.m_82321_(pieceSelection.nextPieceBoundingBoxPlaced)),
                           BooleanOp.f_82695_
                        )
                     );
               } else {
                  ((Map)this.specialBounds.getValue())
                     .put(nextPieceSpecialBounds.get(), Shapes.m_83064_(AABB.m_82321_(pieceSelection.nextPieceBoundingBoxPlaced)));
               }
            }
         }

         BoundingBox thisPieceBoundingBox = pieceSelection.pieceState.piece.m_73547_();
         int thisPieceMinY = thisPieceBoundingBox.m_162396_();
         int thisPieceHeightFromBottomToJigsawBlock = pieceSelection.origJigsaw.f_74675_.m_123342_() - thisPieceMinY;
         int pieceGroundLevelDelta = pieceSelection.pieceState.piece.m_72647_();
         int k1 = thisPieceHeightFromBottomToJigsawBlock
            - pieceSelection.connectedJigsaw.f_74675_.m_123342_()
            + JigsawBlock.m_54250_(pieceSelection.origJigsaw.f_74676_).m_122430_();
         int k2;
         if (pieceSelection.nextPiece.m_210539_() == Projection.RIGID) {
            k2 = pieceGroundLevelDelta - k1;
         } else {
            k2 = pieceSelection.nextPiece.m_210540_();
         }

         pieceSelection.pieceState
            .piece
            .m_209916_(
               new JigsawJunction(
                  pieceSelection.connectedJigsaw.f_74675_.m_123341_(),
                  pieceSelection.l2 - thisPieceHeightFromBottomToJigsawBlock + pieceGroundLevelDelta,
                  pieceSelection.connectedJigsaw.f_74675_.m_123343_(),
                  k1,
                  pieceSelection.nextPiece.m_210539_()
               )
            );
         pieceSelection.poolelementstructurepiece
            .m_209916_(
               new JigsawJunction(
                  pieceSelection.origJigsaw.f_74675_.m_123341_(),
                  pieceSelection.l2 - pieceSelection.connectedJigsaw.f_74675_.m_123342_() + k2,
                  pieceSelection.origJigsaw.f_74675_.m_123343_(),
                  -k1,
                  pieceSelection.origPiece.m_210539_()
               )
            );
         this.pieces.add(pieceSelection.poolelementstructurepiece);
         String tag = null;
         if (pieceSelection.nextPiece instanceof MowziePoolElement mowziePoolElementx) {
            if (mowziePoolElementx.tags.inheritsTag) {
               tag = pieceSelection.pieceState.tag;
            } else {
               tag = ((MowziePoolElement)pieceSelection.nextPiece).getRandomTag(this.random);
            }
         }

         MowzieJigsawManager.PieceState nextPieceState = new MowzieJigsawManager.PieceState(
            pieceSelection.poolelementstructurepiece, pieceSelection.pieceState.depth + 1, pieceSelection.pieceState, tag
         );
         List<StructureBlockInfo> nextJigsaws = this.getJigsawBlocksFromPieceState(nextPieceState);
         if (!(pieceSelection.poolelementstructurepiece.m_209918_() instanceof MowziePoolElement)
            || !((MowziePoolElement)pieceSelection.poolelementstructurepiece.m_209918_()).twoWay()) {
            nextJigsaws.removeIf(jigsawx -> {
               Direction direction = JigsawBlock.m_54250_(pieceSelection.origJigsaw.f_74676_);
               BlockPos thisJigsawBlockPos = pieceSelection.origJigsaw.f_74675_;
               BlockPos nextJigsawBlockPos = thisJigsawBlockPos.m_121945_(direction);
               return jigsawx.f_74675_.equals(nextJigsawBlockPos);
            });
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement && ((MowziePoolElement)pieceSelection.nextPiece).conditions.numPathsOverride.isPresent()) {
            this.numPaths = this.numPaths + ((MowziePoolElement)pieceSelection.nextPiece).conditions.numPathsOverride.get();
         } else {
            for (StructureBlockInfo jigsaw : nextJigsaws) {
               if (jigsaw.f_74677_.m_128461_("target").equals(this.pathJigsawName)) {
                  this.numPaths++;
               }
            }
         }

         for (StructureBlockInfo jigsawx : nextJigsaws) {
            this.placing.add(new Pair(jigsawx, nextPieceState));
         }

         pieceSelection.pieceState.children.add(nextPieceState);
      }
   }
}
