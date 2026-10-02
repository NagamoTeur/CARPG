package com.aqutheseal.celestisynth.manager;

import com.aqutheseal.celestisynth.config.client.CSClientConfig;
import com.aqutheseal.celestisynth.config.common.CSCommonConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

public final class CSConfigManager {
   public static final ForgeConfigSpec COMMON_SPEC;
   public static final CSCommonConfig COMMON;
   public static final ForgeConfigSpec CLIENT_SPEC;
   public static final CSClientConfig CLIENT;

   protected static void registerConfigs() {
      registerConfigFolder();
      registerClientConfig();
      registerCommonConfig();
      registerServerConfig();
   }

   private static void registerClientConfig() {
      ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT_SPEC, "celestisynth/celestisynth-client.toml");
   }

   private static void registerCommonConfig() {
      ModLoadingContext.get().registerConfig(Type.COMMON, COMMON_SPEC, "celestisynth/celestisynth-common.toml");
   }

   private static void registerServerConfig() {
   }

   private static void registerConfigFolder() {
      FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve("celestisynth"), "celestisynth");
   }

   static {
      Pair<CSCommonConfig, ForgeConfigSpec> commonSpecPair = new Builder().configure(CSCommonConfig::new);
      Pair<CSClientConfig, ForgeConfigSpec> clientSpecPair = new Builder().configure(CSClientConfig::new);
      COMMON_SPEC = (ForgeConfigSpec)commonSpecPair.getRight();
      COMMON = (CSCommonConfig)commonSpecPair.getLeft();
      CLIENT_SPEC = (ForgeConfigSpec)clientSpecPair.getRight();
      CLIENT = (CSClientConfig)clientSpecPair.getLeft();
   }
}
