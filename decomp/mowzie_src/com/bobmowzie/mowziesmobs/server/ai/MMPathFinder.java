package com.bobmowzie.mowziesmobs.server.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class MMPathFinder extends PathFinder {
   public MMPathFinder(NodeEvaluator processor, int maxVisitedNodes) {
      super(processor, maxVisitedNodes);
   }

   @Nullable
   public Path m_77427_(PathNavigationRegion regionIn, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
      Path path = super.m_77427_(regionIn, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
      return path == null ? null : new MMPathFinder.PatchedPath(path);
   }

   static class PatchedPath extends Path {
      public PatchedPath(Path original) {
         super(copyPathPoints(original), original.m_77406_(), original.m_77403_());
      }

      public Vec3 m_77382_(Entity entity, int index) {
         Node point = this.m_77375_(index);
         double d0 = (double)point.f_77271_ + (double)Mth.m_14143_(entity.m_20205_() + 1.0F) * 0.5;
         double d1 = (double)point.f_77272_;
         double d2 = (double)point.f_77273_ + (double)Mth.m_14143_(entity.m_20205_() + 1.0F) * 0.5;
         return new Vec3(d0, d1, d2);
      }

      private static List<Node> copyPathPoints(Path original) {
         List<Node> points = new ArrayList<>();

         for (int i = 0; i < original.m_77398_(); i++) {
            points.add(original.m_77375_(i));
         }

         return points;
      }
   }
}
