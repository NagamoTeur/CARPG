package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.world.feature.FeatureHandler;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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

public class FrostmawPieces {
   private static final ResourceLocation FROSTMAW = new ResourceLocation("mowziesmobs", "frostmaw_spawn");
   private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(FROSTMAW, new BlockPos(0, 1, 0));

   public static void addPieces(StructureTemplateManager manager, BlockPos pos, Rotation rot, StructurePieceAccessor pieces, RandomSource rand) {
      BlockPos rotationOffset = new BlockPos(0, 0, 0).m_7954_(rot);
      BlockPos blockPos = rotationOffset.m_121955_(pos);
      pieces.m_142679_(new FrostmawPieces.FrostmawPiece(manager, FROSTMAW, blockPos, rot));
   }

   public static class FrostmawPiece extends TemplateStructurePiece {
      private static StructurePlaceSettings makeSettings(Rotation rotation, ResourceLocation resourceLocation) {
         return new StructurePlaceSettings().m_74379_(rotation).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74046_);
      }

      private static BlockPos makePosition(ResourceLocation resourceLocation, BlockPos pos) {
         return pos.m_121955_((Vec3i)FrostmawPieces.OFFSET.get(resourceLocation));
      }

      public FrostmawPiece(StructureTemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
         super(
            FeatureHandler.FROSTMAW_PIECE,
            0,
            templateManagerIn,
            resourceLocationIn,
            resourceLocationIn.toString(),
            makeSettings(rotationIn, resourceLocationIn),
            makePosition(resourceLocationIn, pos)
         );
      }

      public FrostmawPiece(StructurePieceSerializationContext context, CompoundTag tag) {
         super(
            FeatureHandler.FROSTMAW_PIECE, tag, context.f_226956_(), resourceLocation -> makeSettings(Rotation.valueOf(tag.m_128461_("Rot")), resourceLocation)
         );
      }

      protected void m_183620_(StructurePieceSerializationContext context, CompoundTag tagCompound) {
         super.m_183620_(context, tagCompound);
         tagCompound.m_128359_("Rot", this.f_73657_.m_74404_().name());
      }

      protected void m_213704_(String function, BlockPos pos, ServerLevelAccessor worldIn, RandomSource rand, BoundingBox sbb) {
      }
   }
}
