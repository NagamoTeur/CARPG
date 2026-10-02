package com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.ModNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class PathJobMoveToLocation extends AbstractPathJob {
   public static final float DESTINATION_SLACK_NONE = 0.1F;
   public static final float DESTINATION_SLACK_ADJACENT = (float)Math.sqrt(2.0);
   public BlockPos destination;
   public float destinationSlack = 0.1F;

   public PathJobMoveToLocation(Level world, BlockPos start, BlockPos end, int range, LivingEntity entity) {
      super(world, start, end, range, entity);
      this.destination = new BlockPos(end);
   }

   @Override
   protected Path search() {
      if (this.getGroundHeight(null, this.destination) != this.destination.m_123342_()) {
         this.destinationSlack = DESTINATION_SLACK_ADJACENT;
      }

      return super.search();
   }

   @Override
   protected BlockPos getPathTargetPos(ModNode finalNode) {
      return this.destination;
   }

   @Override
   protected double computeHeuristic(BlockPos pos) {
      return Math.sqrt(this.destination.m_123331_(pos));
   }

   @Override
   protected boolean isAtDestination(ModNode n) {
      if (!(this.destinationSlack <= 0.1F)) {
         return n.pos.m_123342_() == this.destination.m_123342_() - 1
            ? this.destination.m_123314_(new Vec3i(n.pos.m_123341_(), this.destination.m_123342_(), n.pos.m_123343_()), (double)DESTINATION_SLACK_ADJACENT)
            : this.destination.m_123314_(n.pos, (double)DESTINATION_SLACK_ADJACENT);
      } else {
         return n.pos.m_123341_() == this.destination.m_123341_()
            && n.pos.m_123342_() == this.destination.m_123342_()
            && n.pos.m_123343_() == this.destination.m_123343_();
      }
   }

   @Override
   protected double getNodeResultScore(ModNode n) {
      return (double)this.destination.m_123333_(n.pos);
   }
}
