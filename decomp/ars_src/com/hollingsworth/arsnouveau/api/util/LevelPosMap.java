package com.hollingsworth.arsnouveau.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LevelPosMap {
   public Map<String, Set<BlockPos>> posMap = new ConcurrentHashMap<>();
   public BiFunction<Level, BlockPos, Boolean> removeFunction;

   public LevelPosMap(BiFunction<Level, BlockPos, Boolean> removeFunction) {
      this.removeFunction = removeFunction;
   }

   public void addPosition(Level world, BlockPos pos) {
      String key = world.m_46472_().m_135782_().toString();
      if (!this.posMap.containsKey(key)) {
         this.posMap.put(key, new HashSet<>());
      }

      this.posMap.get(key).add(pos);
   }

   public void applyForRange(Level level, BlockPos atPos, double distanceFrom, Function<BlockPos, Boolean> breakEarlyFunction) {
      this.applyForRange(level, new Vec3((double)atPos.m_123341_(), (double)atPos.m_123342_(), (double)atPos.m_123343_()), distanceFrom, breakEarlyFunction);
   }

   public void applyForRange(Level level, Vec3 atPos, double distanceFrom, Function<BlockPos, Boolean> breakEarlyFunction) {
      String key = level.m_46472_().m_135782_().toString();
      if (this.posMap.containsKey(key)) {
         Set<BlockPos> worldList = this.posMap.getOrDefault(key, new HashSet<>());
         List<BlockPos> stale = new ArrayList<>();

         for (BlockPos p : worldList) {
            if (level.m_46749_(p)
               && BlockUtil.distanceFrom(atPos, new Vec3((double)p.m_123341_() + 0.5, (double)p.m_123342_() + 0.5, (double)p.m_123343_() + 0.5))
                  <= distanceFrom) {
               if (this.removeFunction.apply(level, p)) {
                  stale.add(p);
               } else if (breakEarlyFunction.apply(p)) {
                  break;
               }
            }
         }

         for (BlockPos pos : stale) {
            this.posMap.get(key).remove(pos);
         }
      }
   }
}
