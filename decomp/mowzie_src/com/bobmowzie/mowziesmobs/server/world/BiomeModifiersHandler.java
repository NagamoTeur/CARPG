package com.bobmowzie.mowziesmobs.server.world;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class BiomeModifiersHandler {
   public static final DeferredRegister<Codec<? extends BiomeModifier>> REG = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, "mowziesmobs");
   public static final RegistryObject<Codec<? extends BiomeModifier>> MOWZIE_MOB_SPAWNS = REG.register("mowzie_mob_spawns", MobSpawnBiomeModifier::makeCodec);
}
