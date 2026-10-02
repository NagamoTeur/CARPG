package dev.latvian.mods.kubejs.script.data;

import dev.latvian.mods.kubejs.DevProperties;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.Lazy;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.ResourcePackFileNotFoundException;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

public abstract class GeneratedResourcePack implements ExportablePackResources {
   private final PackType packType;
   private Map<ResourceLocation, GeneratedData> generated;
   private Set<String> generatedNamespaces;

   private static Stream<Path> tryWalk(Path path) {
      try {
         return Files.walk(path);
      } catch (Exception var2) {
         return Stream.empty();
      }
   }

   public static void scanForInvalidFiles(String pathName, Path path) throws IOException {
      for (Path p : Files.list(path)
         .filter(x$0 -> Files.isDirectory(x$0))
         .flatMap(GeneratedResourcePack::tryWalk)
         .filter(x$0 -> Files.isRegularFile(x$0))
         .filter(Files::isReadable)
         .toList()) {
         try {
            String fileName = p.getFileName().toString();
            String fileNameLC = fileName.toLowerCase();
            if (!fileNameLC.endsWith(".zip") && !fileNameLC.equals(".ds_store") && !fileNameLC.equals("thumbs.db") && !fileNameLC.equals("desktop.ini")) {
               if (Files.isHidden(path)) {
                  ConsoleJS.STARTUP.error("Invisible file found: " + pathName + path.relativize(p).toString().replace('\\', '/'));
               } else {
                  char[] chars = fileName.toCharArray();

                  for (char c : chars) {
                     if (c >= 'A' && c <= 'Z') {
                        ConsoleJS.STARTUP.error("Invalid file name: Uppercase '" + c + "' in " + pathName + path.relativize(p).toString().replace('\\', '/'));
                        break;
                     }

                     if (c != '_' && c != '-' && (c < 'a' || c > 'z') && (c < '0' || c > '9') && c != '/' && c != '.') {
                        ConsoleJS.STARTUP
                           .error("Invalid file name: Invalid character '" + c + "' in " + pathName + path.relativize(p).toString().replace('\\', '/'));
                        break;
                     }
                  }
               }
            }
         } catch (Exception var11) {
            ConsoleJS.STARTUP.error("Invalid file name: " + pathName + path.relativize(p).toString().replace('\\', '/'));
         }
      }
   }

   public GeneratedResourcePack(PackType t) {
      this.packType = t;
   }

   private static String getFullPath(PackType type, ResourceLocation location) {
      return String.format("%s/%s/%s", type.m_10305_(), location.m_135827_(), location.m_135815_());
   }

   public InputStream m_5542_(String fileName) throws IOException {
      return switch (fileName) {
         case "pack.mcmeta" -> GeneratedData.PACK_META.get();
         case "pack.png" -> GeneratedData.PACK_ICON.get();
         default -> throw new ResourcePackFileNotFoundException(KubeJSPaths.DIRECTORY.toFile(), fileName);
      };
   }

