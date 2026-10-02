package dev.latvian.mods.kubejs.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.Registrar;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import dev.latvian.mods.rhino.mod.util.JsonUtils;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

public class DataExport {
   @HideFromJS
   public static DataExport export = null;
   public CommandSourceStack source;
   private final Map<String, Callable<byte[]>> exportedFiles = new ConcurrentHashMap<>();

   public static void exportData() {
      if (export != null) {
         try {
            export.exportData0();
         } catch (Exception var1) {
            var1.printStackTrace();
         }

         export = null;
      }
   }

   private static <T> void addRegistry(JsonObject o, String name, Registrar<T> r) {
      JsonArray a = new JsonArray();

      for (ResourceLocation id : r.getIds()) {
         a.add(id.toString());
      }

      o.add(name, a);
   }

   public void add(String path, Callable<byte[]> data) {
      try {
         this.exportedFiles.put(path, data);
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public void addString(String path, String data) {
      this.add(path, () -> data.getBytes(StandardCharsets.UTF_8));
   }

   public void addJson(String path, JsonElement json) {
      this.add(path, () -> JsonUtils.toPrettyString(json).getBytes(StandardCharsets.UTF_8));
   }

   private void exportData0() throws Exception {
      for (ResourceKey regKey : Registry.f_122897_.m_214010_()) {
         Registrar reg = KubeJSRegistries.REGISTRIES.get(regKey);
         if (reg != null) {
            ArrayList<Pair<String, String>> regItems = new ArrayList<>();

            for (Object entry0 : reg.entrySet()) {
               Entry<ResourceKey, Object> entry = (Entry<ResourceKey, Object>)entry0;
               regItems.add(Pair.of(entry.getKey().m_135782_().toString(), entry.getValue() == null ? "null" : entry.getValue().getClass().getName()));
            }

            regItems.sort((o1, o2) -> ((String)o1.getLeft()).compareToIgnoreCase((String)o2.getLeft()));
            JsonObject j = new JsonObject();

            for (Pair<String, String> pair : regItems) {
               j.addProperty((String)pair.getLeft(), (String)pair.getRight());
            }

            this.addJson("registries/" + regKey.m_135782_().m_135815_() + ".json", j);
         }
      }

      this.addString("errors.log", String.join("\n", ScriptType.SERVER.errors));
      this.addString("warnings.log", String.join("\n", ScriptType.SERVER.warnings));
      JsonArray modArr = new JsonArray();

      for (Mod mod : Platform.getMods()) {
         JsonObject o = new JsonObject();
         o.addProperty("id", mod.getModId().trim());
         o.addProperty("name", mod.getName().trim());
         o.addProperty("version", mod.getVersion().trim());
         o.addProperty("description", mod.getDescription().trim());
         o.addProperty("authors", String.join(", ", mod.getAuthors()).trim());
         o.addProperty("homepage", mod.getHomepage().orElse("").trim());
         o.addProperty("sources", mod.getSources().orElse("").trim());
         o.addProperty("issue_tracker", mod.getIssueTracker().orElse("").trim());
         o.addProperty("license", mod.getLicense() == null ? "" : String.join(", ", mod.getLicense()).trim());
         o.entrySet().removeIf(e -> {
            if (e.getValue() instanceof JsonPrimitive p && p.isString() && p.getAsString().isEmpty()) {
               return true;
            }

            return false;
         });
         modArr.add(o);
      }

      this.addJson("mods.json", modArr);
      KubeJSPlugins.forEachPlugin(p -> p.exportServerData(this));
      JsonArray index = new JsonArray();
      this.exportedFiles.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(index::add);
      this.addJson("index.json", index);
      Files.walk(KubeJSPaths.EXPORT).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
      Files.createDirectory(KubeJSPaths.EXPORT);
      CompletableFuture[] arr = new CompletableFuture[this.exportedFiles.size()];
      int i = 0;

      for (Entry<String, Callable<byte[]>> entry : this.exportedFiles.entrySet()) {
         arr[i++] = CompletableFuture.runAsync(() -> {
            try {
               Path path = KubeJSPaths.EXPORT.resolve(entry.getKey().replace(':', '/'));
               Path parent = path.getParent();
               if (Files.notExists(parent)) {
                  Files.createDirectories(parent);
               }

               if (Files.notExists(path)) {
                  Files.createFile(path);
               }

               Files.write(path, entry.getValue().call());
            } catch (Exception var3x) {
               var3x.printStackTrace();
            }
         }, Util.m_183992_());
      }

      CompletableFuture.allOf(arr).join();
      if (this.source.m_81377_().m_129792_()) {
         this.source
            .m_81354_(Component.m_237113_("Done! Export in local/kubejs/export").kjs$clickOpenFile(KubeJSPaths.EXPORT.toAbsolutePath().toString()), false);
      } else {
         this.source.m_81354_(Component.m_237113_("Done! Export in local/kubejs/export"), false);
      }
   }
}
