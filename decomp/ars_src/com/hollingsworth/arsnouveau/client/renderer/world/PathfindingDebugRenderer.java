package com.hollingsworth.arsnouveau.client.renderer.world;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import com.hollingsworth.arsnouveau.common.util.Log;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class PathfindingDebugRenderer {
   public static Set<ModNode> lastDebugNodesVisited = new HashSet<>();
   public static Set<ModNode> lastDebugNodesNotVisited = new HashSet<>();
   public static Set<ModNode> lastDebugNodesPath = new HashSet<>();

   public static void render(WorldEventContext ctx) {
      try {
         for (ModNode n : lastDebugNodesVisited) {
            debugDrawNode(n, -65536, ctx);
         }

         for (ModNode n : lastDebugNodesNotVisited) {
            debugDrawNode(n, -16776961, ctx);
         }

         for (ModNode n : lastDebugNodesPath) {
            if (n.isReachedByWorker()) {
               debugDrawNode(n, -39424, ctx);
            } else {
               debugDrawNode(n, -16711936, ctx);
            }
         }
      } catch (ConcurrentModificationException var3) {
         Log.getLogger().catching(var3);
      }
   }

   private static void debugDrawNode(ModNode n, int argbColor, WorldEventContext ctx) {
   }

   private static void renderDebugText(@NotNull ModNode n, WorldEventContext ctx) {
   }
}
