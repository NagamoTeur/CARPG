package io.redspace.ironsspellbooks.data;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import java.io.File;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.jetbrains.annotations.NotNull;

public class DataFixerStorage extends SavedData {
   public static DataFixerStorage INSTANCE;
   private DimensionDataStorage overworldDataStorage;
   private int dataVersion;

   public static void init(LevelStorageAccess levelStorageAccess) {
      DataFixer dataFixer = new DataFixerBuilder(1).buildUnoptimized();
      File file = levelStorageAccess.m_197394_(Level.f_46428_).resolve("data").toFile();

      try {
         if (!file.exists()) {
            file.mkdir();
         }
      } catch (Exception var4) {
      }

      DimensionDataStorage overworldDataStorage = new DimensionDataStorage(file, dataFixer);
      INSTANCE = (DataFixerStorage)overworldDataStorage.m_164861_(DataFixerStorage::load, DataFixerStorage::new, "irons_spellbooks");
      INSTANCE.overworldDataStorage = overworldDataStorage;
   }

   public DataFixerStorage() {
      this.dataVersion = 0;
   }

   public DataFixerStorage(int dataVersion) {
      this.dataVersion = dataVersion;
   }

   public int getDataVersion() {
      return this.dataVersion;
   }

   public void setDataVersion(int dataVersion) {
      this.dataVersion = dataVersion;
      this.m_77762_();
      this.overworldDataStorage.m_78151_();
   }

   @NotNull
   public CompoundTag m_7176_(@NotNull CompoundTag pCompoundTag) {
      CompoundTag tag = new CompoundTag();
      tag.m_128405_("dataVersion", this.dataVersion);
      return tag;
   }

   public static DataFixerStorage load(CompoundTag tag) {
      int dataVersion = tag.m_128451_("dataVersion");
      return new DataFixerStorage(dataVersion);
   }
}
