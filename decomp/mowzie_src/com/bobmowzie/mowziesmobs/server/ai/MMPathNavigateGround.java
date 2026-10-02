package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.Objects;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class MMPathNavigateGround extends GroundPathNavigation {
   static final float EPSILON = 1.0E-8F;

   public MMPathNavigateGround(MowzieEntity entity, Level world) {
      super(entity, world);
   }

   protected PathFinder m_5532_(int maxVisitedNodes) {
      this.f_26508_ = new MMWalkNodeProcessor();
      this.f_26508_.m_77351_(true);
      return new MMPathFinder(this.f_26508_, maxVisitedNodes);
   }

   protected void m_7636_() {
      Path path = Objects.requireNonNull(this.f_26496_);
      Vec3 entityPos = this.m_7475_();
      int pathLength = path.m_77398_();

      for (int i = path.m_77399_(); i < path.m_77398_(); i++) {
         if ((double)path.m_77375_(i).f_77272_ != Math.floor(entityPos.f_82480_)) {
            pathLength = i;
            break;
         }
      }

      Vec3 base = entityPos.m_82520_((double)(-this.f_26494_.m_20205_() * 0.5F), 0.0, (double)(-this.f_26494_.m_20205_() * 0.5F));
      Vec3 max = base.m_82520_((double)this.f_26494_.m_20205_(), (double)this.f_26494_.m_20206_(), (double)this.f_26494_.m_20205_());
      if (this.tryShortcut(path, new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20186_(), this.f_26494_.m_20189_()), pathLength, base, max)
         && (this.isAt(path, 0.5F) || this.atElevationChange(path) && this.isAt(path, this.f_26494_.m_20205_() * 0.5F))) {
         path.m_77393_(path.m_77399_() + 1);
      }

      this.m_6481_(entityPos);
   }

   private boolean isAt(Path path, float threshold) {
      Vec3 pathPos = path.m_77380_(this.f_26494_);
      return Mth.m_14154_((float)(this.f_26494_.m_20185_() - pathPos.f_82479_)) < threshold
         && Mth.m_14154_((float)(this.f_26494_.m_20189_() - pathPos.f_82481_)) < threshold
         && Math.abs(this.f_26494_.m_20186_() - pathPos.f_82480_) < 1.0;
   }

   private boolean atElevationChange(Path path) {
      int curr = path.m_77399_();
      int end = Math.min(path.m_77398_(), curr + Mth.m_14167_(this.f_26494_.m_20205_() * 0.5F) + 1);
      int currY = path.m_77375_(curr).f_77272_;

      for (int i = curr + 1; i < end; i++) {
         if (path.m_77375_(i).f_77272_ != currY) {
            return true;
         }
      }

      return false;
   }

   private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
      int i = pathLength;

      while (--i > path.m_77399_()) {
         Vec3 vec = path.m_77382_(this.f_26494_, i).m_82546_(entityPos);
         if (this.sweep(vec, base, max)) {
            path.m_77393_(i);
            return false;
         }
      }

      return true;
   }

   private boolean sweep(Vec3 vec, Vec3 base, Vec3 max) {
      float t = 0.0F;
      float max_t = (float)vec.m_82553_();
      if (max_t < 1.0E-8F) {
         return true;
      } else {
         float[] tr = new float[3];
         int[] ldi = new int[3];
         int[] tri = new int[3];
         int[] step = new int[3];
         float[] tDelta = new float[3];
         float[] tNext = new float[3];
         float[] normed = new float[3];

         for (int i = 0; i < 3; i++) {
            float value = element(vec, i);
            boolean dir = value >= 0.0F;
            step[i] = dir ? 1 : -1;
            float lead = element(dir ? max : base, i);
            tr[i] = element(dir ? base : max, i);
            ldi[i] = leadEdgeToInt(lead, step[i]);
            tri[i] = trailEdgeToInt(tr[i], step[i]);
            normed[i] = value / max_t;
            tDelta[i] = Mth.m_14154_(max_t / value);
            float dist = dir ? (float)(ldi[i] + 1) - lead : lead - (float)ldi[i];
            tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
         }

         MutableBlockPos pos = new MutableBlockPos();

         do {
            int axis = tNext[0] < tNext[1] ? (tNext[0] < tNext[2] ? 0 : 2) : (tNext[1] < tNext[2] ? 1 : 2);
            float dt = tNext[axis] - t;
            t = tNext[axis];
            ldi[axis] += step[axis];
            tNext[axis] += tDelta[axis];

            for (int i = 0; i < 3; i++) {
               tr[i] += dt * normed[i];
               tri[i] = trailEdgeToInt(tr[i], step[i]);
            }

            int stepx = step[0];
            int x0 = axis == 0 ? ldi[0] : tri[0];
            int x1 = ldi[0] + stepx;
            int stepy = step[1];
            int y0 = axis == 1 ? ldi[1] : tri[1];
            int y1 = ldi[1] + stepy;
            int stepz = step[2];
            int z0 = axis == 2 ? ldi[2] : tri[2];
            int z1 = ldi[2] + stepz;

            for (int x = x0; x != x1; x += stepx) {
               int z = z0;

               while (z != z1) {
                  for (int y = y0; y != y1; y += stepy) {
                     BlockState block = this.f_26495_.m_8055_(pos.m_122178_(x, y, z));
                     if (!block.m_60647_(this.f_26495_, pos, PathComputationType.LAND)) {
                        return false;
                     }
                  }

                  BlockPathTypes below = this.f_26508_.m_7209_(this.f_26495_, x, y0 - 1, z, this.f_26494_, 1, 1, 1, true, true);
                  if (below != BlockPathTypes.WATER && below != BlockPathTypes.LAVA && below != BlockPathTypes.OPEN) {
                     BlockPathTypes in = this.f_26508_.m_7209_(this.f_26495_, x, y0, z, this.f_26494_, 1, y1 - y0, 1, true, true);
                     float priority = this.f_26494_.m_21439_(in);
                     if (!(priority < 0.0F) && !(priority >= 8.0F)) {
                        if (in != BlockPathTypes.DAMAGE_FIRE && in != BlockPathTypes.DANGER_FIRE && in != BlockPathTypes.DAMAGE_OTHER) {
                           z += stepz;
                           continue;
                        }

                        return false;
                     }

                     return false;
                  }

                  return false;
               }
            }
         } while (t <= max_t);

         return true;
      }
   }

   static int leadEdgeToInt(float coord, int step) {
      return Mth.m_14143_(coord - (float)step * 1.0E-8F);
   }

   static int trailEdgeToInt(float coord, int step) {
      return Mth.m_14143_(coord + (float)step * 1.0E-8F);
   }

   static float element(Vec3 v, int i) {
      switch (i) {
         case 0:
            return (float)v.f_82479_;
         case 1:
            return (float)v.f_82480_;
         case 2:
            return (float)v.f_82481_;
         default:
            return 0.0F;
      }
   }
}
