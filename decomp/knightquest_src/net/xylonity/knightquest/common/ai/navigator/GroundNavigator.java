package net.xylonity.knightquest.common.ai.navigator;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GroundNavigator extends GroundPathNavigation {
   private static final float THETA = 1.0E-8F;
   private final Cache<BlockPos, Boolean> cache = CacheBuilder.newBuilder().maximumSize(10000L).expireAfterAccess(5L, TimeUnit.SECONDS).build();

   public GroundNavigator(Mob entity, Level world) {
      super(entity, world);
   }

   @NotNull
   protected PathFinder m_5532_(int maxVisitedNodes) {
      this.f_26508_ = new WalkNodeEvaluator();
      this.f_26508_.m_77351_(true);
      return new BonusPathFinder(this.f_26508_, maxVisitedNodes);
   }

   protected void m_7636_() {
      if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
         Vec3 entityPos = this.m_7475_();
         int nextNodeIndex = this.f_26496_.m_77399_();
         double entityYFloor = Math.floor(entityPos.f_82480_);
         int pathLength = this.f_26496_.m_77398_();
         int lastNodeIndex = nextNodeIndex;

         while (lastNodeIndex < pathLength && (double)this.f_26496_.m_77375_(lastNodeIndex).f_77272_ == entityYFloor) {
            lastNodeIndex++;
         }

         Vec3 base = entityPos.m_82492_((double)(this.f_26494_.m_20205_() * 0.5F), 0.0, (double)(this.f_26494_.m_20205_() * 0.5F));
         if (this.attemptShortcut(this.f_26496_, entityPos, lastNodeIndex, base)
            && (
               this.hasReached(this.f_26496_, 0.6F)
                  || this.isAtElevationChange(this.f_26496_) && this.hasReached(this.f_26496_, this.f_26494_.m_20205_() * 0.5F)
            )) {
            this.f_26496_.m_77374_();
         }

         this.m_6481_(entityPos);
      }
   }

   private boolean hasReached(Path path, float threshold) {
      Vec3 pathPos = path.m_77380_(this.f_26494_);
      return Math.abs(this.f_26494_.m_20185_() - pathPos.f_82479_) < (double)threshold
         && Math.abs(this.f_26494_.m_20189_() - pathPos.f_82481_) < (double)threshold
         && Math.abs(this.f_26494_.m_20186_() - pathPos.f_82480_) < 1.0;
   }

   private boolean isAtElevationChange(Path path) {
      int currIndex = path.m_77399_();
      int endIndex = Math.min(path.m_77398_(), currIndex + Mth.m_14167_(this.f_26494_.m_20205_() * 0.5F) + 1);
      int currY = path.m_77375_(currIndex).f_77272_;

      for (int i = currIndex + 1; i < endIndex; i++) {
         if (path.m_77375_(i).f_77272_ != currY) {
            return true;
         }
      }

      return false;
   }

   private boolean attemptShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base) {
      int nextNodeIndex = path.m_77399_();

      for (int i = pathLength - 1; i > nextNodeIndex; i--) {
         Vec3 vec = path.m_77382_(this.f_26494_, i).m_82546_(entityPos);
         if (this.catchF(vec, base)) {
            path.m_77393_(i);
            return false;
         }
      }

      return true;
   }

   private boolean catchF(Vec3 vec, Vec3 base) {
      float maxT = (float)vec.m_82553_();
      if (maxT < 1.0E-8F) {
         return true;
      } else {
         float dx = (float)vec.f_82479_ / maxT;
         float dy = (float)vec.f_82480_ / maxT;
         float dz = (float)vec.f_82481_ / maxT;
         float tNextX;
         float tDeltaX;
         int stepX;
         if (Math.abs(dx) < 1.0E-8F) {
            tDeltaX = Float.POSITIVE_INFINITY;
            tNextX = Float.POSITIVE_INFINITY;
            stepX = 0;
         } else {
            stepX = dx > 0.0F ? 1 : -1;
            float voxelBoundaryX = stepX > 0 ? (float)(Mth.m_14107_(base.f_82479_) + 1) : (float)Mth.m_14107_(base.f_82479_);
            tDeltaX = 1.0F / Math.abs(dx);
            tNextX = (float)(((double)voxelBoundaryX - base.f_82479_) / (double)dx);
         }

         int currentX = Mth.m_14107_(base.f_82479_);
         float tNextY;
         float tDeltaY;
         int stepY;
         if (Math.abs(dy) < 1.0E-8F) {
            tDeltaY = Float.POSITIVE_INFINITY;
            tNextY = Float.POSITIVE_INFINITY;
            stepY = 0;
         } else {
            stepY = dy > 0.0F ? 1 : -1;
            float voxelBoundaryY = stepY > 0 ? (float)(Mth.m_14107_(base.f_82480_) + 1) : (float)Mth.m_14107_(base.f_82480_);
            tDeltaY = 1.0F / Math.abs(dy);
            tNextY = (float)(((double)voxelBoundaryY - base.f_82480_) / (double)dy);
         }

         int currentY = Mth.m_14107_(base.f_82480_);
         float tNextZ;
         float tDeltaZ;
         int stepZ;
         if (Math.abs(dz) < 1.0E-8F) {
            tDeltaZ = Float.POSITIVE_INFINITY;
            tNextZ = Float.POSITIVE_INFINITY;
            stepZ = 0;
         } else {
            stepZ = dz > 0.0F ? 1 : -1;
            float voxelBoundaryZ = stepZ > 0 ? (float)(Mth.m_14107_(base.f_82481_) + 1) : (float)Mth.m_14107_(base.f_82481_);
            tDeltaZ = 1.0F / Math.abs(dz);
            tNextZ = (float)(((double)voxelBoundaryZ - base.f_82481_) / (double)dz);
         }

         int currentZ = Mth.m_14107_(base.f_82481_);
         MutableBlockPos pos = new MutableBlockPos();
         float t = 0.0F;

         while (t <= maxT) {
            if (tNextX < tNextY) {
               if (tNextX < tNextZ) {
                  currentX += stepX;
                  t = tNextX;
                  tNextX += tDeltaX;
               } else {
                  currentZ += stepZ;
                  t = tNextZ;
                  tNextZ += tDeltaZ;
               }
            } else if (tNextY < tNextZ) {
               currentY += stepY;
               t = tNextY;
               tNextY += tDeltaY;
            } else {
               currentZ += stepZ;
               t = tNextZ;
               tNextZ += tDeltaZ;
            }

            pos.m_122178_(currentX, currentY, currentZ);
            BlockPos immutablePos = pos.m_7949_();
            Boolean isPathfindable = (Boolean)this.cache.getIfPresent(immutablePos);
            if (isPathfindable == null) {
               BlockState blockState = this.f_26495_.m_8055_(pos);
               isPathfindable = blockState.m_60647_(this.f_26495_, pos, PathComputationType.LAND);
               this.cache.put(immutablePos, isPathfindable);
            }

            if (!isPathfindable) {
               return false;
            }

            int mobWidth = Mth.m_14167_(this.f_26494_.m_20205_());
            int mobHeight = Mth.m_14167_(this.f_26494_.m_20206_());
            boolean canOpenDoors = this.f_26508_.m_77360_();
            boolean canEnterDoors = this.f_26508_.m_77357_();
            BlockPathTypes pathType = this.f_26508_
               .m_7209_(this.f_26495_, currentX, currentY, currentZ, this.f_26494_, mobWidth, mobHeight, 1, canOpenDoors, canEnterDoors);
            float malus = this.f_26494_.m_21439_(pathType);
            if (malus < 0.0F
               || malus >= 8.0F
               || pathType == BlockPathTypes.DAMAGE_FIRE
               || pathType == BlockPathTypes.DANGER_FIRE
               || pathType == BlockPathTypes.DAMAGE_OTHER) {
               return false;
            }
         }

         return true;
      }
   }
}
