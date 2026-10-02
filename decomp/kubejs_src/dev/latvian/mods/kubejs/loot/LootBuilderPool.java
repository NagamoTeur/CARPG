package dev.latvian.mods.kubejs.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

public class LootBuilderPool implements FunctionContainer, ConditionContainer {
   public NumberProvider rolls = ConstantValue.m_165692_(1.0F);
   public NumberProvider bonusRolls = null;
   public final JsonArray conditions = new JsonArray();
   public final JsonArray functions = new JsonArray();
   public final JsonArray entries = new JsonArray();

   public JsonObject toJson() {
      JsonObject json = new JsonObject();
      json.add("rolls", UtilsJS.numberProviderJson(this.rolls));
      if (this.bonusRolls != null) {
         json.add("bonus_rolls", UtilsJS.numberProviderJson(this.bonusRolls));
      }

      if (this.conditions.size() > 0) {
         json.add("conditions", this.conditions);
      }

      if (this.functions.size() > 0) {
         json.add("functions", this.functions);
      }

      if (this.entries.size() > 0) {
         json.add("entries", this.entries);
      } else {
         json.add("entries", new JsonArray());
      }

      return json;
   }

   public void setUniformRolls(float min, float max) {
      this.rolls = UniformGenerator.m_165780_(min, max);
   }

   public void setBinomialRolls(int n, float p) {
      this.rolls = BinomialDistributionGenerator.m_165659_(n, p);
   }

   public LootBuilderPool addFunction(JsonObject o) {
      this.functions.add(o);
      return this;
   }

   public LootBuilderPool addCondition(JsonObject o) {
      this.conditions.add(o);
      return this;
   }

   public LootTableEntry addEntry(JsonObject json) {
      this.entries.add(json);
      return new LootTableEntry(json);
   }

   public LootTableEntry addEmpty(int weight) {
      JsonObject json = new JsonObject();
      json.addProperty("type", "minecraft:empty");
      return this.addEntry(json).weight(weight);
   }

   public LootTableEntry addLootTable(ResourceLocation table) {
      JsonObject json = new JsonObject();
      json.addProperty("type", "minecraft:loot_table");
      json.addProperty("name", table.toString());
      return this.addEntry(json);
   }

   public LootTableEntry addItem(ItemStack item, int weight, @Nullable NumberProvider count) {
      ResourceLocation id = KubeJSRegistries.items().getId(item.m_41720_());
      if (id != null && !item.m_41619_()) {
         JsonObject json = new JsonObject();
         json.addProperty("type", "minecraft:item");
         json.addProperty("name", id.toString());
         LootTableEntry entry = this.addEntry(json);
         if (weight >= 0) {
            entry.weight(weight);
         }

         if (count == null && item.m_41613_() > 1) {
            count = ConstantValue.m_165692_((float)item.m_41613_());
         }

         if (count != null) {
            entry.count(count);
         }

         if (item.m_41783_() != null) {
            entry.nbt(item.m_41783_());
         }

         return entry;
      } else {
         return new LootTableEntry(new JsonObject());
      }
   }

   public LootTableEntry addItem(ItemStack item, int weight) {
      return this.addItem(item, weight, null);
   }

   public LootTableEntry addItem(ItemStack item) {
      return this.addItem(item, -1, null);
   }

   public LootTableEntry addTag(String tag, boolean expand) {
      JsonObject json = new JsonObject();
      json.addProperty("type", "minecraft:tag");
      json.addProperty("name", tag);
      json.addProperty("expand", expand);
      return this.addEntry(json);
   }
}
