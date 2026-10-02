package com.github.L_Ender.cataclysm.structures.jisaw;

import com.github.L_Ender.cataclysm.init.ModStructures;
import com.github.L_Ender.cataclysm.world.structures.terrainadaptation.EnhancedTerrainAdaptation;
import com.github.L_Ender.cataclysm.world.structures.terrainadaptation.EnhancedTerrainAdaptationType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

public class CataclysmJigsawStructure extends Structure {
   public static final int MAX_TOTAL_STRUCTURE_RADIUS = 128;
   public static final Codec<CataclysmJigsawStructure> CODEC = RecordCodecBuilder.mapCodec(
         builder -> builder.group(
                  m_226567_(builder),
                  StructureTemplatePool.f_210555_.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                  ResourceLocation.f_135803_.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                  Codec.intRange(0, 128).fieldOf("size").forGetter(structure -> structure.maxDepth),
                  HeightProvider.f_161970_.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                  IntProvider.m_146545_(0, 15).optionalFieldOf("x_offset_in_chunk", ConstantInt.m_146483_(0)).forGetter(structure -> structure.xOffsetInChunk),
                  IntProvider.m_146545_(0, 15).optionalFieldOf("z_offset_in_chunk", ConstantInt.m_146483_(0)).forGetter(structure -> structure.zOffsetInChunk),
                  Codec.BOOL.optionalFieldOf("use_expansion_hack", false).forGetter(structure -> structure.useExpansionHack),
                  Types.f_64274_.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                  Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                  Codec.INT.optionalFieldOf("max_y").forGetter(structure -> structure.maxY),
                  Codec.INT.optionalFieldOf("min_y").forGetter(structure -> structure.minY),
                  EnhancedTerrainAdaptationType.ADAPTATION_CODEC
                     .optionalFieldOf("enhanced_terrain_adaptation", EnhancedTerrainAdaptation.NONE)
                     .forGetter(structure -> structure.enhancedTerrainAdaptation)
               )
               .apply(builder, CataclysmJigsawStructure::new)
      )
      .flatXmap(verifyRange(), verifyRange())
      .codec();
   public final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   public final int maxDepth;
   public final HeightProvider startHeight;
   public final IntProvider xOffsetInChunk;
   public final IntProvider zOffsetInChunk;
   public final boolean useExpansionHack;
   public final Optional<Types> projectStartToHeightmap;
   public final int maxDistanceFromCenter;
   public final Optional<Integer> maxY;
   public final Optional<Integer> minY;
   public final EnhancedTerrainAdaptation enhancedTerrainAdaptation;

   public CataclysmJigsawStructure(
      StructureSettings structureSettings,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int maxDepth,
      HeightProvider startHeight,
      IntProvider xOffsetInChunk,
      IntProvider zOffsetInChunk,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxBlockDistanceFromCenter,
      Optional<Integer> maxY,
      Optional<Integer> minY,
      EnhancedTerrainAdaptation enhancedTerrainAdaptation
   ) {
      super(structureSettings);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.maxDepth = maxDepth;
      this.startHeight = startHeight;
      this.xOffsetInChunk = xOffsetInChunk;
      this.zOffsetInChunk = zOffsetInChunk;
      this.useExpansionHack = useExpansionHack;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxBlockDistanceFromCenter;
      this.maxY = maxY;
      this.minY = minY;
      this.enhancedTerrainAdaptation = enhancedTerrainAdaptation;
   }

   private static Function<CataclysmJigsawStructure, DataResult<CataclysmJigsawStructure>> verifyRange() {
      return structure -> {
         if (structure.m_226620_() != TerrainAdjustment.NONE && structure.enhancedTerrainAdaptation != EnhancedTerrainAdaptation.NONE) {
            return DataResult.error("Cataclysm Structure cannot use both vanilla terrain_adaptation and enhanced_terrain_adaptation");
         } else {
            int vanillaEdgeBuffer = switch (structure.m_226620_()) {
               case NONE -> 0;
               case BURY, BEARD_THIN, BEARD_BOX -> 12;
               default -> throw new IncompatibleClassChangeError();
            };
            if (structure.maxDistanceFromCenter + vanillaEdgeBuffer > 128) {
               return DataResult.error("Cataclysm Structure size including terrain adaptation must not exceed 128");
            } else {
               int enhancedEdgeBuffer = structure.enhancedTerrainAdaptation.getKernelRadius();
               return structure.maxDistanceFromCenter + enhancedEdgeBuffer > 128
                  ? DataResult.error("Cataclysm Structure size including enhanced terrain adaptation must not exceed 128")
                  : DataResult.success(structure);
            }
         }
      };
   }

   @NotNull
   public Optional<GenerationStub> m_214086_(GenerationContext context) {
      ChunkPos chunkPos = context.f_226628_();
      RandomSource randomSource = context.f_226626_();
      int xOffset = this.xOffsetInChunk.m_214085_(randomSource);
      int zOffset = this.zOffsetInChunk.m_214085_(randomSource);
      int startY = this.startHeight.m_213859_(context.f_226626_(), new WorldGenerationContext(context.f_226622_(), context.f_226629_()));
      BlockPos startPos = new BlockPos(chunkPos.m_151382_(xOffset), startY, chunkPos.m_151391_(zOffset));
      return com.github.L_Ender.cataclysm.world.structures.Pieces.CataclysmJigsawManager.assembleJigsawStructure(
         context,
         this.startPool,
         this.startJigsawName,
         this.maxDepth,
         startPos,
         this.useExpansionHack,
         this.projectStartToHeightmap,
         this.maxDistanceFromCenter,
         this.maxY,
         this.minY
      );
   }

   @NotNull
   public BoundingBox m_226569_(@NotNull BoundingBox boundingBox) {
      return super.m_226569_(boundingBox).m_191961_(this.enhancedTerrainAdaptation.getKernelRadius());
   }

   @NotNull
   public StructureType<?> m_213658_() {
      return (StructureType<?>)ModStructures.CATACLYSM_JIGSAW.get();
   }
}
