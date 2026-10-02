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
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class WildenGuardianDen extends WildenDen {
   public static final Codec<WildenGuardianDen> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
                  m_226567_(instance),
                  StructureTemplatePool.f_210555_.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                  ResourceLocation.f_135803_.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                  Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                  HeightProvider.f_161970_.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                  Types.f_64274_.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                  Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
               )
               .apply(instance, WildenGuardianDen::new)
      )
      .codec();

   public WildenGuardianDen(
      StructureSettings config,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int size,
      HeightProvider startHeight,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter
   ) {
      super(config, startPool, startJigsawName, size, startHeight, projectStartToHeightmap, maxDistanceFromCenter);
   }

   @Override
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
         .forEach(pos -> {
            if (level.m_8055_(pos).m_60713_(Blocks.f_220864_)) {
               level.m_7731_(pos, Blocks.f_49990_.m_49966_(), 2);
            }
         });
   }

   @Override
   public StructureType<?> m_213658_() {
      return (StructureType<?>)StructureRegistry.WILDEN_GUARDIAN_DEN.get();
   }
}
