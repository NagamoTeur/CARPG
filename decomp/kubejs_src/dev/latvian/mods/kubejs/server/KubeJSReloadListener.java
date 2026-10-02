package dev.latvian.mods.kubejs.server;

import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.recipe.AfterRecipesLoadedEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;

public class KubeJSReloadListener implements ResourceManagerReloadListener {
   public static ReloadableServerResources resources;
   public static Object recipeContext;

   public void m_6213_(ResourceManager resourceManager) {
      RecipeManager recipeManager = resources == null ? null : resources.m_206887_();
      if (recipeManager != null && ServerEvents.RECIPES_AFTER_LOADED.hasListeners()) {
         ServerEvents.RECIPES_AFTER_LOADED.post(ScriptType.SERVER, new AfterRecipesLoadedEventJS(recipeManager.f_44007_, recipeManager.f_199900_));
      }
   }
}
