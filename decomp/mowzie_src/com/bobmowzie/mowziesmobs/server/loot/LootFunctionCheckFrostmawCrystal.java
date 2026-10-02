package com.bobmowzie.mowziesmobs.server.loot;

import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootFunctionCheckFrostmawCrystal extends LootItemConditionalFunction {
   public LootFunctionCheckFrostmawCrystal(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      if (entity instanceof EntityFrostmaw frostmaw && !frostmaw.getHasCrystal()) {
         stack.m_41764_(0);
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return LootTableHandler.CHECK_FROSTMAW_CRYSTAL;
   }

   public static class FunctionSerializer extends Serializer<LootFunctionCheckFrostmawCrystal> {
      public void serialize(JsonObject object, LootFunctionCheckFrostmawCrystal function, JsonSerializationContext serializationContext) {
      }

      public LootFunctionCheckFrostmawCrystal deserialize(
         JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn
      ) {
         return new LootFunctionCheckFrostmawCrystal(conditionsIn);
      }
   }
}
