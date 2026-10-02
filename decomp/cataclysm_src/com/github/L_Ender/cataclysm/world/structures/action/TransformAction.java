package com.github.L_Ender.cataclysm.world.structures.action;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.structures.jisaw.PieceEntry;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawSinglePoolElement;
import com.github.L_Ender.cataclysm.util.BoxOctree;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;

public class TransformAction extends StructureAction {
   private static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(
      TransformAction::encodeTemplate, ResourceLocation.f_135803_.map(Either::left)
   );
   public static final Codec<TransformAction> CODEC = RecordCodecBuilder.create(
      builder -> builder.group(
               TEMPLATE_CODEC.listOf().fieldOf("output").forGetter(action -> action.output),
               Codec.INT.optionalFieldOf("x_offset", 0).forGetter(action -> action.xOffset),
               Codec.INT.optionalFieldOf("y_offset", 0).forGetter(action -> action.yOffset),
               Codec.INT.optionalFieldOf("z_offset", 0).forGetter(action -> action.zOffset)
            )
            .apply(builder, TransformAction::new)
   );
   private final List<Either<ResourceLocation, StructureTemplate>> output;
   private final int xOffset;
   private final int yOffset;
   private final int zOffset;

   private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> either, DynamicOps<T> ops, T data) {
      return either.left().isEmpty()
         ? DataResult.error("yungsapi - Cannot serialize a runtime pool element")
         : ResourceLocation.f_135803_.encode((ResourceLocation)either.left().get(), ops, data);
   }

   public TransformAction(List<Either<ResourceLocation, StructureTemplate>> output, int xOffset, int yOffset, int zOffset) {
      this.output = output;
      this.xOffset = xOffset;
      this.yOffset = yOffset;
      this.zOffset = zOffset;
   }

   @Override
   public StructureActionType<?> type() {
      return StructureActionType.TRANSFORM;
   }

   @Override
   public void apply(StructureContext ctx, PieceEntry targetPieceEntry) {
      StructureTemplateManager templateManager = ctx.structureTemplateManager();
      if (templateManager == null) {
         Cataclysm.LOGGER.error("Missing required field 'structureTemplateManager' for transform action!");
      } else {
         CataclysmJigsawSinglePoolElement old = (CataclysmJigsawSinglePoolElement)targetPieceEntry.getPiece().m_209918_();
         WorldgenRandom rand = new WorldgenRandom(new LegacyRandomSource(0L));
         rand.m_190064_(
            (long)targetPieceEntry.getPiece().m_72646_().m_123341_(),
            targetPieceEntry.getPiece().m_72646_().m_123342_(),
            targetPieceEntry.getPiece().m_72646_().m_123341_()
         );
         Either<ResourceLocation, StructureTemplate> newTemplate = this.output.get(rand.m_188503_(this.output.size()));
         StructurePoolElement newElement = new CataclysmJigsawSinglePoolElement(
            newTemplate,
            old.processors,
            old.m_210539_(),
            old.name,
            old.maxCount,
            old.minRequiredDepth,
            old.maxPossibleDepth,
            old.isPriority,
            old.ignoreBounds,
            old.condition,
            old.enhancedTerrainAdaptation,
            old.deadendPool,
            old.modifiers
         );
         BlockPos offset = new BlockPos(this.xOffset, this.yOffset, this.zOffset);
         offset = offset.m_7954_(targetPieceEntry.getPiece().m_6830_());
         BlockPos newPos = targetPieceEntry.getPiece().m_72646_().m_121955_(offset);
         BoundingBox newBoundingBox = newElement.m_214015_(templateManager, newPos, targetPieceEntry.getPiece().m_6830_());
         AABB newAabb = AABB.m_82321_(newBoundingBox);
         ((BoxOctree)targetPieceEntry.getBoxOctree().getValue()).removeBox(targetPieceEntry.getPieceAabb());
         ((BoxOctree)targetPieceEntry.getBoxOctree().getValue()).addBox(newAabb);
         PoolElementStructurePiece newPiece = new PoolElementStructurePiece(
            templateManager, newElement, newPos, targetPieceEntry.getPiece().m_72647_(), targetPieceEntry.getPiece().m_6830_(), newBoundingBox
         );
         targetPieceEntry.setPiece(newPiece);
      }
   }
}
