package com.min01.archaeology.structure.processor;

import com.min01.archaeology.block.BrushableBlock;
import com.min01.archaeology.init.ArchaeologyStructureProcessorType;
import com.min01.archaeology.misc.StructureBlockInfoAccess;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CappedProcessor extends StructureProcessor {
   public static final Codec<CappedProcessor> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               StructureProcessorType.f_74465_.fieldOf("delegate").forGetter(processor -> processor.delegate),
               IntProvider.f_146533_.fieldOf("limit").forGetter(processor -> processor.limit),
               ResourceLocation.f_135803_.fieldOf("loot_table").forGetter(processor -> processor.lootTable)
            )
            .apply(instance, CappedProcessor::new)
   );
   private final StructureProcessor delegate;
   private final IntProvider limit;
   @Nullable
   private final ResourceLocation lootTable;
   private StructurePlaceSettings previousSettings;
   private int convertedCount;

   public CappedProcessor(StructureProcessor delegate, IntProvider limit, @Nullable ResourceLocation lootTable) {
      this.delegate = delegate;
      this.limit = limit;
      this.lootTable = lootTable;
   }

   @NotNull
   protected StructureProcessorType<?> m_6953_() {
      return ArchaeologyStructureProcessorType.CAPPED;
   }

   @Nullable
   public StructureBlockInfo m_7382_(
      LevelReader level,
      BlockPos position,
      BlockPos relativePosition,
      StructureBlockInfo structureBlockInfo,
      StructureBlockInfo relativeStructureBlockInfo,
      StructurePlaceSettings settings
   ) {
      if (this.previousSettings != settings) {
         this.previousSettings = settings;
         this.convertedCount = 0;
      }

      if (this.convertedCount == this.limit.m_142737_()) {
         return relativeStructureBlockInfo;
      } else {
         StructureBlockInfo converted = this.delegate.m_7382_(level, position, relativePosition, structureBlockInfo, relativeStructureBlockInfo, settings);
         if (converted != null
            && converted.f_74676_.m_60734_() instanceof BrushableBlock
            && settings.m_230326_(relativePosition).m_188500_() > 0.3
            && this.lootTable != null
            && relativeStructureBlockInfo instanceof StructureBlockInfoAccess access) {
            access.archeology$addLootTable("LootTable", this.lootTable.toString());
            if (!relativeStructureBlockInfo.f_74676_.m_60713_(converted.f_74676_.m_60734_())) {
               this.convertedCount++;
            }

            return converted;
         } else {
            return relativeStructureBlockInfo;
         }
      }
   }
}
