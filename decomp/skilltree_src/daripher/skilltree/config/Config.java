package daripher.skilltree.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(
   modid = "skilltree",
   bus = Bus.MOD
)
public class Config {
   public static final ForgeConfigSpec SPEC = Config.BUILDER.build();
   private static final Builder BUILDER = new Builder();
   private static final ConfigValue<Integer> MAX_SKILL_POINTS = BUILDER.defineInRange("Maximum skill points", 85, 1, 1000);
   private static final ConfigValue<Integer> DEFAULT_HELMET_SOCKETS = BUILDER.defineInRange("Default Helmets Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_CHESTPLATE_SOCKETS = BUILDER.defineInRange("Default Chestplates Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_LEGGINGS_SOCKETS = BUILDER.defineInRange("Default Leggings Sockets", 0, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_BOOTS_SOCKETS = BUILDER.defineInRange("Default Boots Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_WEAPON_SOCKETS = BUILDER.defineInRange("Default Weapons Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_SHIELD_SOCKETS = BUILDER.defineInRange("Default Shields Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_NECKLACE_SOCKETS = BUILDER.defineInRange("Default Necklaces Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> DEFAULT_RING_SOCKETS = BUILDER.defineInRange("Default Rings Sockets", 1, 0, 4);
   private static final ConfigValue<Integer> FIRST_SKILL_COST = BUILDER.defineInRange("First skill point cost", 15, 0, Integer.MAX_VALUE);
   private static final ConfigValue<Integer> LAST_SKILL_COST = BUILDER.defineInRange("Last skill point cost", 1400, 0, Integer.MAX_VALUE);
   private static final ConfigValue<Double> GEM_DROP_CHANCE = BUILDER.defineInRange("Base drop chance", 0.15, 0.0, 1.0);
   private static final ConfigValue<Double> AMNESIA_SCROLL_PENALTY = BUILDER.defineInRange("Amnesia scroll penalty", 0.2, 0.0, 1.0);
   private static final ConfigValue<Double> GRINDSTONE_EXP_MULTIPLIER = BUILDER.defineInRange("Grindstone experience multiplier", 0.1, 0.0, 1.0);
   private static final ConfigValue<Double> MIXTURE_EFFECTS_DURATION = BUILDER.defineInRange("Effects duration multiplier", 1.0, 0.0, 2.0);
   private static final ConfigValue<Double> MIXTURE_EFFECTS_STRENGTH = BUILDER.defineInRange("Effects strength multiplier", 1.0, 0.0, 2.0);
   private static final ConfigValue<Boolean> SHOW_CHAT_MESSAGES = BUILDER.define("Show chat messages", true);
   private static final ConfigValue<Boolean> ENABLE_EXP_EXCHANGE = BUILDER.define("Enable exprerience exchange for skill points", true);
   private static final ConfigValue<Boolean> DRAGON_DROPS_AMNESIA_SCROLL = BUILDER.define("Drop amnesia scrolls from the Ender Dragon", true);
   private static final ConfigValue<Boolean> USE_POINTS_COSTS_ARRAY = BUILDER.define("Use skill points costs array", false);
   private static final ConfigValue<List<? extends Integer>> SKILL_POINTS_COSTS = BUILDER.defineList("Levelup costs", generateDefaultPointsCosts(), o -> {
      if (o instanceof Integer i && i > 0) {
         return true;
      }

      return false;
   });
   private static final ConfigValue<List<? extends String>> SOCKET_BLACKLIST = BUILDER.defineListAllowEmpty(
      List.of("IDs of items that shouldn't have sockets"), ArrayList::new, Config::validateItemName
   );
   public static final int DEFAULT_MAX_SKILLS = 85;
   public static int max_skill_points;
   public static int first_skill_cost;
   public static int last_skill_cost;
   public static int default_helmet_sockets;
   public static int default_chestplate_sockets;
   public static int default_leggings_sockets;
   public static int default_boots_sockets;
   public static int default_weapon_sockets;
   public static int default_shield_sockets;
   public static int default_necklace_sockets;
   public static int default_ring_sockets;
   public static double gem_drop_chance;
   public static double amnesia_scroll_penalty;
   public static double grindstone_exp_multiplier;
   public static double mixture_effects_duration;
   public static double mixture_effects_strength;
   public static boolean show_chat_messages;
   public static boolean use_skill_points_array;
   public static boolean enable_exp_exchange;
   public static boolean dragon_drops_amnesia_scroll;
   public static List<? extends Integer> skill_points_costs;
   public static List<? extends String> socket_blacklist;

   static List<Integer> generateDefaultPointsCosts() {
      List<Integer> costs = new ArrayList<>();
      costs.add(15);

      for (int i = 1; i < 85; i++) {
         int previousCost = costs.get(costs.size() - 1);
         int cost = previousCost + 3 + i;
         costs.add(cost);
      }

      return costs;
   }

   static boolean validateItemName(Object object) {
      if (object instanceof String name && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(name))) {
         return true;
      }

      return false;
   }

   @SubscribeEvent
   static void load(ModConfigEvent event) {
      if (event.getConfig().getSpec() == SPEC) {
         skill_points_costs = (List<? extends Integer>)SKILL_POINTS_COSTS.get();
         use_skill_points_array = (Boolean)USE_POINTS_COSTS_ARRAY.get();
         max_skill_points = (Integer)MAX_SKILL_POINTS.get();
         first_skill_cost = (Integer)FIRST_SKILL_COST.get();
         last_skill_cost = (Integer)LAST_SKILL_COST.get();
         default_helmet_sockets = (Integer)DEFAULT_HELMET_SOCKETS.get();
         default_chestplate_sockets = (Integer)DEFAULT_CHESTPLATE_SOCKETS.get();
         default_leggings_sockets = (Integer)DEFAULT_LEGGINGS_SOCKETS.get();
         default_boots_sockets = (Integer)DEFAULT_BOOTS_SOCKETS.get();
         default_weapon_sockets = (Integer)DEFAULT_WEAPON_SOCKETS.get();
         default_shield_sockets = (Integer)DEFAULT_SHIELD_SOCKETS.get();
         default_necklace_sockets = (Integer)DEFAULT_NECKLACE_SOCKETS.get();
         default_ring_sockets = (Integer)DEFAULT_RING_SOCKETS.get();
         gem_drop_chance = (Double)GEM_DROP_CHANCE.get();
         amnesia_scroll_penalty = (Double)AMNESIA_SCROLL_PENALTY.get();
         grindstone_exp_multiplier = (Double)GRINDSTONE_EXP_MULTIPLIER.get();
         show_chat_messages = (Boolean)SHOW_CHAT_MESSAGES.get();
         enable_exp_exchange = (Boolean)ENABLE_EXP_EXCHANGE.get();
         dragon_drops_amnesia_scroll = (Boolean)DRAGON_DROPS_AMNESIA_SCROLL.get();
         socket_blacklist = (List<? extends String>)SOCKET_BLACKLIST.get();
         mixture_effects_duration = (Double)MIXTURE_EFFECTS_DURATION.get();
         mixture_effects_strength = (Double)MIXTURE_EFFECTS_STRENGTH.get();
      }
   }

   public static int getSkillPointCost(int level) {
      if (use_skill_points_array) {
         return level >= skill_points_costs.size() ? skill_points_costs.get(skill_points_costs.size() - 1) : skill_points_costs.get(level);
      } else {
         return first_skill_cost + (last_skill_cost - first_skill_cost) * level / max_skill_points;
      }
   }

   static {
      BUILDER.push("Skill Points");
      BUILDER.comment("You can set cost for each skill point instead");
      BUILDER.comment("This list's size must be equal to maximum skill points.");
      BUILDER.comment("Disabling this will remove chat messages when you gain a skill point.");
      BUILDER.comment("Warning: If you disable this make sure you make alternative way of getting skill points.");
      BUILDER.pop();
      BUILDER.push("Gems");
      BUILDER.pop();
      BUILDER.push("Equipment");
      BUILDER.comment("You can remove sockets from items here");
      BUILDER.comment("Example: Blacklisting specific items: [\"minecraft:diamond_hoe\", \"minecraft:golden_hoe\"]");
      BUILDER.comment("Example: Blacklisting whole mod: [\"<mod_id>:*\"]");
      BUILDER.comment("Example: Blacklisting all items: [\"*:*\"]");
      BUILDER.comment("You can force items from other mods into equipmentType categories here");
      BUILDER.pop();
      BUILDER.push("Amnesia Scroll");
      BUILDER.comment("How much levels (percentage) player lose using amnesia scroll");
      BUILDER.pop();
      BUILDER.push("Experience");
      BUILDER.pop();
      BUILDER.push("Mixtures");
      BUILDER.pop();
   }
}
