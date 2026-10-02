package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

public interface IPlaceableFeature {
   double distanceFromOthers();

   boolean onPlace(Level var1, BlockPos var2, FeaturePlacementRitual var3, RitualBrazierTile var4);

   String getFeatureName();

   default Pair<BlockPos, BlockPos> getCustomOffsets() {
      return new Pair(BlockPos.f_121853_, BlockPos.f_121853_);
   }
}
