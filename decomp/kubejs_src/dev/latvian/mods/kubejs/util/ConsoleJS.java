package dev.latvian.mods.kubejs.util;

import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.RhinoException;
import dev.latvian.mods.rhino.WrappedException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class ConsoleJS {
   public static ConsoleJS STARTUP;
   public static ConsoleJS SERVER;
   public static ConsoleJS CLIENT;
   private final ScriptType scriptType;
   private final Logger logger;
   private final Path logFile;
   private String group;
   private RhinoException currentError;
   private boolean muted;
   private boolean debugEnabled;
   private boolean writeToFile;
   private final List<String> writeQueue;
   private final String nameStrip;
   public final Consumer<String> debugLogFunction;
   public final Consumer<String> infoLogFunction;
   public final Consumer<String> warnLogFunction;
   public final Consumer<String> errorLogFunction;

   public static ConsoleJS getCurrent(ConsoleJS def) {
      Context cx = ScriptManager.getCurrentContext();
      return cx == null ? def : getCurrent(cx);
   }

   public static ConsoleJS getCurrent(@Nullable Context cx) {
      if (cx == null) {
         cx = ScriptManager.getCurrentContext();
         if (cx == null) {
            return STARTUP;
         }
      }

      return (ConsoleJS)cx.getProperty("Console", null);
   }

   public ConsoleJS(ScriptType m, Logger log) {
      this.scriptType = m;
      this.logger = log;
      this.logFile = m.getLogFile();
      this.group = "";
      this.muted = false;
      this.debugEnabled = false;
      this.writeToFile = true;
      this.writeQueue = new LinkedList<>();
      this.nameStrip = this.scriptType.name + "_scripts:";
      this.debugLogFunction = this.logger::debug;
      this.infoLogFunction = this.logger::info;
      this.warnLogFunction = s -> {
         this.logger.warn(s);
         this.scriptType.warnings.add(s);
      };
      this.errorLogFunction = s -> {
         this.logger.error(s);
         this.scriptType.errors.add(s);
      };
   }

   public Logger getLogger() {
      return this.logger;
   }

   protected boolean shouldPrint() {
      return !this.muted;
   }

   public void setMuted(boolean m) {
      this.muted = m;
   }

   public boolean getMuted() {
      return this.muted;
   }

   public void setDebugEnabled(boolean m) {
      this.debugEnabled = m;
   }

   public boolean getDebugEnabled() {
      return this.debugEnabled;
   }

   public synchronized void setWriteToFile(boolean m) {
      this.writeToFile = m;
   }

   public synchronized boolean getWriteToFile() {
      return this.writeToFile;
   }

   public void resetFile() {
      this.scriptType.executor.execute(() -> {
         try {
            Files.write(this.logFile, List.of());
         } catch (Exception var2) {
            this.logger.error("Failed to clear the log file: " + var2);
         }
      });
   }

   public void pushError(RhinoException e) {
      this.currentError = e;
   }

   public void popError() {
      this.currentError = null;
   }

   private String string(Object object) {
      Object o = UtilsJS.wrap(object, JSObjectType.ANY);
      String s = o != null
            && !o.getClass().isPrimitive()
            && !(o instanceof Boolean)
            && !(o instanceof String)
            && !(o instanceof Number)
            && !(o instanceof WrappedJS)
         ? o + " [" + o.getClass().getName() + "]"
         : String.valueOf(o);
      StringBuilder builder = new StringBuilder();
      int[] lineP = new int[]{0};
      String lineS = null;
      if (this.currentError != null) {
         lineP[0] = this.currentError.lineNumber();
         lineS = this.currentError.lineSource();
      }

      int lpi = s.lastIndexOf(40);
      if (lpi > 0 && s.charAt(s.length() - 1) == ')') {
         String pe = s.substring(lpi + 1, s.length() - 1);
         int ci = pe.lastIndexOf(35);
         if (ci > 0) {
            try {
               lineP[0] = Integer.parseInt(pe.substring(ci + 1));
               lineS = pe.substring(0, ci);
               s = s.substring(0, lpi).trim();
            } catch (Exception var11) {
            }
         }
      }

      if (lineP[0] == 0 || lineS == null) {
         lineS = Context.getSourcePositionFromStack(this.scriptType.manager.get().context, lineP);
      }

      if (lineS != null && lineS.startsWith(this.nameStrip)) {
         lineS = lineS.substring(this.nameStrip.length());
      }

      if (lineP[0] > 0) {
         if (lineS != null && !lineS.isEmpty()) {
            builder.append(lineS);
            builder.append('#');
         } else {
            builder.append("<unknown source>#");
         }

         builder.append(lineP[0]);
         builder.append(": ");
      }

      if (!this.group.isEmpty()) {
         builder.append(this.group);
      }

      builder.append(s);
      return builder.toString();
   }

   private String stringf(Object object, Object... args) {
      return this.string(String.format(String.valueOf(object), args));
   }

   private void log(Consumer<String> logFunction, String type, Object message) {
      if (this.shouldPrint()) {
         String s = this.string(message);
         logFunction.accept(s);
         this.writeToFile(type, s);
      }
   }

   private void logf(Consumer<String> logFunction, String type, Object message, Object... args) {
      if (this.shouldPrint()) {
         String s = this.stringf(message, args);
         logFunction.accept(s);
         this.writeToFile(type, s);
      }
   }

   public synchronized void writeToFile(String type, String line) {
      if (this.writeToFile && !MiscPlatformHelper.get().isDataGen()) {
         Calendar calendar = Calendar.getInstance();
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         if (calendar.get(11) < 10) {
            sb.append('0');
         }

         sb.append(calendar.get(11));
         sb.append(':');
         if (calendar.get(12) < 10) {
            sb.append('0');
         }

         sb.append(calendar.get(12));
         sb.append(':');
         if (calendar.get(13) < 10) {
            sb.append('0');
         }

         sb.append(calendar.get(13));
         sb.append(']');
         sb.append(' ');
         sb.append('[');
         sb.append(type);
         sb.append(']');
         sb.append(' ');
         if (type.equals("ERROR")) {
            sb.append('!');
            sb.append(' ');
         }

         sb.append(line);
         this.writeQueue.add(sb.toString());
      }
   }

   public synchronized void flush(boolean sync) {
      if (!this.writeQueue.isEmpty()) {
         List<String> lines = Arrays.asList(this.writeQueue.toArray(UtilsJS.EMPTY_STRING_ARRAY));
         this.writeQueue.clear();
         if (sync) {
            try {
               Files.write(this.logFile, lines, StandardOpenOption.APPEND);
            } catch (Exception var4) {
               this.logger.error("Failed to write to the log file: " + var4);
            }
         } else {
            this.scriptType.executor.execute(() -> {
               try {
                  Files.write(this.logFile, lines, StandardOpenOption.APPEND);
               } catch (Exception var3) {
                  this.logger.error("Failed to write to the log file: " + var3);
               }
            });
         }
      }
   }

   public void log(Object... message) {
      for (Object s : message) {
         this.info(s);
      }
   }

   public void info(Object message) {
      this.log(this.infoLogFunction, "INFO", message);
   }

   public void infof(Object message, Object... args) {
      this.logf(this.infoLogFunction, "INFO", message, args);
   }

   public void warn(Object message) {
      this.log(this.warnLogFunction, "WARN", message);
   }

   public void warn(String message, Throwable throwable, @Nullable Pattern skip) {
      if (this.shouldPrint()) {
         String s = throwable.toString();
         if (!DevProperties.get().debugInfo && !s.equals("java.lang.NullPointerException")) {
            this.warn(message + ": " + s);
         } else {
            this.warn(message + ":");
            this.printStackTrace(false, throwable, skip);
         }
      }
   }

   public void warn(String message, Throwable throwable) {
      this.warn(message, throwable, null);
   }

   public void warnf(String message, Object... args) {
      this.logf(this.warnLogFunction, "WARN", message, args);
   }

   public void error(Object message) {
      this.log(this.errorLogFunction, "ERROR", message);
   }

   public void error(String message, Throwable throwable, @Nullable Pattern skip) {
      if (this.shouldPrint()) {
         String s = throwable.toString();
         if (!DevProperties.get().debugInfo && !s.equals("java.lang.NullPointerException")) {
            this.error(message + ": " + s);
         } else {
            this.error(message + ":");
            this.printStackTrace(true, throwable, skip);
         }
      }
   }

   public void error(String message, Throwable throwable) {
      this.error(message, throwable, null);
   }

   public void errorf(String message, Object... args) {
      this.logf(this.errorLogFunction, "ERROR", message, args);
   }

   public boolean shouldPrintDebug() {
      return this.debugEnabled && this.shouldPrint();
   }

   public void debug(Object message) {
      if (this.shouldPrintDebug()) {
         this.log(this.debugLogFunction, "DEBUG", message);
      }
   }

   public void debugf(String message, Object... args) {
      if (this.shouldPrintDebug()) {
         this.logf(this.debugLogFunction, "DEBUG", message, args);
      }
   }

   public void group() {
      this.group = this.group + "  ";
   }

   public void groupEnd() {
      if (this.group.length() >= 2) {
         this.group = this.group.substring(0, this.group.length() - 2);
      }
   }

   public void trace() {
      StackTraceElement[] elements = Thread.currentThread().getStackTrace();
      this.info("=== Stack Trace ===");

      for (StackTraceElement element : elements) {
         this.info("=\t" + element);
      }
   }

   public int getScriptLine() {
      int[] linep = new int[]{0};
      Context.getSourcePositionFromStack(this.scriptType.manager.get().context, linep);
      return linep[0];
   }

   public void printClass(String className, boolean tree) {
      try {
         Class<?> c = Class.forName(className);
         Class<?> sc = c.getSuperclass();
         this.info("=== " + c.getName() + " ===");
         this.info("= Parent class =");
         this.info("> " + (sc == null ? "-" : sc.getName()));
         HashMap<String, ConsoleJS.VarFunc> vars = new HashMap<>();
         HashMap<String, ConsoleJS.VarFunc> funcs = new HashMap<>();

         for (Field field : c.getDeclaredFields()) {
            if ((field.getModifiers() & 1) != 0 && (field.getModifiers() & 128) != 0) {
               ConsoleJS.VarFunc f = new ConsoleJS.VarFunc(field.getName(), field.getType());
               f.flags |= 1;
               if ((field.getModifiers() & 16) == 0) {
                  f.flags |= 2;
               }

               vars.put(f.name, f);
            }
         }

         for (Method method : c.getDeclaredMethods()) {
            if ((method.getModifiers() & 1) != 0 && !this.isOverrideMethod(method)) {
               ConsoleJS.VarFunc f = new ConsoleJS.VarFunc(method.getName(), method.getReturnType());

               for (int i = 0; i < method.getParameterCount(); i++) {
                  f.params.add(method.getParameters()[i].getType());
               }

               if (f.name.length() >= 4 && f.name.startsWith("get") && Character.isUpperCase(f.name.charAt(3)) && f.params.size() == 0) {
                  String n = Character.toLowerCase(f.name.charAt(3)) + f.name.substring(4);
                  ConsoleJS.VarFunc f0 = vars.get(n);
                  if (f0 == null) {
                     vars.put(n, new ConsoleJS.VarFunc(n, f.type));
                     continue;
                  }

                  if (f0.type.equals(f.type)) {
                     f0.flags |= 1;
                     continue;
                  }
               }

               funcs.put(f.name, f);
            }
         }

         this.info("= Variables and Functions =");
         if (vars.isEmpty() && funcs.isEmpty()) {
            this.info("-");
         } else {
            vars.values()
               .stream()
               .sorted()
               .forEach(fx -> this.info("> " + ((fx.flags & 2) == 0 ? "val" : "var") + " " + fx.name + ": " + this.getSimpleName(fx.type)));
            funcs.values()
               .stream()
               .sorted()
               .forEach(
                  fx -> this.info(
                        "> function "
                           + fx.name
                           + "("
                           + fx.params.stream().map(this::getSimpleName).collect(Collectors.joining(", "))
                           + "): "
                           + this.getSimpleName(fx.type)
                     )
               );
         }

         if (tree && sc != null) {
            this.info("");
            this.printClass(sc.getName(), true);
         }
      } catch (Throwable var14) {
         this.error("= Error loading class =");
         this.error(var14.toString());
      }
   }

   public void printClass(String className) {
      this.printClass(className, false);
   }

   private String getSimpleName(Class<?> c) {
      if (c.isPrimitive()) {
         return c.getName();
      } else {
         String s = c.getName();
         int i = s.lastIndexOf(46);
         s = s.substring(i + 1);
         i = s.lastIndexOf(36);
         return s.substring(i + 1);
      }
   }

   private boolean isOverrideMethod(Method method) throws Throwable {
      return false;
   }

   public void printObject(@Nullable Object o, boolean tree) {
      if (o == null) {
         this.info("=== null ===");
      } else {
         this.info("=== " + o.getClass().getName() + " ===");
         this.info("= toString() =");
         this.info("> " + o);
         this.info("= hashCode() =");
         this.info("> " + Integer.toHexString(o.hashCode()));
         this.info("");
         this.printClass(o.getClass().getName(), tree);
      }
   }

   public void printObject(@Nullable Object o) {
      this.printObject(o, false);
   }

   public void printStackTrace(boolean error, Throwable throwable, @Nullable Pattern skip) {
      throwable.printStackTrace(new ConsoleJS.StackTracePrintStream(this, error, skip));
   }

   public void handleError(Throwable throwable, @Nullable Pattern skip, String message) {
      try {
         if (throwable instanceof WrappedException ex) {
            this.pushError(ex);
            this.error(message + ": " + ex.getWrappedException());
            this.popError();
            this.printStackTrace(true, ex.getWrappedException(), skip);
         } else if (throwable instanceof RhinoException ex) {
            this.pushError(ex);
            this.error(message + ": " + ex.getMessage());
            this.popError();
         } else {
            this.error(message + ": " + throwable);
            this.printStackTrace(true, throwable, skip);
         }
      } catch (Throwable var6) {
         this.error("Errored while handling error... wtf... " + var6);
         var6.printStackTrace();
      }
   }

   private static class StackTracePrintStream extends PrintStream implements Consumer<String> {
      private final ConsoleJS console;
      private final boolean error;
      private final Pattern skipString;
      private boolean skip;

      private StackTracePrintStream(ConsoleJS c, boolean e, @Nullable Pattern ca) {
         super(System.err);
         this.console = c;
         this.error = e;
         this.skipString = ca;
         this.skip = false;
      }

      @Override
      public void println(@Nullable Object x) {
         this.println(String.valueOf(x));
      }

      @Override
      public void println(@Nullable String x) {
         if (!this.skip) {
            if (x != null && this.skipString != null && this.skipString.matcher(x).find()) {
               this.skip = true;
            } else if (this.error) {
               this.console.log(this, "ERROR", x);
            } else {
               this.console.log(this, "WARN", x);
            }
         }
      }

      public void accept(String s) {
         this.console.logger.error(s);
      }
   }

   private static final class VarFunc implements Comparable<ConsoleJS.VarFunc> {
      public final String name;
      public final Class<?> type;
      public final ArrayList<Class<?>> params;
      public int flags;

      public VarFunc(String n, Class<?> t) {
         this.name = n;
         this.type = t;
         this.flags = 0;
         this.params = new ArrayList<>();
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ConsoleJS.VarFunc varFunc = (ConsoleJS.VarFunc)o;
            return Objects.equals(this.name, varFunc.name)
               && Objects.equals(this.type, varFunc.type)
               && Objects.equals(this.flags, varFunc.flags)
               && Objects.equals(this.params, varFunc.params);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.name, this.type, this.flags, this.params);
      }

      public int compareTo(ConsoleJS.VarFunc o) {
         return this.name.compareToIgnoreCase(o.name);
      }
   }
}
