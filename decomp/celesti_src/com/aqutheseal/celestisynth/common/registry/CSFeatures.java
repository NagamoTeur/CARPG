package com.aqutheseal.celestisynth.common.registry;

import com.aqutheseal.celestisynth.common.world.feature.LunarCraterFeature;
import com.aqutheseal.celestisynth.common.world.feature.SolarCraterFeature;
import com.aqutheseal.celestisynth.common.world.feature.ZephyrDepositFeature;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBlockTagPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSFeatures {
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "celestisynth");
   public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.f_122881_, "celestisynth");
   public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.f_194567_, "celestisynth");
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SOLAR_CRATER = FEATURES.register(
      "solar_crater", () -> new SolarCraterFeature(NoneFeatureConfiguration.f_67815_)
   );
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> LUNAR_CRATER = FEATURES.register(
      "lunar_crater", () -> new LunarCraterFeature(NoneFeatureConfiguration.f_67815_)
   );
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> ZEPHYR_DEPOSIT = FEATURES.register(
      "zephyr_deposit", () -> new ZephyrDepositFeature(NoneFeatureConfiguration.f_67815_)
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> SOLAR_CRATER_CONFIGURED = CONFIGURED_FEATURES.register(
      "solar_crater_configured", () -> new ConfiguredFeature((Feature)SOLAR_CRATER.get(), new NoneFeatureConfiguration())
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> LUNAR_CRATER_CONFIGURED = CONFIGURED_FEATURES.register(
      "lunar_crater_configured", () -> new ConfiguredFeature((Feature)LUNAR_CRATER.get(), new NoneFeatureConfiguration())
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> ZEPHYR_DEPOSIT_CONFIGURED = CONFIGURED_FEATURES.register(
      "zephyr_deposit_configured", () -> new ConfiguredFeature((Feature)ZEPHYR_DEPOSIT.get(), new NoneFeatureConfiguration())
   );
   public static final RegistryObject<PlacedFeature> SOLAR_CRATER_PLACED = PLACED_FEATURES.register(
      "solar_crater_placed",
      () -> new PlacedFeature(
            (Holder)SOLAR_CRATER_CONFIGURED.getHolder().get(),
            List.of(RarityFilter.m_191900_(85), CountOnEveryLayerPlacement.m_191604_(1), BiomeFilter.m_191561_())
         )
   );
   public static final RegistryObject<PlacedFeature> LUNAR_CRATER_PLACED = PLACED_FEATURES.register(
      "lunar_crater_placed",
      () -> new PlacedFeature(
            (Holder)LUNAR_CRATER_CONFIGURED.getHolder().get(),
            List.of(
               RarityFilter.m_191900_(17),
               HeightRangePlacement.m_191683_(UniformHeight.m_162034_(VerticalAnchor.m_158930_(0), VerticalAnchor.m_158922_(30))),
               EnvironmentScanPlacement.m_191657_(Direction.DOWN, BlockPredicate.m_190432_(), MatchingBlockTagPredicate.f_190393_, 12),
               BiomeFilter.m_191561_()
            )
         )
   );
   public static final RegistryObject<PlacedFeature> ZEPHYR_DEPOSIT_PLACED = PLACED_FEATURES.register(
      "zephyr_deposit_placed",
      () -> new PlacedFeature(
            (Holder)ZEPHYR_DEPOSIT_CONFIGURED.getHolder().get(),
            List.of(
               RarityFilter.m_191900_(7),
               HeightRangePlacement.m_191683_(UniformHeight.m_162034_(VerticalAnchor.m_158922_(100), VerticalAnchor.m_158922_(320))),
               BlockPredicateFilter.m_191576_(BlockPredicate.m_190399_(((Block)CSBlocks.SOLAR_CRYSTAL.get()).m_49966_(), BlockPos.f_121853_)),
               BiomeFilter.m_191561_()
            )
         )
   );
}
