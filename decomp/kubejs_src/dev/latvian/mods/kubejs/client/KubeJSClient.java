package dev.latvian.mods.kubejs.client;

import dev.architectury.hooks.PackRepositoryHooks;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSCommon;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.bindings.event.ClientEvents;
import dev.latvian.mods.kubejs.bindings.event.ItemEvents;
import dev.latvian.mods.kubejs.bindings.event.NetworkEvents;
import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.fluid.FluidBuilder;
import dev.latvian.mods.kubejs.gui.KubeJSMenu;
import dev.latvian.mods.kubejs.gui.KubeJSScreen;
import dev.latvian.mods.kubejs.item.ItemModelPropertiesEventJS;
import dev.latvian.mods.kubejs.net.NetworkEventJS;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.data.ExportablePackResources;
import dev.latvian.mods.kubejs.script.data.GeneratedData;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class KubeJSClient extends KubeJSCommon {
   @Override
   public void init() {
      if (Minecraft.m_91087_() != null) {
         reloadClientScripts();
         new KubeJSClientEventHandler().init();
         PackRepository list = Minecraft.m_91087_().m_91099_();
         PackRepositoryHooks.addSource(list, new KubeJSResourcePackFinder());
         KubeJSPlugins.forEachPlugin(KubeJSPlugin::clientInit);
      }
   }

   @Override
   public void reloadClientInternal() {
      reloadClientScripts();
   }

   public static void reloadClientScripts() {
      KubeJSClientEventHandler.staticItemTooltips = null;
      KubeJS.getClientScriptManager().reload(Minecraft.m_91087_() == null ? null : Minecraft.m_91087_().m_91098_());
   }

   public static void copyDefaultOptionsFile(File optionsFile) {
      if (!optionsFile.exists()) {
         Path defOptions = KubeJSPaths.CONFIG.resolve("defaultoptions.txt");
         if (Files.exists(defOptions)) {
            try {
               KubeJS.LOGGER.info("Loaded default options from kubejs/config/defaultoptions.txt");
               Files.copy(defOptions, optionsFile.toPath());
            } catch (IOException var3) {
               var3.printStackTrace();
            }
         }
      }
   }

   @Override
   public void clientSetup() {
      if (Platform.isDevelopmentEnvironment()) {
         KubeJS.LOGGER.info("CLIENT SETUP");
      }

      ClientEvents.INIT.post(ScriptType.STARTUP, new ClientInitEventJS());
      ItemEvents.MODEL_PROPERTIES.post(ScriptType.STARTUP, new ItemModelPropertiesEventJS());
      ClientEvents.ATLAS_SPRITE_REGISTRY.listenJava(ScriptType.CLIENT, TextureAtlas.f_118259_, event -> {
         AtlasSpriteRegistryEventJS e = (AtlasSpriteRegistryEventJS)event;

         for (BuilderBase<?> builder : RegistryInfo.FLUID) {
            if (builder instanceof FluidBuilder b) {
               e.register(b.stillTexture);
               e.register(b.flowingTexture);
            }
         }

         return null;
      });
      if (!CommonProperties.get().serverOnly) {
         MenuRegistry.registerScreenFactory(KubeJSMenu.KUBEJS_MENU.get(), KubeJSScreen::new);
      }
   }

   @Override
   public void handleDataFromServerPacket(String channel, @Nullable CompoundTag data) {
      if (NetworkEvents.DATA_RECEIVED.hasListeners(channel)) {
         NetworkEvents.DATA_RECEIVED.post(ScriptType.CLIENT, channel, new NetworkEventJS(Minecraft.m_91087_().f_91074_, channel, data));
      }
   }

   @Nullable
   @Override
   public Player getClientPlayer() {
      return Minecraft.m_91087_().f_91074_;
   }

   @Override
   public void paint(CompoundTag tag) {
      Painter.INSTANCE.paint(tag);
   }

   private void reload(PreparableReloadListener listener) {
      long start = System.currentTimeMillis();
      Minecraft mc = Minecraft.m_91087_();
      mc.m_91098_().m_213713_(GeneratedData.INTERNAL_RELOAD.id());
      listener.m_5540_(CompletableFuture::completedFuture, mc.m_91098_(), InactiveProfiler.f_18554_, InactiveProfiler.f_18554_, Util.m_183991_(), mc)
         .thenAccept(unused -> mc.f_91074_.m_213846_(Component.m_237113_("Done! You still may have to reload all assets with F3 + T")));
   }

   @Override
   public void reloadTextures() {
      this.reload(Minecraft.m_91087_().m_91097_());
   }

   @Override
   public void reloadLang() {
      this.reload(Minecraft.m_91087_().m_91102_());
   }

   @Override
   public void generateTypings(CommandSourceStack source) {
      source.m_81354_(Component.m_237113_("WIP!"), false);
   }

   @Override
   public void reloadConfig() {
      super.reloadConfig();
      ClientProperties.reload();
   }

   @Override
   public void export(List<ExportablePackResources> packs) {
      for (PackResources pack : Minecraft.m_91087_().m_91098_().m_7536_().toList()) {
         if (pack instanceof ExportablePackResources) {
            ExportablePackResources e = (ExportablePackResources)pack;
            if (!packs.contains(e)) {
               packs.add(e);
            }
         }
      }
   }
}
