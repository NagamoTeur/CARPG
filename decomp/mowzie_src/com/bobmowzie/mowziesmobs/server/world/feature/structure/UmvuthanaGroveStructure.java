package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.ConfiguredFeatureHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class UmvuthanaGroveStructure extends MowzieStructure {
   public static final Codec<UmvuthanaGroveStructure> CODEC = m_226607_(UmvuthanaGroveStructure::new);

   public UmvuthanaGroveStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.UMVUTHI.generationConfig, ConfiguredFeatureHandler.UMVUTHI_BIOMES);
   }

   @Override
   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
      Rotation rotation = Rotation.values()[context.f_226626_().m_188503_(Rotation.values().length)];
      int x = (context.f_226628_().f_45578_ << 4) + 7;
      int z = (context.f_226628_().f_45579_ << 4) + 7;
      BlockPos centerPos = new BlockPos(x, 1, z);
      ChunkGenerator generator = context.f_226622_();
      LevelHeightAccessor heightLimitView = context.f_226629_();
      WorldgenRandom random = context.f_226626_();
      int surfaceY = generator.m_214096_(centerPos.m_123341_(), centerPos.m_123343_(), Types.WORLD_SURFACE_WG, heightLimitView, context.f_226624_());
      int oceanFloorY = generator.m_214096_(centerPos.m_123341_(), centerPos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_());
      if (oceanFloorY >= surfaceY) {
         BlockPos firepitPos = posToSurface(generator, centerPos, heightLimitView, context.f_226624_());
         builder.m_142679_(
            new UmvuthanaGrovePieces.FirepitPiece(context.f_226625_(), Rotation.values()[random.m_188503_(Rotation.values().length)], firepitPos)
         );
         BlockPos offset = new BlockPos(0, 0, 9);
         offset = offset.m_7954_(rotation);
         BlockPos thronePos = posToSurface(generator, centerPos.m_121955_(offset), heightLimitView, context.f_226624_());
         UmvuthanaGrovePieces.addPiece(UmvuthanaGrovePieces.THRONE, context.f_226625_(), thronePos, rotation, builder, context.f_226626_());
         int numHouses = random.m_188503_(2) + 2;

         for (int i = 1; i <= numHouses; i++) {
            for (int j = 0; j < 30; j++) {
               float distance = (float)(random.m_188503_(8) + 13);
               int angle = random.m_188503_(360);
               BlockPos housePos = new BlockPos(
                  (double)centerPos.m_123341_() + (double)distance * Math.sin(Math.toRadians((double)angle)),
                  0.0,
                  (double)centerPos.m_123343_() + (double)distance * Math.cos(Math.toRadians((double)angle))
               );
               housePos = posToSurface(generator, housePos, heightLimitView, context.f_226624_());
               int houseOceanFloorY = generator.m_214096_(
                  housePos.m_123341_(), housePos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_()
               );
               if (houseOceanFloorY >= housePos.m_123342_() && startPlatform(generator, context.f_226625_(), builder, housePos, context.f_226626_())) {
                  break;
               }
            }
         }

         int numTrees = random.m_188503_(3) + 2;

         for (int i = 1; i <= numTrees; i++) {
            for (int jx = 0; jx < 30; jx++) {
               float distance = (float)(random.m_188503_(14) + 13);
               int angle = random.m_188503_(360);
               BlockPos treePos = new BlockPos(
                  (double)centerPos.m_123341_() + (double)distance * Math.sin(Math.toRadians((double)angle)),
                  0.0,
                  (double)centerPos.m_123343_() + (double)distance * Math.cos(Math.toRadians((double)angle))
               );
               treePos = posToSurface(generator, treePos, heightLimitView, context.f_226624_());
               int treeOceanFloorY = generator.m_214096_(treePos.m_123341_(), treePos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_());
               if (treeOceanFloorY >= treePos.m_123342_()) {
                  int whichTree = random.m_188503_(UmvuthanaGrovePieces.TREES.length);
                  StructurePiece tree = UmvuthanaGrovePieces.addPieceCheckBounds(
                     UmvuthanaGrovePieces.TREES[whichTree],
                     context.f_226625_(),
                     treePos,
                     Rotation.values()[random.m_188503_(Rotation.values().length)],
                     builder,
                     context.f_226626_()
                  );
                  if (tree != null) {
                     break;
                  }
               }
            }
         }

         int numFirepits = random.m_188503_(3) + 2;

         for (int i = 1; i <= numFirepits; i++) {
            for (int jxx = 1; jxx <= 10; jxx++) {
               int distance = random.m_188503_(15) + 8;
               int angle = random.m_188503_(360);
               BlockPos pitPos = new BlockPos(
                  (double)centerPos.m_123341_() + (double)distance * Math.sin(Math.toRadians((double)angle)),
                  0.0,
                  (double)centerPos.m_123343_() + (double)distance * Math.cos(Math.toRadians((double)angle))
               );
               pitPos = posToSurface(generator, pitPos, heightLimitView, context.f_226624_());
               int pitOceanFloorY = generator.m_214096_(pitPos.m_123341_(), pitPos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_());
               if (pitOceanFloorY >= pitPos.m_123342_()) {
                  ResourceLocation whichPit = UmvuthanaGrovePieces.FIREPIT_SMALL[random.m_188503_(UmvuthanaGrovePieces.FIREPIT_SMALL.length)];
                  StructurePiece piece = UmvuthanaGrovePieces.addPieceCheckBounds(
                     whichPit, context.f_226625_(), pitPos, Rotation.values()[random.m_188503_(Rotation.values().length)], builder, context.f_226626_()
                  );
                  if (piece != null) {
                     break;
                  }
               }
            }
         }

         int numStakes = random.m_188503_(10) + 7;

         for (int i = 1; i <= numStakes; i++) {
            for (int jxxx = 1; jxxx <= 10; jxxx++) {
               int distance = random.m_188503_(15) + 8;
               int angle = random.m_188503_(360);
               BlockPos stakePos = new BlockPos(
                  (double)centerPos.m_123341_() + (double)distance * Math.sin(Math.toRadians((double)angle)),
                  0.0,
                  (double)centerPos.m_123343_() + (double)distance * Math.cos(Math.toRadians((double)angle))
               );
               stakePos = posToSurface(generator, stakePos, heightLimitView, context.f_226624_());
               int stakeOceanFloorY = generator.m_214096_(
                  stakePos.m_123341_(), stakePos.m_123343_(), Types.OCEAN_FLOOR_WG, heightLimitView, context.f_226624_()
               );
               if (stakeOceanFloorY >= stakePos.m_123342_()) {
                  ResourceLocation whichSpike = UmvuthanaGrovePieces.SPIKES[random.m_188503_(UmvuthanaGrovePieces.SPIKES.length)];
                  StructurePiece piece = UmvuthanaGrovePieces.addPieceCheckBounds(
                     whichSpike, context.f_226625_(), stakePos, Rotation.values()[random.m_188503_(Rotation.values().length)], builder, context.f_226626_()
                  );
                  if (piece != null) {
                     break;
                  }
               }
            }
         }
      }
   }

   private static boolean startPlatform(
      ChunkGenerator generator, StructureTemplateManager templateManagerIn, StructurePiecesBuilder builder, BlockPos housePos, WorldgenRandom random
   ) {
      Rotation rotation = Rotation.values()[random.m_188503_(Rotation.values().length)];
      StructurePiece newPlatform = UmvuthanaGrovePieces.addPlatform(templateManagerIn, housePos, rotation, builder, random);
      return newPlatform != null;
   }

   private static BlockPos posToSurface(ChunkGenerator generator, BlockPos pos, LevelHeightAccessor heightAccessor, RandomState state) {
      int surfaceY = generator.m_214096_(pos.m_123341_(), pos.m_123343_(), Types.WORLD_SURFACE_WG, heightAccessor, state);
      return new BlockPos(pos.m_123341_(), surfaceY - 1, pos.m_123343_());
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)FeatureHandler.UMVUTHANA_GROVE.get();
   }
}
