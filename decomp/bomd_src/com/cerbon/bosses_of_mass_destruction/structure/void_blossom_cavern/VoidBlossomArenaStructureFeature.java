package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.structure.util.CodeStructurePiece;
import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

public class VoidBlossomArenaStructureFeature extends Structure {
   public static final Codec<VoidBlossomArenaStructureFeature> CODEC = m_226607_(VoidBlossomArenaStructureFeature::new);

   protected VoidBlossomArenaStructureFeature(StructureSettings settings) {
      super(settings);
   }

   @NotNull
   public Optional<GenerationStub> m_214086_(@NotNull GenerationContext context) {
      return m_226585_(context, Types.WORLD_SURFACE_WG, collector -> addPieces(collector, context));
   }

   @NotNull
   public StructureType<?> m_213658_() {
      return (StructureType<?>)BMDStructures.VOID_BLOSSOM_STRUCTURE_TYPE.get();
   }

   public static void addPieces(StructurePiecesBuilder collector, GenerationContext context) {
      int x = context.f_226628_().m_45604_();
      int z = context.f_226628_().m_45605_();
      int y = 35 + context.f_226622_().m_142062_();
      collector.m_142679_(
         new CodeStructurePiece(
            (StructurePieceType)BMDStructures.VOID_BLOSSOM_CAVERN_PIECE.get(),
            new BoundingBox(new BlockPos(x, y, z)).m_191961_(32),
            new VoidBlossomCavernPieceGenerator()
         )
      );
   }
}
