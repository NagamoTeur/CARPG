package io.redspace.ironsspellbooks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import io.redspace.ironsspellbooks.item.FurledMapItem;
import io.redspace.ironsspellbooks.registries.LootRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FurledMapLootFunction extends LootItemConditionalFunction {
   final String destination;
   final String translation;

   protected FurledMapLootFunction(LootItemCondition[] lootConditions, String destination, String translation) {
      super(lootConditions);
      this.destination = destination;
      this.translation = translation;
   }

   public static Builder<?> create(String destination, String translation) {
      return m_80683_(functions -> new FurledMapLootFunction(functions, destination, translation));
   }

   protected ItemStack m_7372_(ItemStack itemStack, LootContext lootContext) {
      return itemStack.m_41720_() instanceof FurledMapItem
         ? FurledMapItem.of(new ResourceLocation(this.destination), Component.m_237115_(this.translation))
         : itemStack;
   }

   public LootItemFunctionType m_7162_() {
      return (LootItemFunctionType)LootRegistry.SET_FURLED_MAP_FUNCTION.get();
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<FurledMapLootFunction> {
      public void serialize(JsonObject json, FurledMapLootFunction scrollFunction, JsonSerializationContext jsonDeserializationContext) {
         super.m_6170_(json, scrollFunction, jsonDeserializationContext);
         json.addProperty("destination", scrollFunction.destination);
         json.addProperty("translation", scrollFunction.translation);
      }

      public FurledMapLootFunction deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootConditions) {
         if (!GsonHelper.m_13900_(json, "destination")) {
            throw new JsonSyntaxException("set_furled_map missing key: destination!");
         } else if (!GsonHelper.m_13900_(json, "translation")) {
            throw new JsonSyntaxException("set_furled_map missing key: translation!");
         } else {
            return new FurledMapLootFunction(lootConditions, GsonHelper.m_13906_(json, "destination"), GsonHelper.m_13906_(json, "translation"));
         }
      }
   }
}
