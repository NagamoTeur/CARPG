package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public abstract class MowzieStructure extends Structure {
   private final ConfigHandler.GenerationConfig config;
   private Set<Holder<Biome>> allowedBiomes;
   private boolean doCheckHeight;
   private boolean doAvoidWater;
   private boolean doAvoidStructures;

   public MowzieStructure(
      StructureSettings settings,
      ConfigHandler.GenerationConfig config,
      Set<Holder<Biome>> allowedBiomes,
      boolean doCheckHeight,
      boolean doAvoidWater,
      boolean doAvoidStructures
   ) {
      super(settings);
      this.config = config;
      this.allowedBiomes = allowedBiomes;
      this.doCheckHeight = doCheckHeight;
      this.doAvoidWater = doAvoidWater;
      this.doAvoidStructures = doAvoidStructures;
   }

   public MowzieStructure(StructureSettings settings, ConfigHandler.GenerationConfig config, Set<Holder<Biome>> allowedBiomes) {
      this(settings, config, allowedBiomes, true, true, true);
   }

   public MowzieStructure(StructureSettings settings, ConfigHandler.GenerationConfig config) {
      super(settings);
      this.config = config;
   }

   public Optional<GenerationStub> m_214086_(GenerationContext context) {
      return this.checkLocation(context) ? m_226585_(context, Types.WORLD_SURFACE_WG, builder -> this.generatePieces(builder, context)) : Optional.empty();
   }

   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
   }

   public boolean checkLocation(GenerationContext context) {
      return this.checkLocation(context, this.config, this.allowedBiomes, this.doCheckHeight, this.doAvoidWater, this.doAvoidStructures);
   }

   protected boolean checkLocation(
      GenerationContext context,
      ConfigHandler.GenerationConfig config,
      Set<Holder<Biome>> allowedBiomes,
      boolean checkHeight,
      boolean avoidWater,
      boolean avoidStructures
   ) {
      if ((Integer)config.generationDistance.get() < 0) {
         return false;
      } else {
         ChunkPos chunkPos = context.f_226628_();
         BlockPos centerOfChunk = new BlockPos((chunkPos.f_45578_ << 4) + 7, 0, (chunkPos.f_45579_ << 4) + 7);
         int i = chunkPos.m_151390_();
         int j = chunkPos.m_151393_();
         int k = context.f_226622_().m_223235_(i, j, Types.WORLD_SURFACE_WG, context.f_226629_(), context.f_226624_());
         Holder<Biome> biome = context.f_226622_()
            .m_62218_()
            .m_203407_(QuartPos.m_175400_(i), QuartPos.m_175400_(k), QuartPos.m_175400_(j), context.f_226624_().m_224579_());
         if (!allowedBiomes.contains(biome)) {
            return false;
         } else {
            if (checkHeight) {
               double minHeight = (Double)config.heightMin.get();
               double maxHeight = (Double)config.heightMax.get();
               int landHeight = m_226572_(context, 16, 16);
               if (minHeight != -65.0 && (double)landHeight < minHeight) {
                  return false;
               }

               if (maxHeight != -65.0 && (double)landHeight > maxHeight) {
                  return false;
               }
            }

            if (avoidWater) {
               ChunkGenerator chunkGenerator = context.f_226622_();
               LevelHeightAccessor heightLimitView = context.f_226629_();
               int centerHeight = chunkGenerator.m_214096_(
                  centerOfChunk.m_123341_(), centerOfChunk.m_123343_(), Types.WORLD_SURFACE_WG, heightLimitView, context.f_226624_()
               );
               NoiseColumn columnOfBlocks = chunkGenerator.m_214184_(centerOfChunk.m_123341_(), centerOfChunk.m_123343_(), heightLimitView, context.f_226624_());
               BlockState topBlock = columnOfBlocks.m_183556_(centerHeight);
               if (!topBlock.m_60819_().m_76178_()) {
                  return false;
               }
            }

            if (avoidStructures) {
               List<? extends String> structuresToAvoid = (List<? extends String>)config.avoidStructures.get();
               Registry<StructureSet> structureSetRegistry = context.f_226621_().m_175515_(Registry.f_211073_);

               for (String structureName : structuresToAvoid) {
                  Optional<StructureSet> structureSetOptional = structureSetRegistry.m_6612_(new ResourceLocation(structureName));
                  if (!structureSetOptional.isEmpty()) {
                     Optional<ResourceKey<StructureSet>> resourceKeyOptional = structureSetRegistry.m_7854_(structureSetOptional.get());
                     if (!resourceKeyOptional.isEmpty()
                        && context.f_226622_()
                           .m_223141_(
                              BuiltinRegistries.f_211084_.m_206081_(resourceKeyOptional.get()),
                              context.f_226624_(),
                              context.f_226627_(),
                              chunkPos.f_45578_,
                              chunkPos.f_45579_,
                              5
                           )) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   public Decoration m_226619_() {
      return Decoration.SURFACE_STRUCTURES;
   }

   public ConfigHandler.GenerationConfig getConfig() {
      return this.config;
   }
}
