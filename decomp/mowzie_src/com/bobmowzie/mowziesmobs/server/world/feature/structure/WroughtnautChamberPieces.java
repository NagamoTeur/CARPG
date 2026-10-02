package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class WroughtnautChamberPieces {
   private static final ResourceLocation PART = new ResourceLocation("mowziesmobs", "wroughtnaut_chamber");

   public static void start(StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePieceAccessor pieces) {
      pieces.m_142679_(new WroughtnautChamberPieces.Piece(manager, PART, pos, rot));
   }

   public static class Piece extends TemplateStructurePiece {
      public Piece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
         super(
            FeatureHandler.WROUGHTNAUT_CHAMBER_PIECE,
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotationIn, resourceLocationIn),
            pos
         );
      }

      public Piece(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super(
            FeatureHandler.WROUGHTNAUT_CHAMBER_PIECE,
            tagCompound,
            context.f_226956_(),
            resourceLocation -> makeSettings(
                  Rotation.valueOf(tagCompound.m_128441_("Rot") ? tagCompound.m_128461_("Rot") : Rotation.NONE.name()), resourceLocation
               )
         );
      }

      private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation resourceLocation) {
         return new StructurePlaceSettings().m_74379_(rotation).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74046_);
      }

      protected void m_183620_(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super.m_183620_(context, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
      }
   }
}
