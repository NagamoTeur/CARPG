package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.common.mixin.structure.StructureTemplateAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

public abstract class StructureRitual extends AbstractRitual {
   public ResourceLocation structure;
   public BlockPos offset;
   public List<StructureBlockInfo> blocks = new ArrayList<>();
   public List<StructureEntityInfo> entityInfoList = new ArrayList<>();
   public int index;
   public int sourceRequired;
   public boolean hasConsumed;
   public ResourceKey<Biome> biome;

   public StructureRitual(ResourceLocation structure, BlockPos offset, int sourceRequired, ResourceKey<Biome> biome) {
      this.structure = structure;
      this.offset = offset;
      this.sourceRequired = sourceRequired;
      this.hasConsumed = sourceRequired == 0;
      this.biome = biome;
   }

   @Override
   public void onStart() {
      super.onStart();
      if (!this.getWorld().f_46443_) {
         this.setup();
      }
   }

   public void setup() {
      if (!this.getWorld().f_46443_) {
         StructureTemplateManager manager = this.getWorld().m_7654_().m_236738_();
         StructureTemplate structureTemplate = manager.m_230359_(this.structure);
         List<StructureBlockInfo> infoList = ((Palette)structureTemplate.f_74482_.get(0)).m_74652_();
         this.blocks = new ArrayList<>(infoList.stream().filter(b -> !b.f_74676_.m_60795_()).toList());
         this.blocks.sort(new StructureComparator(this.getPos(), this.offset));
         this.entityInfoList = new ArrayList<>(((StructureTemplateAccessor)structureTemplate).getEntityInfoList());
      }
   }

   @Override
   protected void tick() {
      if (!this.getWorld().f_46443_) {
         if (!this.hasConsumed) {
            this.setNeedsSource(true);
         } else {
            for (int placeCount = 0; placeCount < 5; this.index++) {
               if (this.index >= this.blocks.size()) {
                  for (StructureEntityInfo entityInfo : this.entityInfoList) {
                     BlockPos translatedPos = this.getPos()
                        .m_7918_(entityInfo.f_74684_.m_123341_(), entityInfo.f_74684_.m_123342_(), entityInfo.f_74684_.m_123343_())
                        .m_121955_(this.offset);

                     Optional<Entity> entity;
                     try {
                        CompoundTag entityTag = entityInfo.f_74685_;
                        entityTag.m_128473_("UUID");
                        entity = EntityType.m_20642_(entityTag, this.getWorld());
                     } catch (Exception var7) {
                        continue;
                     }

                     if (entity.isPresent()) {
                        entity.get().m_6027_((double)translatedPos.m_123341_(), (double)translatedPos.m_123342_(), (double)translatedPos.m_123343_());
                        this.getWorld().m_7967_(entity.get());
                     }
                  }

                  this.setFinished();
                  return;
               }

               StructureBlockInfo blockInfo = this.blocks.get(this.index);
               BlockPos translatedPos = this.getPos()
                  .m_7918_(blockInfo.f_74675_.m_123341_(), blockInfo.f_74675_.m_123342_(), blockInfo.f_74675_.m_123343_())
                  .m_121955_(this.offset);
               if (this.getWorld().m_8055_(translatedPos).m_60767_().m_76336_()) {
                  this.getWorld().m_7731_(translatedPos, blockInfo.f_74676_, 2);
                  BlockEntity blockentity1 = this.getWorld().m_7702_(translatedPos);
                  if (blockentity1 != null) {
                     if (blockentity1 instanceof RandomizableContainerBlockEntity) {
                        blockInfo.f_74677_.m_128356_("LootTableSeed", this.getWorld().f_46441_.m_188505_());
                     }

                     blockentity1.m_142466_(blockInfo.f_74677_);
                  }

                  this.getWorld().m_5594_(null, translatedPos, blockInfo.f_74676_.m_60827_().m_56777_(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  placeCount++;
                  if (this.biome != null) {
                     RitualUtil.changeBiome(this.getWorld(), translatedPos, this.biome);
                  }
               }
            }
         }
      }
   }

   @Override
   public void setNeedsSource(boolean needMana) {
      super.setNeedsSource(needMana);
      if (!needMana) {
         this.hasConsumed = true;
      }
   }

   @Override
   public void read(CompoundTag tag) {
      super.read(tag);
      this.index = tag.m_128451_("index");
      this.hasConsumed = tag.m_128471_("hasConsumed");
      this.setup();
   }

   @Override
   public void write(CompoundTag tag) {
      super.write(tag);
      tag.m_128405_("index", this.index);
      tag.m_128379_("hasConsumed", this.hasConsumed);
   }

   @Override
   public int getSourceCost() {
      return this.sourceRequired;
   }

   @Override
   public abstract ResourceLocation getRegistryName();
}
