package net.xylonity.knightquest.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class KnightQuestCommonConfigs {
   public static final Builder BUILDER = new Builder();
   public static final ForgeConfigSpec SPEC = BUILDER.build();
   public static final IntValue REQUIRED_ARMOR_PIECES = BUILDER.defineInRange("Required armor pieces to apply a full-set bonus effect", 4, 1, 4);
   public static final BooleanValue POISON_ELDKNIGHT = BUILDER.define("Should do the poison passive attack", true);
   public static final IntValue NUM_ELDBOMB_ELDKNIGHT = BUILDER.defineInRange("Number of Eld Bombs generated at half hp", 3, 0, 6);
   public static final DoubleValue HEAL_ELDKNIGHT = BUILDER.defineInRange("Quantity of healing each 4 seconds", 3.0, 0.0, 20.0);
   public static final DoubleValue DROP_CHANCE_RATMAN_EYE = BUILDER.defineInRange("Drop chance for ratman eye", 0.4, 0.0, 1.0);
   public static final DoubleValue DROP_CHANCE_LIZZY_SCALE = BUILDER.defineInRange("Drop chance for lizzy scale", 0.3, 0.0, 1.0);
   public static final DoubleValue INVULNERABILITY_RADIUS_GHOSTY = BUILDER.defineInRange("Ghosty invulnerability radius", 7.0, 0.0, 25.0);
   public static final BooleanValue CAN_TAKE_GOLD_GREMLIN = BUILDER.define("Can take gold from a player", true);
   public static final DoubleValue MULTIPLIER_GREMLIN_MOVEMENT_SPEED = BUILDER.defineInRange("Second phase movement speed multipler", 1.1, 1.0, 10.0);
   public static final DoubleValue MULTIPLIER_GREMLIN_ATTACK_SPEED = BUILDER.defineInRange("Second phase attack speed multipler", 1.15, 1.0, 10.0);
   public static final DoubleValue MULTIPLIER_GREMLIN_ATTACK_DAMAGE = BUILDER.defineInRange("Second phase attack damage multipler", 1.2, 1.0, 10.0);
   public static final DoubleValue PHASE_2_HEALING_SWAMPMAN = BUILDER.defineInRange("Amount of healing per second on second phase", 0.0, 0.0, 20.0);
   public static final BooleanValue CAN_CHANGE_PHASE_SWAMPMAN = BUILDER.define("Can change phase", true);
   public static final BooleanValue POISON_PHASE_2_SWAMPMAN = BUILDER.define("Should axe throwables apply poison effect", false);
   public static final DoubleValue NETHERMAN_HEALTH = BUILDER.defineInRange("How much health should the Netherman spawn with?", 450.0, 100.0, 2000.0);
   public static final DoubleValue NETHERMAN_DAMAGE = BUILDER.defineInRange("Amount of damage dealt per normal hit", 16.0, 6.0, 50.0);
   public static final BooleanValue TELEPORT_ON_HIT = BUILDER.define("Should the Netherman teleport when hit?", true);
   public static final IntValue FIRE_ATTACK_MIN_TIME = BUILDER.defineInRange("Minimum time for fire attack (seconds)", 3, 0, 30);
   public static final IntValue FIRE_ATTACK_MAX_TIME = BUILDER.defineInRange("Maximum time for fire attack (seconds)", 7, 0, 60);
   public static final IntValue MAX_NETHERMAN_CLONES = BUILDER.defineInRange("Maximum number of Netherman clones that can spawn per attack", 4, 0, 10);
   public static final IntValue ICE_ATTACK_FREEZE_TICKS = BUILDER.defineInRange("Freeze ticks applied by ice attack", 300, 0, 2000);
   public static final IntValue DARKNESS_ATTACK_MIN_TIME = BUILDER.defineInRange("Minimum time for darkness attack (seconds)", 3, 0, 30);
   public static final IntValue DARKNESS_ATTACK_MAX_TIME = BUILDER.defineInRange("Maximum time for darkness attack (seconds)", 7, 0, 60);
   public static final IntValue CLONE_EXPLOSION_FREEZE_TICKS = BUILDER.defineInRange("Ticks of freeze applied by Netherman clone explosions", 200, 0, 2000);
   public static final DoubleValue NETHERMAN_PROJECTILE_EXPLOSION_RADIUS = BUILDER.defineInRange("Explosion radius of Netherman projectiles", 3.0, 0.0, 10.0);
   public static final BooleanValue RESTORE_BLOCKS_POST_DEATH = BUILDER.define(
      "Should it restore blocks converted to lava back to their original state after dying?", true
   );
   public static final IntValue EXPERIENCE_DROP_AMOUNT = BUILDER.defineInRange("Amount of experience dropped upon death", 500, 0, 3000);
   public static final BooleanValue ENABLE_BAMBOOSET_PUSH_PLAYERS = BUILDER.define("Should Bamboo Set push players?", false);
   public static final IntValue TELEPORT_RADIUS_ENDERMANSET = BUILDER.defineInRange("Teleport radius for Enderman Set", 10, 5, 30);
   public static final DoubleValue CHANCE_ENDERMANSET = BUILDER.defineInRange("Teleport chance for Enderman Set", 0.4, 0.1, 1.0);
   public static final DoubleValue FORZESET_DEFLECT_CHANCE = BUILDER.defineInRange("Chance for Forze Set to deflect", 0.3, 0.1, 1.0);
   public static final DoubleValue FORZESET_DEFLECT_DAMAGE = BUILDER.defineInRange("Damage multiplier for Forze Set deflection", 0.5, 0.1, 2.0);
   public static final DoubleValue SILVERSET_BURN_CHANCE = BUILDER.defineInRange("Chance for Silver Set to burn", 0.3, 0.1, 1.0);
   public static final DoubleValue HOLLOWSET_HEALING_MULTIPLIER = BUILDER.defineInRange(
      "Healing multiplier per hit for Hollow Set (healing won't be higher than victim's health)", 0.25, 0.1, 2.0
   );
   public static final DoubleValue DRAGONSET_DAMAGE_MULTIPLIER = BUILDER.defineInRange("Damage multiplier for Dragon Set", 1.15, 1.0, 2.0);
   public static final DoubleValue WITHERSET_WITHER_CHANCE = BUILDER.defineInRange("Chance of applying Wither with Wither Set", 0.3, 0.1, 1.0);
   public static final BooleanValue SHOULD_WARLORD_SET_EFFECT_APPLY_TO_ITSELF = BUILDER.define("Should Warlord Set effect apply to itself?", false);
   public static final IntValue WARLORD_SET_EFFECT_RADIUS = BUILDER.defineInRange("Effect radius for Warlord Set", 15, 1, 40);
   public static final DoubleValue ZOMBIESET_HEALING_AMOUNT = BUILDER.defineInRange("Healing amount for Zombie Set", 1.0, 1.0, 10.0);
   public static final IntValue ZOMBIESET_HEALING_TICKS = BUILDER.defineInRange("Time in ticks for Zombie Set healing interval", 120, 1, 1000);
   public static final DoubleValue DEEPSLATE_FALL_DAMAGE_MULTIPLIER = BUILDER.defineInRange("Fall damage multiplier for Deepslate Set", 0.2, 0.0, 1.0);
   public static final DoubleValue EVOKER_DARKNESS_CHANCE = BUILDER.defineInRange("Chance to apply Darkness for Evoker Set", 0.25, 0.0, 1.0);
   public static final DoubleValue SQUIRE_DAMAGE_RECEIVED_MULTIPLIER = BUILDER.defineInRange("Damage received multiplier for Squire Set", 0.85, 0.0, 1.0);
   public static final DoubleValue BLAZE_FIRE_CHANCE = BUILDER.defineInRange("Chance to apply Fire for Blaze Set", 0.4, 0.0, 1.0);
   public static final IntValue BLAZE_FIRE_DURATION_MIN = BUILDER.defineInRange("Minimum seconds on fire for Blaze Set", 2, 1, 100);
   public static final IntValue BLAZE_FIRE_DURATION_MAX = BUILDER.defineInRange("Maximum seconds on fire for Blaze Set", 8, 1, 200);
   public static final DoubleValue CREEPER_EXPLOSION_DAMAGE_MULTIPLIER = BUILDER.defineInRange("Explosion damage multiplier for Creeper Set", 0.1, 0.0, 1.0);
   public static final IntValue SILVERFISH_EFFECT_MAX_HEIGHT = BUILDER.defineInRange("Maximum height to apply effect for Silverfish Set", 50, 0, 100);
   public static final IntValue SKULK_MAX_LIGHT_LEVEL = BUILDER.defineInRange("Maximum light level to grant effect for Skulk Set", 4, 0, 15);
   public static final BooleanValue ENABLE_DEEPSLATESET = BUILDER.define("Enable Deepslate Set Passive", true);
   public static final BooleanValue ENABLE_EVOKERSET = BUILDER.define("Enable Evoker Set Passive", true);
   public static final BooleanValue ENABLE_SQUIRESET = BUILDER.define("Enable Squire Set Passive", true);
   public static final BooleanValue ENABLE_BLAZESET = BUILDER.define("Enable Blaze Set Passive", true);
   public static final BooleanValue ENABLE_DRAGONSET = BUILDER.define("Enable Dragon Set Passive", true);
   public static final BooleanValue ENABLE_BAMBOOSET_GREEN = BUILDER.define("Enable Bamboo Set Green Passive", true);
   public static final BooleanValue ENABLE_SHINOBI = BUILDER.define("Enable Shinobi Set Passive", true);
   public static final BooleanValue ENABLE_BAMBOOSET = BUILDER.define("Enable Bamboo Set Passive", true);
   public static final BooleanValue ENABLE_PATHSET = BUILDER.define("Enable Path Set Passive", true);
   public static final BooleanValue ENABLE_BOWSET = BUILDER.define("Enable Bow Set Passive", true);
   public static final BooleanValue ENABLE_BATSET = BUILDER.define("Enable Bat Set Passive", true);
   public static final BooleanValue ENABLE_SHIELDSET = BUILDER.define("Enable Shield Set Passive", true);
   public static final BooleanValue ENABLE_PHANTOMSET = BUILDER.define("Enable Phantom Set Passive", true);
   public static final BooleanValue ENABLE_HORNSET = BUILDER.define("Enable Horn Set Passive", true);
   public static final BooleanValue ENABLE_SEASET = BUILDER.define("Enable Sea Set Passive", true);
   public static final BooleanValue ENABLE_PIRATESET = BUILDER.define("Enable Pirate Set Passive", true);
   public static final BooleanValue ENABLE_SPIDERSET = BUILDER.define("Enable Spider Set Passive", true);
   public static final BooleanValue ENABLE_NETHERSET = BUILDER.define("Enable Nether Set Passive", true);
   public static final BooleanValue ENABLE_SKULK = BUILDER.define("Enable Skulk Passive", true);
   public static final BooleanValue ENABLE_STRAWHATSET = BUILDER.define("Enable Straw Hat Set Passive", true);
   public static final BooleanValue ENABLE_ENDERMANSET = BUILDER.define("Enable Enderman Set Passive", true);
   public static final BooleanValue ENABLE_VETERANSET = BUILDER.define("Enable Veteran Set Passive", true);
   public static final BooleanValue ENABLE_FORZESET = BUILDER.define("Enable Forze Set Passive", true);
   public static final BooleanValue ENABLE_CREEPERSET = BUILDER.define("Enable Creeper Set Passive", true);
   public static final BooleanValue ENABLE_POLAR = BUILDER.define("Enable Polar Passive", true);
   public static final BooleanValue ENABLE_SILVERSET = BUILDER.define("Enable Silver Set Passive", true);
   public static final BooleanValue ENABLE_HOLLOWSET = BUILDER.define("Enable Hollow Set Passive", true);
   public static final BooleanValue ENABLE_WITHERSET = BUILDER.define("Enable Wither Set Passive", true);
   public static final BooleanValue ENABLE_APPLE_SET = BUILDER.define("Enable Apple Set Passive", true);
   public static final BooleanValue ENABLE_CONQUISTADORSET = BUILDER.define("Enable Conquistador Set Passive", true);
   public static final BooleanValue ENABLE_WITCH = BUILDER.define("Enable Witch Passive", true);
   public static final BooleanValue ENABLE_TENGU_HELMET = BUILDER.define("Enable Tengu Helmet Passive", true);
   public static final BooleanValue ENABLE_HUSKSET = BUILDER.define("Enable Husk Set Passive", true);
   public static final BooleanValue ENABLE_BAMBOOSET_BLUE = BUILDER.define("Enable Bamboo Set Blue Passive", true);
   public static final BooleanValue ENABLE_WARLORDSET = BUILDER.define("Enable Warlord Set Passive", true);
   public static final BooleanValue ENABLE_ZOMBIESET = BUILDER.define("Enable Zombie Set Passive", true);
   public static final BooleanValue ENABLE_SILVERFISHSET = BUILDER.define("Enable Silverfish Set Passive", true);
   public static final BooleanValue ENABLE_SKELETONSET = BUILDER.define("Enable Skeleton Set Passive", true);
   public static final BooleanValue ENABLE_CLEAVER = BUILDER.define("Enable Cleaver weapon abilities", true);
   public static final BooleanValue ENABLE_KHOPESH = BUILDER.define("Enable Khopesh weapon abilities", true);
   public static final BooleanValue ENABLE_KUKRI = BUILDER.define("Enable Kukri weapon abilities", true);
   public static final BooleanValue ENABLE_NAIL = BUILDER.define("Enable Nail weapon abilities", true);
   public static final BooleanValue ENABLE_PALADIN = BUILDER.define("Enable Paladin weapon abilities", true);
   public static final BooleanValue ENABLE_UCHIGATANA = BUILDER.define("Enable Uchigatana weapon abilities", true);
   public static final IntValue COOLDOWN_CLEAVER = BUILDER.defineInRange("Cooldown of the Cleaver weapon when using its active ability", 1800, 0, 20000);
   public static final IntValue COOLDOWN_KHOPESH = BUILDER.defineInRange("Cooldown of the Khopesh weapon when using its active ability", 500, 0, 20000);
   public static final IntValue COOLDOWN_KUKRI = BUILDER.defineInRange("Cooldown of the Kukri weapon when using its active ability", 300, 0, 20000);
   public static final IntValue COOLDOWN_NAIL = BUILDER.defineInRange("Cooldown of the Nail weapon when using its active ability", 100, 0, 20000);
   public static final IntValue COOLDOWN_PALADIN = BUILDER.defineInRange("Cooldown of the Paladin weapon when using its active ability", 500, 0, 20000);
   public static final IntValue COOLDOWN_UCHIGATANA = BUILDER.defineInRange("Cooldown of the Uchigatana weapon when using its active ability", 400, 0, 20000);
   public static final IntValue SPEED_TICKS_KUKRI = BUILDER.defineInRange("Duration of the speed boost from the Kukri's active ability", 120, 0, 6000);
   public static final IntValue FREEZE_TICKS_KUKRI = BUILDER.defineInRange("Number of freeze ticks applied by the Kukri per hit", 125, 0, 10000);
   public static final IntValue INV_TICKS_PALADIN = BUILDER.defineInRange("Duration of invulnerability from the Paladin's active ability", 100, 0, 600);
   public static final DoubleValue DASH_POWER_NAIL = BUILDER.defineInRange(
      "Dash power of the nail (this should stay low unless you wanna go to the moon)", 1.5, 0.0, 50.0
   );
   public static final DoubleValue EXTRA_DAMAGE_UCHIGATANA = BUILDER.defineInRange(
      "Extra damage dealt by the Uchigatana when using its active ability", 0.6, 0.0, 4.0
   );
   public static final DoubleValue EXTRA_DAMAGE_PASSIVE_UCHIGATANA = BUILDER.defineInRange(
      "Extra damage dealt by the Uchigatana through its passive ability", 0.2, 0.0, 4.0
   );
   public static final DoubleValue ENEMY_HEALTH_PASSIVE_UCHIGATANA = BUILDER.defineInRange(
      "Maximum health the opponent can have for the Uchigatana's passive ability to take effect", 0.5, 0.0, 1.0
   );
   public static final IntValue REFLECTION_TIME_KHOPESH = BUILDER.defineInRange("Duration of the Khopesh's active ability (reflection)", 160, 0, 1000);
   public static final DoubleValue CHANCE_BURN_KHOPESH = BUILDER.defineInRange("Chance to burn the opponent with the Khopesh's passive ability", 0.15, 0.0, 1.0);
   public static final DoubleValue REGEN_MAX_PALADIN = BUILDER.defineInRange(
      "Maximum percentage of health that the Paladin's passive ability can regenerate", 0.5, 0.0, 1.0
   );
   public static final IntValue REGEN_TICKS_PALADIN = BUILDER.defineInRange(
      "Interval (in ticks) at which the Paladin's passive ability regenerates health", 30, 0, 400
   );
   public static final IntValue REGEN_HP_PALADIN = BUILDER.defineInRange("Amount of health restored by the Paladin's passive ability", 1, 0, 100);
   public static final IntValue TICKS_CLEAVER = BUILDER.defineInRange("Duration of the Cleaver's active ability", 600, 0, 4000);
   public static final DoubleValue EXTRA_DAMAGE_PASSIVE_CLEAVER = BUILDER.defineInRange(
      "Extra damage dealt by the Cleaver through its passive ability", 0.2, 0.0, 4.0
   );
   public static final DoubleValue ENEMY_HEALTH_PASSIVE_CLEAVER = BUILDER.defineInRange(
      "Minimum health the opponent must have for the Cleaver's passive ability to take effect", 0.5, 0.0, 1.0
   );

   static {
      BUILDER.push("General Configuration");
      BUILDER.comment(
         "The amount of armor pieces required to apply a set effect, which means that if this value is set to 1, you can equip 4 different armors pieces and receive the passive effects of each set."
      );
      BUILDER.pop();
      BUILDER.push("Weapon Configuration");
      BUILDER.comment("The following cooldowns and timings, per se, are measured in ticks (1 second = 20 ticks)");
      BUILDER.comment("Extra damage modifiers are measured as percentages: 0 means no extra damage, 1 means 100% extra base damage (resulting in 200% total).");
      BUILDER.pop();
      BUILDER.push("Eld Knight Configuration");
      BUILDER.pop();
      BUILDER.push("Ghosty Configuration");
      BUILDER.pop();
      BUILDER.push("Gremlin Configuration");
      BUILDER.pop();
      BUILDER.push("Swampman Configuration");
      BUILDER.pop();
      BUILDER.push("Netherman Configuration");
      BUILDER.comment("Random number between the interval stated below");
      BUILDER.pop();
      BUILDER.push("Drop Chance Configuration");
      BUILDER.comment("Drop chance for small essence must be changed inside knightlib.toml");
      BUILDER.pop();
      BUILDER.push("Armor Set Passives Configuration");
      BUILDER.pop();
      BUILDER.push("Armor Set Passives Enabler Configuration");
      BUILDER.pop();
      BUILDER.push("Weapon Enabler Configuration");
      BUILDER.pop();
   }
}
