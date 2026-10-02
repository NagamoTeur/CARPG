package com.bobmowzie.mowziesmobs.server.world;

import com.bobmowzie.mowziesmobs.server.world.feature.ConfiguredFeatureHandler;
import com.bobmowzie.mowziesmobs.server.world.spawn.SpawnHandler;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class MobSpawnBiomeModifier implements BiomeModifier {
   private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(
      new ResourceLocation("mowziesmobs", "mowzie_mob_spawns"), Keys.BIOME_MODIFIER_SERIALIZERS, "mowziesmobs"
   );

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         SpawnHandler.addBiomeSpawns(biome, builder);
         ConfiguredFeatureHandler.addBiomeSpawns(biome);
      }
   }

   public Codec<? extends BiomeModifier> codec() {
      return (Codec<? extends BiomeModifier>)SERIALIZER.get();
   }

   public static Codec<MobSpawnBiomeModifier> makeCodec() {
      return Codec.unit(MobSpawnBiomeModifier::new);
   }
}
