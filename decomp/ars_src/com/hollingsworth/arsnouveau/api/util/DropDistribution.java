package com.hollingsworth.arsnouveau.api.util;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class DropDistribution<T> {
   private int totalNum;
   private final Map<T, Integer> map;
   public static final Random rand = new Random();

   public DropDistribution(Map<T, Integer> map) {
      for (Integer val : map.values()) {
         this.totalNum = this.totalNum + val;
      }

      this.map = map;
   }

   @Nullable
   public T nextDrop() {
      if (this.totalNum <= 0) {
         return null;
      } else {
         int gen = rand.nextInt(this.totalNum) + 1;
         int counter = 0;

         for (Entry<T, Integer> entry : this.map.entrySet()) {
            counter += entry.getValue();
            if (gen <= counter) {
               return entry.getKey();
            }
         }

         return null;
      }
   }
}
