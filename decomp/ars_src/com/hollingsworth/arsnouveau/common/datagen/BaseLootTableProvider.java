package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction.NameSource;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction.MergeStrategy;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseLootTableProvider extends net.minecraft.data.loot.LootTableProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   protected final Map<Block, Builder> blockTables = new HashMap<>();
   protected final Map<ResourceLocation, Builder> entityTables = new HashMap<>();
   private final DataGenerator generator;

   public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
      super(dataGeneratorIn);
      this.generator = dataGeneratorIn;
   }

   protected abstract void addTables();

   protected Builder createManaManchineTable(String name, Block block) {
      net.minecraft.world.level.storage.loot.LootPool.Builder builder = LootPool.m_79043_()
         .name(name)
         .m_165133_(ConstantValue.m_165692_(1.0F))
         .m_79076_(
            LootItem.m_79579_(block)
               .m_79078_(CopyNameFunction.m_80187_(NameSource.BLOCK_ENTITY))
               .m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80282_("source", "BlockEntityTag.source", MergeStrategy.REPLACE))
               .m_79078_(
                  SetContainerContents.m_193036_(BlockRegistry.SOURCE_JAR_TILE).m_80930_(DynamicLoot.m_79483_(new ResourceLocation("minecraft", "contents")))
               )
         );
      return LootTable.m_79147_().m_79161_(builder);
   }

   public void m_213708_(CachedOutput cache) {
      this.addTables();
      Map<ResourceLocation, LootTable> tables = new HashMap<>();

      for (Entry<Block, Builder> entry : this.blockTables.entrySet()) {
         tables.put(entry.getKey().m_60589_(), entry.getValue().m_79165_(LootContextParamSets.f_81421_).m_79167_());
      }

      for (Entry<ResourceLocation, Builder> entry : this.entityTables.entrySet()) {
         tables.put(entry.getKey(), entry.getValue().m_79165_(LootContextParamSets.f_81415_).m_79167_());
      }

      this.writeTables(cache, tables);
   }

   private void writeTables(CachedOutput cache, Map<ResourceLocation, LootTable> tables) {
      Path outputFolder = this.generator.m_123916_();
      tables.forEach((key, lootTable) -> {
         Path path = outputFolder.resolve("data/" + key.m_135827_() + "/loot_tables/" + key.m_135815_() + ".json");

         try {
            DataProvider.m_236072_(cache, LootTables.m_79200_(lootTable), path);
         } catch (IOException var6) {
            LOGGER.error("Couldn't write loot table {}", path, var6);
         }
      });
   }

   public String m_6055_() {
      return "Ars Nouveau LootTables";
   }
}
