package net.xylonity.knightquest.common.api.explosiveenhancement;

import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraftforge.fml.loading.FMLPaths;

public class ExplosiveValues {
   static Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("explosiveenhancement.toml");
   private static final boolean V = Files.exists(CONFIG_PATH);
   public static boolean showBlastWave = V ? (Boolean)ExplosiveEnhancementConfig.showBlastWave.get() : true;
   public static boolean showFireball = V ? (Boolean)ExplosiveEnhancementConfig.showFireball.get() : true;
   public static boolean showMushroomCloud = V ? (Boolean)ExplosiveEnhancementConfig.showMushroomCloud.get() : true;
   public static boolean showSparks = V ? (Boolean)ExplosiveEnhancementConfig.showSparks.get() : true;
   public static double sparkSize = V ? (Double)ExplosiveEnhancementConfig.sparkSize.get() : 5.3;
   public static double sparkOpacity = V ? (Double)ExplosiveEnhancementConfig.sparkOpacity.get() : 0.7;
   public static boolean showShockwave = V ? (Boolean)ExplosiveEnhancementConfig.showShockwave.get() : true;
   public static boolean showUnderwaterBlastWave = V ? (Boolean)ExplosiveEnhancementConfig.showUnderwaterBlastWave.get() : true;
   public static int bubbleAmount = V ? (Integer)ExplosiveEnhancementConfig.bubbleAmount.get() : 50;
   public static boolean showUnderwaterSparks = V ? (Boolean)ExplosiveEnhancementConfig.showUnderwaterSparks.get() : false;
   public static double underwaterSparkSize = V ? (Double)ExplosiveEnhancementConfig.underwaterSparkSize.get() : 4.0;
   public static double underwaterSparkOpacity = V ? (Double)ExplosiveEnhancementConfig.underwaterSparkOpacity.get() : 0.3;
   public static boolean dynamicSize = V ? (Boolean)ExplosiveEnhancementConfig.dynamicSize.get() : true;
   public static boolean dynamicUnderwater = V ? (Boolean)ExplosiveEnhancementConfig.dynamicUnderwater.get() : true;
   public static boolean attemptBetterSmallExplosions = V ? (Boolean)ExplosiveEnhancementConfig.attemptBetterSmallExplosions.get() : true;
   public static double smallExplosionYOffset = V ? (Double)ExplosiveEnhancementConfig.smallExplosionYOffset.get() : -0.5;
   public static boolean emissiveExplosion = V ? (Boolean)ExplosiveEnhancementConfig.emissiveExplosion.get() : true;
   public static boolean emissiveWaterExplosion = V ? (Boolean)ExplosiveEnhancementConfig.emissiveWaterExplosion.get() : true;
   public static boolean alwaysShow = V ? (Boolean)ExplosiveEnhancementConfig.alwaysShow.get() : false;
}
