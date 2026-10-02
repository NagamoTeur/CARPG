package com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class PathJobMoveToPathable extends PathJobMoveToLocation {
   public List<BlockPos> destinations;

   public PathJobMoveToPathable(Level world, BlockPos start, List<BlockPos> destinations, int range, LivingEntity entity) {
      super(world, start, destinations.size() == 0 ? start : destinations.get(0), range, entity);
      this.destinations = destinations;
   }

   @Override
   protected Path search() {
      return super.search();
   }
}
