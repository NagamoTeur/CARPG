package dev.latvian.mods.kubejs.level.gen;

import com.google.common.collect.ImmutableSet;
import dev.architectury.hooks.level.biome.BiomeProperties.Mutable;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.biome.BiomeModifications.BiomeContext;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.level.gen.filter.biome.BiomeFilter;
import dev.latvian.mods.kubejs.level.gen.properties.RemoveOresProperties;
import dev.latvian.mods.kubejs.level.gen.properties.RemoveSpawnsProperties;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

public class RemoveWorldgenEventJS extends StartupEventJS {
   protected static boolean checkTree(ConfiguredFeature<?, ?> configuredFeature, Predicate<FeatureConfiguration> predicate) {
      return predicate.test(configuredFeature.f_65378_())
         || configuredFeature.f_65378_().m_7817_().anyMatch(cf -> checkTree((ConfiguredFeature<?, ?>)cf, predicate));
   }

   private void removeFeature(BiomeFilter filter, Decoration decoration, Predicate<FeatureConfiguration> predicate) {
      BiomeModifications.replaceProperties(
         filter,
         (ctx, properties) -> {
            List<Holder<PlacedFeature>> removedFeatures = new ArrayList<>();

            for (Holder<PlacedFeature> feature : properties.getGenerationProperties().getFeatures(decoration)) {
               if (checkTree((ConfiguredFeature<?, ?>)((PlacedFeature)feature.m_203334_()).f_191775_().m_203334_(), predicate)) {
                  feature.m_203543_()
                     .ifPresentOrElse(
                        key -> {
                           ConsoleJS.STARTUP
                              .debug("Removing feature %s from generation step %s in biome %s".formatted(key, decoration.name().toLowerCase(), ctx.getKey()));
                           removedFeatures.add(feature);
                        },
                        () -> ConsoleJS.STARTUP.warn("Feature %s was not removed since it was not found in the registry!".formatted(feature.m_203334_()))
                     );
               }
            }

            for (Holder<PlacedFeature> featurex : removedFeatures) {
               properties.getGenerationProperties().removeFeature(decoration, (ResourceKey)featurex.m_203543_().get());
            }
         }
      );
   }

   private void removeSpawn(BiomeFilter filter, BiPredicate<MobCategory, SpawnerData> predicate) {
      BiomeModifications.replaceProperties(filter, (ctx, properties) -> properties.getSpawnProperties().removeSpawns(predicate));
   }

   public void printFeatures() {
      this.printFeatures(null);
   }

   public void printFiltered() {
      this.printFiltered(null);
   }

   public void printFeatures(@Nullable Decoration type) {
      this.printFeatures(type, BiomeFilter.ALWAYS_TRUE);
   }

   public void printFiltered(@Nullable Decoration type) {
      this.printFiltered(type, BiomeFilter.ALWAYS_TRUE);
   }

   public void printFeatures(@Nullable Decoration type, BiomeFilter filter) {
      this.printFeaturesForType(type, filter, false);
   }

   public void printFiltered(@Nullable Decoration type, BiomeFilter filter) {
      this.printFeaturesForType(type, filter, true);
   }

   public void printFeaturesForType(@Nullable Decoration type, BiomeFilter filter, boolean afterRemoval) {
      if (type == null) {
         for (Decoration step : Decoration.values()) {
            this.printFeaturesForType(step, filter, afterRemoval);
         }
      } else {
         var printer = new BiConsumer<BiomeContext, Mutable>() {
            boolean called = false;

            public void accept(BiomeContext ctx, Mutable properties) {
               if (!this.called) {
                  this.called = true;
                  Optional<ResourceLocation> biome = ctx.getKey();
                  Iterable<Holder<PlacedFeature>> features = properties.getGenerationProperties().getFeatures(type);
                  ConsoleJS.STARTUP.info("Features with type '%s' in biome '%s':".formatted(type.name().toLowerCase(), biome));
                  MutableInt unknown = new MutableInt(0);

                  for (Holder<PlacedFeature> feature : features) {
                     feature.m_203543_().ifPresentOrElse(key -> ConsoleJS.STARTUP.info("- " + key), unknown::increment);
                  }

                  if (unknown.intValue() > 0) {
                     ConsoleJS.STARTUP.info("- " + unknown + " features with unknown id");
                  }
               }
            }
         };
         if (afterRemoval) {
            BiomeModifications.postProcessProperties(filter, printer);
         } else {
            BiomeModifications.removeProperties(filter, printer);
         }
      }
   }

   public void removeFeatureById(BiomeFilter filter, Decoration decoration, ResourceLocation[] ids) {
      BiomeModifications.replaceProperties(
         filter,
         (ctx, properties) -> Stream.of(ids)
               .map(id -> ResourceKey.m_135785_(Registry.f_194567_, id))
               .forEach(id -> properties.getGenerationProperties().removeFeature(decoration, id))
      );
   }

   public void removeFeatureById(Decoration type, ResourceLocation[] ids) {
      this.removeFeatureById(BiomeFilter.ALWAYS_TRUE, type, ids);
   }

   public void removeAllFeatures(BiomeFilter filter, Decoration type) {
      this.removeFeature(filter, type, configuredFeature -> true);
   }

   public void removeAllFeatures(BiomeFilter filter) {
      for (Decoration decoration : Decoration.values()) {
         this.removeAllFeatures(filter, decoration);
      }
   }

   public void removeAllFeatures() {
      this.removeAllFeatures(BiomeFilter.ALWAYS_TRUE);
   }

   public void removeOres(Consumer<RemoveOresProperties> p) {
      RemoveOresProperties properties = new RemoveOresProperties();
      p.accept(properties);
      this.removeFeature(properties.biomes, properties.worldgenLayer, fc -> {
         if (fc instanceof OreConfiguration ore) {
            return properties.blocks.check(ore.f_161005_);
         } else {
            return fc instanceof ReplaceBlockConfiguration rb ? properties.blocks.check(rb.f_161083_) : false;
         }
      });
   }

   public void printSpawns(@Nullable MobCategory category) {
      BiomeModifications.addProperties((ctx, properties) -> {
         Optional<ResourceLocation> biome = ctx.getKey();
         Map<MobCategory, List<SpawnerData>> spawns = properties.getSpawnProperties().getSpawners();

         for (MobCategory cat : category == null ? spawns.keySet() : ImmutableSet.of(category)) {
            ConsoleJS.STARTUP.info("Mob spawns with type '%s' in biome '%s':".formatted(cat.m_21607_(), biome));

            for (SpawnerData data : spawns.get(cat)) {
               ConsoleJS.STARTUP.info("- " + data.toString());
            }
         }
      });
   }

   public void printSpawns() {
      this.printSpawns(null);
   }

   public void removeSpawns(Consumer<RemoveSpawnsProperties> p) {
      RemoveSpawnsProperties properties = new RemoveSpawnsProperties();
      p.accept(properties);
      this.removeSpawn(properties.biomes, properties.mobs);
   }

   public void removeAllSpawns() {
      this.removeSpawn(BiomeFilter.ALWAYS_TRUE, (mobCategory, spawnerData) -> true);
   }
}
