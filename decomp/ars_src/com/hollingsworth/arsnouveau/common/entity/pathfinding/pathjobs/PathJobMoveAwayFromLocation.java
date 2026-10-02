package com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PathJobMoveAwayFromLocation extends AbstractPathJob {
   @NotNull
   protected final BlockPos avoid;
   protected final int avoidDistance;

   public PathJobMoveAwayFromLocation(Level world, @NotNull BlockPos start, @NotNull BlockPos avoid, int avoidDistance, int range, LivingEntity entity) {
      super(world, start, avoid, range, entity);
      this.avoid = new BlockPos(avoid);
      this.avoidDistance = avoidDistance;
   }

   @Override
   protected double computeHeuristic(@NotNull BlockPos pos) {
      return -this.avoid.m_123331_(pos);
   }

   @Override
   protected boolean isAtDestination(@NotNull ModNode n) {
      return Math.sqrt(this.avoid.m_123331_(n.pos)) > (double)this.avoidDistance;
   }

   @Override
   protected double getNodeResultScore(@NotNull ModNode n) {
      return -this.avoid.m_123331_(n.pos);
   }
}
