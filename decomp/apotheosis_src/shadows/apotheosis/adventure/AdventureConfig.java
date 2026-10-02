package shadows.apotheosis.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.adventure.boss.BossEvents;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.config.Configuration;

public class AdventureConfig {
   public static final List<ResourceLocation> DIM_WHITELIST = new ArrayList<>();
   public static final Map<ResourceLocation, LootCategory> TYPE_OVERRIDES = new HashMap<>();
   public static final Map<ResourceLocation, Pair<Float, BossEvents.BossSpawnRules>> BOSS_SPAWN_RULES = new HashMap<>();
   public static final List<AdventureConfig.LootPatternMatcher> AFFIX_ITEM_LOOT_RULES = new ArrayList<>();
   public static final List<AdventureConfig.LootPatternMatcher> GEM_LOOT_RULES = new ArrayList<>();
   public static final Map<ResourceLocation, LootRarity.Clamped> GEM_DIM_RARITIES = new HashMap<>();
   public static final List<AdventureConfig.LootPatternMatcher> AFFIX_CONVERT_LOOT_RULES = new ArrayList<>();
   public static final Map<ResourceLocation, LootRarity.Clamped> AFFIX_CONVERT_RARITIES = new HashMap<>();
   public static boolean curseBossItems = false;
   public static float bossAnnounceRange = 96.0F;
   public static float bossAnnounceVolume = 0.75F;
   public static boolean bossAnnounceIgnoreY = false;
   public static int bossSpawnCooldown = 3600;
   public static boolean bossAutoAggro = false;
   public static boolean bossGlowOnSpawn = true;
   public static int bossDungeonAttempts = 8;
   public static int bossDungeon2Attempts = 8;
   public static int rogueSpawnerAttempts = 65;
   public static int deepRogueSpawnerAttempts = 85;
   public static float spawnerValueChance = 0.11F;
   public static float randomAffixItem = 0.075F;
   public static float gemDropChance = 0.045F;
   public static float gemBossBonus = 0.33F;
   public static boolean disableQuarkOnAffixItems = true;
   public static Supplier<Item> torchItem = () -> Items.f_42000_;
   public static boolean cleaveHitsPlayers = false;
   public static Map<LootRarity, AdventureConfig.ReforgeData> reforgeCosts = new HashMap<>();

