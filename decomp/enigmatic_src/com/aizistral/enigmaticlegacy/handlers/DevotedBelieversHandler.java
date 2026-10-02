package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.FileUtils;

public final class DevotedBelieversHandler {
   private static final Map<String, UUID> DEVOTED_BELIEVERS = loadDevotedBelievers();

   public static Map<String, UUID> getDevotedBelievers() {
      return DEVOTED_BELIEVERS;
   }

   public static boolean isDevotedBeliever(Player player) {
      return isDevotedBeliever(player.m_20148_());
   }

   public static boolean isDevotedBeliever(UUID playerID) {
      return DEVOTED_BELIEVERS.values().contains(playerID);
   }

   private static Map<String, UUID> loadDevotedBelievers() {
      try {
         Map<String, UUID> map = fetchDevotedBelievers();
         EnigmaticLegacy.LOGGER.info("Successfully fetched the list of devoted believers from Enigmatic Legacy's repository.");

         try {
            File backup = new File(FMLPaths.GAMEDIR.get().toFile().getCanonicalFile(), "enigmatic_persistence.dat");
            backupDevotedBelievers(map, backup);
            EnigmaticLegacy.LOGGER.info("Successfully backed up the list of devoted believers.");
         } catch (IOException var4) {
            EnigmaticLegacy.LOGGER.error("Encountered exception when trying to backup devoted believers list:");
            EnigmaticLegacy.LOGGER.catching(var4);
         }

         return map;
      } catch (IOException var5) {
         EnigmaticLegacy.LOGGER.error("Encountered exception when trying to fetch devoted believers from Enigmatic Legacy's repository:");
         EnigmaticLegacy.LOGGER.catching(var5);

         try {
            File backup = new File(FMLPaths.GAMEDIR.get().toFile().getCanonicalFile(), "enigmatic_persistence.dat");
            if (backup.exists()) {
               EnigmaticLegacy.LOGGER.info("Will try to restore the list from local backup...");
               Map<String, UUID> map = restoreDevotedBelievers(backup);
               EnigmaticLegacy.LOGGER.info("Successfully restored the list from local backup.");
               return map;
            } else {
               EnigmaticLegacy.LOGGER.error("No backup found.");
               return new HashMap<>();
            }
         } catch (IOException var3) {
            EnigmaticLegacy.LOGGER.error("Restoration failed with exception:");
            EnigmaticLegacy.LOGGER.catching(var3);
            EnigmaticLegacy.LOGGER.error("We'll have to live without knowing the names of devoted believers for now.");
            return new HashMap<>();
         }
      }
   }

   private static Map<String, UUID> restoreDevotedBelievers(File file) throws IOException {
      List<String> lines = FileUtils.readLines(file, Charset.forName("UTF-8"));
      Map<String, UUID> map = new HashMap<>();

      try {
         lines.forEach(line -> {
            char[] chars = line.toCharArray();

            for (int i = 0; i < chars.length; i++) {
               chars[i] = (char)(chars[i] - 2 - i % 4);
            }

            line = new String(chars);
            String[] splat = line.split(Pattern.quote(":"));
            String name = splat[0];
            UUID uuid = UUID.fromString(splat[1]);
            map.put(name, uuid);
         });
         return map;
      } catch (Exception var4) {
         throw new RuntimeException("File " + file + " seems to have been corrupted! Try to delete it and restart the game.");
      }
   }

   private static void backupDevotedBelievers(Map<String, UUID> map, File file) throws IOException {
      StringBuilder backup = new StringBuilder(1000);
      map.entrySet().forEach(entry -> {
         if (backup.length() > 0) {
            backup.append(System.lineSeparator());
         }

         String line = entry.getKey() + ":" + entry.getValue();
         char[] chars = line.toCharArray();

         for (int i = 0; i < chars.length; i++) {
            chars[i] = (char)(chars[i] + 2 + i % 4);
         }

         backup.append(chars);
      });
      FileUtils.writeStringToFile(file, backup.toString(), Charset.forName("UTF-8"));
   }

   private static Map<String, UUID> fetchDevotedBelievers() throws IOException {
      try {
         String defaultBranch = fetchDefaultBranch();
         URL url = new URL("https://raw.githubusercontent.com/Aizistral-Studios/Enigmatic-Legacy/" + defaultBranch + "/devoted_believers.json");
         StringBuilder response = new StringBuilder(1000);

         try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
               response.append(scanner.next());
            }
         }

         Gson gson = new Gson();
         Map map = (Map)gson.fromJson(response.toString(), Map.class);
         HashMap finalMap = new HashMap();
         map.entrySet().forEach(entry -> finalMap.put((String)entry.getKey(), UUID.fromString((String)entry.getValue())));
         return finalMap;
      } catch (MalformedURLException var8) {
         throw new RuntimeException("Impossible!", var8);
      }
   }

   private static String fetchDefaultBranch() throws IOException {
      try {
         URL url = new URL("https://api.github.com/repos/Aizistral-Studios/Enigmatic-Legacy");
         StringBuilder response = new StringBuilder(10000);

         try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
               response.append(scanner.next());
            }
         }

         JsonObject obj = JsonParser.parseString(response.toString()).getAsJsonObject();
         return obj.get("default_branch").getAsString();
      } catch (MalformedURLException var7) {
         throw new RuntimeException("Impossible!", var7);
      }
   }
}
