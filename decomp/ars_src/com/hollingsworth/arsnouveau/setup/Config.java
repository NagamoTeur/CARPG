package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent.Loading;
import net.minecraftforge.fml.event.config.ModConfigEvent.Reloading;

@EventBusSubscriber(
   bus = Bus.MOD,
   modid = "ars_nouveau"
)
public class Config {
   public static final String CATEGORY_GENERAL = "general";
   public static final String CATEGORY_SPELLS = "spells";
   public static final String DRYGMY_CATEGORY = "drygmy_production";
   public static ForgeConfigSpec COMMON_CONFIG;
   public static ForgeConfigSpec CLIENT_CONFIG;
   public static BooleanValue SPAWN_BOOK;
   public static BooleanValue INFORM_LIGHTS;
   public static Integer TREE_SPAWN_RATE = 100;
   public static IntValue DRYGMY_MANA_COST;
   public static IntValue SYLPH_MANA_COST;
   public static IntValue WHIRLISPRIG_MAX_PROGRESS;
   public static IntValue DRYGMY_MAX_PROGRESS;
   public static IntValue DRYGMY_BASE_ITEM;
   public static IntValue DRYGMY_UNIQUE_BONUS;
   public static IntValue DRYGMY_QUANTITY_CAP;
   public static IntValue JUMP_RING_COST;
   public static IntValue MELDER_OUTPUT;
   public static IntValue MELDER_INPUT_COST;
   public static IntValue MELDER_SOURCE_COST;
   public static IntValue ENCHANTED_FLASK_CAP;
   public static BooleanValue HUNTER_ATTACK_ANIMALS;
   public static BooleanValue STALKER_ATTACK_ANIMALS;
   public static BooleanValue GUARDIAN_ATTACK_ANIMALS;
   public static BooleanValue CHIMERA_DIVE_DESTRUCTIVE;
   public static ConfigValue<List<? extends String>> DIMENSION_BLACKLIST;
   public static IntValue ARCHWOOD_FOREST_WEIGHT;
   public static BooleanValue DYNAMIC_LIGHTS_ENABLED;
   public static BooleanValue SHOW_SUPPORTER_MESSAGE;
   public static IntValue TOUCH_LIGHT_LUMINANCE;
   public static IntValue TOUCH_LIGHT_DURATION;
   public static BooleanValue SPAWN_TOMES;
   public static BooleanValue ALTERNATE_PORTAL_RENDER;
   public static BooleanValue DISABLE_SKY_SHADER;
   public static BooleanValue SHOW_RECIPE_BOOK;
   public static IntValue MAX_LOG_EVENTS;
   public static IntValue TOOLTIP_X_OFFSET;
   public static IntValue TOOLTIP_Y_OFFSET;
   public static IntValue MANABAR_X_OFFSET;
   public static IntValue MANABAR_Y_OFFSET;
   public static IntValue BOOKWYRM_LIMIT;
   private static ConfigValue<List<? extends String>> ENTITY_LIGHT_CONFIG;
   private static ConfigValue<List<? extends String>> ITEM_LIGHT_CONFIG;
   public static Map<ResourceLocation, Integer> ENTITY_LIGHT_MAP = new HashMap<>();
   public static Map<ResourceLocation, Integer> ITEM_LIGHTMAP = new HashMap<>();

   public static boolean isGlyphEnabled(ResourceLocation tag) {
      AbstractSpellPart spellPart = ArsNouveauAPI.getInstance().getSpellpartMap().get(tag);
      if (spellPart == null) {
         throw new IllegalArgumentException("Spell Part with id " + tag + " does not exist in registry. Did you pass the right ID?");
      } else {
         return spellPart.isEnabled();
      }
   }

   public static boolean isGlyphEnabled(AbstractSpellPart tag) {
      return isGlyphEnabled(tag.getRegistryName());
   }

   public static boolean isStarterEnabled(AbstractSpellPart e) {
      return e.STARTER_SPELL != null && (Boolean)e.STARTER_SPELL.get();
   }

   @SubscribeEvent
   public static void onLoad(Loading configEvent) {
      if (configEvent.getConfig().getSpec() == CLIENT_CONFIG) {
         resetLightMaps();
      }
   }

   @SubscribeEvent
   public static void onReload(Reloading configEvent) {
      if (configEvent.getConfig().getSpec() == CLIENT_CONFIG) {
         resetLightMaps();
      }
   }

   public static void resetLightMaps() {
      ENTITY_LIGHT_MAP = new HashMap<>();
      ITEM_LIGHTMAP = new HashMap<>();

      for (Entry<String, Integer> entry : ConfigUtil.parseMapConfig(ENTITY_LIGHT_CONFIG).entrySet()) {
         ENTITY_LIGHT_MAP.put(new ResourceLocation(entry.getKey()), entry.getValue());
      }

      for (Entry<String, Integer> entry : ConfigUtil.parseMapConfig(ITEM_LIGHT_CONFIG).entrySet()) {
         ITEM_LIGHTMAP.put(new ResourceLocation(entry.getKey()), entry.getValue());
      }
   }

