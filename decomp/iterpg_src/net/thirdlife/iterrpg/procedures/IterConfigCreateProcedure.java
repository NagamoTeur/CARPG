package net.thirdlife.iterrpg.procedures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
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
public class IterConfigCreateProcedure {
   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      execute();
   }

   public static void execute() {
      execute(null);
   }

   private static void execute(@Nullable Event event) {
      new File("");
      JsonObject mainjsonobject = new JsonObject();
      new JsonObject();
      new JsonObject();
      JsonObject mobs = new JsonObject();
      JsonObject generation = new JsonObject();
      JsonObject functions = new JsonObject();
      File configfile = new File(FMLPaths.GAMEDIR.get().toString() + "/config/iter_rpg/", File.separator + "iterpg.json");
      if (!configfile.exists()) {
         try {
            configfile.getParentFile().mkdirs();
            configfile.createNewFile();
         } catch (IOException var11) {
            var11.printStackTrace();
         }

         generation.addProperty("vases", true);
         generation.addProperty("spider_eggs", true);
         generation.addProperty("abyss_quartz", true);
         generation.addProperty("goblin_camps", true);
         generation.addProperty("sorrow_spire", true);
         generation.addProperty("geodes", true);
         generation.addProperty("spider_catacombs", true);
         generation.addProperty("generic_dungeons", true);
         mobs.addProperty("elementals", true);
         mobs.addProperty("demons", true);
         mobs.addProperty("scallops", true);
         mobs.addProperty("giant_spiders", true);
         mobs.addProperty("spiderlings", true);
         functions.addProperty("goblin_spawner_cycle_time", 12000);
         functions.addProperty("geodrill_time", 12000);
         mainjsonobject.add("generation", generation);
         mainjsonobject.add("mobs", mobs);
         mainjsonobject.add("functions", functions);
         Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();

         try {
            FileWriter fileWriter = new FileWriter(configfile);
            fileWriter.write(mainGSONBuilderVariable.toJson(mainjsonobject));
            fileWriter.close();
         } catch (IOException var10) {
            var10.printStackTrace();
         }
      }
   }
}
