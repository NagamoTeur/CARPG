package com.hollingsworth.arsnouveau.setup.reward;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class Rewards {
   public static List<Rewards.ContributorStarby> starbuncles = new ArrayList<>();
   public static List<UUID> CONTRIBUTORS = new ArrayList<>();

   public static void init() {
      try {
         JsonObject object = JsonParser.parseString(readUrl(new URL("https://raw.githubusercontent.com/baileyholl/Ars-Nouveau/main/supporters.json")))
            .getAsJsonObject();

         for (JsonElement element : object.getAsJsonArray("uuids")) {
            String uuid = element.getAsString();
            CONTRIBUTORS.add(UUID.fromString(uuid.trim()));
         }

         for (JsonElement element : object.getAsJsonArray("starbuncleAdoptions")) {
            JsonObject jsonObject = element.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String adopter = jsonObject.get("adopter").getAsString();
            String color = jsonObject.get("color").getAsString();
            String bio = jsonObject.get("bio").getAsString();
            starbuncles.add(new Rewards.ContributorStarby(name, adopter, color, bio));
         }
      } catch (IOException var10) {
         var10.printStackTrace();
         if (!FMLEnvironment.production) {
            throw new RuntimeException("Failed to load supporters.json");
         }
      }
   }

   public static String readUrl(URL url) throws IOException {
      BufferedReader reader = null;

      String var6;
      try {
         reader = new BufferedReader(new InputStreamReader(url.openStream()));
         StringBuffer buffer = new StringBuffer();
         char[] chars = new char[1024];

         int read;
         while ((read = reader.read(chars)) != -1) {
            buffer.append(chars, 0, read);
         }

         String var5 = buffer.toString();
         var6 = var5;
      } finally {
         if (reader != null) {
            reader.close();
         }
      }

      return var6;
   }

   public static class ContributorStarby {
      public String name;
      public String adopter;
      public String color;
      public String bio;

      public ContributorStarby(String name, String adopter, String color, String bio) {
         this.name = name;
         this.adopter = adopter;
         this.color = color;
         this.bio = bio;
         if (!FMLEnvironment.production) {
            if (name == null) {
               throw new RuntimeException("Name is null");
            }

            if (adopter == null) {
               throw new RuntimeException("Adopter is null");
            }

            if (color == null) {
               throw new RuntimeException("Color is null");
            }

            if (bio == null) {
               throw new RuntimeException("Bio is null");
            }

            boolean foundColor = Arrays.stream(DyeColor.values()).anyMatch(dye -> dye.m_41065_().equals(color));
            if (!foundColor) {
               throw new RuntimeException("Color is not a valid dye color");
            }
         }
      }
   }
}
