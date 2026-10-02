package net.thirdlife.iterrpg.procedures;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class GoblinSpawnerCycleConfigProcedure {
   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      execute();
   }

   public static double execute() {
      return execute(null);
   }

   private static double execute(@Nullable Event event) {
      new File("");
      new JsonObject();
      new JsonObject();
      new JsonObject();
      new JsonObject();
      new JsonObject();
      new JsonObject();
      new JsonObject();
      boolean flag = false;
      double number = 0.0;
      File configfile = new File(FMLPaths.GAMEDIR.get().toString() + "/config/iter_rpg/", File.separator + "iterpg.json");
      number = 12000.0;
      if (configfile.exists()) {
         try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configfile));
            StringBuilder jsonstringbuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
               jsonstringbuilder.append(line);
            }

            bufferedReader.close();
            JsonObject mainjsonobject = (JsonObject)new Gson().fromJson(jsonstringbuilder.toString(), JsonObject.class);
            JsonObject checkfor = mainjsonobject.get("functions").getAsJsonObject();
            number = checkfor.get("goblin_spawner_cycle_time").getAsDouble();
         } catch (IOException var15) {
            var15.printStackTrace();
         }
      }

      return number;
   }
}
