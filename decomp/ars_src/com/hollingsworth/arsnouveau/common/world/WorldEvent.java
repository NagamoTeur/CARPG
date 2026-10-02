package com.hollingsworth.arsnouveau.common.world;

import com.hollingsworth.arsnouveau.common.block.SourceBerryBush;
import com.hollingsworth.arsnouveau.common.world.tree.MagicTrunkPlacer;
import com.hollingsworth.arsnouveau.common.world.tree.SupplierBlockStateProvider;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldEvent {
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> CASCADING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:cascade_feature",
      Feature.f_65760_,
      buildTree("blue_archwood_leaves", "blue_archwood_log", false, new ResourceLocation("ars_nouveau", "frostaya_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> BLAZING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:blazing_feature",
      Feature.f_65760_,
      buildTree("red_archwood_leaves", "red_archwood_log", false, new ResourceLocation("ars_nouveau", "bombegranate_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> FLOURISHING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:flourishing_feature",
      Feature.f_65760_,
      buildTree("green_archwood_leaves", "green_archwood_log", false, new ResourceLocation("ars_nouveau", "mendosteen_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> VEXING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:vexing_feature",
      Feature.f_65760_,
      buildTree("purple_archwood_leaves", "purple_archwood_log", false, new ResourceLocation("ars_nouveau", "bastion_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> NATURAL_CASCADE_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:natural_cascade_feature",
      Feature.f_65760_,
      buildTree("blue_archwood_leaves", "blue_archwood_log", true, new ResourceLocation("ars_nouveau", "frostaya_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> NATURAL_BLAZING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:natural_blazing_feature",
      Feature.f_65760_,
      buildTree("red_archwood_leaves", "red_archwood_log", true, new ResourceLocation("ars_nouveau", "bombegranate_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> NATURAL_FLOURISHING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:natural_flourishing_feature",
      Feature.f_65760_,
      buildTree("green_archwood_leaves", "green_archwood_log", true, new ResourceLocation("ars_nouveau", "mendosteen_pod"))
   );
   public static Holder<ConfiguredFeature<TreeConfiguration, ?>> NATURAL_VEXING_TREE = FeatureUtils.m_206488_(
      "ars_nouveau:natural_vexing_feature",
      Feature.f_65760_,
      buildTree("purple_archwood_leaves", "purple_archwood_log", true, new ResourceLocation("ars_nouveau", "bastion_pod"))
   );
   public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BERRY_BUSH = FeatureUtils.m_206488_(
      "ars_nouveau:patch_berry",
      Feature.f_65763_,
      FeatureUtils.m_206476_(
         Feature.f_65741_,
         new SimpleBlockConfiguration(
            BlockStateProvider.m_191384_(
               (BlockState)((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", "sourceberry_bush")))
                  .m_49966_()
                  .m_61124_(SourceBerryBush.AGE, 3)
            )
         ),
         List.of(Blocks.f_50440_)
      )
   );
   public static Holder<PlacedFeature> BERRY_BUSH_PATCH_CONFIG = PlacementUtils.m_206513_(
      "ars_nouveau:placed_berry",
      PATCH_BERRY_BUSH,
      new PlacementModifier[]{RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195354_, BiomeFilter.m_191561_()}
   );
   public static Holder<PlacedFeature> PLACED_CASCADE = PlacementUtils.m_206513_(
      "ars_nouveau:placed_cascade", NATURAL_CASCADE_TREE, new PlacementModifier[]{PlacementUtils.m_206493_(BlockRegistry.CASCADING_SAPLING)}
   );
   public static Holder<PlacedFeature> PLACED_BLAZING = PlacementUtils.m_206513_(
      "ars_nouveau:placed_blazing", NATURAL_BLAZING_TREE, new PlacementModifier[]{PlacementUtils.m_206493_(BlockRegistry.BLAZING_SAPLING)}
   );
   public static Holder<PlacedFeature> PLACED_VEXING = PlacementUtils.m_206513_(
      "ars_nouveau:placed_vexing", NATURAL_VEXING_TREE, new PlacementModifier[]{PlacementUtils.m_206493_(BlockRegistry.VEXING_SAPLING)}
   );
   public static Holder<PlacedFeature> PLACED_FLOURISHING = PlacementUtils.m_206513_(
      "ars_nouveau:placed_flourishing", NATURAL_FLOURISHING_TREE, new PlacementModifier[]{PlacementUtils.m_206493_(BlockRegistry.FLOURISHING_SAPLING)}
   );
   public static Holder<PlacedFeature> COMMON_FLOURISHING = PlacementUtils.m_206509_(
      "ars_nouveau:common_flourishing",
      NATURAL_FLOURISHING_TREE,
      List.of(PlacementUtils.f_195355_, PlacementUtils.m_206493_(BlockRegistry.FLOURISHING_SAPLING))
   );
   public static Holder<PlacedFeature> COMMON_CASCADING = PlacementUtils.m_206509_(
      "ars_nouveau:common_cascading", NATURAL_CASCADE_TREE, List.of(PlacementUtils.f_195355_, PlacementUtils.m_206493_(BlockRegistry.CASCADING_SAPLING))
   );
   public static Holder<PlacedFeature> COMMON_BLAZING = PlacementUtils.m_206509_(
      "ars_nouveau:common_blazing", NATURAL_BLAZING_TREE, List.of(PlacementUtils.f_195355_, PlacementUtils.m_206493_(BlockRegistry.BLAZING_SAPLING))
   );
   public static Holder<PlacedFeature> COMMON_VEXING = PlacementUtils.m_206509_(
      "ars_nouveau:common_vexing", NATURAL_VEXING_TREE, List.of(PlacementUtils.f_195355_, PlacementUtils.m_206493_(BlockRegistry.VEXING_SAPLING))
   );
   public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> MIXED_TREES = FeatureUtils.m_206488_(
      "ars_nouveau:random_mixed",
      Feature.f_65755_,
      new SimpleRandomFeatureConfiguration(HolderSet.m_205809_(new Holder[]{PLACED_CASCADE, PLACED_BLAZING, PLACED_VEXING, PLACED_FLOURISHING}))
   );
   public static Holder<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> MIXED_COMMON_TREES = FeatureUtils.m_206488_(
      "ars_nouveau:random_common_archwood",
      Feature.f_65755_,
      new SimpleRandomFeatureConfiguration(HolderSet.m_205809_(new Holder[]{COMMON_FLOURISHING, COMMON_BLAZING, COMMON_VEXING, COMMON_CASCADING}))
   );
   public static ResourceLocation PLACED_MIXED_ID = new ResourceLocation("ars_nouveau", "archwood");
   public static Holder<PlacedFeature> PLACED_MIXED = PlacementUtils.m_206509_(
      PLACED_MIXED_ID.toString(), MIXED_TREES, VegetationPlacements.m_195479_(RarityFilter.m_191900_(Config.TREE_SPAWN_RATE))
   );
   public static Holder<PlacedFeature> COMMON_ARCHWOOD = PlacementUtils.m_206509_(
      "ars_nouveau:common_archwood", MIXED_COMMON_TREES, VegetationPlacements.m_195481_(PlacementUtils.m_195364_(7, 0.01F, 1), BlockRegistry.BLAZING_SAPLING)
   );
   public static Holder<PlacedFeature> ArtisanalMojangGrassTM = PlacementUtils.m_206509_(
      "ars_nouveau:patch_grass_forest", VegetationFeatures.f_195182_, VegetationPlacements.m_195474_(2)
   );
   public static final Holder<PlacedFeature> ArtisanalMojangFlowersTM = PlacementUtils.m_206513_(
      "ars_nouveau:flower_default",
      VegetationFeatures.f_195192_,
      new PlacementModifier[]{RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_()}
   );

   public static TreeConfiguration buildTree(String leaves, String log, boolean natural, ResourceLocation podRegistryName) {
      return new TreeConfigurationBuilder(
            new SupplierBlockStateProvider(log),
            new MagicTrunkPlacer(9, 1, 0, natural, podRegistryName.toString()),
            new SupplierBlockStateProvider(leaves),
            new BlobFoliagePlacer(UniformInt.m_146622_(0, 0), UniformInt.m_146622_(0, 0), 0),
            new TwoLayersFeatureSize(2, 0, 2)
         )
         .m_68251_();
   }
}
