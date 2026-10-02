package com.aizistral.enigmaticlegacy.api.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IPlaytimeCounter extends INBTSerializable<CompoundTag> {
   IPlaytimeCounter DEFAULT = new IPlaytimeCounter() {
      @Override
      public void setTimeWithoutCurses(long time) {
      }

      @Override
      public void setTimeWithCurses(long time) {
      }

      @Override
      public void incrementTimeWithoutCurses() {
      }

      @Override
      public void incrementTimeWithCurses() {
      }

      @Override
      public long getTimeWithoutCurses() {
         return 0L;
      }

      @Override
      public long getTimeWithCurses() {
         return 1L;
      }

      @Override
      public void matchStats() {
      }

      @Override
      public CompoundTag serializeNBT() {
         return new CompoundTag();
      }

      @Override
      public void deserializeNBT(CompoundTag nbt) {
      }
   };

   static IPlaytimeCounter get(Player player) {
      return (IPlaytimeCounter)player.getCapability(EnigmaticCapabilities.PLAYTIME_COUNTER).orElse(DEFAULT);
   }

   long getTimeWithoutCurses();

   long getTimeWithCurses();

   void setTimeWithoutCurses(long var1);

   void setTimeWithCurses(long var1);

   void incrementTimeWithoutCurses();

   void incrementTimeWithCurses();

   void matchStats();

   CompoundTag serializeNBT();

   void deserializeNBT(CompoundTag var1);
}
