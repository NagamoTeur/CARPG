package com.bobmowzie.mowziesmobs.server.world.feature.structure.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class BaseDecoProcessor extends StructureProcessor {
   public static final BaseDecoProcessor INSTANCE = new BaseDecoProcessor();
   public static final Codec<BaseDecoProcessor> CODEC = Codec.unit(() -> INSTANCE);
   private static final BlockState trapDoorBottom = Blocks.f_50221_.m_49966_();
   private static final BlockState trapDoorTop = (BlockState)Blocks.f_50221_.m_49966_().m_61124_(TrapDoorBlock.f_57515_, Half.TOP);
   private static final BlockState slabBottom = Blocks.f_50403_.m_49966_();
   private static final BlockState slabTop = (BlockState)Blocks.f_50403_.m_49966_().m_61124_(SlabBlock.f_56353_, SlabType.TOP);
   private static final BlockState woodStairs = (BlockState)Blocks.f_50373_.m_49966_().m_61124_(StairBlock.f_56842_, Half.TOP);
   private static final BlockState wall = Blocks.f_152592_.m_49966_();
   private static final BlockState button = Blocks.f_50309_.m_49966_();
   private static final BlockState stoneStairs = Blocks.f_152552_.m_49966_();
   private static final BlockState[][] DECO = new BlockState[][]{
      {trapDoorBottom, slabBottom, trapDoorBottom, slabBottom, trapDoorBottom, slabBottom, trapDoorBottom},
      {woodStairs, trapDoorTop, slabTop, trapDoorTop, slabTop, trapDoorTop, woodStairs},
      {wall, button, null, null, null, button, wall},
      {wall, stoneStairs, stoneStairs, stoneStairs, stoneStairs, stoneStairs, wall}
   };

   protected StructureProcessorType<?> m_6953_() {
      return ProcessorHandler.BASE_DECO_PROCESSOR;
   }

   public StructureBlockInfo process(
      LevelReader levelReader,
      BlockPos jigsawPiecePos,
      BlockPos jigsawPieceBottomCenterPos,
      StructureBlockInfo blockInfoLocal,
      StructureBlockInfo blockInfoGlobal,
      StructurePlaceSettings structurePlacementData,
      StructureTemplate template
   ) {
      if (blockInfoGlobal.f_74676_.m_60713_(Blocks.f_50442_)) {
         if (levelReader instanceof WorldGenRegion worldGenRegion && !worldGenRegion.m_143488_().equals(new ChunkPos(blockInfoGlobal.f_74675_))) {
            return blockInfoGlobal;
         }

         Direction facing = ((Direction)blockInfoGlobal.f_74676_.m_61143_(StairBlock.f_56841_)).m_122424_();
         facing = structurePlacementData.m_74404_().m_55954_(facing);
         RandomSource random = structurePlacementData.m_230326_(blockInfoGlobal.f_74675_);
         blockInfoGlobal = new StructureBlockInfo(blockInfoGlobal.f_74675_, Blocks.f_50301_.m_49966_(), blockInfoGlobal.f_74677_);

         for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 4; y++) {
               BlockState state = DECO[y][x];
               if (state != null) {
                  if (state.m_60734_() == Blocks.f_152552_) {
                     state = this.chooseRandomState(random);
                  }

                  BlockPos pos = blockInfoGlobal.f_74675_.m_121945_(facing);
                  pos = pos.m_5484_(facing.m_122427_(), x - 3);
                  pos = pos.m_5484_(Direction.UP, 1 - y);
                  if (!levelReader.m_8055_(pos).m_60767_().m_76333_()
                     && levelReader.m_8055_(pos.m_7495_()).m_60734_() != Blocks.f_50745_
                     && levelReader.m_8055_(pos.m_7495_()).m_60734_() != Blocks.f_50006_
                     && levelReader.m_8055_(pos.m_7495_()).m_60734_() != Blocks.f_50742_) {
                     if (state.m_61138_(HorizontalDirectionalBlock.f_54117_)) {
                        if (state.m_60734_() instanceof StairBlock) {
                           state = (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, facing.m_122424_());
                        } else {
                           state = (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, facing);
                        }
                     }

                     levelReader.m_46865_(pos).m_6978_(pos, state, false);
                  }
               }
            }
         }
      }

      return blockInfoGlobal;
   }

   public BlockState chooseRandomState(RandomSource random) {
      float v = random.m_188501_();
      return (double)v > 0.7 ? Blocks.f_152556_.m_49966_() : Blocks.f_152552_.m_49966_();
   }
}
