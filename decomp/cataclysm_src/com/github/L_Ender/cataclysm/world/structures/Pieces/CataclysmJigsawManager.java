package com.github.L_Ender.cataclysm.world.structures.Pieces;

import com.github.L_Ender.cataclysm.structures.jisaw.JigsawManager;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class CataclysmJigsawManager {
   public static Optional<GenerationStub> assembleJigsawStructure(
      GenerationContext generationContext,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawNameOptional,
      int maxDepth,
      BlockPos startPos,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      Optional<Integer> maxY,
      Optional<Integer> minY
   ) {
      return JigsawManager.assembleJigsawStructure(
         generationContext,
         startPool,
         startJigsawNameOptional,
         maxDepth,
         startPos,
         useExpansionHack,
         projectStartToHeightmap,
         maxDistanceFromCenter,
         maxY,
         minY
      );
   }
}
