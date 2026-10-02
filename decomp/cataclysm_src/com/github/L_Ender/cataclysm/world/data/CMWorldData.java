package com.github.L_Ender.cataclysm.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class CMWorldData extends SavedData {
   private static final String IDENTIFIER = "cataclysm_world_data";
   private boolean LeviathanBossDefeatedOnce = false;
   private boolean IgnisBossDefeatedOnce = false;

   private CMWorldData() {
   }

   public static CMWorldData get(Level world, ResourceKey<Level> dim) {
      if (world instanceof ServerLevel) {
         ServerLevel overworld = world.m_7654_().m_129880_(dim);
         DimensionDataStorage storage = overworld.m_8895_();
         CMWorldData data = (CMWorldData)storage.m_164861_(CMWorldData::load, CMWorldData::new, "cataclysm_world_data");
         if (data != null) {
            data.m_77762_();
         }

         return data;
      } else {
         return null;
      }
   }

   public static CMWorldData load(CompoundTag nbt) {
      CMWorldData data = new CMWorldData();
      data.LeviathanBossDefeatedOnce = nbt.m_128471_("LeviathanDefeatedOnce");
      data.IgnisBossDefeatedOnce = nbt.m_128471_("IgnisDefeatedOnce");
      return data;
   }

   public CompoundTag m_7176_(CompoundTag compound) {
      compound.m_128379_("LeviathanDefeatedOnce", this.LeviathanBossDefeatedOnce);
      compound.m_128379_("IgnisDefeatedOnce", this.IgnisBossDefeatedOnce);
      return compound;
   }

   public boolean isLeviathanDefeatedOnce() {
      return this.LeviathanBossDefeatedOnce;
   }

   public void setLeviathanDefeatedOnce(boolean defeatedOnce) {
      this.LeviathanBossDefeatedOnce = defeatedOnce;
   }

   public boolean isIgnisDefeatedOnce() {
      return this.IgnisBossDefeatedOnce;
   }

   public void setIgnisDefeatedOnce(boolean defeatedOnce) {
      this.IgnisBossDefeatedOnce = defeatedOnce;
   }
}
