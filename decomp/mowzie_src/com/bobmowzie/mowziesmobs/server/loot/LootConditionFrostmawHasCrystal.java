package com.bobmowzie.mowziesmobs.server.loot;

import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public class LootConditionFrostmawHasCrystal implements LootItemCondition {
   private static final LootConditionFrostmawHasCrystal INSTANCE = new LootConditionFrostmawHasCrystal();

   private LootConditionFrostmawHasCrystal() {
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81816_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81456_);
   }

   public boolean test(LootContext context) {
      Entity entity = (Entity)context.m_78953_(LootContextParams.f_81455_);
      return entity instanceof EntityFrostmaw frostmaw ? frostmaw.getHasCrystal() : false;
   }

   public static Builder builder() {
      return () -> INSTANCE;
   }

   public static class ConditionSerializer implements Serializer<LootConditionFrostmawHasCrystal> {
      public void serialize(JsonObject json, LootConditionFrostmawHasCrystal value, JsonSerializationContext context) {
      }

      public LootConditionFrostmawHasCrystal deserialize(JsonObject json, JsonDeserializationContext context) {
         return LootConditionFrostmawHasCrystal.INSTANCE;
      }
   }
}
