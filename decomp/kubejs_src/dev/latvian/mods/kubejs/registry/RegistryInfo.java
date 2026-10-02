package dev.latvian.mods.kubejs.registry;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RegistryInfo implements Iterable<BuilderBase<?>> {
   public static final Map<ResourceKey<? extends Registry<?>>, RegistryInfo> MAP = Collections.synchronizedMap(new LinkedHashMap<>());
   public static final List<BuilderBase<?>> ALL_BUILDERS = new LinkedList<>();
   public static final RegistryInfo SOUND_EVENT = of(Registry.f_122898_).type(SoundEvent.class);
   public static final RegistryInfo FLUID = of(Registry.f_122899_).type(Fluid.class);
   public static final RegistryInfo MOB_EFFECT = of(Registry.f_122900_).type(MobEffect.class).languageKeyPrefix("effect");
   public static final RegistryInfo BLOCK = of(Registry.f_122901_).type(Block.class);
   public static final RegistryInfo ENCHANTMENT = of(Registry.f_122902_).type(Enchantment.class);
   public static final RegistryInfo ENTITY_TYPE = of(Registry.f_122903_).type(EntityType.class);
   public static final RegistryInfo ITEM = of(Registry.f_122904_).type(Item.class).noAutoWrap();
   public static final RegistryInfo POTION = of(Registry.f_122905_).type(Potion.class);
   public static final RegistryInfo PARTICLE_TYPE = of(Registry.f_122906_).type(ParticleType.class);
   public static final RegistryInfo BLOCK_ENTITY_TYPE = of(Registry.f_122907_).type(BlockEntityType.class);
   public static final RegistryInfo PAINTING_VARIANT = of(Registry.f_235743_).type(PaintingVariant.class);
   public static final RegistryInfo CUSTOM_STAT = of(Registry.f_122909_).type(ResourceLocation.class);
   public static final RegistryInfo CHUNK_STATUS = of(Registry.f_122910_).type(ChunkStatus.class);
   public static final RegistryInfo RULE_TEST = of(Registry.f_122911_).type(RuleTestType.class);
   public static final RegistryInfo POS_RULE_TEST = of(Registry.f_122912_).type(PosRuleTestType.class);
   public static final RegistryInfo MENU = of(Registry.f_122913_).type(MenuType.class);
   public static final RegistryInfo RECIPE_TYPE = of(Registry.f_122914_).type(RecipeType.class);
   public static final RegistryInfo RECIPE_SERIALIZER = of(Registry.f_122915_).type(RecipeSerializer.class);
   public static final RegistryInfo ATTRIBUTE = of(Registry.f_122916_).type(Attribute.class);
   public static final RegistryInfo GAME_EVENT = of(Registry.f_175423_).type(GameEvent.class);
   public static final RegistryInfo POSITION_SOURCE_TYPE = of(Registry.f_175408_).type(PositionSourceType.class);
   public static final RegistryInfo STAT_TYPE = of(Registry.f_122917_).type(StatType.class);
   public static final RegistryInfo VILLAGER_TYPE = of(Registry.f_122808_).type(VillagerType.class);
   public static final RegistryInfo VILLAGER_PROFESSION = of(Registry.f_122809_).type(VillagerProfession.class);
   public static final RegistryInfo POINT_OF_INTEREST_TYPE = of(Registry.f_122810_).type(PoiType.class);
   public static final RegistryInfo MEMORY_MODULE_TYPE = of(Registry.f_122811_).type(MemoryModuleType.class);
   public static final RegistryInfo SENSOR_TYPE = of(Registry.f_122812_).type(SensorType.class);
   public static final RegistryInfo SCHEDULE = of(Registry.f_122813_).type(Schedule.class);
   public static final RegistryInfo ACTIVITY = of(Registry.f_122814_).type(Activity.class);
   public static final RegistryInfo LOOT_ENTRY = of(Registry.f_122815_).type(LootPoolEntryType.class);
   public static final RegistryInfo LOOT_FUNCTION = of(Registry.f_122816_).type(LootItemFunctionType.class);
   public static final RegistryInfo LOOT_ITEM = of(Registry.f_122817_).type(LootItemConditionType.class);
   public static final RegistryInfo LOOT_NUMBER_PROVIDER = of(Registry.f_175409_).type(LootNumberProviderType.class);
   public static final RegistryInfo LOOT_NBT_PROVIDER = of(Registry.f_175410_).type(LootNbtProviderType.class);
   public static final RegistryInfo LOOT_SCORE_PROVIDER = of(Registry.f_175411_).type(LootScoreProviderType.class);
   public static final RegistryInfo COMMAND_ARGUMENT_TYPE = of(Registry.f_235724_).type(ArgumentTypeInfo.class);
   public static final RegistryInfo DIMENSION_TYPE = of(Registry.f_122818_).type(DimensionType.class);
   public static final RegistryInfo DIMENSION = of(Registry.f_122819_).type(Level.class);
   public static final RegistryInfo LEVEL_STEM = of(Registry.f_122820_).type(LevelStem.class);
   public static final RegistryInfo FLOAT_PROVIDER_TYPE = of(Registry.f_175414_).type(FloatProviderType.class);
   public static final RegistryInfo INT_PROVIDER_TYPE = of(Registry.f_175416_).type(IntProviderType.class);
   public static final RegistryInfo HEIGHT_PROVIDER_TYPE = of(Registry.f_175418_).type(HeightProviderType.class);
   public static final RegistryInfo BLOCK_PREDICATE_TYPE = of(Registry.f_194565_).type(BlockPredicateType.class);
   public static final RegistryInfo NOISE_GENERATOR_SETTINGS = of(Registry.f_122878_).type(NoiseGeneratorSettings.class);
   public static final RegistryInfo CONFIGURED_CARVER = of(Registry.f_122880_).type(ConfiguredWorldCarver.class);
   public static final RegistryInfo CONFIGURED_FEATURE = of(Registry.f_122881_).type(ConfiguredFeature.class);
   public static final RegistryInfo PLACED_FEATURE = of(Registry.f_194567_).type(PlacedFeature.class);
   public static final RegistryInfo STRUCTURE = of(Registry.f_235725_).type(Structure.class);
   public static final RegistryInfo STRUCTURE_SET = of(Registry.f_211073_).type(StructureSet.class);
   public static final RegistryInfo PROCESSOR_LIST = of(Registry.f_122883_).type(StructureProcessorList.class);
   public static final RegistryInfo TEMPLATE_POOL = of(Registry.f_122884_).type(StructureTemplatePool.class);
   public static final RegistryInfo BIOME = of(Registry.f_122885_).type(Biome.class);
   public static final RegistryInfo NOISE = of(Registry.f_194568_).type(NoiseParameters.class);
   public static final RegistryInfo DENSITY_FUNCTION = of(Registry.f_211074_).type(DensityFunction.class);
   public static final RegistryInfo WORLD_PRESET = of(Registry.f_235726_).type(WorldPreset.class);
   public static final RegistryInfo FLAT_LEVEL_GENERATOR_PRESET = of(Registry.f_235727_).type(FlatLevelGeneratorPreset.class);
   public static final RegistryInfo CARVER = of(Registry.f_122836_).type(WorldCarver.class);
   public static final RegistryInfo FEATURE = of(Registry.f_122838_).type(Feature.class);
   public static final RegistryInfo STRUCTURE_PLACEMENT_TYPE = of(Registry.f_205929_).type(StructurePlacementType.class);
   public static final RegistryInfo STRUCTURE_PIECE = of(Registry.f_122842_).type(StructurePieceType.class);
   public static final RegistryInfo STRUCTURE_TYPE = of(Registry.f_235739_).type(StructureType.class);
   public static final RegistryInfo PLACEMENT_MODIFIER = of(Registry.f_194569_).type(PlacementModifierType.class);
   public static final RegistryInfo BLOCK_STATE_PROVIDER_TYPE = of(Registry.f_122846_).type(BlockStateProviderType.class);
   public static final RegistryInfo FOLIAGE_PLACER_TYPE = of(Registry.f_122848_).type(FoliagePlacerType.class);
   public static final RegistryInfo TRUNK_PLACER_TYPE = of(Registry.f_122849_).type(TrunkPlacerType.class);
   public static final RegistryInfo TREE_DECORATOR_TYPE = of(Registry.f_122850_).type(TreeDecoratorType.class);
   public static final RegistryInfo ROOT_PLACER_TYPE = of(Registry.f_235741_).type(RootPlacerType.class);
   public static final RegistryInfo FEATURE_SIZE_TYPE = of(Registry.f_122851_).type(FeatureSizeType.class);
   public static final RegistryInfo BIOME_SOURCE = of(Registry.f_122852_).type(Codec.class).noAutoWrap();
   public static final RegistryInfo CHUNK_GENERATOR = of(Registry.f_122853_).type(Codec.class).noAutoWrap();
   public static final RegistryInfo CONDITION = of(Registry.f_194571_).type(Codec.class).noAutoWrap();
   public static final RegistryInfo RULE = of(Registry.f_194572_).type(Codec.class).noAutoWrap();
   public static final RegistryInfo DENSITY_FUNCTION_TYPE = of(Registry.f_211075_).type(Codec.class).noAutoWrap();
   public static final RegistryInfo STRUCTURE_PROCESSOR = of(Registry.f_122854_).type(StructureProcessorType.class);
   public static final RegistryInfo STRUCTURE_POOL_ELEMENT = of(Registry.f_122855_).type(StructurePoolElementType.class);
   public static final RegistryInfo CHAT_TYPE = of(Registry.f_235730_).type(ChatType.class);
   public static final RegistryInfo CAT_VARIANT = of(Registry.f_235731_).type(CatVariant.class);
   public static final RegistryInfo FROG_VARIANT = of(Registry.f_235733_).type(FrogVariant.class);
   public static final RegistryInfo BANNER_PATTERN = of(Registry.f_235735_).type(BannerPattern.class);
   public static final RegistryInfo INSTRUMENT = of(Registry.f_235737_).type(Instrument.class);
   public static final LinkedList<RegistryInfo> AFTER_VANILLA = new LinkedList<>();
   public final ResourceKey<? extends Registry<?>> key;
   public Class<?> objectBaseClass;
   public final Map<String, BuilderType> types;
   public final Map<ResourceLocation, BuilderBase<?>> objects;
   public boolean hasDefaultTags = false;
   private BuilderType defaultType;
   public boolean bypassServerOnly;
   public boolean autoWrap;
   public String languageKeyPrefix;

   public static RegistryInfo of(ResourceKey<? extends Registry<?>> key) {
      return MAP.computeIfAbsent(key, RegistryInfo::new);
   }

   private RegistryInfo(ResourceKey<? extends Registry<?>> key) {
      this.key = key;
      this.objectBaseClass = Object.class;
      this.types = new LinkedHashMap<>();
      this.objects = new LinkedHashMap<>();
      this.bypassServerOnly = false;
      this.autoWrap = true;
      this.languageKeyPrefix = key.m_135782_().m_135815_().replace('/', '.');
   }

   public RegistryInfo type(Class<?> baseClass) {
      this.objectBaseClass = baseClass;
      return this;
   }

   public RegistryInfo bypassServerOnly() {
      this.bypassServerOnly = true;
      return this;
   }

   public RegistryInfo languageKeyPrefix(String prefix) {
      this.languageKeyPrefix = prefix;
      return this;
   }

   public RegistryInfo noAutoWrap() {
      this.autoWrap = false;
      return this;
   }

   public void addType(String type, Class<? extends BuilderBase> builderType, BuilderFactory factory, boolean isDefault) {
      BuilderType b = new BuilderType(type, builderType, factory);
      this.types.put(type, b);
      if (isDefault) {
         if (this.defaultType != null) {
            ConsoleJS.STARTUP
               .warn("Previous default type '" + this.defaultType.type() + "' for registry '" + this.key.m_135782_() + "' replaced with '" + type + "'!");
         }

         this.defaultType = b;
      }
   }

   public void addType(String type, Class<? extends BuilderBase<?>> builderType, BuilderFactory factory) {
      this.addType(type, builderType, factory, type.equals("basic"));
   }

   public void addBuilder(BuilderBase<?> builder) {
      if (builder == null) {
         throw new IllegalArgumentException("Can't add null builder in registry '" + this.key.m_135782_() + "'!");
      } else {
         if (DevProperties.get().debugInfo) {
            ConsoleJS.STARTUP.info("~ " + this.key.m_135782_() + " | " + builder.id);
         }

         if (this.objects.containsKey(builder.id)) {
            throw new IllegalArgumentException("Duplicate key '" + builder.id + "' in registry '" + this.key.m_135782_() + "'!");
         } else {
            this.objects.put(builder.id, builder);
            ALL_BUILDERS.add(builder);
         }
      }
   }

   @Nullable
   public BuilderType getDefaultType() {
      if (this.types.isEmpty()) {
         return null;
      } else {
         if (this.defaultType == null) {
            this.defaultType = this.types.values().iterator().next();
         }

         return this.defaultType;
      }
   }

   @Override
   public String toString() {
      return this.key.m_135782_().toString();
   }

   public int registerObjects(BiConsumer<ResourceLocation, Supplier<Object>> function) {
      if (DevProperties.get().debugInfo) {
         if (this.objects.isEmpty()) {
            KubeJS.LOGGER.info("Skipping " + this + " registry");
         } else {
            KubeJS.LOGGER.info("Building " + this.objects.size() + " objects of " + this + " registry");
         }
      }

      if (this.objects.isEmpty()) {
         return 0;
      } else {
         int added = 0;

         for (BuilderBase<?> builder : this) {
            if (!builder.dummyBuilder && (builder.getRegistryType().bypassServerOnly || !CommonProperties.get().serverOnly)) {
               function.accept(builder.id, builder::createTransformedObject);
               if (DevProperties.get().debugInfo) {
                  ConsoleJS.STARTUP.info("+ " + this + " | " + builder.id);
               }

               added++;
            }
         }

         if (!this.objects.isEmpty() && DevProperties.get().debugInfo) {
            KubeJS.LOGGER.info("Registered " + added + "/" + this.objects.size() + " objects of " + this);
         }

         return added;
      }
   }

   @NotNull
   @Override
   public Iterator<BuilderBase<?>> iterator() {
      return this.objects.values().iterator();
   }
}
