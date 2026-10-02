package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.ConfiguredFeatureHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class WroughtnautChamberStructure extends MowzieStructure {
   public static final Codec<WroughtnautChamberStructure> CODEC = m_226607_(WroughtnautChamberStructure::new);

   public WroughtnautChamberStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig, ConfiguredFeatureHandler.FERROUS_WROUGHTNAUT_BIOMES, false, false, true);
   }

   @Override
   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
      int x = context.f_226628_().m_151390_();
      int z = context.f_226628_().m_151393_();
      int y = context.f_226622_().m_223235_(x, z, Types.OCEAN_FLOOR_WG, context.f_226629_(), context.f_226624_());
      Pair<BlockPos, Rotation> tryResult = tryWroughtChamber(context.f_226622_(), context.f_226629_(), x, y, z, context.f_226624_());
      if (tryResult != null) {
         BlockPos pos = (BlockPos)tryResult.getLeft();
         Rotation rotation = (Rotation)tryResult.getRight();
         BlockPos rotationOffset = new BlockPos(0, 0, -9).m_7954_(rotation);
         pos = pos.m_121955_(rotationOffset);
         WroughtnautChamberPieces.start(context.f_226625_(), pos, rotation, builder);
      }
   }

   @Nullable
   public static Pair<BlockPos, Rotation> tryWroughtChamber(
      ChunkGenerator generator, LevelHeightAccessor heightAccessor, int x, int surfaceY, int z, RandomState state
   ) {
      int xzCheckDistance = 8;
      int heightMax = ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig.heightMax.get()).intValue();
      int heightMin = ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig.heightMin.get()).intValue();
      if (heightMax == -65 || heightMax > surfaceY) {
         heightMax = surfaceY;
      }

      if (heightMin == -65) {
         heightMin = -64;
      }

      for (int dx = -xzCheckDistance; dx < xzCheckDistance; dx += 2) {
         for (int dz = -xzCheckDistance; dz < xzCheckDistance; dz += 2) {
            BlockPos airPos = null;
            NoiseColumn column = generator.m_214184_(x + dx, z + dz, heightAccessor, state);

            for (int y = heightMax; y > heightMin; y--) {
               if (!column.m_183556_(y).m_60767_().m_76333_()) {
                  airPos = new BlockPos(x + dx, y, z + dz);
                  break;
               }
            }

            if (airPos != null) {
               BlockPos groundPos = null;

               for (int yx = airPos.m_123342_(); yx > heightMin; yx--) {
                  if (column.m_183556_(yx).m_60767_().m_76333_()) {
                     groundPos = airPos.m_175288_(yx);
                     break;
                  }
               }

               if (groundPos != null) {
                  for (Direction dir : Plane.HORIZONTAL) {
                     MutableBlockPos checkWallPos = groundPos.m_7494_().m_122032_();

                     for (int d = 1; d <= xzCheckDistance; d++) {
                        checkWallPos.m_122173_(dir);
                        NoiseColumn wallCheckColumn = generator.m_214184_(checkWallPos.m_123341_(), checkWallPos.m_123343_(), heightAccessor, state);
                        int wallBaseY = checkWallPos.m_123342_() - 1;
                        int wallHeightCount = 1;

                        while (true) {
                           BlockState wallBlock = wallCheckColumn.m_183556_(checkWallPos.m_123342_());
                           if (!wallBlock.m_60767_().m_76333_()) {
                              break;
                           }

                           if (wallHeightCount == 4) {
                              Rotation rotation = switch (dir) {
                                 case NORTH -> Rotation.COUNTERCLOCKWISE_90;
                                 case EAST -> Rotation.NONE;
                                 case WEST -> Rotation.CLOCKWISE_180;
                                 default -> Rotation.CLOCKWISE_90;
                              };
                              return Pair.of(new BlockPos(checkWallPos.m_123341_(), wallBaseY, checkWallPos.m_123343_()), rotation);
                           }

                           checkWallPos.m_122173_(Direction.UP);
                           wallHeightCount++;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   @Override
   public Decoration m_226619_() {
      return Decoration.UNDERGROUND_STRUCTURES;
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)FeatureHandler.WROUGHTNAUT_CHAMBER.get();
   }
}