   public Map<ResourceLocation, GeneratedData> getGenerated() {
      if (this.generated == null) {
         this.generated = new HashMap<>();
         this.generate(this.generated);
         boolean debug = DevProperties.get().logGeneratedData || DevProperties.get().debugInfo;

         try {
            Path root = KubeJSPaths.get(this.packType);

            for (Path dir : Files.list(root).filter(x$0 -> Files.isDirectory(x$0)).toList()) {
               String ns = dir.getFileName().toString();
               if (debug) {
                  KubeJS.LOGGER.info("# Walking namespace '" + ns + "'");
               }

               for (Path path : Files.walk(dir).filter(x$0 -> Files.isRegularFile(x$0)).filter(Files::isReadable).toList()) {
                  String pathStr = dir.relativize(path).toString().replace('\\', '/').toLowerCase();
                  int sindex = pathStr.lastIndexOf(47);
                  String fileName = sindex == -1 ? pathStr : pathStr.substring(sindex + 1);
                  if (!fileName.endsWith(".zip")
                     && !fileName.equals(".ds_store")
                     && !fileName.equals("thumbs.db")
                     && !fileName.equals("desktop.ini")
                     && !Files.isHidden(path)) {
                     GeneratedData data = new GeneratedData(new ResourceLocation(ns, pathStr), Lazy.of(() -> {
                        try {
                           return Files.readAllBytes(path);
                        } catch (Exception var2x) {
                           var2x.printStackTrace();
                           return new byte[0];
                        }
                     }));
                     if (debug) {
                        KubeJS.LOGGER.info("- File found: '" + data.id() + "' (" + data.data().get().length + " bytes)");
                     }

                     if (this.skipFile(data)) {
                        if (debug) {
                           KubeJS.LOGGER.info("- Skipping '" + data.id() + "'");
                        }
                     } else {
                        this.generated.put(data.id(), data);
                     }
                  }
               }
            }
         } catch (Exception var12) {
            KubeJS.LOGGER.error("Failed to load files from kubejs/" + this.packType.m_10305_(), var12);
         }

         this.generated.put(GeneratedData.INTERNAL_RELOAD.id(), GeneratedData.INTERNAL_RELOAD);
         this.generated = Map.copyOf(this.generated);
         if (debug) {
            KubeJS.LOGGER.info("Generated " + this.packType + " data (" + this.generated.size() + " files)");
         }
      }

      return this.generated;
   }

   protected boolean skipFile(GeneratedData data) {
      return false;
   }

   public InputStream m_8031_(PackType type, ResourceLocation location) throws IOException {
      GeneratedData r = type == this.packType ? this.getGenerated().get(location) : null;
      if (r == GeneratedData.INTERNAL_RELOAD) {
         this.close();
      }

      if (r != null) {
         return r.get();
      } else {
         throw new ResourcePackFileNotFoundException(KubeJSPaths.DIRECTORY.toFile(), getFullPath(type, location));
      }
   }

   public boolean m_7211_(PackType type, ResourceLocation location) {
      return type == this.packType && this.getGenerated().get(location) != null;
   }

   public void generate(Map<ResourceLocation, GeneratedData> map) {
   }

   public Collection<ResourceLocation> m_214146_(PackType type, String namespace, String path, Predicate<ResourceLocation> filter) {
      if (type != this.packType) {
         return Collections.emptySet();
      } else {
         ArrayList<ResourceLocation> list = new ArrayList<>();
         if (!path.endsWith("/")) {
            path = path + "/";
         }

         for (Entry<ResourceLocation, GeneratedData> r : this.getGenerated().entrySet()) {
            if (r.getKey().m_135827_().equals(namespace) && r.getKey().m_135815_().startsWith(path) && filter.test(r.getKey())) {
               list.add(r.getKey());
            }
         }

         return list;
      }
   }

   public Set<String> m_5698_(PackType type) {
      if (type != this.packType) {
         return Collections.emptySet();
      } else {
         if (this.generatedNamespaces == null) {
            this.generatedNamespaces = new HashSet<>();

            for (Entry<ResourceLocation, GeneratedData> s : this.getGenerated().entrySet()) {
               this.generatedNamespaces.add(s.getKey().m_135827_());
            }
         }

         return this.generatedNamespaces;
      }
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> serializer) throws IOException {
      Object var3;
      try (InputStream in = this.m_5542_("pack.mcmeta")) {
         var3 = AbstractPackResources.m_10214_(serializer, in);
      }

      return (T)var3;
   }

   public String m_8017_() {
      return "KubeJS Resource Pack [" + this.packType.m_10305_() + "]";
   }

   public void close() {
      this.generated = null;
      this.generatedNamespaces = null;
   }

   @Override
   public void export(Path root) throws IOException {
      for (Entry<ResourceLocation, GeneratedData> file : this.getGenerated().entrySet()) {
         Path path = root.resolve(this.packType.m_10305_() + "/" + file.getKey().m_135827_() + "/" + file.getKey().m_135815_());
         Files.createDirectories(path.getParent());
         Files.write(path, file.getValue().data().get());
      }

      Files.write(root.resolve("pack.mcmeta"), GeneratedData.PACK_META.data().get());
      Files.write(root.resolve("pack.png"), GeneratedData.PACK_ICON.data().get());
   }
}
