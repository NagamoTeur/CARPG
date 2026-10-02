package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.handlers.LootFunctionRevelation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class EnigmaticLootFunctions {
   private static final EnigmaticLootFunctions INSTANCE = new EnigmaticLootFunctions();
   public static final LootItemFunctionType REVELATION = register("revelation", new LootFunctionRevelation.Serializer());

   private static LootItemFunctionType register(String id, Serializer<? extends LootItemFunction> serializer) {
      return (LootItemFunctionType)Registry.m_122965_(Registry.f_122876_, new ResourceLocation("enigmaticlegacy", id), new LootItemFunctionType(serializer));
   }
}
