package net.thirdlife.iterrpg.world.features.plants;

import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class ArcaneFlowerFeature extends RandomPatchFeature {
   public static ArcaneFlowerFeature FEATURE = null;
   public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CONFIGURED_FEATURE = null;
   public static Holder<PlacedFeature> PLACED_FEATURE = null;
   private final Set<ResourceKey<Level>> generate_dimensions = Set.of(Level.f_46428_);

   public static Feature<?> feature() {
      FEATURE = new ArcaneFlowerFeature();
      CONFIGURED_FEATURE = FeatureUtils.m_206488_(
         "iter_rpg:arcane_flower",
         FEATURE,
         FeatureUtils.m_206480_(
            Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_((Block)IterRpgModBlocks.ARCANE_FLOWER.get())), List.of(), 6
         )
      );
      PLACED_FEATURE = PlacementUtils.m_206509_(
         "iter_rpg:arcane_flower",
         CONFIGURED_FEATURE,
         List.of(CountPlacement.m_191628_(1), RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_())
      );
      return FEATURE;
   }

   public ArcaneFlowerFeature() {
      super(RandomPatchConfiguration.f_67902_);
   }

   public boolean m_142674_(FeaturePlaceContext<RandomPatchConfiguration> context) {
      WorldGenLevel world = context.m_159774_();
      return !this.generate_dimensions.contains(world.m_6018_().m_46472_()) ? false : super.m_142674_(context);
   }
}
