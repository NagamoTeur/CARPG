package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.items.RevelationTome;
import com.aizistral.enigmaticlegacy.registries.EnigmaticLootFunctions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootFunctionRevelation extends LootItemConditionalFunction {
   private final NumberProvider randomPoints;
   private final NumberProvider randomXP;

   private LootFunctionRevelation(LootItemCondition[] conditions, NumberProvider minMaxPoints, NumberProvider minMaxXP) {
      super(conditions);
      this.randomPoints = minMaxPoints;
      this.randomXP = minMaxXP;
   }

   public LootItemFunctionType m_7162_() {
      return EnigmaticLootFunctions.REVELATION;
   }

   public ItemStack m_7372_(ItemStack stack, LootContext context) {
      if (stack.m_41720_() instanceof RevelationTome tome) {
         stack.m_41751_(tome.createTome(this.randomPoints.m_142683_(context), this.randomXP.m_142683_(context)).m_41783_().m_6426_());
      }

      return stack;
   }

   public static LootFunctionRevelation.Builder of(NumberProvider minMaxPoints, NumberProvider minMaxXP) {
      return new LootFunctionRevelation.Builder(minMaxPoints, minMaxXP);
   }

   public static class Builder extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder<LootFunctionRevelation.Builder> {
      private final NumberProvider randomPoints;
      private final NumberProvider randomXP;

      public Builder(NumberProvider minMaxPoints, NumberProvider minMaxXP) {
         this.randomPoints = minMaxPoints;
         this.randomXP = minMaxXP;
      }

      protected LootFunctionRevelation.Builder getThis() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return new LootFunctionRevelation(this.m_80699_(), this.randomPoints, this.randomXP);
      }
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<LootFunctionRevelation> {
      public void serialize(JsonObject json, LootFunctionRevelation value, JsonSerializationContext context) {
         super.m_6170_(json, value, context);
         json.add("randomPoints", context.serialize(value.randomPoints));
         json.add("randomXP", context.serialize(value.randomXP));
      }

      public LootFunctionRevelation deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
         NumberProvider randomPoints = (NumberProvider)GsonHelper.m_13836_(json, "randomPoints", context, NumberProvider.class);
         NumberProvider randomXP = (NumberProvider)GsonHelper.m_13836_(json, "randomXP", context, NumberProvider.class);
         return new LootFunctionRevelation(conditions, randomPoints, randomXP);
      }
   }
}
