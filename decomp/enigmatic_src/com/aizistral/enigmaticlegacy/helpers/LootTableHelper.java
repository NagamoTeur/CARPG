package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootTableHelper {
   public static Field isFrozenTable = null;
   public static Field isFrozenPool = null;
   public static Field lootPoolsTable = null;

   public static void unfreezePlease(LootTable table) {
      try {
         isFrozenTable.set(table, false);

         for (LootPool pool : (List)lootPoolsTable.get(table)) {
            unfreezePlease(pool);
         }
      } catch (Throwable var4) {
         EnigmaticLegacy.LOGGER.fatal("FAILED TO UNFREEZE LOOT TABLE");
         throw new RuntimeException(var4);
      }
   }

   public static void unfreezePlease(LootPool pool) {
      try {
         isFrozenPool.set(pool, false);
      } catch (Throwable var2) {
         EnigmaticLegacy.LOGGER.fatal("FAILED TO UNFREEZE LOOT POOL");
         throw new RuntimeException(var2);
      }
   }

   static {
      try {
         isFrozenTable = LootTable.class.getDeclaredField("isFrozen");
         isFrozenPool = LootPool.class.getDeclaredField("isFrozen");

         try {
            lootPoolsTable = LootTable.class.getDeclaredField("pools");
         } catch (NoSuchFieldException var1) {
            lootPoolsTable = LootTable.class.getDeclaredField("f_79109_");
         }

         isFrozenTable.setAccessible(true);
         isFrozenPool.setAccessible(true);
         lootPoolsTable.setAccessible(true);
      } catch (Throwable var2) {
         EnigmaticLegacy.LOGGER.fatal("FAILED TO REFLECT LOOTTABLE FIELDS");
         EnigmaticLegacy.LOGGER.catching(var2);
         throw new RuntimeException(var2);
      }
   }
}
