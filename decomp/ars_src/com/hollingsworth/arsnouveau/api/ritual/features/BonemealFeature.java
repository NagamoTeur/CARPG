package com.hollingsworth.arsnouveau.api.ritual.features;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.FeaturePlacementRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BonemealFeature implements IPlaceableFeature {
   double distance;
   double chance;

   public BonemealFeature(double distance, double chance) {
      this.distance = distance;
      this.chance = chance;
   }

   @Override
   public double distanceFromOthers() {
      return this.distance;
   }

   @Override
   public boolean onPlace(Level level, BlockPos pos, FeaturePlacementRitual placementRitual, RitualBrazierTile brazierTile) {
      ItemStack stack = new ItemStack(Items.f_42499_, 64);
      if ((double)level.f_46441_.m_188501_() < this.chance
         && BoneMealItem.applyBonemeal(stack, level, pos.m_7495_(), ANFakePlayer.getPlayer((ServerLevel)level))) {
         if (!level.f_46443_) {
            level.m_46796_(1505, pos.m_7495_(), 0);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public String getFeatureName() {
      return "bonemeal";
   }
}
