package shadows.apotheosis.adventure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import shadows.apotheosis.adventure.gen.BossDungeonFeature;
import shadows.apotheosis.adventure.gen.BossDungeonFeature2;
import shadows.apotheosis.adventure.gen.RogueSpawnerFeature;

public class AdventureGeneration {
   public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CF_BOSS_DUNGEON = register(BossDungeonFeature.INSTANCE, "boss_dungeon");
   public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CF_BOSS_DUNGEON_2 = register(BossDungeonFeature2.INSTANCE, "boss_dungeon_2");
   public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CF_ROGUE_SPAWNER = register(RogueSpawnerFeature.INSTANCE, "rogue_spawner");
   public static final Holder<PlacedFeature> BOSS_DUNGEON = register(
      "boss_dungeon",
      CF_BOSS_DUNGEON,
      CountPlacement.m_191628_(AdventureConfig.bossDungeonAttempts),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158929_()),
      BiomeFilter.m_191561_()
   );
   public static final Holder<PlacedFeature> BOSS_DUNGEON_DEEP = register(
      "boss_dungeon_deep",
      CF_BOSS_DUNGEON,
      CountPlacement.m_191628_(AdventureConfig.bossDungeonAttempts / 2),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(6), VerticalAnchor.m_158922_(-1)),
      BiomeFilter.m_191561_()
   );
   public static final Holder<PlacedFeature> BOSS_DUNGEON_2 = register(
      "boss_dungeon_2",
      CF_BOSS_DUNGEON_2,
      CountPlacement.m_191628_(AdventureConfig.bossDungeon2Attempts),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158929_()),
      BiomeFilter.m_191561_()
   );
   public static final Holder<PlacedFeature> BOSS_DUNGEON_2_DEEP = register(
      "boss_dungeon_2_deep",
      CF_BOSS_DUNGEON_2,
      CountPlacement.m_191628_(AdventureConfig.bossDungeon2Attempts / 2),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(6), VerticalAnchor.m_158922_(-1)),
      BiomeFilter.m_191561_()
   );
   public static final Holder<PlacedFeature> ROGUE_SPAWNER = register(
      "rogue_spawner",
      CF_ROGUE_SPAWNER,
      CountPlacement.m_191628_(AdventureConfig.rogueSpawnerAttempts),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158922_(10), VerticalAnchor.m_158935_(0)),
      BiomeFilter.m_191561_()
   );
   public static final Holder<PlacedFeature> ROGUE_SPAWNER_DEEP = register(
      "rogue_spawner_deep",
      CF_ROGUE_SPAWNER,
      CountPlacement.m_191628_(AdventureConfig.deepRogueSpawnerAttempts),
      InSquarePlacement.m_191715_(),
      HeightRangePlacement.m_191680_(VerticalAnchor.m_158930_(6), VerticalAnchor.m_158922_(-1)),
      BiomeFilter.m_191561_()
   );

   static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> register(Feature<NoneFeatureConfiguration> feat, String id) {
      return FeatureUtils.m_206485_("apotheosis:" + id, feat);
   }

   static Holder<PlacedFeature> register(String id, Holder<? extends ConfiguredFeature<?, ?>> feat, PlacementModifier... modifs) {
      return PlacementUtils.m_206513_("apotheosis:" + id, feat, modifs);
   }

   public static void init() {
   }

   public static record BlacklistModifier(HolderSet<Biome> blacklistedBiomes, Holder<PlacedFeature> feature) implements BiomeModifier {
      public static final Codec<AdventureGeneration.BlacklistModifier> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
                  Biome.f_47432_.fieldOf("blacklisted_biomes").forGetter(AdventureGeneration.BlacklistModifier::blacklistedBiomes),
                  PlacedFeature.f_191773_.fieldOf("feature").forGetter(AdventureGeneration.BlacklistModifier::feature)
               )
               .apply(builder, AdventureGeneration.BlacklistModifier::new)
      );

      public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
         if (phase == Phase.ADD && !this.blacklistedBiomes.m_203333_(biome)) {
            builder.getGenerationSettings().m_204201_(Decoration.UNDERGROUND_STRUCTURES, this.feature);
         }
      }

      public Codec<? extends BiomeModifier> codec() {
         return CODEC;
      }
   }
}
