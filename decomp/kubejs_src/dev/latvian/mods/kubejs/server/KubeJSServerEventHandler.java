package dev.latvian.mods.kubejs.server;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.CommandPerformEvent;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.latvian.mods.kubejs.bindings.event.LevelEvents;
import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.command.CommandRegistryEventJS;
import dev.latvian.mods.kubejs.command.KubeJSCommands;
import dev.latvian.mods.kubejs.level.SimpleLevelEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.RhinoException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

public class KubeJSServerEventHandler {
   private static final LevelResource PERSISTENT_DATA = new LevelResource("kubejs_persistent_data.nbt");

   public static void init() {
      LifecycleEvent.SERVER_BEFORE_START.register(KubeJSServerEventHandler::serverAboutToStart);
      CommandRegistrationEvent.EVENT.register(KubeJSServerEventHandler::registerCommands);
      LifecycleEvent.SERVER_BEFORE_START.register(KubeJSServerEventHandler::serverBeforeStarting);
      LifecycleEvent.SERVER_STARTING.register(KubeJSServerEventHandler::serverStarting);
      LifecycleEvent.SERVER_STOPPING.register(KubeJSServerEventHandler::serverStopping);
      LifecycleEvent.SERVER_STOPPED.register(KubeJSServerEventHandler::serverStopped);
      LifecycleEvent.SERVER_LEVEL_SAVE.register(KubeJSServerEventHandler::serverLevelSaved);
      LifecycleEvent.SERVER_LEVEL_LOAD.register(KubeJSServerEventHandler::serverLevelLoaded);
      CommandPerformEvent.EVENT.register(KubeJSServerEventHandler::command);
   }

   public static void serverAboutToStart(MinecraftServer server) {
      Path p = server.m_129843_(PERSISTENT_DATA);
      if (Files.exists(p)) {
         try {
            CompoundTag tag = NbtIo.m_128937_(p.toFile());
            if (tag != null) {
               server.kjs$getPersistentData().m_128391_(tag);
            }
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, CommandSelection selection) {
      KubeJSCommands.register(dispatcher);
      if (ServerEvents.COMMAND_REGISTRY.hasListeners()) {
         ServerEvents.COMMAND_REGISTRY.post(ScriptType.SERVER, new CommandRegistryEventJS(dispatcher, registry, selection));
      }
   }

   private static void serverBeforeStarting(MinecraftServer server) {
      UtilsJS.staticServer = server;
   }

   private static void serverStarting(MinecraftServer server) {
      ServerEvents.LOADED.post(ScriptType.SERVER, new ServerEventJS(server));
   }

   private static void serverStopping(MinecraftServer server) {
      ServerEvents.UNLOADED.post(ScriptType.SERVER, new ServerEventJS(server));
   }

   private static void serverStopped(MinecraftServer server) {
      UtilsJS.staticServer = null;
   }

   private static void serverLevelLoaded(ServerLevel level) {
      if (LevelEvents.LOADED.hasListeners()) {
         LevelEvents.LOADED.post(new SimpleLevelEventJS(level), level.m_46472_().m_135782_());
      }
   }

   private static void serverLevelSaved(ServerLevel level) {
      Path p = level.m_7654_().m_129843_(PERSISTENT_DATA);
      if (level.m_46472_() == Level.f_46428_) {
         Util.m_183992_().execute(() -> {
            try {
               NbtIo.m_128944_(level.m_7654_().kjs$getPersistentData(), p.toFile());
            } catch (Exception var3) {
               var3.printStackTrace();
            }
         });
      }
   }

   public static void tickScheduledEvents(long nowMs, long nowTicks, List<ScheduledEvent> kjs$scheduledEvents) {
      if (!kjs$scheduledEvents.isEmpty()) {
         Iterator<ScheduledEvent> eventIterator = kjs$scheduledEvents.iterator();
         LinkedList<ScheduledEvent> list = new LinkedList<>();

         while (eventIterator.hasNext()) {
            ScheduledEvent e = eventIterator.next();
            if (e.check(nowMs, nowTicks)) {
               list.add(e);
               eventIterator.remove();
            }
         }

         for (ScheduledEvent e : list) {
            try {
               e.callback.onCallback(e);
            } catch (RhinoException var10) {
               ConsoleJS.SERVER.error("Error occurred while handling scheduled event callback: " + var10.getMessage());
            } catch (Throwable var11) {
               var11.printStackTrace();
            }
         }
      }
   }

   public static EventResult command(CommandPerformEvent event) {
      if (ServerEvents.COMMAND.hasListeners()) {
         CommandEventJS e = new CommandEventJS(event);
         return ServerEvents.COMMAND.post(e, e.getCommandName()).arch();
      } else {
         return EventResult.pass();
      }
   }
}
