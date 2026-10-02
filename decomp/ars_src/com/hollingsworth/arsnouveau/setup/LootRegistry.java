package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.common.datagen.DungeonLootGenerator;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class LootRegistry {
   public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(
      Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "ars_nouveau"
   );
   public static final RegistryObject<Codec<DungeonLootGenerator.DungeonLootEnhancerModifier>> STRUCTURE_MODDED_LOOT_IMPORTER = GLM.register(
      "dungeon_loot", DungeonLootGenerator.DungeonLootEnhancerModifier.CODEC
   );
}