   public static Map<String, Integer> getDefaultEntityLight() {
      Map<String, Integer> map = new HashMap<>();
      map.put(an("spell_proj"), 15);
      map.put(an("orbit"), 15);
      map.put(an("linger"), 15);
      map.put(an("flying_item"), 10);
      map.put(an("follow_proj"), 10);
      map.put("minecraft:blaze", 10);
      map.put("minecraft:spectral_arrow", 8);
      map.put("minecraft:magma_cube", 8);
      return map;
   }

   public static Map<String, Integer> getDefaultItemLight() {
      Map<String, Integer> map = new HashMap<>();
      map.put("minecraft:glowstone", 15);
      map.put("minecraft:torch", 14);
      map.put("minecraft:glowstone_dust", 8);
      map.put("minecraft:redstone_torch", 10);
      map.put("minecraft:soul_torch", 10);
      map.put("minecraft:blaze_rod", 10);
      map.put("minecraft:glow_berries", 8);
      map.put("minecraft:lava_bucket", 15);
      map.put("minecraft:lantern", 14);
      map.put("minecraft:soul_lantern", 12);
      map.put("minecraft:shroomlight", 10);
      map.put("minecraft:glow_ink_sac", 10);
      map.put("minecraft:nether_star", 14);
      map.put("minecraft:ochre_froglight", 15);
      map.put("minecraft:pearlescent_froglight", 15);
      map.put("minecraft:verdant_froglight", 15);
      return map;
   }

   public static String an(String s) {
      return new ResourceLocation("ars_nouveau", s).toString();
   }

