package com.github.L_Ender.cataclysm.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class CMMobSpawnBiomeModifier implements BiomeModifier {
   private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(
      new ResourceLocation("cataclysm", "cataclysm_mob_spawns"), Keys.BIOME_MODIFIER_SERIALIZERS, "cataclysm"
   );

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         CMWorldRegistry.addBiomeSpawns(biome, builder);
      }
   }

   public Codec<? extends BiomeModifier> codec() {
      return (Codec<? extends BiomeModifier>)SERIALIZER.get();
   }

   public static Codec<CMMobSpawnBiomeModifier> makeCodec() {
      return Codec.unit(CMMobSpawnBiomeModifier::new);
   }
}
