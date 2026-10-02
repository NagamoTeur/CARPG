package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.ConfiguredFeatureHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class FrostmawStructure extends MowzieStructure {
   public static final Codec<FrostmawStructure> CODEC = m_226607_(FrostmawStructure::new);

   public FrostmawStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.FROSTMAW.generationConfig, ConfiguredFeatureHandler.FROSTMAW_BIOMES);
   }

   @Override
   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
      int x = context.f_226628_().m_45604_();
      int z = context.f_226628_().m_45605_();
      int y = context.f_226622_().m_223221_(x, z, Types.WORLD_SURFACE_WG, context.f_226629_(), context.f_226624_());
      BlockPos blockpos = new BlockPos(x, y, z);
      Rotation rotation = Rotation.m_221990_(context.f_226626_());
      FrostmawPieces.addPieces(context.f_226625_(), blockpos, rotation, builder, context.f_226626_());
   }

   public StructureType<?> m_213658_() {
      return (StructureType<?>)FeatureHandler.FROSTMAW.get();
   }
}
