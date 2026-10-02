package com.bobmowzie.mowziesmobs.server.world.feature;

import com.bobmowzie.mowziesmobs.server.world.feature.structure.FrostmawPieces;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.FrostmawStructure;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.UmvuthanaGrovePieces;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.UmvuthanaGroveStructure;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.WroughtnautChamberPieces;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.WroughtnautChamberStructure;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FeatureHandler {
   public static final DeferredRegister<StructureType<?>> REG = DeferredRegister.create(Registry.f_235739_, "mowziesmobs");
   public static RegistryObject<StructureType<WroughtnautChamberStructure>> WROUGHTNAUT_CHAMBER = registerStructure(
      "wrought_chamber", () -> () -> WroughtnautChamberStructure.CODEC
   );
   public static StructurePieceType WROUGHTNAUT_CHAMBER_PIECE;
   public static RegistryObject<StructureType<UmvuthanaGroveStructure>> UMVUTHANA_GROVE = registerStructure(
      "umvuthana_grove", () -> () -> UmvuthanaGroveStructure.CODEC
   );
   public static StructurePieceType UMVUTHANA_GROVE_PIECE;
   public static StructurePieceType UMVUTHANA_FIREPIT;
   public static RegistryObject<StructureType<FrostmawStructure>> FROSTMAW = registerStructure("frostmaw_spawn", () -> () -> FrostmawStructure.CODEC);
   public static StructurePieceType FROSTMAW_PIECE;

   private static <T extends Structure> RegistryObject<StructureType<T>> registerStructure(String name, Supplier<StructureType<T>> structure) {
      return REG.register(name, structure);
   }

   public static void registerStructurePieces() {
      WROUGHTNAUT_CHAMBER_PIECE = (StructurePieceType)Registry.m_122965_(
         Registry.f_122843_, new ResourceLocation("mowziesmobs", "wrought_chamber_template"), WroughtnautChamberPieces.Piece::new
      );
      UMVUTHANA_GROVE_PIECE = (StructurePieceType)Registry.m_122965_(
         Registry.f_122843_, new ResourceLocation("mowziesmobs", "umvuthana_grove_template"), UmvuthanaGrovePieces.Piece::new
      );
      UMVUTHANA_FIREPIT = (StructurePieceType)Registry.m_122965_(
         Registry.f_122843_, new ResourceLocation("mowziesmobs", "umvuthana_firepit"), UmvuthanaGrovePieces.FirepitPiece::new
      );
      FROSTMAW_PIECE = (StructurePieceType)Registry.m_122965_(
         Registry.f_122843_, new ResourceLocation("mowziesmobs", "frostmaw_template"), FrostmawPieces.FrostmawPiece::new
      );
   }
}
