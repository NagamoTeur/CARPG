package com.hollingsworth.arsnouveau.common.world.structure;

import com.hollingsworth.arsnouveau.setup.StructureRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class WildenDen extends Structure {
   public static final Codec<WildenDen> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
                  m_226567_(instance),
                  StructureTemplatePool.f_210555_.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                  ResourceLocation.f_135803_.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                  Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                  HeightProvider.f_161970_.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                  Types.f_64274_.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                  Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
               )
               .apply(instance, WildenDen::new)
      )
      .codec();
   public final Holder<StructureTemplatePool> startPool;
   public final Optional<ResourceLocation> startJigsawName;
   public final int size;
   public final HeightProvider startHeight;
   public final Optional<Types> projectStartToHeightmap;
   public final int maxDistanceFromCenter;
   public Optional<Integer> terrainHeightCheckRadius = Optional.empty();
   public Optional<Integer> allowedTerrainHeightRange = Optional.empty();
   public boolean cannotSpawnInLiquid = true;
   public Optional<Integer> minYAllowed = Optional.empty();
   public Optional<Integer> maxYAllowed = Optional.empty();

   public WildenDen(
      StructureSettings config,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int size,
      HeightProvider startHeight,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter
   ) {
      super(config);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.size = size;
      this.startHeight = startHeight;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
      this.terrainHeightCheckRadius = Optional.of(1);
      this.allowedTerrainHeightRange = Optional.of(6);
   }

   public Optional<GenerationStub> m_214086_(GenerationContext context) {
      ChunkPos chunkPos = context.f_226628_();
      if (this.cannotSpawnInLiquid) {
         BlockPos centerOfChunk = context.f_226628_().m_151394_(0);
         int landHeight = context.f_226622_()
            .m_223235_(centerOfChunk.m_123341_(), centerOfChunk.m_123343_(), Types.WORLD_SURFACE_WG, context.f_226629_(), context.f_226624_());
         NoiseColumn columnOfBlocks = context.f_226622_()
            .m_214184_(centerOfChunk.m_123341_(), centerOfChunk.m_123343_(), context.f_226629_(), context.f_226624_());
         BlockState topBlock = columnOfBlocks.m_183556_(centerOfChunk.m_123342_() + landHeight);
         if (!topBlock.m_60819_().m_76178_()) {
            return Optional.empty();
         }
      }

      if (this.terrainHeightCheckRadius.isPresent() && (this.allowedTerrainHeightRange.isPresent() || this.minYAllowed.isPresent())) {
         int maxTerrainHeight = Integer.MIN_VALUE;
         int minTerrainHeight = Integer.MAX_VALUE;
         int terrainCheckRange = this.terrainHeightCheckRadius.get();

         for (int curChunkX = chunkPos.f_45578_ - terrainCheckRange; curChunkX <= chunkPos.f_45578_ + terrainCheckRange; curChunkX++) {
            for (int curChunkZ = chunkPos.f_45579_ - terrainCheckRange; curChunkZ <= chunkPos.f_45579_ + terrainCheckRange; curChunkZ++) {
               int height = context.f_226622_()
                  .m_214096_(
                     (curChunkX << 4) + 7,
                     (curChunkZ << 4) + 7,
                     this.projectStartToHeightmap.orElse(Types.WORLD_SURFACE_WG),
                     context.f_226629_(),
                     context.f_226624_()
                  );
               maxTerrainHeight = Math.max(maxTerrainHeight, height);
               minTerrainHeight = Math.min(minTerrainHeight, height);
               if (this.minYAllowed.isPresent() && minTerrainHeight < this.minYAllowed.get()) {
                  return Optional.empty();
               }

               if (this.maxYAllowed.isPresent() && minTerrainHeight > this.maxYAllowed.get()) {
                  return Optional.empty();
               }
            }
         }

         if (this.allowedTerrainHeightRange.isPresent() && maxTerrainHeight - minTerrainHeight > this.allowedTerrainHeightRange.get()) {
            return Optional.empty();
         }
      }

      int startY = this.startHeight.m_213859_(context.f_226626_(), new WorldGenerationContext(context.f_226622_(), context.f_226629_()));
      BlockPos blockPos = new BlockPos(chunkPos.m_45604_(), startY, chunkPos.m_45605_());
      return JigsawPlacement.m_227238_(
         context, this.startPool, this.startJigsawName, this.size, blockPos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter
      );
   }

   public void m_214110_(
      WorldGenLevel level,
      StructureManager manager,
      ChunkGenerator p_226562_,
      RandomSource p_226563_,
      BoundingBox p_226564_,
      ChunkPos p_226565_,
      PiecesContainer p_226566_
   ) {
      super.m_214110_(level, manager, p_226562_, p_226563_, p_226564_, p_226565_, p_226566_);
      BlockPos.m_121976_(
            p_226564_.m_162395_(), p_226564_.m_162396_(), p_226564_.m_162398_(), p_226564_.m_162399_(), p_226564_.m_162400_(), p_226564_.m_162401_()
         )
         .forEach(
            pos -> {
               if (level.m_8055_(pos).m_60713_(Blocks.f_152539_)
                  && level.m_8055_(pos).m_61138_(CaveVinesPlantBlock.f_152949_)
                  && (Boolean)level.m_8055_(pos).m_61143_(CaveVinesPlantBlock.f_152949_)) {
                  level.m_7731_(pos, (BlockState)Blocks.f_152539_.m_49966_().m_61124_(CaveVinesPlantBlock.f_152949_, false), 2);
               }

               if (level.m_8055_(pos).m_60713_(Blocks.f_152538_)) {
                  level.m_7731_(
                     pos,
                     (BlockState)((BlockState)Blocks.f_152538_.m_49966_().m_61124_(CaveVinesPlantBlock.f_152949_, false))
                        .m_61124_(GrowingPlantHeadBlock.f_53924_, 25),
                     2
                  );
               }
            }
         );
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)StructureRegistry.WILDEN_DEN.get();
   }
}
