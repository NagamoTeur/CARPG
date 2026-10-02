package com.aizistral.enigmaticlegacy.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraftforge.fml.loading.FMLPaths;

public class JsonConfigHandler {
   @Nullable
   public static File getConfigFile() {
      try {
         return new File(FMLPaths.CONFIGDIR.get().toFile().getCanonicalFile(), "config.json");
      } catch (Exception var1) {
         return null;
      }
   }

   public static int getInt(String key) {
      return (int)getFloat(key);
   }

   public static float getFloat(String key) {
      try {
         InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(new FileInputStream(getConfigFile())), StandardCharsets.UTF_8);
         GsonBuilder builder = new GsonBuilder();
         Gson gson = builder.create();
         HashMap<String, Double> panelsParameters = (HashMap<String, Double>)gson.fromJson(reader, HashMap.class);
         return panelsParameters.containsKey(key) ? (float)panelsParameters.get(key).doubleValue() : 0.0F;
      } catch (Exception var5) {
         return 0.0F;
      }
   }

   public static String getString(String key) {
      try {
         InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(new FileInputStream(getConfigFile())), StandardCharsets.UTF_8);
         GsonBuilder builder = new GsonBuilder();
         Gson gson = builder.create();
         HashMap<String, String> panelsParameters = (HashMap<String, String>)gson.fromJson(reader, HashMap.class);
         return panelsParameters.containsKey(key) ? panelsParameters.get(key) : "";
      } catch (Exception var5) {
         return "";
      }
   }
}
