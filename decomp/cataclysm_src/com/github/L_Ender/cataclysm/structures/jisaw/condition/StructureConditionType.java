package com.github.L_Ender.cataclysm.structures.jisaw.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public interface StructureConditionType<C extends StructureCondition> {
   Map<ResourceLocation, StructureConditionType<?>> CONDITION_TYPES_BY_NAME = new HashMap<>();
   Map<StructureConditionType<?>, ResourceLocation> NAME_BY_CONDITION_TYPES = new HashMap<>();
   Codec<StructureConditionType<?>> CONDITION_TYPE_CODEC = ResourceLocation.f_135803_
      .flatXmap(
         resourceLocation -> Optional.ofNullable(CONDITION_TYPES_BY_NAME.get(resourceLocation))
               .<DataResult>map(DataResult::success)
               .orElseGet(() -> DataResult.error("Unknown condition type: " + resourceLocation)),
         conditionType -> Optional.of(NAME_BY_CONDITION_TYPES.get(conditionType))
               .<DataResult>map(DataResult::success)
               .orElseGet(() -> DataResult.error("No ID found for condition type " + conditionType + ". Is it registered?"))
      );
   Codec<StructureCondition> CONDITION_CODEC = CONDITION_TYPE_CODEC.dispatch("type", StructureCondition::type, StructureConditionType::codec);
   StructureConditionType<AlwaysTrueCondition> ALWAYS_TRUE = register("always_true", AlwaysTrueCondition.CODEC);

   static <C extends StructureCondition> StructureConditionType<C> register(ResourceLocation resourceLocation, Codec<C> codec) {
      StructureConditionType<C> conditionType = () -> codec;
      CONDITION_TYPES_BY_NAME.put(resourceLocation, conditionType);
      NAME_BY_CONDITION_TYPES.put(conditionType, resourceLocation);
      return conditionType;
   }

   private static <C extends StructureCondition> StructureConditionType<C> register(String id, Codec<C> codec) {
      return register(new ResourceLocation("cataclysm", id), codec);
   }

   Codec<C> codec();
}
