package net.thirdlife.iterrpg.world.features.ores;

import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;
import net.thirdlife.iterrpg.procedures.GeodesConfigConditionProcedure;

public class BlackstoneGeoditeFeature extends OreFeature {
   public static BlackstoneGeoditeFeature FEATURE = null;
   public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_FEATURE = null;
   public static Holder<PlacedFeature> PLACED_FEATURE = null;
   private final Set<ResourceKey<Level>> generate_dimensions = Set.of(Level.f_46429_);

   public static Feature<?> feature() {
      FEATURE = new BlackstoneGeoditeFeature();
      CONFIGURED_FEATURE = FeatureUtils.m_206488_(
         "iter_rpg:blackstone_geodite",
         FEATURE,
         new OreConfiguration(
            List.of(
               OreConfiguration.m_161021_(new BlockStateMatchTest(Blocks.f_50730_.m_49966_()), ((Block)IterRpgModBlocks.BLACKSTONE_GEODITE.get()).m_49966_()),
               OreConfiguration.m_161021_(new BlockStateMatchTest(Blocks.f_50137_.m_49966_()), ((Block)IterRpgModBlocks.BLACKSTONE_GEODITE.get()).m_49966_())
            ),
            4
         )
      );
      PLACED_FEATURE = PlacementUtils.m_206509_(
         "iter_rpg:blackstone_geodite",
         CONFIGURED_FEATURE,
         List.of(
            CountPlacement.m_191628_(8),
            InSquarePlacement.m_191715_(),
            HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158922_(128)),
            BiomeFilter.m_191561_()
         )
      );
      return FEATURE;
   }

   public BlackstoneGeoditeFeature() {
      super(OreConfiguration.f_67837_);
   }

   public boolean m_142674_(FeaturePlaceContext<OreConfiguration> context) {
      WorldGenLevel world = context.m_159774_();
      if (!this.generate_dimensions.contains(world.m_6018_().m_46472_())) {
         return false;
      } else {
         int x = context.m_159777_().m_123341_();
         int y = context.m_159777_().m_123342_();
         int z = context.m_159777_().m_123343_();
         return !GeodesConfigConditionProcedure.execute() ? false : super.m_142674_(context);
      }
   }
}
