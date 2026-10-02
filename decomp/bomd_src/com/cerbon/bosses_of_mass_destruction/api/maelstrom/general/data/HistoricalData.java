package com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data;

import java.util.ArrayList;
import java.util.List;

public class HistoricalData<T> implements IHistoricalData<T> {
   private final List<T> history;
   private final int maxHistory;

   public HistoricalData(T defaultValue, int maxHistory) {
      if (maxHistory < 2) {
         throw new IllegalArgumentException("Max History cannot be less than 2");
      } else {
         this.maxHistory = maxHistory;
         this.history = new ArrayList<>();
         this.history.add(defaultValue);
      }
   }

   @Override
   public void set(T value) {
      this.history.add(value);
      if (this.history.size() > this.maxHistory) {
         this.history.remove(0);
      }
   }

   @Override
   public T get(int past) {
      if (past < 0) {
         throw new IllegalArgumentException("Past cannot be negative");
      } else {
         int clampedPast = Math.max(this.history.size() - 1 - past, 0);
         return this.history.get(clampedPast);
      }
   }

   public List<T> getAll() {
      return new ArrayList<>(this.history);
   }

   public int getSize() {
      return this.history.size();
   }

   public void clear() {
      this.history.clear();
   }
}