   public static void load(Configuration c) {
      c.setTitle("Apotheosis Adventure Module Config");
      TYPE_OVERRIDES.clear();
      TYPE_OVERRIDES.putAll(AdventureModule.IMC_TYPE_OVERRIDES);
      String[] overrides = c.getStringList(
         "Equipment Type Overrides",
         "affixes",
         new String[]{"minecraft:iron_sword|sword", "minecraft:shulker_shell|none"},
         "A list of type overrides for the affix loot system.  Format is <itemname>|chance|<type>.\nValid types are: none, sword, trident, shield, heavy_weapon, pickaxe, shovel, crossbow, bow"
      );

      for (String s : overrides) {
         String[] split = s.split("\\|");

         try {
            LootCategory type = LootCategory.byId(split[1].toLowerCase(Locale.ROOT));
            if (type.isArmor()) {
               throw new UnsupportedOperationException("Cannot override an item to an armor type.");
            }

            TYPE_OVERRIDES.put(new ResourceLocation(split[0]), type);
         } catch (Exception var20) {
            AdventureModule.LOGGER.error("Invalid type override entry: " + s + " will be ignored!");
            var20.printStackTrace();
         }
      }

      randomAffixItem = c.getFloat(
         "Random Affix Chance",
         "affixes",
         randomAffixItem,
         0.0F,
         1.0F,
         "The chance that a naturally spawned mob will be granted an affix item. 0 = 0%, 1 = 100%"
      );
      gemDropChance = c.getFloat("Gem Drop Chance", "affixes", gemDropChance, 0.0F, 1.0F, "The chance that a mob will drop a gem. 0 = 0%, 1 = 100%");
      gemBossBonus = c.getFloat(
         "Gem Boss Bonus",
         "affixes",
         gemBossBonus,
         0.0F,
         1.0F,
         "The flat bonus chance that bosses have to drop a gem, added to Gem Drop Chance. 0 = 0%, 1 = 100%"
      );
      cleaveHitsPlayers = c.getBoolean("Cleave Players", "affixes", cleaveHitsPlayers, "If affixes that cleave can hit players (excluding the user).");
      String[] lootRules = c.getStringList(
         "Affix Item Loot Rules",
         "affixes",
         new String[]{"minecraft:chests.*|0.35", ".*chests.*|0.3", "twilightforest:structures.*|0.3"},
         "Loot Rules, in the form of Loot Table Matchers, permitting affix items to spawn in loot tables.\nThe format for these is domain:pattern|chance and domain is optional.  Domain is a modid, pattern is a regex string, and chance is a float 0..1 chance for the item to spawn in any matched tables.\nIf you omit the domain, the format is pattern|chance, and the matcher will run for all domains.\nThe pattern MUST be a valid regex string, and should match the paths of desired loot tables under the specified domain.  Note: \"Match Any Character\" is \".*\" (dot star) and not \"*\" (star).\nIf there is a match, an item has a chance to spawn in that loot table."
      );
      AFFIX_ITEM_LOOT_RULES.clear();

      for (String s : lootRules) {
         try {
            AFFIX_ITEM_LOOT_RULES.add(AdventureConfig.LootPatternMatcher.parse(s));
         } catch (Exception var19) {
            AdventureModule.LOGGER.error("Invalid affix item loot rule: " + s + " will be ignored");
            var19.printStackTrace();
         }
      }

      lootRules = c.getStringList(
         "Gem Loot Rules",
         "gems",
         new String[]{"minecraft:chests.*|0.25", ".*chests.*|0.20", "twilightforest:structures.*|0.20"},
         "Loot Rules, in the form of Loot Table Matchers, permitting gems to spawn in loot tables.  See comment on \"Affix Item Loot Rules\" for description."
      );
      GEM_LOOT_RULES.clear();

      for (String s : lootRules) {
         try {
            GEM_LOOT_RULES.add(AdventureConfig.LootPatternMatcher.parse(s));
         } catch (Exception var18) {
            AdventureModule.LOGGER.error("Invalid gem loot rule: " + s + " will be ignored");
            var18.printStackTrace();
         }
      }

      lootRules = c.getStringList(
         "Affix Convert Loot Rules",
         "affixes",
         new String[]{".*blocks.*|0", ".*|0.35"},
         "Loot Rules, in the form of Loot Table Matchers, permitting affixes to be added to any valid item. Here, the chance refers to the chance an item receives affixes. See comment on \"Affix Item Loot Rules\" for description."
      );
      AFFIX_CONVERT_LOOT_RULES.clear();

      for (String s : lootRules) {
         try {
            AFFIX_CONVERT_LOOT_RULES.add(AdventureConfig.LootPatternMatcher.parse(s));
         } catch (Exception var17) {
            AdventureModule.LOGGER.error("Invalid affix convert loot rule: " + s + " will be ignored");
            var17.printStackTrace();
         }
      }

      String[] convertRarities = c.getStringList(
         "Affix Convert Rarities",
         "affixes",
         new String[]{"overworld|common|rare", "the_nether|uncommon|epic", "the_end|rare|mythic", "twilightforest:twilight_forest|uncommon|epic"},
         "Dimensional rarities for affix conversion (see \"Affix Convert Loot Rules\"), in the form of dimension|min|max. A dimension not listed uses all rarities."
      );
      AFFIX_CONVERT_RARITIES.clear();

      for (String s : convertRarities) {
         try {
            String[] split = s.split("\\|");
            ResourceLocation dim = new ResourceLocation(split[0]);
            LootRarity min = LootRarity.byId(split[1]);
            LootRarity max = LootRarity.byId(split[2]);
            AFFIX_CONVERT_RARITIES.put(dim, new LootRarity.Clamped.Impl(min, max));
         } catch (Exception var16) {
            AdventureModule.LOGGER.error("Invalid Affix Convert Rarity: " + s + " will be ignored");
            var16.printStackTrace();
         }
      }

      String[] gemDimRarities = c.getStringList(
         "Gem Dimensional Rarities",
         "gems",
         new String[]{"overworld|common|mythic", "the_nether|uncommon|mythic", "the_end|rare|mythic", "twilightforest:twilight_forest|uncommon|mythic"},
         "Dimensional rarities for gem drops, in the form of dimension|min|max. A dimension not listed uses all rarities."
      );
      GEM_DIM_RARITIES.clear();

      for (String s : gemDimRarities) {
         try {
            String[] split = s.split("\\|");
            ResourceLocation dim = new ResourceLocation(split[0]);
            LootRarity min = LootRarity.byId(split[1]);
            LootRarity max = LootRarity.byId(split[2]);
            GEM_DIM_RARITIES.put(dim, new LootRarity.Clamped.Impl(min, max));
         } catch (Exception var15) {
            AdventureModule.LOGGER.error("Invalid Gem Dimensional Rarity: " + s + " will be ignored");
            var15.printStackTrace();
         }
      }

      disableQuarkOnAffixItems = c.getBoolean(
         "Disable Quark Tooltips for Affix Items", "affixes", true, "If Quark's Attribute Tooltip handling is disabled for affix items"
      );
      String torch = c.getString(
         "Torch Placement Item",
         "affixes",
         "minecraft:torch",
         "The item that will be used when attempting to place torches with the torch placer affix.  Must be a valid item that places a block on right click."
      );
      torchItem = () -> {
         try {
            Item i = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(torch));
            return i == Items.f_41852_ ? Items.f_42000_ : i;
         } catch (Exception var2) {
            AdventureModule.LOGGER.error("Invalid torch item {}", torch);
            return Items.f_42000_;
         }
      };
      curseBossItems = c.getBoolean(
         "Curse Boss Items",
         "bosses",
         curseBossItems,
         "If boss items are always cursed.  Enable this if you want bosses to be less overpowered by always giving them a negative effect."
      );
      bossAnnounceRange = c.getFloat(
         "Boss Announce Range",
         "bosses",
         bossAnnounceRange,
         0.0F,
         1024.0F,
         "The range at which boss spawns will be announced.  If you are closer than this number of blocks (ignoring y-level), you will receive the announcement."
      );
      bossAnnounceVolume = c.getFloat(
         "Boss Announce Volume",
         "bosses",
         bossAnnounceVolume,
         0.0F,
         1.0F,
         "The volume of the boss announcement sound. 0 to disable. This control is clientside."
      );
      bossAnnounceIgnoreY = c.getBoolean("Boss Announce Ignore Y", "bosses", bossAnnounceIgnoreY, "If the boss announcement range ignores y-level.");
      bossSpawnCooldown = c.getInt(
         "Boss Spawn Cooldown",
         "bosses",
         bossSpawnCooldown,
         0,
         720000,
         "The time, in ticks, that must pass between any two natural boss spawns in a single dimension."
      );
      bossAutoAggro = c.getBoolean("Boss Auto-Aggro", "bosses", bossAutoAggro, "If true, invading bosses will automatically target the closest player.");
      bossGlowOnSpawn = c.getBoolean("Boss Glowing On Spawn", "bosses", bossGlowOnSpawn, "If true, bosses will glow when they spawn.");
      String[] dims = c.getStringList(
         "Boss Spawn Dimensions",
         "bosses",
         new String[]{
            "minecraft:overworld|0.018|NEEDS_SKY",
            "minecraft:the_nether|0.025|ANY",
            "minecraft:the_end|0.018|SURFACE_OUTER_END",
            "twilightforest:twilight_forest|0.05|NEEDS_SURFACE"
         },
         "Dimensions where bosses can spawn naturally, spawn chance, and spawn rules.\nFormat is dimname|chance|rule, chance is a float from 0..1.\nValid rules are visible here https://github.com/Shadows-of-Fire/Apotheosis/blob/1.19/src/main/java/shadows/apotheosis/adventure/boss/BossEvents.java#L174C27-L174C27"
      );
      BOSS_SPAWN_RULES.clear();

