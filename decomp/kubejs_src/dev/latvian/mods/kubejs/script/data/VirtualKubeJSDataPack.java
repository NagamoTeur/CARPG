package dev.latvian.mods.kubejs.script.data;

import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

public class VirtualKubeJSDataPack extends AbstractPackResources implements ExportablePackResources {
   public final boolean high;
   private final Map<ResourceLocation, String> locationToData;
   private final Map<String, String> pathToData;
   private final Set<String> namespaces;

   public VirtualKubeJSDataPack(boolean h) {
      super(new File("dummy"));
      this.high = h;
      this.locationToData = new HashMap<>();
      this.pathToData = new HashMap<>();
      this.namespaces = new HashSet<>();
   }

   public void addData(ResourceLocation id, String data) {
      this.locationToData.put(id, data);
      this.pathToData.put("data/" + id.m_135827_() + "/" + id.m_135815_(), data);
      this.namespaces.add(id.m_135827_());
      if (DevProperties.get().dataPackOutput) {
         ConsoleJS.SERVER.info("Registered virtual file [" + (this.high ? "high" : "low") + " priority] '" + id + "': " + data);
      }
   }

   public InputStream m_5541_(String path) throws IOException {
      String s = this.pathToData.get(path);
      if (s != null) {
         if (DevProperties.get().dataPackOutput) {
            ConsoleJS.SERVER.info("Served virtual file [" + (this.high ? "high" : "low") + " priority] '" + path + "': " + s);
         }

         return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
      } else {
         throw new FileNotFoundException(path);
      }
   }

   public InputStream m_8031_(PackType type, ResourceLocation location) throws IOException {
      String s = this.locationToData.get(location);
      if (s != null) {
         if (DevProperties.get().dataPackOutput) {
            ConsoleJS.SERVER.info("Served virtual file [" + (this.high ? "high" : "low") + " priority] '" + location + "': " + s);
         }

         return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
      } else {
         throw new FileNotFoundException(location.toString());
      }
   }

   public boolean m_6105_(String path) {
      return this.pathToData.containsKey(path);
   }

   public boolean m_7211_(PackType type, ResourceLocation location) {
      return type == PackType.SERVER_DATA && this.locationToData.containsKey(location);
   }

   public Collection<ResourceLocation> m_214146_(PackType type, String namespace, String path, Predicate<ResourceLocation> filter) {
      return this.locationToData
         .keySet()
         .stream()
         .filter(r -> !r.m_135815_().endsWith(".mcmeta"))
         .filter(r -> r.m_135827_().equals(namespace) && r.m_135815_().startsWith(path))
         .filter(filter)
         .toList();
   }

   public Set<String> m_5698_(PackType type) {
      return new HashSet<>(this.namespaces);
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> serializer) {
      return null;
   }

   public String m_8017_() {
      return this.toString();
   }

   @Override
   public String toString() {
      return "KubeJS Virtual Data Pack [" + (this.high ? "high" : "low") + " priority]";
   }

   @Override
   public void export(Path root) throws IOException {
      for (Entry<String, String> file : this.pathToData.entrySet()) {
         Path path = root.resolve(file.getKey());
         Files.createDirectories(path.getParent());
         Files.writeString(path, file.getValue(), StandardCharsets.UTF_8);
      }
   }

   public void close() {
   }

   public boolean hasNamespace(String key) {
      return this.namespaces.contains(key);
   }
}
