package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.block.ArchfruitPod;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction.NameSource;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction.MergeStrategy;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootTableProvider extends BaseLootTableProvider {
   public LootTableProvider(DataGenerator dataGeneratorIn) {
      super(dataGeneratorIn);
   }

   @Override
   protected void addTables() {
      this.blockTables.put(BlockRegistry.SOURCE_JAR, this.createManaManchineTable("source_jar", BlockRegistry.SOURCE_JAR));
      Builder potionJarBuilder = LootPool.m_79043_()
         .name("potion_jar")
         .m_165133_(ConstantValue.m_165692_(1.0F))
         .m_79076_(
            LootItem.m_79579_(BlockRegistry.POTION_JAR)
               .m_79078_(CopyNameFunction.m_80187_(NameSource.BLOCK_ENTITY))
               .m_79078_(
                  CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_)
                     .m_80282_("potionData", "BlockEntityTag.potionData", MergeStrategy.REPLACE)
                     .m_80282_("currentFill", "BlockEntityTag.currentFill", MergeStrategy.REPLACE)
                     .m_80282_("locked", "BlockEntityTag.locked", MergeStrategy.REPLACE)
                     .m_80282_("potionNames", "potionNames", MergeStrategy.REPLACE)
                     .m_80282_("currentFill", "fill", MergeStrategy.REPLACE)
               )
               .m_79078_(
                  SetContainerContents.m_193036_(BlockRegistry.POTION_JAR_TYPE).m_80930_(DynamicLoot.m_79483_(new ResourceLocation("minecraft", "contents")))
               )
         );
      this.blockTables.put(BlockRegistry.POTION_JAR, LootTable.m_79147_().m_79161_(potionJarBuilder));
      this.putEntityTable(
         (EntityType<?>)ModEntities.WILDEN_STALKER.get(),
         LootTable.m_79147_()
            .m_79161_(
               LootPool.m_79043_()
                  .m_165133_(ConstantValue.m_165692_(1.0F))
                  .m_79076_(
                     LootItem.m_79579_((ItemLike)ItemsRegistry.WILDEN_WING.get())
                        .m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F)))
                        .m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 1.0F)))
                  )
            )
      );
      this.putEntityTable(
         (EntityType<?>)ModEntities.WILDEN_GUARDIAN.get(),
         LootTable.m_79147_()
            .m_79161_(
               LootPool.m_79043_()
                  .m_165133_(ConstantValue.m_165692_(1.0F))
                  .m_79076_(
                     LootItem.m_79579_((ItemLike)ItemsRegistry.WILDEN_SPIKE.get())
                        .m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F)))
                        .m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 1.0F)))
                  )
            )
      );
      this.putEntityTable(
         (EntityType<?>)ModEntities.WILDEN_HUNTER.get(),
         LootTable.m_79147_()
            .m_79161_(
               LootPool.m_79043_()
                  .m_165133_(ConstantValue.m_165692_(1.0F))
                  .m_79076_(
                     LootItem.m_79579_((ItemLike)ItemsRegistry.WILDEN_HORN.get())
                        .m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0F)))
                        .m_79078_(LootingEnchantFunction.m_165229_(UniformGenerator.m_165780_(0.0F, 1.0F)))
                  )
            )
      );
      this.blockTables
         .put(BlockRegistry.BASTION_POD, LootTable.m_79147_().m_79161_(this.POD_BUILDER(BlockRegistry.BASTION_POD.m_5456_(), BlockRegistry.BASTION_POD)));
      this.blockTables
         .put(
            BlockRegistry.MENDOSTEEN_POD, LootTable.m_79147_().m_79161_(this.POD_BUILDER(BlockRegistry.MENDOSTEEN_POD.m_5456_(), BlockRegistry.MENDOSTEEN_POD))
         );
      this.blockTables
         .put(BlockRegistry.FROSTAYA_POD, LootTable.m_79147_().m_79161_(this.POD_BUILDER(BlockRegistry.FROSTAYA_POD.m_5456_(), BlockRegistry.FROSTAYA_POD)));
      this.blockTables
         .put(
            BlockRegistry.BOMBEGRANTE_POD,
            LootTable.m_79147_().m_79161_(this.POD_BUILDER(BlockRegistry.BOMBEGRANTE_POD.m_5456_(), BlockRegistry.BOMBEGRANTE_POD))
         );
      Builder mobJarBuilder = LootPool.m_79043_()
         .name("mob_jar")
         .m_165133_(ConstantValue.m_165692_(1.0F))
         .m_79076_(
            LootItem.m_79579_(BlockRegistry.MOB_JAR)
               .m_79078_(CopyNameFunction.m_80187_(NameSource.BLOCK_ENTITY))
               .m_79078_(
                  CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_)
                     .m_80282_("entityTag", "BlockEntityTag.entityTag", MergeStrategy.REPLACE)
                     .m_80282_("entityId", "entityId", MergeStrategy.REPLACE)
               )
               .m_79078_(
                  SetContainerContents.m_193036_(BlockRegistry.MOB_JAR_TILE).m_80930_(DynamicLoot.m_79483_(new ResourceLocation("minecraft", "contents")))
               )
         );
      this.blockTables.put(BlockRegistry.MOB_JAR, LootTable.m_79147_().m_79161_(mobJarBuilder));
      Builder repository = LootPool.m_79043_()
         .name("repository")
         .m_165133_(ConstantValue.m_165692_(1.0F))
         .m_79076_(LootItem.m_79579_(BlockRegistry.REPOSITORY).m_79078_(CopyNameFunction.m_80187_(NameSource.BLOCK_ENTITY)));
      this.blockTables.put(BlockRegistry.REPOSITORY, LootTable.m_79147_().m_79161_(repository));
   }

   public Builder POD_BUILDER(Item item, Block block) {
      return LootPool.m_79043_()
         .m_165133_(ConstantValue.m_165692_(1.0F))
         .m_79076_(
            LootItem.m_79579_(item)
               .m_79078_(
                  SetItemCountFunction.m_165412_(ConstantValue.m_165692_(3.0F))
                     .m_79080_(
                        LootItemBlockStatePropertyCondition.m_81769_(block)
                           .m_81784_(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.m_67693_().m_67694_(ArchfruitPod.f_51736_, 2))
                     )
               )
         );
   }

   public void putEntityTable(EntityType<?> e, net.minecraft.world.level.storage.loot.LootTable.Builder table) {
      this.entityTables.put(e.m_20677_(), table);
   }
}
