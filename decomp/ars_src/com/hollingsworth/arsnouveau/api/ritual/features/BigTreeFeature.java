package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BigTreeFeature implements IPlaceableFeature {
   BlockState tree;
   double distance;
   double chance;

   public BigTreeFeature(BlockState tree, double distance, double chance) {
      this.tree = tree;
      this.distance = distance;
      this.chance = chance;
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      if ((double)level.f_46441_.m_188501_() < this.chance
         && this.validPos(level, pos)
         && this.validPos(level, pos.m_122012_())
         && this.validPos(level, pos.m_122012_().m_122029_())
         && this.validPos(level, pos.m_122029_())) {
         level.m_7731_(pos, this.tree, 2);
         level.m_7731_(pos.m_122012_(), this.tree, 2);
         level.m_7731_(pos.m_122012_().m_122029_(), this.tree, 2);
         level.m_7731_(pos.m_122029_(), this.tree, 2);
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

   public boolean validPos(Level level, BlockPos pos) {
      return level.m_8055_(pos).m_60767_().m_76336_() && this.tree.m_60710_(level, pos);
   }

   @Override
   public String getFeatureName() {
      return "random_tree";
   }
}
