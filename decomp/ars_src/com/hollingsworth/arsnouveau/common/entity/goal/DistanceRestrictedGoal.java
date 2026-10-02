package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class DistanceRestrictedGoal extends Goal {
   public Supplier<BlockPos> positionFrom;
   public int maxDistance;

   public DistanceRestrictedGoal(Supplier<BlockPos> pos, int maxDistance) {
      this.positionFrom = pos;
      this.maxDistance = maxDistance;
   }

   public boolean isInRange(BlockPos pos) {
      return BlockUtil.distanceFrom(pos, this.positionFrom.get()) <= (double)this.maxDistance;
   }
}
