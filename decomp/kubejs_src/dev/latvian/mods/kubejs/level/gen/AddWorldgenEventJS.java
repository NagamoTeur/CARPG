package dev.latvian.mods.kubejs.level.gen;

import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.level.gen.filter.biome.BiomeFilter;
import dev.latvian.mods.kubejs.level.gen.properties.AddLakeProperties;
import dev.latvian.mods.kubejs.level.gen.properties.AddOreProperties;
import dev.latvian.mods.kubejs.level.gen.properties.AddSpawnProperties;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.ClassWrapper;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.Nullable;

public class AddWorldgenEventJS extends StartupEventJS {
   private static final Pattern SPAWN_PATTERN = Pattern.compile("(\\w+:\\w+)\\*\\((\\d+)-(\\d+)\\):(\\d+)");

   private static <T> Holder<T> registerFeature(Registry<T> registry, ResourceLocation id, T object) {
      ResourceKey<T> key = ResourceKey.m_135785_(registry.m_123023_(), id);
      return ((WritableRegistry)registry).m_203384_(OptionalInt.empty(), key, object, Lifecycle.experimental());
   }

   private void addFeature(ResourceLocation id, BiomeFilter filter, Decoration step, ConfiguredFeature<?, ?> feature, List<PlacementModifier> modifiers) {
      if (id == null) {
         id = new ResourceLocation("kubejs:features/" + UtilsJS.getUniqueId(feature, ConfiguredFeature.f_65373_));
      }

      Holder<ConfiguredFeature<?, ?>> holder = registerFeature(BuiltinRegistries.f_123861_, id, feature);
      PlacedFeature placed = new PlacedFeature(holder, modifiers);
      this.addFeature(id, filter, step, placed);
   }

   private void addFeature(ResourceLocation id, BiomeFilter filter, Decoration step, PlacedFeature feature) {
      if (id == null) {
         id = new ResourceLocation("kubejs:features/" + UtilsJS.getUniqueId(feature, PlacedFeature.f_191772_));
      }

      Holder<PlacedFeature> holder = registerFeature(BuiltinRegistries.f_194653_, id, feature);
      BiomeModifications.postProcessProperties(filter, (ctx, props) -> props.getGenerationProperties().addFeature(step, holder));
   }

   private void addEntitySpawn(BiomeFilter filter, MobCategory category, SpawnerData spawnerData) {
      BiomeModifications.postProcessProperties(filter, (ctx, props) -> props.getSpawnProperties().addSpawn(category, spawnerData));
   }

   @Deprecated(
      forRemoval = true
   )
   public void addFeatureJson(BiomeFilter filter, JsonObject json) {
      ResourceLocation id = json.has("id") ? new ResourceLocation(json.get("id").getAsString()) : null;
      this.addFeatureJson(filter, id, json);
   }

   @Deprecated(
      forRemoval = true
   )
   public void addFeatureJson(BiomeFilter filter, @Nullable ResourceLocation id, JsonObject json) {
      ConsoleJS.STARTUP.warn("addFeatureJson is deprecated for removal in 1.19.2! Please use virtual datapacks or addOre (for ores) instead.");
      JsonObject featureJson = json.has("feature") ? json : (JsonObject)Util.m_137469_(new JsonObject(), o -> o.add("feature", json));
      if (!featureJson.has("placement")) {
         featureJson.add("placement", new JsonArray());
      }

      if (id == null) {
         id = new ResourceLocation("kubejs:features/" + UtilsJS.getUniqueId(featureJson));
      }

      if (!featureJson.get("feature").isJsonObject()) {
         ConsoleJS.STARTUP.error("Adding feature JSONs with indirect references is not supported during worldgen events due to how dynamic registries work.");
         ConsoleJS.STARTUP.error("If you want to add a feature that references another feature by ID, please use virtual datapacks instead.");
      } else {
         PlacedFeature feature = (PlacedFeature)PlacedFeature.f_191772_.parse(JsonOps.INSTANCE, featureJson).get().orThrow();
         this.addFeature(id, filter, Decoration.SURFACE_STRUCTURES, feature);
      }
   }

   public void addOre(Consumer<AddOreProperties> p) {
      AddOreProperties properties = new AddOreProperties();
      p.accept(properties);
      if (!properties.targets.isEmpty()) {
         ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>> oreFeature = new ConfiguredFeature(
            Feature.f_65731_, new OreConfiguration(properties.targets, properties.size, properties.noSurface)
         );
         ArrayList<PlacementModifier> modifiers = new ArrayList<>();
         if (properties.count.m_142737_() > 1) {
            modifiers.add(CountPlacement.m_191630_(properties.count));
         }

         if (properties.chance > 0) {
            modifiers.add(RarityFilter.m_191900_(properties.chance));
         }

         if (properties.squared) {
            modifiers.add(InSquarePlacement.m_191715_());
         }

         modifiers.add(properties.height);
         this.addFeature(properties.id, properties.biomes, properties.worldgenLayer, oreFeature, modifiers);
      }
   }

   public void addLake(Consumer<AddLakeProperties> p) {
      AddLakeProperties properties = new AddLakeProperties();
      p.accept(properties);
      BlockState fluid = (BlockState)Iterables.getFirst(properties.fluid.getBlockStates(), Blocks.f_50016_.m_49966_());
      if (fluid != null && !fluid.m_60795_()) {
         BlockState barrier = (BlockState)Iterables.getFirst(properties.barrier.getBlockStates(), Blocks.f_50016_.m_49966_());
         if (barrier != null && !barrier.m_60795_()) {
            this.addFeature(
               properties.id,
               properties.biomes,
               properties.worldgenLayer,
               new ConfiguredFeature(Feature.f_65783_, new Configuration(BlockStateProvider.m_191384_(fluid), BlockStateProvider.m_191384_(barrier))),
               properties.chance > 0 ? Collections.singletonList(RarityFilter.m_191900_(properties.chance)) : List.of()
            );
         }
      }
   }

   public void addSpawn(Consumer<AddSpawnProperties> p) {
      AddSpawnProperties properties = new AddSpawnProperties();
      p.accept(properties);
      if (properties._entity != null && properties._category != null) {
         this.addEntitySpawn(
            properties.biomes, properties._category, new SpawnerData(properties._entity, properties.weight, properties.minCount, properties.maxCount)
         );
      }
   }

   public void addSpawn(BiomeFilter filter, MobCategory category, String spawn) {
      Matcher matcher = SPAWN_PATTERN.matcher(spawn);
      if (matcher.matches()) {
         try {
            EntityType<?> entity = Objects.requireNonNull((EntityType<?>)KubeJSRegistries.entityTypes().get(new ResourceLocation(matcher.group(1))));
            int weight = Integer.parseInt(matcher.group(4));
            int min = Integer.parseInt(matcher.group(2));
            int max = Integer.parseInt(matcher.group(3));
            this.addEntitySpawn(filter, category, new SpawnerData(entity, weight, min, max));
         } catch (Exception var9) {
            ConsoleJS.STARTUP.info("Failed to add spawn: " + var9);
         }
      } else {
         ConsoleJS.STARTUP.info("Invalid spawn syntax! Must be mod:entity_type*(minCount-maxCount):weight");
      }
   }

   public void addSpawn(MobCategory category, String spawn) {
      this.addSpawn(BiomeFilter.ALWAYS_TRUE, category, spawn);
   }

   public static ClassWrapper<VerticalAnchor> getAnchors() {
      return new ClassWrapper<>(VerticalAnchor.class);
   }
}
