package com.github.L_Ender.cataclysm.structures.jisaw.element;

import com.github.L_Ender.cataclysm.init.ModJigsaw;
import com.github.L_Ender.cataclysm.structures.jisaw.condition.StructureCondition;
import com.github.L_Ender.cataclysm.world.structures.modifier.StructureModifier;
import com.github.L_Ender.cataclysm.world.structures.terrainadaptation.EnhancedTerrainAdaptation;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CataclysmJigsawSinglePoolElement extends CataclysmJigsawPoolElement {
   private static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(
      CataclysmJigsawSinglePoolElement::encodeTemplate, ResourceLocation.f_135803_.map(Either::left)
   );
   public static final Codec<CataclysmJigsawSinglePoolElement> CODEC = RecordCodecBuilder.create(
      builder -> builder.group(
               templateCodec(),
               processorsCodec(),
               m_210538_(),
               nameCodec(),
               maxCountCodec(),
               minRequiredDepthCodec(),
               maxPossibleDepthCodec(),
               isPriorityCodec(),
               ignoreBoundsCodec(),
               conditionCodec(),
               enhancedTerrainAdaptationCodec(),
               ResourceLocation.f_135803_.optionalFieldOf("deadend_pool").forGetter(element -> element.deadendPool),
               StructureModifier.CODEC.listOf().optionalFieldOf("modifiers", new ArrayList()).forGetter(element -> element.modifiers)
            )
            .apply(builder, CataclysmJigsawSinglePoolElement::new)
   );
   public final Either<ResourceLocation, StructureTemplate> template;
   public final Holder<StructureProcessorList> processors;
   public final Optional<ResourceLocation> deadendPool;
   public final List<StructureModifier> modifiers;

   public CataclysmJigsawSinglePoolElement(
      Either<ResourceLocation, StructureTemplate> template,
      Holder<StructureProcessorList> processors,
      Projection projection,
      Optional<String> name,
      Optional<Integer> maxCount,
      Optional<Integer> minRequiredDepth,
      Optional<Integer> maxPossibleDepth,
      boolean isPriority,
      boolean ignoreBounds,
      StructureCondition condition,
      Optional<EnhancedTerrainAdaptation> enhancedTerrainAdaptation,
      Optional<ResourceLocation> deadendPool,
      List<StructureModifier> modifiers
   ) {
      super(projection, name, maxCount, minRequiredDepth, maxPossibleDepth, isPriority, ignoreBounds, condition, enhancedTerrainAdaptation);
      this.template = template;
      this.processors = processors;
      this.deadendPool = deadendPool;
      this.modifiers = modifiers;
   }

   public Vec3i m_213577_(StructureTemplateManager structureTemplateManager, Rotation rotation) {
      StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
      return structureTemplate.m_163808_(rotation);
   }

   public List<StructureBlockInfo> m_213638_(StructureTemplateManager structureTemplateManager, BlockPos blockPos, Rotation rotation, RandomSource randomSource) {
      StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
      ObjectArrayList<StructureBlockInfo> jigsawBlocks = structureTemplate.m_230335_(
         blockPos, new StructurePlaceSettings().m_74379_(rotation), Blocks.f_50678_, true
      );
      Util.m_214673_(jigsawBlocks, randomSource);
      return jigsawBlocks;
   }

   public BoundingBox m_214015_(StructureTemplateManager structureTemplateManager, BlockPos blockPos, Rotation rotation) {
      StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
      return structureTemplate.m_74633_(new StructurePlaceSettings().m_74379_(rotation), blockPos);
   }

   public boolean m_213695_(
      StructureTemplateManager structureTemplateManager,
      WorldGenLevel worldGenLevel,
      StructureManager structureManager,
      ChunkGenerator chunkGenerator,
      BlockPos pos,
      BlockPos pivotPos,
      Rotation rotation,
      BoundingBox boundingBox,
      RandomSource randomSource,
      boolean replaceJigsaws
   ) {
      StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
      StructurePlaceSettings structurePlaceSettings = this.getSettings(rotation, boundingBox, replaceJigsaws);
      if (!structureTemplate.m_230328_(worldGenLevel, pos, pivotPos, structurePlaceSettings, randomSource, 18)) {
         return false;
      } else {
         for (StructureBlockInfo $$13 : StructureTemplate.m_74517_(
            worldGenLevel, pos, pivotPos, structurePlaceSettings, this.getDataMarkers(structureTemplateManager, pos, rotation, false)
         )) {
            this.m_227329_(worldGenLevel, $$13, pos, rotation, randomSource, boundingBox);
         }

         return true;
      }
   }

   public Optional<ResourceLocation> getDeadendPool() {
      return this.deadendPool;
   }

   public boolean hasModifiers() {
      return this.modifiers.size() > 0;
   }

   public StructureTemplate getTemplate(StructureTemplateManager structureTemplateManager) {
      return (StructureTemplate)this.template.map(structureTemplateManager::m_230359_, Function.identity());
   }

   public StructurePoolElementType<?> m_207234_() {
      return ModJigsaw.CATACLYSM_ELEMENT;
   }

   public String toString() {
      return String.format(
         "YungJigsawSingle[%s][%s][%s][%s]",
         this.name.orElse("<unnamed>"),
         this.template,
         this.maxCount.isPresent() ? this.maxCount.get() : "no max count",
         this.isPriority
      );
   }

   private StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, boolean replaceJigsaws) {
      StructurePlaceSettings structurePlaceSettings = new StructurePlaceSettings();
      structurePlaceSettings.m_74381_(boundingBox);
      structurePlaceSettings.m_74379_(rotation);
      structurePlaceSettings.m_74402_(true);
      structurePlaceSettings.m_74392_(false);
      structurePlaceSettings.m_74383_(BlockIgnoreProcessor.f_74046_);
      structurePlaceSettings.m_74405_(true);
      if (!replaceJigsaws) {
         structurePlaceSettings.m_74383_(JigsawReplacementProcessor.f_74122_);
      }

      ((StructureProcessorList)this.processors.m_203334_()).m_74425_().forEach(structurePlaceSettings::m_74383_);
      this.m_210539_().m_210609_().forEach(structurePlaceSettings::m_74383_);
      return structurePlaceSettings;
   }

   private List<StructureBlockInfo> getDataMarkers(
      StructureTemplateManager structureTemplateManager, BlockPos blockPos, Rotation rotation, boolean isPositionLocal
   ) {
      StructureTemplate structureTemplate = this.getTemplate(structureTemplateManager);
      List<StructureBlockInfo> structureBlocks = structureTemplate.m_230335_(
         blockPos, new StructurePlaceSettings().m_74379_(rotation), Blocks.f_50677_, isPositionLocal
      );
      List<StructureBlockInfo> dataBlocks = Lists.newArrayList();

      for (StructureBlockInfo block : structureBlocks) {
         StructureMode structureMode = StructureMode.valueOf(block.f_74677_.m_128461_("mode"));
         if (structureMode == StructureMode.DATA) {
            dataBlocks.add(block);
         }
      }

      return dataBlocks;
   }

   public static <E extends CataclysmJigsawSinglePoolElement> RecordCodecBuilder<E, Holder<StructureProcessorList>> processorsCodec() {
      return StructureProcessorType.f_74468_.fieldOf("processors").forGetter(element -> element.processors);
   }

   public static <E extends CataclysmJigsawSinglePoolElement> RecordCodecBuilder<E, Either<ResourceLocation, StructureTemplate>> templateCodec() {
      return TEMPLATE_CODEC.fieldOf("location").forGetter($$0 -> $$0.template);
   }

   private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> either, DynamicOps<T> ops, T template) {
      Optional<ResourceLocation> optional = either.left();
      return !optional.isPresent()
         ? DataResult.error("Can not serialize a runtime pool element")
         : ResourceLocation.f_135803_.encode(optional.get(), ops, template);
   }
}
