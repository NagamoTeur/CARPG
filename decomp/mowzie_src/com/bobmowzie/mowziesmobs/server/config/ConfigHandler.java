package com.bobmowzie.mowziesmobs.server.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(
   modid = "mowziesmobs",
   bus = Bus.MOD
)
public final class ConfigHandler {
   private static final String LANG_PREFIX = "config.mowziesmobs.";
   public static final ConfigHandler.Common COMMON = new ConfigHandler.Common(ConfigHandler.COMMON_BUILDER);
   public static final ConfigHandler.Client CLIENT = new ConfigHandler.Client(ConfigHandler.CLIENT_BUILDER);
   private static final Builder COMMON_BUILDER = new Builder();
   private static final Builder CLIENT_BUILDER = new Builder();
   public static ForgeConfigSpec COMMON_CONFIG = COMMON_BUILDER.build();
   public static ForgeConfigSpec CLIENT_CONFIG = CLIENT_BUILDER.build();
   private static final Predicate<Object> STRING_PREDICATE = s -> s instanceof String;
   private static final Predicate<Object> RESOURCE_LOCATION_PREDICATE = STRING_PREDICATE.and(s -> ResourceLocation.m_135830_((String)s));
   private static final Predicate<Object> BIOME_COMBO_PREDICATE = STRING_PREDICATE.and(s -> {
      String bigString = (String)s;
      String[] typeStrings = bigString.replace(" ", "").split("[,!]");

      for (String string : typeStrings) {
         if (!RESOURCE_LOCATION_PREDICATE.test(string)) {
            return false;
         }
      }

      return true;
   });
   private static final Predicate<Object> ITEM_NAME_PREDICATE = RESOURCE_LOCATION_PREDICATE.and(
      s -> ForgeRegistries.ITEMS.containsKey(new ResourceLocation((String)s))
   );

   private ConfigHandler() {
   }

   public static class ArmorConfig {
      public final IntValue damageReduction;
      public int damageReductionValue = ArmorMaterials.IRON.m_7365_(EquipmentSlot.HEAD);
      public float toughnessValue = ArmorMaterials.IRON.m_6651_();
      public final DoubleValue toughness;

