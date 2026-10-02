package com.hollingsworth.arsnouveau.common.world;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.LightTile;
import com.hollingsworth.arsnouveau.common.world.feature.SingleBlockFeature;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.DiskFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider.Rule;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Deferred {
   public static final DeferredRegister<Feature<?>> FEAT_REG = DeferredRegister.create(ForgeRegistries.FEATURES, "ars_nouveau");
   public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFG_REG = DeferredRegister.create(Registry.f_122881_, "ars_nouveau");
   public static final DeferredRegister<PlacedFeature> PLACED_FEAT_REG = DeferredRegister.create(Registry.f_194567_, "ars_nouveau");
   public static final RegistryObject<Feature<BlockStateConfiguration>> LIGHT_FEATURE = FEAT_REG.register(
      "lights",
      () -> new SingleBlockFeature(BlockStateConfiguration.f_67546_) {
            public boolean m_142674_(FeaturePlaceContext<BlockStateConfiguration> pContext) {
               return false;
            }

            @Override
            public void onStatePlace(WorldGenLevel seed, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos pos, BlockStateConfiguration config) {
               if (seed instanceof WorldGenRegion world) {
                  RandomSource random = world.m_213780_();
                  if (world.m_7702_(pos) instanceof LightTile tile) {
                     tile.color = new ParticleColor(
                        Math.max(10, random.m_188503_(255)), Math.max(10, random.m_188503_(255)), Math.max(10, random.m_188503_(255))
                     );
                  }
               }
            }
         }
   );
   public static final RegistryObject<Feature<DiskConfiguration>> DISK = FEAT_REG.register("disk", () -> new DiskFeature(DiskConfiguration.f_67618_));
   public static final RegistryObject<ConfiguredFeature<?, ?>> DISK_CLAY = CONFG_REG.register(
      "disk_clay",
      () -> new ConfiguredFeature(
            (Feature)DISK.get(),
            new DiskConfiguration(
               RuleBasedBlockStateProvider.m_225936_(Blocks.f_50129_),
               BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50129_)),
               UniformInt.m_146622_(2, 3),
               1
            )
         )
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> DISK_SAND = CONFG_REG.register(
      "disk_sand",
      () -> new ConfiguredFeature(
            (Feature)DISK.get(),
            new DiskConfiguration(
               new RuleBasedBlockStateProvider(
                  BlockStateProvider.m_191382_(Blocks.f_49992_),
                  List.of(
                     new Rule(BlockPredicate.m_224774_(Direction.DOWN.m_122436_(), new Block[]{Blocks.f_50016_}), BlockStateProvider.m_191382_(Blocks.f_50062_))
                  )
               ),
               BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50440_)),
               UniformInt.m_146622_(2, 6),
               2
            )
         )
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> DISK_GRAVEL = CONFG_REG.register(
      "disk_gravel",
      () -> new ConfiguredFeature(
            (Feature)DISK.get(),
            new DiskConfiguration(
               RuleBasedBlockStateProvider.m_225936_(Blocks.f_49994_),
               BlockPredicate.m_198311_(List.of(Blocks.f_50493_, Blocks.f_50440_)),
               UniformInt.m_146622_(2, 5),
               2
            )
         )
   );
   public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_LIGHTS = CONFG_REG.register(
      "configured_lights", () -> new ConfiguredFeature((Feature)LIGHT_FEATURE.get(), new BlockStateConfiguration(BlockRegistry.LIGHT_BLOCK.m_49966_()))
   );
   public static final RegistryObject<PlacedFeature> DISK_CLAY_PLACED = PLACED_FEAT_REG.register(
      "placed_disk_clay",
      () -> new PlacedFeature(
            Holder.m_205709_((ConfiguredFeature)DISK_CLAY.get()),
            List.of(
               InSquarePlacement.m_191715_(),
               PlacementUtils.f_195353_,
               BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(new Fluid[]{Fluids.f_76193_})),
               BiomeFilter.m_191561_()
            )
         )
   );
   public static final RegistryObject<PlacedFeature> DISK_SAND_PLACED = PLACED_FEAT_REG.register(
      "placed_disk_sand",
      () -> new PlacedFeature(
            Holder.m_205709_((ConfiguredFeature)DISK_SAND.get()),
            List.of(
               CountPlacement.m_191628_(3),
               InSquarePlacement.m_191715_(),
               PlacementUtils.f_195353_,
               BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(new Fluid[]{Fluids.f_76193_})),
               BiomeFilter.m_191561_()
            )
         )
   );
   public static final RegistryObject<PlacedFeature> DISK_GRAVEL_PLACED = PLACED_FEAT_REG.register(
      "placed_disk_gravel",
      () -> new PlacedFeature(
            Holder.m_205709_((ConfiguredFeature)DISK_GRAVEL.get()),
            List.of(
               InSquarePlacement.m_191715_(),
               PlacementUtils.f_195353_,
               BlockPredicateFilter.m_191576_(BlockPredicate.m_224782_(new Fluid[]{Fluids.f_76193_})),
               BiomeFilter.m_191561_()
            )
         )
   );
   public static final RegistryObject<PlacedFeature> PLACED_LIGHTS = PLACED_FEAT_REG.register(
      "placed_lights", () -> new PlacedFeature(Holder.m_205709_((ConfiguredFeature)CONFIGURED_LIGHTS.get()), VegetationPlacements.m_195474_(1))
   );
}
