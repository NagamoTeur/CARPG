package daripher.skilltree.item.gem.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.compat.apotheosis.ApotheosisCompatibility;
import daripher.skilltree.data.reloader.GemTypesReloader;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.item.gem.GemType;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class GemLootPoolEntry extends LootPoolSingletonContainer {
   public static final GemLootPoolEntry.Serializer SERIALIZER = new GemLootPoolEntry.Serializer();
   public static final LootPoolEntryType TYPE = new LootPoolEntryType(SERIALIZER);
   private final ResourceLocation gemTypeId;

   public GemLootPoolEntry(ResourceLocation gemTypeId, int weight, int quality, LootItemCondition[] conditions, LootItemFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.gemTypeId = gemTypeId;
   }

   protected void m_6948_(@NotNull Consumer<ItemStack> consumer, @NotNull LootContext context) {
      if (SkillTreeMod.apotheosisEnabled()) {
         ApotheosisCompatibility.INSTANCE.createGemStack(consumer, context, this.gemTypeId);
      } else {
         GemType gemType = GemTypesReloader.getGemTypeById(this.gemTypeId);
         consumer.accept(GemItem.getDefaultGemStack(gemType));
      }
   }

   @NotNull
   public LootPoolEntryType m_6751_() {
      return TYPE;
   }

   public static class Builder extends net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder<GemLootPoolEntry.Builder> {
      private final ResourceLocation gemTypeId;

      public Builder(ResourceLocation gemTypeId) {
         this.gemTypeId = gemTypeId;
      }

      @NotNull
      protected GemLootPoolEntry.Builder getThis() {
         return this;
      }

      @NotNull
      public LootPoolEntryContainer m_7512_() {
         return new GemLootPoolEntry(
            this.gemTypeId, GemLootHandler.getGemLootWeight(this.gemTypeId), GemLootHandler.getGemLootQuality(this.gemTypeId), this.m_79651_(), this.m_79706_()
         );
      }
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Serializer<GemLootPoolEntry> {
      @NotNull
      protected GemLootPoolEntry deserialize(
         @NotNull JsonObject json,
         @NotNull JsonDeserializationContext ctx,
         int weight,
         int quality,
         @NotNull LootItemCondition[] conditions,
         @NotNull LootItemFunction[] functions
      ) {
         ResourceLocation gemTypeId = new ResourceLocation(json.get("gem").getAsString());
         return new GemLootPoolEntry(gemTypeId, weight, quality, conditions, functions);
      }

      public void serializeCustom(JsonObject json, GemLootPoolEntry entry, @NotNull JsonSerializationContext ctx) {
         json.addProperty("gem", entry.gemTypeId.toString());
         super.m_7219_(json, entry, ctx);
      }
   }
}
