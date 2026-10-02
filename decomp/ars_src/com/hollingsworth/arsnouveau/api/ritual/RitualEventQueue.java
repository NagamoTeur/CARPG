package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RitualEventQueue {
   public static Map<String, Set<BlockPos>> posMap = new HashMap<>();

   public static void addPosition(Level world, BlockPos pos) {
      String key = world.m_46472_().m_135782_().toString();
      if (!posMap.containsKey(key)) {
         posMap.put(key, new HashSet<>());
      }

      posMap.get(key).add(pos);
   }

   public static boolean containsPosition(Level world, BlockPos pos) {
      String key = world.m_46472_().m_135782_().toString();
      return !posMap.containsKey(key) ? false : posMap.get(key).contains(pos);
   }

   public static <T extends RangeRitual> List<T> getRituals(Level level, Class<T> type) {
      List<T> rituals = new ArrayList<>();
      Set<BlockPos> worldList = posMap.getOrDefault(level.m_46472_().m_135782_().toString(), new HashSet<>());
      List<BlockPos> stalePos = new ArrayList<>();

      for (BlockPos p : worldList) {
         if (level.m_46749_(p)) {
            BlockEntity entity = level.m_7702_(p);
            if (entity instanceof RitualBrazierTile) {
               RitualBrazierTile brazierTile = (RitualBrazierTile)entity;
               AbstractRitual ritual = brazierTile.ritual;
               if (ritual != null && ritual.getClass().equals(type)) {
                  rituals.add((T)ritual);
               }
            } else {
               stalePos.add(p);
            }
         }
      }

      return rituals;
   }

   @Nullable
   public static <T extends RangeRitual> T getRitual(Level level, Class<T> type, Predicate<T> isMatch) {
      Set<BlockPos> worldList = posMap.getOrDefault(level.m_46472_().m_135782_().toString(), new HashSet<>());
      List<BlockPos> stalePos = new ArrayList<>();

      for (BlockPos p : worldList) {
         if (level.m_46749_(p)) {
            if (level.m_7702_(p) instanceof RitualBrazierTile brazierTile) {
               AbstractRitual ritual = brazierTile.ritual;
               if (ritual != null && ritual.getClass().equals(type) && isMatch.test((T)ritual)) {
                  return (T)ritual;
               }
            } else {
               stalePos.add(p);
            }
         }
      }

      for (BlockPos px : stalePos) {
         worldList.remove(px);
      }

      return null;
   }
}
