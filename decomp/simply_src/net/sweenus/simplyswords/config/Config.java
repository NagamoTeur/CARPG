package net.sweenus.simplyswords.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Config {
   public static File createFile(String path, String contents, boolean overwrite) {
      File file = new File(path);
      if (file.exists() && !overwrite) {
         return file;
      } else {
         file.getParentFile().mkdirs();

         try {
            file.createNewFile();
         } catch (IOException var10) {
            var10.printStackTrace();
         }

         file.setReadable(true);
         file.setWritable(true);
         file.setExecutable(true);
         if (contents != null && !"".equals(contents)) {
            try (FileWriter writer = new FileWriter(file)) {
               writer.write(contents);
            } catch (IOException var9) {
               var9.printStackTrace();
            }

            return file;
         } else {
            return file;
         }
      }
   }

   public static String readFile(File file) {
      String output = "";

      try (Scanner scanner = new Scanner(file)) {
         scanner.useDelimiter("\\Z");
         output = scanner.next();
      } catch (FileNotFoundException var7) {
         var7.printStackTrace();
      }

      return output;
   }

   public static JsonObject getJsonObject(String json) {
      try {
         return new JsonParser().parse(json).getAsJsonObject();
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }
}