   static {
      Builder SERVER_BUILDER = new Builder();
      Builder CLIENT_BUILDER = new Builder();
      CLIENT_BUILDER.comment("Lighting").push("lights");
      SHOW_SUPPORTER_MESSAGE = CLIENT_BUILDER.comment("Show the supporter message. This is set to false after the first time.")
         .define("showSupporterMessage", true);
      DYNAMIC_LIGHTS_ENABLED = CLIENT_BUILDER.comment("If dynamic lights are enabled").define("lightsEnabled", false);
      TOUCH_LIGHT_LUMINANCE = CLIENT_BUILDER.comment("How bright the touch light is").defineInRange("touchLightLuminance", 8, 0, 15);
      TOUCH_LIGHT_DURATION = CLIENT_BUILDER.comment("How long the touch light lasts in ticks").defineInRange("touchLightDuration", 8, 0, 40);
      ENTITY_LIGHT_CONFIG = CLIENT_BUILDER.comment(
            new String[]{"Light level an entity should emit when dynamic lights are on", "Example entry: minecraft:blaze=15"}
         )
         .defineList("entity_lights", ConfigUtil.writeConfig(getDefaultEntityLight()), ConfigUtil::validateMap);
      ITEM_LIGHT_CONFIG = CLIENT_BUILDER.comment(
            new String[]{"Light level an item should emit when held when dynamic lights are on", "Example entry: minecraft:stick=15"}
         )
         .defineList("item_lights", ConfigUtil.writeConfig(getDefaultItemLight()), ConfigUtil::validateMap);
      CLIENT_BUILDER.pop();
      CLIENT_BUILDER.comment("Overlay").push("overlays");
      TOOLTIP_X_OFFSET = CLIENT_BUILDER.comment("X offset for the tooltip").defineInRange("xTooltip", 20, Integer.MIN_VALUE, Integer.MAX_VALUE);
      TOOLTIP_Y_OFFSET = CLIENT_BUILDER.comment("Y offset for the tooltip").defineInRange("yTooltip", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      MANABAR_X_OFFSET = CLIENT_BUILDER.comment("X offset for the Mana Bar").defineInRange("xManaBar", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      MANABAR_Y_OFFSET = CLIENT_BUILDER.comment("Y offset for the Mana Bar").defineInRange("yManaBar", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      SHOW_RECIPE_BOOK = CLIENT_BUILDER.comment("If the Storage Lectern should show the recipe book icon").define("showRecipeBook", true);
      INFORM_LIGHTS = CLIENT_BUILDER.comment("Inform the player of Dynamic lights once.").define("informLights", true);
      CLIENT_BUILDER.pop();
      CLIENT_BUILDER.comment("Misc").push("misc");
      ALTERNATE_PORTAL_RENDER = CLIENT_BUILDER.comment("Use simplified renderer for Warp Portals").define("no_end_portal_render", false);
      DISABLE_SKY_SHADER = CLIENT_BUILDER.comment("Disables the skyweave renderer. Disable if your sky is broken with shaders.")
         .define("disable_skyweave", false);
      SERVER_BUILDER.comment("General settings").push("general");
      DIMENSION_BLACKLIST = SERVER_BUILDER.comment(
            "Dimensions where hostile mobs will not spawn. Ex: [\"minecraft:overworld\", \"undergarden:undergarden\"]. . Run /forge dimensions for a list."
         )
         .defineList("dimensionBlacklist", new ArrayList(), o -> true);
      SPAWN_BOOK = SERVER_BUILDER.comment("Spawn a book in the players inventory on login").define("spawnBook", true);
      SYLPH_MANA_COST = SERVER_BUILDER.comment("How much mana whirlisprigs consume per generation").defineInRange("sylphManaCost", 250, 0, 10000);
      WHIRLISPRIG_MAX_PROGRESS = SERVER_BUILDER.comment("How much progress whirlisprigs must accumulate before creating resources")
         .defineInRange("whirlisprigProgress", 250, 0, 10000);
      HUNTER_ATTACK_ANIMALS = SERVER_BUILDER.comment("Should the Wilden Hunter attack animals?").define("hunterHuntsAnimals", false);
      STALKER_ATTACK_ANIMALS = SERVER_BUILDER.comment("Should the Wilden Stalker attack animals?").define("stalkerHuntsAnimals", false);
      GUARDIAN_ATTACK_ANIMALS = SERVER_BUILDER.comment("Should the Wilden Defender attack animals?").define("defenderHuntsAnimals", false);
      CHIMERA_DIVE_DESTRUCTIVE = SERVER_BUILDER.comment("Should the Wilden Chimera dive bomb destroy blocks?").define("destructiveDiveBomb", true);
      ARCHWOOD_FOREST_WEIGHT = SERVER_BUILDER.comment("Archwood forest spawn weight").defineInRange("archwoodForest", 2, 0, Integer.MAX_VALUE);
      BOOKWYRM_LIMIT = SERVER_BUILDER.comment("How many inventories can lectern support per bookwyrm").defineInRange("bookwyrmLimit", 8, 1, Integer.MAX_VALUE);
      SERVER_BUILDER.pop();
      SERVER_BUILDER.push("drygmy_production");
      DRYGMY_MANA_COST = SERVER_BUILDER.comment("How much source drygmys consume per generation").defineInRange("drygmyManaCost", 1000, 0, 10000);
      DRYGMY_MAX_PROGRESS = SERVER_BUILDER.comment("How many channels must occur before a drygmy produces loot").defineInRange("drygmyMaxProgress", 20, 0, 300);
      DRYGMY_UNIQUE_BONUS = SERVER_BUILDER.comment("Bonus number of items a drygmy produces per unique mob").defineInRange("drygmyUniqueBonus", 2, 0, 300);
      DRYGMY_BASE_ITEM = SERVER_BUILDER.comment("Base number of items a drygmy produces per cycle before bonuses.")
         .defineInRange("drygmyBaseItems", 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
      DRYGMY_QUANTITY_CAP = SERVER_BUILDER.comment("Max Bonus number of items a drygmy produces from nearby entities. Each entity equals 1 item.")
         .defineInRange("drygmyQuantityCap", 5, 0, 300);
      SERVER_BUILDER.pop();
      SERVER_BUILDER.comment("Items").push("item");
      SPAWN_TOMES = SERVER_BUILDER.comment("Spawn Caster Tomes in Dungeon Loot?").define("spawnTomes", true);
      JUMP_RING_COST = SERVER_BUILDER.comment("How much mana the Ring of Jumping consumes per jump").defineInRange("jumpRingCost", 30, 0, 10000);
      SERVER_BUILDER.pop();
      SERVER_BUILDER.comment("Blocks").push("block");
      MELDER_INPUT_COST = SERVER_BUILDER.comment("How much potion a melder takes from each input jar. 100 = 1 potion")
         .defineInRange("melderInputCost", 200, 100, Integer.MAX_VALUE);
      MELDER_OUTPUT = SERVER_BUILDER.comment("How much potion a melder outputs per cycle. 100 = 1 potion")
         .defineInRange("melderOutput", 100, 100, Integer.MAX_VALUE);
      MELDER_SOURCE_COST = SERVER_BUILDER.comment("How much source a melder takes per cycle").defineInRange("melderSourceCost", 300, 0, Integer.MAX_VALUE);
      ENCHANTED_FLASK_CAP = SERVER_BUILDER.comment(
            "The max potion level the enchanted flask can grant. This isnt needed unless you have an infinite potion leveling exploit."
         )
         .defineInRange("enchantedFlaskCap", 255, 2, Integer.MAX_VALUE);
      SERVER_BUILDER.pop();
      SERVER_BUILDER.comment("Debug").push("debug");
      MAX_LOG_EVENTS = SERVER_BUILDER.comment(
            "Max number of log events to keep on entities. Lowering this number may make it difficult to debug why your entities are stuck."
         )
         .defineInRange("maxLogEvents", 100, 0, Integer.MAX_VALUE);
      SERVER_BUILDER.pop();
      COMMON_CONFIG = SERVER_BUILDER.build();
      CLIENT_CONFIG = CLIENT_BUILDER.build();
   }
}
