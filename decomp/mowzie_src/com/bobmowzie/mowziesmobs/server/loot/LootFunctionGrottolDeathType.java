package com.bobmowzie.mowziesmobs.server.loot;

import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
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

public class LootFunctionGrottolDeathType extends LootItemConditionalFunction {
   public LootFunctionGrottolDeathType(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   protected ItemStack m_7372_(ItemStack stack, LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      if (entity instanceof EntityGrottol grottol) {
         EntityGrottol.EnumDeathType deathType = grottol.getDeathType();
         if (deathType == EntityGrottol.EnumDeathType.NORMAL) {
            stack.m_41764_(0);
         } else if (deathType == EntityGrottol.EnumDeathType.FORTUNE_PICKAXE) {
            stack.m_41764_(stack.m_41613_() + 1);
         }
      }

      return stack;
   }

   public LootItemFunctionType m_7162_() {
      return null;
   }

   public static class FunctionSerializer extends Serializer<LootFunctionGrottolDeathType> {
      public void serialize(JsonObject object, LootFunctionGrottolDeathType functionClazz, JsonSerializationContext serializationContext) {
      }

      public LootFunctionGrottolDeathType deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
         return new LootFunctionGrottolDeathType(conditionsIn);
      }
   }
}
