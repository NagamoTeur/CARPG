package daripher.autoleveling.saveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class GlobalLevelingData extends SavedData {
   private int levelBonus;

   private static GlobalLevelingData create() {
      return new GlobalLevelingData();
   }

   private static GlobalLevelingData load(CompoundTag tag) {
      GlobalLevelingData data = create();
      data.levelBonus = tag.m_128451_("LevelBonus");
      return data;
   }

   public static GlobalLevelingData get(MinecraftServer server) {
      return (GlobalLevelingData)server.m_129783_().m_8895_().m_164861_(GlobalLevelingData::load, GlobalLevelingData::create, "global_leveling");
   }

   @NotNull
   public CompoundTag m_7176_(CompoundTag tag) {
      tag.m_128405_("LevelBonus", this.levelBonus);
      return tag;
   }

   public void setLevel(int level) {
      this.levelBonus = level;
      this.m_77762_();
   }

   public int getLevelBonus() {
      return this.levelBonus;
   }
}
