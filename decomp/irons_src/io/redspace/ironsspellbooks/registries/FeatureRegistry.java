package io.redspace.ironsspellbooks.registries;

import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FeatureRegistry {
   private static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.f_122881_, "irons_spellbooks");
   private static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.f_194567_, "irons_spellbooks");
   public static final Supplier<List<TargetBlockState>> ARCANE_DEBRIS_ORE_TARGET = Suppliers.memoize(
      () -> List.of(OreConfiguration.m_161021_(OreFeatures.f_195073_, ((Block)BlockRegistry.ARCANE_DEBRIS.get()).m_49966_()))
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_ARCANE_DEBRIS = CONFIGURED_FEATURES.register(
      "ore_arcane_debris", () -> new ConfiguredFeature(Feature.f_159727_, new OreConfiguration(ARCANE_DEBRIS_ORE_TARGET.get(), 3, 1.0F))
   );
   public static final RegistryObject<PlacedFeature> ORE_ARCANE_DEBRIS_FEATURE = PLACED_FEATURES.register(
      "ore_arcane_debris_feature",
      () -> new PlacedFeature(
            (Holder)ORE_ARCANE_DEBRIS.getHolder().get(),
            List.of(InSquarePlacement.m_191715_(), HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(-64), VerticalAnchor.m_158922_(-38)))
         )
   );

   public static void register(IEventBus eventBus) {
      CONFIGURED_FEATURES.register(eventBus);
      PLACED_FEATURES.register(eventBus);
   }

   private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
      return List.of(p_195347_, InSquarePlacement.m_191715_(), p_195348_, BiomeFilter.m_191561_());
   }

   private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
      return orePlacement(CountPlacement.m_191628_(pCount), pHeightRange);
   }

   private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
      return orePlacement(RarityFilter.m_191900_(pChance), pHeightRange);
   }
}
