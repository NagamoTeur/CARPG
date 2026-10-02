package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RandomTreeFeature implements IPlaceableFeature {
   List<BlockState> treeStates;
   double distance;
   double chance;

   public RandomTreeFeature(List<BlockState> treeStates, double distance, double chance) {
      this.treeStates = treeStates;
      this.distance = distance;
      this.chance = chance;
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      BlockState treeState = this.treeStates.get(level.f_46441_.m_188503_(this.treeStates.size()));
      if (level.m_8055_(pos).m_60767_().m_76336_() && treeState.m_60710_(level, pos)) {
         level.m_7731_(pos, treeState, 3);
         if (level.m_8055_(pos).m_60734_() instanceof SaplingBlock saplingBlock) {
            saplingBlock.m_222000_((ServerLevel)level, pos, level.m_8055_(pos), level.f_46441_);
         }

         if (level.m_8055_(pos).m_60734_() instanceof SaplingBlock saplingBlock) {
            saplingBlock.m_222000_((ServerLevel)level, pos, level.m_8055_(pos), level.f_46441_);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public String getFeatureName() {
      return "random_tree";
   }
}
