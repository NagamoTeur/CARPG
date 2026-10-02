package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.entities.PermanentItemEntity;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SoulArchive {
   private static final Type SOUL_RECORDS_TYPE = (new TypeToken<List<SoulArchive.SoulData>>() {
   }).getType();
   private static final Charset UTF8 = Charset.forName("UTF-8");
   private static SoulArchive instance;
   private final File saveFile;
   private final Multimap<ResourceKey<Level>, SoulArchive.SoulData> data = HashMultimap.create();

   public SoulArchive(File saveFolder) {
      Preconditions.checkArgument(saveFolder.exists() && saveFolder.isDirectory(), "File " + saveFolder + " does not exist or is not a folder!");
      this.saveFile = new File(saveFolder, "soul_archive.json");
   }

   public Optional<Tuple<UUID, BlockPos>> findNearest(Level level, BlockPos pos) {
      SoulArchive.SoulData data = this.data
         .get(level.m_46472_())
         .stream()
         .filter(record -> record.type == 0)
         .reduce((record1, record2) -> pos.m_123331_(record1.pos) > pos.m_123331_(record2.pos) ? record2 : record1)
         .orElse(null);
      return data != null ? Optional.of(new Tuple(data.id, data.pos)) : Optional.empty();
   }

   public void save() {
      try {
         try (OutputStream out = FileUtils.openOutputStream(this.saveFile, false)) {
            IOUtils.write(this.saveToBytes(), out);
         }
      } catch (Exception var6) {
         EnigmaticLegacy.LOGGER.fatal("FAILED TO SAVE FILE: " + this.saveFile);
         throw new RuntimeException(var6);
      }
   }

   public void load() {
      try {
         if (this.saveFile.exists() && this.saveFile.isFile()) {
            try (InputStream in = FileUtils.openInputStream(this.saveFile)) {
               byte[] bytes = in.readAllBytes();
               this.loadFromBytes(bytes);
            }
         }
      } catch (Exception var6) {
         EnigmaticLegacy.LOGGER.fatal("FAILED TO LOAD FILE: " + this.saveFile);
         throw new RuntimeException(var6);
      }
   }

   private byte[] saveToBytes() {
      String text = new GsonBuilder().setPrettyPrinting().create().toJson(new ArrayList(this.data.values()));
      return text.getBytes(UTF8);
   }

   private void loadFromBytes(byte[] bytes) {
      this.data.clear();
      String text = new String(bytes, UTF8);
      List<SoulArchive.SoulData> list = (List<SoulArchive.SoulData>)new Gson().fromJson(text, SOUL_RECORDS_TYPE);
      list.forEach(record -> {
         ResourceKey<Level> key = ResourceKey.m_135785_(Registry.f_122819_, record.dimension);
         this.data.put(key, record);
      });
   }

   private void synchronize() {
      ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_().forEach(player -> {
         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.SOUL_COMPASS)) {
            SuperpositionHandler.updateSoulCompass(player);
         }
      });
   }

   public void addItem(PermanentItemEntity item) {
      SoulArchive.SoulData record = new SoulArchive.SoulData(
         item.f_19853_.m_46472_().m_135782_(), item.m_20148_(), item.getOwnerId(), item.m_20183_(), item.containsSoul() ? 0 : 1
      );
      if (this.data.put(item.f_19853_.m_46472_(), record)) {
         this.save();
         this.synchronize();
      }
   }

   public void removeItem(UUID id) {
      if (this.data.values().removeIf(record -> record.id.equals(id))) {
         this.save();
         this.synchronize();
      }
   }

   public void removeItem(PermanentItemEntity item) {
      if (this.data.values().removeIf(record -> record.isEqual(item))) {
         this.save();
         this.synchronize();
      }
   }

   public static SoulArchive getInstance() {
      return instance;
   }

   public static void initialize(MinecraftServer server) {
      instance = new SoulArchive(server.m_129843_(LevelResource.f_78182_).toFile());
      instance.load();
   }

   private static class SoulData {
      private ResourceLocation dimension;
      private UUID id;
      private UUID ownerID;
      private BlockPos pos;
      private int type;

      SoulData(ResourceLocation dimension, UUID id, UUID ownerID, BlockPos pos, int type) {
         this.dimension = dimension;
         this.id = id;
         this.ownerID = ownerID;
         this.pos = pos;
         this.type = type;
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.dimension, this.id, this.ownerID, this.pos, this.type);
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            SoulArchive.SoulData other = (SoulArchive.SoulData)obj;
            return Objects.equals(this.dimension, other.dimension)
               && Objects.equals(this.id, other.id)
               && Objects.equals(this.ownerID, other.ownerID)
               && Objects.equals(this.pos, other.pos)
               && this.type == other.type;
         }
      }

      @Override
      public String toString() {
         return "SoulData [dimension=" + this.dimension + ", id=" + this.id + ", ownerID=" + this.ownerID + ", pos=" + this.pos + ", type=" + this.type + "]";
      }

      boolean isEqual(PermanentItemEntity item) {
         return Objects.equals(this.id, item.m_20148_()) && Objects.equals(this.ownerID, item.getOwnerId());
      }
   }
}
