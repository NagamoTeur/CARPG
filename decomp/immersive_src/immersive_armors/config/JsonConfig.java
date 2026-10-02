package immersive_armors.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import immersive_armors.config.configEntries.FloatConfigEntry;
import immersive_armors.config.configEntries.IntegerConfigEntry;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonConfig {
   public static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public int version = 0;

   int getVersion() {
      return 1;
   }

   public JsonConfig() {
      for (Field field : Config.class.getDeclaredFields()) {
         for (Annotation annotation : field.getAnnotations()) {
            try {
               if (annotation instanceof IntegerConfigEntry entry) {
                  field.setInt(this, entry.value());
               } else if (annotation instanceof FloatConfigEntry entry) {
                  field.setFloat(this, entry.value());
               }
            } catch (IllegalAccessException var11) {
               throw new RuntimeException(var11);
            }
         }
      }
   }

   public static File getConfigFile() {
      return new File("./config/immersive_armors.json");
   }

   public void save() {
      try (FileWriter writer = new FileWriter(getConfigFile())) {
         this.version = this.getVersion();
         writer.write(this.toJsonString());
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public String toJsonString() {
      return GSON.toJson(this);
   }

   public static Config fromJsonString(String string) {
      return (Config)GSON.fromJson(string, Config.class);
   }

   public static Config loadOrCreate() {
      if (getConfigFile().exists()) {
         try {
            Config var2;
            try (FileReader reader = new FileReader(getConfigFile())) {
               Config config = (Config)GSON.fromJson(reader, Config.class);
               if (config.version != config.getVersion()) {
                  config = new Config();
               }

               config.save();
               var2 = config;
            }

            return var2;
         } catch (Exception var5) {
            LOGGER.error("Failed to load Immersive Armors config! Default config is used for now. Delete the file to reset.");
            LOGGER.error(var5);
            return new Config();
         }
      } else {
         Config config = new Config();
         config.save();
         return config;
      }
   }
}