      for (String s : dims) {
         try {
            String[] split = s.split("\\|");
            BOSS_SPAWN_RULES.put(
               new ResourceLocation(split[0]), Pair.of(Float.parseFloat(split[1]), BossEvents.BossSpawnRules.valueOf(split[2].toUpperCase(Locale.ROOT)))
            );
         } catch (Exception var14) {
            AdventureModule.LOGGER.error("Invalid boss spawn rules: " + s + " will be ignored");
            var14.printStackTrace();
         }
      }

      dims = c.getStringList("Generation Dimension Whitelist", "worldgen", new String[]{"overworld"}, "The dimensions that the deadly module will generate in.");
      DIM_WHITELIST.clear();

      for (String s : dims) {
         try {
            DIM_WHITELIST.add(new ResourceLocation(s.trim()));
         } catch (ResourceLocationException var13) {
            AdventureModule.LOGGER.error("Invalid dim whitelist entry: " + s + " will be ignored");
         }
      }

      bossDungeonAttempts = c.getInt("Boss Dungeon Attempts", "worldgen", 8, 0, 256, "The number of boss dungeon generation attempts per-chunk.");
      bossDungeon2Attempts = c.getInt(
         "Boss Dungeon (Variant 2) Attempts", "worldgen", 8, 0, 256, "The number of boss dungeon (variant 2) generation attempts per-chunk."
      );
      rogueSpawnerAttempts = c.getInt("Rogue Spawner Attempts", "worldgen", 65, 0, 1024, "The number of rogue spawner generation attempts per-chunk.");
      deepRogueSpawnerAttempts = c.getInt(
         "Deep Rogue Spawner Attempts", "worldgen", 85, 0, 1024, "The number of deep rogue spawner generation attempts per-chunk."
      );
      spawnerValueChance = c.getFloat(
         "Spawner Value Chance",
         "spawners",
         spawnerValueChance,
         0.0F,
         1.0F,
         "The chance that a Rogue Spawner has a \"valuable\" chest instead of a standard one. 0 = 0%, 1 = 100%"
      );
      reforgeCosts.clear();
      int num = 1;

