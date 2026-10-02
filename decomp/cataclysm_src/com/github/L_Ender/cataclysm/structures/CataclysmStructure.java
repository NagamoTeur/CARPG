package com.github.L_Ender.cataclysm.structures;

import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public abstract class CataclysmStructure extends Structure {
   private Set<Holder<Biome>> allowedBiomes;
   private boolean doCheckHeight;
   private boolean doAvoidWater;
   private boolean doAvoidStructures;

   public CataclysmStructure(
      StructureSettings settings, Set<Holder<Biome>> allowedBiomes, boolean doCheckHeight, boolean doAvoidWater, boolean doAvoidStructures
   ) {
      super(settings);
      this.allowedBiomes = allowedBiomes;
      this.doCheckHeight = doCheckHeight;
      this.doAvoidWater = doAvoidWater;
      this.doAvoidStructures = doAvoidStructures;
   }

   public CataclysmStructure(StructureSettings settings, Set<Holder<Biome>> allowedBiomes) {
      this(settings, allowedBiomes, true, true, true);
   }

   public CataclysmStructure(StructureSettings settings) {
      super(settings);
   }

   public Optional<GenerationStub> m_214086_(GenerationContext context) {
      return this.checkLocation(context) ? m_226585_(context, Types.WORLD_SURFACE_WG, builder -> this.generatePieces(builder, context)) : Optional.empty();
   }

   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
   }

   public boolean checkLocation(GenerationContext context) {
      return this.checkLocation(context, this.allowedBiomes, this.doCheckHeight, this.doAvoidWater, this.doAvoidStructures);
   }

   protected boolean checkLocation(
      GenerationContext context, Set<Holder<Biome>> allowedBiomes, boolean checkHeight, boolean avoidWater, boolean avoidStructures
   ) {
      ChunkPos chunkPos = context.f_226628_();
      BlockPos centerOfChunk = new BlockPos((chunkPos.f_45578_ << 4) + 7, 0, (chunkPos.f_45579_ << 4) + 7);
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

      return true;
   }

   public Decoration m_226619_() {
      return Decoration.SURFACE_STRUCTURES;
   }
}
