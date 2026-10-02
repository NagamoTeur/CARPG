package dev.latvian.mods.kubejs;

import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.util.Properties;

public class CommonProperties {
   private static CommonProperties instance;
   private final Properties properties = new Properties();
   private boolean writeProperties;
   public boolean hideServerScriptErrors;
   public boolean serverOnly;
   public boolean announceReload;
   public String packMode;
   public boolean saveDevPropertiesInConfig;
   public boolean allowAsyncStreams;
   public boolean matchJsonRecipes;
   public boolean ignoreCustomUniqueRecipeIds;
   public boolean startupErrorGUI;
   public String startupErrorReportUrl;

   public static CommonProperties get() {
      if (instance == null) {
         instance = new CommonProperties();
      }

      return instance;
   }

   public static void reload() {
      instance = new CommonProperties();
   }

   private CommonProperties() {
      try {
         this.writeProperties = false;
         if (Files.exists(KubeJSPaths.COMMON_PROPERTIES)) {
            try (BufferedReader reader = Files.newBufferedReader(KubeJSPaths.COMMON_PROPERTIES)) {
               this.properties.load(reader);
            }
         } else {
            this.writeProperties = true;
         }

         this.hideServerScriptErrors = this.get("hideServerScriptErrors", false);
         this.serverOnly = this.get("serverOnly", false);
         this.announceReload = this.get("announceReload", true);
         this.packMode = this.get("packmode", "");
         this.saveDevPropertiesInConfig = this.get("saveDevPropertiesInConfig", false);
         this.allowAsyncStreams = this.get("allowAsyncStreams", true);
         this.matchJsonRecipes = this.get("matchJsonRecipes", true);
         this.ignoreCustomUniqueRecipeIds = this.get("ignoreCustomUniqueRecipeIds", false);
         this.startupErrorGUI = this.get("startupErrorGUI", true);
         this.startupErrorReportUrl = this.get("startupErrorReportUrl", "");
         KubeJSPlugins.forEachPlugin(p -> p.loadCommonProperties(this));
         if (this.writeProperties) {
            this.save();
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      KubeJS.LOGGER.info("Loaded common.properties");
   }

   public void remove(String key) {
      String s = this.properties.getProperty(key);
      if (s != null) {
         this.properties.remove(key);
         this.writeProperties = true;
      }
   }

   public String get(String key, String def) {
      String s = this.properties.getProperty(key);
      if (s == null) {
         this.properties.setProperty(key, def);
         this.writeProperties = true;
         return def;
      } else {
         return s;
      }
   }

   public boolean get(String key, boolean def) {
      return this.get(key, def ? "true" : "false").equals("true");
   }

   public void save() {
      try (BufferedWriter writer = Files.newBufferedWriter(KubeJSPaths.COMMON_PROPERTIES)) {
         this.properties.store(writer, "KubeJS Common Properties");
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   public void setPackMode(String s) {
      this.packMode = s;
      this.properties.setProperty("packmode", s);
      this.save();
   }
}