      ArmorConfig(Builder builder, int damageReduction, float toughness) {
         builder.push("armor_config");
         this.damageReduction = builder.comment("See official Minecraft Wiki for an explanation of how armor damage reduction works.")
            .translation("config.mowziesmobs.damage_reduction")
            .defineInRange("damage_reduction", damageReduction, 0, Integer.MAX_VALUE);
         this.toughness = builder.comment("See official Minecraft Wiki for an explanation of how armor toughness works.")
            .translation("config.mowziesmobs.toughness")
            .defineInRange("toughness", (double)toughness, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static class AxeOfAThousandMetals {
      public final ConfigHandler.ToolConfig toolConfig;
      public final BooleanValue breakable;

      AxeOfAThousandMetals(Builder builder) {
         builder.push("axe_of_a_thousand_metals");
         this.toolConfig = new ConfigHandler.ToolConfig(builder, 9.0F, 0.9F);
         this.breakable = builder.comment("Set to true for the Axe of a Thousand Metals to have limited durability.")
            .translation("config.mowziesmobs.breakable")
            .define("breakable", false);
         builder.pop();
      }
   }

   public static class BiomeConfig {
      public final ConfigValue<List<? extends String>> biomeTags;
      public final ConfigValue<List<? extends String>> biomeWhitelist;
      public final ConfigValue<List<? extends String>> biomeBlacklist;

      BiomeConfig(Builder builder, List<? extends String> biomeTags, List<? extends String> biomeWhitelist, List<? extends String> biomeBlacklist) {
         builder.push("biome_config");
         builder.comment(
            "Mowzie's Mobs bosses cannot generate in modded or non-overworld biomes unless the biome is added to the 'has_structure/has_mowzie_structure' tag via a datapack!"
         );
         this.biomeTags = builder.comment(
               new String[]{
                  "Each entry is a combination of allowed biome tags or biome names.",
                  "Separate types with commas to require biomes to have all tags in an entry",
                  "Put a '!' before a biome tag to mean NOT that tag",
                  "A blank entry means all biomes. No entries means no biomes.",
                  "For example, 'minecraft:is_forest,forge:is_spooky,!forge:is_snowy' would mean all biomes that are spooky forests but not snowy forests",
                  "'!minecraft:is_mountain' would mean all non-mountain biomes"
               }
            )
            .translation("config.mowziesmobs.biome_tags")
            .defineList("biome_tags", biomeTags, ConfigHandler.BIOME_COMBO_PREDICATE);
         this.biomeWhitelist = builder.comment("Allow spawns in these biomes regardless of the biome tag settings")
            .translation("config.mowziesmobs.biome_whitelist")
            .defineList("biome_whitelist", biomeWhitelist, ConfigHandler.BIOME_COMBO_PREDICATE);
         this.biomeBlacklist = builder.comment("Prevent spawns in these biomes regardless of the biome tag settings")
            .translation("config.mowziesmobs.biome_blacklist")
            .defineList("biome_blacklist", biomeBlacklist, ConfigHandler.BIOME_COMBO_PREDICATE);
         builder.pop();
      }
   }

   public static class Blowgun {
      public final DoubleValue attackDamage;
      public final IntValue poisonDuration;

      Blowgun(Builder builder) {
         builder.push("blowgun");
         this.poisonDuration = builder.comment("Duration in ticks of the poison effect (20 ticks = 1 second).")
            .translation("config.mowziesmobs.poison_duration")
            .defineInRange("poison_duration", 40, 0, Integer.MAX_VALUE);
         this.attackDamage = builder.comment("Multiply all damage done with the blowgun/darts by this amount.")
            .translation("config.mowziesmobs.attack_damage")
            .defineInRange("attack_damage", 1.0, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static class Client {
      public final BooleanValue glowEffect;
      public final BooleanValue doCameraShakes;
      public final BooleanValue playBossMusic;
      public final BooleanValue customBossBars;
      public final BooleanValue customPlayerAnims;

      private Client(Builder builder) {
         builder.push("client");
         this.glowEffect = builder.comment("Toggles the lantern glow effect, which may look bad with certain shaders.")
            .translation("config.mowziesmobs.glow_effect")
            .define("glow_effect", true);
         this.doCameraShakes = builder.comment("Enable camera shaking during certain mob attacks and abilities.")
            .translation("config.mowziesmobs.do_camera_shake")
            .define("do_camera_shake", true);
         this.playBossMusic = builder.comment("Play boss battle themes during boss encounters.")
            .translation("config.mowziesmobs.play_boss_music")
            .define("play_boss_music", true);
         this.customBossBars = builder.comment("Use custom boss health bar textures, if the boss has them.")
            .translation("config.mowziesmobs.custom_boss_bar")
            .define("custom_boss_bar", true);
         this.customPlayerAnims = builder.comment("Use custom player animations.")
            .translation("config.mowziesmobs.custom_player_anims")
            .define("custom_player_anims", true);
         builder.pop();
      }
   }

   public static class CombatConfig {
      public final DoubleValue healthMultiplier;
      public final DoubleValue attackMultiplier;

      CombatConfig(Builder builder, float healthMultiplier, float attackMultiplier) {
         builder.push("combat_config");
         this.healthMultiplier = builder.comment("Scale mob health by this value")
            .translation("config.mowziesmobs.health_multiplier")
            .defineInRange("health_multiplier", (double)healthMultiplier, 0.0, Double.MAX_VALUE);
         this.attackMultiplier = builder.comment("Scale mob attack damage by this value")
            .translation("config.mowziesmobs.attack_multiplier")
            .defineInRange("attack_multiplier", (double)attackMultiplier, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static class Common {
      public final ConfigHandler.ToolsAndAbilities TOOLS_AND_ABILITIES;
      public final ConfigHandler.Mobs MOBS;

      private Common(Builder builder) {
         this.TOOLS_AND_ABILITIES = new ConfigHandler.ToolsAndAbilities(builder);
         this.MOBS = new ConfigHandler.Mobs(builder);
      }
   }

   public static class EarthboreGauntlet {
      public final DoubleValue attackMultiplier;
      public final BooleanValue breakable;
      public final IntValue durability;
      public int durabilityValue;
      public final ConfigHandler.ToolConfig toolConfig;

      EarthboreGauntlet(Builder builder) {
         builder.push("earthbore_gauntlet");
         this.attackMultiplier = builder.comment("Multiply all damage done with the Earthbore Gauntlet by this amount.")
            .translation("config.mowziesmobs.attack_multiplier")
            .defineInRange("attack_multiplier", 1.0, 0.0, Double.MAX_VALUE);
         this.breakable = builder.comment(
               new String[]{"Set to true for the Earthbore Gauntlet to have limited durability.", "Prevents regeneration in inventory."}
            )
            .translation("config.mowziesmobs.breakable")
            .define("breakable", false);
         this.durability = builder.comment("Earthbore Gauntlet durability")
            .translation("config.mowziesmobs.durability")
            .defineInRange("durability", 400, 1, Integer.MAX_VALUE);
         this.toolConfig = new ConfigHandler.ToolConfig(builder, 6.0F, 1.2F);
         builder.pop();
      }
   }

   public static class FerrousWroughtnaut {
      public final ConfigHandler.GenerationConfig generationConfig;
      public final ConfigHandler.CombatConfig combatConfig;
      public final BooleanValue hasBossBar;
      public final BooleanValue healsOutOfBattle;
      public final BooleanValue resetHealthWhenRespawn;

      FerrousWroughtnaut(Builder builder) {
         builder.push("ferrous_wroughtnaut");
         this.generationConfig = new ConfigHandler.GenerationConfig(
            builder,
            15,
            5,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("!minecraft:is_ocean"), Collections.emptyList(), Collections.emptyList()),
            20.0F,
            50.0F,
            Collections.emptyList()
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         this.hasBossBar = builder.comment("Disable/enable Ferrous Wroughtnaut's boss health bar")
            .translation("config.mowziesmobs.has_boss_bar")
            .define("has_boss_bar", true);
         this.healsOutOfBattle = builder.comment("Disable/enable Ferrous Wroughtnaut healing while not active")
            .translation("config.mowziesmobs.heals_out_of_battle")
            .define("heals_out_of_battle", true);
         this.resetHealthWhenRespawn = builder.comment(
               "Disable/enable Ferrous Wroughtnaut resetting health when a player respawns nearby. (Prevents respawn cheese!)"
            )
            .translation("config.mowziesmobs.reset_health_when_respawn")
            .define("reset_health_when_respawn", true);
         builder.pop();
      }
   }

   public static class Foliaath {
      public final ConfigHandler.SpawnConfig spawnConfig;
      public final ConfigHandler.CombatConfig combatConfig;

      Foliaath(Builder builder) {
         builder.push("foliaath");
         this.spawnConfig = new ConfigHandler.SpawnConfig(
            builder,
            70,
            1,
            4,
            1.0,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("minecraft:is_jungle"), Collections.emptyList(), Collections.emptyList()),
            Collections.emptyList(),
            Arrays.asList("minecraft:valid_spawn", "minecraft:leaves", "minecraft:logs"),
            -65,
            60,
            true,
            false,
            false,
            Arrays.asList("minecraft:villages", "minecraft:pillager_outposts")
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         builder.pop();
      }
   }

   public static class Frostmaw {
      public final ConfigHandler.GenerationConfig generationConfig;
      public final ConfigHandler.CombatConfig combatConfig;
      public final BooleanValue stealableIceCrystal;
      public final BooleanValue hasBossBar;
      public final BooleanValue healsOutOfBattle;
      public final BooleanValue resetHealthWhenRespawn;

      Frostmaw(Builder builder) {
         builder.push("frostmaw");
         this.generationConfig = new ConfigHandler.GenerationConfig(
            builder,
            25,
            8,
            new ConfigHandler.BiomeConfig(
               builder,
               Collections.singletonList("forge:is_snowy,!minecraft:is_ocean,!minecraft:is_river,!minecraft:is_beach,!minecraft:is_forest,!minecraft:is_taiga"),
               Collections.emptyList(),
               Collections.emptyList()
            ),
            50.0F,
            100.0F,
            Arrays.asList("minecraft:villages", "minecraft:pillager_outposts")
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         this.hasBossBar = builder.comment("Disable/enable Frostmaw's boss health bar")
            .translation("config.mowziesmobs.has_boss_bar")
            .define("has_boss_bar", true);
         this.healsOutOfBattle = builder.comment("Disable/enable frostmaws healing while asleep")
            .translation("config.mowziesmobs.heals_out_of_battle")
            .define("heals_out_of_battle", true);
         this.stealableIceCrystal = builder.comment("Allow players to steal frostmaws' ice crystals (only using specific means!)")
            .translation("config.mowziesmobs.stealable_ice_crystal")
            .define("stealable_ice_crystal", true);
         this.resetHealthWhenRespawn = builder.comment("Disable/enable frostmaws resetting health when a player respawns nearby. (Prevents respawn cheese!)")
            .translation("config.mowziesmobs.reset_health_when_respawn")
            .define("reset_health_when_respawn", true);
         builder.pop();
      }
   }

   public static class GenerationConfig {
      public final IntValue generationDistance;
      public final IntValue generationSeparation;
      public final ConfigHandler.BiomeConfig biomeConfig;
      public final DoubleValue heightMin;
      public final DoubleValue heightMax;
      public final ConfigValue<List<? extends String>> avoidStructures;

      GenerationConfig(
         Builder builder,
         int generationDistance,
         int generationSeparation,
         ConfigHandler.BiomeConfig biomeConfig,
         float heightMin,
         float heightMax,
         List<String> avoidStructures
      ) {
         builder.comment("Controls for spawning structure/mob with world generation");
         builder.push("generation_config");
         this.generationDistance = builder.comment(
               new String[]{
                  "Smaller number causes more generation, -1 to disable generation", "Maximum number of chunks between placements of this mob/structure"
               }
            )
            .translation("config.mowziesmobs.generation_distance")
            .defineInRange("generation_distance", generationDistance, -1, Integer.MAX_VALUE);
         this.generationSeparation = builder.comment(
               new String[]{
                  "Smaller number causes more generation, -1 to disable generation", "Minimum number of chunks between placements of this mob/structure"
               }
            )
            .translation("config.mowziesmobs.generation_separation")
            .defineInRange("generation_separation", generationSeparation, -1, Integer.MAX_VALUE);
         this.biomeConfig = biomeConfig;
         this.heightMax = builder.comment("Maximum height for generation placement. -65 to ignore")
            .translation("config.mowziesmobs.height_max")
            .defineInRange("height_max", (double)heightMax, -65.0, 256.0);
         this.heightMin = builder.comment("Minimum height for generation placement. -65 to ignore")
            .translation("config.mowziesmobs.height_min")
            .defineInRange("height_min", (double)heightMin, -65.0, 256.0);
         this.avoidStructures = builder.comment("Names of structures this mob/structure will avoid when generating")
            .translation("config.mowziesmobs.avoid_structures")
            .defineList("avoid_structures", avoidStructures, ConfigHandler.STRING_PREDICATE);
         builder.pop();
      }
   }

   public static class Grottol {
      public final ConfigHandler.SpawnConfig spawnConfig;
      public final ConfigHandler.CombatConfig combatConfig;

      Grottol(Builder builder) {
         builder.push("grottol");
         this.spawnConfig = new ConfigHandler.SpawnConfig(
            builder,
            2,
            1,
            1,
            1.0,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("!forge:is_mushroom"), Collections.emptyList(), Collections.emptyList()),
            Collections.emptyList(),
            Collections.singletonList("minecraft:base_stone_overworld"),
            16,
            -65,
            true,
            false,
            true,
            Collections.emptyList()
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         builder.pop();
      }
   }

   public static class IceCrystal {
      public final DoubleValue attackMultiplier;
      public final BooleanValue breakable;
      public final IntValue durability;
      public int durabilityValue;

      IceCrystal(Builder builder) {
         builder.push("ice_crystal");
         this.attackMultiplier = builder.comment("Multiply all damage done with the ice crystal by this amount.")
            .translation("config.mowziesmobs.attack_multiplier")
            .defineInRange("attack_multiplier", 1.0, 0.0, Double.MAX_VALUE);
         this.breakable = builder.comment(new String[]{"Set to true for the ice crystal to have limited durability.", "Prevents regeneration in inventory."})
            .translation("config.mowziesmobs.breakable")
            .define("breakable", false);
         this.durability = builder.comment("Ice crystal durability")
            .translation("config.mowziesmobs.durability")
            .defineInRange("durability", 600, 1, Integer.MAX_VALUE);
         builder.pop();
      }
   }

   public static class Lantern {
      public final ConfigHandler.SpawnConfig spawnConfig;
      public final ConfigHandler.CombatConfig combatConfig;

      Lantern(Builder builder) {
         builder.push("lantern");
         this.spawnConfig = new ConfigHandler.SpawnConfig(
            builder,
            5,
            2,
            4,
            1.0,
            new ConfigHandler.BiomeConfig(
               builder,
               Collections.singletonList("minecraft:is_forest,mowziesmobs:is_magical,!forge:is_snowy"),
               Collections.emptyList(),
               Collections.emptyList()
            ),
            Collections.emptyList(),
            Arrays.asList("minecraft:valid_spawn", "minecraft:leaves", "minecraft:logs"),
            -65,
            60,
            true,
            false,
            false,
            Collections.emptyList()
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         builder.pop();
      }
   }

   public static class Mobs {
      public final ConfigHandler.Frostmaw FROSTMAW;
      public final ConfigHandler.Umvuthi UMVUTHI;
      public final ConfigHandler.FerrousWroughtnaut FERROUS_WROUGHTNAUT;
      public final ConfigHandler.Sculptor SCULPTOR;
      public final ConfigHandler.Grottol GROTTOL;
      public final ConfigHandler.Lantern LANTERN;
      public final ConfigHandler.Umvuthana UMVUTHANA;
      public final ConfigHandler.Naga NAGA;
      public final ConfigHandler.Foliaath FOLIAATH;

      Mobs(Builder builder) {
         builder.push("mobs");
         this.FROSTMAW = new ConfigHandler.Frostmaw(builder);
         this.UMVUTHI = new ConfigHandler.Umvuthi(builder);
         this.FERROUS_WROUGHTNAUT = new ConfigHandler.FerrousWroughtnaut(builder);
         this.SCULPTOR = new ConfigHandler.Sculptor(builder);
         this.GROTTOL = new ConfigHandler.Grottol(builder);
         this.LANTERN = new ConfigHandler.Lantern(builder);
         this.UMVUTHANA = new ConfigHandler.Umvuthana(builder);
         this.NAGA = new ConfigHandler.Naga(builder);
         this.FOLIAATH = new ConfigHandler.Foliaath(builder);
         builder.pop();
      }
   }

   public static class Naga {
      public final ConfigHandler.SpawnConfig spawnConfig;
      public final ConfigHandler.CombatConfig combatConfig;

      Naga(Builder builder) {
         builder.push("naga");
         this.spawnConfig = new ConfigHandler.SpawnConfig(
            builder,
            15,
            2,
            4,
            1.0,
            new ConfigHandler.BiomeConfig(
               builder,
               Arrays.asList("minecraft:is_beach,minecraft:is_mountain", "minecraft:is_beach,minecraft:is_hill"),
               Collections.singletonList("minecraft:stony_shore"),
               Collections.emptyList()
            ),
            Collections.emptyList(),
            Collections.emptyList(),
            -65,
            70,
            false,
            true,
            false,
            Arrays.asList("minecraft:villages", "minecraft:pillager_outposts")
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         builder.pop();
      }
   }

   public static class NagaFangDagger {
      public final ConfigHandler.ToolConfig toolConfig;
      public final IntValue poisonDuration;
      public final DoubleValue backstabDamageMultiplier;

      NagaFangDagger(Builder builder) {
         builder.push("naga_fang_dagger");
         this.toolConfig = new ConfigHandler.ToolConfig(builder, 3.0F, 2.0F);
         this.poisonDuration = builder.comment("Duration in ticks of the poison effect (20 ticks = 1 second).")
            .translation("config.mowziesmobs.poison_duration")
            .defineInRange("poison_duration", 40, 0, Integer.MAX_VALUE);
         this.backstabDamageMultiplier = builder.comment("Damage multiplier when attacking from behind")
            .translation("config.mowziesmobs.backstab_damage_mult")
            .defineInRange("backstab_damage_mult", 2.0, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static class Sculptor {
      public final ConfigHandler.GenerationConfig generationConfig;
      public final ConfigHandler.CombatConfig combatConfig;
      public final BooleanValue healsOutOfBattle;

      Sculptor(Builder builder) {
         builder.push("sculptor");
         this.generationConfig = new ConfigHandler.GenerationConfig(
            builder,
            25,
            8,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("minecraft:is_mountain"), Collections.emptyList(), Collections.emptyList()),
            120.0F,
            200.0F,
            Collections.emptyList()
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         this.healsOutOfBattle = builder.comment("Disable/enable the Sculptor healing while not in combat")
            .translation("config.mowziesmobs.heals_out_of_battle")
            .define("heals_out_of_battle", true);
         builder.pop();
      }
   }

   public static class SolVisage {
      public final ConfigHandler.ArmorConfig armorConfig;
      public final BooleanValue breakable;

      SolVisage(Builder builder) {
         builder.push("sol_visage");
         this.armorConfig = new ConfigHandler.ArmorConfig(builder, ArmorMaterials.GOLD.m_7365_(EquipmentSlot.HEAD), ArmorMaterials.GOLD.m_6651_());
         this.breakable = builder.comment("Set to true for the Sol Visage to have limited durability.")
            .translation("config.mowziesmobs.breakable")
            .define("breakable", false);
         builder.pop();
      }
   }

   public static class SpawnConfig {
      public final IntValue spawnRate;
      public final IntValue minGroupSize;
      public final IntValue maxGroupSize;
      public final DoubleValue extraRarity;
      public final ConfigHandler.BiomeConfig biomeConfig;
      public final ConfigValue<List<? extends String>> dimensions;
      public final IntValue heightMin;
      public final IntValue heightMax;
      public final BooleanValue needsDarkness;
      public final BooleanValue needsSeeSky;
      public final BooleanValue needsCantSeeSky;
      public final ConfigValue<List<? extends String>> allowedBlocks;
      public final ConfigValue<List<? extends String>> allowedBlockTags;
      public final ConfigValue<List<? extends String>> avoidStructures;

      SpawnConfig(
         Builder builder,
         int spawnRate,
         int minGroupSize,
         int maxGroupSize,
         double extraRarity,
         ConfigHandler.BiomeConfig biomeConfig,
         List<? extends String> allowedBlocks,
         List<? extends String> allowedBlockTags,
         int heightMax,
         int heightMin,
         boolean needsDarkness,
         boolean needsSeeSky,
         boolean needsCantSeeSky,
         List<String> avoidStructures
      ) {
         builder.comment("Controls for vanilla-style mob spawning");
         builder.push("spawn_config");
         this.spawnRate = builder.comment("Smaller number causes less spawning, 0 to disable spawning")
            .translation("config.mowziesmobs.spawn_rate")
            .defineInRange("spawn_rate", spawnRate, 0, Integer.MAX_VALUE);
         this.minGroupSize = builder.comment("Minimum number of mobs that appear in a spawn group")
            .translation("config.mowziesmobs.min_group_size")
            .defineInRange("min_group_size", minGroupSize, 1, Integer.MAX_VALUE);
         this.maxGroupSize = builder.comment("Maximum number of mobs that appear in a spawn group")
            .translation("config.mowziesmobs.max_group_size")
            .defineInRange("max_group_size", maxGroupSize, 1, Integer.MAX_VALUE);
         this.extraRarity = builder.comment(
               "Probability of a spawn attempt succeeding. 1 for normal spawning, 0 will prevent spawning. Used to make mobs extra rare."
            )
            .translation("config.mowziesmobs.extra_rarity")
            .defineInRange("extra_rarity", extraRarity, 0.0, 1.0);
         this.biomeConfig = biomeConfig;
         this.dimensions = builder.comment("Names of dimensions this mob can spawn in")
            .translation("config.mowziesmobs.dimensions")
            .defineList("dimensions", Collections.singletonList("minecraft:overworld"), ConfigHandler.STRING_PREDICATE);
         this.allowedBlocks = builder.comment("Names of blocks this mob is allowed to spawn on. Leave blank to ignore block names.")
            .translation("config.mowziesmobs.allowed_blocks")
            .defineList("allowed_blocks", allowedBlocks, ConfigHandler.STRING_PREDICATE);
         this.allowedBlockTags = builder.comment("Tags of blocks this mob is allowed to spawn on. Leave blank to ignore block tags.")
            .translation("config.mowziesmobs.allowed_block_tags")
            .defineList("allowed_block_tags", allowedBlockTags, ConfigHandler.STRING_PREDICATE);
         this.heightMax = builder.comment("Maximum height for this spawn. -65 to ignore.")
            .translation("config.mowziesmobs.height_max")
            .defineInRange("height_max", heightMax, -65, 256);
         this.heightMin = builder.comment("Minimum height for this spawn. -65 to ignore.")
            .translation("config.mowziesmobs.height_min")
            .defineInRange("height_min", heightMin, -65, 256);
         this.needsDarkness = builder.comment("Set to true to only allow this mob to spawn in the dark, like zombies and skeletons.")
            .translation("config.mowziesmobs.needs_darkness")
            .define("needs_darkness", needsDarkness);
         this.needsSeeSky = builder.comment("Set to true to only spawn mob if it can see the sky.")
            .translation("config.mowziesmobs.min_group_size")
            .define("needs_see_sky", needsSeeSky);
         this.needsCantSeeSky = builder.comment("Set to true to only spawn mob if it can't see the sky.")
            .translation("config.mowziesmobs.min_group_size")
            .define("needs_cant_see_sky", needsCantSeeSky);
         this.avoidStructures = builder.comment("Names of structures this mob will avoid spawning near.")
            .translation("config.mowziesmobs.avoid_structures")
            .defineList("avoid_structures", avoidStructures, ConfigHandler.STRING_PREDICATE);
         builder.pop();
      }
   }

   public static class Spear {
      public final ConfigHandler.ToolConfig toolConfig;

      Spear(Builder builder) {
         builder.push("spear");
         this.toolConfig = new ConfigHandler.ToolConfig(builder, 5.0F, 1.6F);
         builder.pop();
      }
   }

   public static class SunsBlessing {
      public final DoubleValue sunsBlessingAttackMultiplier;
      public final IntValue effectDuration;
      public final IntValue solarBeamCost;
      public final IntValue supernovaCost;

      SunsBlessing(Builder builder) {
         builder.push("suns_blessing");
         this.effectDuration = builder.comment("Duration in minutes of the Sun's Blessing effect.")
            .translation("config.mowziesmobs.suns_blessing_duration")
            .defineInRange("suns_blessing_duration", 60, 0, Integer.MAX_VALUE);
         this.sunsBlessingAttackMultiplier = builder.translation("config.mowziesmobs.suns_blessing_attack_multiplier")
            .defineInRange("suns_blessing_attack_multiplier", 1.0, 0.0, Double.MAX_VALUE);
         this.solarBeamCost = builder.comment("Cost in minutes of using the solar beam ability.")
            .translation("config.mowziesmobs.solar_beam_cost")
            .defineInRange("solar_beam_cost", 5, 0, Integer.MAX_VALUE);
         builder.pop();
         this.supernovaCost = builder.comment("Cost in minutes of using the supernova ability.")
            .translation("config.mowziesmobs.supernova_cost")
            .defineInRange("supernova_cost", 60, 0, Integer.MAX_VALUE);
      }
   }

   public static class ToolConfig {
      public final DoubleValue attackDamage;
      public float attackDamageValue = 9.0F;
      public float attackSpeedValue = 0.9F;
      public final DoubleValue attackSpeed;

      ToolConfig(Builder builder, float attackDamage, float attackSpeed) {
         builder.push("tool_config");
         this.attackDamage = builder.comment("Tool attack damage")
            .translation("config.mowziesmobs.attack_damage")
            .defineInRange("attack_damage", (double)attackDamage, 0.0, Double.MAX_VALUE);
         this.attackSpeed = builder.comment("Tool attack speed")
            .translation("config.mowziesmobs.attack_speed")
            .defineInRange("attack_speed", (double)attackSpeed, 0.0, Double.MAX_VALUE);
         builder.pop();
      }
   }

   public static class ToolsAndAbilities {
      public final DoubleValue geomancyAttackMultiplier;
      public final ConfigHandler.SunsBlessing SUNS_BLESSING;
      public final ConfigHandler.WroughtHelm WROUGHT_HELM;
      public final ConfigHandler.AxeOfAThousandMetals AXE_OF_A_THOUSAND_METALS;
      public final ConfigHandler.SolVisage SOL_VISAGE;
      public final ConfigHandler.IceCrystal ICE_CRYSTAL;
      public final ConfigHandler.UmvuthanaMask UMVUTHANA_MASK;
      public final ConfigHandler.Spear SPEAR;
      public final ConfigHandler.NagaFangDagger NAGA_FANG_DAGGER;
      public final ConfigHandler.Blowgun BLOW_GUN;
      public final ConfigHandler.EarthboreGauntlet EARTHBORE_GAUNTLET;

      ToolsAndAbilities(Builder builder) {
         builder.push("tools_and_abilities");
         this.geomancyAttackMultiplier = builder.translation("config.mowziesmobs.geomancy_attack_multiplier")
            .defineInRange("geomancy_attack_multiplier", 1.0, 0.0, Double.MAX_VALUE);
         this.SUNS_BLESSING = new ConfigHandler.SunsBlessing(builder);
         this.WROUGHT_HELM = new ConfigHandler.WroughtHelm(builder);
         this.AXE_OF_A_THOUSAND_METALS = new ConfigHandler.AxeOfAThousandMetals(builder);
         this.SOL_VISAGE = new ConfigHandler.SolVisage(builder);
         this.ICE_CRYSTAL = new ConfigHandler.IceCrystal(builder);
         this.UMVUTHANA_MASK = new ConfigHandler.UmvuthanaMask(builder);
         this.SPEAR = new ConfigHandler.Spear(builder);
         this.NAGA_FANG_DAGGER = new ConfigHandler.NagaFangDagger(builder);
         this.BLOW_GUN = new ConfigHandler.Blowgun(builder);
         this.EARTHBORE_GAUNTLET = new ConfigHandler.EarthboreGauntlet(builder);
         builder.pop();
      }
   }

   public static class Umvuthana {
      public final ConfigHandler.SpawnConfig spawnConfig;
      public final ConfigHandler.CombatConfig combatConfig;

      Umvuthana(Builder builder) {
         builder.push("umvuthana");
         builder.comment(
            new String[]{
               "Controls spawning for Umvuthana hunting groups",
               "Group size controls how many raptors spawn, not followers",
               "See Umvuthi config for grove structure controls"
            }
         );
         this.spawnConfig = new ConfigHandler.SpawnConfig(
            builder,
            5,
            1,
            1,
            1.0,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("minecraft:is_savanna"), Collections.emptyList(), Collections.emptyList()),
            Collections.emptyList(),
            Arrays.asList("minecraft:valid_spawn", "minecraft:sand"),
            -65,
            60,
            false,
            false,
            false,
            Arrays.asList("minecraft:villages", "minecraft:pillager_outposts", "mowziesmobs:umvuthana_groves")
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         builder.pop();
      }
   }

   public static class UmvuthanaMask {
      public final ConfigHandler.ArmorConfig armorConfig;

      UmvuthanaMask(Builder builder) {
         builder.push("umvuthana_mask");
         this.armorConfig = new ConfigHandler.ArmorConfig(builder, ArmorMaterials.LEATHER.m_7365_(EquipmentSlot.HEAD), ArmorMaterials.LEATHER.m_6651_());
         builder.pop();
      }
   }

   public static class Umvuthi {
      public final ConfigHandler.GenerationConfig generationConfig;
      public final ConfigHandler.CombatConfig combatConfig;
      public final BooleanValue hasBossBar;
      public final BooleanValue healsOutOfBattle;
      public final ConfigValue<? extends String> whichItem;
      public final IntValue howMany;
      public final BooleanValue resetHealthWhenRespawn;

      Umvuthi(Builder builder) {
         builder.push("umvuthi");
         builder.comment("Generation controls for Umvuthana Groves");
         this.generationConfig = new ConfigHandler.GenerationConfig(
            builder,
            25,
            8,
            new ConfigHandler.BiomeConfig(builder, Collections.singletonList("minecraft:is_savanna"), Collections.emptyList(), Collections.emptyList()),
            50.0F,
            100.0F,
            Arrays.asList("minecraft:villages", "minecraft:pillager_outposts")
         );
         this.combatConfig = new ConfigHandler.CombatConfig(builder, 1.0F, 1.0F);
         this.hasBossBar = builder.comment("Disable/enable Umvuthi's boss health bar")
            .translation("config.mowziesmobs.has_boss_bar")
            .define("has_boss_bar", true);
         this.healsOutOfBattle = builder.comment("Disable/enable Umvuthi healing while not in combat")
            .translation("config.mowziesmobs.heals_out_of_battle")
            .define("heals_out_of_battle", true);
         this.whichItem = builder.comment("Which item Umvuthi desires in exchange for the Sun's Blessing")
            .translation("config.mowziesmobs.trade_which_item")
            .define("trade_which_item", "minecraft:gold_block", ConfigHandler.ITEM_NAME_PREDICATE);
         this.howMany = builder.comment("How many of the item Umvuthi desires in exchange for the Sun's Blessing")
            .translation("config.mowziesmobs.trade_how_many")
            .defineInRange("trade_how_many", 7, 0, 64);
         this.resetHealthWhenRespawn = builder.comment("Disable/enable Umvuthi resetting health when a player respawns nearby. (Prevents respawn cheese!)")
            .translation("config.mowziesmobs.reset_health_when_respawn")
            .define("reset_health_when_respawn", true);
         builder.pop();
      }
   }

   public static class WroughtHelm {
      public final ConfigHandler.ArmorConfig armorConfig;
      public final BooleanValue breakable;

      WroughtHelm(Builder builder) {
         builder.push("wrought_helm");
         this.armorConfig = new ConfigHandler.ArmorConfig(builder, ArmorMaterials.IRON.m_7365_(EquipmentSlot.HEAD), ArmorMaterials.IRON.m_6651_());
         this.breakable = builder.comment("Set to true for the Wrought Helm to have limited durability.")
            .translation("config.mowziesmobs.breakable")
            .define("breakable", false);
         builder.pop();
      }
   }
}
