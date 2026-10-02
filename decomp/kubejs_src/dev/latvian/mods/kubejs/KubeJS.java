package dev.latvian.mods.kubejs;

import com.google.common.base.Stopwatch;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.latvian.mods.kubejs.bindings.event.StartupEvents;
import dev.latvian.mods.kubejs.block.KubeJSBlockEventHandler;
import dev.latvian.mods.kubejs.client.KubeJSClient;
import dev.latvian.mods.kubejs.entity.KubeJSEntityEventHandler;
import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.gui.KubeJSMenu;
import dev.latvian.mods.kubejs.item.KubeJSItemEventHandler;
import dev.latvian.mods.kubejs.level.KubeJSWorldEventHandler;
import dev.latvian.mods.kubejs.net.KubeJSNet;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import dev.latvian.mods.kubejs.player.KubeJSPlayerEventHandler;
import dev.latvian.mods.kubejs.recipe.KubeJSRecipeEventHandler;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryEventJS;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.PlatformWrapper;
import dev.latvian.mods.kubejs.script.ScriptFileInfo;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptPack;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.ScriptsLoadedEvent;
import dev.latvian.mods.kubejs.script.data.GeneratedResourcePack;
import dev.latvian.mods.kubejs.server.KubeJSServerEventHandler;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.KubeJSBackgroundThread;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import dev.latvian.mods.kubejs.util.UtilsJS;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KubeJS {
   public static final String MOD_ID = "kubejs";
   public static final String MOD_NAME = "KubeJS";
   public static final Component NAME_COMPONENT = Component.m_237113_("KubeJS");
   public static final Logger LOGGER = LoggerFactory.getLogger("KubeJS");
   public static final int MC_VERSION_NUMBER = 1902;
   public static final String MC_VERSION_STRING = "1.19.2";
   public static String QUERY;
   public static KubeJS instance;
   private static Path gameDirectory;
   public static KubeJSCommon PROXY;
   public static CreativeModeTab tab = CreativeModeTab.f_40753_;
   private static ScriptManager startupScriptManager;
   private static ScriptManager clientScriptManager;
   public static Mod thisMod;

   public static ResourceLocation id(String path) {
      return new ResourceLocation("kubejs", path);
   }

   public static ScriptManager getStartupScriptManager() {
      return startupScriptManager;
   }

   public static ScriptManager getClientScriptManager() {
      return clientScriptManager;
   }

   public KubeJS() throws Throwable {
      instance = this;
      gameDirectory = Platform.getGameFolder().normalize().toAbsolutePath();
      if (Files.notExists(KubeJSPaths.README)) {
         try {
            Files.writeString(
               KubeJSPaths.README,
               "Find out more info on the website: https://kubejs.com/\n\nDirectory information:\n\nassets - Acts as a resource pack, you can put any client resources in here, like textures, models, etc. Example: assets/kubejs/textures/item/test_item.png\ndata - Acts as a datapack, you can put any server resources in here, like loot tables, functions, etc. Example: data/kubejs/loot_tables/blocks/test_block.json\n\nstartup_scripts - Scripts that get loaded once during game startup - Used for adding items and other things that can only happen while the game is loading (Can be reloaded with /kubejs reload_startup_scripts, but it may not work!)\nserver_scripts - Scripts that get loaded every time server resources reload - Used for modifying recipes, tags, loot tables, and handling server events (Can be reloaded with /reload)\nclient_scripts - Scripts that get loaded every time client resources reload - Used for JEI events, tooltips and other client side things (Can be reloaded with F3+T)\n\nconfig - KubeJS config storage. This is also the only directory that scripts can access other than world directory\nexported - Data dumps like texture atlases end up here\n\nYou can find type-specific logs in logs/kubejs/ directory\n"
                  .trim()
            );
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      PROXY = (KubeJSCommon)EnvExecutor.getEnvSpecific(() -> KubeJSClient::new, () -> KubeJSCommon::new);
      if (!MiscPlatformHelper.get().isDataGen()) {
         new KubeJSBackgroundThread().start();
      }

      Stopwatch pluginTimer = Stopwatch.createStarted();
      LOGGER.info("Looking for KubeJS plugins...");
      thisMod = Platform.getMod("kubejs");
      ArrayList<Mod> allMods = new ArrayList<>(Platform.getMods());
      allMods.remove(thisMod);
      allMods.add(0, thisMod);
      KubeJSPlugins.load(allMods, Platform.getEnvironment() == Env.CLIENT);
      LOGGER.info("Done in " + pluginTimer.stop());
      KubeJSPlugins.forEachPlugin(KubeJSPlugin::init);
      KubeJSPlugins.forEachPlugin(KubeJSPlugin::registerEvents);
      startupScriptManager = new ScriptManager(ScriptType.STARTUP, KubeJSPaths.STARTUP_SCRIPTS);
      clientScriptManager = new ScriptManager(ScriptType.CLIENT, KubeJSPaths.CLIENT_SCRIPTS);
      if (!CommonProperties.get().serverOnly) {
         tab = CreativeTabRegistry.create(new ResourceLocation("kubejs", "kubejs"), () -> new ItemStack(Items.f_42493_));
      }

      startupScriptManager.reload(null);
      KubeJSPlugins.forEachPlugin(KubeJSPlugin::initStartup);
      KubeJSWorldEventHandler.init();
      KubeJSPlayerEventHandler.init();
      KubeJSEntityEventHandler.init();
      KubeJSBlockEventHandler.init();
      KubeJSItemEventHandler.init();
      KubeJSServerEventHandler.init();
      KubeJSRecipeEventHandler.init();
      KubeJSMenu.init();
      PROXY.init();

      for (Object extraId : StartupEvents.REGISTRY.findUniqueExtraIds(ScriptType.STARTUP)) {
         if (extraId instanceof ResourceKey<?> key) {
            RegistryInfo info = RegistryInfo.of(UtilsJS.cast(key));
            RegistryEventJS event = new RegistryEventJS(info);
            StartupEvents.REGISTRY.post(event, key);
            event.created.forEach(BuilderBase::createAdditionalObjects);
         }
      }

      GeneratedResourcePack.scanForInvalidFiles("kubejs/assets/", KubeJSPaths.ASSETS);
      GeneratedResourcePack.scanForInvalidFiles("kubejs/data/", KubeJSPaths.DATA);
   }

   public static void loadScripts(ScriptPack pack, Path dir, String path) {
      if (!path.isEmpty() && !path.endsWith("/")) {
         path = path + "/";
      }

      String pathPrefix = path;

      try {
         for (Path file : Files.walk(dir, 10, FileVisitOption.FOLLOW_LINKS).filter(x$0 -> Files.isRegularFile(x$0)).toList()) {
            String fileName = dir.relativize(file).toString().replace(File.separatorChar, '/');
            if (fileName.endsWith(".js") || fileName.endsWith(".ts") && !fileName.endsWith(".d.ts")) {
               pack.info.scripts.add(new ScriptFileInfo(pack.info, pathPrefix + fileName));
            }
         }
      } catch (IOException var7) {
         var7.printStackTrace();
      }
   }

   public static String appendModId(String id) {
      return id.indexOf(58) == -1 ? "kubejs:" + id : id;
   }

   public static Path getGameDirectory() {
      return gameDirectory;
   }

   public static Path verifyFilePath(Path path) throws IOException {
      if (!path.normalize().toAbsolutePath().startsWith(gameDirectory)) {
         throw new IOException("You can't access files outside Minecraft directory!");
      } else {
         return path;
      }
   }

   public void setup() {
      KubeJSNet.init();
      StartupEvents.INIT.post(ScriptType.STARTUP, new StartupEventJS());
   }

   public void loadComplete() {
      KubeJSPlugins.forEachPlugin(KubeJSPlugin::afterInit);
      ((Runnable)ScriptsLoadedEvent.EVENT.invoker()).run();
      StartupEvents.POST_INIT.post(ScriptType.STARTUP, new StartupEventJS());
      UtilsJS.postModificationEvents();
      RecipeNamespace.getAll();
      if (!ScriptType.STARTUP.errors.isEmpty()) {
         ArrayList<String> list = new ArrayList<>();
         list.add("Startup script errors:");
         String[] lines = ScriptType.STARTUP.errors.toArray(new String[0]);

         for (int i = 0; i < lines.length; i++) {
            list.add(i + 1 + ") " + lines[i]);
         }

         LOGGER.error(String.join("\n", list));
         ConsoleJS.STARTUP.flush(true);
         if (Platform.getEnvironment() == Env.SERVER || !CommonProperties.get().startupErrorGUI) {
            throw new RuntimeException("There were KubeJS startup script syntax errors! See logs/kubejs/startup.log for more info");
         }
      }

      QUERY = "source=kubejs&mc=1902&loader=" + PlatformWrapper.getName() + "&v=" + URLEncoder.encode(thisMod.getVersion(), StandardCharsets.UTF_8);
      Thread updater = new Thread(
         () -> {
            try {
               HttpResponse<String> response = HttpClient.newBuilder()
                  .followRedirects(Redirect.ALWAYS)
                  .connectTimeout(Duration.ofSeconds(5L))
                  .build()
                  .send(
                     HttpRequest.newBuilder().uri(URI.create("https://v.kubejs.com/update-check?" + QUERY)).GET().build(),
                     BodyHandlers.ofString(StandardCharsets.UTF_8)
                  );
               if (response.statusCode() == 200) {
                  String body = response.body().trim();
                  if (!body.isEmpty()) {
                     ConsoleJS.STARTUP.info("Update available: " + body);
                  }
               }
            } catch (Exception var2x) {
            }
         }
      );
      updater.setDaemon(true);
      updater.start();
   }
}
