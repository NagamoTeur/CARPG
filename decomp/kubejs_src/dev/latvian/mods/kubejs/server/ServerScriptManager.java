package dev.latvian.mods.kubejs.server;

import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.platform.RecipePlatformHelper;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.ingredientaction.CustomIngredientAction;
import dev.latvian.mods.kubejs.recipe.special.SpecialRecipeSerializerManager;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.data.DataPackEventJS;
import dev.latvian.mods.kubejs.script.data.VirtualKubeJSDataPack;
import dev.latvian.mods.kubejs.server.tag.PreTagEventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;

public class ServerScriptManager {
   public static ServerScriptManager instance;
   private final ScriptManager scriptManager = new ScriptManager(ScriptType.SERVER, KubeJSPaths.SERVER_SCRIPTS);
   public final Map<ResourceKey<?>, PreTagEventJS> preTagEvents = new ConcurrentHashMap<>();

   public static ScriptManager getScriptManager() {
      return instance.scriptManager;
   }

   public ServerScriptManager() {
      try {
         if (Files.notExists(KubeJSPaths.DATA)) {
            Files.createDirectories(KubeJSPaths.DATA);
         }
      } catch (Throwable var2) {
         throw new RuntimeException("KubeJS failed to register it's script loader!", var2);
      }
   }

   public void updateResources(ReloadableServerResources serverResources) {
      KubeJSReloadListener.resources = serverResources;
      KubeJSReloadListener.recipeContext = RecipePlatformHelper.get().createRecipeContext(serverResources);
   }

   public void reloadScriptManager(ResourceManager resourceManager) {
      this.scriptManager.reload(resourceManager);
   }

   public MultiPackResourceManager wrapResourceManager(CloseableResourceManager original) {
      VirtualKubeJSDataPack virtualDataPackLow = new VirtualKubeJSDataPack(false);
      VirtualKubeJSDataPack virtualDataPackHigh = new VirtualKubeJSDataPack(true);
      LinkedList<PackResources> list = new LinkedList<>(original instanceof MultiPackResourceManager mp ? mp.f_203795_ : original.m_7536_().toList());
      list.addFirst(virtualDataPackLow);
      list.addLast(new GeneratedServerResourcePack());

      for (File file : Objects.requireNonNull(KubeJSPaths.DATA.toFile().listFiles())) {
         if (file.isFile() && file.getName().endsWith(".zip")) {
            list.addLast(new FilePackResources(file));
         }
      }

      list.addLast(virtualDataPackHigh);
      MultiPackResourceManager wrappedResourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, list);
      this.reloadScriptManager(wrappedResourceManager);
      ServerEvents.LOW_DATA.post(ScriptType.SERVER, new DataPackEventJS(virtualDataPackLow, wrappedResourceManager));
      ServerEvents.HIGH_DATA.post(ScriptType.SERVER, new DataPackEventJS(virtualDataPackHigh, wrappedResourceManager));
      ConsoleJS.SERVER.info("Scripts loaded");
      RecipesEventJS.customIngredientMap = new HashMap<>();
      CustomIngredientAction.MAP.clear();
      SpecialRecipeSerializerManager.INSTANCE.reset();
      ServerEvents.SPECIAL_RECIPES.post(ScriptType.SERVER, SpecialRecipeSerializerManager.INSTANCE);
      KubeJSPlugins.forEachPlugin(KubeJSPlugin::onServerReload);
      PreTagEventJS.handle(this.preTagEvents);
      if (ServerEvents.RECIPES.hasListeners()) {
         RecipesEventJS.instance = new RecipesEventJS();
      }

      return wrappedResourceManager;
   }
}
