package com.hollingsworth.arsnouveau.api.source;

import com.hollingsworth.arsnouveau.common.block.tile.SourcelinkTile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.eventbus.api.Event;

public class SourcelinkEventQueue {
   public static Map<String, Set<BlockPos>> posMap = new ConcurrentHashMap<>();

   public static void addPosition(Level world, BlockPos pos) {
      String key = world.m_46472_().m_135782_().toString();
      if (!posMap.containsKey(key)) {
         posMap.put(key, new HashSet<>());
      }

      posMap.get(key).add(pos);
   }

   public static void addManaEvent(Level world, Class<? extends SourcelinkTile> tileType, int amount, Event event, BlockPos sourcePos) {
      List<BlockPos> stalePos = new ArrayList<>();
      Set<BlockPos> worldList = posMap.getOrDefault(world.m_46472_().m_135782_().toString(), new HashSet<>());

      for (BlockPos p : worldList) {
         if (world.m_46749_(p)) {
            BlockEntity entity = world.m_7702_(p);
            if (world.m_7702_(p) != null && entity instanceof SourcelinkTile) {
               if (entity.getClass().equals(tileType) && ((SourcelinkTile)entity).eventInRange(sourcePos, event) && ((SourcelinkTile)entity).canAcceptSource()) {
                  ((SourcelinkTile)entity).getManaEvent(sourcePos, amount);
                  break;
               }
            } else {
               stalePos.add(p);
            }
         }
      }

      for (BlockPos px : stalePos) {
         worldList.remove(px);
      }
   }
}
