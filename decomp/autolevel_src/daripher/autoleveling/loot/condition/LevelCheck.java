package daripher.autoleveling.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import daripher.autoleveling.event.MobsLevelingEvents;
import daripher.autoleveling.init.AutoLevelingLootItemConditions;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record LevelCheck(int min, int max) implements LootItemCondition {
   @NotNull
   public LootItemConditionType m_7940_() {
      return (LootItemConditionType)AutoLevelingLootItemConditions.LEVEL_CHECK.get();
   }

   @NotNull
   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81455_);
   }

   public boolean test(LootContext context) {
      if (!context.m_78936_(LootContextParams.f_81455_)) {
         return false;
      } else {
         Entity entity = (Entity)context.m_165124_(LootContextParams.f_81455_);
         if (!MobsLevelingEvents.hasLevel(entity)) {
            return false;
         } else {
            int level = MobsLevelingEvents.getLevel((LivingEntity)entity) + 1;
            return level >= this.min && level <= this.max;
         }
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LevelCheck> {
      public void serialize(JsonObject jsonObject, LevelCheck levelCheck, @NotNull JsonSerializationContext context) {
         jsonObject.addProperty("min", levelCheck.min);
         jsonObject.addProperty("max", levelCheck.max);
      }

      @NotNull
      public LevelCheck deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
         int min = GsonHelper.m_13824_(jsonObject, "min", 0);
         int max = GsonHelper.m_13824_(jsonObject, "max", 0);
         return new LevelCheck(min, max);
      }
   }
}
