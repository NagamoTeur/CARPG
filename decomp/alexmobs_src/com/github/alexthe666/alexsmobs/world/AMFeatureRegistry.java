package com.github.alexthe666.alexsmobs.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AMFeatureRegistry {
   public static final DeferredRegister<Feature<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.FEATURES, "alexsmobs");
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> ANTHILL = DEF_REG.register(
      "leafcutter_anthill", () -> new FeatureLeafcutterAnthill(NoneFeatureConfiguration.f_67815_)
   );

   public static final class AMConfiguredFeatureRegistry {
      public static final DeferredRegister<ConfiguredFeature<?, ?>> DEF_REG = DeferredRegister.create(Registry.f_122881_, "alexsmobs");
      public static final RegistryObject<ConfiguredFeature<?, ?>> ANTHILL_CF = DEF_REG.register(
         "leafcutter_anthill", () -> new ConfiguredFeature((Feature)AMFeatureRegistry.ANTHILL.get(), NoneFeatureConfiguration.f_67816_)
      );
   }

   public static final class AMPlacedFeatureRegistry {
      public static final DeferredRegister<PlacedFeature> DEF_REG = DeferredRegister.create(Registry.f_194567_, "alexsmobs");
      public static final RegistryObject<PlacedFeature> ANTHILL = DEF_REG.register(
         "leafcutter_anthill", () -> new PlacedFeature((Holder)AMFeatureRegistry.AMConfiguredFeatureRegistry.ANTHILL_CF.getHolder().get(), ImmutableList.of())
      );
   }
}
