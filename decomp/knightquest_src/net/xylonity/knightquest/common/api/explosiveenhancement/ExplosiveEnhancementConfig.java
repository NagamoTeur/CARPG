package net.xylonity.knightquest.common.api.explosiveenhancement;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ExplosiveEnhancementConfig {
   public static final Builder BUILDER = new Builder();
   public static final ForgeConfigSpec SPEC = BUILDER.build();
   public static final BooleanValue showBlastWave = BUILDER.comment("Show blast wave effects").define("showBlastWave", true);
   public static final BooleanValue showFireball = BUILDER.comment("Show fireball effects").define("showFireball", true);
   public static final BooleanValue showMushroomCloud = BUILDER.comment("Show mushroom cloud effects").define("showMushroomCloud", true);
   public static final BooleanValue showSparks = BUILDER.comment("Show sparks effects").define("showSparks", true);
   public static final DoubleValue sparkSize = BUILDER.comment("Size of sparks").defineInRange("sparkSize", 5.3, 0.0, Double.MAX_VALUE);
   public static final DoubleValue sparkOpacity = BUILDER.comment("Opacity of sparks").defineInRange("sparkOpacity", 0.7, 0.0, 1.0);
   public static final BooleanValue showDefaultExplosion = BUILDER.comment("Show default explosion effects").define("showDefaultExplosion", false);
   public static final BooleanValue underwaterExplosions = BUILDER.comment("Enable underwater explosions").define("underwaterExplosions", true);
   public static final BooleanValue showShockwave = BUILDER.comment("Show shockwave effects").define("showShockwave", true);
   public static final BooleanValue showUnderwaterBlastWave = BUILDER.comment("Show underwater blast wave effects").define("showUnderwaterBlastWave", true);
   public static final IntValue bubbleAmount = BUILDER.comment("Number of bubbles for underwater explosions")
      .defineInRange("bubbleAmount", 50, 0, Integer.MAX_VALUE);
   public static final BooleanValue showUnderwaterSparks = BUILDER.comment("Show underwater sparks effects").define("showUnderwaterSparks", false);
   public static final DoubleValue underwaterSparkSize = BUILDER.comment("Size of underwater sparks")
      .defineInRange("underwaterSparkSize", 4.0, 0.0, Double.MAX_VALUE);
   public static final DoubleValue underwaterSparkOpacity = BUILDER.comment("Opacity of underwater sparks")
      .defineInRange("underwaterSparkOpacity", 0.3, 0.0, 1.0);
   public static final BooleanValue showDefaultExplosionUnderwater = BUILDER.comment("Show default explosion effects underwater")
      .define("showDefaultExplosionUnderwater", false);
   public static final BooleanValue dynamicSize = BUILDER.comment("Enable dynamic explosion size").define("dynamicSize", true);
   public static final BooleanValue dynamicUnderwater = BUILDER.comment("Enable dynamic underwater explosion size").define("dynamicUnderwater", true);
   public static final BooleanValue attemptBetterSmallExplosions = BUILDER.comment("Attempt better small explosion effects")
      .define("attemptBetterSmallExplosions", true);
   public static final DoubleValue smallExplosionYOffset = BUILDER.comment("Y offset for small explosions")
      .defineInRange("smallExplosionYOffset", -0.5, -Double.MAX_VALUE, Double.MAX_VALUE);
   public static final BooleanValue modEnabled = BUILDER.comment("Enable the mod").define("modEnabled", true);
   public static final BooleanValue emissiveExplosion = BUILDER.comment("Enable emissive explosion effects").define("emissiveExplosion", true);
   public static final BooleanValue emissiveWaterExplosion = BUILDER.comment("Enable emissive water explosion effects").define("emissiveWaterExplosion", true);
   public static final BooleanValue alwaysShow = BUILDER.comment("Always show explosion effects").define("alwaysShow", false);
   public static final BooleanValue debugLogs = BUILDER.comment("Enable debug logs").define("debugLogs", false);

   static {
      BUILDER.push("Config file for Explosive Enhancement");
      BUILDER.pop();
   }
}
