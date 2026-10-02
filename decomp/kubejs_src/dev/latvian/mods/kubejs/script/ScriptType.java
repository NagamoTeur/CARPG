package dev.latvian.mods.kubejs.script;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

public enum ScriptType implements ScriptTypePredicate, ScriptTypeHolder {
   STARTUP("startup", "KubeJS Startup", KubeJS::getStartupScriptManager),
   SERVER("server", "KubeJS Server", ServerScriptManager::getScriptManager),
   CLIENT("client", "KubeJS Client", KubeJS::getClientScriptManager);

   public static final ScriptType[] VALUES = values();
   public final String name;
   public final transient ConcurrentLinkedDeque<String> errors;
   public final transient ConcurrentLinkedDeque<String> warnings;
   public final ConsoleJS console;
   public final transient Supplier<ScriptManager> manager;
   public transient Executor executor;

   public static ScriptType getCurrent(Context cx) {
      return (ScriptType)cx.getProperty("Type");
   }

   private ScriptType(String n, String cname, Supplier<ScriptManager> m) {
      this.name = n;
      this.errors = new ConcurrentLinkedDeque<>();
      this.warnings = new ConcurrentLinkedDeque<>();
      this.console = new ConsoleJS(this, LoggerFactory.getLogger(cname));
      this.manager = m;
      this.executor = Runnable::run;
   }

   public Path getLogFile() {
      Path dir = Platform.getGameFolder().resolve("logs/kubejs");
      Path file = dir.resolve(this.name + ".log");

      try {
         if (!Files.exists(dir)) {
            Files.createDirectories(dir);
         }

         if (!Files.exists(file)) {
            Path oldFile = dir.resolve(this.name + ".txt");
            if (Files.exists(oldFile)) {
               Files.move(oldFile, file);
            } else {
               Files.createFile(file);
            }
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return file;
   }

   public boolean isClient() {
      return this == CLIENT;
   }

   public boolean isServer() {
      return this == SERVER;
   }

   public boolean isStartup() {
      return this == STARTUP;
   }

   @HideFromJS
   public void unload() {
      this.errors.clear();
      this.warnings.clear();
      this.console.resetFile();

      for (EventGroup group : EventGroup.getGroups().values()) {
         for (EventHandler handler : group.getHandlers().values()) {
            handler.clear(this);
         }
      }
   }

   public Component errorsComponent(String command) {
      return Component.m_237113_("KubeJS errors found [" + this.errors.size() + "]! Run '" + command + "' for more info")
         .kjs$clickRunCommand(command)
         .kjs$hover(Component.m_237113_("Click to show"))
         .m_130940_(ChatFormatting.DARK_RED);
   }

   public Component warningsComponent(String command) {
      return Component.m_237113_("KubeJS warnings found [" + this.warnings.size() + "]! Run '" + command + "' for more info")
         .kjs$clickRunCommand(command)
         .kjs$hover(Component.m_237113_("Click to show"))
         .m_130948_(Style.f_131099_.m_131148_(TextColor.m_131266_(16753920)));
   }

   @Override
   public boolean test(ScriptType type) {
      return type == this;
   }

   @Override
   public List<ScriptType> getValidTypes() {
      return List.of(this);
   }

   @NotNull
   public ScriptTypePredicate negate() {
      return switch (this) {
         case STARTUP -> ScriptTypePredicate.COMMON;
         case SERVER -> ScriptTypePredicate.STARTUP_OR_CLIENT;
         case CLIENT -> ScriptTypePredicate.STARTUP_OR_SERVER;
      };
   }

   @Override
   public ScriptType kjs$getScriptType() {
      return this;
   }

   static {
      ConsoleJS.STARTUP = STARTUP.console;
      ConsoleJS.SERVER = SERVER.console;
      ConsoleJS.CLIENT = CLIENT.console;
   }
}