      for (LootRarity r : LootRarity.values()) {
         int matCost = c.getInt("Material Cost", "reforging." + r.id(), 2, 0, 64, "The amount of rarity materials it costs to reforge at this rarity.");
         int dustCost = c.getInt("Gem Dust Cost", "reforging." + r.id(), 2, 0, 64, "The amount of gem dust it costs to reforge at this rarity.");
         int levelCost = c.getInt("XP Level Cost", "reforging." + r.id(), num * 5, 0, 65536, "The amount of xp levels it costs to reforge at this rarity.");
         reforgeCosts.put(r, new AdventureConfig.ReforgeData(matCost, dustCost, levelCost));
         num++;
      }
   }

   public static boolean canGenerateIn(WorldGenLevel world) {
      ResourceKey<Level> key = world.m_6018_().m_46472_();
      return DIM_WHITELIST.contains(key.m_135782_());
   }

   public static record LootPatternMatcher(@Nullable String domain, Pattern pathRegex, float chance) {
      public boolean matches(ResourceLocation id) {
         return (this.domain == null || this.domain.equals(id.m_135827_())) && this.pathRegex.matcher(id.m_135815_()).matches();
      }

      public static AdventureConfig.LootPatternMatcher parse(String s) throws Exception {
         int pipe = s.lastIndexOf(124);
         int colon = s.indexOf(58);
         float chance = Float.parseFloat(s.substring(pipe + 1));
         String domain = colon == -1 ? null : s.substring(0, colon);
         Pattern pattern = Pattern.compile(s.substring(colon + 1, pipe));
         return new AdventureConfig.LootPatternMatcher(domain, pattern, chance);
      }
   }

   public static record ReforgeData(int matCost, int dustCost, int levelCost) {
   }
}
