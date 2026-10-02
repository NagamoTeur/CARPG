package net.xylonity.knightquest.common.ai.navigator;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
import org.jetbrains.annotations.NotNull;

public class BonusPathFinder extends PathFinder {
   public BonusPathFinder(NodeEvaluator processor, int maxVisitedNodes) {
      super(processor, maxVisitedNodes);
   }

   @Nullable
   public Path m_77427_(
      @NotNull PathNavigationRegion region, @NotNull Mob mob, @NotNull Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier
   ) {
      Path path = super.m_77427_(region, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
      return path == null ? null : new BonusPathFinder.CachedPath(path);
   }

   static class CachedPath extends Path {
      private final Cache<Integer, Vec3> cache = CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(5L, TimeUnit.SECONDS).build();

      public CachedPath(Path original) {
         super(copyNodes(original), original.m_77406_(), original.m_77403_());
      }

      @NotNull
      public Vec3 m_77382_(@NotNull Entity entity, int index) {
         try {
            return (Vec3)this.cache.get(index, () -> this.computeEntityPosAtNode(entity, index));
         } catch (Exception var4) {
            return this.computeEntityPosAtNode(entity, index);
         }
      }

      private Vec3 computeEntityPosAtNode(Entity entity, int index) {
         Node point = this.m_77375_(index);
         double offsetX = (double)Mth.m_14143_(entity.m_20205_() + 1.0F) * 0.5;
         double x = (double)point.f_77271_ + offsetX;
         double y = (double)point.f_77272_;
         double z = (double)point.f_77273_ + offsetX;
         return new Vec3(x, y, z);
      }

      private static List<Node> copyNodes(Path original) {
         return IntStream.range(0, original.m_77398_())
            .mapToObj(original::m_77375_)
            .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
      }
   }
}
