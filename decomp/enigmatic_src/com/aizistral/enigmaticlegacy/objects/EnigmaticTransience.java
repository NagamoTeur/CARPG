package com.aizistral.enigmaticlegacy.objects;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.annotation.Nullable;

public class EnigmaticTransience {
   private boolean isCursed;
   private boolean isPermanentlyDead;

   private EnigmaticTransience() {
   }

   public boolean isCursed() {
      return this.isCursed;
   }

   public void setCursed(boolean isCursed) {
      this.isCursed = isCursed;
   }

   public boolean isPermanentlyDead() {
      return OmniconfigHandler.maxSoulCrystalLoss.getValue() >= 10 && this.isPermanentlyDead;
   }

   public void setPermanentlyDead(boolean isPermanentlyDead) {
      this.isPermanentlyDead = isPermanentlyDead;
   }

   public void write(File directory) {
      if (directory.exists() && directory.isDirectory()) {
         try {
            try (FileWriter writer = new FileWriter(new File(directory, "enigmatic_transience.json"))) {
               Gson gson = new GsonBuilder().setPrettyPrinting().create();
               gson.toJson(this, writer);
            }
         } catch (Exception var7) {
            throw new RuntimeException(var7);
         }
      } else {
         throw new IllegalArgumentException("Directory " + directory + " does not exist or is not a folder!");
      }
   }

   public static EnigmaticTransience read(File directory) {
      if (directory.exists() && directory.isDirectory()) {
         File file = new File(directory, "enigmatic_transience.json");
         if (file.exists() && file.isFile()) {
            FileReader reader = null;

            EnigmaticTransience var5;
            try {
               reader = new FileReader(file);
               return (EnigmaticTransience)new Gson().fromJson(reader, EnigmaticTransience.class);
            } catch (IOException var10) {
               throw new RuntimeException(var10);
            } catch (JsonSyntaxException var11) {
               EnigmaticLegacy.LOGGER.warn("Failed to read " + file + ", will regenerate...");
               close(reader);
               EnigmaticTransience transience = new EnigmaticTransience();
               transience.write(directory);
               var5 = transience;
            } finally {
               close(reader);
            }

            return var5;
         } else {
            return new EnigmaticTransience();
         }
      } else {
         throw new IllegalArgumentException("Directory " + directory + " does not exist or is not a folder!");
      }
   }

   private static void close(@Nullable Closeable closeable) {
      try {
         if (closeable != null) {
            closeable.close();
         }
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }
}
